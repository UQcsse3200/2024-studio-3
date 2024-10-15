package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.ScoreSystem.HoverBoxComponent;
import com.csse3200.game.components.ScoreSystem.ScoreSystem;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.ordersystem.OrderManager;
import com.csse3200.game.components.ordersystem.Recipe;
import com.csse3200.game.components.ordersystem.TicketDetails;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.components.player.PlayerStatsDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.services.ServiceLocator;
import com.badlogic.gdx.graphics.Texture;
import com.csse3200.game.components.npc.CustomerManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

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
    private final String SALAD = "salad";
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
            scoreMeal(playerMeal);
            playerInventoryComponent.removeAt(0);
            inventoryDisplay.update();
            submitMeal(item);
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

            // Call to team 1's function with the big ticket info
            //TBD(item, bigTicketInfo[0], bigTicketInfo[1], bigTicketInfo[2]);
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
     * @param playerMeal - the meal that the player is currently submitting to the customer.
     */
    private void scoreMeal(String playerMeal) {
        String[] bigTicketInfo = bigTicket.getCurrentBigTicketInfo();
        if (bigTicketInfo == null || bigTicketInfo.length < 2) {
            logger.warn("No current order to score the meal for.");
            return;
        }

        String orderNumber = bigTicketInfo[0];
        String orderedMeal = bigTicketInfo[1];
        logger.info("Ordered meal: {}", orderedMeal);

        Recipe orderRecipe = OrderManager.getRecipe(orderedMeal);
        List<String> orderIngredients = orderRecipe.getIngredients();
        logger.info("Order ingredients: {}", orderIngredients);

        int orderedMealPrice = getMealPrice(orderedMeal);

        logger.info("Player meal: {}", playerMeal);
        Recipe playerRecipe = OrderManager.getRecipe(playerMeal);
        List<String> playerIngredients = playerRecipe.getIngredients();
        logger.info("Player ingredients: {}", playerIngredients);

        int score = ScoreSystem.compareLists(playerIngredients, orderIngredients);
        String scoreDescription = ScoreSystem.getScoreDescription(score);

        logOrderDetails(orderNumber, score, scoreDescription);

        Entity customer = CustomerManager.getCustomerByOrder(orderNumber);
        if (customer != null) {
            processCustomerReaction(customer, scoreDescription, orderedMealPrice);
        }

        CustomerManager.removeCustomerByOrder(orderNumber);
    }

    private int getMealPrice(String orderedMeal) {
        return switch (orderedMeal) {
            case "acaiBowl", "fruitSalad" -> 20;
            case SALAD, "bananaSplit" -> 25;
            case "steakMeal" -> 40;
            default -> 10;
        };
    }

    private void logOrderDetails(String orderNumber, int score, String scoreDescription) {
        logger.info("Order number: {}", orderNumber);
        logger.info("Score: {}%", score);
        logger.info("Description: {}", scoreDescription);
    }

    private void processCustomerReaction(Entity customer, String scoreDescription, int mealPrice) {
        HoverBoxComponent hoverBox = customer.getComponent(HoverBoxComponent.class);
        if (hoverBox == null) return;

        String faceImagePath = getFaceImagePath(scoreDescription);
        int gold = updateGoldBasedOnScore(ServiceLocator.getLevelService().getCurrGold(), scoreDescription, mealPrice);

        hoverBox.setTexture(new Texture(faceImagePath));
        ServiceLocator.getLevelService().setCurrGold(gold);
        updateGoldUI(gold);
    }

    private String getFaceImagePath(String scoreDescription) {
        return switch (scoreDescription) {
            case "Grin Face" -> "images/customer_faces/grin_face.png";
            case "Smile Face" -> "images/customer_faces/smile_face.png";
            case "Neutral Face" -> "images/customer_faces/neutral_face.png";
            case "Frown Face" -> "images/customer_faces/frown_face.png";
            case "Angry Face" -> "images/customer_faces/angry_face.png";
            default -> {
                logger.error("No image found for preference: {}", scoreDescription);
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

    private int updateGoldBasedOnScore(int currentGold, String scoreDescription, int mealPrice) {
        int gold = currentGold + mealPrice;
        switch (scoreDescription) {
            case "Grin Face" -> gold += 10 * goldMultiplier;
            case "Smile Face" -> gold += 5 * goldMultiplier;
            case "Frown Face" -> gold -= 5 * goldMultiplier;
            case "Angry Face" -> gold -= 10 * goldMultiplier;
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
