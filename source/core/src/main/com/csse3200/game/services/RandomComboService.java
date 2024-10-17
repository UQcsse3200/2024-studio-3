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

/**
 * The RandomComboService class is responsible for managing and activating random upgrades
 * within the game. It selects a random upgrade from a predefined set of upgrades and triggers
 * corresponding events when an upgrade is activated. 
 * Upgrades managed by this service include "Extortion", "Loan", and "Speed"..
 * This service utilizes the EventHandler to communicate with other game components,
 */

public class RandomComboService extends Component {
    private static final Logger logger = LoggerFactory.getLogger(RandomComboService.class);
    // private List<Upgrade> upgrades;
    private Random random;
    int randomChoice;
    private EventHandler eventHandler;
    private int total_upgrades = 4;
    private boolean isDancing = false; 

    public RandomComboService() {
        this(new EventHandler());
    }
    public RandomComboService(EventHandler eventHandler) {
        super();
        this.eventHandler = eventHandler;
        this.random = new Random();
        randomChoice = random.nextInt(total_upgrades); 

        eventHandler.addListener("Dancing", ()->{isDancing = true;}); 
        eventHandler.addListener("UnDancing", ()->{isDancing = true;}); 
        
        // ServiceLocator.getDocketService().getEvents().addListener("UnDancing", ()->{
        //     isDancing = false; 
        // });
    }

    /**
     * Retrieves the name of the currently selected upgrade based on the random choice index.
     * @return A String representing the name of the selected upgrade ("Extortion", "Loan", or "Speed").
     */
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
                upgrade = "Speed";
                break;
            case 3:
                upgrade = "Dance party";
                break;
            default:
                upgrade = "";
        }

        return upgrade;
    }

    /**
     * Activates the currently selected upgrade by triggering its corresponding event.
     * This method should be called when the player confirms the activation of the upgrade,
     * by pressing the "YES" button in the game's UI.
     */
    public void activateUpgrade() {
        eventHandler.trigger(getSelectedUpgrade());
    }

    public void deactivateUpgrade() {
        eventHandler.trigger(getSelectedUpgrade() + "off");
    }


    public EventHandler getEvents() {
        return eventHandler;
      }
    
    public boolean dancing(){
        return isDancing; 

    }
}
    
    

