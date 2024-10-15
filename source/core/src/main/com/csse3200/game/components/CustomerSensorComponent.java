package com.csse3200.game.components;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.physics.BodyUserData;
import com.csse3200.game.entities.Entity;

import java.util.HashSet;
import java.util.Set;

/**
 * Finds the closest customer entity within a range of the player.
 */
public class CustomerSensorComponent extends Component {
    private final float sensorDistance;
    private InteractionComponent interactionComponent;
    private final Set<Fixture> collidingFixtures = new HashSet<>();
    private Fixture closestFixture = null;
    private float closestDistance = -1f;

    /**
     * Create a component that senses customer entities when it collides with them.
     * @param sensorDistance The range of the sensor (how far it can detect)
     */
    public CustomerSensorComponent(float sensorDistance) {
        this.sensorDistance = sensorDistance;
    }

    @Override
    public void create() {

        entity.getEvents().addListener("collisionStart", this::onCollisionStart);
        entity.getEvents().addListener("collisionEnd", this::onCollisionEnd);
        this.interactionComponent = entity.getComponent(InteractionComponent.class);

        if (interactionComponent == null) {
            
        
        super.create();
    }
  }

    public void onCollisionStart(Fixture me, Fixture other) {
        if (interactionComponent.getFixture() != me) {
            return;
        }
        if (!isCustomer(other)) {
            return;
        }

        if (isWithinDistance(other, sensorDistance)) {
            collidingFixtures.add(other);
        }
        updateFixtures();
    }

    public void onCollisionEnd(Fixture me, Fixture other) {
        if (interactionComponent.getFixture() != me) {
            return;
        }
        if (!isCustomer(other)) {
            return;
        }

        collidingFixtures.remove(other);
        updateFixtures();
    }

    private void updateFixtures() {
        Set<Fixture> toRemove = new HashSet<>();
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

        if (collidingFixtures.isEmpty()) {
            closestFixture = null;
            closestDistance = -1f;
        }

    }

    private boolean isCustomer(Fixture fixture) {
        Entity entity = ((BodyUserData) fixture.getBody().getUserData()).entity;
        return entity.getComponent(CustomerComponent.class) != null;
    }

    private float getFixtureDistance(Fixture fixture) {
        Vector2 sensorPosition = interactionComponent.getFixture().getBody().getPosition();
        Vector2 fixturePosition = fixture.getBody().getPosition();
        return sensorPosition.dst(fixturePosition);
    }

    public boolean isWithinDistance(Fixture fixture, float distance) {
        float actualDistance = getFixtureDistance(fixture);
        return actualDistance <= distance;
    }

    public Fixture getClosestCustomer() {
        return closestFixture;
    }
    public InteractionComponent getInteractionComponent() {
      return interactionComponent;
  }
}
