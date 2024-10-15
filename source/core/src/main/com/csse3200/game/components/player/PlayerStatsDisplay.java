package com.csse3200.game.components.player;

import com.badlogic.gdx.Gdx;
import com.csse3200.game.GdxGame;
import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.BaseDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.utils.Timer;



/**
 * AN ui component for displaying player stats, e.g. health.
 */
public class PlayerStatsDisplay extends UIComponent {
  Table table;
  Table goldTable;
  Table timerTable;
  private Image timerImage;
  private Image goldImage;
  private Label goldLabel;
  private static Label dayLabel;
  private static int currentDay;
  private static Label timerLabel;
  private static long timer;
  private static long startTime;
  private static PlayerStatsDisplay instance;
  private ProgressBar timeBar;
  private static final float MAX_TIME = 300f;
  private float screenWidth = Gdx.graphics.getWidth();
  private float screenHeight = Gdx.graphics.getHeight();
  private static final Logger logger = LoggerFactory.getLogger(GdxGame.class);



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
    ServiceLocator.getDayNightService().getEvents().addListener("newday", PlayerStatsDisplay::updateDay);
    ServiceLocator.getDayNightService().getEvents().addListener("Second", (Long time) -> updateTime(time));
  }

  /**
   * Sets the timer to a specific time and starts counting down
   * @param time the time you want to set the timer to count down from in seconds
   */
  public static void setTimer(long time) { timer = time; startTime = time;}

  /**
   * Sets the label for the day
   * @param label: The label being set
   */
  public static void setDayLabel(Label label) { dayLabel = label; }

  /**
   * Gets the current day label
   * @return The current day label
   */
  public static Label getDayLabel() { return dayLabel; }

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
    table.top().left();  // Position table to the top-left of the screen
    table.setFillParent(true);

    goldTable = new Table();
    goldTable.left();  // Align goldTable to the left

    timerTable = new Table();
    timerTable.center();  // Align timerTable to the center

    table.row();

    String LARGE_LABEL = "large";

    // Timer label for the remaining time in the day
    CharSequence TimerText = String.format("%s", convertDigital(timer));
    setTimerLabel(new Label(TimerText, skin, LARGE_LABEL));

    // Add the timer label to the timerTable
    timerTable.add(getTimerLabel()).center();

    // Center the timer table in the middle of the screen
    table.add(timerTable).expandX().center().padTop(10f);  // Ensure the timer is centered
    table.row();  // New row for day and gold labels

    // Label for the Current Day
    CharSequence dayText = String.format("Day: %d", currentDay); // Start with Day 1
    setDayLabel(new Label(dayText, skin, LARGE_LABEL));

    // Label for Current Gold
    goldImage = new Image(ServiceLocator.getResourceService().getAsset("images/money.png", Texture.class));
    int gold = entity.getComponent(CombatStatsComponent.class).getGold();
    CharSequence goldText = String.format("Cash: %d", gold);
    goldLabel = new Label(goldText, skin, LARGE_LABEL);

    // Add gold and day labels to the table
    table.add(getDayLabel()).left().padLeft(0f).padTop(10f);  // Align day label to the very left
    table.row();
    goldTable.add(goldLabel).left().padLeft(0f);  // Align gold label to the very left
    goldTable.add(goldImage).size(20f).padLeft(5f);  // Pad image slightly to the right of the gold label
    table.add(goldTable).left().padLeft(0f).padTop(10f);  // Align to the very left

    // Add the table to the stage
    stage.addActor(table);

    // Progress bar settings
    Texture whiteBgTexture = ServiceLocator
        .getResourceService().getAsset("images/white_background.png", Texture.class);

    ProgressBar.ProgressBarStyle style = new ProgressBar.ProgressBarStyle();
    style.background = new TextureRegionDrawable(new TextureRegion(whiteBgTexture));
    style.background.setMinHeight(15);
    style.background.setMinWidth(10);

    // Static white background ProgressBar
    timeBar = new ProgressBar(0f, 100f, 1f, false, skin);
    timeBar.setValue(100f);  // Start at full value (100%)
    table.row();
    table.add(timeBar).width(200f).height(300f).fill();
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
  public static void updateDay() {
    currentDay++;
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

    // Calculate progress as a percentage of the time remaining
    float progressPercentage = (float) timer / startTime * 100f;

    // Update the progress bar value to reflect the remaining time
    getInstance().timeBar.setValue(progressPercentage);

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
    float newFontScale = 1.5f;  // Make the timer 1.5x its original size
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


}
