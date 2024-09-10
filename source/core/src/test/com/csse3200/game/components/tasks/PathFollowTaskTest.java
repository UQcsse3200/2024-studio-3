/*package com.csse3200.game.components.tasks;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

import com.csse3200.game.ai.tasks.AITaskComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.listeners.EventListener0;
import com.csse3200.game.events.listeners.EventListener1;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.badlogic.gdx.math.Vector2;

@ExtendWith(MockitoExtension.class)
class PathFollowTaskTest {
    @Mock
    private GameTime gameTime;

    @Mock
    private PhysicsComponent physicsComponent;

    private Entity entity;
    private PathFollowTask pathFollowTask;

    @BeforeEach
    void setUp() {
        ServiceLocator.registerTimeSource(gameTime);

        // Create entity and add components
        AITaskComponent aiTaskComponent = new AITaskComponent();
        entity = new Entity().addComponent(aiTaskComponent).addComponent(physicsComponent);
        entity.create();

        // Initialize PathFollowTask with the entity
        pathFollowTask = new PathFollowTask(new Vector2(5f, 5f), 1f); // Example waitTime
        aiTaskComponent.addTask(pathFollowTask);
    }

    @Test
    void shouldTriggerEventOnStart() {
        // Register callbacks
        EventListener0 wanderStartCallback = mock(EventListener0.class);
        entity.getEvents().addListener("wanderStart", wanderStartCallback);

        // Start the task
        pathFollowTask.start();

        // Simulate some game time to allow the task to start
        when(gameTime.getDeltaTime()).thenReturn(0.1f);
        pathFollowTask.update();

        // Verify the event was triggered
        verify(wanderStartCallback).handle();
    }

    @Test
    void shouldMoveToPredefinedPositionAfterWaitTime() {
        // Set up a short wait time for testing
        pathFollowTask = new PathFollowTask(new Vector2(5f, 5f), 0.5f);
        AITaskComponent aiTaskComponent = entity.getComponent(AITaskComponent.class);
        aiTaskComponent.addTask(pathFollowTask);

        // Register event listener for setPosition
        entity.getEvents().addListener("setPosition", (Vector2 pos) -> {
            // Verify position in event listener
            if (pos.equals(new Vector2(-1f, 1f))) {
                // Test success
            }
        });

        // Start the task
        pathFollowTask.start();

        // Simulate game time before the wait time
        when(gameTime.getDeltaTime()).thenReturn(0.4f);
        pathFollowTask.update(); // Simulate time passing

        // Ensure no move has happened yet
        verify(entity.getEvents(), never()).trigger(eq("setPosition"), any(Vector2.class));

        // Simulate additional game time to surpass the wait time
        when(gameTime.getDeltaTime()).thenReturn(0.2f);
        pathFollowTask.update(); // Simulate time passing

        // Verify that the task has moved to the predefined position
        Vector2 expectedPosition = new Vector2(-1f, 1f);
        verify(entity.getEvents()).trigger(eq("setPosition"), argThat(vector -> vector.equals(expectedPosition)));
    }

    @Test
    void shouldHandleEarlyExit() {
        // Register callbacks
        EventListener1<Integer> earlyExitCallback = mock(EventListener1.class);
        entity.getEvents().addListener("leaveEarly", earlyExitCallback);

        // Start the task
        pathFollowTask.start();

        // Simulate early exit event
        entity.getEvents().trigger("leaveEarly", 1); // Passing an example ID

        // Simulate some game time to allow the task to update
        when(gameTime.getDeltaTime()).thenReturn(0.1f);
        pathFollowTask.update();

        // Verify that the early exit was handled
        Vector2 expectedPosition = new Vector2(5f, 5f);
        verify(entity.getEvents()).trigger(eq("setPosition"), argThat(vector -> vector.equals(expectedPosition)));
    }
}
*/