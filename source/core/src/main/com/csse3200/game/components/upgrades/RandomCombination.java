package com.csse3200.game.components.upgrades;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.maingame.PauseMenuDisplay;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class RandomCombination extends Component {
    private static final Logger logger = LoggerFactory.getLogger(RandomCombination.class);
    private List<Upgrade> upgrades;
    private Random random;
    private int randomChoice;

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

        randomChoice = random.nextInt(upgrades.size()); // Randomly generate index
        logger.info(String.valueOf(randomChoice));

//        ServiceLocator.getDayNightService().getEvents().addListener("upgrade", () -> {
//            RandomUpgrade();
//        });
    }

    // Use to get associate image in UpgradesDisplay UI
    public String getSelectedUpgrade() {
        String upgrade;
        switch (randomChoice) {
            case 0:
                upgrade = "Extortion";
                break;
            case 1:
                upgrade = "Loan";
                break;
            case 2:
                upgrade = "Rage";
                break;
            case 3:
                upgrade = "Speed Boot";
                break;
            default:
                upgrade = "";
        }
        return upgrade;
    }

    // Function call when YES button is pressed
    public void activateUpgrade() {
        upgrades.get(randomChoice).activate();
        logger.info("Activate");
    }

    public void RandomUpgrade() {
        int randomChoice = random.nextInt(upgrades.size());  // Randomly generate index
        upgrades.get(randomChoice).activate();
    }
}
    
    