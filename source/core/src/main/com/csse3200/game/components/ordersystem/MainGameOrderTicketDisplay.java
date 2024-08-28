package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.configs.MultiStationRecipeConfig;
import com.csse3200.game.entities.configs.SingleStationRecipeConfig;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.csse3200.game.entities.factories.DishFactory;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Displays Order Ticket at Main Game screen to the Main Menu screen.
 */
public class MainGameOrderTicketDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MainGameOrderTicketDisplay.class);
    private static final float Z_INDEX = 2f;
    private static final float viewportHeight =
            ServiceLocator.getRenderService().getStage().getViewport().getCamera().viewportHeight;
    private static final float viewportWidth =
            ServiceLocator.getRenderService().getStage().getViewport().getCamera().viewportWidth;
    private static final float viewPortHeightMultiplier = 7f/9f;
    private static final float viewPortWidthMultiplier = 3f/32f;
    private static final int distance= 20;
    private static ArrayList<Table> tableArrayList;
    private static ArrayList<Docket> backgroundArrayList;
    private static ArrayList<Long> startTimeArrayList;
    private static ArrayList<Label> countdownLabelArrayList;

    private static final long DEFAULT_TIMER = 1000;

    private String recipeName;

    public MainGameOrderTicketDisplay(String recipeName) {
        this.recipeName = recipeName;
    }

    @Override
    public void create() {
        super.create();
        tableArrayList = new ArrayList<>();
        backgroundArrayList = new ArrayList<>();
        startTimeArrayList = new ArrayList<>();
        countdownLabelArrayList = new ArrayList<>();
    }

    public void addActors() {
        Recipe recipe = new Recipe(recipeName);
        List<String> ingredients = recipe.getIngredients();
        int makingTime = recipe.getMakingTime();
        Integer burnedTime = recipe.getBurnedTime();
        String stationType = recipe.getStationType();
        logger.info("Recipe Name: " + recipeName);
        logger.info("Ingredients: " + ingredients);
        logger.info("Making Time: " + makingTime);
        logger.info("Burned Time: " + (burnedTime != null ? burnedTime : "N/A"));
        logger.info("Station Type: " + stationType);

        Table table = new Table();
        long startTime = TimeUtils.millis();
        startTimeArrayList.add(startTime);
        tableArrayList.add(table);
        table.setFillParent(false);
        table.setSize(viewportWidth * 3f/32f, 5f/27f * viewportHeight); //DEFAULT_HEIGHT
        float xVal = cntXval(tableArrayList.size());
        float yVal = viewportHeight * viewPortHeightMultiplier;
        table.setPosition(xVal, yVal);
        Docket background = new Docket();
        backgroundArrayList.add(background);
        Label recipeNameLabel = new Label("Recipe name", skin);
        Label ingredient1Label = new Label("Ingredient 1", skin);
        Label ingredient2Label = new Label("Ingredient 2", skin);
        Label ingredient3Label = new Label("Ingredient 3", skin);
        Label countdownLabel = new Label("Timer: " + DEFAULT_TIMER, skin);
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
        return 20f + (instanceCnt - 1) * (distance + viewportWidth * 3f/32f);
    }

    public static void reorderDockets(int index) {
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
        ServiceLocator.getDocketService().getEvents().trigger("removeOrder", index);
        background.dispose();
        //dispose();
    }

    @Override
    public void dispose() {
        //logger.info("instance Count (after dispose): {}", instanceCnt);
        super.dispose();
    }
}
