package com.csse3200.game.components.station;

import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class CookingComponentTest {
    private Entity testEntity;
    private EventHandler testEvents;
    private StationInventoryComponent inventoryComponent;
    private StationItemHandlerComponent handlerComponent;
    private CookingComponent cookingComponent;

    private GameTime mockTime;


    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);

        testEntity = new Entity();
        // testEvents = mock(EventHandler.class);

        inventoryComponent = new StationInventoryComponent();
        testEntity.addComponent(inventoryComponent);

        ArrayList<String> acceptableItems = new ArrayList<>();
        acceptableItems.add("acai");
        acceptableItems.add("banana");
        handlerComponent = spy(new StationItemHandlerComponent("OVEN", acceptableItems));
        testEntity.addComponent(handlerComponent);

        cookingComponent = new CookingComponent();
        testEntity.addComponent(cookingComponent);

        testEntity.create();

        mockTime = mock(GameTime.class);
    }

    @Test
    void testRemoveItem() {
        handlerComponent.giveItem("acai");
        cookingComponent.addItem();
        handlerComponent.giveItem("banana");
        cookingComponent.addItem();
        assertTrue(cookingComponent.isCooking());
        cookingComponent.removeItem();
        assertFalse(cookingComponent.isCooking());
    }

    @Test
    void testGetStationType() {
        assertEquals("OVEN", handlerComponent.getType());
    }

    /**
    @Test
    void testUpdateCooking() {
        cookingComponent.addItem();
        when(mockTime.getDeltaTime()).thenReturn((float) 500L);
        cookingComponent.update();
        assertEquals(9500L, cookingComponent.getCookingTime());

        cookingComponent.update();
        assertEquals(9000, cookingComponent.getCookingTime());
    }

    @Test
    void testGetCookingTime() {
        cookingComponent.addItem();
        assertEquals(10000, cookingComponent.getCookingTime());
    }
    **/
}
