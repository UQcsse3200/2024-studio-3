package com.csse3200.game.components.ordersystem;

import com.csse3200.game.ui.UIComponent;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;


public class DocketLine {
    private DocketLineDisplay docketLineDisplay;

    public DocketLine() {
        docketLineDisplay = new DocketLineDisplay();
        docketLineDisplay.create();
    }

    public void show() {
        docketLineDisplay.draw(null);
    }

    public void dispose() {
        docketLineDisplay.dispose();
    }
}
