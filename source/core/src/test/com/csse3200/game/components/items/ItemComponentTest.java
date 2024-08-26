package com.csse3200.game.components.items;

import com.badlogic.gdx.graphics.Texture;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(GameExtension.class)
class ItemComponentTest {
    @Test
    void ItemComponentConstructorTest() {
        ItemComponent item = new ItemComponent("Test", ItemType.BEEF, 1);
        assertEquals("Test", item.getItemName());
        assertEquals(ItemType.BEEF, item.getItemType());
        assertEquals(1, item.weight());
    }
}