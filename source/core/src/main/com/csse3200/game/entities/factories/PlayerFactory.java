package com.csse3200.game.entities.factories;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.TooltipsDisplay;
import com.csse3200.game.components.maingame.CheckWinLoseComponent;
import com.csse3200.game.components.ordersystem.MainGameOrderTicketDisplay;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.components.player.PlayerActions;
import com.csse3200.game.components.player.PlayerStatsDisplay;
import com.csse3200.game.components.player.*;
import com.csse3200.game.components.SensorComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.configs.PlayerConfig;
import com.csse3200.game.files.FileLoader;
import com.csse3200.game.input.InputComponent;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.HitboxComponent;
import com.csse3200.game.physics.components.InteractionComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.services.ServiceLocator;

/**
 * Factory to create a player entity.
 *
 * <p>Predefined player properties are loaded from a config stored as a json file and should have
 * the properties stores in 'PlayerConfig'.
 */
public class PlayerFactory {
  private static final PlayerConfig config =
      FileLoader.readClass(PlayerConfig.class, "configs/player.json");

  public static Entity createPlayer(){
    return createPlayer(config);
  }

  /**
   * Create a player entity.
   * @return entity
   */
  public static Entity createPlayer(PlayerConfig config) {
    InputComponent inputComponent =
        ServiceLocator.getInputService().getInputFactory().createForPlayer();

    AnimationRenderComponent animator =
            new AnimationRenderComponent(ServiceLocator.getResourceService().getAsset(
                    "images/player/" + "player.atlas", TextureAtlas.class));

    animator.addAnimation("Character_StandDown", 0.2f);
    animator.addAnimation("Character_StandUp", 0.2f);
    animator.addAnimation("Character_StandLeft", 0.2f);
    animator.addAnimation("Character_StandRight", 0.2f);

    animator.addAnimation("Character_DownLeft", 0.2f, Animation.PlayMode.LOOP);
    animator.addAnimation("Character_UpRight", 0.2f, Animation.PlayMode.LOOP);
    animator.addAnimation("Character_Up", 0.2f, Animation.PlayMode.LOOP);
    animator.addAnimation("Character_Left", 0.2f, Animation.PlayMode.LOOP);
    animator.addAnimation("Character_DownRight", 0.2f, Animation.PlayMode.LOOP);
    animator.addAnimation("Character_Down", 0.2f, Animation.PlayMode.LOOP);
    animator.addAnimation("Character_UpLeft", 0.2f, Animation.PlayMode.LOOP);
    animator.addAnimation("Character_Right", 0.2f, Animation.PlayMode.LOOP);

    Entity player =
        new Entity()
            .addComponent(new PhysicsComponent())
            .addComponent(new ColliderComponent())
            .addComponent(new HitboxComponent().setLayer(PhysicsLayer.PLAYER))
            .addComponent(new PlayerActions())
            .addComponent(new CombatStatsComponent(config.health, config.baseAttack, ServiceLocator.getLevelService().getCurrGold()))
            .addComponent(new InventoryComponent(config.inventorySize))
            .addComponent(new InventoryDisplay())
            .addComponent(inputComponent)
            .addComponent(animator)
            .addComponent(new PlayerAnimationController())
            .addComponent(new TooltipsDisplay())
            .addComponent(new PlayerStatsDisplay())
            .addComponent(new InteractionComponent(PhysicsLayer.INTERACTABLE))
            .addComponent(new CheckWinLoseComponent(60, 55))
            .addComponent(new SensorComponent(PhysicsLayer.INTERACTABLE, 10f));

    player.scaleHeight(1.5f);
    PhysicsUtils.setScaledCollider(player, 0.1f, 0.3f);
    player.getComponent(ColliderComponent.class).setDensity(1.5f);

    ServiceLocator.getPlayerService().getEvents().trigger("playerCreated", player);

    animator.startAnimation("Character_StandUp");
    return player;
  }


  private PlayerFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
