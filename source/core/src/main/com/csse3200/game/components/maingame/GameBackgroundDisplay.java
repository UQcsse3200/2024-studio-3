package com.csse3200.game.components.maingame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Displays the background for the main game area.
 */
public class GameBackgroundDisplay extends UIComponent {
    private Table table;
    private static final Logger logger = LoggerFactory.getLogger(GameBackgroundDisplay.class);
    protected float timePerFrame = 300/40f; //seconds spent on each frame (assuming
    // 5min day)
    private boolean lastFrame;
    protected long timeSinceLastUpdate;
    protected String currentImage;
    protected int currentImageIndex;
    protected static final String[] BACKGROUND_TEXTURES = {"images/background_images/1.0.png",
            "images/background_images/1.5.png",
            "images/background_images/2.0.png",
            "images/background_images/2.5.png",
            "images/background_images/3.0.png",
            "images/background_images/3.5.png",
            "images/background_images/4.0.png",
            "images/background_images/4.5.png",
            "images/background_images/5.0.png",
            "images/background_images/5.5.png",
            "images/background_images/6.0.png",
            "images/background_images/6.5.png",
            "images/background_images/7.0.png",
            "images/background_images/7.5.png",
            "images/background_images/8.0.png",
            "images/background_images/8.5.png",
            "images/background_images/9.0.png",
            "images/background_images/9.5.png",
            "images/background_images/10.0.png",
            "images/background_images/10.5.png",
            "images/background_images/11.0.png",
            "images/background_images/11.5.png",
            "images/background_images/12.0.png",
            "images/background_images/12.5.png",
            "images/background_images/13.0.png",
            "images/background_images/13.5.png",
            "images/background_images/14.0.png",
            "images/background_images/14.5.png",
            "images/background_images/15.0.png",
            "images/background_images/15.5.png",
            "images/background_images/16.0.png",
            "images/background_images/16.5.png",
            "images/background_images/17.0.png",
            "images/background_images/17.5.png",
            "images/background_images/18.0.png",
            "images/background_images/18.5.png"};




    public GameBackgroundDisplay() {
        super();
        this.lastFrame = false;
        this.currentImage = "images/background_images/1.0.png";
        this.currentImageIndex = 0;
        this.timeSinceLastUpdate = ServiceLocator.getTimeSource().getTime();
    }

    public String getCurrentImage() {
        return this.currentImage;
    }

    public Table getTable() {
        return this.table;
    }

    public void setTable(Table table) {
        this.table = table;
    }

    @Override
    public void create() {
        super.create();
        Gdx.gl.glClearColor(0f/255f, 0f/255f, 0f/255f, 1);
        table = new Table();
        table.setFillParent(true);
        table.setVisible(true);
        stage.addActor(table);

        setBackground();
    }

    /**
     * sets the background with the image currently stored in currentImage member variable
     */
    public void setBackground() {
        Texture texture = ServiceLocator.getResourceService().getAsset(currentImage,
                Texture.class);
        Image image = new Image(texture);
        image.setFillParent(true);
        table.add(image);
    }

    @Override
    protected void draw(SpriteBatch batch) {
        // draw is handled
    }

    @Override
    public void setStage(Stage mock) {
        // setStage is handled
    }

    /**
     * Updates the background image to the next frame in the cycle
     */
    @Override
    public void update() {
        if (!lastFrame) {
            long elapsedTime = ServiceLocator.getTimeSource().getTimeSince(timeSinceLastUpdate);
            long elapsedTimeSecs = elapsedTime/1000;
            //if time to update
            if (elapsedTimeSecs >= this.timePerFrame) {
                this.currentImageIndex++;
                this.currentImage = BACKGROUND_TEXTURES[currentImageIndex];
                this.timeSinceLastUpdate = ServiceLocator.getTimeSource().getTime();
                table.clear(); //clears current background
                setBackground(); //sets background to new image
                String message = "Updated background to " + this.currentImage;
                logger.debug(message);
                if (currentImageIndex >= 35) {
                    this.lastFrame = true; //stops updating when hits last frame of cycle
                }
            }
        }
    }

}
