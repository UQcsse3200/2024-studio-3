package com.csse3200.game.components.ScoreSystem;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.rendering.RenderComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.components.npc.CustomerComponent;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;

public class HoverBoxComponent extends RenderComponent {
    private ShapeRenderer shapeRenderer;
    private BitmapFont font;
    private final float width = 50f;
    private final float height = 30f;
    private final float yOffset = 40f;
    private Vector2 position;
    private Vector2 scale;
    private static final float X_OFFSET = 0.45f;
    private static final float Y_OFFSET = 1.0F;
    private static final float FONT_SCALE = 0.05f;

    @Override
    public void create() {
        super.create();
        shapeRenderer = new ShapeRenderer();
        font = new BitmapFont(); // Initialize the font
        font.getData().setScale(FONT_SCALE); // Set the scale of the font
        ServiceLocator.getRenderService().register(this);
    }

    @Override
    public void update() {
        super.update();
        if (entity != null) {
            position = entity.getPosition();
            scale = entity.getScale();
        }
    }

    @Override
    public void draw(SpriteBatch batch) {
        if (entity == null || position == null || scale == null)
            return;

        if (position.x == 0 && position.y == 0) {
            return; // Skip drawing if the entity is at (0,0)
        }

        final float boxWidth = 0.5f;
        final float boxHeight = 0.1f;

        String entityInfo = getEntityInfo();
        System.out.println("Drawing HoverBox for " + entityInfo
                + " at (" + position.x + "," + position.y + ")"
                + " with scale (" + scale.x + "," + scale.y + ")");

        // float boxWidth = width * scale.x;
        // float boxHeight = height * scale.y;
        // float actualYOffset = yOffset * scale.y;

        // We need to end the SpriteBatch to use ShapeRenderer
        batch.end();

        shapeRenderer.setProjectionMatrix(batch.getProjectionMatrix());
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Draw red outline for visibility
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(position.x + X_OFFSET, position.y + Y_OFFSET, boxWidth, boxHeight);

        shapeRenderer.end();

        // Resume the SpriteBatch
        batch.begin();

        // Draw the text above the NPC
        String text = "42"; // Example number to display
        GlyphLayout layout = new GlyphLayout(font, text); // Measure the dimensions of the text
        float textX = position.x + X_OFFSET;
        float textY = position.y + Y_OFFSET;

        font.draw(batch, layout, textX, textY);

    }

    private String getEntityInfo() {
        String entityId = entity.toString(); // This gives us "Entity{id=XXX}"
        CustomerComponent customerComponent = entity.getComponent(CustomerComponent.class);
        if (customerComponent != null) {
            String name = customerComponent.getName();
            return name != null && !name.isEmpty() ? entityId + " (Customer: " + name + ")"
                    : entityId + " (Unnamed Customer)";
        }
        return entityId + " (Not a Customer)";
    }

    @Override
    public void dispose() {
        super.dispose();
        ServiceLocator.getRenderService().unregister(this);
        if (shapeRenderer != null) {
            shapeRenderer.dispose();
        }
        if (font != null) {
            font.dispose();
        }
    }

    @Override
    public int getLayer() {
        return 3; // Adjust this value to ensure the box renders above the NPC
    }

    @Override
    public void setStage(Stage mock) {

    }
}