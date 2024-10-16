package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

/**
 * BackstoryCutscene manages the scenes that are part of the game's backstory cutscene sequence.
 * It includes multiple scenes, each with its own text and background image, to narrate the player's
 * journey through the backstory.
 */
public class BackstoryCutscene extends Cutscene {

    /**
     * Constructor for the BackstoryCutscene class.
     * Initializes the cutscene by calling the superclass constructor.
     */
    public BackstoryCutscene() {
        super();
    }

    /**
     * Sets up the individual scenes for the backstory cutscene.
     * Each scene is created with its own background image and text, in sequence.
     */
    @Override
    protected void setupScenes() {
        createScene1();
        createScene2();
        createScene3();
        createScene4();
        createScene5();
        createScene6();
        createScene7();
        createScene8();
        createScene9();
        createScene10();
        createScene11();
        createScene12();
    }

    /**
     * Creates the first scene of the backstory.
     * This scene introduces the player's past as an esteemed chef at the Brooklyn Bistro.
     */
    private void createScene1() {
        Array<String> sceneText = new Array<>();
        sceneText.add("\n" +"You were once an esteemed chef at the Brooklyn Bistro, \n" +
                "an establishment specialising in only the finest cuisine...");

        String backgroundImage = "images/Cutscenes/Brooklyn_Bistro_Background.png";
        addSceneWithText(backgroundImage, sceneText, 5.0f);
    }

    /**
     * Creates the second scene of the backstory.
     * This scene introduces how animals were once treated as resources in the meals humans enjoyed.
     */
    private void createScene2() {
        Array<String> sceneText = new Array<>();
        sceneText.add("\n" +"Animals were once just resources to be used \n" +
                "in the meals we humans love so much...");

        String backgroundImage = "images/Cutscenes/Kitchen_Background.png";
        addSceneWithText(backgroundImage, sceneText, 4.0f);
    }

    // Additional scene creation methods follow the same pattern:
    // createScene3, createScene4, createScene5, etc.
    private void createScene3() {
        Array<String> sceneText = new Array<>();
        sceneText.add("\n" + "You built a reputation for your talent & expertise...");

        String backgroundImage = "images/Cutscenes/Food_Critic_Background.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene4() {
        Array<String> sceneText = new Array<>();
        sceneText.add("\n" +"Then, the fall happened...");

        String backgroundImage = "images/Cutscenes/Food_Critic_Background.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene5() {
        Array<String> sceneText = new Array<>();
        sceneText.add("\n" +"It started small. Animals becoming more aware,\n" + "more intelligent...");

        String backgroundImage = "images/Cutscenes/Animals_in_Kitchen_Background.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene6() {
        Array<String> sceneText = new Array<>();
        sceneText.add("\n" +"Farms were overrun, livestock freed themselves...");

        String backgroundImage = "images/Cutscenes/Farm_Background.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene7() {
        Array<String> sceneText = new Array<>();
        sceneText.add("\n" +"The uprising spread quickly, and society quickly \n" +
                "crumbled under the weight of this unexpected revolt...");

        String backgroundImage = "images/Cutscenes/graveyard_mafia.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene8() {
        Array<String> sceneText = new Array<>();
        sceneText.add("\n" +"You managed to survive the chaos but you lost everything - \n" +
                "your reputation, your restaurant, the life you built...");

        String backgroundImage = "images/Cutscenes/deserted_city_opt1.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene9() {
        Array<String> sceneText = new Array<>();
        sceneText.add("\n" +"You strike a deal with the local mafia, who wiped out most \n" + "of the humans...");

        String backgroundImage = "images/Cutscenes/graveyard_mafia_chef.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene10() {
        Array<String> sceneText = new Array<>();
        sceneText.add("\n" +"They don't believe a human can run a restaurant in this world...");

        String backgroundImage = "images/Cutscenes/new_beastly_bistro_pt2.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene11() {
        Array<String> sceneText = new Array<>();
        sceneText.add("\n" +"You have five days to prove yourself, \n" +
                "and if you don't, you might suffer a similar fate...");

        String backgroundImage = "images/Cutscenes/new_beastly_bistro.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene12() {
        Array<String> sceneText = new Array<>();
        sceneText.add("\n" +"Do you have what it takes to survive the Beastly Bistro?");

        String backgroundImage = "images/Cutscenes/resized_black_image.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }


    /**
     * Helper method to add a scene with background and text.
     *
     * @param backgroundImage The background image for the scene.
     * @param sceneText       The text for the scene.
     * @param duration        The duration of the scene in seconds.
     */
    private void addSceneWithText(String backgroundImage, Array<String> sceneText, float duration) {
        Scene scene = new Scene(backgroundImage);
        scene.setSceneText(sceneText);
        scene.setDuration(duration);
        scenes.add(scene);
    }

    /**
     * Loads the assets required for the backstory cutscene, such as background images.
     */
    @Override
    protected void loadAssets() {
        textures = new String[]{
                "images/Cutscenes/Brooklyn_Bistro_Background.png",
                "images/Cutscenes/Kitchen_Background.png",
                "images/Cutscenes/Food_Critic_Background.png",
                "images/Cutscenes/Animals_in_Kitchen_Background.png",
                "images/Cutscenes/Farm_Background.png",
                "images/Cutscenes/graveyard_mafia.png",
                "images/Cutscenes/deserted_city_opt1.png",
                "images/Cutscenes/graveyard_mafia_chef.png",
                "images/Cutscenes/new_beastly_bistro_pt2.png",
                "images/Cutscenes/new_beastly_bistro.png",
                "images/Cutscenes/resized_black_image.png"
        };

        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);
        resourceService.loadAll();
    }

    /**
     * Creates entities specific to the backstory cutscene.
     * This method can be used to define any special logic for entity creation during the cutscene.
     */
    @Override
    public void createEntities() {
        // Any specific entity creation logic for the backstory cutscene
    }
}
