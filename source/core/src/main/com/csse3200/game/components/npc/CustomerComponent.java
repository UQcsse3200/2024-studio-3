package com.csse3200.game.components.npc;

import com.csse3200.game.components.Component;
import com.csse3200.game.entities.configs.BaseCustomerConfig;
import com.csse3200.game.entities.configs.CustomerPersonalityConfig;
import com.csse3200.game.components.tasks.PathFollowTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.ai.tasks.TaskRunner;
import com.csse3200.game.entities.Entity;

/**
 * CustomerComponent controls the behavior and state of a customer entity,
 * including countdown logic, position updates, and recipe preference display.
 */
public class CustomerComponent extends Component implements TaskRunner {
    private static final Logger logger = LoggerFactory.getLogger(CustomerComponent.class);

    private int countDown;
    private int patience;
    private String preference;  // Recipe preference of the customer
    private String name;

    private CountdownManager countdownManager;
    private Vector2 targetPosition = new Vector2(); // Current target position for movement
    private PathFollowTask pathFollowTask; // Task for path following

    /**
     * Constructor for customers using the BaseCustomerConfig.
     *
     * @param config Configuration for basic customers.
     */
    public CustomerComponent(BaseCustomerConfig config) {
        this.patience = config.patience;
        this.countDown = config.countDown;
        this.preference = config.preference;  // Fetch the recipe preference
        this.countdownManager = new CountdownManager(this.countDown);
    }

    /**
     * Constructor for customers using the CustomerPersonalityConfig.
     *
     * @param config Configuration for personality-based customers.
     */
    public CustomerComponent(CustomerPersonalityConfig config) {
        this.patience = config.patience;
        this.countDown = config.countDown;
        this.preference = config.preference;
        this.name = config.name;
        this.countdownManager = new CountdownManager(this.countDown);
    }

    @Override
    public void create() {
        super.create();
        countdownManager.start();
        initializePathFollowTask();

        // Display the customer's preference (recipe) when created
        displayRecipePreference();
    }

    @Override
    public void update() {
        super.update();
        countdownManager.update();

        if (countdownManager.isCountdownComplete()) {
            handleCountdownComplete();
            countdownManager.reset(); // Reset or adjust countdown if necessary
        }
    }

    /**
     * Displays the recipe preference of the customer.
     */
    private void displayRecipePreference() {
        logger.info("Customer {} has a recipe preference for: {}", name, preference);
        // Here you can add additional logic to show the recipe in the game's UI
        // Example: displayRecipeOnTicket(preference);
    }

    private void initializePathFollowTask() {
        // Initialize path follow task with the initial target position
        pathFollowTask = new PathFollowTask(targetPosition, 5f); // Example waitTime
        pathFollowTask.create(this);
    }

    private void handleCountdownComplete() {
        logger.info("Countdown complete for {}! Moving the customer to a new position.", name);

        // Determine and set the new target position
        Vector2 newPosition = determineNewPosition(); // Implement this method as needed
        setTargetPosition(newPosition);

        // Initialize and start the PathFollowTask with the new target position
        if (pathFollowTask != null) {
            pathFollowTask.stop();
        }
        pathFollowTask = new PathFollowTask(targetPosition, 5f); // Example waitTime
        pathFollowTask.create(this);
        pathFollowTask.start();
    }

    private Vector2 determineNewPosition() {
        // Implement logic to determine the new target position for the customer
        // Example placeholder implementation:
        return new Vector2(10f, 10f); // Replace with actual logic
    }

    public void setCountDown(int countDown) {
        this.countDown = countDown;
        countdownManager.setCountDown(countDown);
    }

    public int getCountDown() {
        return this.countDown;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public String getPreference() {
        return this.preference;
    }

    public void setTargetPosition(Vector2 newPosition) {
        this.targetPosition.set(newPosition);
    }

    @Override
    public Entity getEntity() {
        return super.getEntity(); // Ensure this correctly returns the entity associated with this component
    }
}
