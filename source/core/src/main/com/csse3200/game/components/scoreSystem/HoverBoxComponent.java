package com.csse3200.game.components.scoreSystem;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.rendering.RenderComponent;
import com.csse3200.game.services.ServiceLocator;

public class HoverBoxComponent extends RenderComponent {
    private ShapeRenderer shapeRenderer;
    private final float width = 50f; // Width of the box
    private final float height = 30f; // Height of the box
    private final float yOffset = 40f; // How far above the NPC the box should appear

    @Override
    public void create() {
        super.create();
        shapeRenderer = new ShapeRenderer();
        ServiceLocator.getRenderService().register(this);
    }

    @Override
    public void update() {
        super.update();
        // Add any necessary update logic here
    }

    @Override
    protected void draw(SpriteBatch batch) {

        if (!batch.isDrawing()) {
            return; // If the batch isn't drawing, we can't render
        }

        Vector2 position = entity.getPosition();
        Vector2 scale = entity.getScale();

        if (position.x == 0 && position.y == 0) {
            System.out.println("Skipping draw for Entity at (0,0), Entity type: " + entity.getClass().getSimpleName());
            return; // Skip drawing if the entity is at (0,0)
        }

        System.out.println("Drawing HoverBox for " + entity.getClass().getSimpleName() + " at " + position);

        float boxWidth = 50f * scale.x;
        float boxHeight = 20f * scale.y;
        float yOffset = 50f * scale.y;

        // End the SpriteBatch to use ShapeRenderer
        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.RED); // Use a visible color for debugging
        shapeRenderer.rect(
                position.x - boxWidth / 2,
                position.y + yOffset,
                boxWidth,
                boxHeight);
        shapeRenderer.end();

        // Resume the SpriteBatch
        batch.begin();

    }

    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getRenderService().unregister(this);
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
    }

    @Override
    public int getLayer() {
        return 100; // Adjust this value to ensure the box renders above the NPC
    }

    @Override
    public void setStage(Stage mock) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setStage'");
    }

}