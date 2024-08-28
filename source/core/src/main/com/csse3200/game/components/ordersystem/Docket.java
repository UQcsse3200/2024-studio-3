package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.ui.UIComponent;

/**
 * UI component class for displaying a docket and changing its appearance depending on time remaining.
 */
public class Docket extends UIComponent {
    private Skin docketSkin;
    private static String[] textureNameArray = {"fresh_docket", "mild_docket", "old_docket", "expired_docket"};
    private Image docket;
    private int cellHash;
    private static final long DEFAULT_TIMER = 5000;
    private long startTime;


    /**
     * Constructs a docket component and initialises its skin.
     */
    public Docket() {
        // Initialize components here
        this.docketSkin = new Skin();
        this.docket = new Image();
        this.startTime = TimeUtils.millis();
        setupSkin();
    }

    /**
     * Sets up UI skin by loading a texture atlas and
     * setting a drawable.
     */
    private void setupSkin() {
        if (Gdx.files != null) {
            TextureAtlas docketAtlas = new TextureAtlas(Gdx.files.internal("images/ordersystem/DocketStatusIndicator.atlas"));
            docketSkin.addRegions(docketAtlas);
            docket.setDrawable(docketSkin.getDrawable(textureNameArray[0]));
        }
    }

    /**
     * Creates the docket component.
     */
    @Override
    public void create() {
        super.create();
    }

    /**
     * Sets the skin associated with this docket.
     * @param skin the skin to be associated with this docket.
     */
    public void setSkin(Skin skin) {
        this.docketSkin = skin;
    }

    /**
     * Sets the image associated with this docket.
     * @param docket the image to be associated with this docket.
     */
    public void setDocket(Image docket) {
        this.docket = docket;
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

    /**
     * Updates the texture of the docket based on the remaining time before it disposes.
     * As the remaining time decreases, the texture changes to indicate the time state
     * of the docket.
     * @param remainingTimeSecs the remaining time in seconds
     */
    public void updateDocketTexture(double remainingTimeSecs) {
        if (remainingTimeSecs <= 3 && remainingTimeSecs >= 2) {
            docket.setDrawable(docketSkin.getDrawable(textureNameArray[1]));
        } else if (remainingTimeSecs <= 2 && remainingTimeSecs >= 1) {
            docket.setDrawable(docketSkin.getDrawable(textureNameArray[2]));
        } else if (remainingTimeSecs <= 1 && remainingTimeSecs >= 0) {
            docket.setDrawable(docketSkin.getDrawable(textureNameArray[3]));
        }
    }

    /**
     * Draws the docket component.
     * @param batch the SpriteBatch used for drawing
     */
    @Override
    protected void draw(SpriteBatch batch) {
        // Draw is handled by stage
    }

    @Override
    public void setStage(Stage mock) {
        // Implementation not required for the test
    }

    /**
     * Retrieves the name of the current drawable's texture.
     * @return the name of the current texture, or "none" if no drawable is set
     */
    public String getCurrentTextureName() {
        return docket.getDrawable() != null ? docket.getDrawable().toString() : "none";
    }
}