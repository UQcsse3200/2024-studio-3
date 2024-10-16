package com.csse3200.game.components.upgrades;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;

/**
 * Manages the Loan Upgrade component, allowing players to take a loan
 * to receive additional gold under certain conditions.
 */
public class LoanUpgrade extends Component implements Upgrade {
    private CombatStatsComponent combatStatsComponent;


    public LoanUpgrade(){
        super();
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
        });
        ServiceLocator.getRandomComboService().getEvents().addListener("Loan", this::activate); 
    }

    /**
     * Activates the loan upgrade, providing the player with additional gold if they have 
     * at least 20 gold
     */
    public void activate() { 
        // https://pixabay.com/sound-effects/cha-ching-7053/
        Sound moneySound = Gdx.audio.newSound(Gdx.files.internal("sounds/loan.mp3"));
        long moneySoundId = moneySound.play();
        moneySound.setVolume(moneySoundId, 0.2f);
        combatStatsComponent.addGold(100);
    }

    /**
     * Deactivates the loan upgrade
     */
    public void deactivate() {}

    @Override
    public void update() {

    }
}
