
package com.csse3200.game.components.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.items.ItemComponent;

public class InventoryDisplay extends UIComponent {
    private Table table;
    private Label label;

    @Override
    public void create() {
        super.create();
        addActors();
    }

    private void addActors() {
        // Create table
        table = new Table();
        table.bottom().left();
        table.padTop(30f).padLeft(10f);

        // Add an image
        ItemComponent item = entity.getComponent(InventoryComponent.class).getItemFirst();
        CharSequence itemText = String.format("Current Item: %s", item);
        label = new Label(itemText, skin);
        label.setFontScale(2f);

        table.add(label).pad(5);
        stage.addActor(table);
    }

    private void updateLabel(ItemComponent item) {
        // Update the label with the item information
        String itemText = String.format("Current Item: %s", item != null ? item.getItemName() : "None");
        if (label == null) {
            label = new Label(itemText, skin);
            label.setFontScale(2f);
        } else {
            label.setText(itemText); // Update the label text
            //Update player sprite
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
                }
            } else {
                //Updates player sprite back to default
                entity.getEvents().trigger("updateAnimationEmptyInventory");
            }
        }

    }

    // Method to update the UI with a new item
    public void update() {
        updateLabel(entity.getComponent(InventoryComponent.class).getItemFirst());
    }

    @Override
    public void draw(SpriteBatch batch)  {
        // changing position on window resizing is handled by the stage
    }

    @Override
    public void dispose() {
        super.dispose();
        label.remove();
    }

    @Override
    public void setStage(Stage mock) {
        
    }
}