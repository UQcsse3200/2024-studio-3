package com.csse3200.game.components.cutscenes.scenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Scene class represents an individual scene within a cutscene.
 * It contains the background image, animations, text, and duration of the scene.
 */
public class Scene {
    private static final Logger logger = LoggerFactory.getLogger(Scene.class);
    private String backgroundImagePath;  // Path to the background image for the scene
    private String[] animationImagePaths;  // Paths to the animation images for the scene
    private Vector2[] animationPositions;

    private String[] imagePaths;
    private Vector2[] imagePositions;
    private float[] imageScales;

    private Array<String> sceneText;  // Text dialogue for the scene
    private float duration;  // Duration the scene will be displayed

    /**
     * Constructor for the Scene class.
     *
     * @param backgroundImagePath Path to the background image.
     */
    public Scene(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
    }

    /**
     * Gets the background image path for the scene.
     *
     * @return Path to the background image.
     */
    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public void setBackgroundImagePath(String backgroundImagePath) {
        this.backgroundImagePath = backgroundImagePath;
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
     * Gets the animation positions for the scene.
     *
     * @return Array of Vector2 positions for the animation images.
     */
    public Vector2[] getAnimationPositions() {
        return animationPositions;
    }

    public void setAnimationImages(String[] animationImagePaths, Vector2[] animationPositions) {
        if (animationImagePaths.length != animationPositions.length) {
            logger.error("Animation image paths size does not match the position size");
        }
        this.animationImagePaths = animationImagePaths;
        this.animationPositions = animationPositions;
    }

    /**
     * Gets the animation image paths for the scene.
     *
     * @return Array of paths to the images.
     */
    public String[] getImagePaths() {
        return imagePaths;
    }

    /**
     * Gets the image scales for the scene.
     *
     * @return Array of Vector2 positions for the images.
     */
    public float[] getImageScales() {
        return imageScales;
    }
    public Vector2[] getImagePositions() {
        return imagePositions;
    }

    public void setImages(String[] imagePaths, Vector2[] imagePositions, float[] imageScales) {
        if (imagePaths.length != imagePositions.length) {
            logger.error("Image paths size does not match the position size");
        }
        this.imagePaths = imagePaths;
        this.imagePositions = imagePositions;
        this.imageScales = imageScales;
    }

    /**
     * Gets the dialogue text for the scene.
     *
     * @return Array of dialogue text strings for the scene.
     */
    public Array<String> getSceneText() {
        return sceneText;
    }

    public void setSceneText(Array<String> sceneText) {
        this.sceneText = sceneText;
    }

    /**
     * Gets the duration of the scene.
     *
     * @return The duration of the scene in seconds.
     */
    public float getDuration() {
        return duration;
    }

    public void setDuration(float duration) {
        this.duration = duration;
    }
    
}
