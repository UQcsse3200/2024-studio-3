/*package com.csse3200.game.components.tasks;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

import com.csse3200.game.ai.tasks.AITaskComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.listeners.EventListener0;
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
    void shouldTriggerEvent() {
        Vector2 targetPosition = new Vector2(5f, 5f);
        PathFollowTask pathFollowTask = new PathFollowTask(targetPosition);

        AITaskComponent aiTaskComponent = new AITaskComponent().addTask(pathFollowTask);
        Entity entity = new Entity().addComponent(aiTaskComponent).addComponent(new PhysicsMovementComponent());
        entity.create();

        // Register callbacks
        EventListener0 callback = mock(EventListener0.class);
        entity.getEvents().addListener("wanderStart", callback);

        pathFollowTask.start();

        verify(callback).handle();
    }
}
*/

package com.csse3200.game.components.tasks;

import static org.mockito.Mockito.*;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.ai.tasks.TaskRunner;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;


public class PathFollowTaskTest {
    private TaskRunner taskRunner;
    private Entity entity;
    private PathFollowTask pathFollowTask;

    @BeforeEach
    void setUp() {
        taskRunner = mock(TaskRunner.class);
        entity = mock(Entity.class);
        when(taskRunner.getEntity()).thenReturn(entity);
        when(entity.getPosition()).thenReturn(new Vector2(0, 0));
        ServiceLocator.registerEntityService(mock(EntityService.class));
        pathFollowTask = new PathFollowTask(new Vector2(10, 10), 15);
        pathFollowTask.create(taskRunner);
    }

//    @Test
//    void start_initializesCorrectly() {
//        pathFollowTask.start();
//        verify(entity.getEvents()).trigger("wanderStart");
//    }
//
//    @Test
//    void update_movesToPredefinedPositionAfterWaitTime() {
//        pathFollowTask.start();
//        for (int i = 0; i < 900; i++) { // Simulate 15 seconds
//            pathFollowTask.update();
//        }
//        assertTrue(pathFollowTask.hasMovedToPredefined);
//    }
//
//    @Test
//    void update_removesCustomerEntityWhenReachedPredefinedPosition() {
//        pathFollowTask.start();
//        pathFollowTask.triggerMoveToPredefinedPosition();
//        when(entity.getPosition()).thenReturn(new Vector2(-1f, 1f));
//        pathFollowTask.update();
//        verify(ServiceLocator.getEntityService()).unregister(entity);
//        verify(entity).dispose();
//    }
//
//    @Test
//    void triggerMoveToPredefinedPosition_setsCorrectTarget() {
//        pathFollowTask.start();
//        pathFollowTask.triggerMoveToPredefinedPosition();
//        assertEquals(new Vector2(-1f, 1f), pathFollowTask.targetPos);
//    }
}