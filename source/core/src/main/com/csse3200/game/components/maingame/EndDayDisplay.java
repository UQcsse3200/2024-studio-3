package com.csse3200.game.components.maingame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.GdxGame;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.DayNightService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import com.badlogic.gdx.utils.Timer;
import com.badlogic.gdx.utils.Timer.Task;
import com.badlogic.gdx.math.Interpolation;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.csse3200.game.entities.Entity;

import java.util.ArrayList;

public class EndDayDisplay extends UIComponent {
    private Table layout; // Layout manager
    private boolean isVisible;
    private static final Logger logger = LoggerFactory.getLogger(EndDayDisplay.class);

    private final MainGameScreen gameScreen;
    private final GdxGame game;
    private Image birdImage;
    private Image pointImage1;
    private Image pointImage2;
    private Image pointImage3;
    private float imageX;
    private Timer.Task birdMoveTask;
    private int currentGold;
    private Label goldLabel;
    private int startGold;


    private final ArrayList<String> customerNameArray;
    private List<String> customerList;


    public EndDayDisplay() {
        super();
        this.gameScreen = ServiceLocator.getGameScreen();
        this.game = ServiceLocator.getGameScreen().getGame();
        isVisible = false;
        this.startGold = ServiceLocator.getLevelService().getCurrGold();
        this.currentGold = this.startGold;
        ServiceLocator.getLevelService().getEvents().addListener("resetScreen", MainGameScreen::resetScreen);


        this.customerNameArray = new ArrayList<>();


    }

    public void create() {
        super.create();
        layout = new Table();
        layout.setFillParent(true);
        layout.setVisible(isVisible);
        stage.addActor(layout);

        ServiceLocator.getDocketService().getEvents().addListener("goldUpdated", this::handleGoldUpdate);
        //ServiceLocator.getEntityService().getEvents().addListener("spawnCustomer", this::updateCustomerList);

        ServiceLocator.getLevelService().getEvents().addListener("customerSpawned", this::updateCustomerList);


        ServiceLocator.getEntityService().getEvents().addListener("toggleEndDayScreen", this::toggleVisibility);

        // Create a background
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(new Color(234f/255f, 221/255f, 202/255f, 1));
        pixmap.fill();
        Texture pixmapTex = new Texture(pixmap);
        pixmap.dispose();
        Drawable whiteBackground = new TextureRegionDrawable(new TextureRegion(pixmapTex));
        layout.setBackground(whiteBackground);

        setupImages();


        initializeUI();

        //From Team2, we made some changes here to follow the logic of the day cycle transitions
        // but please talk to me if you have any issues
//        setupInputListener();

        //from team 2, added the listener for when game day ends to toggle visibility
        ServiceLocator.getDayNightService().getEvents().addListener("endOfDay", () -> {
            logger.info("it is listened in end day");
            show();});

    }

    private void handleGoldUpdate(int gold) {
        currentGold = gold;
        goldLabel.setText(currentGold);
    }

//    private void updateCustomerList(Entity customer) {
//        System.out.println("999");
//    }



    private void setupCustomerLists() {
        // Customer lists
        List<String> passedCustomers = new List<>(skin);
        List<String> failedCustomers = new List<>(skin);
        customerList = new List<>(skin);
        Table listTable = new Table();

        Label passedLabel = new Label("Passed Customers", skin);
        passedLabel.setFontScale(1.2f);
        Label failedLabel = new Label("Failed Customers", skin);
        failedLabel.setFontScale(1.2f);

        listTable.add(passedLabel).pad(10).center();
        listTable.add(failedLabel).pad(10).center().row();

        ScrollPane passedScrollPane = new ScrollPane(passedCustomers, skin);
        passedScrollPane.setSmoothScrolling(true);

        ScrollPane failedScrollPane = new ScrollPane(customerList, skin);
        failedScrollPane.setSmoothScrolling(true);

        listTable.add(passedScrollPane).pad(10).expand().width(400).fillY();
        listTable.add(failedScrollPane).pad(10).expand().width(400).fillY().row();

        layout.add(listTable).expand().fill().row();
    }
    private void updateCustomerList(String customerName) {
        customerNameArray.add(customerName);
        customerList.setItems(customerNameArray.toArray(new String[0]));
    }

    private void setupImages() {
        // Load the image
        Texture imgTexture = ServiceLocator.getResourceService()
                .getAsset("images/bird.png", Texture.class);
        Drawable imgDrawable = new TextureRegionDrawable(new TextureRegion(imgTexture));
        birdImage = new Image(imgDrawable);
        birdImage.setVisible(false);
        birdImage.setPosition(0, 3 * Gdx.graphics.getHeight() / 4 - birdImage.getHeight() / 2);
        stage.addActor(birdImage);

        Texture pointTexture = ServiceLocator.getResourceService()
                .getAsset("images/point.png", Texture.class);
        Drawable pointDrawable = new TextureRegionDrawable(new TextureRegion(pointTexture));
        pointImage1 = new Image(pointDrawable);
        pointImage1.setVisible(false);
        pointImage1.setPosition(0, 3 * Gdx.graphics.getHeight() / 4 - pointImage1.getHeight() / 2);
        stage.addActor(pointImage1);

        pointImage2 = new Image(pointDrawable);
        pointImage2.setVisible(false);
        pointImage2.setPosition(0, 3 * Gdx.graphics.getHeight() / 4 - pointImage2.getHeight() / 2);
        stage.addActor(pointImage2);

        pointImage3 = new Image(pointDrawable);
        pointImage3.setVisible(false);
        pointImage3.setPosition(0, 3 * Gdx.graphics.getHeight() / 4 - pointImage3.getHeight() / 2);
        stage.addActor(pointImage3);
    }

    private void initializeUI() {
        Table spacer = new Table();
        spacer.add().height(3 * birdImage.getHeight() / 5);
        layout.add(spacer).row();

        Texture coinTexture = ServiceLocator.getResourceService()
                .getAsset("images/coin.png", Texture.class);
        Drawable coinDrawable = new TextureRegionDrawable(new TextureRegion(coinTexture));
        Image coinImage = new Image(coinDrawable);

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("flat-earth/skin/fonts/Tiny5-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 300;
        parameter.gamma = 1.8f;
        BitmapFont font = generator.generateFont(parameter);
        generator.dispose();
        Label.LabelStyle style = new Label.LabelStyle(font, new Color(1.0f, 0.9f, 0.0f, 1.0f));
        goldLabel = new Label("0", style);

        // Sub-table to align the coin image and gold label horizontally
        Table coinAndGoldLayout = new Table();
        coinAndGoldLayout.add(coinImage).padRight(20);  // Add the coin image with some padding to the right
        coinAndGoldLayout.add(goldLabel);  // Add the gold label next to the coin

        // Add the sub-table to the main layout, centered horizontally
        layout.add(coinAndGoldLayout).expandX().fillX().center().row();

//        // Customer lists
//        List<String> passedCustomers = new List<>(skin);
//        passedCustomers.setItems("Customer A", "Customer B", "Customer C");
//        List<String> failedCustomers = new List<>(skin);
//        failedCustomers.setItems("Customer X", "Customer Y");
//
//        Table listTable = new Table();
//        listTable.add(new Label("Passed Customers", skin)).pad(10);
//        listTable.add(new Label("Failed Customers", skin)).pad(10).row();
//        listTable.add(new ScrollPane(passedCustomers, skin)).pad(10);
//        listTable.add(new ScrollPane(failedCustomers, skin)).pad(10);
//
//        layout.add(listTable).expand().fill().row();

//        // Customer lists
//        List<String> passedCustomers = new List<>(skin);
//        List<String> failedCustomers = new List<>(skin);
//        customerList = new List<>(skin);
//        Table listTable = new Table();
//
//        Label passedLabel = new Label("Passed Customers", skin);
//        passedLabel.setFontScale(1.2f);
//        Label failedLabel = new Label("Failed Customers", skin);
//        failedLabel.setFontScale(1.2f);
//
//        listTable.add(passedLabel).pad(10).center();
//        listTable.add(failedLabel).pad(10).center().row();
//
//        ScrollPane passedScrollPane = new ScrollPane(passedCustomers, skin);
//        passedScrollPane.setSmoothScrolling(true);
//
//        ScrollPane failedScrollPane = new ScrollPane(customerList, skin);
//        failedScrollPane.setSmoothScrolling(true);
//
//        listTable.add(passedScrollPane).pad(10).expand().width(400).fillY();
//        listTable.add(failedScrollPane).pad(10).expand().width(400).fillY().row();
//
//        layout.add(listTable).expand().fill().row();

        //Csutomer try
        setupCustomerLists();


        // Close button
        TextButton closeBtn = new TextButton("Close", skin);
        closeBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                hide();
            }
        });
        layout.add(closeBtn).padTop(20).row();
    }

    private void updateBirdPosition(float delta) {
        imageX -= 200 * delta;
        if (imageX + birdImage.getWidth() < 0) {
            imageX = Gdx.graphics.getWidth();
        }
        birdImage.setPosition(imageX, birdImage.getY());

        pointImage1.setPosition(imageX + birdImage.getWidth(), pointImage1.getY());
        pointImage2.setPosition(imageX + birdImage.getWidth() + pointImage1.getWidth(), pointImage2.getY());
        pointImage3.setPosition(imageX + birdImage.getWidth() + 2 * pointImage1.getWidth(), pointImage3.getY());
    }

    private void animateGoldChange() {
        float duration = 1.0f;
        //int startGold = ServiceLocator.getLevelService().getCurrGold();
        goldLabel.addAction(Actions.sequence(
                Actions.run(() -> goldLabel.setText(String.valueOf(startGold))),
                Actions.repeat(30, Actions.run(new Runnable() {
                    private float timePassed = 0;
                    @Override
                    public void run() {
                        timePassed += duration / 30;
                        int displayGold = (int) Interpolation.linear.apply(startGold, currentGold, timePassed / duration);
                        goldLabel.setText(String.valueOf(displayGold));
                    }
                })),
                Actions.run(() -> goldLabel.setText(String.valueOf(currentGold)))
        ));
    }

    //From Team2, we made some changes here to follow the logic of the day cycle transitions
    // but please talk to me if you have any issues. We want to only close with P. Currently it
    // is opening it as well.
//    private void setupInputListener() {
//        stage.addListener(new InputListener() {
//            @Override
//            public boolean keyDown(InputEvent event, int keycode) {
//                if (keycode == com.badlogic.gdx.Input.Keys.P) {
//                    if (isVisible) {
//                        hide();
//                        return true;
//                    }
//                }
//                return false;
//            }
//        });
//    }

    public void show() {
        isVisible = true;
        layout.setVisible(isVisible);
        birdImage.setVisible(true);
        pointImage1.setVisible(true);
        pointImage2.setVisible(true);
        pointImage3.setVisible(true);
        gameScreen.pause(); // Pause the game when the display is shown

        imageX = 3 * Gdx.graphics.getWidth() / 4; // Reset image position
        birdMoveTask = new Timer.Task() {
            @Override
            public void run() {
                updateBirdPosition(Gdx.graphics.getDeltaTime());
            }
        };
        Timer.schedule(birdMoveTask, 0, 1 / 60f); // Schedule the task
        this.animateGoldChange();
    }

    public void hide() {
        /*isVisible = false;
        layout.setVisible(isVisible);
        birdImage.setVisible(false);
        pointImage1.setVisible(false);
        pointImage2.setVisible(false);
        pointImage3.setVisible(false);*/
        /*gameScreen.resume(); // Resume the game when the display is hidden*/
        ServiceLocator.getLevelService().togglePlayerFinishedLevel();
        game.setScreen(GdxGame.ScreenType.MAIN_GAME);
        ServiceLocator.getDayNightService().getEvents().trigger("TOMORAL");

        //birdMoveTask.cancel(); // Cancel the task
    }

    public void toggleVisibility() {
        if (isVisible) {
            hide();
            //From Team 2, when screen is exited, trigger listener for moral display to be shown
//            ServiceLocator.getDayNightService().getEvents().trigger("TOMORAL");

        } else {
            show();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        // draw is handled by the stage

    }

    @Override
    public void setStage(Stage mock) {

    }
}
