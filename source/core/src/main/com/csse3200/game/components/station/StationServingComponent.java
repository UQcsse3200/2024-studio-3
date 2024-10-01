package com.csse3200.game.components.station;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.ScoreSystem.HoverBoxComponent;
import com.csse3200.game.components.ScoreSystem.ScoreSystem;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.ordersystem.OrderActions;
import com.csse3200.game.components.ordersystem.OrderManager;
import com.csse3200.game.components.ordersystem.Recipe;
import com.csse3200.game.components.ordersystem.TicketDetails;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.services.ServiceLocator;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.MealComponent;
import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.components.npc.CustomerManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.security.Provider;

/**
 * StationServingComponent.java
 *
 * StationServingComponent gives the station the ability to submit a meal to be 
 * able to be served to a customer. This will specifically be used by the 
 * serving bench 'station'.
 *
 * {@link #submitMeal(ItemComponent)}: Function which controls the submission of a meal by the
 * class.
 *
 * This component is currently incomplete and will need to be finished.
 */
public class StationServingComponent extends Component {

    // itemHandler allows acess for serving component to see the inventory of
    // the station.
    private static final Logger logger = LoggerFactory.getLogger(StationServingComponent.class);

    private OrderActions orderActions;
    AnimationRenderComponent animator;
    private ScoreSystem scoreSystem;
    TicketDetails bigTicket;

    /**
     * On creation a listener for Submit Meal will be added to the station.
     */
    @Override
    public void create() {
        entity.getEvents().addListener("Station Interaction", this::handleInteraction);
        //orderActions = entity.getComponent(OrderActions.class);
        orderActions = ServiceLocator.getOrderActions(); // ? doesn't seem to work...
        animator = this.entity.getComponent(AnimationRenderComponent.class);
        animator.startAnimation("servery_idle");
        bigTicket = ServiceLocator.getTicketDetails();
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
                case "salad":
                    playerMeal = "salad";
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
                    logger.error("No recipe found for this item: " + itemName);
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

        //ServiceLocator.getLevelService.getCurrGold() + 2;
        ServiceLocator.getLevelService().setCurrGold(ServiceLocator.getLevelService().getCurrGold() + 10);

        String[] bigTicketInfo = bigTicket.getCurrentBigTicketInfo();

        if (bigTicketInfo[0] != null) {
            // logger.info(bigTicketInfo[0]); // order number ("5")
            // logger.info(bigTicketInfo[1]); // meal ("tomato soup")
            // logger.info(bigTicketInfo[2]); // time left ("32")

            // Call to team 1's function with the big ticket info
            //TBD(item, bigTicketInfo[0], bigTicketInfo[1], bigTicketInfo[2]);
            // remove ticket
            ServiceLocator.getDocketService().getEvents().trigger("removeOrder", -1); // removes the order from the orderaction list
            ServiceLocator.getDocketService().getEvents().trigger("removeBigTicket"); // removes the order from the display list

        } else {
            logger.info("no ticket when submitting"); // team 1 can decide if they want to handle this edge case
            return;


        }


    }

    private String scoreMeal(String playerMeal) {
        String[] bigTicketInfo = bigTicket.getCurrentBigTicketInfo();
        String scoreDescription = null;
        if (bigTicketInfo != null && bigTicketInfo.length >= 2) {
            String orderNumber = bigTicketInfo[0];
            String orderedMeal = bigTicketInfo[1];
            logger.info("Ordered meal: " + orderedMeal);
            // get order ticket ingredients
            Recipe orderRecipe = OrderManager.getRecipe(orderedMeal);
            java.util.List<String> orderIngredients = orderRecipe.getIngredients();
            logger.info("Order ingredients: " + orderIngredients);

            // get player ingredients
            // String itemName = item.getItemName();
            // String playerMeal = switch (itemName) {
            //     case "acai bowl" -> "acaiBowl";
            //     case "salad" -> "salad";
            //     case "fruit salad" -> "fruitSalad";
            //     case "steak meal" -> "steakMeal";
            //     case "banana split" -> "bananaSplit";
            //     default -> {
            //         logger.error("No recipe found for this item: " + itemName);
            //         yield null; // yield a default value or handle the error as needed
            //     }
            // };

            logger.info("Player meal: " + playerMeal);
            Recipe playerRecipe = OrderManager.getRecipe(playerMeal);
            // null because chocolate isn't a meal. so getRecipe returns a null error
            java.util.List<String> playerIngredients = playerRecipe.getIngredients();
            logger.info("Player ingredients: " + playerIngredients);

            int score = ScoreSystem.compareLists(playerIngredients, orderIngredients);
            scoreDescription = ScoreSystem.getScoreDescription(score);
            // Before scoreMeal, hoverbox display the order
            // After scoreMeal is complete, replace the order with face
            // Increment gold according to score
            logger.info("Order number: " + orderNumber);
            logger.info("Score: " + score + "%");
            logger.info("Description: " + scoreDescription);

            Entity customer = CustomerManager.getCustomerByOrder(orderNumber);
            if (customer != null) {
                HoverBoxComponent hoverBox = customer.getComponent(HoverBoxComponent.class);
                if (hoverBox != null) {
                    String faceImagePath;
                    switch (scoreDescription) {
                        case "Grin Face":
                            faceImagePath = "images/customer_faces/grin_face.png";
                            ServiceLocator.getLevelService().setCurrGold(ServiceLocator.getLevelService().getCurrGold() + 2);
                            break;
                        case "Smile Face":
                            faceImagePath = "images/customer_faces/smile_face.png";
                            ServiceLocator.getLevelService().setCurrGold(ServiceLocator.getLevelService().getCurrGold() + 1);
                            break;
                        case "Neutral Face":
                            faceImagePath = "images/customer_faces/neutral_face.png";
                            break;
                        case "Frown Face":
                            faceImagePath = "images/customer_faces/frown_face.png";
                            ServiceLocator.getLevelService().setCurrGold(ServiceLocator.getLevelService().getCurrGold() - 1);
                            break;
                        case "Angry Face":
                            faceImagePath = "images/customer_faces/angry_face.png";
                            ServiceLocator.getLevelService().setCurrGold(ServiceLocator.getLevelService().getCurrGold() - 2);
                            break;
                        default:
                            logger.error("No image found for preference: " + scoreDescription);
                            faceImagePath = "images/customer_faces/angry_face.png"; // Provide a default image
                    }
                    hoverBox.setTexture(new Texture(faceImagePath));
                }
            }

            // Remove customer from mapping after serving
            CustomerManager.removeCustomerByOrder(orderNumber);
        } else {
        logger.warn("No current order to score the meal for.");
        }
        return scoreDescription;
    }

}
