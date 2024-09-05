package com.csse3200.game.services;

import com.csse3200.game.extensions.GameExtension;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class LevelServiceTest {
    @Spy LevelService levelServiceSpy;

    /*@BeforeAll
    void initSetUp() {
        ServiceLocator.registerLevelService(levelServiceSpy);
    }*/

    @Test
    void shouldInitialiseProperlyWhenCreated() {
        LevelService levelService = new LevelService();
        assertNotNull(levelService.getCurrLevel());
        assertNotNull(levelService.getEvents());
        levelServiceSpy.getEvents().trigger("startLevel");
        levelServiceSpy.getEvents().trigger("createCustomer");
        verify(levelServiceSpy).getEvents().trigger("startLevel");
        verify(levelServiceSpy).getEvents().trigger("createCustomer");
    }
}
