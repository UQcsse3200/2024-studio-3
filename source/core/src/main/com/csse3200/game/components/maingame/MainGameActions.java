package com.csse3200.game.components.maingame;

import com.csse3200.game.GdxGame;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import com.csse3200.game.components.ordersystem.RecipeNameEnums;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

/**
 * Actions on the main game
 */
public class MainGameActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(MainGameActions.class);
    private static final int ORDER_LIMIT = 8;
    private static final String[] RECIPE_NAMES = {"acaiBowl", "salad", "fruitSalad", "steakMeal", "bananaSplit"};
    private final GdxGame game;
    private final Entity ui;
    private final MainGameOrderTicketDisplay docketDisplayer;

    /**
     * MainGameActions constructor
     * @param game the GDXGame
     * @param docketUI the docket ui
     */
    public MainGameActions(GdxGame game, Entity docketUI) {
        this.game = game;
        this.ui = docketUI;
        this.docketDisplayer = docketUI.getComponent(MainGameOrderTicketDisplay.class);
    }

    /**
     * Create actions
     */
    @Override
    public void create() {
        if (entity == null) {
            throw new IllegalStateException("Entity is not initialized.");
        }
        entity.getEvents().addListener("exit", this::onExit);
        ServiceLocator.getEntityService().getEvents().addListener("createOrder", this::onCreateOrder);
        ServiceLocator.getEntityService().getEvents().addListener("createAcaiDocket", this::onCreateAcai);
        ServiceLocator.getEntityService().getEvents().addListener("createBananaDocket", this::onCreateBanana);
        ServiceLocator.getEntityService().getEvents().addListener("createSaladDocket", this::onCreateSalad);
        ServiceLocator.getEntityService().getEvents().addListener("createSteakDocket", this::onCreateSteak);
        ServiceLocator.getEntityService().getEvents().addListener("createFruitSaladDocket", this::onCreateFruitSalad);
        ServiceLocator.getEntityService().getEvents().addListener("goodEnd", this::onGoodEnd);
        ServiceLocator.getEntityService().getEvents().addListener("badEnd", this::onBadEnd);
        ServiceLocator.getEntityService().getEvents().addListener("loseEnd", this::onLoseEnd);

        ServiceLocator.getEntityService().getEvents().addListener("endDay1", this::moral1);
        ServiceLocator.getEntityService().getEvents().addListener("endDay1", this::moral2);
        ServiceLocator.getEntityService().getEvents().addListener("endDay1", this::moral3);
        ServiceLocator.getEntityService().getEvents().addListener("endDay1", this::moral4);

    }

    public void moral1(){
        logger.info("Starting end of day 1 moral decision cutscene");


        // Now we can transition to the cutscene
        game.setScreen(GdxGame.ScreenType.MORAL_SCENE_1);
    }
    public void moral2(){
        logger.info("Starting end of day 1 moral decision cutscene");

        // Stop any background tasks
        // ServiceLocator.getMainMenuDisplay().stopBackgroundTasks();

        // Now we can transition to the cutscene
        game.setScreen(GdxGame.ScreenType.MORAL_SCENE_2);
    }
    public void moral3(){
        logger.info("Starting end of day 1 moral decision cutscene");

        // Stop any background tasks
        // ServiceLocator.getMainMenuDisplay().stopBackgroundTasks();

        // Now we can transition to the cutscene
        game.setScreen(GdxGame.ScreenType.MORAL_SCENE_3);
    }
    public void moral4(){
        logger.info("Starting end of day 1 moral decision cutscene");

        // Stop any background tasks
        // ServiceLocator.getMainMenuDisplay().stopBackgroundTasks();

        // Now we can transition to the cutscene
        game.setScreen(GdxGame.ScreenType.MORAL_SCENE_4);
    }



    /**
     * Starts bad ending cutscene
     */
    public void onLoseEnd(){
        logger.info("Starting lose cutscene");


        // Now we can transition to the cutscene
        game.setScreen(GdxGame.ScreenType.LOSE_END);
    }

    /**
     * Starts bad ending cutscene
     */
    public void onBadEnd(){
        logger.info("Starting good cutscene");


        // Now we can transition to the cutscene
        game.setScreen(GdxGame.ScreenType.BAD_END);
    }

    /**
     * Starts good ending cutscene
     */
    public void onGoodEnd(){
        logger.info("Starting good cutscene");


        // Now we can transition to the cutscene
        game.setScreen(GdxGame.ScreenType.GOOD_END);
    }

    /**
     * Exit main game screen
     */
    public void onExit() {
        logger.info("Exiting main game screen");
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }


    /**
     * Creates Açai Bowl Docket
     */
    public void onCreateAcai() {
        onCreateOrder(RecipeNameEnums.ACAI_BOWL.getRecipeName());
    }

    /**
     * Creates Banana Split Docket
     */
    public void onCreateBanana() {
        onCreateOrder(RecipeNameEnums.BANANA_SPLIT.getRecipeName());
    }

    /**
     * Creates Salad Docket
     */
    public void onCreateSalad() {
        onCreateOrder(RecipeNameEnums.SALAD.getRecipeName());
    }

    /**
     * Creates Steak Docket
     */
    public void onCreateSteak() {
        onCreateOrder(RecipeNameEnums.STEAK_MEAL.getRecipeName());
    }

    /**
     * Creates Fruit Salad Docket
     */
    public void onCreateFruitSalad() {
        onCreateOrder(RecipeNameEnums.FRUIT_SALAD.getRecipeName());
    }

    /**
     * Create a docket for a recipe
     * @param preferredRecipe the name of the recipe to create a docket for
     */
    public void onCreateOrder(String preferredRecipe) {
        int orderCount = MainGameOrderTicketDisplay.getTableArrayList().size();
        if (orderCount < ORDER_LIMIT) {
            if (preferredRecipe == null || preferredRecipe.isEmpty()) {
                logger.warn("No recipe preference set. Falling back to random recipe.");
                preferredRecipe = RECIPE_NAMES[new Random().nextInt(RECIPE_NAMES.length)];
            }
            docketDisplayer.setRecipe(preferredRecipe);
            docketDisplayer.setStage(ServiceLocator.getRenderService().getStage());
            docketDisplayer.addActors();
            ServiceLocator.getEntityService().register(ui);
        } else {
            logger.info("Order limit of {} reached", ORDER_LIMIT);
        }
    }
}
