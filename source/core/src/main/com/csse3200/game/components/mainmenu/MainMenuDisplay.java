package com.csse3200.game.components.mainmenu;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.Random;
import java.util.TimerTask;

/**
 * An ui component for displaying the Main menu.
 */
public class MainMenuDisplay extends UIComponent {
  private static final Logger logger = LoggerFactory.getLogger(MainMenuDisplay.class);
  private static final float Z_INDEX = 2f;
  private Table table;
  private Table logo;
  private float scale_of_button = 1.5f;

  private float startX=0,startY=0,endX=500,endY=500,duration=8,degrees=1000,rotate_duration=10;

  private int number_of_animals=10;
  private Image animals[]=new Image[number_of_animals];
  private String list_of_animals[]={"thegoat","goos","gorill","monki","wolf","raw_acai","raw_banana","chopped_strawb","chopped_chocolate","salad"};
  private int animal_on_screen=2;


  private float width_of_screen=Gdx.graphics.getWidth(),height_of_screen=Gdx.graphics.getHeight();

  private Timer.Task animalMoveTask,clearstage;

  @Override
  public void create() {
    super.create();
    animalMoveTask = new Timer.Task() {
      public void run() {
        background();
        addActors();
      }
    };
    Timer.schedule(animalMoveTask,0,2);
    clearstage = new Timer.Task() {
      public void run() {
        Array<Actor> actors=stage.getActors();
        for (Actor actor : actors) {
          if(actor.getX()<-230 || actor.getX()>width_of_screen+230 || actor.getY()<-230 || actor.getY()>height_of_screen+230){
            actor.remove();
          }
        }
      }
    };
    Timer.schedule(clearstage,0,6);
  }

  public int randomGenerator(int min,int max) {
    Random rand = new Random();
    return rand.nextInt(max-min+1)+min;
  }

  public void background(){


    float height,width;
    for(int x=1;x<=animal_on_screen;x++) {
      //random start and end position
      int random=randomGenerator(0,3);
      if(random==0) // start from left
      {
        startX=-250;
        startY=randomGenerator(0,(int)height_of_screen);
        endX=width_of_screen+250;
        endY=randomGenerator(0,(int)height_of_screen);
      }
      else if(random==1) // start from bottom
      {
        startX=randomGenerator(0,(int)width_of_screen);
        startY=-250;
        endX=randomGenerator(0,(int)width_of_screen);
        endY=height_of_screen+250;
      }
      else if(random==2) // start from right
      {
        startX=width_of_screen+250;
        startY=randomGenerator(0,(int)height_of_screen);
        endX=-250;
        endY=randomGenerator(0,(int)height_of_screen);
      }
      else if(random==3) // start from top
      {
        startX=randomGenerator(0,(int)width_of_screen);
        startY=height_of_screen+250;
        endX=randomGenerator(0,(int)width_of_screen);
        endY=-250;
      }

      random=randomGenerator(1,number_of_animals)-1;
      Image actor=new Image(ServiceLocator.getResourceService().getAsset("images/main_menu_animals/"+list_of_animals[random]+".png",Texture.class));;
      actor.addAction(Actions.parallel(Actions.moveTo(endX,endY,duration),Actions.rotateBy(degrees,rotate_duration)));
      actor.setPosition(startX,startY);
      height=actor.getHeight();
      width=actor.getWidth();
      actor.setOrigin(width/2,height/2);
      actor.setScale((float)(width_of_screen*2.6/10000 + 0.17), (float)(height_of_screen*4.16/10000 + 0.17));
      actor.toBack();
      stage.addActor(actor);
    }
    stage.act();
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
            animalMoveTask.cancel();
            entity.getEvents().trigger("start");
          }
        });

    loadBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Load button clicked");
            animalMoveTask.cancel();
            entity.getEvents().trigger("load");
          }
        });

    settingsBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Settings button clicked");
            animalMoveTask.cancel();
            entity.getEvents().trigger("settings");
          }
        });

    exitBtn.addListener(
        new ChangeListener() {
          @Override
          public void changed(ChangeEvent changeEvent, Actor actor) {
            logger.debug("Exit button clicked");
            animalMoveTask.cancel();
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
