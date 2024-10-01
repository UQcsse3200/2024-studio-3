package com.csse3200.game.components.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.components.maingame.PauseMenuDisplay;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainMenuBackground extends UIComponent{

        private Table table;
        private static final Logger logger = LoggerFactory.getLogger(PauseMenuDisplay.class);
        private static final String[] backgroundTextures = {"images/Cutscenes/bg.png"};

        private float screenWidth = Gdx.graphics.getWidth();
        private float screenHeight = Gdx.graphics.getHeight();

        public MainMenuBackground() {
            super();
        }

        @Override
        public void create() {
            super.create();
            table = new Table();
            table.setFillParent(true);
            table.setVisible(true);
            stage.addActor(table);
            ServiceLocator.getResourceService().loadTextures(backgroundTextures);

            setupBackground();
        }

        private void setupBackground() {
            Texture texture = ServiceLocator.getResourceService().getAsset("images/Cutscenes/bg.png",
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

