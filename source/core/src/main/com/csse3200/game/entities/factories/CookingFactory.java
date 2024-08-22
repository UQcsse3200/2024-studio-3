
package com.csse3200.game.entity.factory;

import com.csse3200.game.entities.config.CookingConfig;
import com.csse3200.game.entities.configs.CookingConfig;
import com.csse3200.game.entities.configs.NPCConfigs;
import com.csse3200.game.files.FileLoader;


import java.io.File;
import java.io.IOException;
public class CookingFactory {

    private static final CookingConfig configs =
            FileLoader.readClass(CookingConfig.class, "configs/recipe.json");
}
