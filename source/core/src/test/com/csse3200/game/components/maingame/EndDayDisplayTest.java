package com.csse3200.game.components.maingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.physics.PhysicsService;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.AfterEach;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.*;


import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.services.LevelService;
import com.csse3200.game.services.DayNightService;
import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
@ExtendWith(MockitoExtension.class)
class EndDayDisplayTest {

    @Mock LevelService mockLevelService;
    @Mock DayNightService mockDayNightService;
    @Mock EntityService mockEntityService;

    private Entity entity;
    private EndDayDisplay endDayDisplay;

    @BeforeEach
    void setUp() {
        ServiceLocator.registerLevelService(mockLevelService);
        ServiceLocator.registerDayNightService(mockDayNightService);
        ServiceLocator.registerEntityService(mockEntityService);

        when(mockLevelService.getCurrGold()).thenReturn(100);

        endDayDisplay = ServiceLocator.getEntityService().getEndDayScreen().getComponent(EndDayDisplay.class);
    }

    @Test
    void testShow() {
        endDayDisplay.show();
        assertTrue(endDayDisplay.isVisible);
    }

    @Test
    void testHide() {
        endDayDisplay.show();
        endDayDisplay.hide();
        assertFalse(endDayDisplay.isVisible);
        verify(mockLevelService).togglePlayerFinishedLevel();
        verify(mockDayNightService).getEvents();
    }

    @Test
    void testHandleGoldUpdate() {
        int testGold = 150;
        endDayDisplay.handleGoldUpdate(testGold);

    }

    @Test
    void testUpdateCustomerList() {
        String customerName = "TestCustomer";
        endDayDisplay.updateCustomerList(customerName);
    }

    @Test
    void testToggleVisibility() {
        endDayDisplay.toggleVisibility();
        assertTrue(endDayDisplay.isVisible);
        endDayDisplay.toggleVisibility();
        assertFalse(endDayDisplay.isVisible);
    }
}