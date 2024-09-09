package com.csse3200.game.entities.factories;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class PlateFactory {
    private static final Logger logger = LoggerFactory.getLogger(PlateFactory.class);

    public static Entity createTemplatePlate() {
        return new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
                .addComponent(new TooltipsDisplay());
    }

    public static Entity spawnPlate(int id) {
        String texturePath = "images/platecomponent/stackedplates/1plates.png";

        Entity singlePlate = createTemplatePlate()
                .addComponent(new PlateComponent(1))
                .addComponent(new TextureRenderComponent(texturePath));

        PlateComponent plateComponent = singlePlate.getComponent(PlateComponent.class);
        plateComponent.setStacked(false);
        plateComponent.setId(id);

        PhysicsUtils.setScaledCollider(singlePlate, 0.5f, 0.25f);
        singlePlate.getComponent(ColliderComponent.class).setDensity(1.0f);
        singlePlate.getComponent(InteractionComponent.class).setAsBox(singlePlate.getScale());
        singlePlate.getComponent(InteractionComponent.class).create();
        singlePlate.getComponent(InteractionComponent.class).getFixture().setUserData(singlePlate);

        //logger.info("Single plate id: {}", plateComponent.getId());

        return singlePlate;
    }


    public static Entity spawnPlateStack(int quantity) {
        if (quantity < 1 || quantity > 5) {
            //noone
        }

        String texturePath = "images/platecomponent/stackedplates/" + quantity + "plates.png";

        Entity plateStack = createTemplatePlate()
                .addComponent(new PlateComponent(quantity))
                .addComponent(new TextureRenderComponent(texturePath));

        PlateComponent plateComponent = plateStack.getComponent(PlateComponent.class);
        plateComponent.setStacked(true);
        plateComponent.setQuantity(quantity);

        int[] plateArray = new int[quantity];
        for (int i = 0; i < quantity; i++) {
            plateArray[i] = i + 1;
        }
        plateComponent.setPlateArray(plateArray);

        PhysicsUtils.setScaledCollider(plateStack, 0.6f, 0.3f);
        plateStack.getComponent(ColliderComponent.class).setDensity(1.0f);

        plateStack.getComponent(InteractionComponent.class).setAsBox(plateStack.getScale());
        InteractionComponent plateInteraction = plateStack.getComponent(InteractionComponent.class);
        plateInteraction.create();
        plateInteraction.getFixture().setUserData(plateStack);

        return plateStack;
    }

    public static Entity spawnMealOnPlate(int id, String mealType) {

        String mealTexturePath = getMealTexturePath(mealType);

        Entity plate = createTemplatePlate()
                .addComponent(new PlateComponent(1))
                .addComponent(new TextureRenderComponent(mealTexturePath));
        PhysicsUtils.setScaledCollider(plate, 0.6f, 0.3f);
        plate.getComponent(ColliderComponent.class).setDensity(1.0f);

        PlateComponent plateComponent = plate.getComponent(PlateComponent.class);
        plateComponent.setStacked(false);
        plateComponent.setId(id);
        plateComponent.addMealToPlate(mealType);

        //logger.info("mealplate id: {}", plateComponent.getId());

        return plate;
    }

    public static void disposePlate(Entity plate, int quantity) {
        if (quantity == 0) {
            plate.dispose();
            //logger.info("Disposed fr");
        } else {
            String newTexturePath = "images/platecomponent/stackedplates/" + quantity + "plates.png";
            TextureRenderComponent textureRenderComponent = plate.getComponent(TextureRenderComponent.class);
            textureRenderComponent.setTexture(newTexturePath);
            //logger.info("rerendered");
        }
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