package com.csse3200.game.components.station;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.csse3200.game.files.*;
import com.csse3200.game.entities.configs.SingleStationRecipeConfig;
import com.csse3200.game.entities.factories.DishFactory;
import com.csse3200.game.components.player.InventoryComponent;
import com.csse3200.game.components.items.ItemType;
import com.csse3200.game.components.items.ItemComponent;
import com.csse3200.game.components.items.MealComponent;

public class CombinationStationItemHandlerComponent extends StationItemHandlerComponent {

    /**
     * We already have:
     *  - this.type, String
     *  - this.inventoryComponent, InventoryComponent
     *  - this.acceptableItems, ArrayList<String>
     */
    private final DishFactory mealFactory;
    private InventoryComponent inventory;
    private final Map<String, SingleStationRecipeConfig> recipes; 


    /**
     * General constructor
     * @param type - storing type of station
     * @param acceptableItems - HashMap, HashSet etc of mappings for acceptable items based on station
     * @param recipeFile - the file path for the recipe json for this particular Combination Station(TM) 
     */
    public CombinationStationItemHandlerComponent(String type, ArrayList<String> acceptableItems) {
        super(type, acceptableItems);
        this.mealFactory = new DishFactory();                   // required for generating this.recipes
        this.recipes = mealFactory.getAllRecipes();             // required to match possible recipe names to configs
        this.inventory = new InventoryComponent(20);   // required until team 5 makes fields visible
    }

    /**
     * Returns the list of possible meals from the current inventory of the station
     * 
     * @return - a list of possible recipe names from the current inventory. 
     */
    public List<String> validMeals() {
        List<String> possibleRecipes =  mealFactory.getRecipe(this.inventory.getItemNames());
        
        for (String recipe : possibleRecipes) {
            if (recipe.equals("fruitSalad")) {
                // move the two-ingredient meal to the end of the priority
                possibleRecipes.remove("fruitSalad");
                possibleRecipes.addFirst("fruitSalad");
                break;
            }
        }

        return possibleRecipes; 

    }

    /**
     * If possible, returns the best possible meal from the current station inventory.
     * If multiple meals are possible, the first meal with the most ingredients will be chosen
     * for creation in TODO_function.
     * 
     * @return - the name of the best possible meal from the current inventory, null if there is no possible meal.
     */
    public String chooseMeal() {
        // get all the possible meals from the current state of the inventory
        List<String> validMeals = this.validMeals();
        String bestMeal = null;

        // if there are possible meals, find the best one
        if (!validMeals.isEmpty()) {
            // RecipeConfig configs = mealFactory.getRecipeConfigs();
            bestMeal = validMeals.getFirst();
        }

        return bestMeal;
    }

    /**
     * Creates a meal, if possible, from station inventory, and places the 
     * new meal back in the inventory. 
     * 
     * To be triggered by a player interaction.
     * 
     * Trigger TBD. 
     */
    public void handleMealCreation() {
        
        String mealName = this.chooseMeal();
        
        if (mealName != null) {
            // a meal is possible, now we combine
            SingleStationRecipeConfig mealRecipe = recipes.get(mealName);
            ArrayList<ItemComponent> mealIngredients = new ArrayList<ItemComponent>();

            // collate required items from the station inventory into a list for meal creation
            for (String ingredientName : mealRecipe.ingredient) {
                mealIngredients.add(this.inventory.removeItemName(ingredientName));
            }

            // creates the meal and places it in the station inventory at the end
            /* 
            ItemType mealType = null;
            MealComponent meal = new MealComponent(mealName, mealType, 0, mealIngredients);
            this.inventory.addItem(meal);
            */
        }

        // if no meal possible, we just return
    }
}