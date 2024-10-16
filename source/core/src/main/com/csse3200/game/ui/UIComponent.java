package com.csse3200.game.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.csse3200.game.rendering.RenderComponent;
import com.csse3200.game.rendering.Renderable;
import com.csse3200.game.services.ServiceLocator;

/**
 * A generic component for rendering onto the UI.
 */
public abstract class UIComponent extends RenderComponent implements Renderable {
  private static final int UI_LAYER = 3;
  protected Skin skin;
  protected Stage stage;

  /**
   * Constructor that allows injecting the Skin object.
   * If no Skin is provided, a default Skin is loaded.
   *
   * @param skin the Skin to use for the component, or null to use the default.
   */
  public UIComponent(Skin skin) {
    this.skin = skin != null ? skin : new Skin(Gdx.files.internal("flat-earth/skin/flat-earth-ui.json"));
  }

  public UIComponent() {
    this(null);
  }

  @Override
  public void create() {
    super.create();
    stage = ServiceLocator.getRenderService().getStage();
  }

  @Override
  public int getLayer() {
    return UI_LAYER;
  }

  @Override
  public float getZIndex() {
    return 1f;
  }
}
