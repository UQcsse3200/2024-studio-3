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
    private static final int distance = 20;
    private static ArrayList<Table> tableArrayList;
    private static ArrayList<Long> startTimeArrayList;
    private static ArrayList<Docket> backgroundArrayList;
    private static ArrayList<Label> countdownLabelArrayList;

    @Override
    public void create() {
        super.create();
        tableArrayList = new ArrayList<>();
        startTimeArrayList = new ArrayList<>();
        backgroundArrayList = new ArrayList<>();
        countdownLabelArrayList = new ArrayList<>();
    }

    public void addActors() {
        Table table = new Table();
        long startTime = TimeUtils.millis();
        startTimeArrayList.add(startTime);
        tableArrayList.add(table);
        table.setFillParent(false);
        table.setSize(viewPortWidthMultiplier * 3f/32f, 5f/27f * viewPortHeightMultiplier); // DEFAULT_HEIGHT
        float xVal = cntXval(tableArrayList.size());
        float yVal = viewPortHeightMultiplier * viewPortHeightMultiplier;
        table.setPosition(xVal, yVal);

        Docket background = new Docket();
        backgroundArrayList.add(background);
        Label recipeNameLabel = new Label("Recipe name", skin);
        Label ingredient1Label = new Label("Ingredient 1", skin);
        Label ingredient2Label = new Label("Ingredient 2", skin);
        Label ingredient3Label = new Label("Ingredient 3", skin);
        Label countdownLabel = new Label("Timer: 5000", skin);
        countdownLabelArrayList.add(countdownLabel);

        table.setBackground(background.getImage().getDrawable());
        table.add(recipeNameLabel).padLeft(10f).row();
        table.add(ingredient1Label).padLeft(10f).row();
        table.add(ingredient2Label).padLeft(10f).row();
        table.add(ingredient3Label).padLeft(10f).row();
        table.add(countdownLabel).padLeft(10f).row();

        stage.addActor(table);

        // Enlarge the last docket in the list
        updateDocketSizes();
    }

    private float cntXval(int instanceCnt) {
        return 20f + (instanceCnt - 1) * (distance + viewPortWidthMultiplier * 3f/32f);
    }

    public void shiftDocketsLeft() {
       if (tableArrayList.isEmpty() || backgroundArrayList.isEmpty()) return;

    // Shift both lists together
    Table firstTable = tableArrayList.remove(0);
    tableArrayList.add(firstTable);

    Docket firstDocket = backgroundArrayList.remove(0);
    backgroundArrayList.add(firstDocket);

    updateDocketPositions();
    updateDocketSizes();
    }

    public void shiftDocketsRight() {
        if (tableArrayList.isEmpty() || backgroundArrayList.isEmpty()) return;

        // Shift both lists together
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
                background.getImage().setSize(viewPortWidthMultiplier * 6f/32f, viewPortHeightMultiplier * 10f/27f);
            } else {
                // Set normal size for other dockets
                background.getImage().setSize(viewPortWidthMultiplier * 3f/32f, viewPortHeightMultiplier * 5f/27f);
            }
        }
    }

    @Override
    public void update() {
        // No additional update logic needed here, shifting is handled by the OrderActions class
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