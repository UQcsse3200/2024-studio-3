package com.csse3200.game.components.station;

import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
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

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GameExtension.class)
class StationCollectionComponentTest {

    private StationCollectionComponent collectionComponent;
    /*private final String[] textures = 
    {
        "images/ingredients/raw_fish.png",
        "images/ingredients/raw_beef.png"
    };*/
    

    @BeforeEach
    public void BeforeEach() {
        ServiceLocator.registerPhysicsService(new PhysicsService());
        ServiceLocator.registerEntityService(new EntityService());
        ServiceLocator.registerRenderService(new RenderService());

        ResourceService mockResourceService = mock(ResourceService.class);
        when(mockResourceService.getAsset(anyString(), any())).thenReturn(null);
        ServiceLocator.registerResourceService(mockResourceService);

        //collectionComponent = StationFactory.createAppleTree().getComponent(StationCollectionComponent.class);
        collectionComponent = new StationCollectionComponent();
    }

    @Test
    public void TestCollection1() {       
        Entity testEntity = collectionComponent.collectItem("fish");
        assertTrue(testEntity.getComponent(IngredientComponent.class).getItemType() == ItemType.FISH);
    }

    @Test
    public void TestCollection2() {
        Entity tesEntity = collectionComponent.collectItem("beef");
        assertTrue(tesEntity.getComponent(IngredientComponent.class).getItemType() == ItemType.BEEF);
    }

    @Test
    public void TestCollectionFail() {
        Entity testEntity = collectionComponent.collectItem("not an option");
        assertNull(testEntity);
    }

}
