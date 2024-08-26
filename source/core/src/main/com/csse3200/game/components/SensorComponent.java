package com.csse3200.game.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.components.InteractionComponent;

import java.util.HashSet;
import java.util.Set;

/**
 * Finds the closest interactable object inside a range of the player
 */
public class SensorComponent extends Component{
    private short targetLayer;
    private float sensorDistance = 1f;
    private InteractionComponent interactionComponent;
    private Set<Fixture> collidingFixtures = new HashSet<>();

    private Fixture closestFixture = null;
    private float closestDistance = -1f;

    /**
     * Create a component that senses entities when it collides with them.
     * @param targetLayer The physics layer of the target's collider
     */
    public SensorComponent(short targetLayer, float sensorDistance) {
        this.targetLayer = targetLayer;
        this.sensorDistance = sensorDistance;
    }

    @Override
    public void create() {
        entity.getEvents().addListener("interactClose", this::onCollisionStart);
        interactionComponent = entity.getComponent(InteractionComponent.class);
    }

    private void onCollisionStart(Fixture me, Fixture other) {
        if (interactionComponent.getFixture() != me) {
            // Not triggered by interactionComponent, so ignore
            return;
        }

        if (!PhysicsLayer.contains(targetLayer, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }

        // Check if the collidingFixture is close enough senser
        if (isWithinDistance(other, sensorDistance))
        {
            // Update the collision set
            collidingFixtures.add(other);
        }

        updateFixtures();
    }

    /**
     * Removes any fixtures that are not in range of the sensor and updates the closest fixture
     */
    private void updateFixtures() {
        if (collidingFixtures.isEmpty()) {
            closestDistance = -1;
            closestFixture = null;
        } else {
            for (Fixture fixture : collidingFixtures){
                float dist = getFixtureDistance(fixture);
                if (isWithinDistance(fixture, sensorDistance)) {
                    // Check if the fixture is the closest one
                    if (closestDistance < 0 || dist < closestDistance) {
                        closestDistance = dist;
                        closestFixture = fixture;
                    }
                } else {
                    // Fixture is no longer in range, remove it
                    collidingFixtures.remove(fixture);
                }
            }
        }
    }

    public Fixture getClosestFixture() {
        return this.closestFixture;
    }

    /**
     * Returns the distance from the sensor component to the given fixture
     * @param fixture The object being measured
     * @return The distance from the sensor object to the given fixture
     */
    private float getFixtureDistance(Fixture fixture) {
        Vector2 sensorPosition = interactionComponent.getFixture().getBody().getPosition();
        Vector2 fixturePosition = fixture.getBody().getPosition();
        return sensorPosition.dst(fixturePosition);
    }

    /**
     * Determines whether the distance to the fixture is within the required distance
     * @param fixture The object being measured
     * @param distance The maximum distance the fixture can be away from the sensor component
     * @return True: If the distance to the fixture is less than the provided distance, False: Otherwise
     */
    private boolean isWithinDistance(Fixture fixture, float distance) {
        return getFixtureDistance(fixture) <= distance;
    }
}
