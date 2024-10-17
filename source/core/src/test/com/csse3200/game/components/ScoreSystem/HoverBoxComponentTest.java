package com.csse3200.game.components.ScoreSystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyFloat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class HoverBoxComponentTest {

    @Mock private Texture mockTexture;
    @Mock private SpriteBatch mockBatch;
    @Mock private Entity mockEntity;
    @Mock private RenderService mockRenderService;

    private HoverBoxComponent hoverBoxComponent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ServiceLocator.registerRenderService(mockRenderService);
        hoverBoxComponent = new HoverBoxComponent(mockTexture);
    }

    @Test
    void testSetTexture() {
        Texture newTexture = mock(Texture.class);
        hoverBoxComponent.setTexture(newTexture);
        assertEquals(newTexture, hoverBoxComponent.getTexture());
    }

    @Test
    void testUpdate() {
        Vector2 position = new Vector2(1, 1);
        Vector2 scale = new Vector2(2, 2);
        when(mockEntity.getPosition()).thenReturn(position);
        when(mockEntity.getScale()).thenReturn(scale);

        hoverBoxComponent.setEntity(mockEntity);
        hoverBoxComponent.update();

        assertEquals(position, hoverBoxComponent.position);
        assertEquals(scale, hoverBoxComponent.scale);
    }

    @Test
    void testDraw_WithValidEntity() {
        hoverBoxComponent.setEntity(mockEntity);
        hoverBoxComponent.position = new Vector2(1, 1);
        hoverBoxComponent.scale = new Vector2(1, 1);

        hoverBoxComponent.draw(mockBatch);

        verify(mockBatch).draw(eq(mockTexture), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    void testDraw_WithNullEntity() {
        hoverBoxComponent.draw(mockBatch);
        verify(mockBatch, never()).draw(any(Texture.class), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    void testDraw_WithZeroPosition() {
        hoverBoxComponent.setEntity(mockEntity);
        hoverBoxComponent.position = new Vector2(0, 0);
        hoverBoxComponent.scale = new Vector2(1, 1);

        hoverBoxComponent.draw(mockBatch);

        verify(mockBatch, never()).draw(any(Texture.class), anyFloat(), anyFloat(), anyFloat(), anyFloat());
    }

    @Test
    void testGetLayer() {
        assertEquals(3, hoverBoxComponent.getLayer());
    }

    @Test
    void testSetStage() {
        Stage mockStage = mock(Stage.class);
        hoverBoxComponent.setStage(mockStage);
    }
}