package com.csse3200.game.components.maingame;

import com.csse3200.game.GdxGame;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import com.csse3200.game.components.ordersystem.RecipeNameEnums;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.UIFactory;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class MainGameActions extends Component {
    private static final Logger logger = LoggerFactory.getLogger(MainGameActions.class);
    private static final int ORDER_LIMIT = 8; // Maximum number of orders allowed
    private static final String[] RECIPE_NAMES = {"acaiBowl", "salad", "fruitSalad", "steakMeal", "bananaSplit"};
    private GdxGame game;
    private Entity ui = UIFactory.createDocketUI();
    private final MainGameOrderTicketDisplay docketDisplayer = ui.getComponent(MainGameOrderTicketDisplay.class);

    // List to keep track of currently spawning animals
    private static List<String> currentlySpawningAnimals = new CopyOnWriteArrayList<>();

    public MainGameActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("exit", this::onExit);
        ServiceLocator.getEntityService().getEvents().addListener("createOrder", this::onCreateOrder);
        ServiceLocator.getEntityService().getEvents().addListener("createAcaiDocket", this::onCreateAcai);
        ServiceLocator.getEntityService().getEvents().addListener("createBananaDocket", this::onCreateBanana);
        ServiceLocator.getEntityService().getEvents().addListener("createSaladDocket", this::onCreateSalad);
        ServiceLocator.getEntityService().getEvents().addListener("createSteakDocket", this::onCreateSteak);
        ServiceLocator.getEntityService().getEvents().addListener("createFruitSaladDocket", this::onCreateFruitSalad);
        entity.getEvents().addListener("orderDone", this::onOrderDone);
    }

    private void onExit() {
        logger.info("Exiting main game screen");
        game.setScreen(GdxGame.ScreenType.MAIN_MENU);
    }

    private void onCreateAcai() {
        onCreateOrder(RecipeNameEnums.ACAI_BOWL.getRecipeName());
    }private void onCreateBanana() {
        onCreateOrder(RecipeNameEnums.BANANA_SPLIT.getRecipeName());
    }private void onCreateSalad() {
        onCreateOrder(RecipeNameEnums.SALAD.getRecipeName());
    }private void onCreateSteak() {
        onCreateOrder(RecipeNameEnums.STEAK_MEAL.getRecipeName());
    }private void onCreateFruitSalad() {
        onCreateOrder(RecipeNameEnums.FRUIT_SALAD.getRecipeName());
    }

    private void onCreateOrder(String preferredRecipe) {
        int orderCount = MainGameOrderTicketDisplay.getTableArrayList().size();
        if (orderCount < ORDER_LIMIT) {
//            String preferredRecipe = getPreferredRecipeFromSpawningAnimals();
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

    private String getPreferredRecipeFromSpawningAnimals() {
        for (String animalName : currentlySpawningAnimals) {
            String preference = getRecipePreferenceForAnimal(animalName);
            if (preference != null && !preference.isEmpty()) {
                return preference;
            }
        }
        return null;
    }

    private String getRecipePreferenceForAnimal(String animalName) {
        // Iterate over all entities to find the one with the matching name
        for (Entity entity : ServiceLocator.getEntityService().getEntities()) {
            CustomerComponent customerComponent = entity.getComponent(CustomerComponent.class);

            if (customerComponent != null && animalName.equals(customerComponent.getName())) {
                return customerComponent.getPreference();
            }
        }

        logger.warn("Entity with name '{}' not found", animalName);
        return null;
    }

    private void onOrderDone() {
        if (ui != null) {
            ServiceLocator.getEntityService().unregister(ui);
            ui.dispose();
            ui = null;
            logger.info("Order entity disposed");
        }
    }

    public static void addSpawningAnimal(String animalName) {
        if (!currentlySpawningAnimals.contains(animalName)) {
            currentlySpawningAnimals.add(animalName);
        }
    }

    public static void removeSpawningAnimal(String animalName) {
        currentlySpawningAnimals.remove(animalName);
    }
}
