package com.csse3200.game.components.upgrades;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class RageUpgrade extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(RageUpgrade.class);
    private boolean isVisible;
    private Table layout;

    public RageUpgrade() {
        super();
        isVisible = false;
    }

    @Override
    public void create() {
        super.create();
        layout = new Table();
        layout.setFillParent(true);
        layout.setVisible(isVisible);
        stage.addActor(layout);

        setupRedOverlay();
        setupInputListener();
    }

    public void setupRedOverlay() {
        Texture texture = ServiceLocator.getResourceService().getAsset("images/red_overlay.jpg", Texture.class);
        Image image = new Image(texture);
        image.setFillParent(true);
        image.setColor(new Color(1, 0, 0, 0.3f));
        layout.add(image);
    }

    private void setupInputListener() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == com.badlogic.gdx.Input.Keys.R) {
                    toggleRageModeOverlay();
                    return true;
                }
                return false;
            }
        });
    }

    public void show() {
        isVisible = true;
        layout.setVisible(true);
    }

    public void hide() {
        isVisible = false;
        layout.setVisible(false);
    }

    public void toggleRageModeOverlay() {
        if (isVisible) {
            hide();
        } else {
            show();
        }
    }

    @Override
    protected void draw(SpriteBatch batch) {
    }

    @Override
    public void setStage(Stage mock) {
    }

}