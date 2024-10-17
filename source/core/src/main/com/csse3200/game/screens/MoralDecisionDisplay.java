package com.csse3200.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.TimeUtils;


public class MoralDecisionDisplay extends UIComponent {

    private static final Logger logger = LoggerFactory.getLogger(MoralDecisionDisplay.class);
    private Table layout; // Layout manager
    private boolean isVisible;
    private final MainGameScreen game;
    private String question = "Set Question";
    private Label timerLabel; // Timer label
    private long startTime; // Track the start time

    private static final long DEFAULT_TIMER = 10000; // 10 seconds (in milliseconds)
    private long remainingTime; // Remaining time in milliseconds

//    public MoralDecisionDisplay(MainGameScreen game) {
//        super();
//        this.game = game;
//        isVisible = false;
//    }

    /**
     * Constructor for the MoralDecisionDisplay class.
     * Initializes the display and sets its visibility to false.
     * Retrieves the main game screen from the ServiceLocator.
     */
    public MoralDecisionDisplay() {
        super();
        this.game = ServiceLocator.getGameScreen();
        isVisible = false;
    }

    /**
     * Creates the moral decision screen.
     */
    @Override
    public void create() {
        super.create();

        // create a table layout
        layout = new Table();
        layout.setFillParent(true);
        layout.setVisible(isVisible);
        layout.setSkin(skin);
        stage.addActor(layout);

        // create gray background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        Texture pixmapTex = new Texture(pixmap);
        pixmap.dispose();
        Drawable blackBackground = new TextureRegionDrawable(new TextureRegion(pixmapTex));
        layout.setBackground(blackBackground);

        // set up the label using table layout
//        BitmapFont font = new BitmapFont();
        Label titleLabel = new Label("Moral Decision", skin);
        layout.add(titleLabel).pad(10).row();

        // load and position the raccoon image slightly to the left
        Texture imgTexture = new Texture(Gdx.files.internal("images/racoon.png"));
        Drawable imgDrawable = new TextureRegionDrawable(imgTexture);
        Image characterImage = new Image(imgDrawable); // Declared as local variable to avoid unused field warning

        // add raccoon image to the table and shift it left by adjusting padding
        layout.add(characterImage).left();

        // Add a secondary table to the left side of the view.
        Table questionSet = new Table();
        Label questionLabel = new Label("Do you want to save the raccoon?", skin);
        questionSet.add(questionLabel).pad(10).row();

        Label testingLabel = new Label("TestinvfHQ ETAAHWRVFBJWGEE HAET NVBbwegvkjER AHT QTHJTRAJ RTJRTg", skin);
//        layout.add(testingLabel).padRight(100).right().row();

        // add another table inside the existing table, in order to show the Decision question and buttons for yes/no
        Table decisionTable = new Table();
        Skin btnSkin = skin;
        btnSkin.setScale(2);
        Label decisionLabel = new Label(question, btnSkin);
        decisionTable.add(decisionLabel).pad(10).row();
        Actor button = new Actor();
        button.setHeight(50);
        button.setWidth(100);
        button.setColor(Color.GREEN);
        Button.ButtonStyle buttonStyle = new Button.ButtonStyle();
        Button noButton = new Button(button, buttonStyle);
//        decisionTable.add(yesButton).pad(10);
        decisionTable.add(noButton).pad(10).row();
//        layout.add(decisionTable).center().row();

        // add the secondary table to the main table
        layout.add(questionSet).padRight(100).right().row();
//        setupInputListener();
        // Timer label setup - Initialize with default timer
        timerLabel = new Label("Timer: " + (DEFAULT_TIMER / 1000) + "s", skin);
        layout.add(timerLabel).padLeft(10f).row(); // Add timer label to the layout

        entity.getEvents().addListener("triggerMoralScreen", this::toggleVisibility);

        //from team 2, added the listener for when game day ends to toggle visibility
        ServiceLocator.getDayNightService().getEvents().addListener("TOMORAL", () -> {
            logger.info("TOMORAL event received in MoralDecisionDisplay");
            show();
        });
    }


//    private void setupInputListener() {
//        stage.addListener(new InputListener() {
//            @Override
//            public boolean keyDown(InputEvent event, int keycode) {
//                if (keycode == com.badlogic.gdx.Input.Keys.M) {
//                    toggleVisibility();
//                    return true;
//                }
//                return false;
//            }
//        });
//    }


    /**
     * Initialise the User Interface
     */
    private void initialiseUI() {
        Label titleLabel = new Label("moral deiciosn", new Label.LabelStyle(new BitmapFont(), Color.PINK));
        layout.add(titleLabel).pad(10).row();
    }

    /**
     * Updates the timer label with the remaining time in seconds.
     * @param timerLabel the label to update
     * @param remainingTime the remaining time in milliseconds
     */
    private void updateTimerLabel(Label timerLabel, long remainingTime) {
        // Convert remaining time to seconds and update the label's text
        String timeLeft = "Timer: " + (remainingTime / 1000) + "s";
        timerLabel.setText(timeLeft);
    }

    /**
     * Sets the question to be displayed on the moral decision screen.
     * @param question the question to display
     * @return true if the question was set successfully
     */
    public boolean setQuestion(String question) {
        this.question = question;
        return true;
    }

    /**
     * Shows the moral decision screen.
     */
    private void show() {
        isVisible = true;
        layout.setVisible(isVisible);
        game.pause(); // Pause the game when the display is shown
        startTime = TimeUtils.millis(); // Set the start time when shown
        remainingTime = DEFAULT_TIMER; // Reset timer to the default value

    }

    /**
     * Hides the moral decision screen.
     */
    private void hide() {
        isVisible = false;
        layout.setVisible(isVisible);
        game.resume(); // Resume the game when the display is hidden
        logger.info("decisionDone about to be triggered");
        ServiceLocator.getDayNightService().getEvents().trigger("decisionDone");

    }

    /**
     * Toggles the visibility of the moral decision screen.
     */
    private void toggleVisibility(int day) {
        logger.debug(" Day - {}", day);
//        this.update();
        if (isVisible) {
            hide();
        } else {
            show();
        }
    }

    /**
     * Returns the visibility of the moral decision screen.
     * @return true if the screen is visible
     */
    public boolean getVisible() {
        return this.isVisible;
    }

    /**
     * Updates the moral decision screen.
     */
    @Override
    public void update() {
//        super.update();
//        layout.clear();
//        this.create();


        // time stuff
        if (isVisible) {
            // Calculate the elapsed time since the moral decision screen was shown
            long elapsedTime = TimeUtils.timeSinceMillis(startTime);

            // Calculate the remaining time by subtracting the elapsed time from the default timer
            remainingTime = DEFAULT_TIMER - elapsedTime;

            // If there's still time left, update the label with the remaining time in seconds
            if (remainingTime > 0) {
                // Replace the timer text in the timer label
                updateTimerLabel(timerLabel, remainingTime);

            } else {
                // If the time has expired, hide the moral decision screen
                hide();
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    @Override
    public void setStage(Stage stage) {
        // Method intentionally left empty (to handle empty method warning)
    }
}
