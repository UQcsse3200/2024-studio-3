package com.csse3200.game.components;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.components.tasks.PathFollowTask;
import com.csse3200.game.ai.tasks.TaskRunner;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerBehaviorComponent extends Component implements TaskRunner {
    private static final Logger logger = LoggerFactory.getLogger(CustomerBehaviorComponent.class);

    private PathFollowTask pathFollowTask;
    private Vector2 targetPosition = new Vector2(); // The current target position of the customer

    @Override
    public void create() {
        super.create();

        // Initialize the path-follow task with an initial target position
        Vector2 initialTarget = new Vector2(0, 0); // Replace with actual initial target
        pathFollowTask = new PathFollowTask(initialTarget, getEntity().getId());
        pathFollowTask.create(this); // Create the path-follow task

        // Listener for the "leaveEarly" event, which triggers when the customer should leave early
        getEntity().getEvents().addListener("leaveEarly", this::moveToEnd);
    }

    @Override
    public void update() {
        super.update();

        // Update the path-follow task each frame
        if (pathFollowTask != null) {
            pathFollowTask.update();
        }
    }

    // Method to move the customer to the predefined position (when they leave early)
    private void moveToEnd() {
        if (pathFollowTask != null) {
            logger.debug("Customer is leaving early. Moving to predefined position.");
            pathFollowTask.triggerMoveToPredefinedPosition(); // Trigger early exit movement
        }
    }

    // Method to set a new target position for the customer
    public void setTargetPosition(Vector2 newPosition) {
        this.targetPosition.set(newPosition); // Update the internal target position

        if (pathFollowTask != null) {
            // Stop the current task and create a new one with the updated target position
            pathFollowTask.stop();
            pathFollowTask = new PathFollowTask(targetPosition, getEntity().getId());
            pathFollowTask.create(this);
            pathFollowTask.start();
        }
    }
}
