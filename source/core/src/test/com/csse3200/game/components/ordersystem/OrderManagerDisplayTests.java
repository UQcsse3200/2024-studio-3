package com.csse3200.game.components.ordersystem;

import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.params.ParameterizedTest;

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


    @ParameterizedTest
    @ValueSource(strings = {"bananaSplit", "acaiBowl", "salad", "fruitSalad", "steakMeal", "bananaSplit"})
    void testRecipes(String candidate){
        when(customerComponent.getPreference()).thenReturn(candidate);
        OrderManager.displayOrder(customer);
        String response = switch (candidate) {
            case "bananaSplit" -> "createBananaDocket";
            case "acaiBowl" -> "createAcaiDocket";
            case "salad" -> "createSaladDocket";
            case "fruitSalad" -> "createFruitSaladDocket";
            case "steakMeal" -> "createSteakDocket";
            default -> throw new IllegalStateException("Unexpected value: " + candidate);
        };
        verify(eventHandler).trigger(response);

    }
}
