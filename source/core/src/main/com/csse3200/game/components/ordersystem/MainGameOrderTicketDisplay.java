package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.components.maingame.MainGameExitDisplay;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

import java.util.ArrayList;
import java.util.List;


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
    private List<Docket> docketList;
    //private Docket docket;
    private long startTime;
    private static int instanceCnt = 0;
    private boolean disposeDone = false;
    private static final int distance= 20;


    @Override
    public void create() {
        super.create();
        table = new Table();
        table.top().left();
        table.setFillParent(true);
        instanceCnt++;
        //logger.info("instance Count (just created): {}", instanceCnt);
        //addActors();
        startTime = TimeUtils.millis();
        docketList = new ArrayList<>();
        startTime = TimeUtils.millis(); //inspired by services/GameTime
        ServiceLocator.registerDocketService(new DocketService());
        ServiceLocator.getDocketService().getEvents().addListener("addNewDocket", this::addActors);
        ServiceLocator.getDocketService().getEvents().addListener("removeDocket", this::removeDocket);
        addActors();
    }

    private void addActors() {
        Docket docket = new Docket();
        docketList.add(docket);
        table.add(docket.getImage());
        Cell<Image> newCell = table.getCell(docket.getImage());
        newCell.size(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        newCell.padTop(10f);
        newCell.padLeft(10f);
        newCell.padRight(10f);
        stage.addActor(table);
    }

    public void removeDocket(Docket docket) {
        logger.info("Called all g");
        logger.info("Docket check {}", docket.toString());
        logger.info("Image check {}", docket.getImage().toString());
        table.removeActor(docket.getImage());
    }

    /*private void addActors() {
        table = new Table();
        table.setFillParent(false);
        table.setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
        float xVal = cntXval(instanceCnt);
        float yVal = 700f ; //565f
        table.setPosition(xVal, yVal);
        Texture texture = new Texture(Gdx.files.internal("images/ordersystem/docket_background.png"));
        Drawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(texture));
        Label recipeNameLabel = new Label("Recipe name", skin);
        Label ingredient1Label = new Label("Ingredient 1", skin);
        Label ingredient2Label = new Label("Ingredient 2", skin);
        Label ingredient3Label = new Label("Ingredient 3", skin);
        countdownLabel = new Label("Timer: 5", skin);
        table.setBackground(backgroundDrawable); //resize background
//        table.add(recipeNameLabel).padTop(90f).padLeft(10f).row();
        table.add(recipeNameLabel).padLeft(10f).row();
        table.add(ingredient1Label).padLeft(10f).row();
        table.add(ingredient2Label).padLeft(10f).row();
        table.add(ingredient3Label).padLeft(10f).row();
        table.add(countdownLabel).padLeft(10f).row();

        stage.addActor(table);
    }*/

    private float cntXval(int instanceCnt) {
        float cntXval = 20f + (instanceCnt - 1) * (distance + DEFAULT_WIDTH);
        return cntXval;
    }

    @Override
    public void update() {
        long elapsedTime = TimeUtils.timeSinceMillis(startTime); //inspired by services/GameTime
        long remainingTime = DEFAULT_TIMER - elapsedTime; //inspired by services/GameTime
        double remainingTimeSecs = remainingTime / 1000;
        if (remainingTime > 0) {
            //countdownLabel.setText("Timer: " + (remainingTime / 1000));
        }
        for (int i = 0; i < docketList.size(); i++) {
            docketList.get(i).update();
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
