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
import com.csse3200.game.components.maingame.TextDisplay;
import com.csse3200.game.screens.CutsceneScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;

import java.util.ArrayList;
import java.util.List;

/**
 * CutsceneTextDisplay displays scrolling text during a cutscene.
 * It shows text one character at a time and allows skipping to the full text by pressing ENTER.
 */
public class CutsceneTextDisplay extends UIComponent {
    private String text;
    private StringBuilder currentText;
    private int charIndex = 0;
    private long lastUpdate = 0L;
    private long delay = 100L;

    // Displaying variables
    private boolean visible;
    private Stack layout;
    private Label label;
    private Table table;
    private Image displayBox;

    private final CutsceneScreen cutscene;

    /**
     * Default constructor initializes without a specific cutscene.
     */
    public CutsceneTextDisplay() {
        super();
        this.cutscene = null;
        this.table = new Table();
        this.visible = true;
        this.currentText = new StringBuilder();
    }

    /**
     * Constructor that links this text display to a cutscene.
     *
     * @param cutscene the associated CutsceneScreen
     */
    public CutsceneTextDisplay(CutsceneScreen cutscene) {
        this.cutscene = cutscene;
        this.table = new Table();
        this.visible = true;
        this.currentText = new StringBuilder();
    }

    /**
     * Sets up the text display with a background and label, and adds it to the stage.
     */
    @Override
    public void create() {
        super.create();
        // Initially hide the text display
        setVisible(false);
        // Make table fill the screen and align it to the bottom center of the screen
        table.setFillParent(true);
        table.center().bottom();
        stage.addActor(table);

        Stack stack = new Stack();

        // Load and add background image
        Texture textboxTexture = ServiceLocator.getResourceService().getAsset("images/textbox.png", Texture.class);
        Drawable textboxDrawable = new TextureRegionDrawable(textboxTexture);
        Image textboxImage = new Image(textboxDrawable);
        textboxImage.setScale(1.25f);  // Scale up the background
        stack.add(textboxImage);

        // Set up the label for text display
        BitmapFont defaultFont = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(defaultFont, Color.BLACK);
        label = new Label("", labelStyle);
        label.setFontScale(3.0f);
        // Allow the text to be wrapped and align to the top left
        label.setWrap(true);
        label.setAlignment(Align.top | Align.left);

        // Ensure the label has enough space for text to wrap
        Table labelTable = new Table();
        labelTable.add(label).padLeft(140).padBottom(10).size(
                (int) (Gdx.graphics.getWidth() * 0.5), (int) (Gdx.graphics.getHeight() * 0.2));
        stack.add(labelTable);

        table.add(stack).padBottom(70).size(
                (int) (Gdx.graphics.getWidth() * 0.5), (int) (Gdx.graphics.getHeight() * 0.2));

        setupInputListener();  // Add input handling
        entity.getEvents().addListener("SetText", this::setText);  // Listen for text setting event
    }

    /**
     * Sets the text to be displayed.
     *
     * @param text the new text to display
     */
    public void setText(String text) {
        setVisible(true);
        this.text = text;
        this.currentText.setLength(0);  // Reset current text
        this.charIndex = 0;  // Start typing from the beginning
    }

    public void setVisible(boolean value) {
        this.visible = value;
        table.setVisible(value);
    }

    public boolean getVisible() {
        return this.visible;
    }

    @Override
    public void update() {
        long time = ServiceLocator.getTimeSource().getTime();
        if (this.text != null && charIndex < this.text.length()) {
            if (time - lastUpdate >= delay) {
                lastUpdate = time;
                this.currentText.append(text.charAt(charIndex));
                label.setText(currentText.toString());
                charIndex++;
            }
        }
    }

    private void setupInputListener() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == com.badlogic.gdx.Input.Keys.ENTER) {
                    // If ENTER is pressed, skip to the full text
                    label.setText(text);
                    charIndex = text.length();  // Set charIndex to the end
                    return true;
                }
                return false;
            }
        });
    }

    /**
     * Overridden to handle custom drawing logic.
     * In this case, drawing is handled by the stage, so nothing is needed here.
     */
    @Override
    public void draw(SpriteBatch batch) {
        // No custom drawing required, handled by the stage.
    }

    /**
     * Assigns a stage to this UI component.
     *
     * @param stage the stage to be used
     */
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Provides access to the main table containing the text display.
     *
     * @return the table containing the display components
     */
    public Table getTable() {
        return table;
    }
}
