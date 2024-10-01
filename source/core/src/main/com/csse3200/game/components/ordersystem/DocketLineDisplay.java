package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.csse3200.game.components.mainmenu.MainMenuDisplay;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * A UI component for displaying the Docket Pin Line
 */
public class DocketLineDisplay extends UIComponent {
	private static final Logger logger = LoggerFactory.getLogger(DocketLineDisplay.class);
	private static final float Z_INDEX = 2f;
	private Table table;
	private Image pinLine;


	/**
	 * Initializes the DocketLineDisplay component by creating and adding its actors to the stage.
	 */
	@Override
	public void create() {
		super.create();
		addActors();
	}

	/**
	 * Adds the actors to the table and sets up the layout.
	 */
	private void addActors() {
		table = new Table();table.setFillParent(true);

		pinLine =
				new Image(
						ServiceLocator.getResourceService()
								.getAsset("images/ordersystem/pin_line.png", Texture.class));


		pinLine.setWidth(Gdx.graphics.getWidth() * 0.7958f);
		pinLine.setHeight(30);
		pinLine.setPosition(Gdx.graphics.getWidth() * 0.128f, Gdx.graphics.getHeight() * 0.938f);


		table.add(pinLine);
		stage.addActor(table);
		stage.addActor(pinLine);
		table.setZIndex((int)getZIndex());
		pinLine.setZIndex((int)getZIndex());
	}

	/**
	 * Resizes the docket line display by removing image, clearing table and re-adding actors.
	 */
	public void resize() {
		pinLine.remove();
		table.clear();
		addActors();
	}

	/**
	 * Draws the DocketLineDisplay component.
	 * @param batch the SpriteBatch used for drawing
	 */
	@Override
	public void draw(SpriteBatch batch) {
		// draw is handled by the stage
	}

	/**
	 * Returns the Z-index of the DocketLineDisplay component.
	 * @return the Z-index of the component
	 */
	@Override
	public float getZIndex() {
		return Z_INDEX;
	}

	/**
	 * Clears the table and calls the superclass' dispose method.
	 */
	@Override
	public void setStage(Stage mock) {

	}

	/**
	 * Removes the table
	 */
	@Override
	public void dispose() {
		table.clear();
		super.dispose();
	}
}