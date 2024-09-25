package com.csse3200.game.components.upgrades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ProgressBar;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RageUpgrade extends UIComponent implements Upgrade {
    private static final Logger logger = LoggerFactory.getLogger(RageUpgrade.class);
    private final GameTime timesource;

    private boolean isOverlayVisible;
    private Table layout;

    private ProgressBar rageMeter;
    private float rageTimeRemaining;
    private final float rageTime = 30f;
    private boolean isRageActive = false;

    private float rageFillTimeRemaining;
    private final float rageFillTime = 90f;
    private boolean isRageFilling = false;

    private Sound rageSound;
    private Long rageSoundId;
    private Sound powerDownSound;
    private Long powerDownId;

    public RageUpgrade() {
        super();
        timesource = ServiceLocator.getTimeSource();
        isOverlayVisible = false;
    }

    @Override
    public void create() {
        super.create();
        layout = new Table();
        layout.setFillParent(true);
        layout.setVisible(isOverlayVisible);
        stage.addActor(layout);
        layout.setZIndex(0);

        setupRedOverlay();
        setupRageMeter();
        setupInputListener();

        // https://freesound.org/people/Timbre/sounds/86241/
        rageSound = Gdx.audio.newSound(Gdx.files.internal("sounds/rage_sound.wav"));

        // https://freesound.org/people/noirenex/sounds/159399/
        powerDownSound = Gdx.audio.newSound(Gdx.files.internal("sounds/power_down.wav"));
    }

    private void setupRedOverlay() {
        // https://www.freepik.com/free-vector/grunge-red-distressed-textured-background_4043545.htm#query=
        // red%20overlay&position=32&from_view=keyword&track=ais_hybrid&uuid=5e1656db-c1a1-483b-b846-d3d666207271
        Texture texture = ServiceLocator.getResourceService().getAsset("images/red_overlay.jpg", Texture.class);

        Image image = new Image(texture);
        image.setFillParent(true);
        image.setColor(new Color(1, 0, 0, 0.3f));
        layout.add(image);
    }

    private void setupRageMeter() {
        // Made textures in Paint
        Texture whiteTexture = ServiceLocator.getResourceService().getAsset("images/white_background.png", Texture.class);
        Texture fillTexture = ServiceLocator.getResourceService().getAsset("images/red_fill.png", Texture.class);

        ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();

        // Setting white background
        style.background = new TextureRegionDrawable(new TextureRegion(whiteTexture));
        style.background.setMinHeight(15); // Adjust height as needed

        // Setting red fill color
        style.knobBefore = new TextureRegionDrawable(new TextureRegion(fillTexture));
        style.knobBefore.setMinHeight(15);

        rageMeter = new ProgressBar(0f, 1f, 0.01f, false, style);
        rageMeter.setValue(1f); // Initially, the rage meter is full
        rageMeter.setPosition(640, 35);
        stage.addActor(rageMeter);
    }

    private void setupInputListener() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == com.badlogic.gdx.Input.Keys.R) {
                    if (isRageActive) {
                        deactivate();
                    } else if (rageMeter.getValue() == 1f){
                        activate();
                    }
                    return true;
                }
                return false;
            }
        });
    }

    public void activate() {
        // entity.getEvents().trigger("rageModeOn");
        rageSoundId = rageSound.play();
        rageSound.setVolume(rageSoundId, 0.25f);

        isRageActive = true;
        isOverlayVisible = true;
        layout.setVisible(true);
        rageTimeRemaining = rageTime;
    }

    public void deactivate() {
        // entity.getEvents().trigger("rageModeOff");
        powerDownId = powerDownSound.play();
        powerDownSound.setVolume(powerDownId, 0.25f);

        isRageActive = false;
        isOverlayVisible = false;
        layout.setVisible(false);
//        rageMeter.setValue(1f);

        isRageFilling = true;
        logger.info("rage meter value: " + rageMeter.getValue());
        rageFillTimeRemaining = (1 - rageMeter.getValue()) * rageFillTime;
        logger.info("rage fill time remaining : " + rageFillTimeRemaining);
    }

    @Override
    public void update() {
        if (isRageActive) {
            rageTimeRemaining -= timesource.getDeltaTime();
            rageMeter.setValue(rageTimeRemaining / rageTime);
            if (rageTimeRemaining <= 0) {
                deactivate();
            }

        } else if (isRageFilling) {
            rageFillTimeRemaining -= timesource.getDeltaTime();
            rageMeter.setValue((rageFillTime - rageFillTimeRemaining) / rageFillTime);
            if (rageFillTimeRemaining <= 0) {
                isRageFilling = false;
            }
        }
    }

    @Override
    protected void draw(SpriteBatch batch) {
    }

    @Override
    public void setStage(Stage mock) {
    }
}