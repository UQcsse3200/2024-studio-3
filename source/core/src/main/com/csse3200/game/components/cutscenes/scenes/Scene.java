package com.csse3200.game.components.cutscenes.scenes;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.services.ServiceLocator;

/**
 * The Scene class represents an individual scene within a cutscene.
 * It contains the background image, animations, text, and duration of the scene.
 */
public class Scene {
    private String backgroundImagePath;  // Path to the background image for the scene
    private String[] animationImagePaths;  // Paths to the animation images for the scene
    private Array<String> sceneText;  // Text dialogue for the scene
    private float duration;  // Duration the scene will be displayed

    /**
     * Constructor for the Scene class.
     *
     * @param backgroundImagePath Path to the background image.
     * @param animationImagePaths Paths to the animation images.
     * @param sceneText Dialogue text for the scene.
     * @param duration Duration of the scene in seconds.
     */
    public Scene(String backgroundImagePath, String[] animationImagePaths, Array<String> sceneText, float duration) {
        this.backgroundImagePath = backgroundImagePath;
        this.animationImagePaths = animationImagePaths;
        this.sceneText = sceneText;
        this.duration = duration;
    }

    /**
     * Gets the background image path for the scene.
     *
     * @return Path to the background image.
     */
    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    /**
     * Gets the animation image paths for the scene.
     *
     * @return Array of paths to the animation images.
     */
    public String[] getAnimationImagePaths() {
        return animationImagePaths;
    }

    /**
     * Gets the dialogue text for the scene.
     *
     * @return Array of dialogue text strings for the scene.
     */
    public Array<String> getSceneText() {
        return sceneText;
    }

    /**
     * Gets the duration of the scene.
     *
     * @return The duration of the scene in seconds.
     */
    public float getDuration() {
        return duration;
    }

    /**
     * Sets the dialogue text for the cutscene using the CutsceneTextDisplay component.
     * This will display the scene's text on the cutscene screen.
     */
    public void setScript() {
        // Set the dialogue text in the CutsceneScreenDisplay component
        ServiceLocator.getCutsceneScreen().getCutsceneScreenDisplay().setCutsceneText(sceneText);
    }
}
