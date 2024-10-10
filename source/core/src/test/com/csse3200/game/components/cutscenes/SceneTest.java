package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SceneTest {

    private Scene scene;
    private String backgroundImagePath;
    private String[] animationImagePaths;
    private Vector2[] animationPositions;
    private String[] imagePaths;
    private Vector2[] imagePositions;
    private float[] imageScales;

    private Array<String> sceneText;
    private float duration;

    @BeforeEach
    void setUp() {
        backgroundImagePath = "background.png";
        animationImagePaths = new String[]{"anim1.atlas", "anim2.atlas"};
        animationPositions = new Vector2[]{new Vector2(1, 1), new Vector2(1, 2)};
        imagePaths = new String[]{"img1.png", "img2.png"};
        imagePositions = new Vector2[]{new Vector2(1, 1), new Vector2(1, 2)};
        imageScales = new float[] {4.0f, 1.5f};
        sceneText = new Array<>();
        sceneText.add("Line 1");
        sceneText.add("Line 2");
        duration = 10.5f;

        // Initialize the Scene object
        scene = new Scene(backgroundImagePath);
        scene.setAnimationImages(animationImagePaths, animationPositions);
        scene.setSceneText(sceneText);
        scene.setDuration(duration);
    }

    @Test
    void testGetSetBackgroundImagePath() {
        scene.setBackgroundImagePath(backgroundImagePath);
        // Test that the background image path is correctly returned
        assertEquals(backgroundImagePath, scene.getBackgroundImagePath(), "Background image path should match");
    }

    @Test
    void testGetSetAnimationImagePaths() {
        scene.setAnimationImages(animationImagePaths, animationPositions);
        // Test that the animation image paths are correctly returned
        assertEquals(animationImagePaths, scene.getAnimationImagePaths(), "Animation image paths should match");
        assertEquals(animationPositions, scene.getAnimationPositions(), "Animation positions should match");
    }

    @Test
    void testGetSetImagePaths() {
        scene.setImages(imagePaths, imagePositions, imageScales);
        // Test that the animation image paths are correctly returned
        assertEquals(imagePaths, scene.getImagePaths(), "Image paths should match");
        assertEquals(imagePositions, scene.getImagePositions(), "Image positions should match");
        assertEquals(imageScales, scene.getImageScales(), "Image scales should match");
    }

    @Test
    void testGetSetSceneText() {
        scene.setSceneText(sceneText);
        // Test that the scene text is correctly returned
        assertEquals(sceneText, scene.getSceneText(), "Scene text should match");
    }

    @Test
    void testGetSetDuration() {
        scene.setDuration(duration);
        // Test that the duration is correctly returned
        assertEquals(duration, scene.getDuration(), "Duration should match");
    }
}
