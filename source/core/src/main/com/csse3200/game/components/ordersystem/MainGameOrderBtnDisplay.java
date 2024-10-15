package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Displays a button on the main game screen that allows players to create a new order.
 * The button is positioned in the bottom right corner of the screen.
 */
public class MainGameOrderBtnDisplay extends UIComponent{
	private static final Logger logger = LoggerFactory.getLogger(MainGameOrderBtnDisplay.class);
	private static final float Z_INDEX = 2f;
	private Table table;
	private boolean pressed = false;

	/**
	 * Initialises the button display and sets up the actors in the UI.
	 */
	@Override
	public void create() {
		super.create();
		addActors();
		logger.info("order button created");
	}

	public Table getTable() {
		return table;
	}

	/**
	 * Adds the button to the UI and sets up the event listener for the button click.
	 * When the "Create Order" button is clicked, it triggers the "createOrder" event.
	 */
	public void addActors() {
		table = new Table();
		table.bottom().right();
		table.setFillParent(true);

		TextButton createOrderBtn = new TextButton("Create Order", skin);

		createOrderBtn.addListener(
		  new ChangeListener() {
			  @Override
			  public void changed(ChangeEvent changeEvent, Actor actor) {
				  logger.debug("Create Order button clicked");
				  pressed = true;
					ServiceLocator.getEntityService().getEvents().trigger("createOrder",null);
				  pressed = false;
			  }
		  });
		table.add(createOrderBtn).padBottom(10f).padRight(10f);

		stage.addActor(table);
	}

	/**
	 * Draws the button on the screen. The actual rendering is handled by the stage, so this method is empty.
	 *
	 * @param batch the sprite batch used for drawing.
	 */
	@Override
	public void draw(SpriteBatch batch) {
		// draw is handled by the stage
	}

	/**
	 * Returns the z-index for this component. The z-index determines the rendering order of UI components.
	 *
	 * @return the z-index for this component.
	 */
	@Override
	public float getZIndex() {
		return Z_INDEX;
	}

	/**
	 * Disposes of the button display, clearing the table and removing it from the stage.
	 */
	@Override
	public void setStage(Stage mock) {
		// This function is not needed nor is it called
	}

	/**
	 * Get the state of the button
	 *
	 * @return true if button is pressed, false otherwise
	 */
	public boolean getState(){
		return pressed;
	}

	public void setState(boolean pressed) { this.pressed = pressed;}

	/**
	 * Removes the button
	 */
	@Override
	public void dispose() {
		table.clear();
		super.dispose();
	}
}

