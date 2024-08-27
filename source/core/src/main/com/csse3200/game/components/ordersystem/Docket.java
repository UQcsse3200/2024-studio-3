package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;

public class Docket extends UIComponent {
    private Skin docketSkin = new Skin();
    private static String[] textureNameArray = {"fresh_docket", "mild_docket", "old_docket", "expired_docket"};
    private Image docket = new Image();
    private int cellHash;
    private static final long DEFAULT_TIMER = 5000;
    private long startTime = TimeUtils.millis();

    public Docket() {
        TextureAtlas docketAtlas;
        docketAtlas = new TextureAtlas(Gdx.files.internal("images/ordersystem/DocketStatusIndicator.atlas"));
        docketSkin.addRegions(docketAtlas);
        docket.setDrawable(docketSkin, textureNameArray[0]);
    }

    @Override
    public void create() {
        super.create();
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

    public void updateDocketTexture(double remainingTimeSecs) {
        if (remainingTimeSecs <= 3 && remainingTimeSecs >= 2){
            docket.setDrawable(docketSkin, textureNameArray[1]);
        } else if (remainingTimeSecs <= 2 && remainingTimeSecs >= 1) {
            docket.setDrawable(docketSkin, textureNameArray[2]);
        } else if (remainingTimeSecs <= 1 && remainingTimeSecs >= 0) {
            docket.setDrawable(docketSkin, textureNameArray[3]);
        }
    }

    @Override
    protected void draw(SpriteBatch batch) {
        //Do not need to do anything here :)
    }

    @Override
    public void setStage(Stage mock) {

    }
}
