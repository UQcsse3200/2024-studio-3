package com.csse3200.game.components.station;

import com.csse3200.game.components.items.CookIngredientComponent;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class StationMealComponentTest {
    protected StationMealComponent mealHandler;
    protected InventoryComponent stationInventory;
    protected InventoryComponent playerInventory;
    protected Entity mockEntity;
    protected Entity mockStation;
    protected EventHandler mockStationEvents;
    protected EventHandler mockPlayerEvents;

    @BeforeEach
    void setUp() {
        // mock the entities and events
        mockEntity = mock(Entity.class);
        mockStation = mock(Entity.class);
        mockStationEvents = mock(EventHandler.class);

        // and the inventories
        playerInventory = new InventoryComponent(1);
        stationInventory = new InventoryComponent(2); // double check, might be initialised to 1

        // map calls to entities to return correct things
        when(mockEntity.getEvents()).thenReturn(mockPlayerEvents);
        when(mockStation.getEvents()).thenReturn(mockStationEvents);
        when(mockEntity.getComponent(InventoryComponent.class)).thenReturn(playerInventory);
        when(mockStation.getComponent(InventoryComponent.class)).thenReturn(stationInventory);

        // check this is the right thing
        when(mockStation.getComponent(StationMealComponent.class)).thenReturn(mealHandler);

        // I dont know if more needs to go in here?
    }

    @Test
    void shouldGetAcceptableItems() {

    }



}
