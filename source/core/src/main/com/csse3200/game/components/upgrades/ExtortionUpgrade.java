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
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.station.StationServingComponent;
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
    private static final long UPGRADE_DURATION = 30000;
    private final GameTime gameTime;
    private long activateTime;
    private float activateTimeRemaining;
    private CombatStatsComponent combatStatsComponent;
    private StationServingComponent stationServingComponent;

    private static final String[] greenTexture = {"images/green_fill.png"};
    private static final String[] whiteBgTexture = {"images/white_background.png"};
    private Table layout;
    private Label text;
    private ProgressBar meter;
    private boolean isVisible;
    private Sound bgEffect;
    private boolean playSound = false;


    public ExtortionUpgrade() {
        this.isActive = false;
        this.gameTime = ServiceLocator.getTimeSource();
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
        });
        ServiceLocator.getLevelService().getEvents().addListener("startLevel", this::getStationServingComponent);
        ServiceLocator.getRandomComboService().getEvents().addListener("Extortion", this::activate); 
    }

    @Override
    public void create() {
        super.create();
        ServiceLocator.getResourceService().loadTextures(whiteBgTexture);
        ServiceLocator.getResourceService().loadTextures(greenTexture);
        ServiceLocator.getResourceService().loadAll();

        // https://pixabay.com/sound-effects/fx-mario-brothers-coin-grab-236423/
        bgEffect = Gdx.audio.newSound(Gdx.files.internal("sounds/extortion.mp3"));
        layout = new Table();
        layout.setFillParent(true);
        layout.setVisible(false);
        setupMeter();
    }

    /**
     * Sets up the speed meter UI component, including its styling and positioning.
     * Also initializes the accompanying label.
     */
    private void setupMeter() {
        Texture whiteBgTexture = ServiceLocator.getResourceService().getAsset("images/white_background.png", Texture.class);
        Texture fillTexture = ServiceLocator.getResourceService().getAsset("images/green_fill.png", Texture.class);

        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
        style.background = new TextureRegionDrawable(new TextureRegion(whiteBgTexture));
        style.knobBefore = new TextureRegionDrawable(new TextureRegion(fillTexture));

        meter = new ProgressBar(0f, 1f, 0.01f, false, style);
        meter.setValue(1f);

        text = new Label("Upgrade", skin);
        layout.add(text).row();
        layout.add(meter);
    }

    /**
     * Shows the meter and layout on the stage.
     */
    private void showMeter() {
        if (meter != null && !meter.hasParent()) {
            stage.addActor(layout);
        }
    }

    /**
     * Hides and removes the meter and layout from the stage.
     */
    private void hideMeter() {
        if (meter != null && meter.hasParent()) {
            meter.remove();
            text.remove();
            layout.setVisible(false);
        }
    }

    /**
     * Activates the extortion upgrade
     */
    public void activate() {
        if (combatStatsComponent.getGold() >= 40) {
            this.activateTime = gameTime.getTime();
            this.isActive = true;
            combatStatsComponent.addGold(-40);

            stationServingComponent.setGoldMultiplier(2);

            activateTimeRemaining = UPGRADE_DURATION;
            layout.setVisible(true);
            showMeter();

            ServiceLocator.getRandomComboService().getEvents().trigger("ExtortionActive", true);
        } else {
            ServiceLocator.getRandomComboService().getEvents().trigger("notenoughmoney");
        }
    }

    /**
     * Deactivates the extortion upgrade
     */
    public void deactivate() {
        this.isActive = false;
        layout.setVisible(false);
        hideMeter();

        stationServingComponent.setGoldMultiplier(1);
        ServiceLocator.getRandomComboService().getEvents().trigger("ExtortionActive", false);
    }

    public boolean isActive() {
        return isActive;
    }

    /**
     * Finds an instance of StationServingComponent attached to an entity
     * @return StationServingComponent
     */
    private void getStationServingComponent() {
        for (Entity entity : ServiceLocator.getEntityService().getEntities()) {
            StationServingComponent component = entity.getComponent(StationServingComponent.class);
            if (component != null) {
                stationServingComponent = component;
            }
        }
    }

    /**
     * checks to see if the duration of ugprade has ended and consequently deactivates
     */
    @Override
    public void update() {
        if (isActive) {
            activateTimeRemaining -= gameTime.getDeltaTime() * 1000;
            meter.setValue((activateTimeRemaining / (float) UPGRADE_DURATION));

            if (activateTimeRemaining <= 800 && !playSound) {
                long countDownId = bgEffect.play();
                bgEffect.setVolume(countDownId, 0.2f);
                playSound = true;
            }

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

    }
}

