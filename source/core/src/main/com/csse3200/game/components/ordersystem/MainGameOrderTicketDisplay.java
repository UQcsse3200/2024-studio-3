package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;
import com.badlogic.gdx.utils.SnapshotArray;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.PlayerService;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Displays order tickets on the main game screen. This class manages the
 * creation, positioning, and updating of order tickets, as well as shifting
 * them left or right in response to user actions. It also handles the
 * countdown timers for each order and disposes of them when the timer expires.
 */
public class MainGameOrderTicketDisplay extends UIComponent {
    private static final Logger logger = LoggerFactory.getLogger(MainGameOrderTicketDisplay.class);
    private static final float Z_INDEX = 3f;
    private static final float VIEW_PORT_HEIGHT_MULTIPLIER = 7f / 9f;
    private final float viewportWidth;
    private final float viewportHeight;
    private static final int DISTANCE = 20;
    private static final ArrayList<Table> tableArrayList = new ArrayList<>();
    private static final ArrayList<Long> startTimeArrayList = new ArrayList<>();
    private static final ArrayList<Docket> backgroundArrayList = new ArrayList<>();
    private static final ArrayList<Label> countdownLabelArrayList = new ArrayList<>();
    private static final ArrayList<Long> recipeTimeArrayList = new ArrayList<>();
    private static int orderNumb = 0;
    private static final long DEFAULT_TIMER = 10000;
    private Recipe recipe;
    private Image mealImage;
    private final DocketMealDisplay mealDisplay;
    private static final float DISTANCE_MULTIPLIER = 0.015f;
    private CombatStatsComponent combatStatsComponent;
    private boolean isPaused = false;
    private long pauseStartTime = 0;
    private long totalPausedDuration = 0;

    private static final ArrayList<Image> imageArrayList = new ArrayList<>();
    private static final ArrayList<String> stringArrayList = new ArrayList<>();

    private static final Map<String, Texture> textureMap = new HashMap<>();

    private final String TIMER = "Timer: ";

    /**
     * Constructs an MainGameOrderTicketDisplay instance
     */
    public MainGameOrderTicketDisplay(RenderService renderService, PlayerService playerService) {
        this.viewportHeight = renderService.getStage().getViewport().getCamera().viewportHeight;
        this.viewportWidth = renderService.getStage().getViewport().getCamera().viewportWidth;
        mealDisplay = new DocketMealDisplay();

        playerService.getEvents().addListener("playerCreated",
                (Entity player) -> combatStatsComponent = player.getComponent(CombatStatsComponent.class));
        loadTextures();
    }

    /**
     * Get combat statistics component
     */
    public CombatStatsComponent getCombatStats() {
        return combatStatsComponent;
    }

    /**
     * Sets the combat statistics component
     */
    public void setCombatStatsComponent(CombatStatsComponent newCombatStatsComponent) {
        combatStatsComponent = newCombatStatsComponent;
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
     * Get recipe name
     * @return recipe name
     */
    public String getCurrentRecipeName() {
        return recipe != null ? recipe.getName() : null;
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
        if (paused) {
            pauseStartTime = TimeUtils.millis();
        } else {
            totalPausedDuration += TimeUtils.timeSinceMillis(pauseStartTime);
        }

        for (Docket docket : backgroundArrayList) {
            docket.setPaused(paused);
        }
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
        ServiceLocator.getDocketService().getEvents().addListener("removeBigTicket", this::removeBigTicket);

        //From Team 2, these listeners are for our dance party upgrade to pause and unpause docket times
        // ServiceLocator.getDocketService().getEvents().addListener("Dancing", ()->{
        //     addTimeToDockets(600000);
        // });
        ServiceLocator.getRandomComboService().getEvents().addListener("Dancing", ()->{
            addTimeToDockets(600000);
        });

        ServiceLocator.getDocketService().getEvents().addListener("paused", ()->{
            setPaused(true);
        });
        ServiceLocator.getDocketService().getEvents().addListener("unpaused", ()->{
            setPaused(false);
        });

        //From team 2, I used your dispose method here when listening for a new day, so current dockets get removed
        //when the end of day occurs
        ServiceLocator.getDocketService().getEvents().addListener("Dispose", this::dispose);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                return handleKeyDown(keycode);
            };
        });
    }

    /**
     * Handles key press events. If the ESCAPE key is pressed, it toggles the paused state.
     * @param keycode The key code of the key that was pressed.
     * @return true if the ESCAPE key was pressed and the event was handled, false otherwise.
     */
    public boolean handleKeyDown(int keycode) {
        if (keycode == com.badlogic.gdx.Input.Keys.ESCAPE || keycode == Input.Keys.NUM_0)  {
            setPaused(!isPaused);
            return true;
        }
        return false;
    }

    private void loadTextures() {
        for(String path : DocketMealDisplay.getMealDocketTextures()){
            textureMap.put(path, new Texture(Gdx.files.local(path)));
        }
    }

    /**
     * Adds a new order ticket to the display and sets its initial position and size.
     * Initialises the background, labels, and countdown timer for the order.
     */
    public void addActors() {
        logger.info("Adding actors");
        Table table = new Table();
        long startTime = TimeUtils.millis();

        startTimeArrayList.add(startTime);
        tableArrayList.add(table);

        table.setFillParent(false);
        table.setSize(viewportWidth * 3f / 32f, 5f / 27f * viewportHeight); // DEFAULT_HEIGHT
        float xVal = cntXval(250f, tableArrayList.size());
        float yVal = viewportHeight * VIEW_PORT_HEIGHT_MULTIPLIER;
        table.setPosition(xVal, yVal);
        table.padTop(25f);
        Docket background = new Docket(getTimer());

        // Check if Dancing is active, and add 60 seconds to the timer for new tickets
        long ticketTimer = getTimer();
        if (ServiceLocator.getRandomComboService().dancing()) {
            ticketTimer += 600000; // Add 60 seconds
        }
        recipeTimeArrayList.add(ticketTimer); // Add adjusted time to the list

        backgroundArrayList.add(background);
        table.setBackground(background.getImage().getDrawable());

        String orderNumStr = "Order" + " " + ++orderNumb;
        Label orderNumbLabel = new Label(orderNumStr, skin);
        table.add(orderNumbLabel).padLeft(10f).row();

        Label recipeNameLabel = new Label(getRecipe().getName(), skin);
        table.add(recipeNameLabel).padLeft(10f).row();

        String s=getRecipe().getName();
        stringArrayList.add(s);
        Texture texture= textureMap.get(mealDisplay.getMealImage(s,"vertical"));
        mealImage=new Image(new TextureRegionDrawable(texture));
        imageArrayList.add(mealImage);
        table.add(mealImage).row();

        recipeTimeArrayList.add(getTimer());
        Label countdownLabel = new Label(TIMER + getTimer(), skin);
        countdownLabelArrayList.add(countdownLabel);
        table.add(countdownLabel).padLeft(10f).row();

        // Update TicketDetails immediately
        String orderNumber = orderNumStr.replace("Order ", ""); // Remove "Order " prefix
        String mealName = getRecipe().getName();
        String timeLeft = String.valueOf(getTimer() / 1000); // Convert milliseconds to seconds

        // Call onUpdateBigTicket() to update TicketDetails
        ServiceLocator.getTicketDetails().onUpdateBigTicket(orderNumber, mealName, timeLeft);

        stage.addActor(table);
        updateDocketSizes();

        table.setZIndex((int)getZIndex());
        printOrderList();
    }

    private void printOrderList() {
        logger.info("Current List of Orders:");
        for (int i = 0; i < stringArrayList.size(); i++) {
            logger.info("Order {}: {}", i + 1, stringArrayList.get(i));
        }
    }

    /**
     * Calculates the x-position for an order ticket based on its index.
     *
     * @param startPoint the starting point of the first docket.
     * @param instanceCnt the index of the order ticket.
     * @return the x-position for the order ticket.
     */
    private float cntXval(float startPoint, int instanceCnt) {
        return startPoint + 100 + (instanceCnt - 1) * ((viewportWidth * DISTANCE_MULTIPLIER) + viewportWidth * 3f / 32f);
    }

    /**
     * Reorders the dockets after one is removed or shifted.
     *
     * @param index the index of the docket that was removed or shifted.
     */
    public static void reorderDockets(int index) {
        float viewportWidth = Gdx.graphics.getWidth();

        for (int i = index + 1; i < tableArrayList.size(); i++) {
            Table currTable = tableArrayList.get(i);
            currTable.setX(currTable.getX() - (DISTANCE + viewportWidth * 3f / 32f));
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
        Table firstTable = tableArrayList.removeFirst();
        tableArrayList.add(firstTable);

        Docket firstDocket = backgroundArrayList.removeFirst();
        backgroundArrayList.add(firstDocket);

        Long firstStartTime = startTimeArrayList.removeFirst();
        startTimeArrayList.add(firstStartTime);

        Label firstCountdownLabel = countdownLabelArrayList.removeFirst();
        countdownLabelArrayList.add(firstCountdownLabel);

        long firstRecipeTime = recipeTimeArrayList.removeFirst();
        recipeTimeArrayList.add(firstRecipeTime);

        Image firstImage = imageArrayList.removeFirst();
        imageArrayList.add(firstImage);

        String firstString = stringArrayList.removeFirst();
        stringArrayList.add(firstString);

        updateDocketPositions();
        updateDocketSizes();
    }


    /**
     * Removes and disposes of a docket from the stage and its associated resources.
     *
     * @param docket the docket to be disposed of.
     * @param table  the table representing the docket.
     * @param i  the index of the docket.
     */
    public void stageDispose(Docket docket, Table table, int i) {
        logger.info("Dispose Docket");
        table.setBackground((Drawable) null);
        table.clear();
        table.remove();
        docket.dispose();
        tableArrayList.remove(i);
        backgroundArrayList.remove(i);
        startTimeArrayList.remove(i);
        countdownLabelArrayList.remove(i);
        recipeTimeArrayList.remove(i);
        stringArrayList.remove(i);
        imageArrayList.remove(i);

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
        Table lastTable = tableArrayList.removeLast();
        tableArrayList.addFirst(lastTable);

        Docket lastDocket = backgroundArrayList.removeLast();
        backgroundArrayList.addFirst(lastDocket);

        Long lastStartTime = startTimeArrayList.removeLast();
        startTimeArrayList.addFirst(lastStartTime);

        Label lastCountdownLabel = countdownLabelArrayList.removeLast();
        countdownLabelArrayList.addFirst(lastCountdownLabel);

        long recipeTime = recipeTimeArrayList.removeLast();
        recipeTimeArrayList.addFirst(recipeTime);

        Image lastImage = imageArrayList.removeLast();
        imageArrayList.addFirst(lastImage);

        String lastString = stringArrayList.removeLast();
        stringArrayList.addFirst(lastString);

        updateDocketPositions();
        updateDocketSizes();

        // logger.info("Docket positions updated after right shift");
    }

    /**
     * Updates the positions of all dockets based on their current index in the list.
     */
    private void updateDocketPositions() {
        for (int i = 0; i < tableArrayList.size(); i++) {
            Table table = tableArrayList.get(i);
            float xVal = cntXval(250f, i + 1);

            table.setPosition(xVal, table.getY());
        }
    }

    /**
     * Updates the docket sizes
     */
    public void updateDocketSizes() {
        float graphicsWidth = Gdx.graphics.getWidth();
        float graphicsHeight = Gdx.graphics.getHeight();
        float scaleFactor = getScalingFactor(graphicsWidth, graphicsHeight);

        // Docket size ratios
        float normalDocketWidth = 1280f * 0.08f * scaleFactor;
        float normalDocketHeight = 800f * 0.25f * scaleFactor;
        float enlargedDocketWidth = normalDocketWidth * 1.7f;
        float enlargedDocketHeight = normalDocketHeight * 1.7f;

        // Positioning
        float xPosEnlarged = graphicsWidth - enlargedDocketWidth - (graphicsWidth * 0.045f);
        float yPosEnlarged = (graphicsHeight * 0.938f) - (enlargedDocketHeight - 15);
        float yPosNormal = (graphicsHeight * 0.938f) - (normalDocketHeight * 0.8f - 15);
        float leftHandSideDistance = graphicsWidth * 0.105f;

        for (int i = 0; i < tableArrayList.size(); i++) {
            Table table = tableArrayList.get(i);
            boolean isLastDocket = (i == tableArrayList.size() - 1);

            updateTableSizeAndPosition(table, isLastDocket, enlargedDocketWidth, enlargedDocketHeight, normalDocketWidth, normalDocketHeight, xPosEnlarged, yPosEnlarged, leftHandSideDistance, i, yPosNormal);
            updateMealImage(table, isLastDocket, stringArrayList.get(i));
            updateCellStyles(table, isLastDocket, graphicsWidth);
        }
    }

    /**
     * Updates the table size and position
     * @param table: the table to be updated
     * @param isLastDocket: a flag to check if it is the last docket
     * @param enlargedDocketWidth: the width of the enlarged docket
     * @param enlargedDocketHeight: the height of the enlarged docket
     * @param normalDocketWidth: the width of the normal docket
     * @param normalDocketHeight: the height of the normal docket
     * @param xPosEnlarged: the x Position of the enlarged docket
     * @param yPosEnlarged: the y Position of the enlarged docket
     * @param leftHandSideDistance: the distance from the left side of the screen
     * @param index: the index of the docket
     * @param yPosNormal: the y position of the normal docket
     */
    private void updateTableSizeAndPosition(Table table, boolean isLastDocket, float enlargedDocketWidth, float enlargedDocketHeight, float normalDocketWidth, float normalDocketHeight, float xPosEnlarged, float yPosEnlarged, float leftHandSideDistance, int index, float yPosNormal) {
        float width = isLastDocket ? enlargedDocketWidth : normalDocketWidth * 1.3f;
        float height = isLastDocket ? enlargedDocketHeight : normalDocketHeight * 0.8f;
        float xPos = isLastDocket ? xPosEnlarged : cntXval(leftHandSideDistance, index + 1);
        float yPos = isLastDocket ? yPosEnlarged : yPosNormal;

        table.setSize(width, height);
        table.setPosition(xPos, yPos);
        table.setZIndex(isLastDocket ? 10 : 5);
    }

    /**
     * Updates the image of the meal
     * @param table: the table being updated
     * @param isLastDocket: a flag to check whether it is the last docket
     * @param mealId: the string representing the mealID
     */
    private void updateMealImage(Table table, boolean isLastDocket, String mealId) {
        mealImage = (Image) table.getChildren().get(2);
        String orientation = isLastDocket ? "vertical" : "horizontal";
        Texture texture = textureMap.get(mealDisplay.getMealImage(mealId, orientation));
        mealImage.setDrawable(new TextureRegionDrawable(texture));
    }

    /**
     * Updates the cell styles
     * @param table: the table being updated
     * @param isLastDocket: a flag to check if it is the last docket
     * @param graphicsWidth: the width of the viewport
     */
    private void updateCellStyles(Table table, boolean isLastDocket, float graphicsWidth) {
        for (Cell cell : table.getCells()) {
            Actor actor = cell.getActor();

            if (actor instanceof Label label) {
                float fontScale = isLastDocket ? graphicsWidth / 1920f : 0.7f * (graphicsWidth / 1920f);
                label.setFontScale(fontScale);
                if (label.getText().toString().contains("Timer")) {
                    cell.padBottom(isLastDocket ? 5f : 0f);
                }
            }

            if (actor instanceof Image image) {
                cell.padBottom(isLastDocket ? 10f : 5f);
                image.setScaling(Scaling.fit);
            }
        }
    }


    /**
     * Gets the scaling factor in respect to current width and height of screen.
     *
     * @param currentWidth current width of the game window screen.
     * @param currentHeight current height of the game window screen.
     * @return scaling factor
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
        if (isPaused) {
            return;
        }

        // No additional update logic needed here, shifting is handled by the OrderActions class
        for (int i = 0; i < tableArrayList.size(); i++) {
            Docket currBackground = backgroundArrayList.get(i);
            Table currTable = tableArrayList.get(i);
            Label currCountdown = countdownLabelArrayList.get(i);
            long elapsedTime = TimeUtils.timeSinceMillis(startTimeArrayList.get(i)) - totalPausedDuration;
            long remainingTime = recipeTimeArrayList.get(i) - elapsedTime;

            if (remainingTime > 0) {
                currCountdown.setText(TIMER + (remainingTime / 1000));
                currBackground.updateDocketTexture((double) remainingTime / 1000);
                currTable.setBackground(currBackground.getImage().getDrawable());
            } else {
                logger.info("Remaining time is 0");
                stageDispose(currBackground, currTable, i);
            }
        }
        if (!tableArrayList.isEmpty()) {
            Table lastTable = tableArrayList.getLast();
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
            if (actor instanceof Label label) {
                String text = label.getText().toString();
                if (i == 0) {
                    orderNum = text.replace("Order ", "");
                } else if (text.startsWith("Timer:")) {
                    timeLeft = text.replace(TIMER, "");
                } else if (!ingredientBool) { // only handles the ingredient meal
                        meal = text;
                        ingredientBool = true;
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
        //from team 2, I reset the ordernumb back to 0, for each new day when dispose is called
//        orderNumb = 0;
        for (Table table : tableArrayList) {
            table.clear();
            table.remove();
        }
        tableArrayList.clear();
        startTimeArrayList.clear();
        backgroundArrayList.clear();
        countdownLabelArrayList.clear();
        stringArrayList.clear();
        imageArrayList.clear();
        resetOrderNumb();
        //orderNumb = 0;
        super.dispose();
    }

    /**
     * Resets order number to 0 for screen transition purposes.
     */
    public static void resetOrderNumb(){
        orderNumb = 0;
    }

    /**
     * Returns Order Number.
     */
    public static int getOrderNumb(){
        return orderNumb;
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
        return VIEW_PORT_HEIGHT_MULTIPLIER;
    }

    /**
     * Returns the list of tables used for displaying order tickets.
     * @return the list of order ticket tables.
     */
    public static List<Table> getTableArrayList() {
        return tableArrayList;
    }

    /**
     * Gets the times that the recipes were created
     * @return the start time array list
     */
    public static List<Long> getStartTimeArrayList() {
        return startTimeArrayList;
    }

    /**
     * Gets the recipes' timer count down labels
     * @return the countdown label array list
     */
    public static List<Label> getCountdownLabelArrayList() {
        return countdownLabelArrayList;
    }

    /**
     * Gets the Docket's background displays
     * @return the background array list
     */
    public static List<Docket> getBackgroundArrayList() {
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
    public static List<Long> getRecipeTimeArrayList() {
        return recipeTimeArrayList;
    }

    /**
     * Gets the True or False depending on if the game is paused or not respectively
     * @return the boolean value of isPaused
     */
    public boolean isPaused() {
        return isPaused;
    }

    /**
     * Gets the start pause time
     * @return the long value of pauseStartTime
     */
    public long getPauseStartTime() {
        return pauseStartTime;
    }

    /**
     * Gets the total pause duration time
     * @return the long value of totalPausedDuration
     */
    public long getTotalPausedDuration() {
        return totalPausedDuration;

    }
    private void addTimeToDockets(long additionalTime) {
        for (int i = 0; i < recipeTimeArrayList.size(); i++) {
            recipeTimeArrayList.set(i, recipeTimeArrayList.get(i) + additionalTime);
        }
    }

}
