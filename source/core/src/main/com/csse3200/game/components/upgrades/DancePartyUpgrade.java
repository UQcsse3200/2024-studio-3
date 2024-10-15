package com.csse3200.game.components.upgrades;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.components.ordersystem.OrderManager;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;

import com.csse3200.game.ui.UIComponent;
import com.csse3200.game.services.GameTime;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.csse3200.game.components.CombatStatsComponent;

/**
 * The DancePartyUpgrade class represents an upgrade that, when activated,
 * triggers a "Dancing" which causes current dockets within the game to pause
 * Their time, within the MainGameOrderTicketDisplay
 */

public class DancePartyUpgrade extends UIComponent implements Upgrade {
    private static final long UPGRADE_DURATION = 30000;
    private OrderManager orderManager;
    private final GameTime gameTime;
    private boolean isActive;
    private long startTime = -1;
    private float activeTimeRemaining;
    private CombatStatsComponent combatStatsComponent;

    private static final String[] greenTexture = {"images/green_fill.png"};
    private static final String[] whiteBgTexture = {"images/white_background.png"};
    private Table layout;
    private Label text; // the "Upgrade" text above the speedMeter
    private ProgressBar meter; // the meter that show the remaining time
    private boolean isVisible;
    private Sound bgEffect;
    private boolean playSound = false;


    public DancePartyUpgrade() {
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
        });
        this.orderManager = orderManager;
        gameTime = ServiceLocator.getTimeSource();
        this.isActive = false;

        ServiceLocator.getRandomComboService().getEvents().addListener("Dance party", this::activate);
    }

    @Override
    public void create() {
        super.create();
        ServiceLocator.getResourceService().loadTextures(whiteBgTexture);
        ServiceLocator.getResourceService().loadTextures(greenTexture);
        ServiceLocator.getResourceService().loadAll();

        bgEffect = Gdx.audio.newSound(Gdx.files.internal("sounds/dance_party.mp3"));

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
        if (meter == null) {
            Texture whiteBgTexture = ServiceLocator.getResourceService().getAsset("images/white_background.png", Texture.class);
            Texture fillTexture = ServiceLocator.getResourceService().getAsset("images/green_fill.png", Texture.class);

            ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
            style.background = new TextureRegionDrawable(new TextureRegion(whiteBgTexture));
            style.knobBefore = new TextureRegionDrawable(new TextureRegion(fillTexture));

            style.background = new TextureRegionDrawable(new TextureRegion(whiteBgTexture));
            style.background.setMinHeight(15);
            style.background.setMinWidth(10);

            // Setting green fill color
            style.knobBefore = new TextureRegionDrawable(new TextureRegion(fillTexture));
            style.knobBefore.setMinHeight(15);
            style.background.setMinWidth(10);

            meter = new ProgressBar(0f, 1f, 0.01f, false, style);
            meter.setValue(1f);
            meter.setPosition(8, 500);

            text = new Label("Upgrade", skin);
            text.setPosition(meter.getX(), meter.getY() + meter.getHeight() + 8);
            layout.add(text).row();
            layout.add(meter);
        }
    }

     /**
     * Activates the Dance Party Upgrade if conditions are met:
     * by triggering Dancing in get Docketservice, pausing time
     */
     @Override
     public void activate() {
         if (!isActive && startTime == -1 && combatStatsComponent.getGold() >= 20) {
             activeTimeRemaining = UPGRADE_DURATION;
             dancePartyCost();
             isActive = true;
             layout.setVisible(true);

             ServiceLocator.getDocketService().getEvents().trigger("Dancing");
         }
     }

    /**
     * Deactivates the Dance Party Upgrade 
     * by triggering UnDancing in get Docketservice, unpausing time
     */
    @Override
    public void deactivate() {
        isActive = false;
        layout.setVisible(false);
        meter.remove();
        text.remove();

        ServiceLocator.getDocketService().getEvents().trigger("UnDancing");
    }


    public void dancePartyCost() {
        combatStatsComponent.addGold(-20);
    }

    @Override
    public void update() {
        if (isActive) {
            stage.addActor(layout);
            stage.addActor(meter);
            stage.addActor(text);
            activeTimeRemaining -= gameTime.getDeltaTime() * 1000;
            meter.setValue((activeTimeRemaining / (float) UPGRADE_DURATION));

            if (activeTimeRemaining <= 800 && !playSound) {
                long countDownId = bgEffect.play();
                bgEffect.setVolume(countDownId, 0.2f);
                playSound = true;
            }

            if (activeTimeRemaining <= 0) {
                deactivate();
                playSound = false;
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
            if (isActive) {
                deactivate();
            } else {
                activate();
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
