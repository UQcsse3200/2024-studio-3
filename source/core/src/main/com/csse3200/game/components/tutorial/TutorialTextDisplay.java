package com.csse3200.game.components.tutorial;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.screens.TutorialScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import java.util.List;
import java.util.ArrayList;
import com.badlogic.gdx.utils.Align;

public class TutorialTextDisplay extends UIComponent {

    private List<String> text;
    private int current_part = 0;
    private int text_length = 0;
    private StringBuilder currentText;
    private int textLimit = 60;
    private int charIndex = 0;
    private long lastUpdate = 0L;
    private long delay = 100L;

    // Displaying variables
    private boolean visible;
    private Stack layout;
    private Label label;
    private Table table;
    private Image displayBox;
    private final TutorialScreen game;

    public TutorialTextDisplay() {
        super();
        this.game = null;
        this.table = new Table();
        this.visible = true;
        this.currentText = new StringBuilder();
    }

    public TutorialTextDisplay(TutorialScreen game) {
        this.game = game;
        this.table = new Table();
        this.visible = true;
        this.currentText = new StringBuilder();
    }

    @Override
    public void create() {
        super.create();


        setVisible(false);
        table.setFillParent(true);
        table.center().bottom();
        stage.addActor(table);
        Stack stack = new Stack();


        Texture textboxTexture = ServiceLocator.getResourceService()
                .getAsset("images/textbox.png", Texture.class);
        Drawable textboxDrawable = new TextureRegionDrawable(textboxTexture);
        Image textboxImage = new Image(textboxDrawable);
        textboxImage.setScale(1.25f);
        stack.add(textboxImage);


        BitmapFont defaultFont = new BitmapFont();
        Label.LabelStyle labelStyle = new Label.LabelStyle(defaultFont, Color.BLACK);
        label = new Label("", labelStyle);
        label.setFontScale(3.0f);
        label.setWrap(true);
        label.setAlignment(Align.top | Align.left);

        Table labelTable = new Table();
        labelTable.add(label).padLeft(140).padBottom(10).size(
                (int) (Gdx.graphics.getWidth() * 0.5), (int) (Gdx.graphics.getHeight() * 0.2));
        stack.add(labelTable);


        table.add(stack).padBottom(70).padLeft(0).size((int) (Gdx.graphics.getWidth() * 0.5), (int) (Gdx.graphics.getHeight() * 0.2));

        setupInputListener();
        entity.getEvents().addListener("SetText", this::setText);
    }

    public void setText(String text) {
        setVisible(true);
        current_part = 0;
        List<String> new_text = new ArrayList<>();
        text_length = text.length();
        String temp = "";
        for (int i = 0; i < text_length; i++) {
            if (i != 0 && i % textLimit == 0) {
                new_text.add(temp);
                temp = "";
            }
            temp += text.charAt(i);
        }
        new_text.add(temp);
        this.text = new_text;
        this.currentText.setLength(0);
    }

    public List<String> getText() {
        return text;
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
        if (this.text != null && current_part < this.text.size() && charIndex < this.text.get(current_part).length()) {
            if (time - lastUpdate >= delay) {
                lastUpdate = time;
                this.currentText.append(text.get(current_part).charAt(charIndex));
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
                    current_part++;
                    charIndex = 0;
                    lastUpdate = 0;
                    currentText.setLength(0);

                    if (current_part == text.size()) {
                        setVisible(false);
                    }
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

    public Table getTable() {
        return table;
    }
}