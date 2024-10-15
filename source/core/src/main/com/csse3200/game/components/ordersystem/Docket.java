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
 * UI component class for displaying a docketImage and changing its appearance depending on time remaining.
 */
public class Docket extends UIComponent {
    private Skin docketSkin;
    private static final String[] textureNameArray = {"fresh_docket", "mild_docket", "old_docket", "expired_docket"};
    private Image docketImage;
    private int cellHash;
    private final long startTime;
    long totalRecipeTime;
    private boolean isPaused = false;

    /**
     * Constructs a docketImage component and initialises its skin.
     */
    public Docket(long totalRecipeTime) {
        // Initialize components here
        this.docketSkin = new Skin();
        this.docketImage = new Image();
        this.startTime = TimeUtils.millis();
        this.totalRecipeTime = totalRecipeTime / 1000;
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
            docketImage.setDrawable(docketSkin.getDrawable(textureNameArray[0]));
        }
    }

    /**
     * Sets the skin associated with this docketImage.
     * @param skin the skin to be associated with this docketImage.
     */
    public void setSkin(Skin skin) {
        this.docketSkin = skin;
    }

    /**
     * Gets the skin associated with this docket.
     * @return The skin associated with this docket
     */
    public Skin getSkin() { return docketSkin; }
    /**
     * Sets the image associated with this docketImage.
     * @param docketImage the image to be associated with this docketImage.
     */
    public void setDocketImage(Image docketImage) {
        this.docketImage = docketImage;
    }

    /**
     * Gets the image associated with this docket
     * @return the image associated with this docket
     */
    public Image getDocketImage() {
        return docketImage;
    }

    /**
     * Sets the cell hash that uniquely identifies the cell this docketImage is associated with.
     * @param cellHash the unique hash for the cell
     */

    public void setCellHash(int cellHash) {
        this.cellHash = cellHash;
    }

    /**
     * Returns the cell hash associated with this docketImage.
     * @return the cell hash
     */
    public int getCellHash() {
        return cellHash;
    }

    /**
     * Returns the image representing the docketImage.
     * @return the docketImage image
     */
    public Image getImage() {
        return docketImage;
    }

    /**
     * Returns the array of texture names corresponding to the different states of the docketImage.
     * @return an array of texture names
     */
    public String[] getTextureNameArray() {
        return textureNameArray;
    }

    /**
     * Returns the start time of when the docketImage was created.
     *
     * @return the start time in milliseconds
     */
    public long getStartTime() {
        return startTime;
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    public boolean getPaused() {
        return isPaused;
    }

    /**
     * Gets the total recipe time
     * @return the total recipe time
     */
    public long getTotalRecipeTime() {
        return totalRecipeTime;
    }

    /**
     * Sets the total recipe time
     * @param totalRecipeTime: the total recipe time to be set
     */
    public void setTotalRecipeTime(long totalRecipeTime) {
        this.totalRecipeTime = totalRecipeTime;
    }

    /**
     * Updates the texture of the docketImage based on the remaining time before it disposes.
     * As the remaining time decreases, the texture changes to indicate the time state
     * of the docketImage.
     * @param remainingTimeSecs the remaining time in seconds
     */
    public void updateDocketTexture(double remainingTimeSecs) {
        if (isPaused){
            return;
        }

        if (remainingTimeSecs >= this.totalRecipeTime * 0.6) {
            docketImage.setDrawable(docketSkin.getDrawable(textureNameArray[0]));
        } else if (remainingTimeSecs >= this.totalRecipeTime * 0.3) {
            docketImage.setDrawable(docketSkin.getDrawable(textureNameArray[1]));
        } else if (remainingTimeSecs > 0) {
            docketImage.setDrawable(docketSkin.getDrawable(textureNameArray[2]));
        } else {
            docketImage.setDrawable(docketSkin.getDrawable(textureNameArray[3]));
        }
    }

    /**
     * Draws the docketImage component.
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
        return docketImage.getDrawable() != null ? docketImage.getDrawable().toString() : "none";
    }
}