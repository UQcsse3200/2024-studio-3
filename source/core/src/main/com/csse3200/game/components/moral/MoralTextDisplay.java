package com.csse3200.game.components.moral;

import com.badlogic.gdx.Gdx;
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
import com.badlogic.gdx.utils.Align;


public class MoralTextDisplay extends UIComponent {

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
    private final MoralDayOne game;

    /**
     * Constructor for the MoralTextDisplay class.
     */
    public MoralTextDisplay() {
        super();
        this.game = null;
        this.table = new Table();
        this.visible = true;
        this.currentText = new StringBuilder();
    }

    /**
     * Constructor for the MoralTextDisplay class.
     * @param game the MoralDayOne object
     */
    public MoralTextDisplay(MoralDayOne game) {
        this.game = game;
        this.table = new Table();
        this.visible = true;
        this.currentText = new StringBuilder();
    }

    /**
     * Creates the moral text display.
     */
    @Override
    public void create() {
        super.create();

        setVisible(false);
        table.setFillParent(true);
        table.center().bottom();
        stage.addActor(table);
        Stack stack = new Stack();

        // Load texture for the background box
        Texture textboxTexture = ServiceLocator.getResourceService()
                .getAsset("images/textbox.png", Texture.class);
        Drawable textboxDrawable = new TextureRegionDrawable(textboxTexture);
        Image textboxImage = new Image(textboxDrawable);
        textboxImage.setScale(1.25f);
        stack.add(textboxImage);

        // Configure font and label
        BitmapFont defaultFont = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(defaultFont, Color.BLACK);
        label = new Label("", labelStyle);
        label.setFontScale(3.0f);
        label.setWrap(true);  // Enable wrapping for long text
        label.setAlignment(Align.top | Align.left);

        // Ensure label takes enough space for text to wrap properly
        Table labelTable = new Table();
        labelTable.add(label).padLeft(140).padBottom(10).size(
                (int) (Gdx.graphics.getWidth() * 0.5), (int) (Gdx.graphics.getHeight() * 0.2));
        stack.add(labelTable);

        // Add everything to the main table
        table.add(stack).padBottom(70).padLeft(0).size(
                (int) (Gdx.graphics.getWidth() * 0.5), (int) (Gdx.graphics.getHeight() * 0.2));

        setupInputListener();
        entity.getEvents().addListener("SetText", this::setText);
    }

    /**
     *  Sets the text to be displayed.
     * @param text the text to display
     */
    public void setText(String text) {
        setVisible(true);
        this.text = text;
        this.currentText.setLength(0);  // Reset current text
        this.charIndex = 0;  // Start typing from the beginning
    }

    /**
     * Sets the visibility of the text display.
     * @param value the visibility value
     */
    public void setVisible(boolean value) {
        this.visible = value;
        table.setVisible(value);
    }

    /**
     * Returns the visibility of the text display.
     * @return the visibility of the text display
     */
    public boolean getVisible() {
        return this.visible;
    }

    /**
     * Renders the text display to show updates.
     */
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

    /**
     * Sets up the input listener for the text display.
     * Will be changed later
     */
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

    @Override
    public void draw(SpriteBatch batch) {
        // Drawing is handled by the stage
    }

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    /**
     * Returns the table of the text display.
     * @return the table of the text display
     */
    public Table getTable() {
        return table;
    }
}

