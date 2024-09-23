package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.components.player.PlayerStatsDisplay;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;

/**
 * Displays order tickets on the main game screen. This class manages the
 * creation, positioning, and updating of order tickets, as well as shifting
 * them left or right in response to user actions. It also handles the
 * countdown timers for each order and disposes of them when the timer expires.
 */
public class MainGameOrderTicketDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MainGameOrderTicketDisplay.class);
    private static final float Z_INDEX = 3f;
    private static final float viewPortHeightMultiplier = 7f / 9f;
    private static final float viewPortWidthMultiplier = 3f / 32f;
    private static final float viewportHeight =
            ServiceLocator.getRenderService().getStage().getViewport().getCamera().viewportHeight;
    private static final float viewportWidth =
            ServiceLocator.getRenderService().getStage().getViewport().getCamera().viewportWidth;
    private static final int distance = 20;
    private static ArrayList<Table> tableArrayList;
    private static ArrayList<Long> startTimeArrayList;
    private static ArrayList<Docket> backgroundArrayList;
    private static ArrayList<Label> countdownLabelArrayList;
    private static ArrayList<Long> recipeTimeArrayList;
    private static int orderNumb = 0;
    private static final long DEFAULT_TIMER = 10000;
    private static int recipeValue;
    private Recipe recipe;
    public InventoryComponent inventoryComponent;
    private Image mealImage;
    private DocketMealDisplay mealDisplay;
    private static final float DISTANCE_MULTIPLIER = 0.015f;
    public CombatStatsComponent combatStatsComponent;
    public int goldMultiplier = 1;

    /**
     * Constructs an MainGameOrderTicketDisplay instance
     */
    public MainGameOrderTicketDisplay() {
        tableArrayList = new ArrayList<>();
        startTimeArrayList = new ArrayList<>();
        backgroundArrayList = new ArrayList<>();
        countdownLabelArrayList = new ArrayList<>();
        recipeTimeArrayList = new ArrayList<>();
        mealDisplay = new DocketMealDisplay();
        setRecipeValue(2);
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            combatStatsComponent = player.getComponent(CombatStatsComponent.class);
        });
    }

    /**
     * Sets recipe data
     *
     * @param recipeName the name of the recipe
     */
    public void setRecipe(String recipeName) {
        this.recipe = new Recipe(recipeName);
    }


    /**
     * Gets recipe data
     *
     * @return recipe data
     */
    public Recipe getRecipe() {
        return this.recipe;
    }
    public String getCurrentRecipeName() {
        return recipe != null ? recipe.getName() : null;
    }


    /**
     * Initialises the display and sets up event listeners for creating and shifting orders.
     */
    @Override
    public void create() {
        super.create();
        entity.getEvents().addListener("createOrder", this::addActors);
        ServiceLocator.getDocketService().getEvents().addListener("shiftDocketsLeft", this::shiftDocketsLeft);
        ServiceLocator.getDocketService().getEvents().addListener("shiftDocketsRight", this::shiftDocketsRight);
        // logger.info("Listeners added for shiftDocketsLeft and shiftDocketsRight events");
        ServiceLocator.getDocketService().getEvents().addListener("removeBigTicket", this::removeBigTicket);

        //From team 2, i used your dispose method here when listening for a new day, so current dockets get removed
        //when the end of day occurs
        ServiceLocator.getDocketService().getEvents().addListener("Dispose", this::dispose);
    }

    /**
     * Adds a new order ticket to the display and sets its initial position and size.
     * Initialises the background, labels, and countdown timer for the order.
     */
    public void addActors() {
        Table table = new Table();
        long startTime = TimeUtils.millis();

        startTimeArrayList.add(startTime);
        tableArrayList.add(table);

        table.setFillParent(false);
        table.setSize(viewportWidth * 3f / 32f, 5f / 27f * viewportHeight); // DEFAULT_HEIGHT
        float xVal = cntXval(225f, tableArrayList.size());
        float yVal = viewportHeight * viewPortHeightMultiplier;
        table.setPosition(xVal, yVal);
        Docket background = new Docket(getTimer());
        backgroundArrayList.add(background);
        table.setBackground(background.getImage().getDrawable());

        String orderNumStr = "Order" + " " + ++orderNumb;
        Label orderNumbLabel = new Label(orderNumStr, skin);
        table.add(orderNumbLabel).padLeft(10f).row();

        Label recipeNameLabel = new Label(getRecipe().getName(), skin);
        table.add(recipeNameLabel).padLeft(10f).row();

        mealImage = mealDisplay.getMealImage(getRecipe().getName());
        table.add(mealImage).row();

        recipeTimeArrayList.add(getTimer());
        Label countdownLabel = new Label("Timer: " + getTimer(), skin);
        countdownLabelArrayList.add(countdownLabel);
        table.add(countdownLabel).padLeft(10f).row();

        stage.addActor(table);
        updateDocketSizes();

        table.setZIndex((int)getZIndex());
    }

    /**
     * Calculates the x-position for an order ticket based on its index.
     *
     * @param startPoint the starting point of the first docket.
     * @param instanceCnt the index of the order ticket.
     * @return the x-position for the order ticket.
     */
    private float cntXval(float startPoint, int instanceCnt) {
        return startPoint + (instanceCnt - 1) * ((viewportWidth * DISTANCE_MULTIPLIER) + viewportWidth * 3f / 32f);
    }

    /**
     * Reorders the dockets after one is removed or shifted.
     *
     * @param index the index of the docket that was removed or shifted.
     */
    public static void reorderDockets(int index) {
        for (int i = index + 1; i < tableArrayList.size(); i++) {
            Table currTable = tableArrayList.get(i);
            currTable.setX(currTable.getX() - (distance + viewportWidth * 3f / 32f));
        }
    }

    /**
     * * Shifts the order tickets to the left by moving the first ticket to the end of the list.
     * Updates the positions and sizes of all tickets.
     * */
    public void shiftDocketsLeft() {
        if (tableArrayList.isEmpty() || backgroundArrayList.isEmpty()) {
            logger.warn("No dockets to shift left");
            return;
        }
        Table firstTable = tableArrayList.remove(0);
        tableArrayList.add(firstTable);

        Docket firstDocket = backgroundArrayList.remove(0);
        backgroundArrayList.add(firstDocket);

        Long firstStartTime = startTimeArrayList.remove(0);
        startTimeArrayList.add(firstStartTime);

        Label firstCountdownLabel = countdownLabelArrayList.remove(0);
        countdownLabelArrayList.add(firstCountdownLabel);

        long firstRecipeTime = recipeTimeArrayList.remove(0);
        recipeTimeArrayList.add(firstRecipeTime);

        updateDocketPositions();
        updateDocketSizes();

        logger.info("Docket positions updated after left shift");
    }


    /**
     * Removes and disposes of a docket from the stage and its associated resources.
     *
     * @param docket the docket to be disposed of.
     * @param table  the table representing the docket.
     * @param i  the index of the docket.
     */
    public void stageDispose(Docket docket, Table table, int i) {
        table.setBackground((Drawable) null);
        table.clear();
        table.remove();
        ServiceLocator.getDocketService().getEvents().trigger("removeOrder", i);
        docket.dispose();
        tableArrayList.remove(i);
        backgroundArrayList.remove(i);
        startTimeArrayList.remove(i);
        countdownLabelArrayList.remove(i);
        recipeTimeArrayList.remove(i);
        combatStatsComponent.addGold(getRecipeValue() * goldMultiplier);
    }

    /**
     * Sets the recipe value
     * @param value the price of the recipe
     */
    public void setRecipeValue(int value) {
        recipeValue = value;
    }

    /**
     * Gets the recipe value
     * @return the recipe price
     */
    public static int getRecipeValue() {
        return recipeValue;
    }

    /**
     * Shifts the order tickets to the right by moving the last ticket to the beginning of the list.
     * Updates the positions and sizes of all tickets.
     */
    public void shiftDocketsRight() {
        if (tableArrayList.isEmpty() || backgroundArrayList.isEmpty()) {
            logger.warn("No dockets to shift right");
            return;
        }
        Table lastTable = tableArrayList.remove(tableArrayList.size() - 1);
        tableArrayList.add(0, lastTable);

        Docket lastDocket = backgroundArrayList.remove(backgroundArrayList.size() - 1);
        backgroundArrayList.add(0, lastDocket);

        Long lastStartTime = startTimeArrayList.remove(startTimeArrayList.size() - 1);
        startTimeArrayList.add(0, lastStartTime);

        Label lastCountdownLabel = countdownLabelArrayList.remove(countdownLabelArrayList.size() - 1);
        countdownLabelArrayList.add(0, lastCountdownLabel);

        long recipeTime = recipeTimeArrayList.remove(recipeTimeArrayList.size() - 1);
        recipeTimeArrayList.add(0, recipeTime);

        updateDocketPositions();
        updateDocketSizes();

        logger.info("Docket positions updated after right shift");
    }

    /**
     * Updates the positions of all dockets based on their current index in the list.
     */
    private void updateDocketPositions() {
        for (int i = 0; i < tableArrayList.size(); i++) {
            Table table = tableArrayList.get(i);
            float xVal = cntXval(225f, i + 1);

            table.setPosition(xVal, table.getY());
        }
    }

    /**
     * Updates the sizes of all dockets. The last docket in the list is enlarged, while others remain the same size.
     */
    public void updateDocketSizes() {
        float viewportWidth = Gdx.graphics.getWidth();
        float viewportHeight = Gdx.graphics.getHeight();
        float scaleFactor = getScalingFactor(viewportWidth, viewportHeight);

        float docketWidthRatio = 0.08f;
        float docketHeightRatio = 0.25f;

        float normalDocketWidth = 1280f * docketWidthRatio * scaleFactor;
        float normalDocketHeight = 800f * docketHeightRatio * scaleFactor;

        float enlargedDocketWidth = normalDocketWidth * 1.7f;
        float enlargedDocketHeight = normalDocketHeight * 1.7f;

        float dynamicDistanceLeft = 0.175f; // 225f Percentage of the width to place first docket
        float leftHandSideDistance = viewportWidth * dynamicDistanceLeft;
        float dynamicDistanceRight = 0.09f; //115f Percentage of the width to place the enlarged/tail docket
        float rightHandSideDistance = viewportWidth * dynamicDistanceRight;


        float xPosEnlarged = viewportWidth - enlargedDocketWidth - rightHandSideDistance;
        float yPosEnlarged = (viewportHeight * 0.938f) - (enlargedDocketHeight - 15);

        float yPosNormal = (viewportHeight * 0.938f) - (normalDocketHeight - 15);

        for (int i = 0; i < tableArrayList.size(); i++) {
            Table table = tableArrayList.get(i);

            Array<Cell> cells = table.getCells();
            if (i == tableArrayList.size() - 1) { // Tail docket
                table.setSize(enlargedDocketWidth, enlargedDocketHeight);
                // Fixed position for enlarged docket
                table.setPosition(xPosEnlarged, yPosEnlarged);

                table.setZIndex(10);
                // Apply enlarged font size
                for (int j = 0; j < cells.size; j++) {
                    if (cells.get(j).getActor() instanceof Label) {
                        Label label = (Label) cells.get(j).getActor();
                        label.setFontScale(viewportWidth / 1920f);
                        if (label.getText().toString().contains("Timer")) {
                            cells.get(j).padBottom(5f);
                        }
                    } else if (cells.get(j).getActor() instanceof Image) {
                        Image image = (Image) cells.get(j).getActor();
                        cells.get(j).padBottom(10f);
                        image.setScaling(Scaling.fit);
                    }
                }
            } else { // Non-enlarged dockets
                table.setSize(normalDocketWidth, normalDocketHeight);
                float xVal = cntXval(leftHandSideDistance,i + 1);
                table.setPosition(xVal, yPosNormal);
                table.setZIndex(5);
                for (int j = 0; j < cells.size; j++) {
                    if (cells.get(j).getActor() instanceof Label) {
                        Label label = (Label) cells.get(j).getActor();
                        label.setFontScale(0.7f * (viewportWidth / 1920f));
                        if (label.getText().toString().contains("Timer")) {
                            cells.get(j).padBottom(0f);
                        }
                    } else if (cells.get(j).getActor() instanceof Image) {
                        Image image = (Image) cells.get(j).getActor();
                        cells.get(j).padBottom(5f);
                        image.setScaling(Scaling.fit);
                    }
                }
            }
        }
    }

    /**
     * Gets the scaling factor in respect to current width and height of screen.
     *
     * @param currentWidth current width of the game window screen.
     * @param currentHeight current height of the game window screen.
     * @return
     */
    public static float getScalingFactor(float currentWidth, float currentHeight) {
        float widthFactor = currentWidth / 1920;
        float heightFactor = currentHeight / 1080;
        return Math.min(widthFactor, heightFactor);
    }

    /**
     * Updates the state of all order tickets, including countdown timers and their positions on the screen.
     * Removes any order tickets whose timers have expired.
     */
    @Override
    public void update() {
        // No additional update logic needed here, shifting is handled by the OrderActions class
        for (int i = 0; i < tableArrayList.size(); i++) {
            Docket currBackground = backgroundArrayList.get(i);
            Table currTable = tableArrayList.get(i);
            Label currCountdown = countdownLabelArrayList.get(i);
            long elapsedTime = TimeUtils.timeSinceMillis(startTimeArrayList.get(i));
            long remainingTime = recipeTimeArrayList.get(i) - elapsedTime;
            if (remainingTime > 0) {
                currCountdown.setText("Timer: " + (remainingTime / 1000));
                currBackground.updateDocketTexture((double) remainingTime / 1000);
                currTable.setBackground(currBackground.getImage().getDrawable());
            } else {
                // if order is successful
                stageDispose(currBackground, currTable, i);

            }
        }
        if (!tableArrayList.isEmpty()) {
            Table lastTable = tableArrayList.get(tableArrayList.size() - 1);
            updateBigTicketInfo(lastTable);
        }
        updateDocketPositions();
        updateDocketSizes();
    }

    /**
     * Removes the current big ticket from the UI, as well as its values in the array
     */
    public void removeBigTicket(){
        int index = tableArrayList.size() - 1;
        Docket currBackground = backgroundArrayList.get(index);
        Table currTable = tableArrayList.get(index);

        stageDispose(currBackground, currTable, index);
    }

    /**
     * Updates the display for all dockets. This includes updating their positions and sizes.
     */
    public void updateDocketDisplay() {
        // Implement logic to update the display
        updateDocketPositions();
    }

    /**
     * Updates the details of the current info from the big ticket. It gets the order, timer and meal as string values
     * and calls another function to save these values elsewhere.
     * @param bigTicket The current ticket being prioritised by the user
     *
     */
    private void updateBigTicketInfo(Table bigTicket) {

        SnapshotArray<Actor> children = bigTicket.getChildren();
        String orderNum = "";
        String meal = "";
        String timeLeft = "";
        boolean ingredientBool = false;
        for (int i = 0; i < children.size; i++) {
            Actor actor = children.get(i);
            if (actor instanceof Label) {
                Label label = (Label) actor;
                String text = label.getText().toString();
                if (i == 0) {
                    orderNum = text.replace("Order ", "");
                } else if (text.startsWith("Timer:")) {
                    timeLeft = text.replace("Timer: ", "");
                } else { // handling meal name
                    if (ingredientBool == false) { // only handles the ingredient meal
                        meal = text;
                        ingredientBool = true;
                    } else {
                        //if you want to parse the individual ingredients of the recipe, factor the text value here
                        continue;
                    }

                }
            }
        }
        ServiceLocator.getTicketDetails().onUpdateBigTicket(orderNum, meal, timeLeft);
    }

    /**
     * Draws the order tickets on the screen. The actual drawing is handled by the stage.
     *
     * @param batch the sprite batch used for drawing.
     */
    @Override
    public void draw(SpriteBatch batch) {
        // Draw is handled by the stage
    }

    /**
     * Returns the z-index for this component. The z-index determines the rendering order of UI components.
     *
     * @return the z-index for this component.
     */
    @Override
    public float getZIndex() {
        return Z_INDEX;
    }

    /**
     * Sets the stage
     *
     * @param stage the stage of the game
     */
    @Override
    public void setStage(Stage stage) {
        if (stage == null) {
            logger.error("Attempted to set a null stage.");
            return;
        }
        this.stage = stage;
    }

    /**
     * Disposes of all resources associated with the order tickets, including clearing and removing tables from the stage.
     */
    @Override
    public void dispose() {
        // Cleanup resources
        //from team 2, i reset the ordernumb back to 0, for each new day when dispose is called
//        orderNumb = 0;
        for (Table table : tableArrayList) {
            table.clear();
            table.remove();
        }
        tableArrayList.clear();
        startTimeArrayList.clear();
        backgroundArrayList.clear();
        countdownLabelArrayList.clear();
        super.dispose();
    }

    /**
     * Returns the viewportWidth.
     * @return the float value of viewportWidth.
     */
    public float getViewportWidth() {
        return viewportWidth;
    }

    /**
     * Returns the viewportHeight.
     * @return the float value of viewportHeight.
     */
    public float getViewportHeight() {
        return viewportHeight;
    }

    /**
     * Returns the viewportHeightMultiplayer.
     * @return the float value of viewportHeightMultiplayer.
     */
    public float getViewPortHeightMultiplier() {
        return viewPortHeightMultiplier;
    }

    /**
     * Returns the list of tables used for displaying order tickets.
     * @return the list of order ticket tables.
     */
    public static ArrayList<Table> getTableArrayList() {
        return tableArrayList;
    }

    /**
     * Gets the times that the recipes were created
     * @return the start time array list
     */
    public static ArrayList<Long> getStartTimeArrayList() {
        return startTimeArrayList;
    }

    /**
     * Gets the recipes' timer count down labels
     * @return the countdown label array list
     */
    public static ArrayList<Label> getCountdownLabelArrayList() {
        return countdownLabelArrayList;
    }

    /**
     * Gets the Docket's background displays
     * @return the background array list
     */
    public static ArrayList<Docket> getBackgroundArrayList() {
        return backgroundArrayList;
    }

    /**
     * Gets the making time of the recipe multiplied by the default timer
     * @return the recipe timer
     */
    public long getTimer() {
        return getRecipe().getMakingTime() * DEFAULT_TIMER;
    }

    /**
     * Gets the list of recipe times
     * @return the recipe time array list
     */
    public static ArrayList<Long> getRecipeTimeArrayList() {
        return recipeTimeArrayList;
    }

}
