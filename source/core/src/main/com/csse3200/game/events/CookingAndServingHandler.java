package com.csse3200.game.events;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.services.GameTime;

public class CookingAndServingHandler extends Component {

    private IngredientComponent ingredient;
    private TextureRenderComponent textureRender;
    private final GameTime timeSource;
    private boolean isCooking;
    private long cookStartTime;
    private boolean isServed;

    public boolean isCooking() {
        return isCooking;
    }

    public CookingAndServingHandler(GameTime timeSource) {
        this.timeSource = timeSource;
    }

    public void create() {

        ingredient = entity.getComponent(IngredientComponent.class);
        textureRender = entity.getComponent(TextureRenderComponent.class);

        if (ingredient == null || textureRender == null) {
            System.out.println("IngredientComponent or TextureRenderComponent is missing on the entity.");
            return;
        }
        updateTexture();
    }

    public void startCooking() {

        if (ingredient != null && ingredient.getItemState().equals("raw")) {
            isCooking = true;
            cookStartTime = timeSource.getTime();
        } else {
            System.out.println("Item is already cooked or burnt.");
        }
    }

    public void stopCooking() {
        isCooking = false;
        System.out.println("Finished cooking " + ingredient.getItemName());
    }

    private void updateTexture() {

        String texturePath;

        switch (ingredient.getItemState()) {
            case "cooked":
                texturePath = String.format("images/cooked_%s.png", ingredient.getItemName().toLowerCase());
                break;
            case "burnt":
                texturePath = String.format("images/burnt_%s.png", ingredient.getItemName().toLowerCase());
            case "raw":
            default:
                texturePath = String.format("images/raw_%s.png", ingredient.getItemName().toLowerCase());
                break;
        }

        /**
         * Dynamically updates textures for items based on their cooking state.
         */
        textureRender.setTexture(texturePath);
    }

    public void serveMeal() {
        if (ingredient != null && ingredient.getItemState().equals("cooked")) {
            isServed = true;
            System.out.println(ingredient.getItemName() + " has been served to the customer.");

            } else {
            System.out.println("Meal cannot be served yet. It is not ready.");

        }
    }
    public void deleteMeal() {
        /**
         * Disposes of meals once served to customers.
         */
        if (isServed) {
            entity.dispose();
            System.out.println("Meal Entity has served and removed from the game");
        } else {
            System.out.println("Meal has not been served yet so it cannot be removed.");
        }
    }

    public void updateState() {

        if (isCooking && ingredient != null) {
            long currentTime = timeSource.getTime();
            long elapsedTime = currentTime - cookStartTime;
            /**
             * Check if it's time to change the item's state
             */
            if (elapsedTime >= ingredient.getCookTime() * 1000L + 1500) {

                if (!ingredient.getItemState().equals("burnt")) {
                    ingredient.burnItem();
                    System.out.println(ingredient.getItemName() + " has burnt!");
                    updateTexture();
                    stopCooking();
                }
                /**
                 * Need to implement a method to stop cooking the item/ingredient.
                 */
            } else if (elapsedTime >= ingredient.getCookTime() * 1000L) {

                /**
                 * Item has been cooked if the cooking time has passed.
                 */
                if (!ingredient.getItemState().equals("cooked")) {
                    ingredient.cookItem();
                    updateTexture();

                }
            }
        }
    }
    public boolean isServed() {
        return isServed;
    }
}
