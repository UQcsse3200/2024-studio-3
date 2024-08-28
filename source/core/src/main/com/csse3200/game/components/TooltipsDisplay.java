package com.csse3200.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;

/**
 * A UI component for displaying tooltips when the player is near an interactable object.
 */
public class TooltipsDisplay extends UIComponent {
    private Table table;
    private Image tooltipBackground;
    private Label tooltipLabel;

    @Override
    public void create() {
        super.create();
        addActors();
        entity.getEvents().addListener("showTooltip", this::showTooltip);
        entity.getEvents().addListener("hideTooltip", this::hideTooltip);
    }

    private void addActors() {
        table = new Table();
        table.top().center();
        table.setFillParent(true);
        table.padTop(200f); // Position the tooltip above the center of the screen
        table.setVisible(false);

        // Add the background image
        tooltipBackground = new Image(ServiceLocator.getResourceService().getAsset("images/tooltip_bg.png", Texture.class));


        float backgroundWidth = tooltipBackground.getWidth() * 0.25f;
        float backgroundHeight = tooltipBackground.getHeight() * 0.25f;

        // Add the tooltip text
        tooltipLabel = new Label("ToolTip", skin, "large");
        tooltipLabel.setFontScale(0.8f);

        // Combine them into the table
        table.add(tooltipBackground).size(backgroundWidth, backgroundHeight);
        table.row();
        table.add(tooltipLabel).padTop(-backgroundHeight);

        stage.addActor(table);
    }

    @Override
    public void draw(SpriteBatch batch) {
        // Drawing is handled by the stage
    }

    private void showTooltip(String tooltipText) {
        tooltipLabel.setText(tooltipText);
        table.setVisible(true);
    }

    private void hideTooltip() {
        tooltipLabel.setText("");
        table.setVisible(false);
    }

    @Override
    public void dispose() {
        super.dispose();
        tooltipLabel.remove();
        tooltipBackground.remove();
    }

    @Override
    public void setStage(Stage mock) {

    }
}
