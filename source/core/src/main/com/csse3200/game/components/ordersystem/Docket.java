package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.ui.UIComponent;

public class Docket extends UIComponent {
    private Skin docketSkin;
    private static String[] textureNameArray = {"fresh_docket", "mild_docket", "old_docket", "expired_docket"};
    private Image docket;
    private int cellHash;
    private static final long DEFAULT_TIMER = 5000;
    private long startTime;

    // Default constructor
    public Docket() {
        // Initialize components here
        this.docketSkin = new Skin();
        this.docket = new Image();
        this.startTime = TimeUtils.millis();
        setupSkin();
    }

    // Initialize the Skin and Image
    private void setupSkin() {
        if (Gdx.files != null) {
            TextureAtlas docketAtlas = new TextureAtlas(Gdx.files.internal("images/ordersystem/DocketStatusIndicator.atlas"));
            docketSkin.addRegions(docketAtlas);
            docket.setDrawable(docketSkin.getDrawable(textureNameArray[0]));
        }
    }

    @Override
    public void create() {
        super.create();
    }

    // Setter for testing
    public void setSkin(Skin skin) {
        this.docketSkin = skin;
    }

    // Setter for testing
    public void setDocket(Image docket) {
        this.docket = docket;
    }

    public void setCellHash(int cellHash) {
        this.cellHash = cellHash;
    }

    public int getCellHash() {
        return cellHash;
    }

    public Image getImage() {
        return docket;
    }

    public String[] getTextureNameArray() {
        return textureNameArray;
    }

    public Skin getDocketSkin() {
        return docketSkin;
    }

    public long getStartTime() {
        return startTime;
    }

    public void updateDocketTexture(double remainingTimeSecs) {
        if (remainingTimeSecs <= 3 && remainingTimeSecs >= 2) {
            docket.setDrawable(docketSkin.getDrawable(textureNameArray[1]));
        } else if (remainingTimeSecs <= 2 && remainingTimeSecs >= 1) {
            docket.setDrawable(docketSkin.getDrawable(textureNameArray[2]));
        } else if (remainingTimeSecs <= 1 && remainingTimeSecs >= 0) {
            docket.setDrawable(docketSkin.getDrawable(textureNameArray[3]));
        }
    }

    @Override
    protected void draw(SpriteBatch batch) {
        // Do not need to do anything here :)
    }

    @Override
    public void setStage(Stage mock) {
        // Implementation not required for the test
    }

    // Method to get the current texture name for testing
    public String getCurrentTextureName() {
        return docket.getDrawable() != null ? docket.getDrawable().toString() : "none";
    }
}
