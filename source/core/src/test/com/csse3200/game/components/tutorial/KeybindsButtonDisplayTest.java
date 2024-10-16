package com.csse3200.game.components.tutorial;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


public class KeybindsButtonDisplayTest {
    private KeybindsButtonDisplay keybindsButtonDisplay;
    private Stage stage;
    private SpriteBatch batch;
    private Texture keybindsMenuTexture;
    private Table keybindButton;
    private Table keybindsMenu;
    private Table keybindsText;
    private static RenderService mockRenderService;
    private static ResourceService mockResourceService;
    private static Texture mockTexture;

    @BeforeAll
    static void setUpClass() {
        mockRenderService = mock(RenderService.class);
        ServiceLocator.registerRenderService(mockRenderService);

        mockResourceService = mock(ResourceService.class);
        ServiceLocator.registerResourceService(mockResourceService);

        mockTexture = mock(Texture.class);
        when(mockResourceService.getAsset(anyString(), eq(Texture.class))).thenReturn(mockTexture);

    }

    @AfterAll
    static void tearDown() {
        ServiceLocator.clear();
    }

    @BeforeEach
    void setUp() {
        batch = Mockito.mock(SpriteBatch.class);
        stage = Mockito.mock(Stage.class);
        keybindsMenuTexture = Mockito.mock(Texture.class);
        keybindButton = Mockito.mock(Table.class);
        keybindsMenu = Mockito.mock(Table.class);
        keybindsText = Mockito.mock(Table.class);
        Skin skin = mock(Skin.class);
        keybindsButtonDisplay = new KeybindsButtonDisplay(skin);
        keybindsButtonDisplay.setStage(stage);
        keybindsButtonDisplay.setKeybindsMenuTexture(keybindsMenuTexture);
        keybindsButtonDisplay.setButtonTable(keybindButton);
        keybindsButtonDisplay.setMenuTable(keybindsMenu);
        keybindsButtonDisplay.setTextTable(keybindsText);
    }

    /**
     * Tests if batch is initialised.
     */
    @Test
    void testBatchIsNotNull() {
        assertNotNull(batch, "Batch should be initialised");
    }

    /**
     * Checks if components are initialised.
     */
    @Test
    void testInitialiseComponents() {
        assertNotNull(keybindsButtonDisplay, "KeybindsButtonDisplay should be initialised");
        assertNotNull(keybindsButtonDisplay.getStage(), "Stage should be set");
        assertNotNull(keybindButton, "Keybinds button should be initialized");
        assertNotNull(keybindsMenu, "Keybinds menu should be initialised");
        assertNotNull(keybindsText, "Keybinds text should be initialised");
    }

    /**
     * Tests if the menu is toggled when setVisible takes in Boolean values.
     */
    @Test
    void testButtonToggling() {
        assertFalse(keybindsMenu.isVisible(), "Keybinds menu should be initially hidden");
        assertFalse(keybindsText.isVisible(), "Keybinds text should be initially hidden");

        keybindsButtonDisplay.showKeybinds(true);
        verify(keybindsMenu).setVisible(true);
        verify(keybindsText).setVisible(true);

        keybindsButtonDisplay.showKeybinds(false);
        verify(keybindsMenu).setVisible(false);
        verify(keybindsText).setVisible(false);
    }

    /**
     * Tests dispose function.
     */
    @Test
    void testDisposeClearsTables() {
        keybindsButtonDisplay.dispose();

        verify(keybindButton).clear();
        verify(keybindsMenu).clear();
        verify(keybindsText).clear();
    }
}