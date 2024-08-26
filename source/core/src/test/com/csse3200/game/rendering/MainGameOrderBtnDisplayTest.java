package com.csse3200.game.rendering;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.csse3200.game.components.ordersystem.MainGameOrderBtnDisplay;
import com.csse3200.game.services.ServiceLocator;
import com.csse3200.game.ui.UIComponent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MainGameOrderBtnDisplayTest {

    private MainGameOrderBtnDisplay orderBtnDisplay;
    private Table table;
    private TextButton createOrderBtn;

    @BeforeEach
    public void setUp() {
        // Initialize the UI component
        orderBtnDisplay = new MainGameOrderBtnDisplay();

        // Create a mock for the stage
        orderBtnDisplay.setStage(Mockito.mock(com.badlogic.gdx.scenes.scene2d.Stage.class));
        orderBtnDisplay.create(); // Initialize the component

        }

    @Test
    public void testButtonInitialization() {
        assertNotNull(table, "Table should be initialized");
        assertNotNull(createOrderBtn, "Create Order button should be initialized");
        assertTrue(createOrderBtn.isVisible(), "Create Order button should be visible");
        assertTrue(createOrderBtn.getText().toString().equals("Create Order"), "Button text should be 'Create Order'");
    }



    @Test
    public void testDispose() {
        // Ensure that the table is not empty before dispose
        assertNotNull(table, "Table should be initialized");
        assertTrue(table.getChildren().size > 0, "Table should have children before dispose");

        // Call dispose
        orderBtnDisplay.dispose();

        // Check if the table is cleared
        assertTrue(table.getChildren().isEmpty(), "Table should be cleared after dispose");
    }
}
