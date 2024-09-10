package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.TimeUtils;
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
    private static final float Z_INDEX = 2f;
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

    /**
     * Constructs an MainGameOrderTicketDisplay instance
     */
    public MainGameOrderTicketDisplay() {
        tableArrayList = new ArrayList<>();
        startTimeArrayList = new ArrayList<>();
        backgroundArrayList = new ArrayList<>();
        countdownLabelArrayList = new ArrayList<>();
        recipeTimeArrayList = new ArrayList<>();
        setRecipeValue(2);
        ServiceLocator.getPlayerService().getEvents().addListener("playerCreated", (Entity player) -> {
            inventoryComponent = player.getComponent(InventoryComponent.class);
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
        float xVal = cntXval(tableArrayList.size());
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

        for (String ingredient : getRecipe().getIngredients()) {
            Label ingredientLabel = new Label(ingredient, skin);
            table.add(ingredientLabel).padLeft(10f).row();
        }
        recipeTimeArrayList.add(getTimer());
        Label countdownLabel = new Label("Timer: " + getTimer(), skin);
        countdownLabelArrayList.add(countdownLabel);
        table.add(countdownLabel).padLeft(10f).row();

        stage.addActor(table);
        updateDocketSizes();
    }

    /**
     * Calculates the x-position for an order ticket based on its index.
     *
     * @param instanceCnt the index of the order ticket.
     * @return the x-position for the order ticket.
     */
    private float cntXval(int instanceCnt) {
        return 225f + (instanceCnt - 1) * (distance + viewportWidth * 3f / 32f);
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
//        table.setBackground((Drawable) null);
//        table.clear();
//        table.remove();
//        ServiceLocator.getDocketService().getEvents().trigger("removeOrder", i);
//        docket.dispose();
//        tableArrayList.remove(i);
//        backgroundArrayList.remove(i);
//        startTimeArrayList.remove(i);
//        countdownLabelArrayList.remove(i);
//        recipeTimeArrayList.remove(i);
//        inventoryComponent.addGold(getRecipeValue());

        if (table != null) {
            table.setBackground((Drawable) null);
            table.clear(); // Null check before calling clear
            table.remove();
        } else {
            logger.error("Table is null in stageDispose.");
        }

        if (docket != null) {
            docket.dispose();
        } else {
            logger.error("Docket is null in stageDispose.");
        }

        ServiceLocator.getDocketService().getEvents().trigger("removeOrder", i);

        // Remove from respective lists
        if (i < tableArrayList.size()) tableArrayList.remove(i);
        if (i < backgroundArrayList.size()) backgroundArrayList.remove(i);
        if (i < startTimeArrayList.size()) startTimeArrayList.remove(i);
        if (i < countdownLabelArrayList.size()) countdownLabelArrayList.remove(i);
        if (i < recipeTimeArrayList.size()) recipeTimeArrayList.remove(i);

        if (inventoryComponent != null) {
            inventoryComponent.addGold(getRecipeValue());
        }
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
            float xVal = cntXval(i + 1);

            table.setPosition(xVal, table.getY());
        }
    }

    /**
     * Updates the sizes of all dockets. The last docket in the list is enlarged, while others remain the same size.
     */
    public void updateDocketSizes() {
        float xEnlargedArea = viewportWidth - 320f;
        for (int i = 0; i < tableArrayList.size(); i++) {
            Table table = tableArrayList.get(i);
            float xVal = cntXval(i + 1);
            float yVal = viewportHeight * viewPortHeightMultiplier;
            Array<Cell> cells = table.getCells();
            if (i == tableArrayList.size() - 1) { // Tail docket
                table.setSize(170f * (viewportWidth/1920f), 200f * (viewportHeight/1080f));
                // Fixed position for enlarged docket
                table.setPosition(xEnlargedArea, 855 * (viewportHeight/1080)); // - (40 * viewPortHeightMultiplier)
                // Apply enlarged font size
                for (int j = 0; j < cells.size; j++) {
                    Label label = (Label)cells.get(j).getActor();
                    label.setFontScale(1f * (viewportWidth/1920f));
                }
            } else { // Non-enlarged dockets
                table.setSize(120f * (viewportWidth/1920f), 150f * (viewportHeight/1080f));
                table.setPosition(xVal, 900f * (viewportHeight/1080f));
                for (int j = 0; j < cells.size; j++) {
                    Label label = (Label)cells.get(j).getActor();
                    label.setFontScale(0.7f * (viewportWidth/1920f));
                }
            }
        }
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
        } else {
            ServiceLocator.getDocketService().getEvents().trigger("updateBigTicket", null, null, null);

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
        tableArrayList.remove(index);
        backgroundArrayList.remove(index);
        startTimeArrayList.remove(index);
        countdownLabelArrayList.remove(index);
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
                    // TODO Overrides the current meal name with the last ingredient in the big ticket.
                    // this last ingredient should instead be the meal name (ie "banana split")
                    // alternatively for it to store all the ingredients, could concatenate it all with
                    // meal = meal + " " + text;
                    meal = text;
                }
            }
        }
        ServiceLocator.getDocketService().getEvents().trigger("updateBigTicket", orderNum, meal, timeLeft);
        //orderActions.onUpdateBigTicket(orderNum, meal, timeLeft);
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
        for (Table table : tableArrayList) {
            if (table != null) { // Null check
                table.clear();
                table.remove();
            } else {
                logger.error("Table is null in dispose method.");
            }
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

    public static ArrayList<Long> getRecipeTimeArrayList() {
        return recipeTimeArrayList;
    }

}