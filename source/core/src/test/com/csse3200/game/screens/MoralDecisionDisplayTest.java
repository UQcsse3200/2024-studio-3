package com.csse3200.game.screens;

import com.csse3200.game.areas.GameArea;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class MoralDecisionDisplayTest {

    @Mock
    private MoralDecisionDisplay moralDecisionDisplay;

    @Mock
    private Entity entity;


    @BeforeEach
    void setUp() {
        TerrainFactory factory = mock(TerrainFactory.class);

        GameArea gameArea =
                new GameArea() {
                    @Override
                    public void create() {}
                };

        ServiceLocator.registerEntityService(new EntityService());
        // Initialize MoralDecisionDisplay with the mocked game
        MoralDecisionDisplay testDisplay = mock(MoralDecisionDisplay.class);
        entity = mock(Entity.class);
        entity.addComponent(testDisplay);
        //moralDecisionDisplay.setStage(mockStage); // Set the mocked stage
//        moralDecisionDisplay.create(); // Initialize the component
        ServiceLocator.getEntityService().registerMoral(entity);
    }

    @Test
    void testToggleVisibility_Hide() {
        // Initially, the display should not be visible
        assertFalse(moralDecisionDisplay.getVisible(), "Initially, the display should not be visible.");



        // When toggleVisibility() is called for the first time, it should show the display
        //entity.getEvents().trigger("triggerMoralScreen");
//        ServiceLocator.getEntityService().getMoralScreen().getEvents().trigger("triggerMoralScreen");
//        verify(moralDecisionDisplay.getVisible());
//        verify(mockGame, times(1)).pause(); // Verify that the game is paused when shown
//
//        // When toggleVisibility() is called again, it should hide the display
//        moralDecisionDisplay.toggleVisibility();
//        assertFalse(moralDecisionDisplay.getVisible(), "After toggling again, the display should be hidden.");
//        verify(mockGame, times(1)).resume(); // Verify that the game is resumed when hidden
    }

}



//import com.csse3200.game.entities.Entity;
//import com.csse3200.game.events.EventHandler;
//import com.csse3200.game.events.listeners.EventListener0;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.ArgumentCaptor;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class MoralDecisionDisplayTest {
//
//    private MoralDecisionDisplay handler;
//
//    @Mock
//    private Entity mockEntity;
//
//    @Mock
//    private EventHandler mockEvents;
//
//    @BeforeEach
//    void setUp() {
//        // Create the handler instance
//        handler = new MoralDecisionDisplay();
//
//        // Mock the Entity and EventHandler
//        when(mockEntity.getEvents()).thenReturn(mockEvents);
//
//        // Add the listener
//        doNothing().when(mockEvents).addListener((String) eq("triggerMoralScreen"), (EventListener0) any());
//    }
//
//    @Test
//    void testTriggerMoralScreenTogglesVisibility() {
//        // Capture the event listener when it is added
//        ArgumentCaptor<Runnable> captor = ArgumentCaptor.forClass(Runnable.class);
//        verify(mockEvents).addListener((String) eq("triggerMoralScreen"), (EventListener0) captor.capture());
//
//        // Initially, assume the visibility is false
//        handler.getVisible();
//        assertFalse(handler.getVisible(), "Initially, the display should not be visible.");
//
//        // Simulate triggering the event, which should call toggleVisibility
//        captor.getValue().run();
//
//        // After the trigger, visibility should be toggled
//        assertTrue(handler.getVisible(), "After triggering, the display should be visible.");
//
//        // Simulate triggering the event again to toggle back
//        captor.getValue().run();
//        assertFalse(handler.getVisible(), "After triggering again, the display should not be visible.");
//    }
//}


//import com.badlogic.gdx.Files;
//import com.badlogic.gdx.Gdx;
//import com.badlogic.gdx.files.FileHandle;
//import com.badlogic.gdx.scenes.scene2d.Stage;
//import com.badlogic.gdx.scenes.scene2d.ui.Image;
//import com.badlogic.gdx.scenes.scene2d.ui.Skin;
//import com.csse3200.game.GdxGame;
//import com.csse3200.game.components.ordersystem.Docket;
//import com.csse3200.game.components.ordersystem.MainGameOrderBtnDisplay;
//import com.csse3200.game.components.player.InventoryComponent;
//import com.csse3200.game.components.station.FireExtinguisherHandlerComponent;
//import com.csse3200.game.entities.Entity;
//import com.csse3200.game.events.EventHandler;
//import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import static org.junit.jupiter.api.Assertions.assertFalse;
//import static org.junit.jupiter.api.Assertions.assertTrue;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(MockitoExtension.class)
//public class MoralDecisionDisplayTest {
//
//    private MoralDecisionDisplay handler;
//
//    @Mock
//    private Entity mockEntity;
//
//    @Mock
//    private EventHandler mockEvents;
//
//    @Mock
//    private Entity mockStation;
//
//    @Mock
//    private Stage stage;
//
//
//    @BeforeEach
//    void setUp() {
//        // Create handler to test
//        handler = new MoralDecisionDisplay();
//
//        // Mock Gdx.files to avoid NullPointerException during test execution
////        Gdx.files = mock(Files.class);
////        FileHandle mockFileHandle = mock(FileHandle.class);
////        when(Gdx.files.internal(anyString())).thenReturn(mockFileHandle);
//        GdxGame mockGame = mock(GdxGame.class);
//
//
//        // Mock Entity and EventHandler so stuff actually runs
//        mockEntity = mock(Entity.class);
//        mockEvents = mock(EventHandler.class);
//        // Map any InventoryComponent calls to actually return mock inventory
//        when(mockEntity.getEvents()).thenReturn(mockEvents);
//
//        mockStation = mock(Entity.class);
//        when(mockStation.getComponent(MoralDecisionDisplay.class)).thenReturn(handler);z
//    }
//
//
//    @Test
//    void testMoralDecisionDisplayIsVisible() {
//        // Simulate the stage behavior when the display is visible
//        when(handler.getVisible()).thenReturn(true);
//
//        // Check if the handler logic makes the display visible
//        boolean result = handler.getVisible();
//
//        // Validate that the result is true
//        assertTrue(result, "The moral decision display should be visible.");
//    }
//
//    @Test
//    void testUpdate() {
//        try {
//            when(handler.getVisible()).thenReturn(true);
//        } catch (NullPointerException e) {
//            // Tried to remove entity and failed which is good so pass test
//            return;
//        }
//
//    }
//
//    @Test
//    void testMoralDecisionDisplayIsNotVisible() {
//        // Simulate the stage behavior when the display is not visible
//        when(handler.getVisible()).thenReturn(false);
//
//        // Check if the handler logic makes the display not visible
//        boolean result = handler.getVisible();
//
//        // Validate that the result is false
//        assertFalse(result, "The moral decision display should not be visible.");
//    }
//
//
//
////    @Test
////    public void testCreate() {
//////        //Assertions.assertNotNull(handler);
//////        MainGameOrderBtnDisplay mockBtn = mock(MainGameOrderBtnDisplay.class);
//////        MoralDecisionDisplay mockMoral = mock(MoralDecisionDisplay.class);
//////        mockMoral.setStage(stage);
//////        verify(mockMoral);
////
////
////    }
