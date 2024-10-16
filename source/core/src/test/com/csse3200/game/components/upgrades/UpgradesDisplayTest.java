package com.csse3200.game.components.upgrades;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.Viewport;

import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
public class UpgradesDisplayTest {
    @Mock RenderService renderService;
    @Spy OrthographicCamera camera;
    @Mock Stage stage;
    @Mock Viewport viewport;
    @Mock ResourceService resourceService;
    @Mock Texture textureMock;
    @Mock Table upgradesTable;
    @Mock EventHandler mockEventHandler;
    @Mock MainGameScreen mainGameScreen;
    RandomComboService randomComboService;
    private UpgradesDisplay upgradesDisplay;

    private static Stream<String> provideUpgrades() {
        return Stream.of("Speed", "Extortion", "Loan", "Dance party");
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        ServiceLocator.clear();

        randomComboService = spy(new RandomComboService());

        ServiceLocator.registerRandomComboService(randomComboService);
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerGameScreen(mainGameScreen);

        lenient().when(resourceService.getAsset(anyString(), eq(Texture.class))).thenReturn(textureMock);
        lenient().when(renderService.getStage()).thenReturn(stage);
        lenient().when(renderService.getStage().getViewport()).thenReturn(viewport);
        lenient().when(renderService.getStage().getViewport().getCamera()).thenReturn(camera);
        lenient().when(randomComboService.getEvents()).thenReturn(mockEventHandler);

        upgradesDisplay = new UpgradesDisplay(mainGameScreen);
        upgradesDisplay.setUpgradesTable(upgradesTable);
        upgradesDisplay.setStage(stage);
    }

    @Test
    void testVisibilityOnSetup() {
        upgradesDisplay.create();

        assertNotNull(upgradesDisplay);
        assertFalse(upgradesDisplay.isVisible());
        assertFalse(upgradesDisplay.getUpgradesMenuImage().isVisible());
        assertFalse(upgradesDisplay.getUpgradesTable().isVisible());
        assertTrue(upgradesDisplay.getUpgradesTable().getChildren().get(1).isVisible());
        assertTrue(upgradesDisplay.getUpgradesTable().getChildren().get(0).isVisible());
        Table buttonsTable = (Table) upgradesDisplay.getUpgradesTable().getChildren().get(0);
        assertTrue(buttonsTable.getChildren().get(0).isVisible());
        assertTrue(buttonsTable.getChildren().get(1).isVisible());
    }

    @Test
    void testCreateCallsDependencies() {
        upgradesDisplay.create();

        UpgradesDisplay spyUpgradesDisplay = spy(upgradesDisplay);
        spyUpgradesDisplay.create();
        verify(spyUpgradesDisplay).addUpgradeImage();
        verify(spyUpgradesDisplay).createUpgradesMenuDisplay();
        verify(spyUpgradesDisplay).createButtonsTable();
    }

    @Test
    void testToggleVisibilityOn() {
        upgradesDisplay.create();

        upgradesDisplay.toggleVisibility();
        assertTrue(upgradesDisplay.isVisible());
        assertTrue(upgradesDisplay.getUpgradesMenuImage().isVisible());
        assertTrue(upgradesDisplay.getUpgradesTable().isVisible());
        verify(mainGameScreen).pause();
    }

    @Test
    void testToggleVisibilityOff() {
        upgradesDisplay.create();

        upgradesDisplay.toggleVisibility();
        upgradesDisplay.toggleVisibility();
        assertFalse(upgradesDisplay.isVisible());
        assertFalse(upgradesDisplay.getUpgradesMenuImage().isVisible());
        assertFalse(upgradesDisplay.getUpgradesTable().isVisible());
        verify(mainGameScreen).resume();
    }

    @ParameterizedTest
    @MethodSource("provideUpgrades")
    void testAddUpgradeImage(String upgrade) {
        when(randomComboService.getSelectedUpgrade()).thenReturn(upgrade);
        upgradesDisplay.create();

        verify(randomComboService).getSelectedUpgrade();
        String texturePath = switch (upgrade) {
            case "Extortion" -> "images/Extortion1.png";
            case "Speed" -> "images/SpeedBoot.png";
            case "Loan" -> "images/Loan1.png";
            case "Dance party" -> "images/Dance_party.png";
            default -> "";
        };

        verify(resourceService).getAsset("images/Upgrade_display.png", Texture.class);
        verify(resourceService).getAsset(texturePath, Texture.class);
        assertEquals(2, upgradesDisplay.getUpgradesTable().getChildren().size);
        assertInstanceOf(Image.class, upgradesDisplay.getUpgradesTable().getChildren().get(1));
    }

    @Test
    void testCreateButtonsTable() {
        upgradesDisplay.create();

        // Retrieve the buttons table
        Table buttonsTable = (Table) upgradesDisplay.getUpgradesTable().getChildren().get(0);
        assertNotNull(buttonsTable);
        assertEquals(2, buttonsTable.getChildren().size);

        // Check for YES button
        TextButton yesButton = (TextButton) buttonsTable.getChildren().get(0);
        assertNotNull(yesButton);
        assertEquals("YES", yesButton.getText().toString());

        // Check for NO button
        TextButton noButton = (TextButton) buttonsTable.getChildren().get(1);
        assertNotNull(noButton);
        assertEquals("NO", noButton.getText().toString());
    }

    @Test
    void testYesButtonClick() {
        upgradesDisplay.create();
        upgradesDisplay.toggleVisibility();

        upgradesDisplay.simulateYesButtonClick();

        verify(randomComboService).activateUpgrade();
        verify(randomComboService.getEvents()).trigger("response");
        assertFalse(upgradesDisplay.isVisible());
    }

    @Test
    void testNoButtonClick() {
        upgradesDisplay.create();
        upgradesDisplay.toggleVisibility();

        upgradesDisplay.simulateNoButtonClick();

        verify(mainGameScreen, times(2)).resume();
        verify(randomComboService.getEvents()).trigger("response");
        assertFalse(upgradesDisplay.isVisible());
    }

    @Test
    void testDisplayNotEnoughGoldUI() {
        upgradesDisplay.create();
        upgradesDisplay.toggleVisibility();
        upgradesDisplay.displayNotEnoughGoldUI();

        verify(resourceService).getAsset("images/notEnoughGold.png", Texture.class);
        assertEquals(1, upgradesDisplay.getNotEnoughGoldImages().size());
        assertInstanceOf(Image.class, upgradesDisplay.getNotEnoughGoldImages().getFirst());
        assertTrue(upgradesDisplay.getNotEnoughGoldImages().getFirst().isVisible());
    }

    @Test
    void testDispose() {
        upgradesDisplay.dispose();
        verify(resourceService).unloadAssets(upgradesDisplay.getUpgradesMenuTexture());
        verify(resourceService).unloadAssets(upgradesDisplay.getUpgradeTexturePaths());
    }

    @AfterEach
    void tearDown() {
        ServiceLocator.clear();
    }
}
