package com.csse3200.game.components.gameArea;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.components.mainmenu.MainMenuBackground;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockitoAnnotations;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class MainMenuTest {
    private AutoCloseable mocks;
    private Entity entity;
    private MainMenuBackground mainMenuBackgroundDisplay;
    private GameTime gameTime;
    private Table table;
    private ResourceService mockResourceService;

    @BeforeEach
    void init() {
        mocks = MockitoAnnotations.openMocks(this);
        RenderService renderService = mock(RenderService.class);
        EntityService entityService = mock(EntityService.class);
        ResourceService resourceService = mock(ResourceService.class);
        Stage stage = mock(Stage.class);
        gameTime = mock(GameTime.class);
        Texture texture = mock(Texture.class);
        table = mock(Table.class);

        mock(Texture.class);
        mockResourceService = mock(ResourceService.class);


        // Stub the getAsset method to return the mock texture
     //   when(mockResourceService.getAsset("images/Cutscenes/bg.png", Texture.class)).thenReturn(mockTexture);

        // Mock the ServiceLocator to return the mocked ResourceService
        ServiceLocator.clear();  // Clear any previous instance
        ServiceLocator.registerResourceService(mockResourceService);

        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerEntityService(entityService);
        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.getRenderService().setStage(stage);
        ServiceLocator.registerTimeSource(gameTime);

        when(ServiceLocator.getResourceService().getAsset(anyString(), eq(Texture.class))).thenReturn(texture);

        entity = new Entity();

        mainMenuBackgroundDisplay = spy(new MainMenuBackground() {
            @Override
            public void create() {}
        });
        Texture texture1 = mock(Texture.class);
        when(mockResourceService.getAsset("images/Cutscenes/bg.png", Texture.class)).thenReturn(texture1);
        mainMenuBackgroundDisplay.setTable(table);

        entity.addComponent(mainMenuBackgroundDisplay);

        ServiceLocator.getEntityService().register(entity);
    }
    @AfterEach
    void end() throws Exception{
        mocks.close();
        ServiceLocator.clear();
    }

    @Test
    public void isInitialised() {
        //assertTrue(true);
        entity.create();
        verify(mainMenuBackgroundDisplay).create();
    }

    @Test
    public void correctFinalUpdate() {
        mainMenuBackgroundDisplay.setupBackground();
        String initialImage = "images/Cutscenes/bg.png";
        String currentImage = mainMenuBackgroundDisplay.getImage();
        Assertions.assertEquals(initialImage, currentImage);
    }


}