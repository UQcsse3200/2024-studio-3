package com.csse3200.game.components.npc;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Component that manages the positions of ghosts and handles their movement to available spots.
 */
public class GhostPositionManagerComponent extends Component {
    private static final Vector2[] ghostPositions = {
            new Vector2(0f, 0f),   // Position 1
            new Vector2(5f, 5f),   // Position 2
            new Vector2(10f, 10f)  // Position 3
    };

    private final Queue<Entity> ghostQueue = new LinkedList<>();
    private final boolean[] positionOccupied = new boolean[ghostPositions.length];

    /**
     * Adds a ghost to the manager and assigns it to the next available position.
     *
     * @param ghost The ghost entity
     */
    public void addGhost(Entity ghost) {
        int positionIndex = getNextAvailablePosition();
        if (positionIndex != -1) {
            assignGhostToPosition(positionIndex);
            ghostQueue.add(ghost);
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

    private void assignGhostToPosition(int positionIdx) {
        positionOccupied[positionIdx] = true;
    }

}
