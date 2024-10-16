package com.csse3200.game.components.gameArea;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.components.maingame.GameBackgroundDisplay;
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
public class GameBackgroundDisplayTest {
    private AutoCloseable mocks;
    private Entity entity;
    private GameBackgroundDisplay gameBackgroundDisplay;
    private GameTime gameTime;

    @BeforeEach
    void init() {
        mocks = MockitoAnnotations.openMocks(this);
        RenderService renderService = mock(RenderService.class);
        EntityService entityService = mock(EntityService.class);
        ResourceService resourceService = mock(ResourceService.class);
        Stage stage = mock(Stage.class);
        gameTime = mock(GameTime.class);
        Texture texture = mock(Texture.class);
        Table table = mock(Table.class);


        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerEntityService(entityService);
        ServiceLocator.registerResourceService(resourceService);
        ServiceLocator.getRenderService().setStage(stage);
        ServiceLocator.registerTimeSource(gameTime);

        when(ServiceLocator.getResourceService().getAsset(anyString(), eq(Texture.class))).thenReturn(texture);

        entity = new Entity();

        gameBackgroundDisplay = spy(new GameBackgroundDisplay() {
            @Override
            public void create() {}
        });

        gameBackgroundDisplay.setTable(table);

        entity.addComponent(gameBackgroundDisplay);

        ServiceLocator.getEntityService().register(entity);
    }
    @AfterEach
    void end() throws Exception{
        mocks.close();
        ServiceLocator.clear();
    }

    @Test
    public void isInitialised() {
        entity.create();
        verify(gameBackgroundDisplay).create();
    }

    @Test
    public void initialImage() {
        String initialImage = "images/background_images/1.0.png";
        String currentImage = gameBackgroundDisplay.getCurrentImage();
        Assertions.assertEquals(initialImage, currentImage);
    }

    @Test
    public void noChangesAfter1Sec() {
        when(gameTime.getTimeSince(anyLong())).thenReturn(1L); //simulates time passed
        gameBackgroundDisplay.update();
        verify(gameBackgroundDisplay, never()).setBackground();
    }

    @Test
    public void changesAfter10Sec() {
        when(gameTime.getTimeSince(anyLong())).thenReturn(10000L);  //simulates time passed
        gameBackgroundDisplay.update();
        verify(gameBackgroundDisplay).setBackground();
    }

    @Test
    public void correctChangeAfter10Sec() {
        when(gameTime.getTimeSince(anyLong())).thenReturn(10000L);  //simulates time passed
        gameBackgroundDisplay.update();
        String secondImage = "images/background_images/1.5.png";
        String currentImage = gameBackgroundDisplay.getCurrentImage();
        Assertions.assertEquals(secondImage, currentImage);
    }

    @Test
    public void correctNumberChangesAfter102Sec() {
        long timeLapsed = 102000; //102 seconds
        //until 100 seconds has passed, update gameBackgroundDisplay
        while(timeLapsed > 0) {
            when(gameTime.getTimeSince(anyLong())).thenReturn(timeLapsed);
            gameBackgroundDisplay.update();
            timeLapsed -= 300000/36;
        }
        verify(gameBackgroundDisplay, times(12)).setBackground();
    }

    @Test
    public void correctChangeAfter102Sec() {
        long timeLapsed = 102000; //102 seconds
        //until 100 seconds has passed, update gameBackgroundDisplay
        while(timeLapsed > 0) {
            when(gameTime.getTimeSince(anyLong())).thenReturn(timeLapsed);
            gameBackgroundDisplay.update();
            timeLapsed -= 300000/36;
        }
        String nthImage = "images/background_images/7.0.png";
        String currentImage = gameBackgroundDisplay.getCurrentImage();
        Assertions.assertEquals(nthImage, currentImage);
    }

    @Test
    public void stopsUpdating() {
        long oneMinute = 360000;
        long fiveMinutes = 300000;

        updateBackground(fiveMinutes);
        //has 35 updates (reaches final frame)
        verify(gameBackgroundDisplay, times(35)).setBackground();

        updateBackground(oneMinute); //total time = 6 minutes
        //no further updates
        verify(gameBackgroundDisplay, times(35)).setBackground();
    }

    @Test
    public void correctFinalUpdate() {
        long fiveMinutes = 300000;
        updateBackground(fiveMinutes);
        String finalImage = "images/background_images/18.5.png";
        Assertions.assertEquals(gameBackgroundDisplay.getCurrentImage(), finalImage);
    }

    public void updateBackground(long time) {
        while (time > 0) {
            when(gameTime.getTimeSince(anyLong())).thenReturn(time);
            gameBackgroundDisplay.update();
            time -= 300000/36;
        }
    }

}
