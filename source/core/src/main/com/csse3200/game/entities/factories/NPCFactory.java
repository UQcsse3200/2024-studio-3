package com.csse3200.game.entities.factories;

import com.csse3200.game.components.ScoreSystem.HoverBoxComponent;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.ai.tasks.AITaskComponent;
import com.csse3200.game.components.npc.CustomerComponent;
import com.csse3200.game.components.npc.CustomerManager;
import com.csse3200.game.components.ordersystem.OrderManager;
import com.csse3200.game.components.ordersystem.Recipe;
import com.csse3200.game.components.ordersystem.TicketDetails;
import com.csse3200.game.components.player.TouchPlayerInputComponent;
import com.csse3200.game.components.npc.GhostAnimationController;
import com.csse3200.game.components.npc.SpecialNPCAnimationController;
import com.csse3200.game.components.TouchAttackComponent;
import com.csse3200.game.components.tasks.PathFollowTask;
import com.csse3200.game.components.tasks.TurnTask;
import com.csse3200.game.components.upgrades.UpgradesDisplay;
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

import java.util.Arrays;

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
     * Utility class for creating upgrade NPCs within the game.
     * This method initializes a penguin entity with various components and behaviors
     * to serve as an upgrade vendor. The penguin can be interacted with by the player
     * to display available upgrades.
     */
    public static Entity createUpgradeNPC(Vector2 firstPosition, UpgradesDisplay upgradesDisplay) {
        Entity penguin = createStandard(firstPosition);
        AITaskComponent aiComponent = new AITaskComponent();
        aiComponent.addTask(new PathFollowTask(firstPosition, 15));

        // Animation setup
        AnimationRenderComponent animator = new AnimationRenderComponent(
            ServiceLocator.getResourceService()
                    .getAsset("images/special_NPCs/penguin.atlas", TextureAtlas.class));
        animator.addAnimation("walk", 0.3f, Animation.PlayMode.LOOP);
        animator.addAnimation("turn", 0.3f, Animation.PlayMode.LOOP);
        penguin.addComponent(animator)
            .addComponent(new SpecialNPCAnimationController())
            .addComponent(aiComponent);

        final boolean[] isHoverBox = {false};
        HoverBoxComponent hoverBox = new HoverBoxComponent(new Texture("images/special_NPCs/upgrade_sign.png"));
        hoverBox.setEnabled(false);  // Disable hover box visibility initially
        penguin.addComponent(hoverBox);

        penguin.getEvents().addListener("ready", () -> {
            hoverBox.setEnabled(true);
            isHoverBox[0] = true;
        });

        // Add TouchPlayerInputComponent for click detection
        penguin.addComponent(new TouchPlayerInputComponent());
        final boolean[] isClicked = {false};

        // Add a click event listener for the penguin
        penguin.getEvents().addListener("penguinactivated", ()->{
            if (!isClicked[0] && isHoverBox[0] ) {
                ServiceLocator.getDocketService().getEvents().trigger("paused");
                    hoverBox.setEnabled(false);
                    logger.info("Penguin clicked!");
                    upgradesDisplay.create();
                    upgradesDisplay.toggleVisibility();
                    isClicked[0] = true;
            } else {
                logger.info("Penguin has already been clicked, ignoring.");
            }
        });

        ServiceLocator.getRandomComboService().getEvents().addListener("response", ()->{
            ServiceLocator.getDocketService().getEvents().trigger("unpaused");
            penguin.dispose();});
        return penguin;
    }

    public static Entity createCustomerPersonal(String name, Vector2 targetPosition) {
        Vector2 newTargetPosition = new Vector2(targetPosition.x, targetPosition.y + customerCount);

        CustomerPersonalityConfig config = switch (name) {
                case "Hank" -> configs.Hank;
                case "Lewis" -> configs.Lewis;
                case "Silver" -> configs.Silver;
                case "John" -> configs.John;
                case "Moonki" -> configs.Moonki;
                default -> configs.Default;
        };

        // Retrieve recipe to determine the waiting time
        Recipe recipe = OrderManager.getRecipe(config.preference);
        float waitingTime = (recipe != null) ? recipe.getMakingTime() * 10 : 0; // Default to 0 seconds if no recipe
        logger.info("waitingTime: {}", waitingTime);
        Entity customer = createBaseCustomer(newTargetPosition, waitingTime);

        CustomerComponent customerComponent = new CustomerComponent(config);
        customer.addComponent(customerComponent);


        // gets the preference of the customer
        String preference = customer.getComponent(CustomerComponent.class).getPreference();
        // finding the correct imagePath to display the customer's meal image above them when spawning in
        String imagePath = getMealImagePath(preference);

        AnimationRenderComponent animator = new AnimationRenderComponent(
                        ServiceLocator.getResourceService()
                                        .getAsset(config.texture, TextureAtlas.class));
        animator.addAnimation("walk", 0.3f, Animation.PlayMode.LOOP);

        customer.addComponent(animator)
                .addComponent(new GhostAnimationController());

        customer.getComponent(AnimationRenderComponent.class).scaleEntity();

        // Display the order for the customer
        customer.getEvents().addListener("customerArrived", () -> {
            OrderManager.displayOrder(customer);

            TicketDetails bigTicket = ServiceLocator.getTicketDetails();
            String[] bigTicketInfo = bigTicket.getCurrentBigTicketInfo();
            logger.info(Arrays.toString(bigTicketInfo));

            // Set the order number using bigTicketInfo[0]
            customerComponent.setOrderNumber(bigTicketInfo[0]);

            // Update CustomerManager with the updated order number
            CustomerManager.addCustomer(bigTicketInfo[0], customer);
            CustomerManager.printMessage();
        });

        logger.debug("Created customer {} with initial position: {}", name, customer.getPosition());

        if (customer.getComponent(HoverBoxComponent.class) == null) {
                customer.addComponent(new HoverBoxComponent(new Texture(imagePath)));
        }
        customerCount++;
        orderID++;

        return customer;
    }

    private static String getMealImagePath(String preference) {
        return switch (preference) {
            case "acaiBowl" -> "images/meals/acai_bowl.png";
            case "salad" -> "images/meals/salad.png";
            case "fruitSalad" -> "images/meals/fruit_salad.png";
            case "steakMeal" -> "images/meals/steak_meal.png";
            case "bananaSplit" -> "images/meals/banana_split.png";
            default -> {
                logger.error("No image found for preference: {}", preference);
                yield "images/meals/incorrect_meal.png"; // Provide a default image
            }
        };
    }

    public static Entity createBasicCustomer(String name, Vector2 targetPosition) {
        Vector2 newTargetPosition = new Vector2(targetPosition.x, targetPosition.y + customerCount);

        Entity customer = createBaseCustomer(newTargetPosition, 15);

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

    public static Entity createBaseCustomer(Vector2 targetPosition, float waitingTime) {
        AITaskComponent aiComponent = new AITaskComponent();
        aiComponent
                .addTask(new PathFollowTask(targetPosition, waitingTime));
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
                        .addTask(new PathFollowTask(targetPosition, 15)) // Default countdown
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

    public static Entity createStandard(Vector2 targetPosition) {
        AITaskComponent aiComponent = new AITaskComponent();
        aiComponent
                        .addTask(new PathFollowTask(targetPosition, 15)); // Default countdown
        Entity npc = new Entity()
                        .addComponent(new PhysicsComponent())
                        .addComponent(new PhysicsMovementComponent())
                        .addComponent(new ColliderComponent())
                        .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
                        .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 1.5f));
        PhysicsUtils.setScaledCollider(npc, 0.9f, 0.4f);
        npc.getComponent(PhysicsComponent.class).getBody().setUserData("Customer");
        return npc;
    }

    public static void decreaseCustomerCount() {
        customerCount --;
    }

    public static void reset() {
        customerCount = 0;
    }

    private NPCFactory() {
        throw new IllegalStateException("Instantiating static util class");
    }
}
