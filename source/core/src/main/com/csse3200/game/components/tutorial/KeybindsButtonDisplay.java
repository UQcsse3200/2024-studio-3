package com.csse3200.game.components.tutorial;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;

/**
 * Displays Keybindings button
 */
public class KeybindsButtonDisplay extends UIComponent {
    private Table keybindButton;
    private Table keybindsMenu;
    private Table keybindsText;
    private boolean buttonPressed = false;
    private Texture keybindsMenuTexture;
    private String[] keybindsLabels = {"W - Move Up\nA - Move Left\nS - Move Down\nD - Move Right", "E - Pick Up",
            "Q - Chop", "P - End Day Screen", "ESC - Pause", "F1 - Debug Terminal", "[ - Move Docket Left",
            "] - Move Docket Right", "R - Rage Upgrade", "0 - Recipe Card"};

    public KeybindsButtonDisplay(Skin skin) {
        super(skin);
    }

    public KeybindsButtonDisplay() {
        super(null);
    }
    /**
     * Initializes button display
     */
    @Override
    public void create() {
        super.create();
        setupUI();
    }

    /**
     * Adds Keybindings button to UI
     */
    void setupUI() {
        keybindsMenu = createKeybindsMenu();
        keybindButton = createButtonTable();
        keybindsText = createKeybindsText();

        stage.addActor(keybindsMenu);
        stage.addActor(keybindButton);
        stage.addActor(keybindsText);
    }

    /**
     * Creates keybinds table menu
     * @return the keybinds menu table
     */
    public Table createKeybindsMenu() {
        keybindsMenu = new Table();
        keybindsMenu.center();
        keybindsMenu.setFillParent(true);
        keybindsMenu.setVisible(false);

        keybindsMenuTexture = ServiceLocator
                .getResourceService().getAsset("images/pause_menu.png", Texture.class);
        Image backgroundImage = new Image(keybindsMenuTexture);
        keybindsMenu.add(backgroundImage).width(500).height(550).expand().center();
        return keybindsMenu;
    }

    /**
     * Toggles the visibility of the keybinds menu
     * @param isPressed keybinds button is pressed
     */
    public void showKeybinds(boolean isPressed) {
        keybindsMenu.setVisible(isPressed);
        keybindsText.setVisible(isPressed);
    }

    /**
     * Creates the button
     * @return the keybinds button
     */
    public Table createButtonTable() {
        keybindButton = new Table();
        keybindButton.bottom().right();
        keybindButton.setFillParent(true);
        keybindButton.padTop(270f).padLeft(10f);

        TextButton button = new TextButton("Keybindings", skin);
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent changeEvent, Actor actor) {
                buttonPressed = !buttonPressed;
                showKeybinds(buttonPressed);
            }
        });
        keybindButton.add(button);

        return keybindButton;
    }

    /**
     * Creates the keybinds menu text
     * @return the keybinds menu text
     */
    public Table createKeybindsText() {
        keybindsText = new Table();
        keybindsText.center();
        keybindsText.setFillParent(true);
        keybindsText.padTop(20f);
        keybindsText.setVisible(false);

        for (int i = 0; i < keybindsLabels.length; i++) {
            Label text = new Label(keybindsLabels[i], skin);
            keybindsText.add(text).row();
        }

        return keybindsText;
    }

    /**
     * Removes the button
     */
    @Override
    public void dispose() {
        super.dispose();
        if (keybindButton != null) {
            keybindButton.clear();
        }
        if (keybindsMenu != null) {
            keybindsMenu.clear();
        }
        if (keybindsText != null) {
            keybindsText.clear();
        }
    }

    /**
     * Draws the button on the screen. The actual rendering is handled by the stage, so this method is empty.
     * @param batch Batch to render to.
     */
    public void draw(SpriteBatch batch) {
        // Drawing is handled by the stage, so no implementation needed
    }

    /**
     * Gets the state of the button
     * @return the stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Set the stage
     * @param stage the stage
     */
    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setKeybindsMenuTexture(Texture keybindsMenuTexture) {
        this.keybindsMenuTexture = keybindsMenuTexture;
    }

    public void setButtonTable(Table table) {
        this.keybindButton = table;
    }

    public void setMenuTable(Table table) {
        this.keybindsMenu = table;
    }

    public void setTextTable(Table table) {
        this.keybindsText = table;
    }


}