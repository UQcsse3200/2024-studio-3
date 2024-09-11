package com.csse3200.game.services;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.DayNightService;

import static org.junit.Assert.*;

@ExtendWith(GameExtension.class)
public class DayNightServiceTest {
    private GameTime mockgameTime;
    private EventHandler mockEventHandler; 
    private DayNightService mockDayNightService;

    @BeforeEach
    public void setUp() {
        mockEventHandler= mock(EventHandler.class);
        mockgameTime = mock(GameTime.class);
        ServiceLocator.registerTimeSource(mockgameTime);
        mockDayNightService = new DayNightService();
        mockDayNightService.create();
        
    }
    
    
}


