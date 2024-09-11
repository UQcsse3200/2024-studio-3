package com.csse3200.game.components.player;

import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;

import com.csse3200.game.extensions.GameExtension;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;

import org.junit.Before;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;



@ExtendWith(GameExtension.class)
class InventoryDisplayTest {
    private AutoCloseable mocks;
    private Entity entity;

    ItemComponent item1;
    IngredientComponent item2;
    ItemComponent item3;
    InventoryComponent inventory;
    InventoryDisplay display;

    Entity mockEntity;
    EventHandler mockEventHandler;

    @BeforeEach
    void setUp() {
        //mocks
        mockEntity = mock(Entity.class);
        mockEventHandler = mock(EventHandler.class);
        inventory = mock(InventoryComponent.class);
        when(mockEntity.getComponent(InventoryComponent.class)).thenReturn(inventory);
        when(mockEntity.getEvents()).thenReturn(mockEventHandler);

        mocks = MockitoAnnotations.openMocks(this);
        entity = new Entity();

        //generating items
        item1 = new ItemComponent("Cucumber", ItemType.CUCUMBER, 10);
        item2 = new IngredientComponent("Cucumber", ItemType.CUCUMBER, 10,
                5, 3, "raw");
        //inventory = new InventoryComponent(2, 50);


        // Initialize InventoryDisplay and set the mock entity
        display = new InventoryDisplay();
        display.setEntity(mockEntity);
    }

    @Test
    public void testUpdate() {
        inventory.addItem(item2);
        display.update();
        verify(mockEventHandler).trigger("updateAnimationRawCucumber");
    }
}
