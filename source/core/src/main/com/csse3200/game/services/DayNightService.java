package com.csse3200.game.services;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.maingame.CheckWinLoseComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.events.EventHandler; 
import java.util.Random;



/**
 * The DayNightService class handles the day-night cycle within the game. It keeps track
 * of game time and triggers events when certain time thresholds, such as the end of a day,
 * are reached. It utilizes an event handler to manage day transitions and in-game events
 * triggered by time changes.
 */
public class DayNightService {
    private static final Logger logger = LoggerFactory.getLogger(DayNightService.class);
    public static final int MAX_DAYS = 5; // Maximum number of days
    public  long FIVE_MINUTES = 5 * 60 * 1000; // 5 minutes in milliseconds
    public long lastSecondCheck;
    public long lastUpgradeCheck;
    public long lastEndOfDayCheck;
    public long timeRemaining;
    private final GameTime gameTime;
    private boolean endOfDayTriggered = false;
    private boolean pastSecond = false;
    private boolean pastUpgrade = false; 
    private final EventHandler enddayEventHandler;
    private final EventHandler docketServiceEventHandler;
    private Random random;
    private int randomChoice;
    private int day;
    private int highQualityMeals = 0;
    

    /**
     * Constructs a new DayNightService. Initializes the game time and event handler,
     * and sets up the day-night cycle tracking.
     */
    public DayNightService() {
        this(new EventHandler());
        day = 0;
    }

    public DayNightService(EventHandler enddayEventHandler) {
        this(enddayEventHandler, ServiceLocator.getDocketService().getEvents());
        day = 0;
    }

    public DayNightService(EventHandler enddayEventHandler, EventHandler docketServiceEventHandler) {
        gameTime = ServiceLocator.getTimeSource();
        this.enddayEventHandler = enddayEventHandler;
        this.docketServiceEventHandler = docketServiceEventHandler;
        this.lastSecondCheck = gameTime.getTime();
        this.lastUpgradeCheck = gameTime.getTime();
        this.lastEndOfDayCheck = gameTime.getTime();
        this.timeRemaining = FIVE_MINUTES;
        this.random = new Random();
        day = 1; // was 1 but probably should be 0? ask calvin
        randomChoice = random.nextInt(10) * 1000;

        create();
    }

    public void create() {
        // ***Working version of Day cycle used "decisionDone"***
        enddayEventHandler.addListener("decisionDone", this::startNewDay);
        // enddayEventHandler.addListener("animationDone", this::startNewDay);
        enddayEventHandler.addListener("callpastsecond", this::updatepastSecond);

        // Listen for high-quality meal events
        ServiceLocator.getEntityService().getEvents().addListener("mealHighQuality", this::incrementHighQualityMealCount);
    }

    private void incrementHighQualityMealCount() {
        highQualityMeals += 1;
        logger.info("High-quality meal served! Total: " + highQualityMeals);
    }

    private void applyEndOfDayBonus() {
        int bonusGold = highQualityMeals * 1;
        ServiceLocator.getPlayerService().getPlayer().getComponent(CombatStatsComponent.class).addGold(bonusGold);
        logger.info("Bonus gold added: " + bonusGold);
    }

    private void resetHighQualityMealCount() {
        highQualityMeals = 0;
    }

    /**
     * Updates the game state by checking if enough time has passed to trigger
     * end-of-day events or timer-related events.
     */
    public void update() {
        if(gameTime.isPaused()){
            logger.info("Paused at DayNightService");
            return;
        }

        long currentTime = gameTime.getTime(); // Get the current game time

        // Check if it has been 1 second
        if(currentTime - lastSecondCheck >= 1000 && !pastSecond){
            pastSecond = true;
            this.timeRemaining -= 1000;
            enddayEventHandler.trigger("Second", this.timeRemaining);
            lastSecondCheck = currentTime;
        }

        if (currentTime - lastUpgradeCheck >= randomChoice && !pastUpgrade) {
            pastUpgrade = true;
            enddayEventHandler.trigger("upgrade");
            randomChoice = random.nextInt(10) * 1000;
        }

        if (this.timeRemaining == 0 && !endOfDayTriggered) {
            endOfDayTriggered = true;
            gameTime.setTimeScale(0);
            this.timeRemaining = FIVE_MINUTES;
            docketServiceEventHandler.trigger("Dispose");
            enddayEventHandler.trigger("endOfDay"); // Trigger the end of the day event
        }
    }

    /**
     * Starts a new day, resuming the game time and resetting relevant counters and orders.
     * This method is triggered after the end-of-day sequence has completed.
     *
     * Our working version was with the "decisionDone" listener, however, we limited our
     * Day cycle to end for just one day on request of Team 6 and hence commented out our last check
     * time updates
     */
    private void startNewDay() {

        applyEndOfDayBonus(); // Apply the bonus before checking win/loss condition

        // Checking if the game should end (i.e. it's the 5th day)
        if (day > MAX_DAYS) {
            logger.info("Game is ending after days!");
            ServiceLocator.getDayNightService().getEvents().trigger("endGame");
            return;

        } else {

            // Get the player's CheckWinLoseComponent to check for Day 1-4 win/loss conditions
            CheckWinLoseComponent checkWinLoseComponent = ServiceLocator.getPlayerService().getPlayer()
                    .getComponent(CheckWinLoseComponent.class);

            if (checkWinLoseComponent == null) {
                logger.error("CheckWinLoseComponent not found on player entity!");
                return;
            }

            // For days 1-4, check if the player has lost
            String gameState = checkWinLoseComponent.checkGameState();

            if ("LOSE".equals(gameState)) {
                logger.info("Game over! Player lost on day {}!", day);
                ServiceLocator.getDayNightService().getEvents().trigger("endGame");
                return;

            }
        }

        resetHighQualityMealCount(); // Reset count for next day

        logger.info("It's a new Day!");
        enddayEventHandler.trigger("newday");
        // // Resume the game time and reset the last check time
        lastSecondCheck = gameTime.getTime(); // Reset lastCheckTime to the current time
        lastEndOfDayCheck = gameTime.getTime();
        endOfDayTriggered = false;
        pastUpgrade = false;
        day += 1;
        gameTime.setTimeScale(1); // Resume game time
    }

    public int getDay() { return day;}

    public void setDay(int day) { this.day = day;}

    public EventHandler getEvents() {
        return enddayEventHandler;
      }

    public boolean getEndOfDayTriggered() {
        return endOfDayTriggered;
    }

    public void updatepastSecond() {
        pastSecond = false;
    }

}
