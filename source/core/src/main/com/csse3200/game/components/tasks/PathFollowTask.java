package com.csse3200.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.ai.tasks.DefaultTask;
import com.csse3200.game.ai.tasks.PriorityTask;
import com.csse3200.game.ai.tasks.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Task to move the NPC to a specific point on the screen in a step-by-step manner (horizontal then vertical or vice versa).
 * The NPC will stop once it reaches the destination.
 */
public class PathFollowTask extends DefaultTask implements PriorityTask {
    private static final Logger logger = LoggerFactory.getLogger(WanderTask.class);

    private final Vector2 targetPos;
    private Vector2 currentTarget;
    private MovementTask movementTask;
    private Task currentTask;

    /**
     * @param targetPos The target position on the screen where the NPC should move.
     */
    public PathFollowTask(Vector2 targetPos) {
        this.targetPos = targetPos;
    }

    @Override
    public int getPriority() {
        return 1; // Low priority task
    }

    @Override
    public void start() {
        super.start();

        // Start moving horizontally first
        Vector2 startPos = owner.getEntity().getPosition();
        currentTarget = new Vector2(targetPos.x, startPos.y);

        movementTask = new MovementTask(currentTarget);
        movementTask.create(owner);

        movementTask.start();
        currentTask = movementTask;

        this.owner.getEntity().getEvents().trigger("wanderStart");
    }

    @Override
    public void update() {
        if (currentTask.getStatus() != Status.ACTIVE) {
            if (currentTarget.epsilonEquals(targetPos)) {
                // Stop when the target position is reached
                currentTask.stop();
            } else if (currentTarget.epsilonEquals(targetPos.x, owner.getEntity().getPosition().y, 0.1f)) {
                // Horizontal movement complete, start moving vertically
                currentTarget.set(targetPos);
                startMoving();
            }
        }
        currentTask.update();
    }

    private void startMoving() {
        logger.debug("Starting moving to next step");
        movementTask.setTarget(currentTarget);
        swapTask(movementTask);
    }

    private void swapTask(Task newTask) {
        if (currentTask != null) {
            currentTask.stop();
        }
        currentTask = newTask;
        currentTask.start();
    }
}