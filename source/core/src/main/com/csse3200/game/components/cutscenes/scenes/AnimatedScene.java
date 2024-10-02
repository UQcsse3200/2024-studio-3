package com.csse3200.game.components.cutscenes.scenes;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.services.ServiceLocator;

/**
 * AnimatedScene class represents one animated scene for a cutscene.
 * It contains atlas file for the scene and the length it needs to play for.
 */
public class AnimatedScene {
    public String animName;
    private String atlasFilePath; // the atlas file path
    private float duration; // The duration of the animation

    /**
     * Constructor for an animatedScene
     * @param atlasFilePath Path to the atlas file for the animation.
     * @param duration Duration of the animation.
     */
    public AnimatedScene(String atlasFilePath, String animName, float duration) {
        this.atlasFilePath = atlasFilePath;
        this.animName = animName;
        this.duration = duration;
    }

    /**
     * Get the atlas file path.
     * @return String which is the path to the atlas file.
     */
    public String getAtlasFilePath() {
        return this.atlasFilePath;
    }

    /**
     * Get the animation duration.
     * @return Float which is the duration of the animation.
     */
    public float getDuration() {
        return this.duration;
    }

    /**
     * Get the animation name.
     * @return String which is the animation name
     */
    public String getAnimName() {
        return this.animName;
    }
}
