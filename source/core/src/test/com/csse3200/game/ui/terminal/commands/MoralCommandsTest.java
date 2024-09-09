package com.csse3200.game.ui.terminal.commands;

import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.areas.GameArea;
import com.csse3200.game.components.moral.MoralDecision;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.screens.MoralDecisionDisplay;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class MoralCommandsTest {

    MoralCommands command;
    ArrayList<String> args;

    @BeforeEach
    void beforeEach() {
        ServiceLocator.clear();
        command = new MoralCommands();
        args = new ArrayList<>();
        GameArea gameArea = mock(ForestGameArea.class);
        Entity screen = mock(Entity.class);
        MoralDecision moralDecision = mock(MoralDecision.class);
        screen.addComponent(moralDecision);
        when(ServiceLocator.getEntityService().getMoralScreen()).thenReturn(screen);
        when(ServiceLocator.getEntityService().getMoralScreen().getComponent(MoralDecision.class)).thenReturn(moralDecision);
        ServiceLocator.registerGameArea(gameArea);
    }

//    @Test
//    void testMoralCommands() {
//
//        args.add("addQuestion 'Is it good'");
//        command.action(args);
//
//        verify(ServiceLocator.getEntityService().getMoralScreen().getComponent(MoralDecision.class)).addQuestion("Is it good");
//    }
}
