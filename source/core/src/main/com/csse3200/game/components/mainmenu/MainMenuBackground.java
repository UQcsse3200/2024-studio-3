package com.csse3200.game.components.mainmenu;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainMenuBackground extends UIComponent {

    private Table table;
    private static final Logger logger = LoggerFactory.getLogger(MainMenuBackground.class); // Updated to use MainMenuBackground.class
    private static final String BACKGROUND_IMAGE_PATH = "images/Cutscenes/bg.png"; // Defined constant for repeated string
    private static final String[] backgroundTextures = {BACKGROUND_IMAGE_PATH};

    private String image;

    public MainMenuBackground() {
        super();
    }

    @Override
    public void create() {
        super.create();
        table = new Table();
        table.setFillParent(true);
        table.setVisible(true);
        stage.addActor(table);
        ServiceLocator.getResourceService().loadTextures(backgroundTextures);

        setupBackground();
    }

    public void setupBackground() {
        Texture texture = ServiceLocator.getResourceService().getAsset(BACKGROUND_IMAGE_PATH, Texture.class); // Used constant for image path
        Image bgImage = new Image(texture); // Renamed local variable to avoid conflict with the field 'image'
        bgImage.setFillParent(true);
        setImage(BACKGROUND_IMAGE_PATH);
        table.add(bgImage);
    }

    @Override
    protected void draw(SpriteBatch batch) {
        // Method intentionally left empty (addressed empty method warning)
    }

    @Override
    public void setStage(Stage mock) {
        // Method intentionally left empty (addressed empty method warning)
    }

    public void setTable(Table table) {
        this.table = table;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return this.image;
    }

    public String getImage(Table table) {
        return table.getChildren().get(0).toString();
    }
}
