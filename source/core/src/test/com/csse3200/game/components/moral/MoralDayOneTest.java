package com.csse3200.game.components.moral;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class MoralDayOneTest {
    private MoralDayOne moralDayOne;
    private MainGameScreen gameScreen;
    private Stage stage;

    @BeforeEach
    void setUp() {
        RenderService renderService = mock(RenderService.class);
        ResourceService resourceService = mock(ResourceService.class);
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerResourceService(resourceService);

        when(resourceService.getAsset("images/moral_scenes/moneylaunder.png", Texture.class))
                .thenReturn(mock(Texture.class));

        gameScreen = mock(MainGameScreen.class);
        ServiceLocator.registerGameScreen(gameScreen);

        stage = mock(new Stage());
        SpriteBatch spriteBatch = mock(SpriteBatch.class);
        when(stage.getBatch()).thenReturn(spriteBatch);
        when(ServiceLocator.getRenderService().getStage()).thenReturn(stage);

        moralDayOne = new MoralDayOne(stage);
    }
//
//    @Test
//    void setQuestion_shouldSetQuestionSuccessfully() {
//        String question = "Is this a moral decision?";
//        assertTrue(moralDayOne.setQuestion(question));
//    }
//
//    @Test
//    void show_shouldMakeScreenVisible() {
//        moralDayOne.toggleVisibility(1);
//        assertTrue(moralDayOne.getVisible());
//        verify(gameScreen).pause();
//    }
//
//    @Test
//    void hide_shouldMakeScreenInvisible() {
//        if (!moralDayOne.getVisible()) {
//            moralDayOne.toggleVisibility(1);
//        }
//        moralDayOne.toggleVisibility(1);
//        assertFalse(moralDayOne.getVisible());
//        verify(gameScreen).resume();
//    }
//
//    @Test
//    void toggleVisibility_shouldToggleScreenVisibility() {
//        moralDayOne.toggleVisibility(1);
//        assertTrue(moralDayOne.getVisible());
//        moralDayOne.toggleVisibility(1);
//        assertFalse(moralDayOne.getVisible());
//    }

//    @Test
//    void toggleVisibility_shouldLogDay() {
//        Logger logger = mock(Logger.class);
//        when(logger.isDebugEnabled()).thenReturn(true);
//        moralDayOne.toggleVisibility(1);
//        verify(logger).debug(" Day - {}", 1);
//    }
}