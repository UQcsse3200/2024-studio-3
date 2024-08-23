package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.components.maingame.MainGameExitDisplay;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Displays Order Ticket at Main Game screen to the Main Menu screen.
 */

public class MainGameOrderTicketDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MainGameExitDisplay.class);
    private static final float Z_INDEX = 2f;
    private static final long DEFAULT_TIMER = 5000;
    private Table table;
    private Label countdownLabel;
    private long startTime;

    @Override
    public void create() {
        super.create();
        addActors();
        startTime = TimeUtils.millis(); //inspired by services/GameTime
    }

    private void addActors() {
        table = new Table();
        table.top().left();
        table.setFillParent(true);

        Label recipeNameLabel = new Label("Recipe name", skin);
        Label ingredient1Label = new Label("Ingredient 1", skin);
        Label ingredient2Label = new Label("Ingredient 2", skin);
        Label ingredient3Label = new Label("Ingredient 3", skin);
        countdownLabel = new Label("Timer: 5000", skin);

        table.add(recipeNameLabel).padTop(90f).padLeft(10f).row();
        table.add(ingredient1Label).padLeft(10f).row();
        table.add(ingredient2Label).padLeft(10f).row();
        table.add(ingredient3Label).padLeft(10f).row();
        table.add(countdownLabel).padLeft(10f).row();

        stage.addActor(table);
    }

    @Override
    public void update() {
        long elapsedTime = TimeUtils.timeSinceMillis(startTime); //inspired by services/GameTime
        long remainingTime = DEFAULT_TIMER - elapsedTime; //inspired by services/GameTime

        if (remainingTime > 0) {
            countdownLabel.setText("Timer: " + (remainingTime / 1000));
        } else {
            dispose();
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
        table.clear();
        super.dispose();
    }
}
