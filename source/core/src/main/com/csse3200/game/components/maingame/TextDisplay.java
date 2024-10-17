package com.csse3200.game.components.maingame;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.Gdx;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

import com.badlogic.gdx.utils.Align;
import com.csse3200.game.components.cutscenes.*;

/***
 * The UIComponent to create textbox which are drawn to the bottom of the screen
 */

public class TextDisplay extends UIComponent {
    //String building variables
    private List<String> text;
    private int currentPart = 0;
    public StringBuilder currentText;
    private int charIndex = 0;
    private long lastUpdate = 0L;
    private final long delay = 100L;

    // Displaying variables
    private boolean visible;
    public Label label;
    private final Table table;
    private final ScreenAdapter game;
    private String screen;
    private static final Logger logger = LoggerFactory.getLogger(TextDisplay.class);

    public TextDisplay() {
        super();
        this.game = null;
        this.screen = "";
        this.table = new Table();
        this.visible = false;
        this.currentText = new StringBuilder();
        this.text = new ArrayList<>(); //N! to fix crashing when pressing Enter
    }

    public TextDisplay(ScreenAdapter game) {
        this.game = game;
        this.screen = "";
        this.table = new Table();
        this.visible = false;
        this.currentText = new StringBuilder();
        this.text = new ArrayList<>();
    }

    public TextDisplay(ScreenAdapter game, String screen) {
        super();
        this.game = game;
        this.table = new Table();
        this.screen = screen;
        this.visible = true;
        this.currentText = new StringBuilder();
        this.text = new ArrayList<>(); //N! to fix crashing when pressing Enter


    }

    /***
     * Gets the delay of each character printing on the screen
     * @return a long
     */
    public long getDelay() {
        return delay;
    }


    /***
     * This function will
     *     - create a table where the UI components will be placed on
     *     - render the textbox and stack text on top using the stack
     *     - position the table in the correct place
     *     - add key input listeners and events
     */
//    public void create() {
//        super.create();
//        // Create the table for layout control and stack for layering
//        table.setFillParent(true);
//        table.center().bottom();
//        stage.addActor(table);
//        Stack stack = new Stack();
//
//        // Create and add the textbox image
//        Texture textboxTexture = ServiceLocator.getResourceService()
//                .getAsset("images/textbox.png", Texture.class);
//        Drawable textboxDrawable = new TextureRegionDrawable(textboxTexture);
//        Image textboxImage = new Image(textboxDrawable);
//        textboxImage.setScale(1.25f);
//        stack.add(textboxImage);
//
//        // Create and add the label on top of the image in the stack
//        BitmapFont defaultFont = new BitmapFont();
//        Label.LabelStyle labelStyle = new Label.LabelStyle(defaultFont, Color.BLACK);
//        label = new Label("", labelStyle);
//        label.setFontScale(3.0f);
//        label.setWrap(true);
//        label.setAlignment(Align.top | Align.left);
//
//        Table labelTable = new Table();
//        labelTable.add(label).padLeft(140).padBottom(10).size(
//                (int) (Gdx.graphics.getWidth() * 0.5), (int) (Gdx.graphics.getHeight() * 0.2));
//        stack.add(labelTable);
//
//        // Add the stack to the table with padding or alignment options
//        table.add(stack).padBottom(70).padLeft(0).size((int) (Gdx.graphics.getWidth() * 0.5), (int) (Gdx.graphics.getHeight() * 0.2));
//
//        // Now call setUpBackstoryLayout() if this is a backstory cutscene
//        if (Objects.equals(this.screen, "backstory")) {
//            setUpBackstoryLayout();  // Safe to call after label is initialized
//        }
//
//        setVisible(Objects.equals(this.screen, "cutscene")
//                || Objects.equals(this.screen, "moralDecision")
//                || Objects.equals(this.screen, "backstory"));
//
//        setupInputListener();
//        entity.getEvents().addListener("SetText", this::setText);
//    }
    public void create() {
        super.create();
        // Create the table for layout control and stack for layering
        table.setFillParent(true);
        table.center().bottom();
        stage.addActor(table);
        Stack stack = new Stack();

        // Create and add the textbox image
        Texture textboxTexture = ServiceLocator.getResourceService()
                .getAsset("images/textbox.png", Texture.class);
        Drawable textboxDrawable = new TextureRegionDrawable(textboxTexture);
        Image textboxImage = new Image(textboxDrawable);
        textboxImage.setScale(1.25f);
        stack.add(textboxImage);

        // Create and add the label on top of the image in the stack
        BitmapFont defaultFont = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(defaultFont, Color.BLACK);
        label = new Label("\n" + "Press Enter to continue", labelStyle);  // Initializing with this text
        label.setFontScale(3.0f);
        label.setWrap(true);
        label.setAlignment(Align.top | Align.left);

        Table labelTable = new Table();
        labelTable.add(label).padLeft(140).padBottom(10).size(
                (int) (Gdx.graphics.getWidth() * 0.5), (int) (Gdx.graphics.getHeight() * 0.2));
        stack.add(labelTable);

        // Add the stack to the table with padding or alignment options
        table.add(stack).padBottom(70).padLeft(0).size((int) (Gdx.graphics.getWidth() * 0.5), (int) (Gdx.graphics.getHeight() * 0.2));

        // Now call setUpBackstoryLayout() if this is a backstory cutscene
        if (Objects.equals(this.screen, "backstory")) {
            setUpBackstoryLayout();  // Safe to call after label is initialized
        }

        setVisible(Objects.equals(this.screen, "cutscene")
                || Objects.equals(this.screen, "moralDecision")
                || Objects.equals(this.screen, "backstory"));

        setupInputListener();  // Set up listener for handling key presses
        entity.getEvents().addListener("SetText", this::setText);
    }


    /***
     * Sets the text in allocated blocks based on text limit
     * @param text - a string which is the text to be displayed
     */
    public void setText(String text) {

        if (this.label == null) {
            BitmapFont defaultFont = new BitmapFont();
            Label.LabelStyle labelStyle = new Label.LabelStyle(defaultFont, Color.BLACK);
            this.label = new Label("", labelStyle);
        }

        currentText.setLength(0);
        label.setText("");
        charIndex = 0;
        currentPart = 0;

        setVisible(true);
        List<String> newText = new ArrayList<>();
        int textLength = text.length();
        StringBuilder temp = new StringBuilder();
        StringBuilder current = new StringBuilder();

        for (int i = 0; i < textLength; i++) {
            // if word formed in temp
            if (text.charAt(i) == ' ') {
                temp.append(current);
                current = new StringBuilder();
            }

            int textLimit = 60;
            if (i != 0 && i % textLimit == 0) {
                temp.append(" (enter to continue)");
                newText.add(temp.toString());
                temp = new StringBuilder();
            }
            current.append(text.charAt(i));
        }
        temp.append(current).append(" (enter to continue)");
        newText.add(temp.toString());
        this.text = newText;
    }


    /***
     * Gets the text in the blocks allocated by the algorithm
     * @return a List of strings which is the text
     */
    public List<String> getText() {
        return text;
    }

    /***
     * Set visibility of the textbox
     * @param value - True or False if the textbox is visible
     */
    public void setVisible(boolean value) {
        this.visible = value;
        table.setVisible(value);
    }

    /***
     * Gets the visibility of the textbox on the screen
     * @return boolean which is the visibility of the status
     */
    public boolean getVisible() {
        return this.visible;
    }

    /***
     * This function will add text after a certain amount of time given the delay constant
     */
    @Override
    public void update() {
        long time = ServiceLocator.getTimeSource().getTime();

        if (this.text != null && currentPart < text.size()) {
            if (charIndex < text.get(currentPart).length()) {
                if (time - lastUpdate >= delay) {
                    lastUpdate = time;
                    this.currentText.append(text.get(currentPart).charAt(charIndex));
                    label.setText(currentText.toString());
                    charIndex++;
                }
            }
            if (charIndex >= text.get(currentPart).length()) {
                entity.getEvents().trigger("TextComplete", currentPart);
            }
        } else {
            //Nothing
            logger.info("Do nothing");
        }
    }

    /***
     * Sets up the key input listener for the ENTER keyword to either move to the next section
     * of the text or clear the textbox from the screen
     */
    private void setupInputListener() {
        logger.info(TextDisplay.this.screen);
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {

                if (TextDisplay.this.screen.equals("cutscene")) {
                    if (keycode == com.badlogic.gdx.Input.Keys.ENTER || keycode == com.badlogic.gdx.Input.Keys.SPACE) {
                        logger.info("we've pressed enter");
                        Cutscene currentCutscene = ServiceLocator.getCurrentCutscene();
                        currentCutscene.setTextForScene(currentCutscene.currentScene);
                        label.setText(currentCutscene.currentText);
                    }
                    return true;
                } else if (TextDisplay.this.screen.equals("moralDecision")) {
                    Cutscene currentCutscene = ServiceLocator.getCurrentCutscene();
                    Boolean atEnd = currentCutscene.isAtEnd();
                    if (keycode == com.badlogic.gdx.Input.Keys.ENTER || keycode == com.badlogic.gdx.Input.Keys.SPACE) {
                        logger.info("at moral in textDisplay");
                        if (!atEnd) {
                            logger.info("parsing through");
                            currentCutscene.setTextForSceneMoral(currentCutscene.currentScene);
                            label.setText(currentCutscene.currentText);
                        }
                    } else if (keycode == Input.Keys.Y && atEnd) {
                        logger.info("WE'RE ALMOST THERE");
                        currentCutscene = ServiceLocator.getCurrentCutscene();
                        currentCutscene.setTextForScene(currentCutscene.currentScene);

                        ServiceLocator.getDayNightService().getEvents().trigger("YesAtMoralDecision");
                    } else if (keycode == Input.Keys.N && atEnd) {
                        logger.info("WE'RE ALMOST THERE NO");
                        currentCutscene = ServiceLocator.getCurrentCutscene();
                        currentCutscene.setTextForScene(currentCutscene.currentScene);

                        ServiceLocator.getDayNightService().getEvents().trigger("NoAtMoralDecision");
                    }
                    return true;
                } else if (TextDisplay.this.screen.equals("backstory")) {
                    // Handling for backstory cutscenes
                    Cutscene currentCutscene = ServiceLocator.getCurrentCutscene();
                    Boolean atEnd = currentCutscene.isAtEnd();

                    // Check if the current text is "Press Enter to continue"
                    if (label.getText().toString().equals("Press Enter to continue")) {
                        if (keycode == com.badlogic.gdx.Input.Keys.ENTER || keycode == com.badlogic.gdx.Input.Keys.SPACE) {
                            logger.info("Displaying backstory cutscene text");

                            // Set the actual text for the backstory scene
                            currentCutscene.setTextForScene(currentCutscene.currentScene);
                            label.setText(currentCutscene.currentText);
                        }
                    } else {
                        // Continue through the backstory cutscene normally
                        if (keycode == com.badlogic.gdx.Input.Keys.ENTER || keycode == com.badlogic.gdx.Input.Keys.SPACE) {
                            logger.info("at backstory in textDisplay");
                            if (!atEnd) {
                                logger.info("parsing through backstory");
                                currentCutscene.setTextForScene(currentCutscene.currentScene);
                                label.setText(currentCutscene.currentText);
                            }
                        }
                    }
                    return true;
                } else if (keycode == com.badlogic.gdx.Input.Keys.ENTER) {
                    if (currentPart < text.size()) {
                        if (charIndex < TextDisplay.this.text.get(currentPart).length()) {
                            label.setText(text.get(currentPart));
                            charIndex = TextDisplay.this.text.get(currentPart).length();
                        } else {
                            currentPart++;
                            charIndex = 0;
                            lastUpdate = 0;
                            TextDisplay.this.currentText = new StringBuilder();
                            // if no more text remaining
                            if (currentPart == TextDisplay.this.text.size()) {
                                setVisible(false);
                            }
                        }
                    }
                    return true;
                }
                return false;
            }
        });
    }


    /**
     * Overrides the draw method from the Actor class.
     * This method is responsible for rendering the component.
     * However, the actual drawing is handled by the stage, so no rendering logic is needed here.
     *
     * @param batch The SpriteBatch used for drawing.
     */
    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }

    /**
     * Sets the stage for this UI component.
     * The stage is used to manage the layout and actors (UI elements) for this component.
     *
     * @param stage The Stage to be set for this component.
     */
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Returns the table layout for the UI component.
     * This method is useful for accessing and modifying the table layout externally.
     *
     * @return The Table used for the UI layout.
     */
    public Table getTable() {
        return table;
    }

    /**
     * Sets the current screen for this component.
     * This method allows you to assign a specific screen that the UI component belongs to.
     *
     * @param screen The name of the screen to be set.
     */
    public void setScreen(String screen) {
        this.screen = screen;
    }

    /**
     * Sets up the layout for the backstory cutscene.
     * This method modifies the font size, positioning, and overall layout to suit
     * the backstory cutscene UI. It uses a stack layout with a textbox image and a label.
     * The font size is reduced and positioned higher on the screen to create a cleaner UI.
     */
    private void setUpBackstoryLayout() {
        // Modify font size for backstory cutscene
        label.setFontScale(2.7f);  // Make the font smaller


        // Change the position to be higher on the screen
        table.center().top().padTop(50);  // Adjust the padding to move it higher
        table.clear();  // Clear previous layout

        Stack stack = new Stack();

        // Use the same textbox image
        Texture textboxTexture = ServiceLocator.getResourceService()
                .getAsset("images/textbox.png", Texture.class);
        Drawable textboxDrawable = new TextureRegionDrawable(textboxTexture);
        Image textboxImage = new Image(textboxDrawable);
        textboxImage.setScale(1.0f);  // Keep the scale of the box smaller for a cleaner UI
        stack.add(textboxImage);

        // Modify the label size and placement
        Table labelTable = new Table();
        labelTable.add(label).padLeft(100).padTop(10).size(
                (int) (Gdx.graphics.getWidth() * 0.6), (int) (Gdx.graphics.getHeight() * 0.15));  // Adjust the label size
        stack.add(labelTable);

        // Add stack to the table
        table.add(stack).padTop(100).size((int) (Gdx.graphics.getWidth() * 0.6), (int) (Gdx.graphics.getHeight() * 0.15));
    }
}

