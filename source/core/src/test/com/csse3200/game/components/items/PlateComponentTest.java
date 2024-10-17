package com.csse3200.game.components.items;

import com.csse3200.game.entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;

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

    @Test
    void cleanOnCreationState()  {
        assert plateComponent.getPlateState() == PlateComponent.PlateState.CLEAN;
    }

    @Test
    void cleanOnCreationGetter() {
        assert plateComponent.isClean() == true;
    }

    @Test
    void idOnCreation() {
        assert plateComponent.getPlateId() == 3;
    }

    @Test
    void usePlateState() {
        plateComponent.usePlate();
        assert plateComponent.getPlateState() == PlateComponent.PlateState.DIRTY;
    }

    @Test
    void usePlateGetter() {
        plateComponent.usePlate();
        assert plateComponent.isClean() == false;
    }

    @Test
    void useThenCleanState() {
        plateComponent.usePlate();
        plateComponent.washPlate();
        assert plateComponent.getPlateState() == PlateComponent.PlateState.CLEAN;
    }

    @Test
    void useThenCleanGetter() {
        plateComponent.usePlate();
        plateComponent.washPlate();
        assert plateComponent.isClean() == true;
    }

}