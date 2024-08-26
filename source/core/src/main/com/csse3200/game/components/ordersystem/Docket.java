package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.utils.TimeUtils;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.math.Vector2;

public class Docket extends UIComponent {
    private Actor actor;
    private Skin docketSkin = new Skin();
    private static String[] textureNameArray = {"fresh_docket", "mild_docket", "old_docket", "expired_docket"};
    private Image docket = new Image();
    private int cellHash;
    private static final long DEFAULT_TIMER = 5000;
    private long startTime = TimeUtils.millis();
    private Vector2 position;
    private boolean visible = true;
    private boolean touchable = true;

    public Docket() {
        actor = new Actor();
        TextureAtlas docketAtlas;
        docketAtlas = new TextureAtlas(Gdx.files.internal("images/ordersystem/DocketStatusIndicator.atlas"));
        docketSkin.addRegions(docketAtlas);
        docket.setDrawable(docketSkin, textureNameArray[0]);

         position = new Vector2(0, 0); // Initial position
    }
    public Vector2 localToStageCoordinates(Vector2 localCoords) {
        // Manually calculate stage coordinates, assuming no parent transformations
        // This is just an example and may not account for all transformations
        return localCoords.add(position);
    }

    public boolean isVisible() {
    return visible;
    }

    public boolean getTouchable() {
        return touchable;
    }

    // You will also need to implement getWidth and getHeight methods if needed
    public float getWidth() {
        return docket.getWidth();
    }

    public float getHeight() {
        return docket.getHeight();
    }

    @Override
    public void create() {
        super.create();
    }
    public void setPosition(float x, float y) {
        this.setPosition(x, y);
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }

    public void addAction(Action action) {
        actor.addAction(action);
    }

    public void act(float delta) {
        actor.act(delta);
    }

    public void draw(SpriteBatch batch) {  // Ensure the signature matches the one required by UIComponent or RenderComponent
    docket.draw(batch, 1f);  // Draw the Image
}

    public Actor hit(float x, float y, boolean touchable) {
    if (!isVisible() || !getTouchable()) return null;
    return x >= 0 && x < getWidth() && y >= 0 && y < getHeight() ? actor : null;  // Return the internal actor or null
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

}
