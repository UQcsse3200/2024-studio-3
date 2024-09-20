package com.csse3200.game.components.upgrades;

import com.csse3200.game.components.Component;
import com.csse3200.game.services.ServiceLocator;
import java.util.Random;

public class RandomCombination extends Component {
    private LoanUpgrade loanUpgrade; 
    private RageUpgrade rageUpgrade; 
    private Random random;


    public RandomCombination() {
        super(); 
        this.loanUpgrade = new LoanUpgrade();
        this.rageUpgrade = new RageUpgrade(); 
        this.loanUpgrade.create();
        this.rageUpgrade.create();
        this.random = new Random();
        ServiceLocator.getDayNightService().getEvents().addListener("upgrade", () -> {
            RandomUpgrade();});
    }
    public void RandomUpgrade(){
        // loanUpgrade.Loaner();
        // rageUpgrade.toggleRageModeOverlay();
        int randomChoice = random.nextInt(2);  // Randomly generate either 0 or 1
        
        if (randomChoice == 0) {
            loanUpgrade.Loaner();  // Call Loaner() if randomChoice is 0
        } else {
            rageUpgrade.toggleRageModeOverlay();  // Call toggleRageModeOverlay() if randomChoice is 1
        }
    }


}
    