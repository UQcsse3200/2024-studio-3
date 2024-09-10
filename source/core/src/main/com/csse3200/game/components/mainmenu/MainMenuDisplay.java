package com.csse3200.game.components.mainmenu;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An ui component for displaying the Main menu.
 */
public class MainMenuDisplay extends UIComponent {
  private static final Logger logger = LoggerFactory.getLogger(MainMenuDisplay.class);
  private static final float Z_INDEX = 2f;
  private Table table;
  private Table logo;
  private float scale_of_button = 1.5f;

  @Override
  public void create() {
    super.create();
    addActors();
  }

  /**
   * Adds the main title screen elements and buttons.
   * */
  private void addActors() {
    logo = new Table();
    logo.setFillParent(true);
    table = new Table();
    table.setFillParent(true);
    Image title =
        new Image(
            ServiceLocator.getResourceService()
                .getAsset("images/Beastly.png", Texture.class));

    ImageTextButton startBtn = new ImageTextButton("Start", skin);
    ImageTextButton loadBtn = new ImageTextButton("Load", skin);
    ImageTextButton settingsBtn = new ImageTextButton("Settings", skin);
    ImageTextButton exitBtn = new ImageTextButton("Exit", skin);
    startBtn.setTransform(true);
    startBtn.setScale(scale_of_button);
    loadBtn.setTransform(true);
    loadBtn.setScale(scale_of_button);
    settingsBtn.setTransform(true);
    settingsBtn.setScale(scale_of_button);
    exitBtn.setTransform(true);
    exitBtn.setScale(scale_of_button);

      // Triggers an event when the button is pressed
    startBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Start button clicked");
            entity.getEvents().trigger("start");
          }
        });

    loadBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Load button clicked");
            entity.getEvents().trigger("load");
          }
        });

    settingsBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Settings button clicked");
            entity.getEvents().trigger("settings");
          }
        });

    exitBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Exit button clicked");
            entity.getEvents().trigger("exit");
          }
        });

    // Add logo and buttons
    logo.add(title).pad(0,0,250,0);
    table.add(startBtn).pad(600, 0, 0, 0).height(60);
    table.add(loadBtn).pad(600, 95, 0, 0).height(60);
    table.add(settingsBtn).pad(600, 90, 0, 0).height(60);
    table.add(exitBtn).pad(600, 120, 0, 0).height(60);
    table.center();

    // Render logo and buttons
    stage.addActor(logo);
    stage.addActor(table);
  }

  @Override
  public void draw(SpriteBatch batch) {
    // draw is handled by the stage
  }

  @Override
  public float getZIndex() {
    return Z_INDEX;
  }

    @Override
    public void setStage(Stage mock) {

    }

    @Override
  public void dispose() {
    table.clear();
    super.dispose();
  }
}
