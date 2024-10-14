package com.csse3200.game.areas.map;

import com.csse3200.game.components.maingame.GameBackgroundDisplay;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.EntityService;
import com.csse3200.game.entities.benches.Bench;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.rendering.RenderService;
import com.csse3200.game.services.ResourceService;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedConstruction;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(GameExtension.class)
public class BenchTwoTest {
    private AutoCloseable mocks;
    private Entity entity;
    private BenchGenerator benchGenerator;

    @BeforeEach
    void init() {
        mocks = MockitoAnnotations.openMocks(this);
        RenderService renderService = mock(RenderService.class);
        EntityService entityService = mock(EntityService.class);
        ResourceService resourceService = mock(ResourceService.class);

        ServiceLocator.registerRenderService(renderService);
        ServiceLocator.registerEntityService(entityService);
        ServiceLocator.registerResourceService(resourceService);

        benchGenerator = spy(new BenchGenerator());
    }

    @Test
    void testCreateBenchColumn() {
        try(MockedConstruction<Bench> mockBench =
                    Mockito.mockConstruction(Bench.class)){

            ArrayList<Bench> benches =  benchGenerator.createBenchColumn(5, 1, 4);
            assertEquals(4, mockBench.constructed().size()); //both work - how many times a bench
            // was initalised
            assertEquals(4, benches.size()); //both work - how long the bench column is
        }

    }
}
