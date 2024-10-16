package com.csse3200.game.components.tasks;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.ai.tasks.DefaultTask;
import com.csse3200.game.ai.tasks.PriorityTask;
import com.csse3200.game.ai.tasks.Task;
import com.csse3200.game.ai.tasks.TaskRunner;
import com.csse3200.game.components.ScoreSystem.HoverBoxComponent;
import com.csse3200.game.entities.Entity;
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

    private Vector2 predefinedTargetPos = new Vector2(1, 0);
    private float elapsedTime = 0f;
    boolean hasMovedToPredefined = false;
    private float elapsedTime2 = 0f;
    private float upgradeStart = 3f;
    private boolean hoverboxcheck = false;
    private float makingTime; // Dynamic making time based on the recipe
    private boolean reachedFirstTarget = false;

    /**
     * Task to make an entity follow a path to a target position.
     * @param targetPos The target position to move to
     * @param waitingTime The making time from the recipe, scaled by any time factor like DEFAULT_TIMER if needed.
     */
    public PathFollowTask(Vector2 targetPos, float waitingTime) {
        this.targetPos = targetPos;
        this.makingTime = waitingTime;
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
        this.elapsedTime2 = 0f;
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
        if (reachedFirstTarget) {
            elapsedTime += getDeltaTime(); // Start counting only after the customer has arrived
        }
        elapsedTime2 += getDeltaTime();

        // Check if it's time to move to the predefined position
        if (!hasMovedToPredefined && elapsedTime >= makingTime) {
            Entity customer = owner.getEntity();
            HoverBoxComponent hoverBox = customer.getComponent(HoverBoxComponent.class);
            hoverBox.setTexture(new Texture("images/customer_faces/angry_face.png"));
            triggerMoveToPredefinedPosition();
            hasMovedToPredefined = true;
        }

        if (!reachedFirstTarget && targetPos.epsilonEquals(owner.getEntity().getPosition(), 0.1f)) {
            owner.getEntity().getEvents().trigger("customerArrived");
            reachedFirstTarget = true;
        }

        if(!hoverboxcheck && elapsedTime2 >= upgradeStart){
            owner.getEntity().getEvents().trigger("ready");
            elapsedTime2 = 0;
            hoverboxcheck = true;

        }
        if(hoverboxcheck && elapsedTime2 >= upgradeStart){
            owner.getEntity().getEvents().trigger("unready");
            hoverboxcheck = false;
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
