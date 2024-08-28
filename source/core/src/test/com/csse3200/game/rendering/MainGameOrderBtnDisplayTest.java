
package com.csse3200.game.rendering;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.csse3200.game.components.ordersystem.MainGameOrderBtnDisplay;
import com.csse3200.game.ui.UIComponent;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MainGameOrderBtnDisplayTest {

    private Stage mockStage;
    private TextButton mockButton;

    @BeforeEach
    public void setUp() {

        MainGameOrderBtnDisplay display = new MainGameOrderBtnDisplay();
        mockStage = mock(Stage.class);
        mockButton = mock(TextButton.class);
        var mockEntity = mock(Object.class);


        display.setStage(mockStage);

        try {
            var entityField = UIComponent.class.getDeclaredField("entity");
            entityField.setAccessible(true);
            entityField.set(display, mockEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        doNothing().when(mockStage).addActor(any(TextButton.class));

        doAnswer(invocation -> {
            ChangeListener listener = invocation.getArgument(0);
            listener.changed(new ChangeListener.ChangeEvent(), mockButton);
            return null;
        }).when(mockButton).addListener(any(ChangeListener.class));

        display.create();
    }

    @Test
    public void testButtonCreation() {
        verify(mockStage).addActor(any(TextButton.class));
    }

}