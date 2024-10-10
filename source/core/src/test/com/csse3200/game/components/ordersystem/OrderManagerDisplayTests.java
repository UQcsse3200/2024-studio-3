package com.csse3200.game.components.ordersystem;

import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class OrderManagerDisplayTests {

    private EventHandler eventHandler;
    private Entity customer;
    private CustomerComponent customerComponent;
    private Recipe recipe;

    @BeforeEach
    void setup(){
        recipe = mock(Recipe.class);
        eventHandler = mock(EventHandler.class);
        EntityService entityService = mock(EntityService.class);
        ServiceLocator.registerEntityService(entityService);

        customer = mock(Entity.class);
        customerComponent = mock(CustomerComponent.class);

        when(customer.getComponent(CustomerComponent.class)).thenReturn(customerComponent);
        when(entityService.getEvents()).thenReturn(eventHandler);
    }

    @Test
    void displayOrderTest_Banana_Split(){
        when(customerComponent.getPreference()).thenReturn("bananaSplit");
        OrderManager.displayOrder(customer);
        verify(eventHandler).trigger("createBananaDocket");
    }

    @Test
    void displayOrderTest_Acai(){
        when(customerComponent.getPreference()).thenReturn("acaiBowl");
        OrderManager.displayOrder(customer);
        verify(eventHandler).trigger("createAcaiDocket");
    }

    @Test
    void displayOrderTest_Salad(){
        when(customerComponent.getPreference()).thenReturn("salad");
        OrderManager.displayOrder(customer);
        verify(eventHandler).trigger("createSaladDocket");
    }

    @Test
    void displayOrderTest_fruitSalad(){
        when(customerComponent.getPreference()).thenReturn("fruitSalad");
        OrderManager.displayOrder(customer);
        verify(eventHandler).trigger("createFruitSaladDocket");
    }

    @Test
    void displayOrderTest_steakMeal(){
        when(customerComponent.getPreference()).thenReturn("steakMeal");
        OrderManager.displayOrder(customer);
        verify(eventHandler).trigger("createSteakDocket");
    }

    @Test
    void displayOrderTest_bananaSplit(){
        when(customerComponent.getPreference()).thenReturn("bananaSplit");
        OrderManager.displayOrder(customer);
        verify(eventHandler).trigger("createBananaDocket");
    }
}
