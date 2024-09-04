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

public class TextDisplay extends UIComponent {
    private boolean isVisible;
    private int textLimit = 80;
    private final MainGameScreen game;
    private Image displayBox;
    private List<String> text;
    private Table layout;
    public TextDisplay(MainGameScreen game) {
        super();
        this.game = game;
        isVisible = true;
    }
    public void create() {
        super.create();
        layout = new Table();
        layout.setVisible(isVisible);
        layout.center().bottom();
        layout.setFillParent(true);
        stage.addActor(layout);

        Texture textboxTexture = ServiceLocator.getResourceService()
                .getAsset("images/textbox.png", Texture.class);
        Drawable textboxDrawable = new TextureRegionDrawable(textboxTexture);
        Image textboxImage = new Image(textboxDrawable);
        textboxImage.setScale(0.4f);
        layout.add(textboxImage).bottom().center().padBottom(0).padLeft(100).row();
    }
    public void setText(String text) {
        List<String> new_text = new ArrayList<>();
        String temp = "";
        for (int i = 0; i < text.length(); i++) {
            if (i != 0 && i % textLimit == 0) {
                new_text.add(temp);
                temp = "";
            }
            temp += text.charAt(i);
        }
        new_text.add(temp);
        this.text = new_text;
    }
    public List<String> getText() {
        return text;
    }

    @Override
    public void draw(SpriteBatch batch) {}
    @Override
    public void setStage(Stage mock) {}
}