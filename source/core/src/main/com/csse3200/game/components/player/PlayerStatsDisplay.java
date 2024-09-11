package com.csse3200.game.components.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;

/**
 * A ui component for displaying player stats, e.g. health.
 */
public class PlayerStatsDisplay extends UIComponent {
  Table table;
  private Image heartImage;
  private Image goldImage;
  private Label healthLabel;
  private Label goldLabel;
  private static Label dayLabel;
  private static int currentday;

  /**
   * Creates reusable ui styles and adds actors to the stage.
   */
  @Override
  public void create() {
    super.create();
    addActors();

    entity.getEvents().addListener("updateHealth", this::updatePlayerHealthUI);
    entity.getEvents().addListener("updateGold", this::updatePlayerGoldUI);
    ServiceLocator.getDayNightService().getEvents().addListener("newday", () -> {
            updateDay();});
  }

  /**
   * Creates actors and positions them on the stage using a table.
   * @see Table for positioning options
   */
  private void addActors() {
    table = new Table();
    table.top().left();
    table.setFillParent(true);
    table.padTop(45f).padLeft(5f);

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
     CharSequence goldText = String.format("Cash: %d", gold);
     goldLabel = new Label(goldText, skin, "large");

     table.add(goldImage).size(heartSideLength).pad(5);
     table.add(goldLabel);
     table.row();

    //Label for the Current Day
    CharSequence dayText = String.format("Day: %d", currentday); // Start with Day 1
    dayLabel = new Label(dayText, skin, "large");
    table.add(dayLabel);
    stage.addActor(table);
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
    CharSequence text = String.format("Gold: %d", gold);
    goldLabel.setText(text);
  }

  //used to update the day
  public static void updateDay() {
    currentday++;
    CharSequence dayText = String.format("Day: %d", currentday);
    dayLabel.setText(dayText);
  }



  @Override
  public void dispose() {
    super.dispose();
    heartImage.remove();
    healthLabel.remove();
    goldImage.remove();
    goldLabel.remove();
  }

  @Override
  public void setStage(Stage mock) {

  }
}
