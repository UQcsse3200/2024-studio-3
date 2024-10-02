package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import com.csse3200.game.services.ServiceLocator;

/**
 * Specific cutscene class handling the backstory of the game.
 */
public class BackstoryCutscene extends Cutscene {

    private Array<Array<String>> scenesText;  // Store text for all scenes

    /**
     * Constructor for BackstoryCutscene.
     */
    public BackstoryCutscene() {
        super();
        scenesText = new Array<>();
    }

    /**
     * Sets up the scenes for the backstory cutscene, including background images,
     * animations, and the corresponding text for each scene.
     */
    @Override
    protected void setupScenes() {
        // Define the cutscene text for each scene
        Array<String> scene1Text = new Array<>();
        scene1Text.add("You were once an esteemed chef at the Brooklyn Bistro, \n" +
                "an establishment specialising in only the finest cuisine...");
        scenesText.add(scene1Text);

        Array<String> scene2Text = new Array<>();
        scene2Text.add("Animals were once just resources to be used \n" +
                "in the meals we humans love so much...");
        scenesText.add(scene2Text);

        Array<String> scene3Text = new Array<>();
        scene3Text.add("You built a reputation for your talent & expertise...");
        scenesText.add(scene3Text);

        Array<String> scene4Text = new Array<>();
        scene4Text.add("Then, the fall happened...");
        scenesText.add(scene4Text);

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
        scene4.setSceneText(scene4Text);
        scene4.setDuration(3.0f);

        // Add all scenes to the cutscene
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

    @Override
    public void createEntities() {

    }

    /**
     * Returns the cutscene text for each scene.
     *
     * @return Array<Array<String>> containing the text for all scenes.
     */
    public Array<Array<String>> getScenesText() {
        return scenesText;
    }
}
