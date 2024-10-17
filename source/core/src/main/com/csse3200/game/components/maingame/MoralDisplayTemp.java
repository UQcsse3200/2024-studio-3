package com.csse3200.game.components.maingame;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
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
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;



//From team 2, this is just a temporary file that serves as the moraldipslay
//when the proper file is implemented, the logic for the eventlisteners will be used in that
//and this file will be deleted

public class MoralDisplayTemp extends UIComponent {
    private Table layout; // Layout manager
    private boolean isVisible;
    private final MainGameScreen game;
    private static final Logger logger = LoggerFactory.getLogger(EndDayDisplay.class);


    public MoralDisplayTemp(MainGameScreen game) {
        super();
        this.game = game;
        isVisible = false;
    }

    @Override
    public void create() {
        super.create();
        layout = new Table();
        layout.setFillParent(true);
        layout.setVisible(isVisible);
        stage.addActor(layout);

        // Create a white background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.RED);
        pixmap.fill();
        Texture pixmapTex = new Texture(pixmap);
        pixmap.dispose();
        Drawable whiteBackground = new TextureRegionDrawable(new TextureRegion(pixmapTex));
        layout.setBackground(whiteBackground);

        // Load the image
        Texture imgTexture = ServiceLocator.getResourceService()
                .getAsset("images/bird.png", Texture.class);
        Drawable imgDrawable = new TextureRegionDrawable(imgTexture);
        Image dayEndImage = new Image(imgDrawable);
        layout.add(dayEndImage).center().padTop(10).row();

        initializeUI();

        //from team 2, added the listener for when game day ends to toggle visibility
        ServiceLocator.getDayNightService().getEvents().addListener("TOMORAL", () -> {
            logger.info("it is listened in moral");
            toggleVisibility();});

    }

    private void initializeUI() {
        Label titleLabel = new Label("MORAL DILEMMA", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
        layout.add(titleLabel).pad(10).row();

        // Example event labels
        Label eventLabel = new Label("WHAT WILL YOU DO", new Label.LabelStyle(new BitmapFont(), Color.BLACK));
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
            ServiceLocator.getDayNightService().getEvents().trigger("temp"); //new

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
        // do nothing
    }
}