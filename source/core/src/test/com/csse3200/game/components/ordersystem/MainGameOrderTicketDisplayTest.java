package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.DocketService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;


import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import java.util.Random;

import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class MainGameOrderTicketDisplayTest {
    @Mock RenderService renderService;
    @Spy OrthographicCamera camera;
    @Mock Stage stage;
    @Mock Viewport viewport;
    @Mock DocketService docketService;
    @Mock EventHandler eventHandler;
    MainGameOrderTicketDisplay orderTicketDisplay;

    @BeforeEach
    void setUp() {
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerDocketService(docketService);
        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerDocketService(docketService);

        when(ServiceLocator.getRenderService().getStage()).thenReturn(stage);
        when(ServiceLocator.getRenderService().getStage().getViewport()).thenReturn(viewport);
        lenient().when(ServiceLocator.getRenderService().getStage().getViewport().getCamera()).thenReturn(camera);
        when(ServiceLocator.getDocketService().getEvents()).thenReturn(eventHandler);

        orderTicketDisplay = new MainGameOrderTicketDisplay();
        String[] recipeNames = {"acaiBowl", "salad", "fruitSalad", "steakMeal", "bananaSplit"};
        String randomRecipe = recipeNames[new Random().nextInt(recipeNames.length)];
        orderTicketDisplay.setRecipe(randomRecipe);
        Entity entity = new Entity();
        entity.addComponent(orderTicketDisplay);
        orderTicketDisplay.create();
    }

    @AfterEach
    void tearDown() {
        orderTicketDisplay.getTableArrayList().clear();
    }

    @Test
    public void testCreateInitializesComponents() {
        orderTicketDisplay.create();

        assertNotNull("Table should be initialized", orderTicketDisplay.getTableArrayList());
        assertNotNull("Countdown label should be initialized", orderTicketDisplay.getCountdownLabelArrayList());
        Assert.assertNotNull(
                "Start time should be set", orderTicketDisplay.getStartTimeArrayList());
    }

    @Test
    public void testAddActorsAddsUIComponents() {
        orderTicketDisplay.addActors(); // This will call addActors()

        verify(stage).addActor(any(Table.class));
    }

    @Test
    void testAddActors() {
        orderTicketDisplay.addActors();

        verify(stage).addActor(any(Table.class));

        assertEquals(1, orderTicketDisplay.getTableArrayList().size());

        Table table = orderTicketDisplay.getTableArrayList().getFirst();
        Assertions.assertNotNull(table);

        Label countdownLabel = MainGameOrderTicketDisplay.getCountdownLabelArrayList().getFirst();
        Assertions.assertNotNull(countdownLabel);
        assertEquals("Timer: 30000", countdownLabel.getText().toString());
    }

    @Test
    void shouldAddTablesToArrayAndRemove() {
        orderTicketDisplay.addActors();
        orderTicketDisplay.addActors();

        assertEquals(2, (orderTicketDisplay.getTableArrayList()).size());
        orderTicketDisplay.getTableArrayList().clear();
        assertEquals(0, (orderTicketDisplay.getTableArrayList()).size());
    }

    @Test
    void testEnlargementOfLastDocket() {
        for (int i = 0; i < 5; i++) {
            orderTicketDisplay.addActors();
        }
        orderTicketDisplay.updateDocketSizes();
        Table lastTable = MainGameOrderTicketDisplay.getTableArrayList().get(MainGameOrderTicketDisplay.getTableArrayList().size() - 1);
        assertEquals(170f * (orderTicketDisplay.getViewportWidth()/1920f), lastTable.getWidth(), 0.1f);
        assertEquals(200f * (orderTicketDisplay.getViewportHeight()/1080f), lastTable.getHeight(), 0.1f);

        float expectedX = orderTicketDisplay.getViewportWidth() - 320f;
        float expectedY = 900f * (orderTicketDisplay.getViewportHeight()/1080f);

        assertEquals(expectedX, lastTable.getX(), 0.1f);
        assertEquals(expectedY, lastTable.getY(), 0.1f);

    }


    @Test
    public void testUpdateCountdownDecreasesCorrectly() {
        orderTicketDisplay.create();
        orderTicketDisplay.addActors();
        long startTime = orderTicketDisplay.getStartTimeArrayList().get(0);
        long elapsedTime = TimeUtils.timeSinceMillis(startTime);
        orderTicketDisplay.update();
        Assert.assertEquals(
                "Timer should orderTicketDisplay correct countdown", "Timer: " +
                        ((orderTicketDisplay.getTimer() - elapsedTime)/1000),
                            orderTicketDisplay.getCountdownLabelArrayList().get(0).getText().toString());
    }

    @Test
    public void testDisposeClearsComponents() {
        orderTicketDisplay.addActors();
        stage.dispose();

        verify(stage).dispose();
    }

    @Test
    void testStageDispose() {
        orderTicketDisplay.addActors();
        Table table = orderTicketDisplay.getTableArrayList().getFirst();

        Docket background = orderTicketDisplay.getBackgroundArrayList().getFirst();

        boolean hasChildrenBeforeDispose = table.getChildren().isEmpty();

        orderTicketDisplay.stageDispose(background, table, 0);

        assertTrue(table.getChildren().isEmpty());
        assertFalse(hasChildrenBeforeDispose, "Table should be cleared of children.");
    }

    @Test
    void testSetStage() {
        orderTicketDisplay.setStage(stage);
        when(ServiceLocator.getRenderService().getStage()).thenReturn(stage);
        assertEquals(ServiceLocator.getRenderService().getStage(), stage);
    }


    @Test
    void testEnlargementOfSingleDocket() {
        orderTicketDisplay.addActors();
        orderTicketDisplay.updateDocketSizes();
        Table singleTable = MainGameOrderTicketDisplay.getTableArrayList().get(0);

        assertEquals(
                170f * (orderTicketDisplay.getViewportWidth()/1920f), singleTable.getWidth(),
                        0.1f, "Docket width is incorrect.");
        assertEquals(200f * (orderTicketDisplay.getViewportHeight()/1080f), singleTable.getHeight(),
                        0.1f, "Docket height is incorrect.");

        float expectedX = orderTicketDisplay.getViewportWidth() - 320f; //orderTicketDisplay.getViewportWidth() - 260f
        float expectedY = 900f * (orderTicketDisplay.getViewportHeight()/1080f);

        assertEquals(expectedX, singleTable.getX(), 0.1f, "Docket X position is incorrect.");
        assertEquals(expectedY, singleTable.getY(), 0.1f, "Docket Y position is incorrect.");
    }

    @Test
    void testNotEnlargedDocketSizes() {
        for (int i = 0; i < 5; i++) {
            orderTicketDisplay.addActors();
        }

        orderTicketDisplay.updateDocketSizes();
        for (int i = 0; i < orderTicketDisplay.getTableArrayList().size() - 1; i++) {
            Table table = orderTicketDisplay.getTableArrayList().get(i);
            assertEquals(120f * (orderTicketDisplay.getViewportWidth()/1920f), table.getWidth(), 0.1f);
            assertEquals(150f * (orderTicketDisplay.getViewportHeight()/1080f), table.getHeight(), 0.1f);
        }
    }

    @Test
    void testGetZIndex() {
        assertEquals(2f, orderTicketDisplay.getZIndex());
    }

}


