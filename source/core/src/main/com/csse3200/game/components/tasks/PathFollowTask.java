package com.csse3200.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.ai.tasks.DefaultTask;
import com.csse3200.game.ai.tasks.PriorityTask;
import com.csse3200.game.ai.tasks.Task;
import com.csse3200.game.ai.tasks.TaskRunner;
import com.csse3200.game.entities.configs.BaseCustomerConfig; // Import the BaseCustomerConfig
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
    private final int Customer_id; // Use int for customerId

    private Vector2 predefinedTargetPos = new Vector2(-1f, 1f); // Example coordinates
    private static final float WAIT_TIME = 15f; // 15 seconds
    private float elapsedTime = 0f;
    private boolean hasMovedToPredefined = false;

    public PathFollowTask(Vector2 targetPos, int Customer_id) {
        this.targetPos = targetPos;
        this.Customer_id = Customer_id;
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
        movementTask.create(owner);
        movementTask.start();
        currentTask = movementTask;

        this.owner.getEntity().getEvents().trigger("wanderStart");

        // Assuming the event system can handle int
        this.owner.getEntity().getEvents().addListener("leaveEarly", (Object idObj) -> {
            if (idObj instanceof Integer) {
                int id = (Integer) idObj;
                if (this.Customer_id == id) {
                    triggerMoveToPredefinedPosition();
                    logger.debug("Customer {} is leaving early.", id);
                }
            }
        });
    }

    @Override
    public void create(TaskRunner taskRunner) {
        super.create(taskRunner);
        this.owner = taskRunner; // Assuming owner is of type TaskRunner
    }

    @Override
    public void update() {
        elapsedTime += getDeltaTime();

        if (!hasMovedToPredefined && elapsedTime >= WAIT_TIME) {
            logger.debug("Wait time elapsed. Moving to predefined position.");
            triggerMoveToPredefinedPosition();
            hasMovedToPredefined = true;
        }

        if (currentTask != null) {

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
    }

    private void startMoving() {
        logger.debug("Starting to move to the next step, currentTarget: {}", currentTarget);

        if (movementTask == null) {
            logger.error("Movement task is null, cannot start moving");
            return;
        }

        logger.debug("Movement task is active: {}", movementTask.getStatus());
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

    public void triggerMoveToPredefinedPosition() {
        logger.debug("Triggering move to predefined position: {}", predefinedTargetPos);

        if (currentTask != null && currentTask.getStatus() == Status.ACTIVE) {
            currentTask.stop();  // Stop the current task cleanly
        }

        this.targetPos = predefinedTargetPos;
        this.currentTarget = new Vector2(targetPos.x, owner.getEntity().getPosition().y);

        //startMoving();
    }


    private float getDeltaTime() {
        return 1 / 60f; // Assuming 60 FPS
    }
}
