package com.csse3200.game.components.cutscenes;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Array;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.cutscenes.scenes.Scene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BackstoryCutsceneDisplay extends CutsceneScreenDisplay {
    private static final Logger logger = LoggerFactory.getLogger(BackstoryCutsceneDisplay.class);

    private int cutsceneStep = 0;
    private CutsceneTextDisplay textDisplay;
    private Array<String> cutsceneText;

    public BackstoryCutsceneDisplay(GdxGame game) {
        super(game);
        this.cutsceneText = new Array<>(); // Initialize cutscene text
    }

    @Override
    public void create() {
        super.create();
        setupUI();  // Setup UI components
        loadCutsceneText();  // Preload the cutscene text
    }

    private void setupUI() {
        Table table = new Table();
        table.setFillParent(true);

        // Initialize the text display and set it to be invisible initially
        textDisplay = new CutsceneTextDisplay();
        textDisplay.setVisible(false);  // Initially hidden

        TextButton nextSceneBtn = new TextButton("Next Scene", skin);
        nextSceneBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                advanceCutsceneStep();
            }
        });

        // Add the text display to the stage
        stage.addActor(textDisplay.getTable());  // Assuming `getTable()` returns the table UI component

        // Set up other UI components
        setupUI();
        loadCutsceneText();  // Preload the cutscene text

        table.add(nextSceneBtn).padTop(10f).padRight(10f);
        stage.addActor(table); // Add the table to the stage that is set externally
    }

    private void loadCutsceneText() {
        BackstoryCutscene cutscene = (BackstoryCutscene) game.getCurrentCutscene();
        if (cutscene != null) {
            cutsceneText.addAll(cutscene.getCutsceneText());
            advanceCutsceneStep(); // Start with the first text
        }
    }

    public void advanceCutsceneStep() {
        if (cutsceneStep < cutsceneText.size) {
            String nextText = cutsceneText.get(cutsceneStep);
            textDisplay.setText(nextText);
            cutsceneStep++; // Move to the next step
            textDisplay.setVisible(true);
        } else {
            textDisplay.setText("The cutscene ends.");
            textDisplay.setVisible(false);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        if (textDisplay != null) {
            textDisplay.dispose();
        }
    }
}
