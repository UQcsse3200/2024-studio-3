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
    public Table layout;
    public Label text; // the "Upgrade" text above the meter
    public ProgressBar meter; // the meter that show the remaining time
    private Sound bgEffect;
    private boolean playSound = false;


    /**
     * Constructor for DancePartyUpgrade that initializes the upgrade and sets up event listeners.
     * It listens for "playerCreated" and "Dance party" events.
     */
    public DancePartyUpgrade() {
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> this.combatStatsComponent = player.getComponent(CombatStatsComponent.class));
        gameTime = ServiceLocator.getTimeSource();
        this.isActive = false;

        ServiceLocator.getRandomComboService().getEvents().addListener("Dance party", this::activate);
        ServiceLocator.getRandomComboService().getEvents().addListener("Dance partyoff", this::deactivate);
    }

    /**
     * Constructor for DancePartyUpgrade with an explicit CombatStatsComponent.
     * This initializes the upgrade and registers the same event listeners as the default constructor.
     *
     * @param combatStatsComponent the CombatStatsComponent for the player
     */
    public DancePartyUpgrade(CombatStatsComponent combatStatsComponent) {
        this.combatStatsComponent = combatStatsComponent;
        this.orderManager = orderManager;
        gameTime = ServiceLocator.getTimeSource();
        this.isActive = false;

        ServiceLocator.getRandomComboService().getEvents().addListener("Dance party", this::activate);
        ServiceLocator.getRandomComboService().getEvents().addListener("Dance partyoff", this::deactivate);
    }

    /**
     * Initializes the Dance Party upgrade by loading necessary assets,
     * such as textures and sound effects, and setting up the UI components
     * (progress meter and label). This method is called when the component is created.
     */
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
             setupMeter();
             System.out.println("Dance party meter value on activate: " + meter.getValue());

             ServiceLocator.getRandomComboService().getEvents().trigger("Dancing");
         }
         else{
             ServiceLocator.getRandomComboService().getEvents().trigger("notenoughmoney");
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
        // Ensure the text and meter are removed from the stage after time finish
        if (meter != null && meter.hasParent()) {
            meter.remove();
            text.remove();
        }

        ServiceLocator.getDocketService().getEvents().trigger("UnDancing");
    }

    /**
     * Handles the cost of the Dance Party upgrade, deducting gold from the player's CombatStatsComponent.
     */
    public void dancePartyCost() {
        combatStatsComponent.addGold(-20);
    }

    /**
     * Checks and updates the remaining time for the Dance Party upgrade, updating the progress bar meter.
     * It also checks if the upgrade's time has run out and deactivates it accordingly.
     */
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

//        if (Gdx.input.isKeyJustPressed(Input.Keys.V)) {
//            if (isActive) {
//                deactivate();
//            } else {
//                activate();
//            }
//        }
    }

    /**
     * Disposes of assets and cleans up when the upgrade is no longer needed.
     * Unloads the textures used by the upgrade.
     */
    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getResourceService().unloadAssets(whiteBgTexture);
        ServiceLocator.getResourceService().unloadAssets(greenTexture);
    }

    /**
     * Draw method required by the UIComponent class, but not used in this upgrade.
     *
     * @param batch the SpriteBatch used to draw
     */
    @Override
    protected void draw(SpriteBatch batch) {

    }

    /**
     * Sets the stage for the UI components, such as the layout, meter, and text.
     *
     * @param mock the Stage to which the UI components belong
     */
    @Override
    public void setStage(Stage mock) {
        this.stage = mock;
    }

    /**
     * Gets the current active state of the Dance Party upgrade.
     *
     * @return true if the upgrade is currently active, false otherwise
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * Retrieves the remaining time for which the upgrade will stay active.
     *
     * @return the remaining active time in milliseconds
     */
    public float getActiveTimeRemaining() {
        return activeTimeRemaining;
    }

    /**
     * Retrieves the total upgrade duration.
     *
     * @return the total duration of the upgrade in milliseconds
     */
    public long getUpgradeDuration() {
        return UPGRADE_DURATION;
    }

    /**
     * Retrieves the current state of the playSound flag, which indicates whether the upgrade's sound has been played.
     *
     * @return true if the sound has been played, false otherwise
     */
    public boolean getPlaySound() {
        return playSound;
    }
}
