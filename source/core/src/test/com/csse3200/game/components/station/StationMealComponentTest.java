package com.csse3200.game.components.station;

import com.csse3200.game.components.items.CookIngredientComponent;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.items.ItemType;
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

import java.util.ArrayList;

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
    void shouldSetAcceptableItems() {
        ArrayList<String> acceptableItems = new ArrayList<>();
        acceptableItems.add("banana");
        acceptableItems.add("strawberry");
        mealHandler = new StationMealComponent("combining", acceptableItems);
        ArrayList<String> actualItems = mealHandler.acceptableItems;
        assertEquals(acceptableItems, actualItems);
    }

    @Test
    void shouldSetStationType() {
        ArrayList<String> acceptableItems = new ArrayList<>();
        mealHandler = new StationMealComponent("combining", acceptableItems);
        assertEquals("combining", mealHandler.getType());
    }

    // TODO: this test has problems since it always returns true!! Will need to be modified once this method is fixed
    @Test
    void shouldAcceptItem() {
        ArrayList<String> acceptableItems = new ArrayList<>();
        acceptableItems.add("banana");
        acceptableItems.add("strawberry");
        mealHandler = new StationMealComponent("combining", acceptableItems);
        ItemComponent item = new ItemComponent("banana", ItemType.BANANA, 1);
        assertTrue(mealHandler.isItemAccepted(item));
    }

    // might need to include a shouldntAcceptItem() test once this has been implemented


    @Test
    void tooLittleShouldntMakeMeal() {
        ArrayList<String> acceptableItems = new ArrayList<>();
        acceptableItems.add("banana");
        acceptableItems.add("strawberry");
        mealHandler = new StationMealComponent("combining", acceptableItems);

    }

    @Test
    void incorrectIngrsShouldntMakeMeal() {

    }

    @Test
    void shouldMakeAcaiBowl() {

    }

    @Test
    void shouldMakeBananaSplit() {

    }

    @Test
    void shouldMakeFruitSalad() {

    }

    @Test
    void shouldMakeSalad() {
        ArrayList<String> acceptableItems = new ArrayList<>();
        acceptableItems.add("tomato");
        acceptableItems.add("cucumber");
        acceptableItems.add("lettuce");
        mealHandler = new StationMealComponent("combining", acceptableItems);


    }

    @Test
    void shouldMakeSteakMeal() {

    }


}
