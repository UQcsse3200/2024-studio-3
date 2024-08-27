package com.csse3200.game.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.components.ordersystem.DocketDisplay;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class DocketDisplayTest {

    private DocketDisplay docketDisplay;
    private SpriteBatch spriteBatch;
    private Stage stage;

    @BeforeEach
    public void setUp() {
        // Mock FileHandle
        FileHandle mockFileHandle = mock(FileHandle.class);
        when(mockFileHandle.exists()).thenReturn(true);
        when(mockFileHandle.read()).thenReturn(new ByteArrayInputStream(new byte[0])); // Simulate file contents

        // Mock Gdx.files
        com.badlogic.gdx.Files mockFiles = mock(com.badlogic.gdx.Files.class);
        when(mockFiles.internal("images/ordersystem/docket_background.png")).thenReturn(mockFileHandle);
        Gdx.files = mockFiles; // Inject mock

        // Mock ResourceService
        ResourceService mockResourceService = mock(ResourceService.class);
        when(mockResourceService.getAsset(eq("images/ordersystem/docket_background.png"), eq(Texture.class)))
                .thenReturn(new Texture("dummy.png"));


        // Initialize DocketDisplay
        docketDisplay = new DocketDisplay();
        docketDisplay.create(); // Initialize components
    }

    @Test
    public void testDocketDisplayNotNull() {
        assertNotNull(docketDisplay);
    }


}
