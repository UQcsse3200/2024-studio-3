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
import com.badlogic.gdx.scenes.scene2d.ui.List;
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

public class EndDayDisplay extends UIComponent {
    private Table layout; // Layout manager
    private boolean isVisible;
    private final MainGameScreen game;
    private Image dayEndImage;

    public EndDayDisplay(MainGameScreen game) {
        super();
        this.game = game;
        isVisible = false;
    }

    public void create() {
        super.create();
        layout = new Table();
        layout.setFillParent(true);
        layout.setVisible(isVisible);
        stage.addActor(layout);

        // Create a white background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.WHITE);
        pixmap.fill();
        Texture pixmapTex = new Texture(pixmap);
        pixmap.dispose();
        Drawable whiteBackground = new TextureRegionDrawable(new TextureRegion(pixmapTex));
        layout.setBackground(whiteBackground);

        // Load the image
        Texture imgTexture = ServiceLocator.getResourceService()
                .getAsset("images/bird.png", Texture.class);
        Drawable imgDrawable = new TextureRegionDrawable(imgTexture);
        dayEndImage = new Image(imgDrawable);
        layout.add(dayEndImage).center().padTop(10).row();

        initializeUI();
        setupInputListener();
    }

    private void initializeUI() {
        Label titleLabel = new Label("Day End Summary", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        layout.add(titleLabel).pad(10).row();

        // Example event labels
        Label eventLabel = new Label("Major Events in the Kitchen Game Today", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        layout.add(eventLabel).pad(10).row();

        // Customer lists
        List<String> passedCustomers = new List<>(skin);
        passedCustomers.setItems("Customer A", "Customer B", "Customer C");
        List<String> failedCustomers = new List<>(skin);
        failedCustomers.setItems("Customer X", "Customer Y");

        Table listTable = new Table();
        listTable.add(new Label("Passed Customers", skin)).pad(10);
        listTable.add(new Label("Failed Customers", skin)).pad(10).row();
        listTable.add(new ScrollPane(passedCustomers, skin)).pad(10);
        listTable.add(new ScrollPane(failedCustomers, skin)).pad(10);

        layout.add(listTable).expand().fill().row();

        TextButton closeBtn = new TextButton("Close", skin);
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                toggleVisibility();
            }
        });
        layout.add(closeBtn).padTop(20).row();
    }

    private void setupInputListener() {
        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == com.badlogic.gdx.Input.Keys.P) {
                    toggleVisibility();
                    return true;
                }
                return false;
            }
        });
    }

    public void show() {
        isVisible = true;
        layout.setVisible(isVisible);
        game.pause(); // Pause the game when the display is shown
    }

    public void hide() {
        isVisible = false;
        layout.setVisible(isVisible);
        game.resume(); // Resume the game when the display is hidden
    }

    public void toggleVisibility() {
        if (isVisible) {
            hide();
        } else {
            show();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage

    }

    @Override
    public void setStage(Stage mock) {

    }
}
