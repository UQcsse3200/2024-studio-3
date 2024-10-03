package com.csse3200.game.components.cutscenes;

import org.junit.jupiter.api.Test;

public class BackgroundRenderComponentTest {
    @Test
    public void checkGetLayer() {
        BackgroundRenderComponent backgroundRenderComponent = new BackgroundRenderComponent("images/Cutscenes/Farm_Background.png");
        assert(backgroundRenderComponent.getLayer() == 1);
    }
}
