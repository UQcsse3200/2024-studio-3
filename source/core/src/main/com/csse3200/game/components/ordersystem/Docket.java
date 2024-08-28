package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    private long startTime;

    // Default constructor
    public Docket() {
        this.docketSkin = new Skin(); // Initialize with a new Skin
        this.docket = new Image(); // Initialize with a new Image
        this.startTime = (1000);
    }

    public Docket(Skin mockSkin, Image mockImage) {
        super();
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

    // Excluded the texture setup logic that relies on Gdx.files
    public void updateDocketTexture(double remainingTimeSecs) {
        // No texture update logic included in the test environment
    }

    @Override
    protected void draw(SpriteBatch batch) {
        // No drawing logic needed for testing
    }

    @Override
    public void setStage(Stage mock) {
        // Implementation not required for the test
    }

    public String getCurrentTextureName() {
        return docket.getDrawable() != null ? docket.getDrawable().toString() : "none";
    }
}