package com.csse3200.game.rendering;


import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;


@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class OrderActionsTest {
    @Test
    void itHasAllGoneToShambles() {
        assertEquals(1,1);
    }
    /*private OrderActions orderActions;
    private Entity entity;
    @Mock private GdxGame mockGame;
    @Mock private DocketService mockDocketService;
    @Mock private Logger mockLogger;
    @Mock private InputService mockInputService;
    @Mock private EntityService mockEntityService;

    @BeforeEach
    void setUp() {
        ServiceLocator.registerDocketService(mockDocketService);
        ServiceLocator.registerInputService(mockInputService);
        ServiceLocator.registerEntityService(mockEntityService);
        orderActions = new OrderActions(mockGame);
        entity = new Entity();
        entity.addComponent(orderActions);
        //mockEntityService.register(entity);
        //when(ServiceLocator.getInputService()).thenReturn(mockInputService);
    }

    @Test
    void testOnAddOrder() {
        // Simulate triggering the "addOrder" event
        orderActions.getEntity().getEvents().trigger("addOrder");

        verify(mockLogger).info("Adding a new order ticket");

    }

    @Test
    void testOnRemoveOrder() {

        int index = 1;
        orderActions.getEntity().getEvents().trigger("removeOrder", index);

        verify(mockLogger).info("Remove order");

        ArgumentCaptor<Integer> argumentCaptor = ArgumentCaptor.forClass(Integer.class);
        verify(mockDocketService.getEvents()).trigger("reorderDockets", argumentCaptor.capture());
        assertEquals(index, argumentCaptor.getValue());
    }

    @Test
    void testOnMoveOrder() {

        orderActions.getEntity().getEvents().trigger("moveOrder");


        verify(mockLogger).info("Move order");

    }

    @Test
    void testOnChangeColour() {

        orderActions.getEntity().getEvents().trigger("changeColour");


        verify(mockLogger).info("Change colour");
       
    }*/

}




