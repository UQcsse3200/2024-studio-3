package com.csse3200.game.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.maingame.EndDayDisplay;
// import com.csse3200.game.components.maingame.MoralDilemmaDisplay;  
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.entities.Entity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class DayNightService extends Component{
    private static final Logger logger = LoggerFactory.getLogger(DayNightService.class);
    private static final long FIVE_MINUTES = 1000; // 5 minutes in milliseconds
    private long lastCheckTime;
    private int currentDay = 1;
    private final GameTime gameTime;
    private boolean endOfDayTriggered = false;

    private final EndDayDisplay endDayDisplay;
    // private final MoralDilemmaDisplay moralDilemmaDisplay;
    private final MainGameScreen gameScreen;
    private final Label dayLabel; // Label to show the current day
    private final Stage stage;
    private final Entity entity; // Entity to handle events

    public DayNightService(GameTime gameTime, MainGameScreen gameScreen, Stage stage) {
        this.gameTime = gameTime;
        this.gameScreen = gameScreen;
        this.endDayDisplay = new EndDayDisplay(gameScreen);
        // this.moralDilemmaDisplay = new MoralDilemmaDisplay(gameScreen);
        this.stage = stage;
        this.entity = new Entity(); // Create an entity to manage events
        this.lastCheckTime = gameTime.getTime(); // Initialize with current game time

        // Initialize the label to display the current day
        dayLabel = new Label("Day: " + currentDay, new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        setupDayLabel();

        // Initialize event listeners
        create(); // Call create instead of setupEventListeners
    }

    /**
     * Use the create method to set up event listeners for the end-of-day cycle.
     */
    public void create() {
        entity.getEvents().addListener("endOfDay", this::triggerEndOfDay);
        // entity.getEvents().addListener("moralDilemma", this::triggerMoralDilemma);
        entity.getEvents().addListener("startNewDay", this::startNewDay);
    }

    /**
     * Update the day-night cycle. This should be called periodically in the main game loop.
     */
    public void update() {
        long currentTime = gameTime.getTime(); // Get the current game time

        // Check if 5 minutes have passed and trigger the end of the day
        if (currentTime - lastCheckTime >= FIVE_MINUTES && !endOfDayTriggered) {
            entity.getEvents().trigger("endOfDay"); // Trigger the end of the day event
        }
    }

    /**
     * Sets up the label to display the current day at the top-left corner of the screen.
     */
    private void setupDayLabel() {
        Table table = new Table();
        table.top().left(); // Position the label at the top-left corner
        table.setFillParent(true); // Ensure the table takes up the entire screen
        table.add(dayLabel).padTop(10).padLeft(10).align(Align.topLeft);

        stage.addActor(table); // Add the table (and label) to the stage
    }

    /**
     * Triggers the end of the day, showing the end-of-day screen and pausing the game time.
     * jjj
     */
    private void triggerEndOfDay() {
        logger.info("End of Day triggered.");
        endOfDayTriggered = true;

        // Pause the in-game time
        gameTime.setTimeScale(0); // Pauses in-game time

        // Show the end-of-day screen
        endDayDisplay.toggleVisibility();

        // After the end-of-day screen, trigger the moral dilemma event
        // while true:
        //     if p is pressed:
        //     // Gdx.app.postRunnable(() -> entity.getEvents().trigger("moralDilemma"));
        //     break 
    }

    //instead, put moral dilemma in hide of end of display
    //put start new day in hide of moral dilemma

    /**
     * Starts a new day, updating the day counter, resuming the game time, and resetting orders.
     */
    private void startNewDay() {
        logger.info("Starting new game day.");
        currentDay++;
        dayLabel.setText("Day: " + currentDay); // Update the day label

        // Reset orders for the new day
        // resetOrders();

        // Resume the game time and reset the last check time
        lastCheckTime = gameTime.getTime(); // Reset lastCheckTime to the current time
        endOfDayTriggered = false;

        gameTime.setTimeScale(1); // Resume game time
        logger.info("Game time resumed.");
    }

    /**
     * Resets the orders for the new day by triggering the appropriate event in DocketService.
     */
    // private void resetOrders() {
    //     ServiceLocator.getDocketService().getEvents().trigger("resetOrders");
    //     logger.info("Orders reset for the new day.");
    // }

    // include money retain logic here
}