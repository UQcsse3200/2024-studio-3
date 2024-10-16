package com.csse3200.game.components.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * An ui component for displaying the Main menu.
 */
public class MainMenuDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MainMenuDisplay.class);
    private static final float Z_INDEX = 2f;
    private Table table;

    private float scaleOfButton = 1.5f;

    // Animation variables
    private float startX = 0;
    private float startY = 0;
    private float endX = 500;
    private float endY = 500;
    private float duration = 8;
    private float degrees = 1000;
    private float rotateDuration = 10;

    private int numberOfAnimals = 10;
    private String[] listOfAnimals = {"thegoat", "goos", "gorill", "monki", "wolf", "raw_acai", "raw_banana", "chopped_strawb", "chopped_chocolate", "salad"};

    private int animalOnScreen = 2;

    private float widthOfScreen = Gdx.graphics.getWidth();
    private float heightOfScreen = Gdx.graphics.getHeight();

    private Timer.Task animalMoveTask;
    private Timer.Task clearStage;

    private static final String BACKGROUND_MUSIC = "sounds/Main_Menu_BGM.mp3";
    private static final String[] SCREEN_MUSIC = {BACKGROUND_MUSIC};

    private Random rand = new Random(); // Reuse the Random instance as suggested

    @Override
    public void create() {
        ServiceLocator.getResourceService().loadMusic(SCREEN_MUSIC);
        ServiceLocator.getResourceService().loadAll();
        ServiceLocator.registerMainMenuDisplay(this);
        super.create();

        animalMoveTask = new Timer.Task() {
            public void run() {
                background();
                addActors();
            }
        };
        Timer.schedule(animalMoveTask, 0, 2);

        clearStage = new Timer.Task() {
            public void run() {
                Array<Actor> actors = stage.getActors();
                for (Actor actor : actors) {
                    if (actor.getX() < -230 || actor.getX() > widthOfScreen + 230 || actor.getY() < -230 || actor.getY() > heightOfScreen + 230) {
                        actor.remove();
                    }
                }
            }
        };
        Timer.schedule(clearStage, 0, 6);
        playMusic();
    }

    public int randomGenerator(int min, int max) {
        return rand.nextInt(max - min + 1) + min;
    }

    /**
     * Create the background animation. This method will create a random number of animals and move them across
     * the screen.
     */
    public void background() {
        for (int x = 1; x <= animalOnScreen; x++) {
            int random = randomGenerator(0, 3);
            if (random == 0) {
                startX = -250;
                startY = randomGenerator(0, (int) heightOfScreen);
                endX = widthOfScreen + 250;
                endY = randomGenerator(0, (int) heightOfScreen);
            } else if (random == 1) {
                startX = randomGenerator(0, (int) widthOfScreen);
                startY = -250;
                endX = randomGenerator(0, (int) widthOfScreen);
                endY = heightOfScreen + 250;
            } else if (random == 2) {
                startX = widthOfScreen + 250;
                startY = randomGenerator(0, (int) heightOfScreen);
                endX = -250;
                endY = randomGenerator(0, (int) heightOfScreen);
            } else if (random == 3) {
                startX = randomGenerator(0, (int) widthOfScreen);
                startY = heightOfScreen + 250;
                endX = randomGenerator(0, (int) widthOfScreen);
                endY = -250;
            }

            random = randomGenerator(1, numberOfAnimals) - 1;
            Image actor = new Image(ServiceLocator.getResourceService().getAsset("images/main_menu_animals/" + listOfAnimals[random] + ".png", Texture.class));
            actor.addAction(Actions.parallel(Actions.moveTo(endX, endY, duration), Actions.rotateBy(degrees, rotateDuration)));
            actor.setPosition(startX, startY);
            float height = actor.getHeight();
            float width = actor.getWidth();
            actor.setOrigin(width / 2, height / 2);
            actor.setScale((float) (widthOfScreen * 2.6 / 10000 + 0.17), (float) (heightOfScreen * 4.16 / 10000 + 0.17));
            actor.toBack();
            stage.addActor(actor);
        }
        stage.act();
    }

    private void addActors() {
        Table logo = new Table();
        logo.setFillParent(true);

        table = new Table();
        table.setFillParent(true);

        Image title = new Image(ServiceLocator.getResourceService().getAsset("images/Logo.png", Texture.class));

        ImageTextButton startBtn = new ImageTextButton("Start", skin);
        ImageTextButton loadBtn = new ImageTextButton("Load", skin);
        ImageTextButton settingsBtn = new ImageTextButton("Settings", skin);
        ImageTextButton exitBtn = new ImageTextButton("Exit", skin);

        startBtn.setTransform(true);
        startBtn.setScale(scaleOfButton);
        loadBtn.setTransform(true);
        loadBtn.setScale(scaleOfButton);
        settingsBtn.setTransform(true);
        settingsBtn.setScale(scaleOfButton);
        exitBtn.setTransform(true);
        exitBtn.setScale(scaleOfButton);

        startBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Start button clicked");
                        animalMoveTask.cancel();
                        entity.getEvents().trigger("start");
                    }
                });

        loadBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Load button clicked");
                        animalMoveTask.cancel();
                        entity.getEvents().trigger("load");
                    }
                });

        settingsBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Settings button clicked");
                        animalMoveTask.cancel();
                        entity.getEvents().trigger("settings");
                    }
                });

        exitBtn.addListener(
                new ChangeListener() {
                    @Override
                    public void changed(ChangeEvent changeEvent, Actor actor) {
                        logger.debug("Exit button clicked");
                        animalMoveTask.cancel();
                        entity.getEvents().trigger("exit");
                    }
                });

        logo.add(title).pad(0, 0, 250, 0);

        table.add(startBtn).pad(600, 0, 0, 0).height(60);
        table.add(loadBtn).pad(600, 95, 0, 0).height(60);
        table.add(settingsBtn).pad(600, 90, 0, 0).height(60);
        table.add(exitBtn).pad(600, 120, 0, 0).height(60);
        //table.add(tutBtn).pad(600, 80, 0, 0).height(60);

        table.center();

        stage.addActor(logo);
        stage.addActor(table);
    }

    private void playMusic() {
        Music music = ServiceLocator.getResourceService().getAsset(BACKGROUND_MUSIC, Music.class);
        music.setLooping(true);
        music.setVolume(0.05f);
        music.play();
    }

    @Override
    public void draw(SpriteBatch batch) {
    }

    @Override
    public float getZIndex() {
        return Z_INDEX;
    }

    @Override
    public void setStage(Stage mock) {
    }

    public void stopBackgroundTasks() {
        ServiceLocator.getResourceService().getAsset(BACKGROUND_MUSIC, Music.class).stop();
        ServiceLocator.getResourceService().unloadAssets(SCREEN_MUSIC);

        if (animalMoveTask != null) {
            animalMoveTask.cancel();
        }
        if (clearStage != null) {
            clearStage.cancel();
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        table.clear();
        ServiceLocator.getResourceService().getAsset(BACKGROUND_MUSIC, Music.class).stop();
        ServiceLocator.getResourceService().unloadAssets(SCREEN_MUSIC);
    }
}
