package com.csse3200.game.components.player;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;

/**
 * A ui component for displaying player stats, e.g. health.
 */
public class PlayerStatsDisplay extends UIComponent {
  Table table;
  Table goldTable;
  Table timerTable;
  Skin newFont;
  //private ImageTextButton goldText;
  private Image timerImage;
  private Image goldImage;
  private Label goldLabel;
  private static Label dayLabel;
  private static int currentday;
  private static Label timerLabel;
  public static long timer;
  private static String digitaltime;



  /**
   * Creates reusable ui styles and adds actors to the stage.
   */
  @Override
  public void create() {
    super.create();
    addActors();
    timer =  ServiceLocator.getDayNightService().FIVE_MINUTES;

    entity.getEvents().addListener("updateGold", this::updatePlayerGoldUI);
    ServiceLocator.getDayNightService().getEvents().addListener("newday", () -> {
            updateDay();});
    ServiceLocator.getDayNightService().getEvents().addListener("Second", () -> {
      updateTime();});
  }

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
    CharSequence dayText = String.format("Day: %d", currentday); // Start with Day 1
    dayLabel = new Label(dayText, skin, "large");
    table.add(dayLabel).left();
    table.row();

    //Label for Current Gold
    goldImage = new Image(ServiceLocator.getResourceService().getAsset("images/money.png", Texture.class));
    int gold = entity.getComponent(CombatStatsComponent.class).getGold();
    CharSequence goldText = String.format("Cash: %d", gold);
    goldLabel = new Label(goldText, skin, "large");

    goldTable.add(goldLabel).left();
    goldTable.add(goldImage).size(20f).padLeft(5f);
    table.add(goldTable);
    table.row();

    //Timer image
    timerImage = new Image(ServiceLocator.getResourceService().getAsset("images/hourglass.png", Texture.class));

    // Timer label for the remaining time in the day
    CharSequence TimerText = String.format("%s", convertDigital(timer));
    timerLabel = new Label(TimerText, skin, "large");
    timerTable.add(timerImage).size(20f).left().padRight(5f);
    timerTable.add(timerLabel).left();
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
    currentday++;
    CharSequence dayText = String.format("Day: %d", currentday);
    dayLabel.setText(dayText);
  }

  /**
   * Updates the remaining time for the current day on the UI. Decreases the timer by one second
   * and updates the displayed time.
   */
  public static void updateTime() {
    // timer;
    timer -= 1000;
    System.out.println(timer);
    CharSequence TimerText = String.format("%s", convertDigital(timer));
    timerLabel.setText(TimerText);
    ServiceLocator.getDayNightService().getEvents().trigger("callpastsecond");



  }


  @Override
  public void dispose() {
    super.dispose();
    goldImage.remove();
    goldLabel.remove();
    dayLabel.remove();
    timerLabel.remove();
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
