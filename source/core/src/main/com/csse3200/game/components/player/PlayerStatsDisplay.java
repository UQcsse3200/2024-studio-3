package com.csse3200.game.components.player;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;

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
    ServiceLocator.getDayNightService().getEvents().addListener("newday", PlayerStatsDisplay::updateDay);
    ServiceLocator.getDayNightService().getEvents().addListener("Second", (Long time) -> updateTime(time));
  }

  /**
   * Sets the timer to a specific time and starts counting down
   * @param time the time you want to set the timer to count down from in seconds
   */
  public static void setTimer(long time) { timer = time; }

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
    table.top().left();
    table.setFillParent(true);
    table.padTop(80f).padLeft(20f);

    goldTable = new Table();
    goldTable.left();

    timerTable = new Table();
    timerTable.left().pad(10f);

    //Label for the Current Day
    CharSequence dayText = String.format("Day: %d", currentDay); // Start with Day 1
    String LARGE_LABEL = "large";
    setDayLabel(new Label(dayText, skin, LARGE_LABEL));
    table.add(getDayLabel()).left();
    table.row();

    //Label for Current Gold
    goldImage = new Image(ServiceLocator.getResourceService().getAsset("images/money.png", Texture.class));
    int gold = entity.getComponent(CombatStatsComponent.class).getGold();
    CharSequence goldText = String.format("Cash: %d", gold);
    goldLabel = new Label(goldText, skin, LARGE_LABEL);

    goldTable.add(goldLabel).left();
    goldTable.add(goldImage).size(20f).padLeft(5f);
    table.add(goldTable);
    table.row();

    //Timer image
    timerImage = new Image(ServiceLocator.getResourceService().getAsset("images/hourglass.png", Texture.class));

    // Timer label for the remaining time in the day
    CharSequence TimerText = String.format("%s", convertDigital(timer));
    setTimerLabel(new Label(TimerText, skin, LARGE_LABEL));
    timerTable.add(timerImage).size(20f).left().padRight(5f);
    timerTable.add(getTimerLabel()).left();
    table.add(timerTable).padTop(20f);
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
  public static void updateDay() {
    currentDay++;
    CharSequence dayText = String.format("Day: %d", currentDay);
    getDayLabel().setText(dayText);
  }

  /**
   * Updates the remaining time for the current day on the UI. Decreases the timer by one second
   * and updates the displayed time.
   */

  public static void updateTime(long time) {
    CharSequence timerText = String.format("Time Left: %n   %s", convertDigital(time));
    getTimerLabel().setText(timerText);
    ServiceLocator.getDayNightService().getEvents().trigger("callpastsecond");
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
