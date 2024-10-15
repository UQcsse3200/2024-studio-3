package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A UI component for displaying the Docket Pin Line.
 */
public class DocketLineDisplay extends UIComponent {
	private static final Logger logger = LoggerFactory.getLogger(DocketLineDisplay.class);
	private static final float Z_INDEX = 2f;
	private Table table;
	private Image pinLine;

	public DocketLineDisplay(Skin skin) {
		super(skin);
	}

	public DocketLineDisplay() {
		super(null);  // Use default skin if none is provided
	}

	@Override
	public void create() {
		super.create();
		addActors();
		logger.info("Just created the Docket line display");
	}

	private void addActors() {
		table = new Table();
		table.setFillParent(true);

		pinLine = new Image(
				ServiceLocator.getResourceService().getAsset(
						"images/ordersystem/pin_line2.png", Texture.class));

		pinLine.setWidth(Gdx.graphics.getWidth() * 1f);
		pinLine.setHeight(30);
		pinLine.setPosition(Gdx.graphics.getWidth() * 0f, Gdx.graphics.getHeight() * 0.938f);

		table.add(pinLine);
		stage.addActor(table);
		stage.addActor(pinLine);
		setZIndexToActors();
	}

	private void setZIndexToActors() {
		table.setZIndex((int) getZIndex());
		pinLine.setZIndex((int) getZIndex());
	}

	public void resize() {
		pinLine.remove();
		table.clear();
		addActors();
	}

	@Override
	public void draw(SpriteBatch batch) {
		// Drawing is handled by the stage
	}

	@Override
	public float getZIndex() {
		return Z_INDEX;
	}

	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public Stage getStage() {
		return this.stage;
	}

	public void setPinLine(Image pinLine) {
		this.pinLine = pinLine;
	}

	public Image getPinLine() {
		return pinLine;
	}

	public void setTable(Table table) {
		this.table = table;
	}

	public Table getTable() {
		return table;
	}

	@Override
	public void dispose() {
		table.clear();
		super.dispose();
	}
}
