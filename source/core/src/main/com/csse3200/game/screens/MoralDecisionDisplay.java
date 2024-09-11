package com.csse3200.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;

public class MoralDecisionDisplay extends UIComponent {
    private Table layout; // Layout manager
    private boolean isVisible;
    private final MainGameScreen game;
    private Image characterImage;
    private Label timerLabel; // Timer label
    private long startTime; // Track the start time

    private static final long DEFAULT_TIMER = 10000; // 10 seconds (in milliseconds)
    private long remainingTime; // Remaining time in milliseconds



    public MoralDecisionDisplay(MainGameScreen game) {
        super();
        this.game = game;
        isVisible = false;
    }

    public MoralDecisionDisplay() {
        super();
        this.game = ServiceLocator.getGameScreen();
        isVisible = false;
    }

    @Override
    public void create() {
        super.create();

        // Create a table layout
        layout = new Table();
        layout.setFillParent(true);
        layout.setVisible(isVisible);
        stage.addActor(layout);

        // Create gray background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.GRAY);
        pixmap.fill();
        Texture pixmapTex = new Texture(pixmap);
        pixmap.dispose();
        Drawable blackBackground = new TextureRegionDrawable(new TextureRegion(pixmapTex));
        layout.setBackground(blackBackground);

        // Set up the label using table layout
        BitmapFont font = new BitmapFont();
        Label titleLabel = new Label("Moral Decision", new Label.LabelStyle(font, Color.WHITE));
        layout.add(titleLabel).pad(10).row();

        // Load and position the raccoon image slightly to the left
        Texture imgTexture = new Texture(Gdx.files.internal("images/racoon.png"));
        Drawable imgDrawable = new TextureRegionDrawable(imgTexture);
        characterImage = new Image(imgDrawable);

        // Add raccoon image to the table and shift it left by adjusting padding
        layout.add(characterImage).padRight(1000).center().row(); // Add padding to move left

        // Timer label setup - Initialize with default timer
        timerLabel = new Label("Timer: " + (DEFAULT_TIMER / 1000) + "s", new Label.LabelStyle(font, Color.RED));
        layout.add(timerLabel).padLeft(10f).row(); // Add timer label to the layout


        entity.getEvents().addListener("triggerMoralScreen", this::toggleVisibility);
    }

//    @Overridex
//    public void update() {
//        if (isVisible) {
//            // Calculate elapsed time since the moral decision screen was shown
//            long elapsedTime = TimeUtils.timeSinceMillis(startTime);
//
//            // Calculate remaining time by subtracting the elapsed time from the default timer
//            remainingTime = DEFAULT_TIMER - elapsedTime;
//
//
//            // Update the timer and background if there's still time left
//            if (remainingTime > 0) {
//                // Update the label with remaining time in seconds
//                timerLabel.setText("Timer: " + (remainingTime / 1000) + "s");
//
//                // Optionally, update the visual background here if needed
//            } else {
//                // If the time has expired, hide the moral decision screen
//                hide();
//            }
//        }
//    }

    @Override
    public void update() {
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

    private void updateTimerLabel(Label timerLabel, long remainingTime) {
        // Convert remaining time to seconds and update the label's text
        String timeLeft = "Timer: " + (remainingTime / 1000) + "s";
        timerLabel.setText(timeLeft);
    }


    public void show() {
        isVisible = true;
        layout.setVisible(isVisible);
        game.pause(); // Pause the game when the display is shown
        startTime = TimeUtils.millis(); // Set the start time when shown
        remainingTime = DEFAULT_TIMER; // Reset timer to the default value

    }

    public void hide() {
        isVisible = false;
        layout.setVisible(isVisible);
        game.resume(); // Resume the game when the display is hidden
    }

    public boolean toggleVisibility() {
        if (isVisible) {
            hide();
        } else {
            show();
        }
        return true;
    }

    public boolean getVisible() {
        return this.isVisible;
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    @Override
    public void setStage(Stage stage) {
    }

}
