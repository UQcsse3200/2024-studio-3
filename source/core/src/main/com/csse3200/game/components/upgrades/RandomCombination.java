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
        this.upgrades.add(new RageUpgrade());
        this.upgrades.add(new SpeedBootsUpgrade());

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
    
    