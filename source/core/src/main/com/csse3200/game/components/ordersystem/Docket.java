package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;

/**
 * UI component class for displaying a docket and changing its appearance depending on time remaining.
 */
public class Docket extends UIComponent {
    private Skin docketSkin = new Skin();
    private static String[] textureNameArray = {"fresh_docket", "mild_docket", "old_docket", "expired_docket"};
    private Image docket = new Image();
    private int cellHash;
    private static final long DEFAULT_TIMER = 5000;
    private long startTime = TimeUtils.millis();

    /**
     * Constructs a docket component and initialises its skin.
     */
    public Docket() {
        TextureAtlas docketAtlas;
        docketAtlas = new TextureAtlas(Gdx.files.internal("images/ordersystem/DocketStatusIndicator.atlas"));
        docketSkin.addRegions(docketAtlas);
        docket.setDrawable(docketSkin, textureNameArray[0]);
    }

    /**
     * Creates the docket component.
     */
    @Override
    public void create() {
        super.create();
    }

    /**
     * Sets the cell hash that uniquely identifies the cell this docket is associated with.
     * @param cellHash the unique hash for the cell
     */
    public void setCellHash(int cellHash) {
        this.cellHash = cellHash;
    }

    /**
     * Returns the cell hash associated with this docket.
     * @return the cell hash
     */
    public int getCellHash() {
        return cellHash;
    }

    /**
     * Returns the image representing the docket.
     * @return the docket image
     */
    public Image getImage() {
        return docket;
    }

    /**
     * Returns the array of texture names corresponding to the different states of the docket.
     * @return an array of texture names
     */
    public String[] getTextureNameArray() {
        return textureNameArray;
    }

    /**
     * Returns the skin object containing the docket textures.
     * @return the docket skin
     */
    public Skin getDocketSkin() {
        return docketSkin;
    }

    /**
     * Returns the start time of when the docket was created.
     *
     * @return the start time in milliseconds
     */
    public long getStartTime() {
        return startTime;
    }

    /*@Override
    public void update() {
        long elapsedTime = TimeUtils.timeSinceMillis(getStartTime()); //inspired by services/GameTime
        long remainingTime = DEFAULT_TIMER - elapsedTime; //inspired by services/GameTime
        double remainingTimeSecs = remainingTime / 1000;
        if (remainingTime > 0) {
            //countdownLabel.setText("Timer: " + (remainingTime / 1000));
            updateDocketTexture(remainingTimeSecs);
        } else {
            ServiceLocator.getDocketService().getEvents().trigger("removeDocket", this);
        }
    }*/

    /**
     * Updates the texture of the docket based on the remaining time before it disposes.
     * As the remaining time decreases, the texture changes to indicate the time state
     * of the docket.
     * @param remainingTimeSecs the remaining time in seconds
     */
    public void updateDocketTexture(double remainingTimeSecs) {
        if (remainingTimeSecs <= 3 && remainingTimeSecs >= 2){
            docket.setDrawable(docketSkin, textureNameArray[1]);
        } else if (remainingTimeSecs <= 2 && remainingTimeSecs >= 1) {
            docket.setDrawable(docketSkin, textureNameArray[2]);
        } else if (remainingTimeSecs <= 1 && remainingTimeSecs >= 0) {
            docket.setDrawable(docketSkin, textureNameArray[3]);
        }
    }

    /**
     * Draws the docket component.
     * @param batch the SpriteBatch used for drawing
     */
    @Override
    protected void draw(SpriteBatch batch) {
        //Do not need to do anything here :)
    }
}
