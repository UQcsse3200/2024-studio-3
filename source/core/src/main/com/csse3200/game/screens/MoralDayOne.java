package com.csse3200.game.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.GdxGame;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MoralDayOne extends UIComponent {

    private static final Logger logger = LoggerFactory.getLogger(MoralDayOne.class);

    private Table layout; // Layout manager
    private boolean isVisible;
    private final MainGameScreen game;
    private Image backgroundImage;
    private String question = "Set Question";


    /**
     * Constructor for the MoralDecisionDisplay class.
     * Initializes the display and sets its visibility to false.
     * Retrieves the main game screen from the ServiceLocator.
     */
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



        // load and position the racoon image slightly to the left
        Texture imgTexture = new Texture(Gdx.files.internal("images/moral_scenes/drugbackground.png"));
        Drawable imgDrawable = new TextureRegionDrawable(imgTexture);
        backgroundImage = new Image(imgDrawable);

        layout.add(backgroundImage).fill().expand();


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


        entity.getEvents().addListener("triggerMoralScreen", this::toggleVisibility);

        //from team 2, added the listener for when game day ends to toggle visibility
        ServiceLocator.getDayNightService().getEvents().addListener("TOMORAL", () -> {
            logger.info("TOMORAL event received in MoralDecisionDisplay");
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

    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    @Override
    public void setStage(Stage stage) {
    }
}