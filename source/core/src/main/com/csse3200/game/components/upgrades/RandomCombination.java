package com.csse3200.game.components.upgrades;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.ServiceLocator;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class RandomCombination extends Component {
    private List<Upgrade> upgrades;
    private Random random;

    public RandomCombination() {
        super();

        this.upgrades = new ArrayList<>();
        this.upgrades.add(new ExtortionUpgrade(10L));
        this.upgrades.add(new LoanUpgrade());
        RageUpgrade rageUpgrade = new RageUpgrade();
        rageUpgrade.create();
        this.upgrades.add(rageUpgrade);
        SpeedBootsUpgrade speedBootsUpgrade = new SpeedBootsUpgrade();
        speedBootsUpgrade.create();
        this.upgrades.add(speedBootsUpgrade);

        this.random = new Random();
        ServiceLocator.getDayNightService().getEvents().addListener("upgrade", () -> {
            RandomUpgrade();
        });
    }

    public void RandomUpgrade() {
        int randomChoice = random.nextInt(upgrades.size());  // Randomly generate index
        upgrades.get(randomChoice).activate();
    }
}
    
    