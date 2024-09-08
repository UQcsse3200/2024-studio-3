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
    private static final Logger logger = LoggerFactory.getLogger(PathFollowTask.class);

    private Vector2 targetPos;
    private Vector2 currentTarget;
    private MovementTask movementTask;
    private Task currentTask;
    private final String customerId;

    /**
     * Predefined coordinates where the NPC will move to when triggered.
     */
    private Vector2 predefinedTargetPos = new Vector2(-1f, 1f); // Example coordinates, change as needed

    /**
     * Time in seconds to wait before moving to the predefined position.
     */
    private static final float WAIT_TIME = 15f; // 15 seconds
    private float elapsedTime = 0f; // Time elapsed since the task started
    private boolean hasMovedToPredefined = false; // Track if the movement to the predefined position has been triggered

    /**
     * @param targetPos The target position on the screen where the NPC should move.
     * @param customerId The unique ID of the customer.
     */
    public PathFollowTask(Vector2 targetPos, String customerId) {
        this.targetPos = targetPos;
        this.customerId = customerId;
    }

    @Override
    public int getPriority() {
        return 1; // Low priority task
    }

    @Override
    public void start() {
        super.start();
        this.elapsedTime = 0f; // Reset elapsed time
        this.hasMovedToPredefined = false; // Reset the movement flag

        // Start moving horizontally first
        Vector2 startPos = owner.getEntity().getPosition();
        currentTarget = new Vector2(targetPos.x, startPos.y);

        movementTask = new MovementTask(currentTarget);
        movementTask.create(owner);

        movementTask.start();
        currentTask = movementTask;

        // Trigger event indicating the wander task has started
        this.owner.getEntity().getEvents().trigger("wanderStart");

        // Add listener for early leave event
        this.owner.getEntity().getEvents().addListener("leaveEarly", (String id) -> {
            if (this.customerId.equals(id)) {
                triggerMoveToPredefinedPosition(); // Trigger early leave
                logger.debug("Customer {} is leaving early.", id);
            }
        });
    }

    @Override
    public void update() {
        elapsedTime += getDeltaTime(); // Update elapsed time

        // If no movement to predefined position has occurred and wait time is reached, trigger movement
        if (!hasMovedToPredefined && elapsedTime >= WAIT_TIME) {
            logger.debug("Wait time elapsed. Moving to predefined position.");
            triggerMoveToPredefinedPosition();
            hasMovedToPredefined = true; // Ensure this happens only once
        }

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

    /**
     * Private function to trigger NPC movement to a predefined position.
     */
    private void triggerMoveToPredefinedPosition() {
        logger.debug("Triggering move to predefined position: {}", predefinedTargetPos);
        this.targetPos = predefinedTargetPos; // Set target to predefined position
        this.currentTarget = new Vector2(targetPos.x, owner.getEntity().getPosition().y); // Horizontal movement
        startMoving(); // Start the movement to predefined position
    }

    /**
     * Placeholder method for getting delta time. Replace with actual implementation.
     */
    private float getDeltaTime() {
        return 1 / 60f; // Assuming 60 FPS, adjust as necessary
    }
}
