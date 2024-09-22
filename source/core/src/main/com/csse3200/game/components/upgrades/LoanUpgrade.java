package com.csse3200.game.components.upgrades;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;

public class LoanUpgrade extends Component {
    private CombatStatsComponent combatStatsComponent;

    public LoanUpgrade(){
        super();
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            this.combatStatsComponent = player.getComponent(CombatStatsComponent.class);
        });
    }

    @Override
    public void update() {
        // Check if the 'L' key is pressed in each frame
        if (Gdx.input.isKeyJustPressed(Input.Keys.L)) {
            Loaner();  // Add 100 gold when 'L' is pressed
        }
    }

    public void Loaner(){
        combatStatsComponent.addGold(100);
    }

}
