package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.components.maingame.MainGameExitDisplay;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.Gdx;

import java.util.ArrayList;

/**
 * Displays Order Ticket at Main Game screen to the Main Menu screen.
 */
public class MainGameOrderTicketDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MainGameExitDisplay.class);
    private static final float Z_INDEX = 2f;
    private static final long DEFAULT_TIMER = 5000;
    private static final float viewPortHeightMultiplier = 7f/9f;
    private static final float viewPortWidthMultiplier = 3f/32f;
    private static final float viewportHeight =
            ServiceLocator.getRenderService().getStage().getViewport().getCamera().viewportHeight;
    private static final float viewportWidth =
            ServiceLocator.getRenderService().getStage().getViewport().getCamera().viewportWidth;
    private static final int distance = 20;
    private static ArrayList<Table> tableArrayList;
    private static ArrayList<Long> startTimeArrayList;
    private static ArrayList<Docket> backgroundArrayList;
    private static ArrayList<Label> countdownLabelArrayList;
    private static int orderNumb = 0;

    @Override
    public void create() {
        super.create();
        tableArrayList = new ArrayList<>();
        startTimeArrayList = new ArrayList<>();
        backgroundArrayList = new ArrayList<>();
        countdownLabelArrayList = new ArrayList<>();

        entity.getEvents().addListener("createOrder", this::addActors);
    }

    public void addActors() {
        Table table = new Table();
        long startTime = TimeUtils.millis();
        startTimeArrayList.add(startTime);
        tableArrayList.add(table);
        table.setFillParent(false);
        table.setSize(viewportWidth * 3f/32f, 5f/27f * viewportHeight); // DEFAULT_HEIGHT
        float xVal = cntXval(tableArrayList.size());
        float yVal = viewportHeight * viewPortHeightMultiplier;
        table.setPosition(xVal, yVal);

        Docket background = new Docket();
        backgroundArrayList.add(background);
        String orderNumStr = "Order " + ++orderNumb;
        Label orderNumbLabel = new Label(orderNumStr, skin);
        Label recipeNameLabel = new Label("Recipe name", skin);
        Label ingredient1Label = new Label("Ingredient 1", skin);
        Label ingredient2Label = new Label("Ingredient 2", skin);
        Label ingredient3Label = new Label("Ingredient 3", skin);
        Label countdownLabel = new Label("Timer: 5000", skin);
        countdownLabelArrayList.add(countdownLabel);

        table.setBackground(background.getImage().getDrawable());
        table.add(orderNumbLabel).padLeft(10f).row();
        table.add(recipeNameLabel).padLeft(10f).row();
        table.add(ingredient1Label).padLeft(10f).row();
        table.add(ingredient2Label).padLeft(10f).row();
        table.add(ingredient3Label).padLeft(10f).row();
        table.add(countdownLabel).padLeft(10f).row();

        stage.addActor(table);

        // Enlarge the last docket in the list
        //updateDocketSizes();
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

    public void shiftDocketsLeft() {
       if (tableArrayList.isEmpty() || backgroundArrayList.isEmpty()) return;
            Table firstTable = tableArrayList.remove(0);
            tableArrayList.add(firstTable);

            Docket firstDocket = backgroundArrayList.remove(0);
            backgroundArrayList.add(firstDocket);

            updateDocketPositions();
            updateDocketSizes();
    }

    private void stageDispose(Docket docket, Table table, int index) {
        table.setBackground((Drawable) null);
        table.clear();
        table.remove();
        ServiceLocator.getDocketService().getEvents().trigger("removeOrder", index);
        docket.dispose();
    }

    public void shiftDocketsRight() {
        if (tableArrayList.isEmpty() || backgroundArrayList.isEmpty()) return;
        Table lastTable = tableArrayList.remove(tableArrayList.size() - 1);
        tableArrayList.add(0, lastTable);
        Docket lastDocket = backgroundArrayList.remove(backgroundArrayList.size() - 1);
        backgroundArrayList.add(0, lastDocket);
        updateDocketPositions();
        updateDocketSizes();
    }

    private void updateDocketPositions() {
        for (int i = 0; i < tableArrayList.size(); i++) {
            Table table = tableArrayList.get(i);
            float xVal = cntXval(i + 1);
            table.setPosition(xVal, table.getY());
        }
    }

    private void updateDocketSizes() {
        for (int i = 0; i < backgroundArrayList.size(); i++) {
            Docket background = backgroundArrayList.get(i);
            if (i == backgroundArrayList.size() - 1) {
                // Enlarge the last docket
                background.getImage().setSize(viewportWidth * 6f/32f, viewportHeight * 10f/27f);
            } else {
                // Set normal size for other dockets
                background.getImage().setSize(viewportWidth * 3f/32f, viewportHeight * 5f/27f);
            }
        }
    }

    @Override
    public void update() {
        // No additional update logic needed here, shifting is handled by the OrderActions class
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
    

    public void updateDocketDisplay() {
        // Implement logic to update the display
        updateDocketPositions();
        updateDocketSizes();
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Draw is handled by the stage
    }

    @Override
    public float getZIndex() {
        return Z_INDEX;
    }

    @Override
    public void dispose() {
        // Cleanup resources
        for (Table table : tableArrayList) {
            table.clear();
            table.remove();
        }
        tableArrayList.clear();
        startTimeArrayList.clear();
        backgroundArrayList.clear();
        countdownLabelArrayList.clear();
        super.dispose();
    }
}