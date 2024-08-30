package com.csse3200.game.rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.csse3200.game.components.ordersystem.DocketDisplay;
import com.csse3200.game.services.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class DocketDisplayTest {

    private DocketDisplay docketDisplay;

    @BeforeEach
    public void setUp() {

        FileHandle mockFileHandle = mock(FileHandle.class);
        when(mockFileHandle.exists()).thenReturn(true);
        when(mockFileHandle.read()).thenReturn(new ByteArrayInputStream(new byte[0])); // Simulate file contents


        com.badlogic.gdx.Files mockFiles = mock(com.badlogic.gdx.Files.class);
        when(mockFiles.internal("images/ordersystem/docket_background.png")).thenReturn(mockFileHandle);
        Gdx.files = mockFiles; // Inject mock


        ResourceService mockResourceService = mock(ResourceService.class);
        when(mockResourceService.getAsset(eq("images/ordersystem/docket_background.png"), eq(Texture.class)))
                .thenReturn(new Texture("dummy.png"));



        docketDisplay = new DocketDisplay();
        docketDisplay.create(); // Initialize components
    }

    @Test
    public void testDocketDisplayNotNull() {
        assertNotNull(docketDisplay);
    }


}
