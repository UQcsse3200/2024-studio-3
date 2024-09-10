/*package com.csse3200.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.ai.tasks.Task;
import com.csse3200.game.ai.tasks.TaskRunner;
import com.csse3200.game.entities.configs.BaseCustomerConfig;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class PathFollowTaskTest {
    @Mock
    private TaskRunner taskRunner;

    @Mock
    private Task movementTask;

    private PathFollowTask pathFollowTask;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        pathFollowTask = new PathFollowTask(new Vector2(10, 10), 1234);
        pathFollowTask.create(taskRunner);
        pathFollowTask.movementTask = (MovementTask) movementTask; // Inject the mock movement task
    }

    @Test
    void testStart() {
        pathFollowTask.start();

        verify(taskRunner, times(1)).getEntity(); // Verify interactions with the taskRunner
        verify(movementTask, times(1)).create(taskRunner);
        verify(movementTask, times(1)).start();
    }

    @Test
    void testUpdate_MoveToPredefinedPosition() {
        pathFollowTask.start();
        pathFollowTask.elapsedTime = 15f; // Simulate time passing

        pathFollowTask.update();

        verify(movementTask, times(1)).stop();
        verify(movementTask, times(1)).setTarget(any(Vector2.class));
    }

    @Test
    void testTriggerMoveToPredefinedPosition() {
        pathFollowTask.triggerMoveToPredefinedPosition();

        verify(movementTask, times(1)).stop();
        assert pathFollowTask.targetPos.equals(new Vector2(-1f, 1f));
    }

    @Test
    void testAddListener_TriggerMoveEarly() {
        pathFollowTask.start();
        pathFollowTask.owner.getEntity().getEvents().trigger("leaveEarly", 1234);

        verify(movementTask, times(1)).stop();
        verify(movementTask, times(1)).setTarget(any(Vector2.class));
    }

    @Test
    void testSwapTask() {
        Task newTask = mock(Task.class);
        pathFollowTask.swapTask(newTask);

        verify(movementTask, times(1)).stop();
        verify(newTask, times(1)).start();
        assert pathFollowTask.currentTask == newTask;
    }
}
*/