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

/**
 * PlateFactory is used to create manage plate entities in game with their textures
 * methods such as spawn individual plates, spawn plate stacks and spawn plates with meals
 * and also depose plates also getmealtexture to limit meals
 */
public class PlateFactory {

    private PlateFactory() {
        //This is private
    }
    /**
     * Creates a template plate entity
     *
     * @return A template plate entity components such as physics, collider, and interaction
     */
    public static Entity createTemplatePlate() {
        return new Entity()
                .addComponent(new PhysicsComponent())
                .addComponent(new ColliderComponent())
                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
                .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
                .addComponent(new TooltipsDisplay());
    }

    /**
     * Spawns a single plate with an ID obtained from array of stacked plates
     *
     * @param id The ID of the plate
     * @return The spawned plate entity
     */
    public static Entity spawnPlate(int id) {
        String texturePath = "images/platecomponent/stackedplates/1plates.png";

        Entity plate = createTemplatePlate()
                .addComponent(new PlateComponent(1))
                .addComponent(new TextureRenderComponent(texturePath));

        PlateComponent plateComponent = plate.getComponent(PlateComponent.class);
        plateComponent.setStacked(false);
        plateComponent.setId(id);

        PhysicsUtils.setScaledCollider(plate, 0.5f, 0.25f);
        plate.getComponent(ColliderComponent.class).setDensity(1.0f);
        plate.getComponent(InteractionComponent.class).setAsBox(plate.getScale());
        plate.getComponent(InteractionComponent.class).create();
        plate.getComponent(InteractionComponent.class).getFixture().setUserData(plate);

        return plate;
    }

    /**
     * Spawns a stack of plates with a given amount of quantity
     *
     * @param quantity The number plates in stack ([1,5])
     * @return The spawned plate stack entity
     */
    public static Entity spawnPlateStack(int quantity) {
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

    /**
     * Spawns a plate with a meal on it, retaining plate ID when it was empty
     *
     * @param id       The ID of the plate
     * @param mealType The mealType to place to plate
     * @return The plate entity with the meal
     */
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

        return plate;
    }

    /**
     * Disposes of the plate stack or updates the texture based on the remaining quantity (for stacked)
     *
     * @param plate    The plate entity to dispose or update
     * @param quantity The remaining number of plates in the stack
     */
    public static void disposePlate(Entity plate, int quantity) {
        if (quantity == 0) {
            plate.dispose();
        } else {
            String newTexturePath = "images/platecomponent/stackedplates/" + quantity + "plates.png";
            TextureRenderComponent textureRenderComponent = plate.getComponent(TextureRenderComponent.class);
            textureRenderComponent.setTexture(newTexturePath);
        }
    }

    /**
     * Retrieves the texture path for the given mealType
     *
     * @param mealType The mealType to place to plate
     *
     * @return The texture path for the meal, or null if the type is unknown
     */
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