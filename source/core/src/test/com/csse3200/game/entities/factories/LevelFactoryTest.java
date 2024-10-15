import com.csse3200.game.components.levels.LevelComponent;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.factories.LevelFactory;
import org.junit.Test;

import static org.junit.Assert.*;

public class LevelFactoryTest {

    @Test
    public void testCreateSpawnControllerEntity() {
        Entity entity = LevelFactory.createSpawnControllerEntity();

        assertNotNull("Entity should not be null", entity);

        LevelComponent levelComponent = entity.getComponent(LevelComponent.class);
        assertNotNull("Entity should have a LevelComponent", levelComponent);
    }
}
