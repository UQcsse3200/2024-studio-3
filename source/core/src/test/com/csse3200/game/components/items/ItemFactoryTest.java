package com.csse3200.game.components.items;

import com.csse3200.game.components.items.*;
import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.components.ColliderComponent;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.junit.jupiter.api.Assertions.*;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.physics.components.HitboxComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.*;
import com.csse3200.game.services.*;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ItemFactoryTest {

    @BeforeEach
    void setUp() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        ResourceService mockResourceService = mock(ResourceService.class);
        when(mockResourceService.getAsset(anyString(), any())).thenReturn(null);
        ServiceLocator.registerResourceService(mockResourceService);
    }

    @Test
    void createTemplateItem() {
        PhysicsService physicsService = new PhysicsService();
        ServiceLocator.registerPhysicsService(physicsService);
        Entity templateItem = ItemFactory.createTemplateItem();
        assertNotNull(templateItem);
        assertNotNull(templateItem.getComponent(HitboxComponent.class));
        assertNotNull(templateItem.getComponent(PhysicsComponent.class));
        assertNotNull(templateItem.getComponent(ColliderComponent.class));
    }

    void baseComponentsAssertion(Entity e) {
        assertNotNull(e.getComponent(PhysicsComponent.class));
        assertNotNull(e.getComponent(HitboxComponent.class));
        assertNotNull(e.getComponent(ColliderComponent.class));
    }

    @Test
    void createFish() {
        Entity fish = ItemFactory.createFish("raw");
        assertEquals(ItemType.FISH, fish.getComponent(IngredientComponent.class).getItemType());
        baseComponentsAssertion(fish);
    }

    @Test
    void createBeef() {
        Entity beef = ItemFactory.createBeef("raw");
        assertEquals(ItemType.BEEF, beef.getComponent(IngredientComponent.class).getItemType());
        baseComponentsAssertion(beef);
    }

    @Test
    void createBanana() {
        Entity banana = ItemFactory.createBanana("raw");
        assertEquals(ItemType.BANANA, banana.getComponent(IngredientComponent.class).getItemType());
        baseComponentsAssertion(banana);
    }

    @Test
    void createCucumber() {
        Entity cucumber = ItemFactory.createCucumber("raw");
        assertEquals(ItemType.CUCUMBER, cucumber.getComponent(IngredientComponent.class).getItemType());
        baseComponentsAssertion(cucumber);
    }

    @Test
    void createTomato() {
        Entity tomato = ItemFactory.createTomato("raw");
        assertEquals(ItemType.TOMATO, tomato.getComponent(IngredientComponent.class).getItemType());
        baseComponentsAssertion(tomato);
    }

    @Test
    void createStrawberry() {
        Entity strawberry = ItemFactory.createStrawberry("raw");
        assertEquals(ItemType.STRAWBERRY, strawberry.getComponent(IngredientComponent.class).getItemType());
        baseComponentsAssertion(strawberry);
    }

    @Test
    void createLettuce() {
        Entity lettuce = ItemFactory.createLettuce("raw");
        assertEquals(ItemType.LETTUCE, lettuce.getComponent(IngredientComponent.class).getItemType());
        baseComponentsAssertion(lettuce);
    }

    @Test
    void createChocolate() {
        Entity chocolate = ItemFactory.createChocolate("raw");
        assertEquals(ItemType.CHOCOLATE, chocolate.getComponent(IngredientComponent.class).getItemType());
        baseComponentsAssertion(chocolate);
    }

    @Test
    void createAcai() {
        Entity acai = ItemFactory.createAcai("raw");
        assertEquals(ItemType.ACAI, acai.getComponent(IngredientComponent.class).getItemType());
        baseComponentsAssertion(acai);
    }

    @Test
    void createFruitSalad() {
        Entity fruitSalad = ItemFactory.createFruitSalad();
        assertEquals(ItemType.FRUITSALAD, fruitSalad.getComponent(MealComponent.class).getItemType());
        baseComponentsAssertion(fruitSalad);
    }

    @Test
    void createAcaiBowl() {
        Entity acaiBowl = ItemFactory.createAcaiBowl();
        assertEquals(ItemType.ACAIBOWL, acaiBowl.getComponent(MealComponent.class).getItemType());
        baseComponentsAssertion(acaiBowl);
    }

    @Test
    void createSalad() {
        Entity salad = ItemFactory.createSalad();
        assertEquals(ItemType.SALAD, salad.getComponent(MealComponent.class).getItemType());
        baseComponentsAssertion(salad);
    }

    @Test
    void createSteakMeal() {
        Entity steakMeal = ItemFactory.createSteakMeal();
        assertEquals(ItemType.STEAKMEAL, steakMeal.getComponent(MealComponent.class).getItemType());
        baseComponentsAssertion(steakMeal);
    }

    @Test
    void createBananaSplit() {
        Entity bananaSplit = ItemFactory.createBananaSplit();
        assertEquals(ItemType.BANANASPLIT, bananaSplit.getComponent(MealComponent.class).getItemType());
        baseComponentsAssertion(bananaSplit);
    }



}
