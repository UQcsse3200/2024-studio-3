package com.csse3200.game.components.upgrades;

/**
 * An Upgrade that activate the specific upgrade name when it is called and deactivate after reaches time limit
 */
public interface Upgrade {
    /** Activate the upgrade, this will be called once the random upgrade is generated */
    void activate();

    /** Deactivate the activated upgrade once it reaches time limit */
    void deactivate();

}
