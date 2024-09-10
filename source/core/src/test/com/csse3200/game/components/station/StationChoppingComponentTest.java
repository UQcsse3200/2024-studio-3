package com.csse3200.game.components.station;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.csse3200.game.components.items.ChopIngredientComponent;
import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.extensions.GameExtension;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GameExtension.class)
public class StationChoppingComponentTest {

    public Entity mockStation;
    public GameTime mockTime;
    public StationItemHandlerComponent mockItemHandler;
    public StationChoppingComponent mockChopingComponent;
    public EventHandler mockEventHandler;
    public ChopIngredientComponent mockChopIngredientComponent;
    public IngredientComponent mockIngredientComponent;
    public Entity chopEntity;

    @BeforeEach
    public void BeforeEach() {
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
        mockChopIngredientComponent = new ChopIngredientComponent();//mock(CookIngredientComponent.class);
        chopEntity = new Entity()
            .addComponent(mockIngredientComponent)
            .addComponent(mockChopIngredientComponent);
        chopEntity.create();

        mockChopIngredientComponent.setEntity(chopEntity);
        mockIngredientComponent.setEntity(chopEntity);
        
        // Create the mock station 
        mockItemHandler = mock(StationItemHandlerComponent.class);
        mockChopingComponent = new StationChoppingComponent();

        mockStation = new Entity()
            .addComponent(mockChopingComponent)
            .addComponent(mockItemHandler);
        mockStation.create();

        // Set up the fish entity for cooking and fake it being inside the itemHandler
        when(mockItemHandler.peek()).thenReturn(chopEntity.getComponent(IngredientComponent.class));
        when(mockItemHandler.peek().getEntity()).thenReturn(chopEntity);
        assertNotNull(mockItemHandler.peek());
    }

    @Test
    public void IngredientStartsChopping() {

        // Test the ingredient starts cooking
        mockStation.getEvents().trigger("Chop Ingredient");

        //chopEntity.update();

        // Now check the item is cooking
        boolean isChopping = mockItemHandler.peek().getEntity().getComponent(ChopIngredientComponent.class).getIsChopping();
        assertTrue(isChopping);
    }

    @Test
    public void IngredientStartsChoppingThenStops() {

        // Test the ingredient starts cooking
        mockStation.getEvents().trigger("Chop Ingredient");

        //chopEntity.update();

        // Now check the item is cooking
        boolean isChopping = mockItemHandler.peek().getEntity().getComponent(ChopIngredientComponent.class).getIsChopping();
        assertTrue(isChopping);

        // Now stop ingredient cooking
        mockStation.getEvents().trigger("Stop Chopping Ingredient");
        
        isChopping = mockItemHandler.peek().getEntity().getComponent(ChopIngredientComponent.class).getIsChopping();
        assertFalse(isChopping);
    }

    @Test
    public void TestIngredientChops() {
        // Set up time
        when(mockTime.getTime()).thenReturn(1000L, 10000L);

        // Test the ingredient starts cooking
        mockStation.getEvents().trigger("Chop Ingredient");
        
        // Now check the item is cooking
        boolean isChopping = mockItemHandler.peek().getEntity().getComponent(ChopIngredientComponent.class).getIsChopping();
        assertTrue(isChopping);

        // Now run the update thing
        chopEntity.update();

        // now check if it is cooked
        verify(mockIngredientComponent).chopItem();
        boolean isStillChopping = mockChopIngredientComponent.getIsChopping();
        assertFalse(isStillChopping);
    }
    
}
