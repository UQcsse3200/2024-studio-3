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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.csse3200.game.components.TooltipsDisplay;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.station.IngredientStationHandlerComponent;
import com.csse3200.game.components.station.StationCollectionComponent;
import com.csse3200.game.components.station.StationCookingComponent;
import com.csse3200.game.components.station.StationItemHandlerComponent;
import com.csse3200.game.components.station.StationServingComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

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
    /*
    @Test
    public void testCreateOven() {
        Entity oven = StationFactory.createOven();

        assertNotNull(oven);
        assertNotNull(oven.getComponent(TextureRenderComponent.class));
        assertNotNull(oven.getComponent(PhysicsComponent.class));
        assertNotNull(oven.getComponent(ColliderComponent.class));
        assertNotNull(oven.getComponent(InteractionComponent.class));
        assertNotNull(oven.getComponent(TooltipsDisplay.class));
        assertNotNull(oven.getComponent(StationCookingComponent.class));
        assertNotNull(oven.getComponent(StationItemHandlerComponent.class));
        assertNotNull(oven.getComponent(InventoryComponent.class));

        assertEquals(BodyDef.BodyType.StaticBody, oven.getComponent(PhysicsComponent.class).getBody().getType());
    }
    */

    @Test
    public void testCreateStove() {
        Entity stove = StationFactory.createStove();

        assertNotNull(stove);
        assertNotNull(stove.getComponent(TextureRenderComponent.class));
        assertNotNull(stove.getComponent(PhysicsComponent.class));
        assertNotNull(stove.getComponent(ColliderComponent.class));
        assertNotNull(stove.getComponent(InteractionComponent.class));
        assertNotNull(stove.getComponent(TooltipsDisplay.class));
        assertNotNull(stove.getComponent(InventoryComponent.class));
        assertNotNull(stove.getComponent(StationCookingComponent.class));
        assertNotNull(stove.getComponent(StationItemHandlerComponent.class));

        assertEquals(BodyDef.BodyType.StaticBody, stove.getComponent(PhysicsComponent.class).getBody().getType());
    }

    @Test
    public void testCreateAppleTree() {
        Entity appleTree = StationFactory.createAppleTree();

        assertNotNull(appleTree);
        assertNotNull(appleTree.getComponent(TextureRenderComponent.class));
        assertNotNull(appleTree.getComponent(PhysicsComponent.class));
        assertNotNull(appleTree.getComponent(ColliderComponent.class));
        assertNotNull(appleTree.getComponent(InteractionComponent.class));
        assertNotNull(appleTree.getComponent(TooltipsDisplay.class));
        assertNotNull(appleTree.getComponent(StationCollectionComponent.class));
        assertNotNull(appleTree.getComponent(InventoryComponent.class));
        assertNotNull(appleTree.getComponent(IngredientStationHandlerComponent.class));

        assertEquals(BodyDef.BodyType.StaticBody, appleTree.getComponent(PhysicsComponent.class).getBody().getType());
    }

    @Test
    public void testCreateFeetBenchTable() {
        Entity benchTable = StationFactory.createFeetBenchTable();

        assertNotNull(benchTable);
        assertNotNull(benchTable.getComponent(TextureRenderComponent.class));
        assertNotNull(benchTable.getComponent(PhysicsComponent.class));
        assertNotNull(benchTable.getComponent(ColliderComponent.class));
        assertNotNull(benchTable.getComponent(InteractionComponent.class));
        assertNotNull(benchTable.getComponent(TooltipsDisplay.class));

        assertEquals(BodyDef.BodyType.StaticBody, benchTable.getComponent(PhysicsComponent.class).getBody().getType());
    }

    @Test
    public void testCreateTopBenchTable() {
        Entity benchTable = StationFactory.createTopBenchTable();

        assertNotNull(benchTable);
        assertNotNull(benchTable.getComponent(TextureRenderComponent.class));
        assertNotNull(benchTable.getComponent(PhysicsComponent.class));
        assertNotNull(benchTable.getComponent(ColliderComponent.class));
        assertNotNull(benchTable.getComponent(InteractionComponent.class));
        assertNotNull(benchTable.getComponent(TooltipsDisplay.class));
        assertNotNull(benchTable.getComponent(InventoryComponent.class));

        // Verify if the StationItemHandlerComponent is added
        assertNull(benchTable.getComponent(StationItemHandlerComponent.class), "StationItemHandlerComponent should not be present");

        assertEquals(BodyDef.BodyType.StaticBody, benchTable.getComponent(PhysicsComponent.class).getBody().getType());
    }

    @Test
    public void testCreateMainBenchTable() {
        Entity benchTable = StationFactory.createMainBenchTable();

        assertNotNull(benchTable);
        assertNotNull(benchTable.getComponent(TextureRenderComponent.class));
        assertNotNull(benchTable.getComponent(PhysicsComponent.class));
        assertNotNull(benchTable.getComponent(ColliderComponent.class));
        assertNotNull(benchTable.getComponent(InteractionComponent.class));
        assertNotNull(benchTable.getComponent(TooltipsDisplay.class));
        assertNotNull(benchTable.getComponent(InventoryComponent.class));

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

    @Test
    public void testCreateStation() {
        String type = "customStation";
        float height = 2.0f;
        Entity station = StationFactory.createStation(type, height);

        assertNotNull(station);
        assertNotNull(station.getComponent(TextureRenderComponent.class));
        assertNotNull(station.getComponent(PhysicsComponent.class));
        assertNotNull(station.getComponent(ColliderComponent.class));
        assertNotNull(station.getComponent(StationItemHandlerComponent.class));
        assertNotNull(station.getComponent(InventoryComponent.class));

        assertEquals(BodyDef.BodyType.StaticBody, station.getComponent(PhysicsComponent.class).getBody().getType());
        assertEquals(height, station.getScale().y);
    }
}

