package com.csse3200.game.rendering;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.csse3200.game.services.ServiceLocator;

/** Render a static texture. */
public class TextureRenderComponent extends RenderComponent {
  private static final int IMAGE_LAYER = 2;
  private Texture texture;
  private String texturePath;

  /**
   * @param texturePath Internal path of static texture to render.
   *                    Will be scaled to the entity's scale.
   */
  public TextureRenderComponent(String texturePath) {
    this(ServiceLocator.getResourceService().getAsset(texturePath, Texture.class));
    this.texturePath = texturePath;
  }

  /** @param texture Static texture to render. Will be scaled to the entity's scale. */
  public TextureRenderComponent(Texture texture) {
    this.texture = texture;
  }

  public String getTexturePath() {
    return texturePath;
  }

  /**
   * Updates the texture with a new one.
   * @param texturePath Internal path of the new texture to render.
   */

  public void setTexture (String texturePath) {

    /**
     * Dispose of the current texture
     */
    if (texture != null) {
      texture.dispose();
    }
    /**
     * Loading and setting the new texture.
     */
    this.texture = ServiceLocator.getResourceService().getAsset(texturePath, Texture.class);
    this.texturePath = texturePath;

    /**
     * Scaling the entity based on the new texture.
     */
    scaleEntity();
  }

  /** Scale the entity to a width of 1 and a height matching the texture's ratio */
  public void scaleEntity() {
    entity.setScale(1f, (float) texture.getHeight() / texture.getWidth());
  }

  /**
   * Gets the width of the texture
   * @return the width of the texture
   */
  public float getWidth() {
    return texture.getWidth();
  }

  public Texture getTexture() {
    return this.texture;
  }

  /**
   * Gets the height of the texture
   * @return the height of the texture
   */
  public float getHeight() {
    return texture.getHeight();
  }

  @Override
  protected void draw(SpriteBatch batch) {
    Vector2 position = entity.getPosition();
    Vector2 scale = entity.getScale();
    batch.draw(texture, position.x, position.y, scale.x, scale.y);
  }

  @Override
  public void setStage(Stage mock) {

  }

  @Override
  public int getLayer() {
    return IMAGE_LAYER;
  }
}
