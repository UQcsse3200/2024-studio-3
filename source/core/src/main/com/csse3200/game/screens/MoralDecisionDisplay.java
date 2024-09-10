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
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MoralDecisionDisplay extends UIComponent {

    private static final Logger logger = LoggerFactory.getLogger(MoralDecisionDisplay.class);

    private Table layout; // Layout manager
    private boolean isVisible;
    private final MainGameScreen game;
    private Image characterImage;

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

        // create a table layout
        layout = new Table();
        layout.setFillParent(true);
        layout.setVisible(isVisible);
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
        BitmapFont font = new BitmapFont();
        Label titleLabel = new Label("Moral Decision", new Label.LabelStyle(font, Color.WHITE));
        layout.add(titleLabel).pad(10).row();

        // load and position the racoon image slightly to the left
        Texture imgTexture = new Texture(Gdx.files.internal("images/racoon.png"));
        Drawable imgDrawable = new TextureRegionDrawable(imgTexture);
        characterImage = new Image(imgDrawable);

        // add racoon image to the table and shift it left by adjusting padding
        layout.add(characterImage).padRight(1000).center().row(); // Add padding to move left

//        setupInputListener();
        entity.getEvents().addListener("triggerMoralScreen", this::toggleVisibility);
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


    private void initialiseUI() {
        Label titleLabel = new Label("moral deiciosn", new Label.LabelStyle(new BitmapFont(), Color.PINK));
        layout.add(titleLabel).pad(10).row();

    }


    public void show() {
        isVisible = true;
        layout.setVisible(isVisible);
        game.pause(); // Pause the game when the display is shown
    }

    public void hide() {
        isVisible = false;
        layout.setVisible(isVisible);
        game.resume(); // Resume the game when the display is hidden
    }

    public void toggleVisibility(int day) {
        logger.debug(" Day - {}", day);
        if (isVisible) {
            hide();
        } else {
            show();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    @Override
    public void setStage(Stage stage) {
    }
}
