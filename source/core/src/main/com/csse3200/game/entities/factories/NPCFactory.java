package com.csse3200.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.ai.tasks.AITaskComponent;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.npc.GhostAnimationController;
import com.csse3200.game.components.TouchAttackComponent;
import com.csse3200.game.components.tasks.PathFollowTask;
import com.csse3200.game.components.tasks.WaitTask;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.configs.*;
import com.csse3200.game.files.FileLoader;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.HitboxComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.physics.components.PhysicsMovementComponent;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.services.ServiceLocator;

/**
 * Factory to create non-playable character (NPC) entities with predefined components.
 *
 * <p>Each NPC entity type should have a creation method that returns a corresponding entity.
 * Predefined entity properties can be loaded from configs stored as json files which are defined in
 * "NPCConfigs".
 *
 * <p>If needed, this factory can be separated into more specific factories for entities with
 * similar characteristics.
 */
public class NPCFactory {
    private static final NPCConfigs configs =
            FileLoader.readClass(NPCConfigs.class, "configs/NPCs.json");
    private static final BaseCustomerConfig customerConfig =
            FileLoader.readClass(BaseCustomerConfig.class, "configs/Customer.json");

    /**
     * Creates a ghost entity.
     *
     * @param target entity to chase
     * @return entity
     */
    public static Entity createGhost(Entity target, Vector2 targetPosition) {
        Entity ghost = createBaseNPC(target, targetPosition);
        BaseEntityConfig config = configs.ghost;

        AnimationRenderComponent animator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService().getAsset("images/ghost.atlas", TextureAtlas.class));
        animator.addAnimation("angry_float", 0.1f, Animation.PlayMode.LOOP);
        animator.addAnimation("float", 0.1f, Animation.PlayMode.LOOP);

        ghost
                .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
                .addComponent(animator)
                .addComponent(new GhostAnimationController());

        ghost.getComponent(AnimationRenderComponent.class).scaleEntity();

        return ghost;
    }

    /**
     * Creates a boss entity.
     *
     * @param targetPosition Place to roam to
     * @return entity

    public static Entity createBoss(Entity target, Vector2 targetPosition) {
        Entity boss = createBaseNPC(target, targetPosition);
        BaseEntityConfig config = configs.ghost;

        AnimationRenderComponent animator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService().getAsset("images/ghost.atlas", TextureAtlas.class));
        animator.addAnimation("angry_float", 0.1f, Animation.PlayMode.LOOP);
        animator.addAnimation("float", 0.1f, Animation.PlayMode.LOOP);

        boss
                .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
                .addComponent(animator)
                .addComponent(new GhostAnimationController());

        boss.getComponent(AnimationRenderComponent.class).scaleEntity();

        return boss;
    } */

    public static Entity createBoss(Vector2 targetPosition) {
        Entity customer = createBaseCharacter(targetPosition);
        GhostKingConfig config = configs.ghostKing;

        AnimationRenderComponent animator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                .getAsset("images/special_NPCs/boss.atlas", TextureAtlas.class));
        animator.addAnimation("float", 0.3f, Animation.PlayMode.LOOP);
        animator.addAnimation("angry_float", 0.3f, Animation.PlayMode.LOOP);

        customer
                .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
                .addComponent(animator)
                .addComponent(new GhostAnimationController());

        //customer.getComponent(AnimationRenderComponent.class).scaleEntity();
        return customer;
    }



    /**
     * Creates a ghost king entity at a specific target position.
     *
     * @param target entity to chase
     * @param targetPosition the target position on the screen where the ghost king should move
     * @return entity
     */
    public static Entity createGhostKing(Entity target, Vector2 targetPosition) {
        Entity ghostKing = createBaseNPC(target, targetPosition);
        GhostKingConfig config = configs.ghostKing;


        AnimationRenderComponent animator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                .getAsset("images/ghostKing.atlas", TextureAtlas.class));
        animator.addAnimation("float", 0.1f, Animation.PlayMode.LOOP);
        animator.addAnimation("angry_float", 0.1f, Animation.PlayMode.LOOP);

        ghostKing
                .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
                .addComponent(animator)
                .addComponent(new GhostAnimationController());

        ghostKing.getComponent(AnimationRenderComponent.class).scaleEntity();
        return ghostKing;
    }



    public static Entity createCustomerPersonal(Vector2 targetPosition) {

        Entity customer = createBaseCustomer(targetPosition);
        GhostKingConfig config = configs.ghostKing;

        AnimationRenderComponent animator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                .getAsset("images/ghostKing.atlas", TextureAtlas.class));
        animator.addAnimation("float", 0.3f, Animation.PlayMode.LOOP);
        animator.addAnimation("angry_float", 0.3f, Animation.PlayMode.LOOP);

        customer
                .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
                .addComponent(animator)
                .addComponent(new GhostAnimationController());

        customer.getComponent(AnimationRenderComponent.class).scaleEntity();
        return customer;
    }


    public static Entity createCustomer(Vector2 targetPosition) {
        Entity customer = createBaseCustomer(targetPosition);
        GhostKingConfig config = configs.ghostKing;

        AnimationRenderComponent animator =
                new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                .getAsset("images/ghostKing.atlas", TextureAtlas.class));
        animator.addAnimation("float", 0.3f, Animation.PlayMode.LOOP);
        animator.addAnimation("angry_float", 0.3f, Animation.PlayMode.LOOP);

        customer
                .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
                .addComponent(animator)
                .addComponent(new GhostAnimationController());

        customer.getComponent(AnimationRenderComponent.class).scaleEntity();
        return customer;
    }

    public static Entity createBaseCustomer(Vector2 targetPosition) {
        AITaskComponent aiComponent =
                new AITaskComponent()
                        .addTask(new PathFollowTask(targetPosition));
        Entity npc =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new PhysicsMovementComponent())
                        .addComponent(new ColliderComponent())
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                        .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 1.5f))
                        .addComponent(aiComponent);
        PhysicsUtils.setScaledCollider(npc, 0.9f, 0.4f);
        return npc;
    }

    public static Entity createBaseCharacter(Vector2 targetPosition) {
        AITaskComponent aiComponent = new AITaskComponent();
                aiComponent
                        .addTask(new PathFollowTask(targetPosition));
                        //.addTask(new PathFollowTask(targetPosition2));
        Entity npc =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new PhysicsMovementComponent())
                        .addComponent(new ColliderComponent())
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                        .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 1.5f))
                        .addComponent(aiComponent);
        PhysicsUtils.setScaledCollider(npc, 0.9f, 0.4f);
        return npc;
    }

    /**
     * Creates a generic NPC to be used as a base entity by more specific NPC creation methods.
     *
     * @return entity
     */
    private static Entity createBaseNPC(Entity target, Vector2 targetPosition) {
        AITaskComponent aiComponent = new AITaskComponent();
                aiComponent
                        .addTask(new PathFollowTask(new Vector2(1f, 4f)))
                        .addTask(new PathFollowTask(new Vector2(7f, 3f)))
                        .addTask(new PathFollowTask(new Vector2(2f, 6f)));

        Entity npc =
                new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new PhysicsMovementComponent())
                        .addComponent(new ColliderComponent())
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                        //.addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 1.5f))
                        .addComponent(aiComponent);

        PhysicsUtils.setScaledCollider(npc, 0.9f, 0.4f);
        return npc;
    }

    private NPCFactory() {
        throw new IllegalStateException("Instantiating static util class");
    }
    public static void createMultipleNPCs(Entity target) {
        // Different target positions for each NPC
        Vector2 targetPosition1 = new Vector2(5, 5);
        Vector2 targetPosition2 = new Vector2(10, 8);
        Vector2 targetPosition3 = new Vector2(3, 2);

        // Create different NPCs
        Entity ghost1 = createGhost(target, targetPosition1);
        Entity ghost2 = createGhost(target, targetPosition2);
        Entity ghostKing = createGhostKing(target, targetPosition3);

        // Add these NPCs to your game world or entity manager here
        // ServiceLocator.getGameWorld().addEntity(ghost1);
        // ServiceLocator.getGameWorld().addEntity(ghost2);
        // ServiceLocator.getGameWorld().addEntity(ghostKing);
    }
}