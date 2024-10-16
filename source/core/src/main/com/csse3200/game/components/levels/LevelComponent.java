package com.csse3200.game.components.levels;

import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.GdxGame;
import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.npc.PersonalCustomerEnums;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Random;
import java.util.HashMap;

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
    private HashMap<GdxGame.LevelType , ArrayList<String>> acceptableCustomers;

    /**
     * Initialise the map of acceptable customers in each level
     * key = game level i.e. LEVEL_1, value = array list of customers
     */
    public void initialiseAcceptableCustomers() {
        ArrayList<String> levelOne = new ArrayList<>();
        levelOne.add("JOHN");

        ArrayList<String> levelTwo = new ArrayList<>();
        levelTwo.add("JOHN");
        levelTwo.add("HANK");

        ArrayList<String> levelThree = new ArrayList<>();
        levelThree.add("JOHN");
        levelThree.add("SILVER");

        ArrayList<String> levelFour = new ArrayList<>();
        levelFour.add("HANK");
        levelFour.add("MOONKI");
        levelFour.add("LEWIS");

        ArrayList<String> levelFive = new ArrayList<>();
        levelFive.add("HANK");
        levelFive.add("JOHN");
        levelFive.add("SILVER");
        levelFive.add("MOONKI");
        levelFive.add("LEWIS");

        acceptableCustomers = new HashMap<>();
        acceptableCustomers.put(GdxGame.LevelType.LEVEL_0, levelOne);
        acceptableCustomers.put(GdxGame.LevelType.LEVEL_1, levelOne);
        acceptableCustomers.put(GdxGame.LevelType.LEVEL_2, levelTwo);
        acceptableCustomers.put(GdxGame.LevelType.LEVEL_3, levelThree);
        acceptableCustomers.put(GdxGame.LevelType.LEVEL_4, levelFour);
        acceptableCustomers.put(GdxGame.LevelType.LEVEL_5, levelFive);
    }

    /**
     * Initialise the customer name array which is dependent on which customers are acceptable in each level.
     */
    public void initialiseCustomerNameArr() {
        customerNameArray = new ArrayList<>();
        for (PersonalCustomerEnums customer: PersonalCustomerEnums.values()) {
            String name = customer.name();
            ArrayList<String> customersInLevel = acceptableCustomers.get(gameArea.getLevel());
            // basic sheep & chicken was included in a previous commit, ive left it here
            if (!name.equals("BASIC_SHEEP") && !name.equals("BASIC_CHICKEN") && customersInLevel.contains(name)) {
                customerNameArray.add(customer.name());
            }
        }
    }

    /**
     * Initialises the component to add necessary listeners to the LevelService and initialise an array with customer
     * names
     */
    public void create() {
        super.create();
        ServiceLocator.getLevelService().getEvents().addListener("startSpawning", this::initSpawning);
        ServiceLocator.getLevelService().getEvents().addListener("setGameArea", this::setGameArea);
        initialiseAcceptableCustomers();
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
            initialiseCustomerNameArr();
            // if more than 40 secs or no customers then spawn. keep track of limits on customer num size
            // this can certainly be changed I just put in an arbitrary time
            if ((currentCustomersLinedUp == 0 || elapsedTimeSecs >= 40) && numbCustomersSpawned < levelSpawnCap && currentCustomersLinedUp < 3) {
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
            // say they "left" the lineup if 40 secs have passed to trigger the next spawning
            // NOTE: this does NOT change where the customer is or remove them, it just triggers the next spawning
            if (elapsedTimeSecs >= 40) {
                customerLeftLineUp();
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
        // add to "line up" (not actually but for our purposes)
        customerJoinedLineUp();
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
     * Stores the new levels spawn capacity in  the levelSpawnCap attribute
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
     * @return whether the game is spawning customers
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
     * Get the Entity that handles all the customer spawning events
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
