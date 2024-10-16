package com.csse3200.game.components.maingame;

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
    private int textLength = 0;
    private StringBuilder currentText;
    private int textLimit = 60;
    private int charIndex = 0;
    private long lastUpdate = 0L;
    private long delay = 100L;

    // Displaying variables
    private boolean visible;
    private Label label;
    private Table table;
    private final ScreenAdapter game;
    private String screen;
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
     * Sets the delay of each character printing on the screen
     * @param delay - A long which is the time it takes
     */
    public void setDelay(long delay) {
        this.delay = delay;
    }

    /***
     * This function will
     *     - create a table where the UI components will be placed on
     *     - render the textbox and stack text on top using the stack
     *     - position the table in the correct place
     *     - add keyinput listeners and events
     */
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
        label = new Label("", labelStyle);
        label.setFontScale(3.0f);
        label.setWrap(true);
        label.setAlignment(Align.top | Align.left);

        Table labelTable = new Table();
        labelTable.add(label).padLeft(140).padBottom(10).size(
                (int)(Gdx.graphics.getWidth() * 0.5), (int)(Gdx.graphics.getHeight() * 0.2));
        stack.add(labelTable);

        // Add the stack to the table with padding or alignment options
        table.add(stack).padBottom(70).padLeft(0).size((int)(Gdx.graphics.getWidth() * 0.5), (int)(Gdx.graphics.getHeight() * 0.2));
        setVisible(Objects.equals(this.screen, "cutscene"));
        setupInputListener();
        entity.getEvents().addListener("SetText", this::setText);
    }

    /***
     * Sets the text in allocated blocks based on text limit
     * @param text - a string which is the text to be displayed
     */
    public void setText(String text) {
        setVisible(true);
        currentPart = 0;
        List<String> newText = new ArrayList<>();
        textLength = text.length();
        StringBuilder temp = new StringBuilder();
        StringBuilder current = new StringBuilder();
        for (int i = 0; i < textLength; i++) {
            // if word formed in temp
            if (text.charAt(i) == ' ') {
                temp.append(current);
                current = new StringBuilder();
            }

            if (i != 0 && i % textLimit == 0) {
                temp.append(" (enter to continue)");
                newText.add(temp.toString());
                temp = new StringBuilder();
            }
            current.    append(text.charAt(i));
        }
        temp.append(current).append(" (enter to continue)");
        newText.add(temp.toString());
        this.text = newText;
    }

    /** Alternative method to set text with no modificiation or spiliting up **/
    public void setTextRaw(String text) {

        label.setText(text);
    }

    /***
     * Gets the text in the blocks allocated by the algorithm
     * @return an array of strings which is the text
     */
    public List<String> getText() {
        return text;
    }

    /***
     * Set visiblility of the textbox
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
    public boolean getVisible() {return this.visible;}

    /***
     * This function will add text after a certain amount of time given the delay constant
     */
    @Override
    public void update() {
        long time = ServiceLocator.getTimeSource().getTime();
        if (this.text != null && currentPart < TextDisplay.this.text.size() && charIndex < this.text.get(currentPart).length()) {
            if (time - lastUpdate >= delay) {
                lastUpdate = time;
                // Add a character and set the label to new text
                this.currentText.append(text.get(currentPart).charAt(charIndex));
                label.setText(currentText.toString());
                charIndex++;
            }
        }
    }

    /***
     * Sets up the key input listener for the ENTER keyword to either move to the next section
     * of the text or clear the textbox from the screen
     */
    private void setupInputListener() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == com.badlogic.gdx.Input.Keys.ENTER || keycode == com.badlogic.gdx.Input.Keys.SPACE) {
                    // if the text hasn't been fully shown
                    if (TextDisplay.this.screen.equals("cutscene")) {
                        Cutscene currentCutscene = ServiceLocator.getCurrentCutscene();
                        currentCutscene.setTextForScene(currentCutscene.currentScene);
                        label.setText(currentCutscene.currentText);
                    } else if (charIndex < TextDisplay.this.text.get(currentPart).length()) {
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
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }
    public Table getTable() {
        return table;
    }
    public void disable() {
        visible = false;
        table.setVisible(false);
    }
}