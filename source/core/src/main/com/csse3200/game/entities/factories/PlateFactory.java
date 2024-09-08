package com.csse3200.game.entities.factories;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.TooltipsDisplay;
import com.csse3200.game.components.items.PlateComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.HitboxComponent;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.areas.*;
import com.csse3200.game.entities.factories.*;
import com.csse3200.game.services.ServiceLocator;

public class PlateFactory {

    /**
     * Create a template plate entity with common components.
     *
     * @return A basic plate entity.
     */
    private static Entity createTemplatePlate() {
        return new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
                .addComponent(new TooltipsDisplay())
                .addComponent(new PlateComponent());
    }

    public static Entity createPlate(int quantity) {
        if (quantity < 1 || quantity > 5) {
            // no action
        }
        String texturePath = "images/platecomponent/stackedplates/" + quantity + "plates.png";

        Entity plate = createTemplatePlate()
                .addComponent(new PlateComponent())
                .addComponent(new TextureRenderComponent(texturePath));
        PhysicsUtils.setScaledCollider(plate, 0.6f, 0.3f);
        plate.getComponent(ColliderComponent.class).setDensity(1.0f);

        return plate;
    }

    //disposePlate method but unstestable waiting for actions hotkey activated
    public static void disposePlate(Entity plate, int quantity) {
        if (quantity <= 1) {
            //plate.getComponent(TextureRenderComponent.class).setTexture(null);
            plate.dispose();
        } else {
            int newQuantity = quantity - 1;
            String newTexturePath = "images/platecomponent/stackedplates/" + newQuantity + "plates.png";

            TextureRenderComponent textureRenderComponent = plate.getComponent(TextureRenderComponent.class);
            textureRenderComponent.setTexture(null);
            textureRenderComponent.setTexture(newTexturePath);
        }
    }

    public static Entity spawnMealOnPlate(String mealType) {

        String mealTexturePath = getMealTexturePath(mealType);
        if (mealTexturePath == null) {
            //no meal
        }

        Entity plate = createTemplatePlate()
                .addComponent(new PlateComponent())
                .addComponent(new TextureRenderComponent(mealTexturePath));
        PhysicsUtils.setScaledCollider(plate, 0.6f, 0.3f);
        plate.getComponent(ColliderComponent.class).setDensity(1.0f);

        return plate;
    }

    private static String getMealTexturePath(String mealType) {
        return switch (mealType.toLowerCase()) {
            case "acai bowl" -> "images/meals/acai_bowl.png";
            case "banana split" -> "images/meals/banana_split.png";
            case "fruit salad" -> "images/meals/fruit_salad.png";
            case "salad" -> "images/meals/salad.png";
            case "steak" -> "images/meals/steak_meal.png";
            default -> null;
        };
    }


}