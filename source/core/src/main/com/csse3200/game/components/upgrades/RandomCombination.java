package com.csse3200.game.components.upgrades;

import com.csse3200.game.components.Component;
import com.csse3200.game.components.maingame.PauseMenuDisplay;
import com.csse3200.game.events.EventHandler;
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
    private EventHandler eventHandler;

    public RandomCombination() {
        this(new EventHandler());
    }
    public RandomCombination(EventHandler eventHandler) {
        super();
        // this.upgrades = new ArrayList<>();
        // this.upgrades.add(new ExtortionUpgrade(10L));
        // this.upgrades.add(new LoanUpgrade());
        // RageUpgrade rageUpgrade = new RageUpgrade();
        // rageUpgrade.create();
        // this.upgrades.add(rageUpgrade);
        // SpeedBootsUpgrade speedBootsUpgrade = new SpeedBootsUpgrade();
        // speedBootsUpgrade.create();
        // this.upgrades.add(speedBootsUpgrade);

        this.eventHandler = eventHandler;
        this.random = new Random();
        

        // randomChoice = random.nextInt(upgrades.size()); // Randomly generate index
        randomChoice = random.nextInt(4); // Randomly generate index
        System.out.println("TTTTTTTTTTTTTTTTTTTTTT" + randomChoice);

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
                upgrade = "Speed";
                break;
            default:
                upgrade = "";
        }
        System.out.println("NNNNNNNNNNNNNNNNNNN" + randomChoice);
        return upgrade;
    }

    // Function call when YES button is pressed
    public void activateUpgrade() {
        // upgrades.get(randomChoice).activate();
        // System.out.println("OOOOOOOOOOO" + randomChoice);
        // logger.info("Activate");
        // entity.getEvents().trigger("speed");
        eventHandler.trigger("speed"); 
    }

    public void RandomUpgrade() {
        int randomChoice = random.nextInt(upgrades.size());  // Randomly generate index
        upgrades.get(randomChoice).activate();
    }

    
}
    
    