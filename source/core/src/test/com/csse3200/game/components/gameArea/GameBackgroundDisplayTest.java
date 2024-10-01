package com.csse3200.game.components.gameArea;

import com.badlogic.gdx.Files;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.GdxGame;
import com.csse3200.game.components.cutscenes.IntroCutscene;
import com.csse3200.game.components.maingame.GameBackgroundDisplay;
import com.csse3200.game.components.player.InventoryDisplay;
import com.csse3200.game.components.player.PlayerAnimationController;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.events.EventHandler;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;

import java.security.Provider;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class GameBackgroundDisplayTest {
    private AutoCloseable mocks;
    private Entity entity;
    private GameBackgroundDisplay gameBackgroundDisplay;
    private RenderService renderService;
    private Stage stage;
    private EntityService entityService;
    private GameTime gameTime;
    private Texture texture;
    private Table table;
    private ResourceService resourceService;

    @BeforeEach
    void init() {
        mocks = MockitoAnnotations.openMocks(this);
        renderService = mock(RenderService.class);
        entityService = mock(EntityService.class);
        resourceService = mock(ResourceService.class);
        stage = mock(Stage.class);
        gameTime = mock(GameTime.class);
        texture = mock(Texture.class);
        table = mock(Table.class);


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
    public void isInitalised() {
        //assertTrue(true);
        entity.create();
        verify(gameBackgroundDisplay).create();
    }

    @Test
    public void initialImage() {
        String initialImage = "images/background_images/1.0.png";
        String currentImage = gameBackgroundDisplay.getCurrentImage();
        assertEquals(initialImage, currentImage);
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
        assertEquals(secondImage, currentImage);
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
        assertEquals(nthImage, currentImage);
    }

    @Test
    public void stopsUpdating() {
        long timeLapsed = 360000; //360 seconds (6 minutes) - should stop updating after 5
        //until 6 minutes has passed, update gameBackgroundDisplay
        while(timeLapsed > 0) {
            when(gameTime.getTimeSince(anyLong())).thenReturn(timeLapsed);
            gameBackgroundDisplay.update();
            timeLapsed -= 300000/36;
        }
        String nthImage = "images/background_images/18.5.png";
        String currentImage = gameBackgroundDisplay.getCurrentImage();
        assertEquals(nthImage, currentImage);
    }

}
