package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.csse3200.game.ui.UIComponent;

public class Docket extends UIComponent {
    private Skin docketSkin;
    private String[] textureNameArray;
    private Image docket;

    @Override
    public void create() {
        super.create();
        docketSkin = new Skin();
        textureNameArray = new String[4];
        textureNameArray[0] = "fresh_docket";
        textureNameArray[1] = "mild_docket";
        textureNameArray[2] = "old_docket";
        textureNameArray[3] = "expired_docket";
        TextureAtlas docketAtlas;
        docketAtlas = new TextureAtlas(Gdx.files.internal("images/ordersystem/DocketStatusIndicator.atlas"));
        docketSkin.addRegions(docketAtlas);
        docket = new Image(docketSkin, textureNameArray[0]);
    }

    public Image returnImage() {
        return docket;
    }

    @Override
    protected void draw(SpriteBatch batch) {
        //Do not need to do anything here :)
    }
}
