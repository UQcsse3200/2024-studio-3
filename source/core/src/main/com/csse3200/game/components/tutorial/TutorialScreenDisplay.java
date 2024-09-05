package com.csse3200.game.components.tutorial;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;

/**
 * Displays tutorial-related UI elements on the screen.
 */
public class TutorialScreenDisplay extends UIComponent {
    private Table instructionTable;
    private Label instructionLabel;

    @Override
    public void create() {
        super.create();

        Stage stage = ServiceLocator.getRenderService().getStage();
        instructionTable = new Table();
        instructionLabel = new Label("", skin);

        instructionTable.add(instructionLabel).pad(10f).row();
        instructionTable.setFillParent(true);

        stage.addActor(instructionTable);

        // Start listening to tutorial events
        ServiceLocator.getTutorialService().getEvents().addListener("updateInstruction", this::updateInstruction);
    }

    /**
     * Updates the tutorial instruction text displayed on the screen.
     *
     * @param instructionText The instruction text to display.
     */
    private void updateInstruction(String instructionText) {
        instructionLabel.setText(instructionText);
    }

    @Override
    public void draw(SpriteBatch batch) {
        // handled by stage
    }

    @Override
    public void dispose() {

        instructionTable.clear();
        instructionTable.remove();
        super.dispose();
    }
}
