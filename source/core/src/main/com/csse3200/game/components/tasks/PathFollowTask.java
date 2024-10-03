package com.csse3200.game.components.tasks;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.ai.tasks.DefaultTask;
import com.csse3200.game.ai.tasks.PriorityTask;
import com.csse3200.game.ai.tasks.Task;
import com.csse3200.game.ai.tasks.TaskRunner;
import com.csse3200.game.entities.factories.NPCFactory;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PathFollowTask extends DefaultTask implements PriorityTask {
    private static final Logger logger = LoggerFactory.getLogger(PathFollowTask.class);

    Vector2 targetPos;
    private Vector2 currentTarget;
    private MovementTask movementTask;
    private Task currentTask;
    private final int customer_id;

    private Vector2 predefinedTargetPos = new Vector2(1, 0);
    private static final float WAIT_TIME = 15f;
    private float elapsedTime = 0f;
    boolean hasMovedToPredefined = false;

    /**
     * Task to make an entity follow a path to a target position.
     * @param targetPos The target position to move to
     * @param customer_id The id of the customer
     */
    public PathFollowTask(Vector2 targetPos, int customer_id) {
        this.targetPos = targetPos;
        this.customer_id = customer_id;
    }

    /**
     * Get the priority of the task
     * @return The priority of the task
     */
    @Override
    public int getPriority() {
        return 1;
    }

    /**
     * Start the task
     */
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

        ServiceLocator.getEntityService().getEvents().addListener("leaveEarly", this::triggerMoveToPredefinedPosition);
    }

    /**
     * Create the Task
     * @param taskRunner Task runner to attach to
     */
    @Override
    public void create(TaskRunner taskRunner) {
        super.create(taskRunner);
        this.owner = taskRunner;
    }

    /**
     * Update the task
     */
    @Override
    public void update() {
        elapsedTime += getDeltaTime();

        // Check if it's time to move to the predefined position
        if (!hasMovedToPredefined && elapsedTime >= WAIT_TIME) {
            triggerMoveToPredefinedPosition();
            hasMovedToPredefined = true;
        }

        if (currentTask != null) {
            if (currentTask.getStatus() != Status.ACTIVE) {
                if (currentTarget.epsilonEquals(targetPos)) {
                    currentTask.stop();
                    // Check if the entity reached the predefined position
                    if (targetPos.epsilonEquals(predefinedTargetPos, 0.1f)) {
                        // Remove the customer from the game
                        removeCustomerEntity();
                    }
                } else if (currentTarget.epsilonEquals(targetPos.x, owner.getEntity().getPosition().y, 0.1f)) {
                    currentTarget.set(targetPos);
                    startMoving();
                }
            }
            currentTask.update();
        }
    }

    /**
     * Start Moving the character on screen
     */
    private void startMoving() {
        if (movementTask == null) {
            return;
        }
        movementTask.setTarget(currentTarget);
        swapTask(movementTask);
    }

    /**
     * Swap the current task with a new task
     * @param newTask The new task to swap to
     */
    private void swapTask(Task newTask) {
        if (currentTask != null) {
            currentTask.stop();
        }
        currentTask = newTask;
        currentTask.start();
    }

    /**
     * Trigger the move to the predefined position
     */
    public void triggerMoveToPredefinedPosition() {
        if (currentTask != null && currentTask.getStatus() == Status.ACTIVE) {
            currentTask.stop();
        }

        this.targetPos = predefinedTargetPos;
        this.currentTarget = new Vector2(targetPos.x, owner.getEntity().getPosition().y);
        startMoving();
        NPCFactory.decreaseCustomerCount();
    }

    /**
     * Remove the customer entity from the game
     */
    private void removeCustomerEntity() {
        logger.info("Customer reached predefined position and will be removed.");
        ServiceLocator.getEntityService().unregister(owner.getEntity());
        owner.getEntity().dispose(); // Dispose of the entity
    }

    private float getDeltaTime() {
        return 1 / 60f;
    }
}
