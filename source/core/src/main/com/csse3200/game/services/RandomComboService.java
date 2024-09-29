package com.csse3200.game.services;


import com.csse3200.game.components.Component;
import com.csse3200.game.components.maingame.PauseMenuDisplay;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Random;
import java.util.List;

public class RandomComboService extends Component {
    private static final Logger logger = LoggerFactory.getLogger(RandomComboService.class);
    // private List<Upgrade> upgrades;
    private Random random;
    private int randomChoice;
    private EventHandler eventHandler;
    private int total_upgrades = 4; 

    public RandomComboService() {
        this(new EventHandler());
    }
    public RandomComboService(EventHandler eventHandler) {
        super();
        this.eventHandler = eventHandler;
        this.random = new Random();
        randomChoice = random.nextInt(total_upgrades); 
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

        return upgrade;
    }

    // Function call when YES button is pressed
    public void activateUpgrade() {
        eventHandler.trigger(getSelectedUpgrade()); 
        
    }

    public EventHandler getEvents() {
        return eventHandler;
      }
}
    
    

