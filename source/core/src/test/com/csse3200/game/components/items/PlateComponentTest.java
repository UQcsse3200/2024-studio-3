package com.csse3200.game.components.items;

import com.csse3200.game.entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;

class PlateComponentTest {

    private PlateComponent plateComponent;
    private Entity mockEntity;
    private Entity mockPlayer;

    @BeforeEach
    void setUp() {

        mockEntity = mock(Entity.class);
        mockPlayer = mock(Entity.class);

        plateComponent = new PlateComponent(3);
        plateComponent.setEntity(mockEntity);

    }

}