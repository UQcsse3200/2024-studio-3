package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.components.maingame.MainGameExitDisplay;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


/**
 * Displays Order Ticket at Main Game screen to the Main Menu screen.
 */

public class MainGameOrderTicketDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MainGameExitDisplay.class);
    private static final float Z_INDEX = 2f;
    private static final long DEFAULT_TIMER = 5000;
    private static final float DEFAULT_HEIGHT = 200f;
    private static final float DEFAULT_WIDTH = 180f;
    private Table table;
    private Label countdownLabel;
    private long startTime;
    private static int instanceCnt = 0;
    private boolean disposeDone = false;
    private static final int distance= 20;


    @Override
    public void create() {
        super.create();
        instanceCnt++;
        //logger.info("instance Count (just created): {}", instanceCnt);
        addActors();
        startTime = TimeUtils.millis();
    }

    private void addActors() {
        table = new Table();
        table.setFillParent(false);
        table.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);

        float xVal = cntXval(instanceCnt);
        float yVal = 565f ;
        table.setPosition(xVal, yVal);

        Image docket =
          new Image(
            ServiceLocator.getResourceService()
              .getAsset("images/ordersystem/docket_background.png", Texture.class));

        Texture texture = new Texture(Gdx.files.internal("images/ordersystem/docket_background.png"));
        Docket backgroundDrawable = new Docket();

        Label recipeNameLabel = new Label("Recipe name", skin);
        Label ingredient1Label = new Label("Ingredient 1", skin);
        Label ingredient2Label = new Label("Ingredient 2", skin);
        Label ingredient3Label = new Label("Ingredient 3", skin);
        countdownLabel = new Label("Timer: 5000", skin);

        table.setBackground(backgroundDrawable.getImage().getDrawable()); //resize background
//        table.add(recipeNameLabel).padTop(90f).padLeft(10f).row();
        table.add(recipeNameLabel).padLeft(10f).row();
        table.add(ingredient1Label).padLeft(10f).row();
        table.add(ingredient2Label).padLeft(10f).row();
        table.add(ingredient3Label).padLeft(10f).row();
        table.add(countdownLabel).padLeft(10f).row();

        stage.addActor(table);
    }

    private float cntXval(int instanceCnt) {
        float cntXval = 20f + (instanceCnt - 1) * (distance + DEFAULT_WIDTH);
        return cntXval;
    }

    @Override
    public void update() {
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        long remainingTime = DEFAULT_TIMER - elapsedTime;

        if (remainingTime > 0) {
            countdownLabel.setText("Timer: " + (remainingTime / 1000));
        } else if (!disposeDone) {
            logger.info("disposing");
            dispose();
        } else {
            //none
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage

    }

    @Override
    public float getZIndex() {
        return Z_INDEX;
    }

    @Override
    public void dispose() {
        table.setBackground((Drawable) null);
        table.clear();
        table.remove();
        instanceCnt--;
        disposeDone = true;
        //logger.info("instance Count (after dispose): {}", instanceCnt);

        super.dispose();
    }
}
