package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

/**
 * Specific cutscene class handling the backstory of the game.
 */
public class BackstoryCutscene extends Cutscene {
    private String foodCriticBackground = "images/Cutscenes/Food_Critic_Background.png";

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
        scene4Text.add("Then, the fall happened...");

        Array<String> scene5Text = new Array<>();
        scene5Text.add("It started small. Animals becoming more aware, more intelligent...");

        Array<String> scene6Text = new Array<>();
        scene6Text.add("Farms were overrun, livestock freed themselves...");

        Array<String> scene7Text = new Array<>();
        scene7Text.add("The uprising spread quickly, and society quickly \n" +
                "crumbled under the weight of this unexpected revolt...");

        Array<String> scene8Text = new Array<>();
        scene8Text.add("You managed to survive the chaos but you lost everything - \n" +
                "your reputation, your restaurant, the life you built...");

        Array<String> scene9Text = new Array<>();
        scene9Text.add("You strike a deal with the local mafia, who wiped out most of the humans...");

        Array<String> scene10Text = new Array<>();
        scene10Text.add("They don't believe a human can run a restaurant in this world...");

        Array<String> scene11Text = new Array<>();
        scene11Text.add("You have five days to prove yourself, \n" +
                "and if you don't, you might suffer a similar fate...");

        Array<String> scene12Text = new Array<>();
        scene12Text.add("Do you have what it takes to survive the Beastly Bistro?");

        // Add scenes with background images, text, and duration
        Scene scene1 = new Scene("images/Cutscenes/Brooklyn_Bistro_Background.png");
        scene1.setSceneText(scene1Text);
        scene1.setDuration(5.0f);
        scenes.add(scene1);

        Scene scene2 = new Scene("images/Cutscenes/Kitchen_Background.png");
        scene2.setSceneText(scene2Text);
        scene2.setDuration(4.0f);
        scenes.add(scene2);


        Scene scene3 = new Scene("images/Cutscenes/Food_Critic_Background.png");
        scene3.setSceneText(scene3Text);
        scene3.setDuration(3.0f);
        scenes.add(scene3);

        Scene scene4 = new Scene("images/Cutscenes/Food_Critic_Background.png");
        scene4.setSceneText(scene4Text);
        scene4.setDuration(3.0f);
        scenes.add(scene4);

        Scene scene5 = new Scene("images/Cutscenes/Animals_in_Kitchen_Background.png");
        scene5.setSceneText(scene5Text);
        scene5.setDuration(3.0f);
        scenes.add(scene5);

        Scene scene6 = new Scene("images/Cutscenes/Farm_Background.png");
        scene6.setSceneText(scene6Text);
        scene6.setDuration(3.0f);
        scenes.add(scene6);

        Scene scene7 = new Scene("images/Cutscenes/graveyard_mafia.png");
        scene7.setSceneText(scene7Text);
        scene7.setDuration(3.0f);
        scenes.add(scene7);

        Scene scene8 = new Scene("images/Cutscenes/deserted_city_opt1.png");
        scene8.setSceneText(scene8Text);
        scene8.setDuration(3.0f);
        scenes.add(scene8);

        Scene scene9 = new Scene("images/Cutscenes/graveyard_mafia_chef.png");
        scene9.setSceneText(scene9Text);
        scene9.setDuration(3.0f);
        scenes.add(scene9);

        Scene scene10 = new Scene("images/Cutscenes/new_beastly_bistro_pt2.png");
        scene10.setSceneText(scene10Text);
        scene10.setDuration(3.0f);
        scenes.add(scene10);

        Scene scene11 = new Scene("images/Cutscenes/new_beastly_bistro.png");
        scene11.setSceneText(scene11Text);
        scene11.setDuration(3.0f);
        scenes.add(scene11);

        Scene scene12 = new Scene("images/Cutscenes/resized_black_image.png");
        scene12.setSceneText(scene12Text);
        scene12.setDuration(3.0f);
        scenes.add(scene12);

    }

    /**
     * Loads the assets needed for the backstory cutscene, including textures for backgrounds.
     */

    @Override
    protected void loadAssets() {
        // Load the background images for the backstory cutscene
         textures = new String[] {
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

        // Get the resource service to load the assets
        ResourceService resourceService = ServiceLocator.getResourceService();
        resourceService.loadTextures(textures);
        resourceService.loadAll();
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

