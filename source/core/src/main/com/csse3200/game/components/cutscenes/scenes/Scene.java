package com.csse3200.game.components.cutscenes.scenes;

import com.badlogic.gdx.utils.Array;
import com.csse3200.game.services.ServiceLocator;

public class Scene {
    private String backgroundImagePath;
    private String[] animationImagePaths;
    private  Array<String> sceneText;
    private float duration;

    public Scene(String backgroundImagePath, String[] animationImagePaths,  Array<String> sceneText, float duration) {
        this.backgroundImagePath = backgroundImagePath;
        this.animationImagePaths = animationImagePaths;
        this.sceneText = sceneText;
        this.duration = duration;
    }

    public String getBackgroundImagePath() {
        return backgroundImagePath;
    }

    public String[] getAnimationImagePaths() {
        return animationImagePaths;
    }

    public  Array<String> getSceneText() {
        return sceneText;
    }

    public float getDuration() {
        return duration;
    }

    public void setScript() {
        ServiceLocator.getCutsceneScreen().getCutsceneScreenDisplay().setCutsceneText(sceneText);
    }
}

