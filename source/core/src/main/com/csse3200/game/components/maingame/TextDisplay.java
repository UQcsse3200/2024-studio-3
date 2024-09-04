package com.csse3200.game.components.maingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.GdxGame;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import java.util.List;
import java.util.ArrayList;
import com.badlogic.gdx.utils.Align;

public class TextDisplay extends UIComponent {
    //String building variables
    private List<String> text;
    private int text_length = 0;
    private StringBuilder currentText;
    private int textLimit = 80;
    private int charIndex = 0;
    private long lastUpdate = 0L;
    private long delay = 100L;

    // Displaying variables
    private boolean visible;
    private Stack layout;
    private Label label;
    private Image displayBox;
    private final MainGameScreen game;

    public TextDisplay(MainGameScreen game) {
        super();
        this.game = game;
        this.visible = true;
        this.currentText = new StringBuilder();
    }
    public void create() {
        super.create();

        // Create the table for layout control and stack for layering
        Table table = new Table();
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
        label = new Label("", skin);
        label.setWrap(false);
        label.setAlignment(Align.top | Align.left);
        stack.add(label);

        // Add the stack to the table with padding or alignment options
        table.add(stack).padBottom(70).padLeft(100).size(800, 200);

        setText("Hello There Chat whats up with you guys. I just love CSSE3200 so much. Please send help");
    }
    public void setText(String text) {
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
        System.out.println(new_text);
        this.text = new_text;
    }
    public List<String> getText() {
        return text;
    }
    public void toggleVisible() {
        this.visible = !this.visible;
    }
    @Override
    public void update() {
        long time = ServiceLocator.getTimeSource().getTime();
        if (charIndex < text_length) {
            if (time - lastUpdate >= delay) {
                lastUpdate = time;
                this.currentText.append(text.get(charIndex / textLimit).charAt(charIndex % textLimit));
                label.setText(currentText.toString());
                charIndex++;
            }
        }
    }

    @Override
    public void draw(SpriteBatch batch) {}
    @Override
    public void setStage(Stage mock) {}
}