package com.csse3200.game.rendering;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.components.ordersystem.MainGameOrderBtnDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.events.EventHandler;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class MainGameOrderBtnDisplayTest {

    @Mock
    private Stage stage;

    @Mock
    private Entity entity;

    @Mock
    private EventHandler eventHandler;

    @InjectMocks
    private MainGameOrderBtnDisplay orderBtnDisplay;

    @Captor
    ArgumentCaptor<String> logCaptor;

    @BeforeEach
    public void setUp() {
        when(entity.getEvents()).thenReturn(eventHandler);

        orderBtnDisplay = new MainGameOrderBtnDisplay();
        orderBtnDisplay.setEntity(entity);
        orderBtnDisplay.setStage(stage);
    }


}