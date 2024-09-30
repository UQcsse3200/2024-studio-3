package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.csse3200.game.screens.CutsceneScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;

/**
 * CutsceneTextDisplay handles displaying scrolling text during a cutscene. The text is displayed
 * one character at a time, and pressing ENTER will skip the scrolling and display the full text immediately.
 */
public class CutsceneTextDisplay extends UIComponent {
    private String text;  // Full text to be displayed
    private StringBuilder currentText;  // Text that is currently displayed
    private int charIndex = 0;  // Index of the current character to be displayed
    private long lastUpdate = 0L;  // Last time a character was added
    private long delay = 100L;  // Delay between displaying each character

    // UI components for displaying the text
    private boolean visible;
    private Stack layout;
    private Label label;
    private Table table;
    private Image displayBox;

    private final CutsceneScreen cutscene;

    /**
     * Default constructor that initializes without a specific cutscene.
     */
    public CutsceneTextDisplay() {
        super();
        this.cutscene = null;
        this.table = new Table();
        this.visible = true;
        this.currentText = new StringBuilder();
    }

    /**
     * Constructor that links the text display to a specific cutscene.
     * @param cutscene the associated CutsceneScreen
     */
    public CutsceneTextDisplay(CutsceneScreen cutscene) {
        this.cutscene = cutscene;
        this.table = new Table();
        this.visible = true;
        this.currentText = new StringBuilder();
    }

    /**
     * Sets up the text display UI, including the background image and text label, and adds it to the stage.
     */
    @Override
    public void create() {
        super.create();
        // Initially hide the text display
        setVisible(false);
        // Set up the table to fill the screen and align it to the bottom center
        table.setFillParent(true);
        table.center().bottom();
        stage.addActor(table);

        Stack stack = new Stack();

        // Load and add background image
        Texture textboxTexture = ServiceLocator.getResourceService().getAsset("images/textbox.png", Texture.class);
        Drawable textboxDrawable = new TextureRegionDrawable(textboxTexture);
        Image textboxImage = new Image(textboxDrawable);
        textboxImage.setScale(1.25f);  // Scale the background image
        stack.add(textboxImage);

        // Set up the label for text display
        BitmapFont defaultFont = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(defaultFont, Color.BLACK);
        label = new Label("", labelStyle);
        label.setFontScale(3.0f);
        label.setWrap(true);  // Enable text wrapping
        label.setAlignment(Align.top | Align.left);

        // Add the label to a table for proper alignment and padding
        Table labelTable = new Table();
        labelTable.add(label).padLeft(140).padBottom(10).size(
                (int) (Gdx.graphics.getWidth() * 0.5), (int) (Gdx.graphics.getHeight() * 0.2));
        stack.add(labelTable);

        table.add(stack).padBottom(70).size(
                (int) (Gdx.graphics.getWidth() * 0.5), (int) (Gdx.graphics.getHeight() * 0.2));

        // Setup input listener to handle user input
        setupInputListener();
        entity.getEvents().addListener("SetText", this::setText);  // Event listener to update text display
    }

    /**
     * Sets the text to be displayed during the cutscene.
     * @param text The text to display.
     */
    public void setText(String text) {
        setVisible(true);  // Make the text display visible
        this.text = text;
        this.currentText.setLength(0);  // Clear the currently displayed text
        this.charIndex = 0;  // Start from the beginning of the text
    }

    /**
     * Controls the visibility of the text display.
     * @param value Whether the text display should be visible.
     */
    public void setVisible(boolean value) {
        this.visible = value;
        table.setVisible(value);  // Set the visibility of the table
    }

    /**
     * Returns whether the text display is currently visible.
     * @return True if visible, false otherwise.
     */
    public boolean getVisible() {
        return this.visible;
    }

    /**
     * Updates the text display, adding characters one at a time based on the specified delay.
     */
    @Override
    public void update() {
        long time = ServiceLocator.getTimeSource().getTime();
        if (this.text != null && charIndex < this.text.length()) {
            // Check if enough time has passed to add the next character
            if (time - lastUpdate >= delay) {
                lastUpdate = time;
                this.currentText.append(text.charAt(charIndex));  // Add the next character
                label.setText(currentText.toString());  // Update the label text
                charIndex++;  // Increment character index
            }
        }
    }

    /**
     * Sets up the input listener to allow skipping text scrolling by pressing ENTER.
     */
    private void setupInputListener() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                // If ENTER is pressed, skip to displaying the full text
                if (keycode == com.badlogic.gdx.Input.Keys.ENTER) {
                    label.setText(text);
                    charIndex = text.length();  // Set character index to the end
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Custom draw method to handle additional rendering.
     * Drawing is handled by the stage, so no custom drawing is needed here.
     */
    @Override
    public void draw(SpriteBatch batch) {
        // No custom drawing required, handled by the stage.
    }

    /**
     * Sets the stage for this UI component.
     * @param stage The stage to assign.
     */
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Returns the main table containing the text display UI components.
     * @return The table containing the display components.
     */
    public Table getTable() {
        return table;
    }
}
