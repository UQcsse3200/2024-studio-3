package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;  // Import the Actor class
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.Component;
import com.csse3200.game.input.InputComponent;  // Assuming InputComponent is an interface
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * This class listens to events related to the Order System and handles actions when events are triggered.
 */
public class OrderActions extends InputComponent {
    private static final Logger logger = LoggerFactory.getLogger(OrderActions.class);
    private GdxGame game;
    private Docket draggedDocket;
    private float offsetX, offsetY;
    private Vector2[] snapPositions;
    private final float SNAP_DISTANCE = 50f; // Adjust as needed

    public OrderActions(GdxGame game) {
        this.game = game;
    }

    @Override
    public void create() {
        super.create();

        entity.getEvents().addListener("addOrder", this::onAddOrder);
        entity.getEvents().addListener("removeOrder", this::onRemoveOrder);
        entity.getEvents().addListener("moveOrder", this::onMoveOrder);
        entity.getEvents().addListener("changeColour", this::onChangeColour);

        // Register this component as an input handler
        ServiceLocator.getInputService().register(this);

        snapPositions = new Vector2[] {
            new Vector2(100, 500), // Example positions, adjust to fit your layout
            new Vector2(200, 500),
            new Vector2(300, 500),
            new Vector2(400, 500),
            new Vector2(500, 500)
        };
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.LEFT_BRACKET) {
            onShiftDocketsLeft();
            return true;
        } else if (keycode == Keys.RIGHT_BRACKET) {
            onShiftDocketsRight();
            return true;
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector2 stageCoords = new Vector2(screenX, screenY);
        ServiceLocator.getDocketService().getDocketTable().stageToLocalCoordinates(stageCoords);

        for (Actor actor : ServiceLocator.getDocketService().getDocketTable().getChildren()) {
            // Assuming actor.getUserObject() returns the Docket if it's associated with the Actor
            if (actor.getUserObject() instanceof Docket) {
                Docket docket = (Docket) actor.getUserObject();
                // Assuming Docket has methods for checking position (you may need to implement this in Docket)
                if (isWithinBounds(docket, stageCoords)) {
                    draggedDocket = docket;
                    offsetX = stageCoords.x - docket.getX(); // Assuming Docket has getX()
                    offsetY = stageCoords.y - docket.getY(); // Assuming Docket has getY()
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        if (draggedDocket != null) {
            Vector2 closestPosition = null;
            float closestDistance = Float.MAX_VALUE;

            for (Vector2 position : snapPositions) {
                float distance = new Vector2(draggedDocket.getX(), draggedDocket.getY()).dst(position); // Assuming Docket has getX(), getY()
                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestPosition = position;
                }
            }

            if (closestPosition != null && closestDistance <= SNAP_DISTANCE) {
                draggedDocket.setPosition(closestPosition.x - offsetX, closestPosition.y - offsetY); // Assuming Docket has setPosition()
            }

            logger.info("Dropped docket at position: " + draggedDocket.getX() + ", " + draggedDocket.getY());
            draggedDocket = null;  // Clear the dragged docket reference
            return true;
        }
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        if (draggedDocket != null) {
            Vector2 stageCoords = new Vector2(screenX, screenY);
            ServiceLocator.getDocketService().getDocketTable().stageToLocalCoordinates(stageCoords);

            stageCoords.y = Math.max(480, Math.min(stageCoords.y, 520)); // Adjust based on your layout

            draggedDocket.setPosition(stageCoords.x - offsetX, stageCoords.y - offsetY); // Assuming Docket has setPosition()
            return true;
        }
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }

    /**
     * Adds order to the line
     */
    private void onAddOrder() {
        logger.info("Add order");
        Docket newDocket = new Docket();
        ServiceLocator.getDocketService().addDocket(newDocket);
    }

    /**
     * Removes order from the line
     */
    private void onRemoveOrder() {
        logger.info("Remove order");
        if (!ServiceLocator.getDocketService().getDocketTable().hasChildren()) return;
        Actor actor = ServiceLocator.getDocketService().getDocketTable().getChildren().first();
        if (actor.getUserObject() instanceof Docket) {
            Docket firstDocket = (Docket) actor.getUserObject();
            ServiceLocator.getDocketService().removeDocket(firstDocket);
        }
    }

    /**
     * Shifts dockets to the left.
     */
    private void onShiftDocketsLeft() {
        logger.info("Shift dockets left");
        ServiceLocator.getDocketService().shiftDocketsLeft();
    }

    /**
     * Shifts dockets to the right.
     */
    private void onShiftDocketsRight() {
        logger.info("Shift dockets right");
        ServiceLocator.getDocketService().shiftDocketsRight();
    }

    /**
     * Moves order
     */
    private void onMoveOrder() {
        logger.info("Move order");
    }

    /**
     * Changes order colour based on recipe timer
     */
    private void onChangeColour() {
        logger.info("Change order color");
    }

    /**
     * Helper method to check if the touch coordinates are within the bounds of the docket.
     */
    private boolean isWithinBounds(Docket docket, Vector2 coords) {
        // Assuming Docket has getX(), getY(), getWidth(), getHeight()
        float x = docket.getX();
        float y = docket.getY();
        float width = docket.getWidth();
        float height = docket.getHeight();

        return coords.x >= x && coords.x <= x + width && coords.y >= y && coords.y <= y + height;
    }
}