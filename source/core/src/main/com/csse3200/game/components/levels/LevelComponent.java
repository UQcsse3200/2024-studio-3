package com.csse3200.game.components.levels;

import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.components.Component;

public class LevelComponent extends Component {
    private long spawnStartTime = TimeUtils.millis();
    private boolean nowSpawning = false;
    private int levelSpawnCap = 0;
    private int currentCustomersLinedUp = 0;

    @Override
    public void update() {
        if (nowSpawning) {
            ;
        }
    }

    public void initSpawning(int spawnCap) {
        setLevelSpawnCap(spawnCap);
        toggleNowSpawning();
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
}
