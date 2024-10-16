package com.csse3200.game.components.station;

import com.csse3200.game.ai.tasks.AITaskComponent;
import com.csse3200.game.ai.tasks.Task;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.ScoreSystem.HoverBoxComponent;
import com.csse3200.game.components.ScoreSystem.ScoreSystem;
import com.csse3200.game.components.items.ChopIngredientComponent;
import com.csse3200.game.components.items.CookIngredientComponent;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.items.ItemTimerComponent;
import com.csse3200.game.components.items.MealComponent;
import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import com.csse3200.game.components.ordersystem.OrderManager;
import com.csse3200.game.components.ordersystem.Recipe;
import com.csse3200.game.components.ordersystem.TicketDetails;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.components.player.PlayerStatsDisplay;
import com.csse3200.game.components.tasks.PathFollowTask;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.services.ServiceLocator;
import com.badlogic.gdx.graphics.Texture;
import com.csse3200.game.components.npc.CustomerManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.ArrayList;

/**
 * StationServingComponent.java
 * StationServingComponent gives the station the ability to submit a meal to be 
 * able to be served to a customer. This will specifically be used by the 
 * serving bench 'station'.
 * {@link #submitMeal(ItemComponent)}: Function which controls the submission of a meal by the
 * class.
 * This component is currently incomplete and will need to be finished.
 */
public class StationServingComponent extends Component {

    // itemHandler allows access for serving component to see the inventory of
    // the station.
    private static final Logger logger = LoggerFactory.getLogger(StationServingComponent.class);
    AnimationRenderComponent animator;
    TicketDetails bigTicket;
    private int goldMultiplier = 1;
    private static final String SALAD = "salad";
    private boolean IsExtortion = false;

    /**
     * On creation a listener for Submit Meal will be added to the station.
     */
    @Override
    public void create() {
        entity.getEvents().addListener("Station Interaction", this::handleInteraction);
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        animator.startAnimation("servery_idle");
        bigTicket = ServiceLocator.getTicketDetails();
        ServiceLocator.getRandomComboService().getEvents().addListener("extortion active", ()->
        {IsExtortion = true;});
        ServiceLocator.getRandomComboService().getEvents().addListener("extortion unactive", ()->
        {IsExtortion = false;});
    }

    public boolean canSubmitMeal(ItemComponent item) {
        String itemName = item.getItemName();
        switch (itemName) {
            case "acai bowl", SALAD, "fruit salad", "steak meal", "banana split":
                return true;
            default:
                return false;
        }
    }

    /**
     * Handles any interaction with station, using current state of player and station
     * inventory to determine intended interaction
     * @param playerInventoryComponent reference to player inventory component
     * @param inventoryDisplay reference to individual inventory display
     * @param type the type of interaction attempt
     */
    public void handleInteraction(InventoryComponent playerInventoryComponent, InventoryDisplay inventoryDisplay, String type) {
        if (playerInventoryComponent.isFull()) {
            ItemComponent item = playerInventoryComponent.getItemFirst();
            String itemName = item.getItemName();
            String playerMeal;
            switch (itemName) {
                case "acai bowl":
                    playerMeal = "acaiBowl";
                    break;
                case SALAD:
                    playerMeal = SALAD;
                    break;
                case "fruit salad":
                    playerMeal = "fruitSalad";
                    break;
                case "steak meal":
                    playerMeal = "steakMeal";
                    break;
                case "banana split":
                    playerMeal = "bananaSplit";
                    break;
                default:
                    logger.error("No recipe found for this item: {}", itemName);
                    return; // Exit the method
            }
            if (!checkSubmit(item)) {
                return;
            }
            scoreMeal(playerMeal, item);
            playerInventoryComponent.removeAt(0);
            inventoryDisplay.update();
            submitMeal(item);
        }
    }

    public Boolean checkSubmit(ItemComponent item) {
        if (MainGameOrderTicketDisplay.getTableArrayList().isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Function which calls to identify the current ticket info, as well as the item in the inventory
     * Calls another function which grades the submission
     * @param item reference to the item being submitted by the user
     */
    public void submitMeal(ItemComponent item) {

        String[] bigTicketInfo = bigTicket.getCurrentBigTicketInfo();
        if (bigTicketInfo[0] != null) {

            // Triggering customer leave for successful meal submission
            Entity customer = CustomerManager.getCustomerByOrder(bigTicketInfo[0]);
            if (customer != null) {
                ServiceLocator.getEntityService().getEvents().trigger("customerPassed",
                        customer.getComponent(CustomerComponent.class).getName());
                AITaskComponent aiTaskComponent = customer.getComponent(AITaskComponent.class);
                if (aiTaskComponent != null) {
                    Task highestPriorityTask = aiTaskComponent.getHighestPriorityTask();
                    if (highestPriorityTask instanceof PathFollowTask pathFollowTask) {
                        pathFollowTask.triggerMoveToPredefinedPosition();
                    }
                }
            }

            CustomerManager.removeCustomerByOrder(bigTicketInfo[0]);
            CustomerManager.printMessage();

            // Call to team 1's function with the big ticket info
            // remove ticket
            logger.info("Submitting meal and removing docket");
            ServiceLocator.getDocketService().getEvents().trigger("removeOrder", -1); // removes the order from the order action list
            ServiceLocator.getDocketService().getEvents().trigger("removeBigTicket"); // removes the order from the display list


        } else {
            logger.info("no ticket when submitting"); // team 1 can decide if they want to handle this edge case
        }


    }

    /**
     * Function that is called to score the meal.
     * Updates the HoverBoxComponent to display customer satisfaction by face types.
     * Increments the gold and updates the gold in the UI according to customer satisfaction and actual ordered meal price.
     *
     * @param playerMeal - the name of the meal that the player is currently submitting to the customer.
     * @param meal - the meal item that the player is submitting to the customer.
     */
    private void scoreMeal(String playerMeal, ItemComponent meal) {
        String[] bigTicketInfo = bigTicket.getCurrentBigTicketInfo();
        if (bigTicketInfo == null || bigTicketInfo.length < 2) {
            logger.warn("No current order to score the meal for.");
            return;
        }

        String orderNumber = bigTicketInfo[0];
        String orderedMeal = bigTicketInfo[1];

        // Retrieves the ingredients of the customer's order
        Recipe orderRecipe = OrderManager.getRecipe(orderedMeal);
        List<String> orderIngredients = orderRecipe.getIngredients();

        // Retrieves the ingredients of the player's meal
        Recipe playerRecipe = OrderManager.getRecipe(playerMeal);
        List<String> playerIngredients = playerRecipe.getIngredients();

        // Determines the accuracy of the player's meal against the customer's order
        int accuracyScore = ScoreSystem.getAccuracyScore(playerIngredients, orderIngredients);

        // Retrieves the time remaining when the meal is submitted
        // Then determines the score based on the time remaining
        String orderTime = bigTicketInfo[2];
        int timeScore = ScoreSystem.getTimeScore(orderTime);

        // Retrieves the completion percentages of the ingredients in the player's meal
        // Then determines the score based on how 'cooked' or 'chopped' the ingredients are
        List<Float> completionList = getCompletionList(meal);
        int completionScore = ScoreSystem.getCompletionScore(completionList);

        // Determines the final score based on the three previous scoring criteria
        String finalScore = ScoreSystem.getFinalScore(accuracyScore, timeScore, completionScore);
        logger.info(finalScore);

        // Updating the gold amount and the HoverBox display based on the final score
        Entity customer = CustomerManager.getCustomerByOrder(orderNumber);
        int orderedMealPrice = getMealPrice(orderedMeal);
        if (customer != null) {
            processCustomerReaction(customer, finalScore, orderedMealPrice);
        }
    }

    /**
     * Function that is called to retrieve the completion statuses of the ingredients used in the player's meal.
     *
     * @param meal - the meal item that the player is submitting to the customer.
     */
    private List<Float> getCompletionList(ItemComponent meal) {
        List<Float> ingredientCompletionList = new ArrayList<>();
        if (meal instanceof MealComponent) {
            MealComponent mealComponent = (MealComponent) meal;
            List<IngredientComponent> ingredients = mealComponent.getIngredients();
            for (IngredientComponent ingredient : ingredients) {
                logger.info(ingredient.getItemName());
                Entity ingredientEntity = ingredient.getEntity();
                ItemTimerComponent timerComponent = null;

                if (ingredientEntity != null) {

                    // Check for ChopIngredientComponent
                    if (ingredientEntity.getComponent(ChopIngredientComponent.class) != null) {
                        timerComponent = ingredientEntity.getComponent(ChopIngredientComponent.class);
                    }
                    // Check for CookIngredientComponent
                    else if (ingredientEntity.getComponent(CookIngredientComponent.class) != null) {
                        timerComponent = ingredientEntity.getComponent(CookIngredientComponent.class);
                    }
                }

                Float ingredientCompletionPercent = timerComponent.getCompletionPercent();
                ingredientCompletionList.add(ingredientCompletionPercent);
            }
        } else {
            logger.error("The provided meal is not a MealComponent.");
        }
        return ingredientCompletionList;
    }

    /**
     * Function that is called to retrieve the meal price.
     *
     * @param orderedMeal - the name of the meal that the player is currently submitting to the customer.
     */
    private int getMealPrice(String orderedMeal) {
        return switch (orderedMeal) {
            case "acaiBowl", "fruitSalad" -> 20;
            case SALAD, "bananaSplit" -> 25;
            case "steakMeal" -> 40;
            default -> 10;
        };
    }

    /**
     * Function that is called to process the customer reaction, and increments the gold.
     *
     * @param customer - the customer entity.
     * @param finalScore - the final score that is assigned to a meal.
     * @param mealPrice - the price of the meal.
     */
    private void processCustomerReaction(Entity customer, String finalScore, int mealPrice) {
        HoverBoxComponent hoverBox = customer.getComponent(HoverBoxComponent.class);
        if (hoverBox == null) return;

        String faceImagePath = getFaceImagePath(finalScore);
        int gold = updateGoldBasedOnScore(ServiceLocator.getLevelService().getCurrGold(), finalScore, mealPrice);

        hoverBox.setTexture(new Texture(faceImagePath));
        ServiceLocator.getLevelService().setCurrGold(gold);
        updateGoldUI(gold);
    }

    /**
     * Function that is called to retrieve the path to update the HoverBox Display.
     *
     * @param finalScore - the final score that is assigned to a meal.
     */
    private String getFaceImagePath(String finalScore) {
        return switch (finalScore) {
            case "Grin Face" -> "images/customer_faces/grin_face.png";
            case "Smile Face" -> "images/customer_faces/smile_face.png";
            case "Neutral Face" -> "images/customer_faces/neutral_face.png";
            case "Frown Face" -> "images/customer_faces/frown_face.png";
            case "Angry Face" -> "images/customer_faces/angry_face.png";
            default -> {
                logger.error("No image found for preference: {}", finalScore);
                yield "images/customer_faces/angry_face.png"; // Provide a default image
            }
        };
    }

    /**
     * Sets the gold multiplier
     * @param multiplier the multiplier desired
     */
    public void setGoldMultiplier(int multiplier) {
        goldMultiplier = multiplier;
    }

    /**
     * Function that is called to update the gold based on the meal price.
     *
     * @param currentGold - the current amount of gold the player has.
     * @param finalScore - the final score that is assigned to a meal.
     * @param mealPrice - the price of the meal.
     */
    private int updateGoldBasedOnScore(int currentGold, String finalScore, int mealPrice) {
        int gold = currentGold + mealPrice;
        switch (finalScore) {
            case "Grin Face" -> gold += 10 * goldMultiplier;
            case "Smile Face" -> gold += 5 * goldMultiplier;
            case "Frown Face" -> gold -= 5 * goldMultiplier;
            case "Angry Face" -> gold -= 10 * goldMultiplier;
            default -> gold += 0;
        }
        return gold;
    }


    /**
         * Function that is called to player gold UI.
         * Calls another method from PlayerStatsDisplay.java to update the gold.
         * @param gold - the amount of gold the player currently have after serving the customer.
         */
        private void updateGoldUI(int gold) {
        PlayerStatsDisplay playerStatsDisplay = PlayerStatsDisplay.getInstance();
        if (playerStatsDisplay != null) {
            if(IsExtortion){
                playerStatsDisplay.updatePlayerGoldUI(2*gold);
            }
            else{
                playerStatsDisplay.updatePlayerGoldUI(gold);
            }
        } else {
            logger.error("PlayerStatsDisplay instance is null");
        }
    }

}
