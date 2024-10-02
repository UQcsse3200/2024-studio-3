//package com.csse3200.game.components.cutscenes;
//
//import com.badlogic.gdx.utils.Array;
//import com.csse3200.game.components.cutscenes.scenes.Scene;
//import com.csse3200.game.services.ResourceService;
//import com.csse3200.game.services.ServiceLocator;
//
///**
// * Specific cutscene class handling the backstory of the game.
// */
//public class BackstoryCutscene extends Cutscene {
//    /**
//     * Constructor for BackstoryCutscene.
//     */
//    public BackstoryCutscene() {
//        super();
//    }
//
//    /**
//     * Sets up the scenes for the backstory cutscene, including background images,
//     * animations, and the corresponding text for each scene.
//     */
//    @Override
//    protected void setupScenes() {
//        // Add cutscene text
//        cutsceneText.add("In the year 2045, the world changed...");
//        cutsceneText.add("Humans and animals were forced to coexist in a new order.");
//        cutsceneText.add("Our hero, a chef, is one of the last remaining humans...");
//
//        // Add scenes with background images, text, and duration
//        scenes.add(new Scene(
//                "images/Cutscenes/Brooklyn_Bistro_Background.png",
//                null,  // No animations for this scene
//                new Array<>(new String[]{"In the year 2045, the world changed..."}),
//                5.0f
//        ));
//        scenes.add(new Scene(
//                "images/Cutscenes/Kitchen_Background.png",
//                null,  // No animations for this scene
//                new Array<>(new String[]{"Humans and animals were forced to coexist in a new order."}),
//                4.0f
//        ));
//        scenes.add(new Scene(
//                "images/Cutscenes/Food_Critic_Background.png",
//                null,  // No animations for this scene
//                new Array<>(new String[]{"Humans and animals were forced to coexist in a new order."}),
//                4.0f
//        ));
//        scenes.add(new Scene(
//                "images/Cutscenes/Food_Critic_Background.png",
//                null,  // No animations for this scene
//                new Array<>(new String[]{"Humans and animals were forced to coexist in a new order."}),
//                4.0f
//        ));
//        scenes.add(new Scene(
//                "images/Cutscenes/Kitchen_Background.png",
//                null,  // No animations for this scene
//                new Array<>(new String[]{"Humans and animals were forced to coexist in a new order."}),
//                4.0f
//        ));
//        scenes.add(new Scene(
//                "images/Cutscenes/Farm_Background.png",
//                null,  // No animations for this scene
//                new Array<>(new String[]{"Our hero, a chef, is one of the last remaining humans..."}),
//                3.0f
//        ));
//    }
//
//    /**
//     * Loads the assets needed for the backstory cutscene, including textures for backgrounds.
//     */
////    @Override
////    protected void loadAssets() {
////        // Load the background images for the cutscene
////        textures = new String[] {
////                "images/Cutscenes/scene1_background.png",
////                "images/Cutscenes/scene2_background.png",
////                "images/Cutscenes/scene3_background.png"
////        };
////
////        // Get the resource service to load assets
////        ResourceService resourceService = ServiceLocator.getResourceService();
////        resourceService.loadTextures(textures);
////        resourceService.loadAll();  // Ensure all assets are loaded
////    }
//    @Override
//    protected void loadAssets() {
//        // Load assets for the backstory cutscene
//        for (Scene scene : scenes) {
//            ServiceLocator.getResourceService().loadTextures(new String[]{scene.getBackgroundImagePath()});
//        }
//    }
//
//    /**
//     * Handles specific entity creation logic for the backstory cutscene.
//     * Currently, there is no specific logic for creating entities.
//     */
//    @Override
//    public void createEntities() {
//        // Any specific entity creation logic for the backstory cutscene
//    }
//
//    /**
//     * Returns the cutscene text that will be displayed during the cutscene.
//     *
//     * @return Array<String> of cutscene text.
//     */
//    public Array<String> getCutsceneText() {
//        return cutsceneText;
//    }
//}
package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

/**
 * Specific cutscene class handling the backstory of the game.
 */
public class BackstoryCutscene extends Cutscene {

    /**
     * Constructor for BackstoryCutscene.
     */
    public BackstoryCutscene() {
        super();
    }

    /**
     * Sets up the scenes for the backstory cutscene, including background images,
     * animations, and the corresponding text for each scene.
     */
    @Override
    protected void setupScenes() {
        // Define the cutscene text
        Array<String> scene1Text = new Array<>();
        scene1Text.add("You were once an esteemed chef at the Brooklyn Bistro, \n" +
                "an establishment specialising in only the finest cuisine... ");


        Array<String> scene2Text = new Array<>();
        scene2Text.add("Animals were once just resources to be used \n" +
                "in the meals we humans love so much...");

        Array<String> scene3Text = new Array<>();
        scene3Text.add("You built a reputation for your talent & expertise...");

        Array<String> scene4Text = new Array<>();
        scene3Text.add("Then, the fall happened...");

        // Add scenes with background images, text, and duration
        Scene scene1 = new Scene("images/Cutscenes/Brooklyn_Bistro_Background.png");
        scene1.setSceneText(scene1Text);
        scene1.setDuration(5.0f);

        Scene scene2 = new Scene("images/Cutscenes/Kitchen_Background.png");
        scene2.setSceneText(scene2Text);
        scene2.setDuration(4.0f);

        Scene scene3 = new Scene("images/Cutscenes/Food_Critic_Background.png");
        scene3.setSceneText(scene3Text);
        scene3.setDuration(3.0f);

        Scene scene4 = new Scene("images/Cutscenes/Food_Critic_Background.png");
        scene4.setSceneText(scene2Text);
        scene4.setDuration(3.0f);

        scenes.add(scene1);
        scenes.add(scene2);
        scenes.add(scene3);
        scenes.add(scene4);
    }

    /**
     * Loads the assets needed for the backstory cutscene, including textures for backgrounds.
     */
    @Override
    protected void loadAssets() {
        // Load the assets for the backstory cutscene
        for (Scene scene : scenes) {
            ServiceLocator.getResourceService().loadTextures(new String[]{scene.getBackgroundImagePath()});
        }
        ServiceLocator.getResourceService().loadAll();
    }

    /**
     * Handles specific entity creation logic for the backstory cutscene.
     * Currently, there is no specific logic for creating entities.
     */
    @Override
    public void createEntities() {
        // No specific entity creation logic for the backstory cutscene at this time
    }
}
