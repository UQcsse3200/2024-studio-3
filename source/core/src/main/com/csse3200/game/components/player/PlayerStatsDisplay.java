package com.csse3200.game.components.player;

import java.util.concurrent.TimeUnit;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.PixmapTextureData;
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
  private Image heartImage;
  private Image goldImage;
  private Label healthLabel;
  private Label goldLabel;
  private static Label dayLabel;
  private static int currentday;
  private static Label timerLabel;
  public static long timer;
  private static String digitaltime;
  private float width_of_screen=Gdx.graphics.getWidth(),height_of_screen=Gdx.graphics.getHeight();


  /**
   * Creates reusable ui styles and adds actors to the stage.
   */
  @Override
  public void create() {
    super.create();
    addActors();
    timer =  ServiceLocator.getDayNightService().FIVE_MINUTES;
  


    entity.getEvents().addListener("updateHealth", this::updatePlayerHealthUI);
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
//    table.setSize(200f,150f);
    table.padTop(45f).padLeft(5f);
//    table.setPosition(5,height_of_screen - (table.getHeight()*2));

    goldTable = new Table();
    goldTable.bottom().left();
    goldTable.setFillParent(true);
    goldTable.padBottom(45f).padLeft(100f);

    timerTable = new Table();
//    timerTable.bottom().right();
    timerTable.setSize(200f,150f);
    timerTable.setPosition(width_of_screen - timerTable.getWidth(), 100f);

    Texture texture = new Texture(Gdx.files.internal("images/component_background.png"));
    TextureRegionDrawable drawable = new TextureRegionDrawable(new TextureRegion(texture));
    timerTable.setBackground(drawable);

    // Heart image
    float heartSideLength = 30f;
    heartImage = new Image(ServiceLocator.getResourceService().getAsset("images/heart.png", Texture.class));

    // Health text
    int health = entity.getComponent(CombatStatsComponent.class).getHealth();
    CharSequence healthText = String.format("Health: %d", health);
    healthLabel = new Label(healthText, skin, "large");

    table.add(heartImage).size(heartSideLength).pad(5);
    table.add(healthLabel);
    table.row();

     goldImage = new Image(ServiceLocator.getResourceService().getAsset("images/money.png", Texture.class));
     int gold = entity.getComponent(CombatStatsComponent.class).getGold();
     CharSequence goldText = String.format("%d", gold);
     goldLabel = new Label(goldText, skin);

     goldTable.add(goldImage).size(heartSideLength).pad(5);
     goldTable.add(goldLabel);
     stage.addActor(goldTable);
     goldTable.row();

    //Label for the Current Day
    CharSequence dayText = String.format("Day: %d", currentday); // Start with Day 1
    dayLabel = new Label(dayText, skin, "large");
    table.add(dayLabel).colspan(2);
    stage.addActor(table);
    table.row();

    //Timer image
    timerImage = new Image(ServiceLocator.getResourceService().getAsset("images/hourglass.png", Texture.class));

    // Timer label for the remaining time in the day
    CharSequence TimerText = String.format("\n   %s", convertDigital(timer));
    timerLabel = new Label(TimerText, skin, "large");
    timerLabel.setFontScale(0.65f);

    timerTable.add(timerImage).size(heartSideLength);
    timerTable.add(timerLabel).padRight(0.5f).padBottom(20f);

    stage.addActor(timerTable);
  }

  @Override
  public void draw(SpriteBatch batch)  {
    // draw is handled by the stage
  }

  /**
   * Updates the player's health on the ui.
   * @param health player health
   */
  public void updatePlayerHealthUI(int health) {
    CharSequence text = String.format("Health: %d", health);
    healthLabel.setText(text);
  }

  /**
   * Updates the player's gold on the ui.
   * @param gold player gold
   */
  public void updatePlayerGoldUI(int gold) {
    CharSequence text = String.format("%d", gold);
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
    CharSequence TimerText = String.format("\n   %s", convertDigital(timer));
    timerLabel.setText(TimerText);
    ServiceLocator.getDayNightService().getEvents().trigger("callpastsecond");



  }


  @Override
  public void dispose() {
    super.dispose();
    heartImage.remove();
    healthLabel.remove();
    goldImage.remove();
    goldLabel.remove();
    dayLabel.remove();
    timerLabel.remove();
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
