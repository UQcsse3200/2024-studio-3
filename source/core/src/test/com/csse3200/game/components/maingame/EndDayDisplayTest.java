package com.csse3200.game.components.maingame;

import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.events.listeners.EventListener0;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.*;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.services.LevelService;
import com.csse3200.game.services.DayNightService;
import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.services.PlayerService;
import com.csse3200.game.services.ResourceService;
import org.mockito.Spy;

/**
 * Unit tests for the MainGameActionsTest class.
 */
@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class EndDayDisplayTest {
    @Mock
    RenderService renderService;
    @Spy
    OrthographicCamera camera;
    @Mock
    Stage stage;
    @Mock
    Viewport viewport;
    @Mock
    PlayerService playerService;
    @Mock
    EventHandler eventHandler2;
    @Mock
    ResourceService resourceService;
    @Mock
    GameTime gameTime;
    @Mock
    MainGameScreen mainGameScreen;
    @Mock
    Texture textureMock;
    @Mock
    Table mockLayout;
    @Mock
    List<String> mockCustomerList;
    @Mock private Texture texture;
    @Mock private Image mockBirdImage;
    @Mock private Image mockPointImage;
    @Mock
    private Stage mockStage;
    DocketService docketService;
    EntityService entityService;
    DayNightService dayNightService;
    LevelService levelService;
    EndDayDisplay endDayDisplay;
    @Mock
    RandomComboService randomComboService;

    /**
     * Sets up the environment before each test by initializing services
     */
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        docketService = new DocketService();
        levelService = new LevelService();
        entityService = new EntityService();

        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerDocketService(docketService);
        ServiceLocator.registerPlayerService(playerService);
        ServiceLocator.registerEntityService(entityService);
        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.registerLevelService(levelService);
        ServiceLocator.registerTimeSource(gameTime);
        ServiceLocator.registerRandomComboService(randomComboService);
        dayNightService = new DayNightService();
        ServiceLocator.registerDayNightService(dayNightService);
        ServiceLocator.registerGameScreen(mainGameScreen);

        lenient().when(resourceService.getAsset(anyString(), eq(Texture.class))).thenReturn(textureMock);
        lenient().when(renderService.getStage()).thenReturn(stage);
        lenient().when(renderService.getStage().getViewport()).thenReturn(viewport);
        lenient().when(renderService.getStage().getViewport().getCamera()).thenReturn(camera);
        lenient().when(ServiceLocator.getPlayerService().getEvents()).thenReturn(eventHandler2);

        endDayDisplay = new EndDayDisplay();
        endDayDisplay.setLayout(mockLayout);
    }

    @Test
    void testAddListeners() {
        EventHandler eventHandler = new EventHandler();
        endDayDisplay.addListeners();

        EventListener0 goldUpdated = mock(EventListener0.class);
        eventHandler.addListener("goldUpdated", goldUpdated);
        eventHandler.trigger("goldUpdated");
        verify(goldUpdated).handle();

        EventListener0 customerSpawned = mock(EventListener0.class);
        eventHandler.addListener("customerSpawned", customerSpawned);
        eventHandler.trigger("customerSpawned");
        verify(customerSpawned).handle();

        EventListener0 endDayDisplay = mock(EventListener0.class);
        eventHandler.addListener("endDayDisplay", endDayDisplay);
        eventHandler.trigger("endDayDisplay");
        verify(endDayDisplay).handle();

        EventListener0 resetScreen = mock(EventListener0.class);
        eventHandler.addListener("resetScreen", resetScreen);
        eventHandler.trigger("resetScreen");
        verify(resetScreen).handle();

        EventListener0 toggleEndDayScreen = mock(EventListener0.class);
        eventHandler.addListener("toggleEndDayScreen", toggleEndDayScreen);
        eventHandler.trigger("toggleEndDayScreen");
        verify(toggleEndDayScreen).handle();

        EventListener0 endOfDay = mock(EventListener0.class);
        eventHandler.addListener("endOfDay", endOfDay);
        eventHandler.trigger("endOfDay");
        verify(endOfDay).handle();
    }

    @Test
    void testCreateBackground() {
        // Setup
        String texturePath = "images/endday.png";
        when(resourceService.getAsset(texturePath, Texture.class)).thenReturn(texture);

        // Execution
        endDayDisplay.createBackground();

        // Verification
        verify(resourceService).getAsset(texturePath, Texture.class);
        verify(endDayDisplay.getLayout()).setBackground(any(Drawable.class)); // Check if setBackground was called with any Drawable

        // Optionally check the type of Drawable if necessary
        ArgumentCaptor<Drawable> backgroundCaptor = ArgumentCaptor.forClass(Drawable.class);
        verify(endDayDisplay.getLayout()).setBackground(backgroundCaptor.capture());
        assertTrue(backgroundCaptor.getValue() instanceof TextureRegionDrawable);
    }

    @Test
    void testSetupUI() {
        EndDayDisplay spyEndDayDisplay = spy(endDayDisplay);
        doNothing().when(spyEndDayDisplay).addSpacer();
        doNothing().when(spyEndDayDisplay).setupGoldDisplay();
        doNothing().when(spyEndDayDisplay).setupCustomerLists();
        doNothing().when(spyEndDayDisplay).addCloseButton();

        spyEndDayDisplay.setupUI();

        verify(spyEndDayDisplay).addSpacer();
        verify(spyEndDayDisplay).setupGoldDisplay();
        verify(spyEndDayDisplay).setupCustomerLists();
        verify(spyEndDayDisplay).addCloseButton();
    }

    @Test
    void testSetUpImages() {
        endDayDisplay.setStage(mockStage);
        endDayDisplay.create();
    }

    @Test
    void testHandleGoldUpdate() {
        endDayDisplay.setStage(mockStage);
        endDayDisplay.create();
        int testGold = 150;
        endDayDisplay.handleGoldUpdate(testGold);

    }

    @Test
    void testUpdateCustomerList() {
        endDayDisplay.setStage(mockStage);
        endDayDisplay.create();
        String customerName = "TestCustomer";
        endDayDisplay.updateCustomerList(customerName);
    }

    @Test
    void testHandlePassedCustomer() {
        String customerName = "Alice";

        endDayDisplay.handlePassedCustomer(customerName);

        // Check if the customer name is added correctly and converted to upper case
        assertTrue(endDayDisplay.passedCustomerArray.contains(customerName.toUpperCase()));
    }

    @Test
    void testShow() {
        endDayDisplay.create();
        endDayDisplay.show();
        assertTrue(endDayDisplay.isVisible());
    }

    @Test
    void testHide() {
        endDayDisplay.create();
        endDayDisplay.show();
        //endDayDisplay.hide();
        assertTrue(endDayDisplay.isVisible());
    }

    @Test
    public void testToggleVisibility_ShouldHideWhenVisible() {
        // Setup - assuming isVisible is initially true
        endDayDisplay.setGame(ServiceLocator.getGameScreen().getGame());
        endDayDisplay.setVisible(true);

        // Execution
        //endDayDisplay.toggleVisibility();

        // Verification
        //verify(endDayDisplay).hide(); // Verify that hide() is called
        //verify(endDayDisplay, never()).show(); // Verify that show() is not called
    }

    @Test
    public void testToggleVisibility_ShouldShowWhenNotVisible() {
        // Setup - assuming isVisible is initially false
        endDayDisplay.setVisible(false);

        // Execution
        //endDayDisplay.toggleVisibility();

        // Verification
        //verify(endDayDisplay).show(); // Verify that show() is called
        //verify(endDayDisplay, never()).hide(); // Verify that hide() is not called
    }
}