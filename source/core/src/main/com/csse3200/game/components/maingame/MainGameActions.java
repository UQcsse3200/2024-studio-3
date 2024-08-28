package com.csse3200.game.components.maingame;

import com.csse3200.game.GdxGame;
import com.csse3200.game.components.Component;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * This class listens to events relevant to the Main Game Screen and does something when one of the
 * events is triggered.
 */
public class MainGameActions extends Component {
  private static final Logger logger = LoggerFactory.getLogger(MainGameActions.class);
  private GdxGame game;
  private Entity ui;
  private MainGameOrderTicketDisplay docketDisplayer;


  public MainGameActions(GdxGame game) {
    this.game = game;
   
  }

  @Override
  public void create() {
    entity.getEvents().addListener("exit", this::onExit);
    entity.getEvents().addListener("createOrder", this::onCreateOrder);
    ui = new Entity();
    docketDisplayer = new MainGameOrderTicketDisplay();
    ui.addComponent(docketDisplayer);
    ServiceLocator.getEntityService().register(ui);

  }

  /**
   * Swaps to the Main Menu screen.
   */
  private void onExit() {
    logger.info("Exiting main game screen");
    game.setScreen(GdxGame.ScreenType.MAIN_MENU);
  }

  /**
   * Create Order Ticket
   */
  private void onCreateOrder() {
    logger.info("Creating order");
    docketDisplayer.addActors();
  }

//  /**
//   * Order Done Button
//   */
 private void onOrderDone() {
     ServiceLocator.getEntityService().unregister(ui);
     ui.dispose();
     ui = null;
     logger.info("Order entity disposed");
 }

}
 




 
