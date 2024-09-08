package com.csse3200.game.components.tasks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.csse3200.game.ai.tasks.AITaskComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.listeners.EventListener0;
import com.csse3200.game.events.listeners.EventListener1;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.utils.math.Vector2Utils;
import com.csse3200.game.physics.components.PhysicsMovementComponent;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import com.badlogic.gdx.math.Vector2;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class PathFollowTaskTest {
    @Mock
    GameTime gameTime;

    @BeforeEach
    void beforeEach() {
        ServiceLocator.registerTimeSource(gameTime);
    }

    @Test
    void shouldTriggerWanderStartEvent() {
        Vector2 targetPosition = new Vector2(5f, 5f);
        String customerId = "testCustomerId"; // Use a sample customer ID
        PathFollowTask pathFollowTask = new PathFollowTask(targetPosition, customerId);

        AITaskComponent aiTaskComponent = new AITaskComponent().addTask(pathFollowTask);
        Entity entity = new Entity().addComponent(aiTaskComponent).addComponent(new PhysicsMovementComponent());
        entity.create();

        // Register callbacks
        EventListener0 wanderStartCallback = mock(EventListener0.class);
        entity.getEvents().addListener("wanderStart", wanderStartCallback);

        pathFollowTask.start();

        // Verify that wanderStart event was triggered
        verify(wanderStartCallback).handle();
    }

    @Test
    void shouldTriggerLeaveEarlyEvent() {
        Vector2 targetPosition = new Vector2(5f, 5f);
        String customerId = "testCustomerId";
        PathFollowTask pathFollowTask = new PathFollowTask(targetPosition, customerId);

        AITaskComponent aiTaskComponent = new AITaskComponent().addTask(pathFollowTask);
        Entity entity = new Entity().addComponent(aiTaskComponent).addComponent(new PhysicsMovementComponent());
        entity.create();

        // Register callbacks
        EventListener1 leaveEarlyCallback = mock(EventListener1.class);
        entity.getEvents().addListener("leaveEarly", leaveEarlyCallback);

        // Simulate an early leave event
        entity.getEvents().trigger("leaveEarly", customerId);

        // Update task to ensure it's executed
        pathFollowTask.start();
        pathFollowTask.update();

        // Verify that leaveEarly event was triggered
        verify(leaveEarlyCallback).handle(customerId);
    }
}