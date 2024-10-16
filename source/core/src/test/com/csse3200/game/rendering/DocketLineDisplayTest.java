package com.csse3200.game.rendering;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.headless.HeadlessApplication;
import com.badlogic.gdx.backends.headless.HeadlessApplicationConfiguration;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.csse3200.game.components.ordersystem.DocketLineDisplay;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocketLineDisplayTest {

    private SpriteBatch batch;
    private DocketLineDisplay docketLineDisplay;
    private Stage stage;
    private Image pinLine;
    private Table table;

    @BeforeAll
    static void setUpClass() {
        RenderService mockRenderService = mock(RenderService.class);
        ServiceLocator.registerRenderService(mockRenderService);

        ResourceService mockResourceService = mock(ResourceService.class);
        ServiceLocator.registerResourceService(mockResourceService);

        Texture mockTexture = mock(Texture.class);
        when(mockResourceService.getAsset(anyString(), eq(Texture.class))).thenReturn(mockTexture);



        HeadlessApplicationConfiguration config = new HeadlessApplicationConfiguration();
        new HeadlessApplication(new ApplicationListener() {
            @Override public void create() {}
            @Override public void resize(int width, int height) {}
            @Override public void render() {}
            @Override public void pause() {}
            @Override public void resume() {}
            @Override public void dispose() {}
        }, config);
    }

    @AfterAll
    static void tearDown() {
        ServiceLocator.clear();
    }

    @BeforeEach
    void setUp() {
        batch = Mockito.mock(SpriteBatch.class);
        stage = Mockito.mock(Stage.class);
        pinLine = Mockito.mock(Image.class);
        table = Mockito.mock(Table.class);
        Skin skin = mock(Skin.class);
        docketLineDisplay = new DocketLineDisplay(skin);
        docketLineDisplay.setStage(stage);
        docketLineDisplay.setPinLine(pinLine);
        docketLineDisplay.setTable(table);
    }

    @Test
    void testBatchIsNotNull() {
        assertNotNull(batch, "Batch should be initialized");
    }

    @Test
    void testCreate() {
        Stage mockStage = mock(Stage.class);
        when(ServiceLocator.getRenderService().getStage()).thenReturn(mockStage);
        docketLineDisplay.create();
        verify(mockStage, atLeastOnce()).addActor(any());
    }

    @Test
    void testResize() {
        docketLineDisplay.resize();
        verify(pinLine).remove();
        verify(table).clear();
        verify(stage, atLeastOnce()).addActor(any());
    }

    @Test
    void testDispose() {
        docketLineDisplay.dispose();
        verify(table).clear();
    }

    @Test
    void testGetSetPinLine() {
        Image newPinLine = Mockito.mock(Image.class);
        docketLineDisplay.setPinLine(newPinLine);
        assertEquals(newPinLine, docketLineDisplay.getPinLine());
    }

    @Test
    void testGetSetTable() {
        Table newTable = Mockito.mock(Table.class);
        docketLineDisplay.setTable(newTable);
        assertEquals(newTable, docketLineDisplay.getTable());
    }

    @Test
    public void testGetSetStage() {
        Stage newStage = Mockito.mock(Stage.class);
        docketLineDisplay.setStage(newStage);
        assertEquals(newStage, docketLineDisplay.getStage());
    }

    @Test
    void testGetZIndex() {
        assertEquals(2f, docketLineDisplay.getZIndex());
    }

    @Test
    void testDraw() {
        docketLineDisplay.draw(batch);
        // draw is handled by the stage, no need to verify batch interactions
    }
}
