package com.csse3200.game.components.levels;

import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.components.Component;
import com.csse3200.game.services.ServiceLocator;

public class LevelComponent extends Component {
    private long spawnStartTime = 0;
    private boolean nowSpawning = false;
    private int levelSpawnCap = 0;
    private int numbCustomersSpawned = 0;
    private int currentCustomersLinedUp = 0;

    public void create() {
        super.create();
        ServiceLocator.getLevelService().getEvents().addListener("startSpawning", this::initSpawning);
    }

    @Override
    public void update() {
        if (nowSpawning) {
            long elapsedTime = TimeUtils.timeSinceMillis(spawnStartTime);
            long elapsedTimeSecs = elapsedTime / 1000;
            //If more than five seconds have passed, there are more customers to spawn
            //AND if another customer can join the line
            if (elapsedTimeSecs >= 5 && numbCustomersSpawned < levelSpawnCap && currentCustomersLinedUp < 5) {
                setSpawnStartTime();
                customerSpawned();
                ServiceLocator.getLevelService().getEvents().trigger("spawnCustomer");
                if (numbCustomersSpawned == levelSpawnCap) {
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
