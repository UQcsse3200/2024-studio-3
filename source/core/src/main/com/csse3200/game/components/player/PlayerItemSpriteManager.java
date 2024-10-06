
package com.csse3200.game.components.player;

import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.ItemComponent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * A class that handles updating the player sprite to display what the
 * player is currently holding in their hand.
 */
public class PlayerItemSpriteManager extends Component {

    // Logger for this class
    private static final Logger LOGGER = LoggerFactory.getLogger(PlayerItemSpriteManager.class);

    /**
     * Updates the player sprite to match what is currently being held in
     * the player's InventoryComponent
     */
    //@Override
    public void updateInventory() {
        updatePlayerSprite(entity.getComponent(InventoryComponent.class).getItemFirst());
    }

    /**
     * Add the event listener to the player item sprite manager
     */
    @Override
    public void create() {
        // listener for when the player's InventoryComponent is updated
        entity.getEvents().addListener("updateInventory", this::updateInventory);
        LOGGER.info("PlayerItemSpriteManager created");
    }

    /**
     * Updates the player sprite to show it holding the item provided.
     * @param item The item to show the player sprite holding.
     */
    private void updatePlayerSprite(ItemComponent item) {
        if (item == null) { // All items should have this?????
            //Updates player sprite to default
            entity.getEvents().trigger("updateAnimationEmptyInventory");
            LOGGER.info("PlayerItemSpriteManager animation updated");
            return;
        }

        // Update the animation based on the item and its state
        switch (item.getItemType()) {
            case ItemType.ACAI:
                //Updates player sprite back to hold fish (raw or chopped)
                if (((IngredientComponent) item).getItemState().equals("raw")) {
                    entity.getEvents().trigger("updateAnimationRawAcai");
                } else {
                    entity.getEvents().trigger("updateAnimationChoppedAcai");
                }
                break;
            case ItemType.BEEF:
                //Updates player sprite back to hold fish (raw, cooked, burnt)
                if (((IngredientComponent) item).getItemState().equals("raw")) {
                    entity.getEvents().trigger("updateAnimationRawBeef");
                } else if (((IngredientComponent) item).getItemState().equals("cooked")) {
                    entity.getEvents().trigger("updateAnimationCookedBeef");
                } else {
                    entity.getEvents().trigger("updateAnimationBurntBeef");
                }
                break;
            case ItemType.BANANA:
                //Updates player sprite back to hold fish (raw or chopped)
                if (((IngredientComponent) item).getItemState().equals("raw")) {
                    entity.getEvents().trigger("updateAnimationRawBanana");
                } else {
                    entity.getEvents().trigger("updateAnimationChoppedBanana");
                }
                break;
            case ItemType.LETTUCE:
                //Updates player sprite back to hold fish (raw or chopped)
                if (((IngredientComponent) item).getItemState().equals("raw")) {
                    entity.getEvents().trigger("updateAnimationRawLettuce");
                } else {
                    entity.getEvents().trigger("updateAnimationChoppedLettuce");
                }
                break;
            case ItemType.CUCUMBER:
                //Updates player sprite back to hold fish (raw or chopped)
                if (((IngredientComponent) item).getItemState().equals("raw")) {
                    entity.getEvents().trigger("updateAnimationRawCucumber");
                } else {
                    entity.getEvents().trigger("updateAnimationChoppedCucumber");
                }
                break;
            case ItemType.TOMATO:
                //Updates player sprite back to hold fish (raw or chopped)
                if (((IngredientComponent) item).getItemState().equals("raw")) {
                    entity.getEvents().trigger("updateAnimationRawTomato");
                } else {
                    entity.getEvents().trigger("updateAnimationChoppedTomato");
                }
                break;
            case ItemType.STRAWBERRY:
                //Updates player sprite back to hold fish (raw or chopped)
                if (((IngredientComponent) item).getItemState().equals("raw")) {
                    entity.getEvents().trigger("updateAnimationRawStrawberry");
                } else {
                    entity.getEvents().trigger("updateAnimationChoppedStrawberry");
                }
                break;
            case ItemType.CHOCOLATE:
                //Updates player sprite back to hold fish (raw or chopped)
                if (((IngredientComponent) item).getItemState().equals("raw")) {
                    entity.getEvents().trigger("updateAnimationRawChocolate");
                } else {
                    entity.getEvents().trigger("updateAnimationChoppedChocolate");
                }
                break;
            case ItemType.FISH:
                //Updates player sprite back to hold fish (raw or cooked)
                if (((IngredientComponent) item).getItemState().equals("raw")) {
                    entity.getEvents().trigger("updateAnimationRawFish");
                } else {
                    entity.getEvents().trigger("updateAnimationCookedFish");
                }
                break;
            case ItemType.ACAIBOWL:
                //Updates player sprite back to hold acai bowl
                entity.getEvents().trigger("updateAnimationAcaiBowl");
                break;
            case ItemType.BANANASPLIT:
                //Updates player sprite back to hold banana split
                entity.getEvents().trigger("updateAnimationBananaSplit");
                break;
            case ItemType.FRUITSALAD:
                //Updates player sprite back to hold fruit salad
                entity.getEvents().trigger("updateAnimationFruitSalad");
                break;
            case ItemType.SALAD:
                //Updates player sprite back to hold salad
                entity.getEvents().trigger("updateAnimationSalad");
                break;
            case ItemType.STEAKMEAL:
                //Updates player sprite back to hold steak meal
                entity.getEvents().trigger("updateAnimationSteak");
                break;
            case ItemType.PLATE:
                entity.getEvents().trigger("updateAnimationPlate");
                break;
            case ItemType.FIREEXTINGUISHER:
                entity.getEvents().trigger("updateAnimationFireExtinguisher");
                break;
            default:
                //Updates player sprite back to default
                entity.getEvents().trigger("updateAnimationEmptyInventory");
                break;
        }
        LOGGER.info("PlayerItemSpriteManager animation updated");
    }

}