
package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

public class BackstoryCutscene extends Cutscene {

    public BackstoryCutscene() {
        super();
    }

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

    private void createScene1() {
        Array<String> sceneText = new Array<>();
        sceneText.add("You were once an esteemed chef at the Brooklyn Bistro, \n" +
                "an establishment specialising in only the finest cuisine...");

        String backgroundImage = "images/Cutscenes/Brooklyn_Bistro_Background.png";
        addSceneWithText(backgroundImage, sceneText, 5.0f);
    }

    private void createScene2() {
        Array<String> sceneText = new Array<>();
        sceneText.add("Animals were once just resources to be used \n" +
                "in the meals we humans love so much...");

        String backgroundImage = "images/Cutscenes/Kitchen_Background.png";
        addSceneWithText(backgroundImage, sceneText, 4.0f);
    }

    private void createScene3() {
        Array<String> sceneText = new Array<>();
        sceneText.add("You built a reputation for your talent & expertise...");

        String backgroundImage = "images/Cutscenes/Food_Critic_Background.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene4() {
        Array<String> sceneText = new Array<>();
        sceneText.add("Then, the fall happened...");

        String backgroundImage = "images/Cutscenes/Food_Critic_Background.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene5() {
        Array<String> sceneText = new Array<>();
        sceneText.add("It started small. Animals becoming more aware, more intelligent...");

        String backgroundImage = "images/Cutscenes/Animals_in_Kitchen_Background.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene6() {
        Array<String> sceneText = new Array<>();
        sceneText.add("Farms were overrun, livestock freed themselves...");

        String backgroundImage = "images/Cutscenes/Farm_Background.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene7() {
        Array<String> sceneText = new Array<>();
        sceneText.add("The uprising spread quickly, and society quickly \n" +
                "crumbled under the weight of this unexpected revolt...");

        String backgroundImage = "images/Cutscenes/graveyard_mafia.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene8() {
        Array<String> sceneText = new Array<>();
        sceneText.add("You managed to survive the chaos but you lost everything - \n" +
                "your reputation, your restaurant, the life you built...");

        String backgroundImage = "images/Cutscenes/deserted_city_opt1.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene9() {
        Array<String> sceneText = new Array<>();
        sceneText.add("You strike a deal with the local mafia, who wiped out most of the humans...");

        String backgroundImage = "images/Cutscenes/graveyard_mafia_chef.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene10() {
        Array<String> sceneText = new Array<>();
        sceneText.add("They don't believe a human can run a restaurant in this world...");

        String backgroundImage = "images/Cutscenes/new_beastly_bistro_pt2.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene11() {
        Array<String> sceneText = new Array<>();
        sceneText.add("You have five days to prove yourself, \n" +
                "and if you don't, you might suffer a similar fate...");

        String backgroundImage = "images/Cutscenes/new_beastly_bistro.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    private void createScene12() {
        Array<String> sceneText = new Array<>();
        sceneText.add("Do you have what it takes to survive the Beastly Bistro?");

        String backgroundImage = "images/Cutscenes/resized_black_image.png";
        addSceneWithText(backgroundImage, sceneText, 3.0f);
    }

    /**
     * Helper method to add a scene with background and text.
     *
     * @param backgroundImage The background image for the scene.
     * @param sceneText       The text for the scene.
     * @param duration        The duration of the scene.
     */
    private void addSceneWithText(String backgroundImage, Array<String> sceneText, float duration) {
        Scene scene = new Scene(backgroundImage);
        scene.setSceneText(sceneText);
        scene.setDuration(duration);
        scenes.add(scene);
    }

    @Override
    protected void loadAssets() {
        // Load the background images for the cutscene
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

    @Override
    public void createEntities() {
        // Any specific entity creation logic for the backstory cutscene
    }
}
