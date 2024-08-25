package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
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

import java.util.ArrayList;


/**
 * Displays Order Ticket at Main Game screen to the Main Menu screen.
 */

public class MainGameOrderTicketDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MainGameExitDisplay.class);
    private static final float Z_INDEX = 2f;
    private static final long DEFAULT_TIMER = 5000;
    private static final float DEFAULT_HEIGHT = 200f;
    private static final float DEFAULT_WIDTH = 180f;
    private static final float viewPortHeightMultiplier = 7f/9f;
    private static final float viewPortWidthMultiplier = 3f/32f;
    private static Table table;
    private Label countdownLabel;
    private Docket background;
    private long startTime;
    private static int instanceCnt = 0;
    private boolean disposeDone = false;
    private static final int distance= 20;
    private static ArrayList<Table> tableArrayList;
    private static ArrayList<Docket> backgroundArrayList;
    private static ArrayList<Long> startTimeArrayList;
    private static ArrayList<Label> countdownLabelArrayList;


    @Override
    public void create() {
        super.create();
        //logger.info("instance Count (just created): {}", instanceCnt);
        //addActors();
        tableArrayList = new ArrayList<>();
        backgroundArrayList = new ArrayList<>();
        startTimeArrayList = new ArrayList<>();
        countdownLabelArrayList = new ArrayList<>();
    }

    public void addActors() {
        instanceCnt++;
        table = new Table();
        startTime = TimeUtils.millis();
        startTimeArrayList.add(startTime);
        tableArrayList.add(table);
        table.setFillParent(false);
        float viewportHeight = ServiceLocator.getRenderService().getStage().getViewport().getCamera().viewportHeight;
        float viewportWidth = ServiceLocator.getRenderService().getStage().getViewport().getCamera().viewportWidth;
        table.setSize(viewportWidth * 3f/32f, 5f/27f * viewportHeight); //DEFAULT_HEIGHT
        float xVal = cntXval(tableArrayList.size());
        float yVal = viewportHeight * viewPortHeightMultiplier;
        table.setPosition(xVal, yVal);
        background = new Docket();
        backgroundArrayList.add(background);
        Label recipeNameLabel = new Label("Recipe name", skin);
        Label ingredient1Label = new Label("Ingredient 1", skin);
        Label ingredient2Label = new Label("Ingredient 2", skin);
        Label ingredient3Label = new Label("Ingredient 3", skin);
        countdownLabel = new Label("Timer: 5000", skin);
        countdownLabelArrayList.add(countdownLabel);
        table.setBackground(background.getImage().getDrawable()); //resize background
//        table.add(recipeNameLabel).padTop(90f).padLeft(10f).row();
        table.add(recipeNameLabel).padLeft(10f).row();
        table.add(ingredient1Label).padLeft(10f).row();
        table.add(ingredient2Label).padLeft(10f).row();
        table.add(ingredient3Label).padLeft(10f).row();
        table.add(countdownLabel).padLeft(10f).row();

        stage.addActor(table);
    }

    private float cntXval(int instanceCnt) {
        float viewportWidth = ServiceLocator.getRenderService().getStage().getViewport().getCamera().viewportWidth;
        return 20f + (instanceCnt - 1) * (distance + viewportWidth * 3f/32f);
    }

    public static void reorderDockets(int index) {
        float viewportWidth = ServiceLocator.getRenderService().getStage().getViewport().getCamera().viewportWidth;
        for (int i = index + 1; i < tableArrayList.size(); i++) {
            Table currTable = tableArrayList.get(i);
            currTable.setX(currTable.getX() - (distance + viewportWidth * 3f/32f));
        }
    }

    @Override
    public void update() {
        for (int i = 0; i < tableArrayList.size(); i++) {
            Docket currBackground = backgroundArrayList.get(i);
            Table currTable = tableArrayList.get(i);
            Label currCountdown = countdownLabelArrayList.get(i);
            long elapsedTime = TimeUtils.timeSinceMillis(startTimeArrayList.get(i));
            long remainingTime = DEFAULT_TIMER - elapsedTime;
            if (remainingTime > 0) {
                currCountdown.setText("Timer: " + (remainingTime / 1000));
                currBackground.updateDocketTexture((double) remainingTime/1000);
                currTable.setBackground(currBackground.getImage().getDrawable());
            } else {
                stageDispose(currBackground, currTable, i);
                tableArrayList.remove(i);
                backgroundArrayList.remove(i);
                startTimeArrayList.remove(i);
                countdownLabelArrayList.remove(i);
            }
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

    public void stageDispose(Docket background, Table table, int index) {
        table.setBackground((Drawable) null);
        table.clear();
        table.remove();
        instanceCnt--;
        ServiceLocator.getDocketService().getEvents().trigger("removeOrder", index);
        //dispose();
    }

    @Override
    public void dispose() {
        //logger.info("instance Count (after dispose): {}", instanceCnt);
        super.dispose();
    }
}
