package com.csse3200.game.components.maingame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.ui.UIComponent;
import com.csse3200.game.services.GameTime;

import java.text.SimpleDateFormat;
import java.util.Date;

public class EndDayDisplay extends UIComponent {
    private final GameTime gameTime;
    private Table table; // UI to display stats
    private Label timeLabel;
    private Label moneyLabel;
    private Label passedCustomersLabel;
    private Label failedCustomersLabel;


    public EndDayDisplay(GameTime gameTime) {
        this.gameTime = gameTime;
        createStatsDisplay();
    }

    @Override
    public void create() {
        super.create();
        addKeyListener();
        stage.addActor(table);
    }

    private void addKeyListener() {
        // InputListener to handle key press events
        InputListener inputListener = new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Input.Keys.P) {
                    if (!gameTime.isPaused()) {
                        pause();
                    }
                    update();
                    table.setVisible(true);
                    return true; // Return true to indicate the event was handled
                }
                return false;
            }

            @Override
            public boolean keyUp(InputEvent event, int keycode) {
                if (keycode == Input.Keys.P) {
                    if (gameTime.isPaused()) {
                        resume();
                    }
                    update();
                    table.setVisible(false);
                    return true; // Return true to indicate the event was handled
                }
                return false;
            }
        };

        // Assuming `stage` is a valid reference to the Stage object
        stage.addListener(inputListener);
    }

    private void createStatsDisplay() {
        table = new Table(skin);
        table.center();
        table.setFillParent(true); // Make the table fill the parent stage
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.fill();
        TextureRegionDrawable backgroundDrawable = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();

        table.setBackground(backgroundDrawable);

        BitmapFont font = new BitmapFont();
        font.getData().setScale(2.0f);
        Label.LabelStyle whiteLabelStyle = new Label.LabelStyle(font, Color.WHITE);
        Label titleLabel = new Label("The sun has gone down. Today's job is finished.", whiteLabelStyle);
        titleLabel.setFontScale(5.0f);

        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(new Date());
        timeLabel = new Label("Current Time: " + dateString, whiteLabelStyle);

        moneyLabel = new Label("Money Gained/Lost: $0", whiteLabelStyle);

        passedCustomersLabel = new Label("Passed Customers: ", whiteLabelStyle);
        failedCustomersLabel = new Label("Failed Customers: ", whiteLabelStyle);

        table.add(titleLabel).colspan(3).expandX().fillX().pad(20).center();
        table.row();
        table.add(timeLabel).colspan(3).expandX().fillX().pad(10).center();
        table.row();
        table.add(moneyLabel).colspan(3).expandX().fillX().pad(10).center();
        table.row();
        table.add(passedCustomersLabel).expandX().fillX().pad(10).center();
        table.add().expandX();
        table.add(failedCustomersLabel).expandX().fillX().pad(10).center();
        table.row();

        table.setVisible(false); // Initially invisible
    }

    @Override
    public void update() {
        super.update();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
        String dateString = formatter.format(new Date());
        timeLabel.setText("Current Time: " + dateString);
    }

    private void pause() {
        gameTime.pause();
    }

    private void resume() {
        gameTime.resume();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage
    }
}
