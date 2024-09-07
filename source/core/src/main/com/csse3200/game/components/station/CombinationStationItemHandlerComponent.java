package com.csse3200.game.components.station;

import java.util.ArrayList;

import com.csse3200.game.files.*;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.entities.configs.RecipeConfig;
import com.csse3200.game.entities.factories.DishFactory;

public class CombinationStationItemHandlerComponent extends StationItemHandlerComponent {

    /**
     * We already have:
     *  - this.type, String
     *  - this.inventoryComponent, InventoryComponent
     *  - this.acceptableItems, ArrayList<String>
     */
    private DishFactory recipes;   // data type TBD
    // maybe more here, depends

    /**
     * General constructor
     * @param type - storing type of station
     * @param acceptableItems - HashMap, HashSet etc of mappings for acceptable items based on station
     * @param recipeFile - the file path for the recipe json for this particular Combination Station(TM) 
     */
    public CombinationStationItemHandlerComponent(String type, ArrayList<String> acceptableItems, String recipeFile) {
        super(type, acceptableItems);
        this.recipes = new DishFactory();    // load in recipe files maybe
    }


}