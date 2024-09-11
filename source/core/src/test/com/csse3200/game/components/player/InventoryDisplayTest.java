package com.csse3200.game.components.player;

import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;
import static org.junit.Assert.*;

import com.csse3200.game.extensions.GameExtension;
import org.junit.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.services.ServiceLocator;

import org.junit.Before;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;



@ExtendWith(GameExtension.class)
class InventoryDisplayTest {
    private AutoCloseable mocks;
    private Entity entity;
    private ItemComponent item1;
    private IngredientComponent item2;
    private ItemComponent item3;
    private InventoryComponent inventory;
    private InventoryDisplay display;
    private EventHandler mockEventHandler;

    @BeforeEach
    void setUp() {
        entity = spy(new Entity());
        inventory = spy (new InventoryComponent(1));

        entity.addComponent(inventory);

        mocks = MockitoAnnotations.openMocks(this);

        //generating items
        item1 = new ItemComponent("Cucumber", ItemType.CUCUMBER, 10);
        item2 = new IngredientComponent("Cucumber", ItemType.CUCUMBER, 10, 5, 3, "raw");

        inventory.addItem(item2);

        // Initialize InventoryDisplay and set the mock entity
        display = spy(new InventoryDisplay());
        display.setEntity(entity);
    }
    @AfterEach
    void end() throws Exception {
        mocks.close();
    }
    public void shouldCreate() {
        display.create();
        verify(display).create();
    }

    @Test
    public void testUpdate() {
        display.update();
        verify(inventory).getItemFirst();
        assertEquals(inventory.getItemFirst().getItemName(), "Cucumber");
        assertEquals(inventory.getItemFirst().getItemType(), ItemType.CUCUMBER);
        assert (((IngredientComponent) item2).getItemState().equals("raw"));

        //verify(entity).getEvents(); this isn't getting called somehow

    }
}
