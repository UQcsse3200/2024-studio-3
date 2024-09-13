
package com.csse3200.game.components.player;

import java.util.ArrayList;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.components.items.PlateComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.rendering.TextureRenderComponent;

/**
 * A class that handles updating the player sprite to display what the
 * player is currently holding in their hand.
 */
public class PlayerItemSpriteManager extends Component {
    public PlayerItemSpriteManager() {
        if (entity != null) {
            // listener for when the player's InventoryComponent is updated
            entity.getEvents().addListener("updateInventory", this::update);
        }
    }

    private void updatePlayerSprite(ItemComponent item) {
        if (item != null) {
            if (item instanceof IngredientComponent) {
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
                    default:
                        //Updates player sprite back to default
                        entity.getEvents().trigger("updateAnimationEmptyInventory");
                        break;
                }
            } else {
                switch(item.getItemType()) {
                    case PLATE:
                        entity.getEvents().trigger("updateAnimationPlate");
                        break;
                    case FIREEXTINGUISHER:
                        entity.getEvents().trigger(
                                "updateAnimationFireExtinguisher");
                        break;
                }
            }
        } else {
            //Updates player sprite back to default
            entity.getEvents().trigger("updateAnimationEmptyInventory");
        }
    }

    public void update() {
        if (entity != null) {
            updatePlayerSprite(entity.getComponent(InventoryComponent.class).getItemFirst());
        }
    }
}