package com.csse3200.game.entities.factories;

import com.csse3200.game.components.ScoreSystem.HoverBoxComponent;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.ai.tasks.AITaskComponent;
import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.components.npc.CustomerManager;
import com.csse3200.game.components.ordersystem.OrderManager;
import com.csse3200.game.components.npc.GhostAnimationController;
import com.csse3200.game.components.npc.SpecialNPCAnimationController;
import com.csse3200.game.components.TouchAttackComponent;
import com.csse3200.game.components.tasks.PathFollowTask;
import com.csse3200.game.components.tasks.TurnTask;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Factory to create non-playable character (NPC) entities with predefined
 * components.
 */
public class NPCFactory {

        private static final NPCConfigs configs = FileLoader.readClass(NPCConfigs.class, "configs/NPCs.json");

        private static final Logger logger = LoggerFactory.getLogger(NPCFactory.class);
        private static int customerCount = 0;
        private static int orderID = 1;

        /**
         * Creates a ghost entity.
         *
         * @param target entity to chase
         * @return entity
         */

        /**
         * Creates a boss entity.
         *
         * @param targetPosition Place to roam to
         * @return entity
         */
        public static Entity createBoss(Vector2 targetPosition) {
                Entity boss = createBaseCharacter(targetPosition);

                AnimationRenderComponent animator = new AnimationRenderComponent(
                                ServiceLocator.getResourceService()
                                                .getAsset("images/special_NPCs/boss.atlas", TextureAtlas.class));
                animator.addAnimation("walk", 0.3f, Animation.PlayMode.LOOP);
                animator.addAnimation("turn", 0.3f, Animation.PlayMode.LOOP);

                boss
                                .addComponent(animator)
                                .addComponent(new SpecialNPCAnimationController());

                return boss;
        }

        /**
         * Creates a ghost king entity at a specific target position.
         *
         * @param target         entity to chase
         * @param targetPosition the target position on the screen where the ghost king
         *                       should move
         * @return entity
         */
        public static Entity createGhostKing(Entity target, Vector2 targetPosition) {
                Entity ghostKing = createBaseNPC(target, targetPosition);
                GhostKingConfig config = configs.ghostKing;

                AnimationRenderComponent animator = new AnimationRenderComponent(
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

        public static Entity createCustomerPersonal(String name, Vector2 targetPosition) {
                Vector2 newTargetPosition = new Vector2(targetPosition.x, targetPosition.y + customerCount);

                Entity customer = createBaseCustomer(newTargetPosition);

                CustomerPersonalityConfig config = switch (name) {
                        case "Hank" -> configs.Hank;
                        case "Lewis" -> configs.Lewis;
                        case "Silver" -> configs.Silver;
                        case "John" -> configs.John;
                        case "Moonki" -> configs.Moonki;
                        default -> configs.Default;
                };
                // orderID is to link a specific customer to a specific order ticket
                String orderNumber = String.valueOf(orderID);
                logger.info("Order number: " + orderNumber);
                CustomerComponent customerComponent = new CustomerComponent(config);
                customerComponent.setOrderNumber(orderNumber);
                customer.addComponent(customerComponent);

                CustomerManager.addCustomer(orderNumber, customer);

                // gets the preference of the customer
                String preference = customer.getComponent(CustomerComponent.class).getPreference();
                // finding the correct imagePath to display the customer's meal image above them when spawning in
                String imagePath = getMealImagePath(preference);

                AnimationRenderComponent animator = new AnimationRenderComponent(
                                ServiceLocator.getResourceService()
                                                .getAsset(config.texture, TextureAtlas.class));
                animator.addAnimation("walk", 0.3f, Animation.PlayMode.LOOP);

                customer
                                .addComponent(animator)
                                .addComponent(new GhostAnimationController());

                customer.getComponent(AnimationRenderComponent.class).scaleEntity();

                // Display the order for the customer
                OrderManager.displayOrder(customer);

                logger.debug("Created customer " + name + " with initial position: " + customer.getPosition());

                if (customer.getComponent(HoverBoxComponent.class) == null) {
                        customer.addComponent(new HoverBoxComponent(new Texture(imagePath)));
                }
                customerCount++;
                orderID++;

                return customer;
        }

        private static String getMealImagePath(String preference) {
                switch (preference) {
                    case "acaiBowl":
                        return "images/meals/acai_bowl.png";
                    case "salad":
                        return "images/meals/salad.png";
                    case "fruitSalad":
                        return "images/meals/fruit_salad.png";
                    case "steakMeal":
                        return "images/meals/steak_meal.png";
                    case "bananaSplit":
                        return "images/meals/banana_split.png";
                    default:
                        logger.error("No image found for preference: " + preference);
                        return "images/meals/incorrect_meal.png"; // Provide a default image
                }
        }

        public static Entity createBasicCustomer(String name, Vector2 targetPosition) {
                Vector2 newTargetPosition = new Vector2(targetPosition.x, targetPosition.y + customerCount);

                Entity customer = createBaseCustomer(newTargetPosition);

                BaseCustomerConfig config = switch (name) {
                        case "Basic Chicken" -> configs.Basic_Chicken;
                        case "Basic Sheep" -> configs.Basic_Sheep;
                        default -> configs.Default;
                };

                // Ensure CustomerComponent is added
                customer.addComponent(new CustomerComponent(config));

                AnimationRenderComponent animator = new AnimationRenderComponent(
                                ServiceLocator.getResourceService()
                                                .getAsset("images/ghostKing.atlas", TextureAtlas.class));
                animator.addAnimation("float", 0.3f, Animation.PlayMode.LOOP);
                animator.addAnimation("angry_float", 0.3f, Animation.PlayMode.LOOP);

                customer
                                .addComponent(animator)
                                .addComponent(new GhostAnimationController());

                customer.getComponent(AnimationRenderComponent.class).scaleEntity();

                // Display the order for the customer
                OrderManager.displayOrder(customer);
                customerCount++;

                return customer;
        }

        public static Entity createBaseCustomer(Vector2 targetPosition) {
                AITaskComponent aiComponent = new AITaskComponent();
                aiComponent
                        .addTask(new PathFollowTask(targetPosition, 30));
                Entity npc = new Entity()
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
                                .addTask(new PathFollowTask(targetPosition, 30)) // Default countdown
                                .addTask(new TurnTask(10, 0.01f, 10f));
                Entity npc = new Entity()
                                .addComponent(new PhysicsComponent())
                                .addComponent(new PhysicsMovementComponent())
                                .addComponent(new ColliderComponent())
                                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                                .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 1.5f))
                                .addComponent(aiComponent);
                PhysicsUtils.setScaledCollider(npc, 0.9f, 0.4f);
                npc.getComponent(PhysicsComponent.class).getBody().setUserData("Customer");
                return npc;
        }

        /**
         * Creates a generic NPC to be used as a base entity by more specific NPC
         * creation methods.
         *
         * @return entity
         */
        private static Entity createBaseNPC(Entity target, Vector2 targetPosition) {
                AITaskComponent aiComponent = new AITaskComponent();
                aiComponent
                                .addTask(new PathFollowTask(targetPosition, 30)); // Default countdown

                Entity npc = new Entity()
                                .addComponent(new PhysicsComponent())
                                .addComponent(new PhysicsMovementComponent())
                                .addComponent(new ColliderComponent())
                                .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                                .addComponent(aiComponent);

                PhysicsUtils.setScaledCollider(npc, 0.9f, 0.4f);
                return npc;
        }

        public static void decreaseCustomerCount() {
                customerCount --;
        }

        private NPCFactory() {
                throw new IllegalStateException("Instantiating static util class");
        }

        /*
         * public static void createMultipleNPCs(Entity target) {
         * // Different target positions for each NPC
         * Vector2 targetPosition1 = new Vector2(5, 5);
         * Vector2 targetPosition2 = new Vector2(10, 8);
         * Vector2 targetPosition3 = new Vector2(3, 2);
         * 
         * // Create different NPCs
         * Entity ghost1 = createGhost(target, targetPosition1);
         * Entity ghost2 = createGhost(target, targetPosition2);
         * Entity ghostKing = createGhostKing(target, targetPosition3);
         * 
         * // Assuming some method to add NPCs to the game
         * ForestGameArea.getInstance().addEntity(ghost1);
         * ForestGameArea.getInstance().addEntity(ghost2);
         * ForestGameArea.getInstance().addEntity(ghostKing);
         * }
         * 
         */
}
