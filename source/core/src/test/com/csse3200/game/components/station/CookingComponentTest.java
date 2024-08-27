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

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class CookingComponentTest {

    private CookingComponent cookingComponent;
    private StationInventoryComponent mockInventory;
    private StationItemHandlerComponent itemHandle;
    private GameTime mockTime;
    private Entity mockEntity;
    private EventHandler mockEvents;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        ArrayList<String> acceptableItems = new ArrayList<>();
        acceptableItems.add("acai");
        acceptableItems.add("banana");
        itemHandle = new StationItemHandlerComponent("OVEN", acceptableItems);
        mockInventory = mock(StationInventoryComponent.class);

        mockEntity = mock(Entity.class);
        mockEvents = mock(EventHandler.class);

        when(mockEntity.getComponent(StationInventoryComponent.class)).thenReturn(mockInventory);
        when(mockEntity.getEvents()).thenReturn(mockEvents);

        itemHandle.setEntity(mockEntity);
        itemHandle.create();

        cookingComponent = new CookingComponent();
        cookingComponent.setEntity(mockEntity);
        cookingComponent.create();
    }

    /**
    @Test
    void testRemoveItem() {
        cookingComponent.addItem();
        assertTrue(cookingComponent.isCooking());
        cookingComponent.removeItem();
        assertFalse(cookingComponent.isCooking());
    }
    **/
    @Test
    void testGetStationType() {
        assertEquals("OVEN", itemHandle.getType());
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
