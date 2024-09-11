/*package com.csse3200.game.components.npc;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.components.tasks.PathFollowTask;
import java.util.LinkedList;
import java.util.Queue;
import com.csse3200.game.ai.tasks.AITaskComponent;

/**
 * Component that manages the positions of customers and handles their movement to available spots.

public class CustomerPositionManagerComponent extends Component {
    private static final Vector2[] customerPositions = {
            new Vector2(0f, 0f),   // Position 1
            new Vector2(5f, 5f),   // Position 2
            new Vector2(10f, 10f)  // Position 3
    };

    private final Queue<Entity> customerQueue = new LinkedList<>();
    private final boolean[] positionOccupied = new boolean[customerPositions.length];

    /**
     * Adds a customer to the manager and assigns it to the next available position.
     *
     * @param customer The customer entity

    public void addCustomer(Entity customer) {
        int positionIndex = getNextAvailablePosition();
        if (positionIndex != -1) {
            assignCustomerToPosition(customer, positionIndex);
            customerQueue.add(customer);
        } else {
            customerQueue.add(customer);
        }
    }

    private int getNextAvailablePosition() {
        for (int i = 0; i < positionOccupied.length; i++) {
            if (!positionOccupied[i]) {
                return i;
            }
        }
        return -1;
    }

    private void assignCustomerToPosition(Entity customer, int positionIdx) {
        positionOccupied[positionIdx] = true;
        // Assign the customer to move to the position using PathFollowTask
        AITaskComponent aiComponent = customer.getComponent(AITaskComponent.class);
        if (aiComponent != null) {
            aiComponent.addTask(new PathFollowTask(customerPositions[positionIdx]));
        }
    }

    public void freeCustomerPosition(Entity customer) {
        customerQueue.remove(customer);
        for (int i = 0; i < customerPositions.length; i++) {
            if (positionOccupied[i]) {
                positionOccupied[i] = false;
                break;
            }
        }

        if (!customerQueue.isEmpty()) {
            Entity nextCustomer = customerQueue.peek();
            int positionIndex = getNextAvailablePosition();
            if (positionIndex != -1) {
                assignCustomerToPosition(nextCustomer, positionIndex);
            }
        }
    }
}
*/