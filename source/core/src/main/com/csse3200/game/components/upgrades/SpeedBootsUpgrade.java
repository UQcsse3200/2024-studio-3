package com.csse3200.game.components.upgrades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.player.KeyboardPlayerInputComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;


/**
 * Manages the Speed Boots Upgrade UI component, handling activation, deactivation,
 * visual updates, and related sound effects.
 * when activated, makes player move twice as fast
 */
public class SpeedBootsUpgrade extends UIComponent implements Upgrade {
    private static final long BOOST_DURATION = 30000; // 30 sec
    private static final float NORMAL_SPEED = 1f;
    private static final float BOOSTED_SPEED = 2f; // 2x speed
    private CombatStatsComponent combatStatsComponent;
    private KeyboardPlayerInputComponent keyboardPlayerInputComponent;
    private final GameTime gameTime;
    private long boostStartTime = -1;
    private static final String[] greenTexture = {"images/green_fill.png"};
    private static final String[] whiteBgTexture = {"images/white_background.png"};
    private boolean isActivate;
    private Table layout;
    private Label text; // the "Upgrade" text above the speedMeter
    private ProgressBar speedMeter; // the meter that show the remaining time
    private boolean isVisible;
    private float activeTimeRemaining;
    private Sound countDown;
    private boolean playSound = false;


    public SpeedBootsUpgrade() {
        super();
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
            this.keyboardPlayerInputComponent = player.getComponent(KeyboardPlayerInputComponent.class);
        });
        gameTime = ServiceLocator.getTimeSource();
        isActivate = false;
        
    }

    @Override
    public void create() {
        super.create();
        ServiceLocator.getResourceService().loadTextures(whiteBgTexture);
        ServiceLocator.getResourceService().loadTextures(greenTexture);
        ServiceLocator.getResourceService().loadAll(); // Ensures the texture is loaded
        // https://mixkit.co/free-sound-effects/countdown/
        countDown = Gdx.audio.newSound(Gdx.files.internal("sounds/upgrade_count_down.wav"));
        layout = new Table();
        layout.setFillParent(true);
        layout.setVisible(isVisible);
        // setupInputListener();
        ServiceLocator.getRandomComboService().getEvents().addListener("Speed", this::activate); 
 
    }

    /**
     * Activates the speed boost if conditions are met (e.g., sufficient gold).
     * Deducts the cost, updates player speed, and displays the UI.
     */
    public void activate() {
        // Change back to 20 after testing
        if (!isActivate && boostStartTime == -1 && combatStatsComponent.getGold() >= 20) {
            keyboardPlayerInputComponent.setWalkSpeed(BOOSTED_SPEED);
            activeTimeRemaining = BOOST_DURATION;
            speedCost();
            isActivate = true;
            isVisible = true;
            layout.setVisible(true);
            setupSpeedMeter();
        }
        else{
            ServiceLocator.getRandomComboService().getEvents().trigger("notenoughmoney");
        }
    }

    /**
     * Deactivates the speed boost, resetting player speed and hiding the UI.
     * Cleans up UI elements and resets relevant flags.
     */
    public void deactivate() {
        keyboardPlayerInputComponent.setWalkSpeed(NORMAL_SPEED);
        boostStartTime = -1;
        isActivate = false;
        isVisible = false;
        layout.setVisible(false);

        // Ensure the text and meter are removed from the stage after time finish
        if (speedMeter != null && speedMeter.hasParent()) {
            speedMeter.remove();
            text.remove();
        }
    }

    /**
     * Updates the SpeedBootsUpgrade component each frame, managing the countdown timer
     * and handling deactivation when the boost duration expires.
     */
    @Override
    public void update() {
        if (isActivate) {
            stage.addActor(layout);
            stage.addActor(speedMeter);
            stage.addActor(text);
            activeTimeRemaining -= gameTime.getDeltaTime() * 1000; // Calculate speed boot duration
            speedMeter.setValue((activeTimeRemaining /  BOOST_DURATION)); // Update progress bar

            if (activeTimeRemaining <= 4000 && !playSound) {
                long countDownId = countDown.play();
                countDown.setVolume(countDownId, 0.05f);
                playSound = true;
            }

            // Check if boost has expired
            if (activeTimeRemaining <= 0) {
                deactivate();
                playSound = false;
            }
        }
    }

    /**
     * Sets up the speed meter UI component, including its styling and positioning.
     * Also initializes the accompanying label.
     */
    private void setupSpeedMeter() {
        Texture whiteBcgTexture = ServiceLocator
                .getResourceService().getAsset("images/white_background.png", Texture.class);
        Texture fillTexture = ServiceLocator
                .getResourceService().getAsset("images/green_fill.png", Texture.class);

        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();

        // Setting white background
        style.background = new TextureRegionDrawable(new TextureRegion(whiteBcgTexture));
        style.background.setMinHeight(15);
        style.background.setMinWidth(10);

        // Setting green fill color
        style.knobBefore = new TextureRegionDrawable(new TextureRegion(fillTexture));
        style.knobBefore.setMinHeight(15);
        style.background.setMinWidth(10);


        // Only show the speed meter if it is activated
        if (isActivate) {
            speedMeter = new ProgressBar(0f, 1f, 0.01f, false, style);
            speedMeter.setValue(1f); // Initially, the meter is full
            speedMeter.setPosition(8, 500);

            // Set up text
            text =  new Label("Upgrade", skin);
            text.setPosition(speedMeter.getX(), speedMeter.getY() + speedMeter.getHeight() + 8); // Placed above meter
        }
        else {
            speedMeter = null;
            text = null;
        }
    }


    /**
     * Decrement cost when speed boot is activate.
     */
    public void speedCost() {
        combatStatsComponent.addGold(-20);
    }

    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getResourceService().unloadAssets(whiteBgTexture);
        ServiceLocator.getResourceService().unloadAssets(greenTexture);
    }
    @Override
    protected void draw(SpriteBatch batch) {
        // This method is intended for custom rendering of the UI component.
        // However, the SpeedBootsUpgrade UI does not require any additional
        // drawing logic, as all visual elements are managed by Scene2D
        // and drawn automatically. Therefore, this method is left empty.

    }

    @Override
    public void setStage(Stage mock) {
        // This method is intended to set the stage for the UI component.
        // However, this component manages its own UI elements and does not
        // require an explicit stage setting. As such, calling this method
        // does not have any meaningful effect.
        throw new UnsupportedOperationException("setStage is not supported for SpeedBootsUpgrade.");

    }
}
