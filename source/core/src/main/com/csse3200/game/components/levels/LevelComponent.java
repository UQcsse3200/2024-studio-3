package com.csse3200.game.components.levels;

import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.npc.PersonalCustomerEnums;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Random;

public class LevelComponent extends Component {
    private static final Logger logger = LoggerFactory.getLogger(LevelComponent.class);
    private static final Random rand = new Random();
    private long spawnStartTime = 0;
    private boolean nowSpawning = false;
    private int levelSpawnCap = 0;
    private int numbCustomersSpawned = 0;
    private int currentCustomersLinedUp = 0;
    private ForestGameArea gameArea;
    private Entity customerSpawnController;
    private ArrayList<String> customerNameArray;

    /**
     * Initialises the component to add necessary listeners to the LevelService and initialise an array with customer
     * names
     */
    public void create() {
        super.create();
        ServiceLocator.getLevelService().getEvents().addListener("startSpawning", this::initSpawning);
        ServiceLocator.getLevelService().getEvents().addListener("setGameArea", this::setGameArea);
        customerNameArray = new ArrayList<>();
        for (PersonalCustomerEnums customer: PersonalCustomerEnums.values()) {
            String name = customer.name();
            if (name != "BASIC_SHEEP" && name != "BASIC_CHICKEN") {
                customerNameArray.add(customer.name());
            }
        }
    }

    /**
     * Checks if the level should be spawning. If it is, then it calculates the seconds since the last customer spawned,
     * and if it has been more than three seconds and the spawn capacity of the level has not been reached yet, then
     * spawn another customer
     */
    @Override
    public void update() {
        if (nowSpawning) {
            long elapsedTime = TimeUtils.timeSinceMillis(spawnStartTime);
            long elapsedTimeSecs = elapsedTime / 1000;
            //If more than five seconds have passed, there are more customers to spawn
            //AND if another customer can join the line
            if (elapsedTimeSecs >= 3 && numbCustomersSpawned < levelSpawnCap && currentCustomersLinedUp < 5) {
                setSpawnStartTime();
                customerSpawned();
                spawnCustomer();
                //ServiceLocator.getLevelService().getEvents().trigger("createCustomer", gameArea);
                logger.info("Spawned {} customer(s) so far", numbCustomersSpawned);
                if (numbCustomersSpawned == levelSpawnCap) {
                    logger.info("Hit the spawn limit of {} with {}", getLevelSpawnCap(), getNumbCustomersSpawned());
                    toggleNowSpawning();
                }
            }
        }
    }

    /**
     * Ensures that all private attributes are set to default and then enables spawning
     *
     * @param spawnCap the number of customers that need to be spawned in the current level
     */
    public void initSpawning(int spawnCap) {
        resetCustomerSpawn();
        setLevelSpawnCap(spawnCap);
        setSpawnStartTime();
        toggleNowSpawning();
    }

    /**
     * Function called to trigger a random customer spawn event
     */
    private void spawnCustomer() {
        int index = rand.nextInt(customerNameArray.size());
        customerSpawnController.getEvents().trigger(customerNameArray.get(index));
        logger.info("Spawned {}", customerNameArray.get(index));
        ServiceLocator.getLevelService().getEvents().trigger("customerSpawned", customerNameArray.get(index));
    }

    /**
     * Sets and stores the current GameArea/map in a private attribute
     *
     * @param newGameArea the new game area that needs to be stored
     */
    public void setGameArea (ForestGameArea newGameArea) {
        gameArea = newGameArea;
        setCustomerSpawnController(gameArea.getCustomerSpawnController());
    }

    /**
     * Gets the Entity responsible for storing and handling all customer spawn events
     *
     * @param var the Entity with all spawning events
     */
    public void setCustomerSpawnController(Entity var) {
        customerSpawnController = var;
    }

    /**
     * Make the private attribute spawnStartTime store the time when the most recently spawned customer was
     * actually spawned
     */
    public void setSpawnStartTime() {
        spawnStartTime = TimeUtils.millis();
    }

    /**
     * Inverses the boolean value of nowSpawning attribute
     */
    public void toggleNowSpawning() {
        nowSpawning = !nowSpawning;
    }

    /**
     * Stores the new levels spawn capacity in the levelSpawnCap attribute
     *
     * @param cap the number of customers that must be spawned
     */
    public void setLevelSpawnCap(int cap) {
        levelSpawnCap = cap;
    }

    /**
     * Get the current number of customers in the line
     *
     * @return the current number of customers in the line
     */
    public int getCurrentCustomersLinedUp() {
        return currentCustomersLinedUp;
    }

    /**
     * Get the total number of customers that have been spawned in the level currently
     *
     * @return the number of customers that have spawned
     */
    public int getNumbCustomersSpawned() {
        return numbCustomersSpawned;
    }

    /**
     * Is the game spawning customers?
     *
     * @return whether or not the game is spawning customers
     */
    public boolean getNowSpawning() {
        return nowSpawning;
    }

    /**
     * Get the number of customers required to spawn in the level
     *
     * @return the number of customers that must spawn in the level
     */
    public int getLevelSpawnCap() {
        return levelSpawnCap;
    }

    /**
     * Get the current GameArea/Map
     *
     * @return the current ForestGameArea
     */
    public ForestGameArea getGameArea() {
        return gameArea;
    }

    /**
     * Get the Entity that handles all of the customer spawning events
     *
     * @return the Entity with all customer spawning events
     */
    public Entity getCustomerSpawnController() {
        return customerSpawnController;
    }

    /**
     * Get the time that the most recent customer spawned
     *
     * @return when the latest customer spawned
     */
    public Long getSpawnStartTime() {
        return spawnStartTime;
    }

    /**
     * Update that a customer joined the line
     */
    public void customerJoinedLineUp() {
        currentCustomersLinedUp++;
    }

    /**
     * Update that a customer left the line
     */
    public void customerLeftLineUp() {
        currentCustomersLinedUp--;
    }

    /**
     * Add one to the total number of customers spawned
     */
    public void customerSpawned() {
        numbCustomersSpawned++;
    }

    /**
     * Set the number of customers that have spawned to 0
     */
    public void resetCustomerSpawn() {
        numbCustomersSpawned = 0;
    }
}
