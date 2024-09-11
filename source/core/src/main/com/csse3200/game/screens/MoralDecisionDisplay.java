package com.csse3200.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.GdxGame;
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
    private String question = "Set Question";

    public MoralDecisionDisplay(MainGameScreen game) {
        super();
        this.game = game;
        if (this.game == null) {
            logger.error("MainGameScreen is null!");
        }
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
        Label titleLabel = new Label("Moral Decision",skin);
        layout.add(titleLabel).pad(10).row();

        // load and position the racoon image slightly to the left
        Texture imgTexture = new Texture(Gdx.files.internal("images/racoon.png"));
        Drawable imgDrawable = new TextureRegionDrawable(imgTexture);
        characterImage = new Image(imgDrawable);

        // add racoon image to the table and shift it left by adjusting padding
        layout.add(characterImage).left();

        // Add a secondary table to the left side of the view.
        Table questionSet = new Table();
        Label questionLabel = new Label("Do you want to save the racoon?", skin);
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
        setupInputListener();
        entity.getEvents().addListener("triggerMoralScreen", this::toggleVisibility);

        //from team 2, added the listener for when game day ends to toggle visibility
        ServiceLocator.getDayNightService().getEvents().addListener("TOMORAL", () -> {
            logger.info("TOMORAL event received in MoralDecisionDisplay");
            show();
        });
    }


    private void setupInputListener() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                logger.debug("Key Pressed: {}, isVisible: {}", keycode, isVisible);
                if (keycode == com.badlogic.gdx.Input.Keys.M) {
                    // from team 2, currently using hide() is also opening the Moral decision display
                    // when "M" is pressed. Based on the transition logic, we don't want this to happen.
                    if (isVisible) {
                        hide();
                        logger.debug("Moral Decision Display is now hidden.");
                    }
//                    toggleVisibility(1);
                    return true;
                }
                return false;
            }
        });
    }


    private void initialiseUI() {
        Label titleLabel = new Label("moral deiciosn", new Label.LabelStyle(new BitmapFont(), Color.PINK));
        layout.add(titleLabel).pad(10).row();

    }

    public boolean setQuestion(String question) {
        this.question = question;
        return true;
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
        ServiceLocator.getDayNightService().getEvents().trigger("decisionDone");

    }

    public void toggleVisibility(int day) {
        logger.debug(" Day - {}", day);
//        this.update();
        if (isVisible) {
            hide();
//            ServiceLocator.getDayNightService().getEvents().trigger("decisionDone");
        } else {
            show();
        }
    }

    @Override
    public void update() {
        super.update();
        layout.clear();
        this.create();
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    @Override
    public void setStage(Stage stage) {
    }

    public boolean isVisible(){
        return isVisible;
    }
}
