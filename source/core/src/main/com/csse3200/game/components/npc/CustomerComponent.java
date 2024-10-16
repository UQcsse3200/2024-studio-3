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
    private String type;
    private String name;
    private String preference;
    private String orderNumber;

    /**
     *  Set the order number for the customer
     * @param orderNumber the order number
     */
    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    /**
     * Get the order number for the customer
     * @return the order number
     */
    public String getOrderNumber() {
        return orderNumber;
    }

    /**
     * Constructor for the CustomerComponent
     * @param config the configuration for the customer
     */
    public CustomerComponent(BaseCustomerConfig config) {
        this.type = config.type;
        this.patience = config.patience;
        this.countDown = config.countDown;
        this.preference = config.preference;
    }

    /**
     * Constructor for the CustomerComponent
     * @param config the configuration for the customer
     */
    public CustomerComponent(CustomerPersonalityConfig config) {
        this.name = config.name;
        this.type = config.type;
        this.patience = config.patience;
        this.countDown = config.countDown;
        this.reputation = config.reputation;
        this.preference = config.preference;
    }

    /**
     * Set the reputation of the customer
     * @param reputation the reputation of the customer
     */
    public void setReputation(int reputation) {
        if (this.reputation < 0) {
            logger.error("Reputation cannot be set for general customers");
        } else if (reputation > 100 || reputation < 0) {
            logger.error("Reputation needs to be between 0 and 100");
        } else {
            this.reputation = reputation;
        }
    }

    /**
     * Get the reputation of the customer
     * @return the reputation of the customer
     */
    public int getReputation() {
        return this.reputation;
    }

    /**
     * Set the patience of the customer
     * @param patience the patience of the customer
     */
    public void setPatience(int patience) {
        this.patience = patience;
    }

    /**
     * Get the patience of the customer
     * @return the patience of the customer
     */
    public int getPatience() {
        return this.patience;
    }

    /**
     *  Set the spawn timer for the customer
     * @param spawnTimer the spawn timer
     */
    public void setSpawnTimer(int spawnTimer) {
        this.spawnTimer = spawnTimer;
    }

    /**
     * Get the spawn timer for the customer
     * @return the spawn timer
     */
    public int getSpawnTimer() {
        return this.spawnTimer;
    }

    /**
     * Set the countdown for the customer
     * @param countDown the countdown
     */
    public void setCountDown(int countDown) {
        this.countDown = countDown;
    }

    /**
     * Get the countdown for the customer
     * @return the countdown
     */
    public int getCountDown() {
        return this.countDown;
    }

    /**
     * Set the name of the customer
     * @param name the name of the customer
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Get the name of the customer
     * @return the name of the customer
     */
    public String getName() {
        return this.name;
    }

    /**
     * Set the preference of the customer
     * @param preference the preference of the customer
     */
    public void setPreference(String preference) {
        this.preference = preference;
    }

    /**
     * Get the preference of the customer
     * @return the preference of the customer
     */
    public String getPreference() {
        return this.preference;
    }

    /**
     * Get the type of the customer
     * @return the type of the customer
     */
    public String getType() {
        return this.type;
    }
}
