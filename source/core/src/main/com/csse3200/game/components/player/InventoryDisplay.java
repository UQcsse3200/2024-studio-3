
package com.csse3200.game.components.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.items.ItemComponent;

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
        ItemComponent item = entity.getComponent(InventoryComponent.class).getItemFirst();
        CharSequence itemText = String.format("Current Item: %s", item);
        label = new Label(itemText, skin);
        label.setFontScale(2f);

        table.add(label).pad(5);
        stage.addActor(table);
    }

    private void updateLabel(ItemComponent item) {
        // Update the label with the item information
        String itemText = String.format("Current Item: %s", item != null ? item.getItemName() : "None");
        if (label == null) {
            label = new Label(itemText, skin);
            label.setFontScale(2f);
        } else {
            label.setText(itemText); // Update the label text
            //animation update event trigger
        }

    }

    // Method to update the UI with a new item
    public void update() {
        updateLabel(entity.getComponent(InventoryComponent.class).getItemFirst());
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

    @Override
    public void setStage(Stage mock) {
        
    }
}