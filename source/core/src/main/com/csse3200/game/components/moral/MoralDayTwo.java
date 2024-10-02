package com.csse3200.game.components.moral;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoralDayTwo extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MoralDayTwo.class);

    private Table layout; // Layout manager
    private int moralStep = 0;
    private boolean isVisible;
    private final MainGameScreen game;
    private Image backgroundImage;
    private String question = "Set Question";

//    private MoralTextDisplay textDisplay;
//
//    private Table table;


    /**
     * Constructor for the MoralDecisionDisplay class.
     * Initializes the display and sets its visibility to false.
     * Retrieves the main game screen from the ServiceLocator.
     */
    public MoralDayTwo() {
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

//        // Initialise the textDisplay before using it
//        textDisplay = new MoralTextDisplay();
//        textDisplay.setVisible(false);  // Initially hidden
//        stage.addActor(textDisplay.getTable());  // Add it to the stage
//
//        layout.add(textDisplay.getTable()).pad(10).row();
//        //stage.addActor(table);
//
//        moralChoice();  // Ensure textDisplay is initialized before calling this method



        // load and position the racoon image slightly to the left
        Texture imgTexture = new Texture(Gdx.files.internal("images/moral_scenes/gamblingnight.png"));
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
        decisionTable.add(noButton).pad(10).row();


        entity.getEvents().addListener("triggerMoralScreen", this::toggleVisibility);

        //from team 2, added the listener for when game day ends to toggle visibility
        ServiceLocator.getDayNightService().getEvents().addListener("TOMORAL", () -> {
            logger.info("TOMORAL event received in MoralDecisionDisplay");
            show();
        });
    }

//    /**
//     * Proceeds to the next tutorial step using a switch-case.
//     */
//    public void moralChoice() {
//        moralStep++;
//        switch (moralStep) {
//            case 1:
//                showMovementTutorial();
//                break;
//            case 2:
//                showItemPickupTutorial();
//                break;
//            default:
//                logger.error("Unexpected tutorial step: " + moralStep);
//        }
//    }
//
//    /**
//     * Creates tutorial text box. Calls set text.
//     * @param text being displayed into textbox.
//     */
//    private void createTextBox(String text) {
//        Array<Entity> entities = ServiceLocator.getEntityService().getEntities();
//
//        for (int i = 0; i < entities.size; i++) {
//            Entity entity = entities.get(i);
//            entity.getEvents().trigger("SetText", text);
//        }
//    }
//
//
//
//    /**
//     * Displays the movement tutorial. The player needs to use W/A/S/D to move.
//     */
//    private void showMovementTutorial() {
//        textDisplay.setVisible(true);
//        createTextBox("Not bad, human. You made enough to pay rent. Maybe there’s some hope for you.");
//    }
//
//    /**
//     * Displays the item pickup tutorial. The player needs to press E to pick up an item.
//     */
//    private void showItemPickupTutorial() {
//        textDisplay.setVisible(true);
//        createTextBox("Here’s an offer—you wash some of my dirty money through your restaurant, and tomorrow you " +
//                "won’t need to stress about the budget. What do you say?");
//    }



    /**
     * Initialise the User Interface
     */
    private void initialiseUI() {
//        Label titleLabel = new Label("moral deiciosn", new Label.LabelStyle(new BitmapFont(), Color.PINK));
//        layout.add(titleLabel).pad(10).row();
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
//        switch (moralStep) {
//            case 1:
//                if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
//                    moralChoice();
//                }
//                break;
//        }
    }

//    @Override
//    public void dispose() {
//        super.dispose();
//
//        if (textDisplay != null) {
//            textDisplay.setVisible(false);
//            textDisplay.getTable().clear();
//        }
//    }



    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    @Override
    public void setStage(Stage stage) {
    }
}

