package com.csse3200.game.entities.factories;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import com.csse3200.game.components.TooltipsDisplay;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.station.IngredientStationHandlerComponent;
import com.csse3200.game.components.station.StationCollectionComponent;
import com.csse3200.game.components.station.StationCookingComponent;
import com.csse3200.game.components.station.StationItemHandlerComponent;
import com.csse3200.game.components.station.StationServingComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.TextureRenderComponent;

@ExtendWith(GameExtension.class)
public class StationFactoryTest {

    @BeforeEach
    public void setUp() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerRenderService(new RenderService());

        // Mock ResourceService
        ResourceService mockResourceService = mock(ResourceService.class);

        // Create a mock Texture with dimensions
        Texture mockTexture = mock(Texture.class);
        when(mockTexture.getHeight()).thenReturn(100); // Set a sample height
        when(mockTexture.getWidth()).thenReturn(100);  // Set a sample width

        // Create a mock TextureAtlas
        TextureAtlas mockAtlas = mock(TextureAtlas.class);

        // Configure ResourceService to return the mock Texture or the mock Texture atlas for servery as it is animated
        when(mockResourceService.getAsset(anyString(), any())).thenAnswer(invocation ->
                "images/stations/Servery_Animation/servery.atlas".equals(invocation.getArgument(0)) &&
                        invocation.getArgument(1) == TextureAtlas.class ? mockAtlas : mockTexture
        );
        ServiceLocator.registerResourceService(mockResourceService);

    }
    // Atlas error, Andi to investigate
//    @Test
//    public void testCreateOven() {
//        Entity oven = StationFactory.createOven();
//
//        assertNotNull(oven);
//        assertNotNull(oven.getComponent(TextureRenderComponent.class));
//        assertNotNull(oven.getComponent(PhysicsComponent.class));
//        assertNotNull(oven.getComponent(ColliderComponent.class));
//        assertNotNull(oven.getComponent(InteractionComponent.class));
//        assertNotNull(oven.getComponent(TooltipsDisplay.class));
//        assertNotNull(oven.getComponent(StationCookingComponent.class));
//        assertNotNull(oven.getComponent(StationItemHandlerComponent.class));
//        assertNotNull(oven.getComponent(InventoryComponent.class));
//
//        assertEquals(BodyDef.BodyType.StaticBody, oven.getComponent(PhysicsComponent.class).getBody().getType());
//    }


    @Test
    public void testCreateStove() {
        Entity stove = StationFactory.createStove();

        assertNotNull(stove);
        assertNotNull(stove.getComponent(InventoryComponent.class));
        assertNotNull(stove.getComponent(StationCookingComponent.class));
        assertNotNull(stove.getComponent(StationItemHandlerComponent.class));
        verifyCommonComponents(stove);

        assertEquals(BodyDef.BodyType.StaticBody, stove.getComponent(PhysicsComponent.class).getBody().getType());
    }

    @Test
    public void testCreateBananaBasket() {
        Entity bananaBasket = StationFactory.createBananaBasket();

        assertNotNull(bananaBasket);
        assertNotNull(bananaBasket.getComponent(StationCollectionComponent.class));
        assertNotNull(bananaBasket.getComponent(InventoryComponent.class));
        assertNotNull(bananaBasket.getComponent(IngredientStationHandlerComponent.class));
        verifyCommonComponents(bananaBasket);

        assertEquals(BodyDef.BodyType.StaticBody, bananaBasket.getComponent(PhysicsComponent.class).getBody().getType());
    }

    @Test
    public void testCreateStrawberryBasket() {
        Entity strawberryBasket = StationFactory.createStrawberryBasket();

        assertNotNull(strawberryBasket);
        assertNotNull(strawberryBasket.getComponent(StationCollectionComponent.class));
        assertNotNull(strawberryBasket.getComponent(InventoryComponent.class));
        assertNotNull(strawberryBasket.getComponent(IngredientStationHandlerComponent.class));
        verifyCommonComponents(strawberryBasket);

        assertEquals(BodyDef.BodyType.StaticBody, strawberryBasket.getComponent(PhysicsComponent.class).getBody().getType());
    }

    @Test
    public void testCreateLettuceBasket() {
        Entity lettuceBasket = StationFactory.createLettuceBasket();

        assertNotNull(lettuceBasket);
        assertNotNull(lettuceBasket.getComponent(StationCollectionComponent.class));
        assertNotNull(lettuceBasket.getComponent(InventoryComponent.class));
        assertNotNull(lettuceBasket.getComponent(IngredientStationHandlerComponent.class));
        verifyCommonComponents(lettuceBasket);

        assertEquals(BodyDef.BodyType.StaticBody, lettuceBasket.getComponent(PhysicsComponent.class).getBody().getType());
    }

    @Test
    public void testCreateTomatoBasket() {
        Entity tomatoBasket = StationFactory.createTomatoBasket();

        assertNotNull(tomatoBasket);
        assertNotNull(tomatoBasket.getComponent(StationCollectionComponent.class));
        assertNotNull(tomatoBasket.getComponent(InventoryComponent.class));
        assertNotNull(tomatoBasket.getComponent(IngredientStationHandlerComponent.class));
        verifyCommonComponents(tomatoBasket);

        assertEquals(BodyDef.BodyType.StaticBody, tomatoBasket.getComponent(PhysicsComponent.class).getBody().getType());
    }

    @Test
    public void testCreateCucumberBasket() {
        Entity cucumberBasket = StationFactory.createCucumberBasket();

        assertNotNull(cucumberBasket);
        assertNotNull(cucumberBasket.getComponent(StationCollectionComponent.class));
        assertNotNull(cucumberBasket.getComponent(InventoryComponent.class));
        assertNotNull(cucumberBasket.getComponent(IngredientStationHandlerComponent.class));
        verifyCommonComponents(cucumberBasket);

        assertEquals(BodyDef.BodyType.StaticBody, cucumberBasket.getComponent(PhysicsComponent.class).getBody().getType());
    }

    @Test
    public void testCreateFeetBenchTable() {
        Entity benchTable = StationFactory.createFeetBenchTable();

        assertNotNull(benchTable);
        verifyCommonComponents(benchTable);

        assertEquals(BodyDef.BodyType.StaticBody, benchTable.getComponent(PhysicsComponent.class).getBody().getType());
    }

    @Test
    public void testCreateTopBenchTable() {
        Entity benchTable = StationFactory.createTopBenchTable();

        assertNotNull(benchTable);
        assertNotNull(benchTable.getComponent(InventoryComponent.class));
        verifyCommonComponents(benchTable);

        // Verify if the StationItemHandlerComponent is added
        assertNull(benchTable.getComponent(StationItemHandlerComponent.class), "StationItemHandlerComponent should not be present");

        assertEquals(BodyDef.BodyType.StaticBody, benchTable.getComponent(PhysicsComponent.class).getBody().getType());
    }

    @Test
    public void testCreateMainBenchTable() {
        Entity benchTable = StationFactory.createMainBenchTable();

        assertNotNull(benchTable);
        assertNotNull(benchTable.getComponent(InventoryComponent.class));
        verifyCommonComponents((benchTable));

        // Verify if the StationItemHandlerComponent is added
        assertNull(benchTable.getComponent(StationItemHandlerComponent.class), "StationItemHandlerComponent should not be present");

        assertEquals(BodyDef.BodyType.StaticBody, benchTable.getComponent(PhysicsComponent.class).getBody().getType());
    }

    @Test
    public void testCreateSubmissionWindow() {
        Entity submissionWindow = StationFactory.createSubmissionWindow();

        assertNotNull(submissionWindow);
        assertNotNull(submissionWindow.getComponent(AnimationRenderComponent.class));
        assertNotNull(submissionWindow.getComponent(PhysicsComponent.class));
        assertNotNull(submissionWindow.getComponent(ColliderComponent.class));
        assertNotNull(submissionWindow.getComponent(InteractionComponent.class));
        assertNotNull(submissionWindow.getComponent(TooltipsDisplay.class));
        assertNotNull(submissionWindow.getComponent(InventoryComponent.class));
        assertNotNull(submissionWindow.getComponent(StationServingComponent.class));

        assertEquals(BodyDef.BodyType.StaticBody, submissionWindow.getComponent(PhysicsComponent.class).getBody().getType());
    }

    @AfterEach
    public void tearDown() {
        // Unregister services to clean up between tests
        ServiceLocator.clear();
    }

    private void verifyCommonComponents(Entity entity) {
        assertNotNull(entity.getComponent(TextureRenderComponent.class));
        assertNotNull(entity.getComponent(PhysicsComponent.class));
        assertNotNull(entity.getComponent(ColliderComponent.class));
        assertNotNull(entity.getComponent(InteractionComponent.class));
        assertNotNull(entity.getComponent(TooltipsDisplay.class));
        assertEquals(BodyDef.BodyType.StaticBody, entity.getComponent(PhysicsComponent.class).getBody().getType());
    }

}

