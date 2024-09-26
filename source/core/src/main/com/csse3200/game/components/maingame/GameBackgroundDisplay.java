package com.csse3200.game.components.maingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

//public class GameBackgroundDisplay extends UIComponent {
//    private final MainGameScreen game;
//    private Table table;
//    private static final Logger logger = LoggerFactory.getLogger(PauseMenuDisplay.class);
//    private static final String[] backgroundTextures = {"images/background_images/1.0.png",
//            "images/background_images/1.5.png",
//            "images/background_images/2.0.png",
//            "images/background_images/2.5.png",
//            "images/background_images/3.0.png",
//            "images/background_images/3.5.png",
//            "images/background_images/4.0.png",
//            "images/background_images/4.5.png",
//            "images/background_images/5.0.png",
//            "images/background_images/5.5.png",
//            "images/background_images/6.0.png",
//            "images/background_images/6.5.png",
//            "images/background_images/7.0.png",
//            "images/background_images/7.5.png",
//            "images/background_images/8.0.png",
//            "images/background_images/8.5.png",
//            "images/background_images/9.0.png",
//            "images/background_images/9.5.png",
//            "images/background_images/10.0.png",
//            "images/background_images/10.5.png",
//            "images/background_images/11.0.png",
//            "images/background_images/11.5.png",
//            "images/background_images/12.0.png",
//            "images/background_images/12.5.png",
//            "images/background_images/13.0.png",
//            "images/background_images/13.5.png",
//            "images/background_images/14.0.png",
//            "images/background_images/14.5.png",
//            "images/background_images/15.0.png",
//            "images/background_images/15.5.png",
//            "images/background_images/16.0.png",
//            "images/background_images/16.5.png",
//            "images/background_images/17.0.png",
//            "images/background_images/17.5.png",
//            "images/background_images/18.0.png",
//            "images/background_images/18.5.png"};
//
//    private float screenWidth = Gdx.graphics.getWidth();
//    private float screenHeight = Gdx.graphics.getHeight();
//
//
//    public GameBackgroundDisplay(MainGameScreen game) {
//        super();
//        this.game = game;
//    }
//
//    /**
//     * Create the pause menu background image
//     * @return backgroundImage
//     */
//    private Image createBackgroundImage() {
//        Texture gameBackgroundTexture = ServiceLocator
//                .getResourceService().getAsset("images/background_images/1.0.png", Texture.class);
//
//        Image backgroundImage = new Image(gameBackgroundTexture);
//
//        return backgroundImage;
//
//    }
//
//    private void createGameBackground() {
//        ServiceLocator.getResourceService().loadTextures(backgroundTextures);
//        table = new Table();
//        table.setFillParent(true);
//        Image backgroundImage = createBackgroundImage();
//
//        table.add(backgroundImage);
//        table.setVisible(true);
//
//        stage.addActor(table);
//    }
//
//    public void addActors() {
//        createGameBackground();
//    }
//
//
//    public void create() {
//        super.create();
//        ServiceLocator.getResourceService().loadTextures(backgroundTextures);
//        table = new Table();
//        table.setFillParent(true);
//        table.setVisible(true);
//        createGameBackground();
//        stage.addActor(table);
//    }
//
//    @Override
//    public void dispose() {
//        super.dispose();
//        ServiceLocator.getResourceService().unloadAssets(backgroundTextures);
//    }
//
//    @Override
//    protected void draw(SpriteBatch batch) {
//    }
//
//    @Override
//    public void setStage(Stage mock) {
//    }
//}

public class GameBackgroundDisplay extends UIComponent {
    private Table table;
    private static final Logger logger = LoggerFactory.getLogger(PauseMenuDisplay.class);
    private static final String[] backgroundTextures = {"images/background_images/1.0.png",
            "images/background_images/1.5.png",
            "images/background_images/2.0.png",
            "images/background_images/2.5.png",
            "images/background_images/3.0.png",
            "images/background_images/3.5.png",
            "images/background_images/4.0.png",
            "images/background_images/4.5.png",
            "images/background_images/5.0.png",
            "images/background_images/5.5.png",
            "images/background_images/6.0.png",
            "images/background_images/6.5.png",
            "images/background_images/7.0.png",
            "images/background_images/7.5.png",
            "images/background_images/8.0.png",
            "images/background_images/8.5.png",
            "images/background_images/9.0.png",
            "images/background_images/9.5.png",
            "images/background_images/10.0.png",
            "images/background_images/10.5.png",
            "images/background_images/11.0.png",
            "images/background_images/11.5.png",
            "images/background_images/12.0.png",
            "images/background_images/12.5.png",
            "images/background_images/13.0.png",
            "images/background_images/13.5.png",
            "images/background_images/14.0.png",
            "images/background_images/14.5.png",
            "images/background_images/15.0.png",
            "images/background_images/15.5.png",
            "images/background_images/16.0.png",
            "images/background_images/16.5.png",
            "images/background_images/17.0.png",
            "images/background_images/17.5.png",
            "images/background_images/18.0.png",
            "images/background_images/18.5.png",
            "images/b1.png"};

    private float screenWidth = Gdx.graphics.getWidth();
    private float screenHeight = Gdx.graphics.getHeight();

    public GameBackgroundDisplay() {
        super();
    }

    @Override
    public void create() {
        super.create();
        table = new Table();
        table.setFillParent(true);
        table.setVisible(true);
        stage.addActor(table);

        setupBackground();
    }

    private void setupBackground() {
        Texture texture = ServiceLocator.getResourceService().getAsset("images/b1.png",
                Texture.class);
        Image image = new Image(texture);
        image.setFillParent(true);
        table.add(image);
    }

    @Override
    protected void draw(SpriteBatch batch) {
    }

    @Override
    public void setStage(Stage mock) {
    }
}
