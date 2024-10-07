package com.csse3200.game.components.tutorial;

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
 * Displays Keybindings button
 */
public class KeybindsButtonDisplay extends UIComponent {
	private static final Logger logger = LoggerFactory.getLogger(KeybindsButtonDisplay.class);
	private Table table;

	/**
	 * Initalizes button display
	 */
	@Override
	public void create() {
		super.create();

		addActors();
	}

	/**
	 * Adds Keybindings button to UI
	 */
	private void addActors() {
		if (table == null) {
			table = new Table();
		}

		table.left().left();
		table.setFillParent(true);
		table.padTop(100f).padLeft(20f);

		TextButton button = new TextButton("Keybindings", skin);

		button.addListener(new ChangeListener() {
			@Override
			public void changed(ChangeEvent changeEvent, Actor actor) {
				ServiceLocator.getEntityService().getEvents().trigger("keybindings");
			}
		});
		table.add(button);

		stage.addActor(table);
	}

	/**
	 * Removes the button
	 */
	@Override
	public void dispose() {
		super.dispose();

		if (table != null) {
			table.clear();
		}
	}

	/**
	 * Draws the button on the screen. The actual rendering is handled by the stage, so this method is empty.
	 * @param batch Batch to render to.
	 */
	public void draw(SpriteBatch batch) {
		// Drawing is handled by the stage, so no implementation needed
	}

	/**
	 * Gets the state of the button
	 * @return the stage
	 */
	public Stage getStage() {
		return stage;
	}

	/**
	 * Set the stage
	 * @param stage the stage
	 */
	@Override
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	/**
	 * Set the table
	 * @param table the gable
	 */
	public void setTable(Table table) {
		this.table = table;
	}

	/**
	 * Gets the table
	 * @return the table
	 */
	public Table getTable() {
		return table;
	}

}
