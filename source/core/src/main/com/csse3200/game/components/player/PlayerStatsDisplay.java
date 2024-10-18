package com.csse3200.game.components.player;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import com.badlogic.gdx.utils.Timer;
/**
 * AN ui component for displaying player stats, e.g. health.
 */
public class PlayerStatsDisplay extends UIComponent {
  Table table;
  Table goldTable;
  Table timerTable;
  Table dayTable;
  Table goldContentsTable;
  Table dayContentsTable;

  private Image timerImage;
  private Image goldImage;

    private Label goldLabel;
  private Label dayLabel;
  private static Label timerLabel;
  private static long timer;
  private static PlayerStatsDisplay instance;



    /**
   * Creates reusable ui styles and adds actors to the stage.
   */
  @Override
  public void create() {
    super.create();
    setPlayerStatsDisplay(this);
    addActors();
    setTimer(ServiceLocator.getDayNightService().FIVE_MINUTES);


    entity.getEvents().addListener("updateGold", this::updatePlayerGoldUI);
    ServiceLocator.getDayNightService().getEvents().addListener("newday", this::updateDay);
    ServiceLocator.getDayNightService().getEvents().addListener("Second", PlayerStatsDisplay::updateTime);
  }

  /**
   * Sets the timer to a specific time and starts counting down
   * @param time the time you want to set the timer to count down from in seconds
   */
  public static void setTimer(long time) { timer = time;}

  /**
   * Sets the label for the day
   * @param label: The label being set
   */
  public void setDayLabel(Label label) { dayLabel = label; }

  /**
   * Gets the current day label
   * @return The current day label
   */
  public Label getDayLabel() { return dayLabel; }

  /**
   * Sets the label for the timer
   * @param label: The label being set
   */
  public static void setTimerLabel(Label label) { timerLabel = label; }

  /**
   * Gets the current timer label
   * @return The current timer label
   */
  public static Label getTimerLabel() { return timerLabel; }

  /**
   * Sets the player stats display
   * @param playerStatsDisplay: The Player stats display that is being set.
   */
  public static void setPlayerStatsDisplay(PlayerStatsDisplay playerStatsDisplay) { instance = playerStatsDisplay; }

  /**
   * Gets the current Player stats display
   * @return the current player stats display
   */
  public static PlayerStatsDisplay getInstance() { return instance; }

  /**
   * Creates actors and positions them on the stage using a table.
   * @see Table for positioning options
   */
  private void addActors() {
    table = new Table();
    table.top().left();
    table.setFillParent(true);
    table.padTop(80f).padLeft(20f);

    goldTable = new Table();
    timerTable = new Table();
    dayTable = new Table();
    goldContentsTable = new Table();
    dayContentsTable = new Table();

    table.row();

    String smallLabel = "cash";


    // Timer label for the remaining time in the day
    CharSequence timerText = String.format("%s", convertDigital(timer));
    setTimerLabel(new Label(timerText, skin, smallLabel));

    // Timer Icon
    timerImage = new Image(ServiceLocator.getResourceService().getAsset("images/hourglass.png", Texture.class));
    timerTable.add(timerImage).size(50).padRight(5); // Add icon before the timer label

    // Add the timer label to the timerTable
    timerTable.add(getTimerLabel());

    // Center the timer table in the middle of the screen
    table.add(timerTable);  // Ensure the timer is centered
    table.row();

    // --- Update: Day Label with Container ---

    // Label for the Current Day
    CharSequence dayText = String.format("Day: %d", ServiceLocator.getDayNightService().getDay()); // Start with Day 1
    dayLabel = new Label(dayText, skin, smallLabel);
    dayLabel.setColor(Color.GOLD);  // Set text color to white for contrast

    // Wrap day label in container for background
    Container<Label> dayLabelContainer = new Container<>(dayLabel);

    // Set background for day label container (similar to gold label)
    TextureRegionDrawable dayBackground = new TextureRegionDrawable(
        new TextureRegion(ServiceLocator.getResourceService().getAsset("images/box_background4.png", Texture.class))
    );
    dayLabelContainer.setBackground(dayBackground);  // Set background drawable

    // Calendar Icon
    Image calendarImage = new Image(ServiceLocator.getResourceService().getAsset("images/calendar.png", Texture.class));
    dayContentsTable.add(calendarImage).size(50).padRight(0).padLeft(5); // Add icon before the gold label

    // Add padding to the container for better spacing
    dayLabelContainer.pad(10).padLeft(20);  // Optional: Adjust padding as needed

    // Add the day label container to the table
    dayContentsTable.add(dayLabelContainer);
    table.add(dayContentsTable).left().width(210f).height(180).padTop(0);
    table.row();

    // --- Existing: Gold Label with Container ---
    int gold = entity.getComponent(CombatStatsComponent.class).getGold();
    CharSequence goldText = String.format("Cash: %d", gold);

    // Create a container to hold the gold label
    goldLabel = new Label(goldText, skin, smallLabel);
    goldLabel.setColor(Color.GOLD);  // Make text color gold

    // Wrap gold label in container for background
    Container<Label> goldLabelContainer = new Container<>(goldLabel);

    // Set background for container
    TextureRegionDrawable goldBackground = new TextureRegionDrawable(
        new TextureRegion(ServiceLocator.getResourceService().getAsset("images/box_background.png", Texture.class))
    );
    goldLabelContainer.setBackground(goldBackground);  // Set background drawable

    // Gold Icon
    goldImage = new Image(ServiceLocator.getResourceService().getAsset("images/coin.png", Texture.class));
    goldContentsTable.add(goldImage).size(35).padRight(0).padLeft(10); // Add icon before the gold label

    // Add the gold label container and image to the goldTable
    goldContentsTable.add(goldLabelContainer);
    goldTable.add(goldContentsTable);
    table.add(goldTable).left().width(220f).height(170).padTop(0);  // Align to the very left

    table.row();


    // Add the table to the stage
    stage.addActor(table);

  }

  @Override
  public void draw(SpriteBatch batch)  {
    // draw is handled by the stage
  }

  /**
   * Updates the player's gold on the ui.
   * @param gold player gold
   */
  public void updatePlayerGoldUI(int gold) {
    CharSequence text = String.format("Cash: %d", gold);
    goldLabel.setText(text);
  }

  /**
   * Updates the displayed current day on the UI.
   */
  public void updateDay() {
    int currentDay = ServiceLocator.getDayNightService().getDay();
    CharSequence dayText = String.format("Day: %d", currentDay);
    getDayLabel().setText(dayText);
  }

  /**
   * Updates the colour of the timer text depending on current time
   */
  private static void updateTimeColor() {
    long timeInSeconds = TimeUnit.MILLISECONDS.toSeconds(timer);

    if (timeInSeconds <= 60) {
      getTimerLabel().setColor(Color.RED);  // Critical - red when below 1 minute
    } else if (timeInSeconds <= 150) {
      getTimerLabel().setColor(Color.YELLOW);  // Warning - yellow when below 2 minutes and 30 seconds
    } else {
      getTimerLabel().setColor(Color.WHITE);  // Safe - white when above 2:30
    }
  }

  /**
   * Updates the remaining time for the current day on the UI. Decreases the timer by one second
   * and updates the displayed time.
   */

  public static void updateTime(long time) {
    timer = time;

    // Update the timer color based on remaining time
    updateTimeColor();

    // Emphasize the timer when below 1 minute by flickering and increasing size
    if (timer < TimeUnit.MINUTES.toMillis(1)) {
      flickerTimer();
      increaseTimerSize();
    } else {
      resetTimerSize();  // Reset the size when the time is above 1 minute
    }

    // Format and update the timer label with the remaining time
    CharSequence timerText = String.format("Time Left: %n   %s", convertDigital(time));
    getTimerLabel().setText(timerText);

    // Trigger other events if necessary
    ServiceLocator.getDayNightService().getEvents().trigger("callpastsecond");
  }

  /**
   * Flickers the timer by toggling its visibility every 0.5 seconds.
   */
  private static void flickerTimer() {
    Timer.schedule(new Timer.Task() {
      @Override
      public void run() {
        boolean isVisible = getTimerLabel().isVisible();
        getTimerLabel().setVisible(!isVisible);  // Toggle visibility
      }
    }, 0, 0.5f);  // Flicker every 0.5 seconds
  }

  /**
   * Increases the size of the timer as it approaches zero.
   */
  private static void increaseTimerSize() {
    float newFontScale = 1.2f;  // Make the timer 1.2x its original size
    getTimerLabel().setFontScale(newFontScale);
  }

  /**
   * Resets the timer label size to its original value.
   */
  private static void resetTimerSize() {
    float originalFontScale = 1.0f;  // Reset the font scale
    getTimerLabel().setFontScale(originalFontScale);
  }

  @Override
  public void dispose() {
    super.dispose();
    goldImage.remove();
    goldLabel.remove();
    getDayLabel().remove();
    getTimerLabel().remove();
    timerImage.remove();
  }

  @Override
  public void setStage(Stage mock) {
    // Method not needed
  }

  /**
   * Converts a time value in milliseconds into a formatted digital time (MM:SS).
   *
   * @param x The time in milliseconds.
   * @return A string representing the time in "MM:SS" format.
   */
  public static String convertDigital(long x) {
    long minutes = TimeUnit.MILLISECONDS.toMinutes(x);
    long seconds = TimeUnit.MILLISECONDS.toSeconds(x) - TimeUnit.MINUTES.toSeconds(minutes);
    return String.format("%02d:%02d", minutes, seconds);

}
public static void reset() {
    ServiceLocator.getDayNightService().setDay(1);
}


}
