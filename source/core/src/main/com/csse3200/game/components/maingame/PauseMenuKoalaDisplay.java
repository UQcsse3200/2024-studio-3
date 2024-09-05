package com.csse3200.game.components.maingame;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PauseMenuKoalaDisplay extends UIComponent {

    private static final Logger logger = LoggerFactory.getLogger(PauseMenuKoalaDisplay.class);
    private Table table;

    private Image koala;

    private void addActors() {
        table = new Table();
        table.bottom().left();
//        table.setFillParent(true);
        Texture koalaTexture = ServiceLocator
                .getResourceService().getAsset("images/koala5.png", Texture.class);
        Image koalaImage = new Image(koalaTexture);
        logger.debug("Not loading");
        table.add(koalaImage);
        table.row();
        stage.addActor(table);
    }
    @Override
    public void create() {
        super.create();
        addActors();
    }
    @Override
    protected void draw(SpriteBatch batch) {
    }

    @Override
    public void setStage(Stage mock) {
    }

    @Override
    public void dispose() {
        table.clear();
        super.dispose();
    }
}
