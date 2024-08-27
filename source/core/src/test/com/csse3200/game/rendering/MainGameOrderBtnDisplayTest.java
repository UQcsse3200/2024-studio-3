
package com.csse3200.game.rendering;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.files.FileHandle;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.csse3200.game.components.ordersystem.MainGameOrderBtnDisplay;
import com.csse3200.game.ui.UIComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class MainGameOrderBtnDisplayTest {

    private MainGameOrderBtnDisplay display;
    private Stage mockStage;
    private TextButton mockButton;
    private Object mockEntity;

    @BeforeEach
    public void setUp() {

        display = new MainGameOrderBtnDisplay();
        mockStage = mock(Stage.class);
        mockButton = mock(TextButton.class);
        mockEntity = mock(Object.class);  // Adjust this if Entity is a specific class

        // Inject mocks into display
        display.setStage(mockStage);

        // Use reflection to set the protected 'entity' field
        try {
            var entityField = UIComponent.class.getDeclaredField("entity");
            entityField.setAccessible(true);
            entityField.set(display, mockEntity);
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Mock the behavior of addActor
        doNothing().when(mockStage).addActor(any(TextButton.class));

        // Mock the button click listener setup
        doAnswer(invocation -> {
            ChangeListener listener = invocation.getArgument(0);
            listener.changed(new ChangeListener.ChangeEvent(), mockButton);
            return null;
        }).when(mockButton).addListener(any(ChangeListener.class));

        // Call create to initialize and add actors
        display.create();
    }

    @Test
    public void testButtonCreation() {
        // Add assertions to verify the button creation logic
        // For example:
        verify(mockStage).addActor(any(TextButton.class));
    }

}