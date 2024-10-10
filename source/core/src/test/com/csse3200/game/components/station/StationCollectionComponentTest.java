package com.csse3200.game.components.station;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.csse3200.game.components.items.IngredientComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GameExtension.class)
class StationCollectionComponentTest {

    private StationCollectionComponent collectionComponent;
    

    @BeforeEach
    void BeforeEach() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        ResourceService mockResourceService = mock(ResourceService.class);
        when(mockResourceService.getAsset(anyString(), any())).thenReturn(null);
        ServiceLocator.registerResourceService(mockResourceService);

        collectionComponent = new StationCollectionComponent();
    }

    @Test
    void TestCollection1() {
        Entity testEntity = collectionComponent.collectItem("fish");
        Assertions.assertSame(ItemType.FISH, testEntity.getComponent(IngredientComponent.class).getItemType());
    }

    @Test
    void TestCollection2() {
        Entity tesEntity = collectionComponent.collectItem("beef");
        Assertions.assertSame(ItemType.BEEF, tesEntity.getComponent(IngredientComponent.class).getItemType());
    }

    @Test
    void TestCollectionFail() {
        Entity testEntity = collectionComponent.collectItem("not an option");
        Assertions.assertNull(testEntity);
    }

}
