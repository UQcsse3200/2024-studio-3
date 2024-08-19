package com.csse3200.game.components.npc;

import com.csse3200.game.components.Component;
import com.csse3200.game.entities.configs.BaseCustomerConfig;
import com.csse3200.game.entities.configs.CustomerPersonalityConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomerComponent extends Component {
    private static final Logger logger = LoggerFactory.getLogger(CustomerComponent.class);
    private int countDown;
    private int reputation;
    private int patience;
    private int spawnTimer;
    private String name;
    private int pref;


    public CustomerComponent(BaseCustomerConfig config) {
        this.name = config.name;
        this.patience = config.patience;
        this.spawnTimer = config.spawnTimer;
        this.countDown = config.countDown;
    }

    public CustomerComponent(CustomerPersonalityConfig config) {
        this.name = config.name;
        this.patience = config.patience;
        this.spawnTimer = config.spawnTimer;
        this.countDown = config.countDown;
        this.reputation = config.reputation;
        this.pref = config.pref;
    }

    public void setReputation(int reputation) {
        if (this.reputation < 0) {
            logger.error("Reputation cannot be set for general customers");
        } else if (reputation > 100 || reputation < 0) {
            logger.error("Reputation needs to be between 0 and 100");
        } else {
            this.reputation = reputation;
        }
    }

    public int getReputation() {
        return this.reputation;
    }
    // make getter and setter for rest
}
