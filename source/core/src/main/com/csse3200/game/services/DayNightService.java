package com.csse3200.game.services;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.maingame.CheckWinLoseComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.events.EventHandler; 
import java.util.Random;



/**
 * The DayNightService class manages the day-night cycle within the game. It tracks game time,
 * handles day transitions, and triggers events at specific intervals, such as the end of a day
 * or when a meal of high quality is made. It also manages bonuses given to the player for high-quality meals.
 */
public class DayNightService {
    private static final Logger logger = LoggerFactory.getLogger(DayNightService.class);
    public long FIVE_MINUTES = 5L* 60 * 1000; // 5 minutes in milliseconds
    public static final int MAX_DAYS = 5; // Maximum number of days
    public long SEVENTY_FIVE_PERCENT = (long) (FIVE_MINUTES * 0.75);
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
     * Constructs a new DayNightService. Initializes the game time and event handlers.
     * This constructor sets the first day of the game and sets up event tracking.
     */
    public DayNightService() {
        this(new EventHandler());
        day = 0;
    }

    /**
     * Constructs a new DayNightService with a provided event handler.
     * This constructor is typically used for testing or more controlled event management.
     *
     * @param enddayEventHandler Event handler to manage the end-of-day events
     */
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
        randomChoice = random.nextInt((int) SEVENTY_FIVE_PERCENT);
        day = 1;
        randomChoice = random.nextInt(10) * 1000;

        create();
    }

    /**
     * Sets up listeners for end-of-day events and high-quality meal events.
     * This method initializes listeners for in-game decisions and meal-related events.
     */
    public void create() {
        // ***Working version of Day cycle used "decisionDone"***
        enddayEventHandler.addListener("decisionDone", this::startNewDay);
        // enddayEventHandler.addListener("animationDone", this::startNewDay);

        enddayEventHandler.addListener("callpastsecond", this::updatepastSecond);

        // Listen for high-quality meal events
        ServiceLocator.getEntityService().getEvents().addListener("mealHighQuality", this::incrementHighQualityMealCount);
    }

    /**
     * Increments the count of high-quality meals served. This method is triggered
     * when a "mealHighQuality" event occurs.
     */
    public void incrementHighQualityMealCount() {
        highQualityMeals += 1;
        logger.info("High-quality meal served! Total: " + highQualityMeals);
    }

    /**
     * Applies the end-of-day bonus based on the number of high-quality meals served.
     * The bonus is added to the player's gold before win/loss conditions are evaluated.
     */
    private void applyEndOfDayBonus() {
        int bonusGold = highQualityMeals * 1;
        ServiceLocator.getPlayerService().getPlayer().getComponent(CombatStatsComponent.class).addGold(bonusGold);
        logger.info("Bonus gold added: " + bonusGold);
    }

    /**
     * Resets the count of high-quality meals. This is typically called at the start of
     * a new day after applying bonuses.
     */
    private void resetHighQualityMealCount() {
        highQualityMeals = 0;
    }

    /**
     * Updates the game state, tracking elapsed time and checking whether it is time
     * to trigger end-of-day events or other timed events such as upgrades.
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
            // lastCheckTime3 = currentTime;
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
     * Starts a new day, resetting the time and relevant counters such as high-quality meals.
     * This method is triggered after the end-of-day events are processed.
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

    /**
     * Gets the current day in the game.
     *
     * @return the current day number
     */
    public int getDay() { return day;}

    /**
     * Sets the current day in the game.
     *
     * @param day the day to set as the current day
     */
    public void setDay(int day) { this.day = day;}

    /**
     * Gets the event handler for the end-of-day events.
     *
     * @return the event handler for end-of-day events
     */
    public EventHandler getEvents() {
        return enddayEventHandler;
      }

    /**
     * Checks if the end-of-day event has been triggered.
     *
     * @return true if the end-of-day event was triggered, false otherwise
     */
    public boolean getEndOfDayTriggered() {
        return endOfDayTriggered;
    }

    /**
     * Updates the state after a second passes, resetting the flag for tracking time.
     */
    public void updatepastSecond() {
        pastSecond = false;
    }

    /**
     * Gets the number of high-quality meals served during the day.
     *
     * @return the number of high-quality meals
     */
    public int getHighQualityMeals() {
        return highQualityMeals;
    }
}





