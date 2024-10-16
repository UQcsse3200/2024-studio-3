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
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;

/**
 * Manages the Extortion Upgrade component, allowing players to receive extra
 * gold per customer
 */
public class ExtortionUpgrade extends UIComponent implements Upgrade {
    private boolean isActive;
    private final long upgradeDuration = 30000;
    private final GameTime gameTime;
    private long actviateTime;
    private CombatStatsComponent combatStatsComponent;

    private static final String[] greenTexture = {"images/green_fill.png"};
    private static final String[] whiteBgTexture = {"images/white_background.png"};
    public Table layout;
    public Label text; // the "Upgrade" text above the meter
    public ProgressBar meter; // the meter that show the remaining time
    private boolean isVisible;
    private Sound bgEffect;
    private boolean playSound = false;
    private float activateTimeRemaining;


    public ExtortionUpgrade() {
        this.isActive = false;
        this.gameTime = ServiceLocator.getTimeSource();
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) ->
        {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
        });
        ServiceLocator.getRandomComboService().getEvents().addListener("Extortion", this::activate);
        ServiceLocator.getRandomComboService().getEvents().addListener("Extortionoff", this::deactivate);
    }

    public ExtortionUpgrade(CombatStatsComponent combatStatsComponent) {
        this.isActive = false;
        this.gameTime = ServiceLocator.getTimeSource();
        this.combatStatsComponent = combatStatsComponent;
        ServiceLocator.getRandomComboService().getEvents().addListener("Extortion", this::activate);
        ServiceLocator.getRandomComboService().getEvents().addListener("Extortionoff", this::deactivate);
    }

    @Override
    public void create() {
        super.create();
        ServiceLocator.getResourceService().loadTextures(whiteBgTexture);
        ServiceLocator.getResourceService().loadTextures(greenTexture);
        ServiceLocator.getResourceService().loadAll(); // Ensures the texture is loaded

        // https://pixabay.com/sound-effects/fx-mario-brothers-coin-grab-236423/
        bgEffect = Gdx.audio.newSound(Gdx.files.internal("sounds/extortion.mp3"));
        layout = new Table();
        layout.setFillParent(true);
        layout.setVisible(isVisible);
//        setupMeter();
    }

    /**
     * Sets up the speed meter UI component, including its styling and positioning.
     * Also initializes the accompanying label.
     */
    private void setupMeter() {
        Texture whiteBgTexture = ServiceLocator
                .getResourceService().getAsset("images/white_background.png", Texture.class);
        Texture fillTexture = ServiceLocator
                .getResourceService().getAsset("images/green_fill.png", Texture.class);

        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();

        // Setting white background
        style.background = new TextureRegionDrawable(new TextureRegion(whiteBgTexture));
        style.background.setMinHeight(15);
        style.background.setMinWidth(10);

        // Setting green fill color
        style.knobBefore = new TextureRegionDrawable(new TextureRegion(fillTexture));
        style.knobBefore.setMinHeight(15);
        style.background.setMinWidth(10);


        // Only show the speed meter if it is activated
        if (isActive) {
            meter = new ProgressBar(0f, 1f, 0.01f, false, style);
            meter.setValue(1f); // Initially, the meter is full
            meter.setPosition(30, 250);

            // Set up text
            text =  new Label("Upgrade", skin);
            text.setPosition(meter.getX(), meter.getY() + meter.getHeight() + 8); // Placed above meter
        }
        else {
            meter = null;
            text = null;
        }
    }

    /**
     * Activates the extortion upgrade
     */
    public void activate() {
        if (combatStatsComponent.getGold() >= 40){
            this.actviateTime = gameTime.getTime();
            this.isActive = true;
            combatStatsComponent.addGold(-40);
            ServiceLocator.getRandomComboService().getEvents().trigger("extortion active");

            // Display the meter
            activateTimeRemaining = upgradeDuration;
            isVisible = true;
            layout.setVisible(true);
            setupMeter();
        }
        else {
            ServiceLocator.getRandomComboService().getEvents().trigger("notenoughmoney");
        }
    }

    /**
     * Deactivates the extortion upgrade
     */
    public void deactivate() {
        this.isActive = false;

        // Remove meter
        isVisible = false;
        layout.setVisible(false);
        ServiceLocator.getRandomComboService().getEvents().trigger("extortion unactive");


        // Ensure the text and meter are removed from the stage after time finish
        if (meter != null && meter.hasParent()) {
            meter.remove();
            text.remove();
        }
    }

    /**
     * checks to see if the duration of ugprade has ended and consequently deactivates
     */
    @Override
    public void update() {
//        if (isActive && (gameTime.getTime() - actviateTime >= upgradeDuration)) {
//            deactivate();
//        }

        if (isActive) {
            stage.addActor(layout);
            stage.addActor(meter);
            stage.addActor(text);
            activateTimeRemaining -= (gameTime.getDeltaTime() * 1000); // Calculate speed boot duration
            meter.setValue((activateTimeRemaining / (float) upgradeDuration)); // Update progress bar

            if (activateTimeRemaining <= 800 && !playSound) {
                long countDownId = bgEffect.play();
                bgEffect.setVolume(countDownId, 0.2f);
                playSound = true;
            }

            // Check if boost has expired
            if (activateTimeRemaining <= 0) {
                deactivate();
                playSound = false;
            }
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getResourceService().unloadAssets(whiteBgTexture);
        ServiceLocator.getResourceService().unloadAssets(greenTexture);
    }

    @Override
    protected void draw(SpriteBatch batch) {

    }

    @Override
    public void setStage(Stage mock) {
        this.stage = mock;
    }

    public boolean isActive() {
        return isActive;
    }

    public boolean isVisible() {
        return isVisible;
    }

    public long getUpgradeDuration() {
        return upgradeDuration;
    }

    public float getActivateTimeRemaining() {
        return activateTimeRemaining;
    }

    public boolean getPlaySound() {
        return playSound;
    }
}