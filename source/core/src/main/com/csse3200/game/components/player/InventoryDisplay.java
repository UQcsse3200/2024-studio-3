
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
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.rendering.TextureRenderComponent;

/**
 * A UI component used to display slots of an InventoryComponent and the
 * items they hold. To use: add this component to an entity that has a
 * InventoryComponent. This component will not work if the entity
 * it is added to does not have a InventoryComponent.
 */
public class InventoryDisplay extends UIComponent {
    private Table table;

    private ArrayList<Stack> slots;


    @Override
    public void create() {
        super.create();

        if (entity != null) {
            // listener for when the InventoryComponent attached to this entity is updated
            entity.getEvents().addListener("updateInventory", this::update);
        }
        addActors();
    }

    private void addActors() {
        // Create table
        table = new Table();
        slots = new ArrayList<>();
        table.bottom().left();
        table.padBottom(10f);

        // create slot images
        for (int i = 0; i < entity.getComponent(InventoryComponent.class).getCapacity(); i++) {
            Stack currentStack = new Stack();
            slots.add(currentStack);
            currentStack.add(new Image(ServiceLocator.getResourceService().getAsset("images/inventory_ui/slot.png", Texture.class)));

            // add item image if there is an item in the slot
            ItemComponent item = entity.getComponent(InventoryComponent.class).getItemAt(i);
            if (item != null) {
                Table itemPadding = new Table();
                //N! cannot access item's parent entity from within another component
                // 3 options:
                // 1. change InventoryComponent to hold Entity types (that we assume have an ItemComponent or derivative)
                // 2. make a get texture method in ItemComponent that returns the texture component's path (this makes ItemComponent
                // now reliant on the entity also having a TextureRenderComponent
                // 3. construct the path to the image using the Item's state variables. This is difficult as there
                // are several classes that extend the ItemComponent, and thus store different variables. Most variables
                // we would require (i.e. chopped, burnt, etc) are not included in the base ItemComponent class.

                //String itemTexturePath = item.entity.getComponent(TextureRenderComponent.class).getTexturePath();
                //Image itemImage = new Image(ServiceLocator.getResourceService().getAsset(itemTexturePath, Texture.class));

                // placeholder lettuce image for now
                Image itemImage = new Image(ServiceLocator.getResourceService().getAsset("images/ingredients/raw_lettuce.png", Texture.class));
                itemPadding.add(itemImage).pad(20);
                currentStack.add(itemPadding);

            }
            table.add(currentStack).size(200).padLeft(20);

        }

        stage.addActor(table);
    }

    private void updateLabel(ItemComponent item) {
        // Update the label with the item information
        /*
        String itemText = String.format("Current Item: %s", item != null ? item.getItemName() : "None");
        if (label == null) {
            label = new Label(itemText, skin);
            label.setFontScale(2f);
        } else {
            label.setText(itemText); // Update the label text
            //Update player sprite
         */
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


    private void updateDisplay() {

        for (int i = 0; i < slots.size(); i++) {
            Stack currentStack = slots.get(i);
            currentStack.clear();
            currentStack.add(new Image(ServiceLocator.getResourceService().getAsset("images/inventory_ui/slot.png", Texture.class)));

            // add item image if there is an item in the slot
            ItemComponent item = entity.getComponent(InventoryComponent.class).getItemAt(i);
            if (item != null) {
                Table itemPadding = new Table();
                //N! See addActors

                //String itemTexturePath = item.entity.getComponent(TextureRenderComponent.class).getTexturePath();
                //Image itemImage = new Image(ServiceLocator.getResourceService().getAsset(itemTexturePath, Texture.class));

                // placeholder beef image for now
                Image itemImage = new Image(ServiceLocator.getResourceService().getAsset("images/ingredients/cooked_beef.png", Texture.class));
                itemPadding.add(itemImage).pad(20);
                currentStack.add(itemPadding);
            }
        }

        }
    // Method to update the UI with a new item
    public void update() {
        // updateLabel(entity.getComponent(InventoryComponent.class).getItemFirst());
        updateDisplay();
        updateLabel(entity.getComponent(InventoryComponent.class).getItemFirst());
    }

    @Override
    public void draw(SpriteBatch batch)  {
        // changing position on window resizing is handled by the stage
    }

    @Override
    public void dispose() {
        super.dispose();
        table.remove();
        for (Stack slot : slots) {
            slot.remove();
        }
    }

    @Override
    public void setStage(Stage mock) {
        
    }
}