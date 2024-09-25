package com.csse3200.game.components.upgrades;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.ServiceLocator;
import java.util.Random;

public class RandomCombination extends Component {
    private LoanUpgrade loanUpgrade; 
//    private RageUpgrade rageUpgrade;
    private Random random;
    private SpeedBootsUpgrade speedBootsUpgrade;


    public RandomCombination() {
        super(); 
        this.loanUpgrade = new LoanUpgrade();
//        this.rageUpgrade = new RageUpgrade();
        this.speedBootsUpgrade = new SpeedBootsUpgrade(); 
        this.loanUpgrade.create();
//        this.rageUpgrade.create();
        this.speedBootsUpgrade.create();
        this.random = new Random();
        ServiceLocator.getDayNightService().getEvents().addListener("upgrade", () -> {
            RandomUpgrade();});
    }
    public void RandomUpgrade(){
        // loanUpgrade.Loaner();
        // rageUpgrade.toggleRageModeOverlay();
        int randomChoice = random.nextInt(3);  // Randomly generate either 0 or 1
        
        switch (randomChoice) {
            case 0:
                loanUpgrade.activate();  // Call Loaner() if randomChoice is 0
                break;
            case 1:
//                rageUpgrade.activate();  // Call toggleRageModeOverlay() if randomChoice is 1
                break;
            default:
                speedBootsUpgrade.activate();  // Call activate() if randomChoice is any other value
                break;
        }
    }


}
    
    