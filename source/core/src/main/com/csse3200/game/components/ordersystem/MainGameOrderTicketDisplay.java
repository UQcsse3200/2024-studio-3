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
import com.badlogic.gdx.utils.StringBuilder;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.physics.components.PhysicsMovementComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.List;

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
    private static int orderNumb = 0;
    private static final long DEFAULT_TIMER = 10000;

    private String recipeName;
    private Recipe recipe;
    private List<String> ingredients;
    private Integer burnedTime;
    private String stationType;
    private long timer;

    /**
     * Constructs an MainGameOrderTicketDisplay instance
     *
     * @param recipeName the name of the recipe to be displayed
     */
    public MainGameOrderTicketDisplay(String recipeName) {
        this.recipeName = recipeName;
        this.recipe = new Recipe(recipeName);
        this.ingredients = recipe.getIngredients();
        this.burnedTime = recipe.getBurnedTime();
        this.stationType = recipe.getStationType();
        this.timer = recipe.getMakingTime() * DEFAULT_TIMER;
        tableArrayList = new ArrayList<>();
        startTimeArrayList = new ArrayList<>();
        backgroundArrayList = new ArrayList<>();
        countdownLabelArrayList = new ArrayList<>();



    }

    /**
     * Initialises the display and sets up event listeners for creating and shifting orders.
     */
    @Override
    public void create() {
        super.create();
        logger.info("MainGameOrderTicketDisplay created");

        entity.getEvents().addListener("createOrder", this::addActors);
//        logger.info("Listener added for createOrder event");
        ServiceLocator.getDocketService().getEvents().addListener("shiftDocketsLeft", this::shiftDocketsLeft);
        ServiceLocator.getDocketService().getEvents().addListener("shiftDocketsRight", this::shiftDocketsRight);
        // logger.info("Listeners added for shiftDocketsLeft and shiftDocketsRight events");
    }
    /**
     * Adds a new order ticket to the display and sets its initial position and size.
     * Initialises the background, labels, and countdown timer for the order.
     */
    public void addActors() {
        if (startTimeArrayList == null) {
            logger.error("startTimeArrayList is not initialized");
            return;
        }

        logger.info("Adding a new order ticket");

        Table table = new Table();
        long startTime = TimeUtils.millis();
        startTimeArrayList.add(startTime);
        tableArrayList.add(table);
//        logger.info("New table added. Total tables: {}", tableArrayList.size());

        table.setFillParent(false);
        table.setSize(viewportWidth * 3f / 32f, 5f / 27f * viewportHeight); // DEFAULT_HEIGHT
        float xVal = cntXval(tableArrayList.size());
        float yVal = viewportHeight * viewPortHeightMultiplier;
        table.setPosition(xVal, yVal);
//        logger.info("Position set for new table: ({}, {})", xVal, yVal);

        Docket background = new Docket();
        backgroundArrayList.add(background);
//        logger.info("New docket background added. Total backgrounds: {}", backgroundArrayList.size());
        table.setBackground(background.getImage().getDrawable());

        String orderNumStr = "Order" + " " + ++orderNumb;
        Label orderNumbLabel = new Label(orderNumStr, skin);
        table.add(orderNumbLabel).padLeft(10f).row();

        Label recipeNameLabel = new Label(recipeName, skin);
        table.add(recipeNameLabel).padLeft(10f).row();

        for (String ingredient : ingredients) {
            Label ingredientLabel = new Label(ingredient, skin);
            table.add(ingredientLabel).padLeft(10f).row();
        }

        Label countdownLabel = new Label("Timer: " + timer, skin);
        countdownLabelArrayList.add(countdownLabel);
//        logger.info("Labels added. Total countdown labels: {}", countdownLabelArrayList.size());
        table.add(countdownLabel).padLeft(10f).row();

        stage.addActor(table);
        logger.info("Order ticket added to stage");

        // Enlarge the last docket in the list
        updateDocketSizes();
    }

    /**
     * Calculates the x-position for an order ticket based on its index.
     *
     * @param instanceCnt the index of the order ticket.
     * @return the x-position for the order ticket.
     */
    private float cntXval(int instanceCnt) {
        return 20f + (instanceCnt - 1) * (distance + viewportWidth * 3f / 32f);
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
         logger.info("First table moved to the end. New first table index: {}", tableArrayList.get(0));

         Docket firstDocket = backgroundArrayList.remove(0);
         backgroundArrayList.add(firstDocket);
         logger.info("First docket background moved to the end. New first docket index: {}", backgroundArrayList.get(0));

        Long firstStartTime = startTimeArrayList.remove(0);
        startTimeArrayList.add(firstStartTime);

        Label firstCountdownLabel = countdownLabelArrayList.remove(0);
        countdownLabelArrayList.add(firstCountdownLabel);

        updateDocketPositions();
        updateDocketSizes();
        logger.info("Docket positions updated after left shift");
    }

    /**
     * Removes and disposes of a docket from the stage and its associated resources.
     *
     * @param docket the docket to be disposed of.
     * @param table  the table representing the docket.
     * @param index  the index of the docket.
     */
    private void stageDispose(Docket docket, Table table, int index) {
        table.setBackground((Drawable) null);
        table.clear();
        table.remove();
        ServiceLocator.getDocketService().getEvents().trigger("removeOrder", index);
        docket.dispose();
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
//            logger.info("Updated position of docket {}: ({}, {})", i, xVal, table.getY());
        }
    }
    /**
     * Updates the sizes of all dockets. The last docket in the list is enlarged, while others remain the same size.
     */
    public void updateDocketSizes() {
        float xEnlargedArea = viewportWidth - 260f;
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
            long remainingTime = timer - elapsedTime;
            if (remainingTime > 0) {
                currCountdown.setText("Timer: " + (remainingTime / 1000));
                currBackground.updateDocketTexture((double) remainingTime / 1000);
                currTable.setBackground(currBackground.getImage().getDrawable());
            } else {
                stageDispose(currBackground, currTable, i);
                tableArrayList.remove(i);
                backgroundArrayList.remove(i);
                startTimeArrayList.remove(i);
                countdownLabelArrayList.remove(i);
            }
        }
        if (!tableArrayList.isEmpty()) {
            Table lastTable = tableArrayList.get(tableArrayList.size() - 1);
            updateBigTicketInfo(lastTable);
        } else {
            ServiceLocator.getDocketService().getEvents().trigger("onUpdateBigTicket", null, null, null);

        }


        updateDocketPositions();
        updateDocketSizes();
    }

    /**
     * Updates the display for all dockets. This includes updating their positions and sizes.
     */
    public void updateDocketDisplay() {
        // Implement logic to update the display
        updateDocketPositions();
    }

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
                } else if (i == 1) {  // Assuming the recipe name is always the second label
                    meal = text;
                } else if (i == 2) {
                    timeLeft = text.replace("Timer: ", "");
                }
            }
        }
        /*
        Array<Label> labels = bigTicket.getChildren().filter(Label.class);
        String orderNum = labels.get(0).getText().toString().replace("Order ", "");
        String meal = labels.get(1).getText().toString();
        String timeLeft = labels.get(labels.size - 1).getText().toString();

         */
        ServiceLocator.getDocketService().getEvents().trigger("onUpdateBigTicket", orderNum, meal, timeLeft);
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

//    public void stageDispose(Docket background, Table table, int index) {
//        table.setBackground((Drawable) null);
//        table.clear();
//        table.remove();
//        ServiceLocator.getDocketService().getEvents().trigger("removeOrder", index);
//        background.dispose();
//        //dispose();
//    }
    /**
     * Returns the list of tables used for displaying order tickets.
     * @return the list of order ticket tables.
     */
    public static ArrayList<Table> getTableArrayList() {
        return tableArrayList;
    }

}