//package com.csse3200.game.components.station;
//
//import com.csse3200.game.events.EventHandler;
//import com.csse3200.game.extensions.GameExtension;
//import com.csse3200.game.services.GameTime;
//import com.csse3200.game.entities.Entity;
//import com.csse3200.game.services.ServiceLocator;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.MockitoAnnotations;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Optional;
//
//import static org.junit.jupiter.api.Assertions.*;
//import static org.mockito.Mockito.*;
//
//@ExtendWith(GameExtension.class)
//public class CookingComponentTest {
//    private GameTime mockTime;
//    private Entity testEntity;
//    private StationInventoryComponent inventoryComponent;
//    private StationItemHandlerComponent handlerComponent;
//    private CookingComponent cookingComponent;
//
//
//    @BeforeEach
//    void init() {
//        MockitoAnnotations.openMocks(this);
//
//        mockTime = mock(GameTime.class);
//        ServiceLocator.registerTimeSource(mockTime);
//
//        testEntity = new Entity();
//
//        inventoryComponent = spy(new StationInventoryComponent()); // Spies on inventory component
//        testEntity.addComponent(inventoryComponent);
//
//        ArrayList<String> acceptableItems = new ArrayList<>();
//        acceptableItems.add("acai");
//        acceptableItems.add("banana");
//        handlerComponent = spy(new StationItemHandlerComponent("OVEN", acceptableItems)); // Creates spy on
//                                                                                                // Handler component
//        testEntity.addComponent(handlerComponent);
//
//        cookingComponent = spy(new CookingComponent());
//        testEntity.addComponent(cookingComponent);
//
//        testEntity.create();
//    }
//
//    /**
//     * Tests that the event trigger affects both
//     * CookingComponent.addItem and StationItemHandlerComponent.giveItem.
//     */
//    @Test
//    void testEvent() {
//        testEntity.getEvents().trigger("give station item", "acai");
//        verify(handlerComponent).giveItem("acai"); // verifies that giveItem method was called
//        verify(cookingComponent).addItem("acai"); // verifies that addItem method was called
//    }
//
//    @Test
//    void testAddOneItem() {
//        testEntity.getEvents().trigger("give station item", "banana");
//        assertFalse(cookingComponent.isCooking());
//    }
//
//    @Test
//    void testRemoveItem() {
//        testEntity.getEvents().trigger("give station item", "acai");
//        testEntity.getEvents().trigger("give station item", "banana");
//        assertTrue(cookingComponent.isCooking()); // Chekcs that cooking started
//        cookingComponent.removeItem(); // Removes
//        assertFalse(cookingComponent.isCooking()); // Checks cooking stopped after being removed
//    }
//
//    @Test
//    void testGetStationType() {
//        assertEquals("OVEN", handlerComponent.getType()); // checks station type is set correctly
//    }
//
//    @Test
//    void testUpdateCooking() {
//        testEntity.getEvents().trigger("give station item", "acai");
//        testEntity.getEvents().trigger("give station item", "banana");
//
//        when(mockTime.getDeltaTime()).thenReturn((float) 500L); // Mocks delta time to simulate time elapsing
//        cookingComponent.update();
//        assertEquals(9500L, cookingComponent.getCookingTime()); // checks cooking time has decreased by 500
//        cookingComponent.update();
//        assertEquals(9000, cookingComponent.getCookingTime());
//    }
//
//    @Test
//    void testGetCookingTime() {
//        testEntity.getEvents().trigger("give station item", "acai");
//        testEntity.getEvents().trigger("give station item", "banana");
//        assertEquals(10000, cookingComponent.getCookingTime()); // Verifies initial cooking time
//    }
//}
