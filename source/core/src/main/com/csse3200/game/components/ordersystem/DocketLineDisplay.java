package com.csse3200.game.components.ordersystem;

import com.csse3200.game.ui.UIComponent;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class DocketLineDisplay extends UIComponent {
    private Label docketLabel;
    private Table table;

    @Override
    public void create() {
        super.create();
        addActors();
    }

    private void addActors() {
        docketLabel = new Label("Docket Line", skin);

        table = new Table();
        table.setFillParent(true);
        table.add(docketLabel).pad(10);
        table.row();

        stage.addActor(table);
    }

    @Override
    public void draw(SpriteBatch batch) {
    }

    @Override
    public void dispose() {
        super.dispose();
        table.remove();
    }

}
