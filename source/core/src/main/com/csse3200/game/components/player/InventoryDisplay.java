
package com.csse3200.game.components.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.components.player.InventoryComponent;

public class InventoryDisplay extends UIComponent {
    private Table table;
    private Label label;

    @Override
    public void create() {
        super.create();
        addActors();
    }

    private void addActors() {
        // Create table
        table = new Table();
        table.bottom().left();
        table.padTop(30f).padLeft(10f);

        // Add an image
        String item = entity.getComponent(InventoryComponent.class).getItemFirst();
        CharSequence itemText = String.format("Current Item: %s", item);
        label = new Label(itemText, skin);
        label.setFontScale(2f);

        table.add(label).pad(5);
        stage.addActor(table);
    }

    @Override
    public void draw(SpriteBatch batch)  {
        // changing position on window resizing is handled by the stage
    }

    @Override
    public void dispose() {
        super.dispose();
        label.remove();
    }
}