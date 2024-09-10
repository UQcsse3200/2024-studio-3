/*package com.csse3200.game.components;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.components.tasks.PathFollowTask;
import com.csse3200.game.ai.tasks.Task;
import com.csse3200.game.ai.tasks.TaskRunner;
import com.csse3200.game.entities.Entity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.mockito.Mockito.*;

class CustomerBehaviorComponentTest {

    @Mock
    private TaskRunner taskRunner;

    @Mock
    private PathFollowTask pathFollowTask;

    @Mock
    private Entity entity;

    @InjectMocks
    private CustomerBehaviorComponent customerBehaviorComponent;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(taskRunner.getEntity()).thenReturn(entity);
        when(entity.getId()).thenReturn(1234); // Example ID
    }

    @Test
    void testCreate() {
        // Directly verify the creation of PathFollowTask and listener registration
        customerBehaviorComponent.create();

        // Verify that create method is called on the PathFollowTask
        verify(pathFollowTask, times(1)).create(customerBehaviorComponent);

        // Verify that the listener is added to the EventSystem
           }

    @Test
    void testUpdate() {
        customerBehaviorComponent.update();

        verify(pathFollowTask, times(1)).update();
    }

    @Test
    void testMoveToEnd() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        customerBehaviorComponent.create(); // Initialize the component

        // Use reflection to call the private method
        Method moveToEndMethod = CustomerBehaviorComponent.class.getDeclaredMethod("moveToEnd");
        moveToEndMethod.setAccessible(true);
        moveToEndMethod.invoke(customerBehaviorComponent);

        verify(pathFollowTask, times(1)).triggerMoveToPredefinedPosition();
    }

    @Test
    void testSetTargetPosition() {
        Vector2 newPosition = new Vector2(10, 10);
        when(pathFollowTask.getStatus()).thenReturn(Task.Status.INACTIVE); // Ensure task is inactive

        // Set a new target position for the customer
        customerBehaviorComponent.setTargetPosition(newPosition);

        // Verify that stop, create, and start methods are called on PathFollowTask
        verify(pathFollowTask, times(1)).stop();
        verify(pathFollowTask, times(1)).create(customerBehaviorComponent);
        verify(pathFollowTask, times(1)).start();
    }
}
*/