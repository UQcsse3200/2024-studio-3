package com.csse3200.game.components.ScoreSystem;

import com.badlogic.gdx.graphics.Texture;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.csse3200.game.rendering.RenderComponent;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.components.npc.CustomerComponent;

public class HoverBoxComponent extends RenderComponent {
    private static final Logger logger = LoggerFactory.getLogger(HoverBoxComponent.class);
    private Texture hoverImage;
    private ShapeRenderer shapeRenderer;
    private Vector2 position;
    private Vector2 scale;
    private static final float X_OFFSET = 0.45f;
    private static final float Y_OFFSET = 1.0F;

    public HoverBoxComponent(Texture contentImage) {
        hoverImage = contentImage;
    }

    public void setTexture(Texture newTexture) {
        this.hoverImage = newTexture;
    }

    @Override
    public void create() {
        super.create();
        shapeRenderer = new ShapeRenderer();
        /*
        try {
            hoverImage = new Texture("images/customer_faces/angry_face.png");
        } catch (Exception e) {
            System.err.println("Failed to load hover box image: " + e.getMessage());
        }
        */

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
        final float boxHeight = 0.5f;

        String entityInfo = getEntityInfo();

        // System.out.println("Drawing HoverBox for " + entityInfo
        //         + " at (" + position.x + "," + position.y + ")"
        //         + " with scale (" + scale.x + "," + scale.y + ")");

        batch.draw(hoverImage,
                position.x + X_OFFSET,
                position.y + Y_OFFSET,
                boxWidth,
                boxHeight);

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
    }

    @Override
    public int getLayer() {
        return 3; // Adjust this value to ensure the box renders above the NPC
    }

    @Override
    public void setStage(Stage mock) {

    }
}