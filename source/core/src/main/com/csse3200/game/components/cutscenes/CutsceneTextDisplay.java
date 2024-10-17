package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Align;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * CutsceneTextDisplay handles displaying scrolling text during a cutscene. The text is displayed
 * one character at a time, and pressing ENTER will skip the scrolling and display the full text immediately.
 */
public class CutsceneTextDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(CutsceneTextDisplay.class);

    // UI components for displaying the text
    private Label label;
    private final Table table;
    private boolean visible;

    /**
     * Default constructor that initializes without a specific cutscene.
     */
    public CutsceneTextDisplay(boolean visible) {
        super();
        this.table = new Table();
        this.visible = visible;
    }

    public CutsceneTextDisplay(Skin skin) {
        super(skin);
        this.table = new Table();
        this.table.setVisible(this.visible);
    }

    /**
     * Sets up the text display UI, including the background image and text label, and adds it to the stage.
     */
    @Override
    public void create() {
        super.create();
        if (this.visible) {
            setupUI();
            // Setup input listener to handle user input
            setupInputListener();
        }
    }

    private void setupUI() {

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
        label = new Label("Press Enter to continue", labelStyle);
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

        table.setVisible(visible);
    }


    /**
     * Sets up the input listener to allow skipping text scrolling by pressing ENTER.
     */
    private void setupInputListener() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                // If ENTER is pressed, skip to displaying the full text
                if ((keycode == com.badlogic.gdx.Input.Keys.ENTER) && (visible)) {
                    logger.info("Space bar pressed. Moving to next piece of text");
                    Cutscene currentCutscene = ServiceLocator.getCurrentCutscene();
                    currentCutscene.setTextForScene(currentCutscene.currentScene);
                    label.setText(currentCutscene.currentText);
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

    // Method to set text directly
    public void setText(String text) {
        label.setText(text);
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
