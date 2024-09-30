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
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoralDayOne extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MoralDayOne.class);

    private Table layout; // Layout manager
    private boolean isVisible;
    private final MainGameScreen game;
    private Image characterImage;
    private String question = "Set Question";

    public MoralDayOne() {
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
        Label titleLabel = new Label("Moral Decision",skin);
        layout.add(titleLabel).pad(10).row();

        // load and position the racoon image slightly to the left
        Texture imgTexture = new Texture(Gdx.files.internal("images/moraldecisions/testng.PNG"));
        Drawable imgDrawable = new TextureRegionDrawable(imgTexture);
        characterImage = new Image(imgDrawable);

//        // add racoon image to the table and shift it left by adjusting padding
//        layout.add(characterImage).left();
//
//        // Add a secondary table to the left side of the view.
//        Table questionSet = new Table();
//        Label questionLabel = new Label("Do you want to save the racoon?", skin);
//        questionSet.add(questionLabel).pad(10).row();

//        Label testingLabel = new Label("TestinvfHQ ETAAHWRVFBJWGEE HAET NVBbwegvkjER AHT QTHJTRAJ RTJRTg", skin);
////        layout.add(testingLabel).padRight(100).right().row();

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

//        // add the secondary table to the main table
//        layout.add(questionSet).padRight(100).right().row();
////        setupInputListener();



        entity.getEvents().addListener("triggerMoralScreen", this::toggleVisibility);

        //from team 2, added the listener for when game day ends to toggle visibility
        ServiceLocator.getDayNightService().getEvents().addListener("TOMORAL", () -> {
            logger.info("TOMORAL event received in MoralDayOne Display");
            show();
        });
    }

    /**
     * Initialise the User Interface
     */
    private void initialiseUI() {
        Label titleLabel = new Label("moral deiciosn", new Label.LabelStyle(new BitmapFont(), Color.PINK));
        layout.add(titleLabel).pad(10).row();
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

    }

    /**
     * Hides the moral decision screen.
     */
    private void hide() {
        isVisible = false;
        layout.setVisible(isVisible);
        game.resume(); // Resume the game when the display is hidden
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

    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    @Override
    public void setStage(Stage stage) {

    }
}



