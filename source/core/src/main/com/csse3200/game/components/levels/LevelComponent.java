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

    public void create() {
        super.create();
        ServiceLocator.getLevelService().getEvents().addListener("startSpawning", this::initSpawning);
        ServiceLocator.getLevelService().getEvents().addListener("setGameArea", this::setGameArea);
        customerNameArray = new ArrayList<>();
        for (PersonalCustomerEnums customer: PersonalCustomerEnums.values()) {
            logger.info("{}", customer.name());
            customerNameArray.add(customer.name());
        }
    }

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

    public void initSpawning(int spawnCap) {
        resetCustomerSpawn();
        setLevelSpawnCap(spawnCap);
        setSpawnStartTime();
        toggleNowSpawning();
    }

    private void spawnCustomer() {
        int index = rand.nextInt(7);
        customerSpawnController.getEvents().trigger(customerNameArray.get(index));
    }

    public void setGameArea (ForestGameArea newGameArea) {
        gameArea = newGameArea;
        setCustomerSpawnController(gameArea.getCustomerSpawnController());
    }

    public void setCustomerSpawnController(Entity var) {
        customerSpawnController = var;
    }

    public void setSpawnStartTime() {
        spawnStartTime = TimeUtils.millis();
    }

    public void toggleNowSpawning() {
        nowSpawning = !nowSpawning;
    }

    public void setLevelSpawnCap(int cap) {
        levelSpawnCap = cap;
    }

    public int getCurrentCustomersLinedUp() {
        return currentCustomersLinedUp;
    }

    public int getNumbCustomersSpawned() {
        return numbCustomersSpawned;
    }

    public boolean getNowSpawning() {
        return nowSpawning;
    }

    public int getLevelSpawnCap() {
        return levelSpawnCap;
    }

    public ForestGameArea getGameArea() {
        return gameArea;
    }

    public Entity getCustomerSpawnController() {
        return customerSpawnController;
    }

    public Long getSpawnStartTime() {
        return spawnStartTime;
    }

    public void customerJoinedLineUp() {
        currentCustomersLinedUp++;
    }

    public void customerLeftLineUp() {
        currentCustomersLinedUp--;
    }

    public void customerSpawned() {
        numbCustomersSpawned++;
    }

    public void resetCustomerSpawn() {
        numbCustomersSpawned = 0;
    }
}
