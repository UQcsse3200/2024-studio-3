
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
    public void onUpdateInventory() {
        updatePlayerSprite(entity.getComponent(InventoryComponent.class).getItemFirst());
    }

    /**
     * Add the event listener to the player item sprite manager
     */
    @Override
    public void create() {
        // listener for when the player's InventoryComponent is updated
        entity.getEvents().addListener("updateInventory", this::onUpdateInventory);
        LOGGER.info("PlayerItemSpriteManager created");
    }

    /**
     * Updates the player sprite to show it holding the item provided.
     * @param item The item to show the player sprite holding.
     */
    private void updatePlayerSprite(ItemComponent item) {
        if (item == null) {
            triggerDefaultAnimation();
            return;
        }

        switch (item.getItemType()) {
            case ItemType.ACAI, ItemType.BANANA, ItemType.LETTUCE, ItemType.CUCUMBER, ItemType.TOMATO,
                 ItemType.STRAWBERRY, ItemType.CHOCOLATE:
                handleRawOrChoppedAnimation((IngredientComponent) item);
                break;
            case ItemType.BEEF:
                handleBeefAnimation((IngredientComponent) item);
                break;
            case ItemType.FISH:
                handleFishAnimation((IngredientComponent) item);
                break;
            case ItemType.ACAIBOWL:
                triggerAnimation("updateAnimationAcaiBowl");
                break;
            case ItemType.BANANASPLIT:
                triggerAnimation("updateAnimationBananaSplit");
                break;
            case ItemType.FRUITSALAD:
                triggerAnimation("updateAnimationFruitSalad");
                break;
            case ItemType.SALAD:
                triggerAnimation("updateAnimationSalad");
                break;
            case ItemType.STEAKMEAL:
                triggerAnimation("updateAnimationSteak");
                break;
            case ItemType.PLATE:
                triggerAnimation("updateAnimationPlate");
                break;
            case ItemType.FIREEXTINGUISHER:
                triggerAnimation("updateAnimationFireExtinguisher");
                break;
            default:
                triggerDefaultAnimation();
                break;
        }
        LOGGER.info("PlayerItemSpriteManager animation updated");
    }

    private void handleRawOrChoppedAnimation(IngredientComponent item) {
        String itemType = item.getItemType().toString().toLowerCase();
        String itemState = item.getItemState();
        if (itemState.equals("raw")) {
            triggerAnimation("updateAnimationRaw" + capitalize(itemType));
        } else {
            triggerAnimation("updateAnimationChopped" + capitalize(itemType));
        }
    }

    private void handleBeefAnimation(IngredientComponent item) {
        String itemState = item.getItemState();
        if (itemState.equals("raw")) {
            triggerAnimation("updateAnimationRawBeef");
        } else if (itemState.equals("cooked")) {
            triggerAnimation("updateAnimationCookedBeef");
        } else {
            triggerAnimation("updateAnimationBurntBeef");
        }
    }

    private void handleFishAnimation(IngredientComponent item) {
        if (item.getItemState().equals("raw")) {
            triggerAnimation("updateAnimationRawFish");
        } else {
            triggerAnimation("updateAnimationCookedFish");
        }
    }

    private void triggerAnimation(String animation) {
        entity.getEvents().trigger(animation);
    }

    private void triggerDefaultAnimation() {
        triggerAnimation("updateAnimationEmptyInventory");
    }

    private String capitalize(String str) {
        return str.substring(0, 1).toUpperCase() + str.substring(1);
    }
}