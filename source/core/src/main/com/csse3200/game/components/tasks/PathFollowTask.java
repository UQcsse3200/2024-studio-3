package com.csse3200.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.ai.tasks.DefaultTask;
import com.csse3200.game.ai.tasks.PriorityTask;
import com.csse3200.game.ai.tasks.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.services.ServiceLocator;

public class PathFollowTask extends DefaultTask implements PriorityTask {
    private static final Logger logger = LoggerFactory.getLogger(PathFollowTask.class);

    private Vector2 targetPos;
    private Vector2 currentTarget;
    private MovementTask movementTask;
    private Task currentTask;

    private Vector2 predefinedTargetPos = new Vector2(-1f, 1f); // Example coordinates
    private float waitTime; // Time to wait before moving to the predefined position
    private float elapsedTime = 0f;
    private boolean hasMovedToPredefined = false;

    public PathFollowTask(Vector2 targetPos, float waitTime) {
        this.targetPos = targetPos;
        this.waitTime = waitTime;
    }

    @Override
    public int getPriority() {
        return 1; // Low priority task
    }

    @Override
    public void start() {
        super.start();
        this.elapsedTime = 0f;
        this.hasMovedToPredefined = false;

        Vector2 startPos = owner.getEntity().getPosition();
        currentTarget = new Vector2(targetPos.x, startPos.y);

        movementTask = new MovementTask(currentTarget);
        movementTask.create(owner); // Initialize with the correct context
        movementTask.start();
        currentTask = movementTask;

        this.owner.getEntity().getEvents().trigger("wanderStart");

        // Handle early leave event
        this.owner.getEntity().getEvents().addListener("leaveEarly", (Object idObj) -> {
            // Directly call the method to move to predefined position
            triggerMoveToPredefinedPosition();
            logger.debug("Customer is leaving early.");
        });
    }

    @Override
    public void update() {
        elapsedTime += ServiceLocator.getTimeSource().getDeltaTime();

        if (!hasMovedToPredefined && elapsedTime >= waitTime) {
            logger.debug("Wait time elapsed. Moving to predefined position.");
            triggerMoveToPredefinedPosition();
            hasMovedToPredefined = true;
        }

        if (currentTask.getStatus() != Status.ACTIVE) {
            if (currentTarget.epsilonEquals(targetPos)) {
                currentTask.stop();
            } else if (currentTarget.epsilonEquals(targetPos.x, owner.getEntity().getPosition().y, 0.1f)) {
                currentTarget.set(targetPos);
                startMoving();
            }
        }
        currentTask.update();
    }

    private void startMoving() {
        logger.debug("Starting to move to the next step");
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

    private void triggerMoveToPredefinedPosition() {
        logger.debug("Triggering move to predefined position: {}", predefinedTargetPos);
        this.targetPos = predefinedTargetPos;
        this.currentTarget = new Vector2(targetPos.x, owner.getEntity().getPosition().y);
        startMoving();
    }

    private void handleEarlyExit() {
        logger.debug("Handling early exit. Moving to target position: {}", targetPos);
        this.currentTarget = targetPos;
        startMoving();
    }
}
