//package com.csse3200.game.components.moral;
//import com.csse3200.game.areas.GameArea;
//import com.csse3200.game.areas.terrain.TerrainFactory;
//import com.csse3200.game.components.moral.MoralDecisionDisplay;
//import com.csse3200.game.entities.Entity;
//import com.csse3200.game.entities.EntityService;
//import com.csse3200.game.screens.MainGameScreen;
//import com.csse3200.game.services.ServiceLocator;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import static org.mockito.Mockito.*;
//import static org.junit.jupiter.api.Assertions.*;
//
//@ExtendWith(MockitoExtension.class)
//public class MoralDecisionDisplayTest {
//
//    @Mock
//    private MoralDecisionDisplay moralDecisionDisplay;
//
//    @Mock
//    private Entity entity;
//
//    @Mock
//    private MainGameScreen mockGame;
//
//    private GameArea gameArea;
//
////    @Mock
////    private Stage mockStage;
//    @BeforeEach
//    void setUp() {
//        TerrainFactory factory = mock(TerrainFactory.class);
//
//        GameArea gameArea =
//                new GameArea() {
//                    @Override
//                    public void create() {}
//                };
//
//        ServiceLocator.registerEntityService(new EntityService());
//        // Initialize MoralDecisionDisplay with the mocked game
//        MoralDecisionDisplay testDisplay = mock(MoralDecisionDisplay.class);
//        entity = mock(Entity.class);
//        entity.addComponent(testDisplay);
//        //moralDecisionDisplay.setStage(mockStage); // Set the mocked stage
////        moralDecisionDisplay.create(); // Initialize the component
//        ServiceLocator.getEntityService().registerMoral(entity);
//    }
//
//    @Test
//    void testToggleVisibility_Hide() {
//        // Initially, the display should not be visible
//        assertFalse(moralDecisionDisplay.getVisible(), "Initially, the display should not be visible.");
//
//
//    }
//
//}


package com.csse3200.game.components.moral;

import com.csse3200.game.areas.GameArea;
import com.csse3200.game.areas.terrain.TerrainFactory;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.screens.MainGameScreen;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class MoralDayTwoTest {

    @Mock
    private MoralDayTwo moralDayTwo;

    @Mock
    private Entity entity;

    @BeforeEach
    void setUp() {
        TerrainFactory factory = mock(TerrainFactory.class);

        GameArea gameArea =
                new GameArea() {
                    @Override
                    public void create() {}
                };

        ServiceLocator.registerEntityService(new EntityService());
        // Initialize MoralDecisionDisplay with the mocked game
        MoralDayTwo testDisplay = mock(MoralDayTwo.class);
        entity = mock(Entity.class);
        entity.addComponent(testDisplay);
        //moralDecisionDisplay.setStage(mockStage); // Set the mocked stage
//        moralDecisionDisplay.create(); // Initialize the component
        ServiceLocator.getEntityService().registerMoral(entity);
    }

    @Test
    void testToggleVisibility_Hide() {
        // Initially, the display should not be visible
        assertFalse(moralDayTwo.getVisible(), "Initially, the display should not be visible.");


    }

}
