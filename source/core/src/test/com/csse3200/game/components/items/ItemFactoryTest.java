package com.csse3200.game.components.items;

import java.util.Arrays;

import com.csse3200.game.entities.factories.ItemFactory;
import com.csse3200.game.physics.components.ColliderComponent;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import com.badlogic.gdx.scenes.scene2d.ui.List;
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
        java.util.List<IngredientComponent> ingredients = Arrays.asList(
            new IngredientComponent("Banana", ItemType.BANANA, 1, 3, 10,"chopped"),
            new IngredientComponent("Strawberry", ItemType.STRAWBERRY, 1, 3, 10, "chopped"));
        Entity fruitSalad = ItemFactory.createFruitSalad(ingredients);
        assertEquals(ItemType.FRUITSALAD, fruitSalad.getComponent(MealComponent.class).getItemType());
        baseComponentsAssertion(fruitSalad);
    }

    @Test
    void createAcaiBowl() {
        java.util.List<IngredientComponent> ingredients = Arrays.asList(
            new IngredientComponent("Banana", ItemType.BANANA, 1, 3, 10,"chopped"),
            new IngredientComponent("Acai", ItemType.ACAI, 1, 3, 10, "chopped"));
        Entity acaiBowl = ItemFactory.createAcaiBowl(ingredients);
        assertEquals(ItemType.ACAIBOWL, acaiBowl.getComponent(MealComponent.class).getItemType());
        baseComponentsAssertion(acaiBowl);
    }

    @Test
    void createSalad() {
        java.util.List<IngredientComponent> ingredients = Arrays.asList(
            new IngredientComponent("Lettuce", ItemType.LETTUCE, 1, 3, 10,"chopped"),
            new IngredientComponent("Tomato", ItemType.TOMATO, 1, 3, 10, "chopped"),
            new IngredientComponent("Cucumber", ItemType.CUCUMBER, 1, 3, 10, "chopped"));
        Entity salad = ItemFactory.createSalad(ingredients);
        assertEquals(ItemType.SALAD, salad.getComponent(MealComponent.class).getItemType());
        baseComponentsAssertion(salad);
    }

    @Test
    void createSteakMeal() {
        java.util.List<IngredientComponent> ingredients = Arrays.asList(
            new IngredientComponent("Beef", ItemType.BEEF, 1, 3, 10,"raw"),
            new IngredientComponent("Tomato", ItemType.TOMATO, 1, 3, 10, "chopped"),
            new IngredientComponent("Cucumber", ItemType.CUCUMBER, 1, 3, 10, "chopped"));
        Entity steakMeal = ItemFactory.createSteakMeal(ingredients);
        assertEquals(ItemType.STEAKMEAL, steakMeal.getComponent(MealComponent.class).getItemType());
        baseComponentsAssertion(steakMeal);
    }

    @Test
    void createBananaSplit() {
        java.util.List<IngredientComponent> ingredients = Arrays.asList(
            new IngredientComponent("Strawberry", ItemType.STRAWBERRY, 1, 3, 10,"chopped"),
            new IngredientComponent("Banana", ItemType.BANANA, 1, 3, 10, "chopped"),
            new IngredientComponent("Chocolate", ItemType.CHOCOLATE, 1, 3, 10, "chopped"));
        Entity bananaSplit = ItemFactory.createBananaSplit(ingredients);
        assertEquals(ItemType.BANANASPLIT, bananaSplit.getComponent(MealComponent.class).getItemType());
        baseComponentsAssertion(bananaSplit);
    }

    @Test
    void testCreateBaseItemValidItem() {
        Entity testItem = ItemFactory.createBaseItem("strawberry");
        assertEquals(ItemType.STRAWBERRY, testItem.getComponent(IngredientComponent.class).getItemType());
        baseComponentsAssertion(testItem);
    }

    @Test
    void testCreateBaseItemInvalidItem() {
        Entity testItem = ItemFactory.createBaseItem("i don't exist");
        assertTrue(testItem == null);
    }

}
