package com.csse3200.game.components.upgrades;


public class ExtortionUpgradeTest {
    /**
    private CombatStatsComponent combatStatsComponent;
    private ExtortionUpgrade extortionUpgrade;
    private MainGameOrderTicketDisplay mainGameOrderTicketDisplay;


    @BeforeEach
    void beforeEach() {
        GameTime gameTime = mock(GameTime.class);
        when(gameTime.getTime()).thenReturn(0L);
        ServiceLocator.registerTimeSource(gameTime);

        PlayerService playerService = new PlayerService();
        ServiceLocator.registerPlayerService(playerService);

        //To my knowledge you can't do when() on this, which is causing a lot of problems
        //when(ServiceLocator.getPlayerService()).thenReturn(new PlayerService());

        //extortionUpgrade = mock(new ExtortionUpgrade(10, gameTime));
        extortionUpgrade = mock(ExtortionUpgrade.class);
        mainGameOrderTicketDisplay = new MainGameOrderTicketDisplay();
        when(extortionUpgrade.getTickets()).thenReturn(mainGameOrderTicketDisplay);

        //TODO mock CombatStatsService
    }

    @Test
    void shouldDoubleGold() {
        int goldBefore = mainGameOrderTicketDisplay.getRecipeValue();
        int goldAfter = mainGameOrderTicketDisplay.getRecipeValue();
        assertEquals(goldBefore, goldAfter);
    }
    */
}
