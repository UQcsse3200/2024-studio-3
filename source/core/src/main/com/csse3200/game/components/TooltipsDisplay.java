package com.csse3200.game.components;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
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

    public static class TooltipInfo {
        public final String text;
        public final Vector2 position;

        public TooltipInfo(String text, Vector2 position) {
            this.text = text;
            this.position = position;
        }
    }

    @Override
    public void create() {
        super.create();
        addActors();
        // Now the listener expects a TooltipInfo object
        entity.getEvents().addListener("showTooltip", this::showTooltip);
        entity.getEvents().addListener("hideTooltip", this::hideTooltip);
    }

    private void addActors() {
        table = new Table();// Position the tooltip above the center of the screen
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

    /**
     * Show the tooltip with dynamic positioning.
     * @param tooltipInfo TooltipInfo object containing the text and position.
     */
    private void showTooltip(TooltipInfo tooltipInfo) {
        tooltipLabel.setText(tooltipInfo.text);

        // Get the camera from the stage's viewport
        float viewportHeight = ServiceLocator.getRenderService().getStage().getViewport().getCamera().viewportHeight;

        // Convert world position to screen position using the camera from the viewport
        Vector3 worldPos = new Vector3(tooltipInfo.position.x, tooltipInfo.position.y, 0);
        Vector3 screenPos = ServiceLocator.getRenderService().getStage().getViewport().getCamera().project(worldPos);

        // Set the tooltip position above the interactable object based on viewport height
        table.setPosition(screenPos.x*190, screenPos.y*190 + (viewportHeight * 0.05f));  // Adjust Y position relative to object and screen size

        table.setVisible(false);
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
