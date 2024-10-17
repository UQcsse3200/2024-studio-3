package com.csse3200.game.components.cutscenes;
import com.csse3200.game.components.cutscenes.scenes.AnimatedScene;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class AnimatedSceneTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String atlasFilePath = "path/to/atlas";
        String animName = "TestAnimation";
        float duration = 5.0f;

        // Act
        AnimatedScene scene = new AnimatedScene(atlasFilePath, animName, duration);

        // Assert
        assertEquals(atlasFilePath, scene.getAtlasFilePath());
        assertEquals(animName, scene.getAnimName());
        assertEquals(duration, scene.getDuration());
    }

    @Test
    void testGetAtlasFilePath() {
        // Arrange
        String atlasFilePath = "path/to/another/atlas";
        AnimatedScene scene = new AnimatedScene(atlasFilePath, "Anim", 3.0f);

        // Act
        String result = scene.getAtlasFilePath();

        // Assert
        assertEquals(atlasFilePath, result);
    }

    @Test
    void testGetDuration() {
        // Arrange
        float duration = 7.5f;
        AnimatedScene scene = new AnimatedScene("path/to/atlas", "Anim", duration);

        // Act
        float result = scene.getDuration();

        // Assert
        assertEquals(duration, result);
    }

    @Test
    void testGetAnimName() {
        // Arrange
        String animName = "WalkCycle";
        AnimatedScene scene = new AnimatedScene("path/to/atlas", animName, 4.5f);

        // Act
        String result = scene.getAnimName();

        // Assert
        assertEquals(animName, result);
    }
}