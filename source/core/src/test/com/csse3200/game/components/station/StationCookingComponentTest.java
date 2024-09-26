package com.csse3200.game.components.station;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.csse3200.game.components.items.CookIngredientComponent;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
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

@ExtendWith(GameExtension.class)
public class StationCookingComponentTest {

    public Entity mockStation;
    public GameTime mockTime;
    public StationItemHandlerComponent mockItemHandler;
    public StationCookingComponent mockCookingComponent;
    public EventHandler mockEventHandler;
    public CookIngredientComponent mockCookIngredientComponent;
    public IngredientComponent mockIngredientComponent;
    public Entity fishEntity;
    
    @BeforeEach
    public void BeforeEach() {
        // Clear service locator before each
        ServiceLocator.clear();

        // Set-up services for Item entity creation
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        // Set up mock time source
        mockTime = mock(GameTime.class);
        ServiceLocator.registerTimeSource(mockTime);

        // Set up mock event handler
        mockEventHandler = mock(EventHandler.class);

        // Fake a useless recourse service
        ResourceService mockResourceService = mock(ResourceService.class);
        when(mockResourceService.getAsset(anyString(), any())).thenReturn(null);
        ServiceLocator.registerResourceService(mockResourceService);

        // Now creaate our fake entities

        // Create a fish entity
        mockIngredientComponent = mock(IngredientComponent.class);
        when(mockIngredientComponent.getCookTime()).thenReturn(1);
        mockCookIngredientComponent = new CookIngredientComponent();//mock(CookIngredientComponent.class);
        fishEntity = new Entity()
            .addComponent(mockIngredientComponent)
            .addComponent(mockCookIngredientComponent);
        fishEntity.create();

        mockCookIngredientComponent.setEntity(fishEntity);
        mockIngredientComponent.setEntity(fishEntity);
        
        // Create the mock station 
        mockItemHandler = mock(StationItemHandlerComponent.class);
        mockCookingComponent = new StationCookingComponent();

        mockStation = new Entity()
            .addComponent(mockCookingComponent)
            .addComponent(mockItemHandler);

        // Set up the fish entity for cooking and fake it being inside the itemHandler
        when(mockItemHandler.peek()).thenReturn(fishEntity.getComponent(IngredientComponent.class));
        when(mockItemHandler.peek().getEntity()).thenReturn(fishEntity);
        assertNotNull(mockItemHandler.peek());
    }

    @Test
    public void IngredientStartsCooking() {
        // Need to call create on station here
        mockStation.create();

        // Test the ingredient starts cooking
        mockStation.getEvents().trigger("Cook Ingredient");

        //fishEntity.update();

        // Now check the item is cooking
        boolean isCooking = mockItemHandler.peek().getEntity().getComponent(CookIngredientComponent.class).getIsCooking();
        assertTrue(isCooking);
    }

    @Test
    public void IngredientStartsCookingThenStops() {
        // Need to call create on station here
        mockStation.create();

        // Test the ingredient starts cooking
        mockStation.getEvents().trigger("Cook Ingredient");

        //fishEntity.update();

        // Now check the item is cooking
        boolean isCooking = mockItemHandler.peek().getEntity().getComponent(CookIngredientComponent.class).getIsCooking();
        assertTrue(isCooking);

        // Now stop ingredient cooking
        mockStation.getEvents().trigger("Stop Cooking Ingredient");
        //fishEntity.getEvents().trigger("stopCookingIngredient");
        
        isCooking = mockItemHandler.peek().getEntity().getComponent(CookIngredientComponent.class).getIsCooking();
        assertFalse(isCooking);
    }

    @Test
    public void TestIngredientCooks() {
        // Need to call create on station here
        mockStation.create();

        // Set up time
        when(mockTime.getTime()).thenReturn(1000L, 10000L);

        // Test the ingredient starts cooking
        mockStation.getEvents().trigger("Cook Ingredient");
        
        // Now check the item is cooking
        boolean isCooking = mockItemHandler.peek().getEntity().getComponent(CookIngredientComponent.class).getIsCooking();
        assertTrue(isCooking);

        // Now run the update thing
        fishEntity.update();

        // now check if it is cooked
        verify(mockIngredientComponent).cookItem();
        boolean isStillCooking = mockCookIngredientComponent.getIsCooking();
        assertTrue(isStillCooking);
    }

    @Test
    public void TestIngredientBurns() {
        // Need to call create on station here
        mockStation.create();

        // Set up time
        when(mockTime.getTime()).thenReturn(1000L, 20000L);

        // Test the ingredient starts cooking
        mockStation.getEvents().trigger("Cook Ingredient");
        
        // Now check the item is cooking
        boolean isCooking = mockItemHandler.peek().getEntity().getComponent(CookIngredientComponent.class).getIsCooking();
        assertTrue(isCooking);

        // Now run the update thing
        fishEntity.update();

        // now check if it is cooked
        verify(mockIngredientComponent).burnItem();
        boolean isStillCooking = mockCookIngredientComponent.getIsCooking();
        assertFalse(isStillCooking);
    }

    @Test
    public void TestIngredientCooksThenBurns() {
        // Need to call create on station here
        mockStation.create();

        // Set up time
        when(mockTime.getTime()).thenReturn(1000L, 10000L, 10000L, 20000L);

        // Test the ingredient starts cooking
        mockStation.getEvents().trigger("Cook Ingredient");
        
        // Now check the item is cooking
        boolean isCooking = mockItemHandler.peek().getEntity().getComponent(CookIngredientComponent.class).getIsCooking();
        assertTrue(isCooking);

        // Now run the update thing
        fishEntity.update();

        // now check if it is cooked
        verify(mockIngredientComponent).cookItem();
        boolean isStillCooking = mockCookIngredientComponent.getIsCooking();
        assertTrue(isStillCooking);

        // Update the item again
        fishEntity.update();

        verify(mockIngredientComponent).burnItem();
        boolean isStillStillCooking = mockCookIngredientComponent.getIsCooking();
        assertFalse(isStillStillCooking);
    }

    @Test
    public void TestIngredientDoesntBurn() {
        // Need to call create on station here
        mockStation.create();
        
        // Set up time
        when(mockTime.getTime()).thenReturn(1000L, 10000L, 20000L);

        // Test the ingredient starts cooking
        mockStation.getEvents().trigger("Cook Ingredient");
        
        // Now check the item is cooking
        boolean isCooking = mockItemHandler.peek().getEntity().getComponent(CookIngredientComponent.class).getIsCooking();
        assertTrue(isCooking);

        // Now run the update thing
        fishEntity.update();

        // now check if it is cooked
        verify(mockIngredientComponent).cookItem();
        boolean isStillCooking = mockCookIngredientComponent.getIsCooking();
        assertTrue(isStillCooking);

        // Now attempt to stop the cooking
        mockStation.getEvents().trigger("Stop Cooking Ingredient");

        // Update the item again
        fishEntity.update();

        verify(mockIngredientComponent, times(0)).burnItem();
        boolean isStillStillCooking = mockCookIngredientComponent.getIsCooking();
        assertFalse(isStillStillCooking);
    }

}
