package com.csse3200.game.extensions;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.api.extension.ExtensionContext.Namespace;
import org.junit.jupiter.api.extension.ExtensionContext.Store;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.Extension;
import com.csse3200.game.services.ServiceLocator;

/**
 * Custom JUnit 5 extension that sets up and tears down the game environment
 * for each test. It clears the ServiceLocator to ensure no state is shared
 * between tests and handles any other setup or cleanup tasks needed.
 */
public class GameExtension implements BeforeEachCallback, AfterEachCallback {

    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
        // Initialize or reset any services you need before each test
        ServiceLocator.clear();  // Clear any existing services
        // Initialize any necessary services here, e.g.:
        // ServiceLocator.registerTimeSource(new GameTime());
    }

    @Override
    public void afterEach(ExtensionContext context) throws Exception {
        // Clean up resources after each test if necessary
        ServiceLocator.clear();  // Clear services after each test to avoid state leakage
    }
}
