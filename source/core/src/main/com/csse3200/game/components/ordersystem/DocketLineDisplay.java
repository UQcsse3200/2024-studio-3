package com.csse3200.game.components.ordersystem;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.csse3200.game.components.mainmenu.MainMenuDisplay;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




/**
 * A UI component for displaying the Docket Pin Line
 */
public class DocketLineDisplay extends UIComponent {
	private static final Logger logger = LoggerFactory.getLogger(MainMenuDisplay.class);
	private static final float Z_INDEX = 2f;
	private Table table;
    private Image pineLine;



	@Override
	public void create() {
		super.create();
		addActors();
	}

	private void addActors() {
		table = new Table();
		table.top().left();
		table.setFillParent(true);
		table.padTop(30f).padLeft(10f);

		pineLine =
				new Image(
						ServiceLocator.getResourceService()
								.getAsset("images/ordersystem/pin_line.png", Texture.class));
			docketLineResize();  

		table.add(pineLine).pad(5);
		table.row();

		stage.addActor(table);
	}
	public Image getPineLine() {
        return pineLine;
    }

	    private void docketLineResize() {
        // Dynamically get the current viewport width and height
        float viewportWidth = stage.getViewport().getWorldWidth();
        float viewportHeight = stage.getViewport().getWorldHeight();

        pineLine.setSize(viewportWidth * 0.9f, pineLine.getHeight());  // Adjust the width multiplier as needed
    }
	

	@Override
	public void draw(SpriteBatch batch) {
		// draw is handled by the stage
	}

	@Override
	public float getZIndex() {
		return Z_INDEX;
	}

	@Override
	public void dispose() {
		table.clear();
		super.dispose();
	}
}