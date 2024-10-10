package com.csse3200.game.components;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.physics.BodyUserData;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.utils.math.Vector2Utils;

import java.util.HashSet;
import java.util.Set;
import java.util.Vector;
/**
 * Finds the closest interactable object inside a range of the player
 */
public class SensorComponent extends Component {
    private final short targetLayer;
    private final float sensorDistance;
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
        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
        entity.getEvents().addListener("collisionEnd", this::onCollisionEnd);
        this.interactionComponent = entity.getComponent(InteractionComponent.class);
        super.create();
    }

    /**
     * Getters
     */

    public InteractionComponent getInteractionComponent() {
        return this.interactionComponent;
    }

    public short getTargetLayer() {
        return this.targetLayer;
    }

    public Set<Fixture> getClosestFixtures() {
        return this.collidingFixtures;
    }

    public int getNumFixtures() {
        return this.collidingFixtures.size();
    }

    /**
     *  Called when the component collides with another collider
     * @param me Should be the fixture that got collided with - aka this sensor component
     * @param other The fixture that collided with this component
     */
    public void onCollisionStart(Fixture me, Fixture other) {
        if (interactionComponent.getFixture() != me) {
            // Not triggered by me, so ignore
            return;
        }
        // Check that the fixture has the correct target layer
        if (!PhysicsLayer.contains(targetLayer, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }
        // Check if the collidingFixture is close enough sensor
        if (isWithinDistance(other, sensorDistance))
        {
            // Update the collision set
            collidingFixtures.add(other);
        }
        //Update the set of fixtures
        updateFixtures();
    }

    /**
     * Called when a collision has ended between 2 fixtures
     * @param me Should be the fixture that got collided with - aka this sensor component
     * @param other The fixture that this component stopped colliding with
     */
    public void onCollisionEnd(Fixture me, Fixture other) {
        if (interactionComponent.getFixture() != me) {
            // Not triggered by interactionComponent, so ignore
            return;
        }
        if (!PhysicsLayer.contains(targetLayer, other.getFilterData().categoryBits)) {
            // Doesn't match our target layer, ignore
            return;
        }
        // Remove the fixture if it was previously detected
        collidingFixtures.remove(other);
        //Update the set of fixtures
        updateFixtures();
    }

    /**
     * Removes any fixtures that are not in range of the sensor and updates the closest fixture
     */
    private void updateFixtures() {
        Set<Fixture> toRemove = new HashSet<>();
        Fixture previousClosestFixture = closestFixture;
        for (Fixture fixture : collidingFixtures) {
            float dist = getFixtureDistance(fixture);
            if (dist > sensorDistance) {
                toRemove.add(fixture);
            } else if (closestDistance < 0 || dist < closestDistance) {
                closestDistance = dist;
                closestFixture = fixture;
            }
        }
        collidingFixtures.removeAll(toRemove);

        // If no colliding fixtures, then it is empty
        if (collidingFixtures.isEmpty()) {
            closestFixture = null;
            closestDistance = -1f;
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
    public boolean isWithinDistance(Fixture fixture, float distance) {
        return getFixtureDistance(fixture) <= distance;
    }

}
