package com.csse3200.game.components.ScoreSystem;

import java.util.List;
import com.csse3200.game.components.Component;

public class ScoreSystem extends Component {
    public static int getAccuracyScore(List<String> playerIngredients, List<String> orderIngredients) {
        // Determine the size of the longer ingredient list
        int longerIngredientList = Math.max(playerIngredients.size(), orderIngredients.size());

        // Count number of matching ingredients
        int matchingIngredients = 0;
        for (String ingredient : playerIngredients) {
            if (orderIngredients.contains(ingredient)) {
                matchingIngredients++;
            }
        }

        // Calculate percentage score
        double percentage = ((double) matchingIngredients / longerIngredientList) * 100;

        // Round to nearest whole digit
        return (int) Math.round(percentage);
    }

    // Add function that determines the time remaining on order ticket.
    public static int getTimeScore(String orderTime) {
        float time = Float.parseFloat(orderTime);
        double percentage;
        if (time >= 15) {
            percentage = 100;
        } else if (time >= 10 && time < 15) {
            percentage = 75;
        } else if (time >= 5 && time < 10) {
            percentage = 50;
        } else if (time > 0 && time < 5) {
            percentage = 25;
        } else {
            percentage = 0;
        }

        return (int) Math.round(percentage);
    }

    public static String getScoreDescription(int score) {

        if (score > 100 || score < 0) {
            throw new IllegalArgumentException(
                    "Invalid score parameter. Must be an integer between 0 and 100 (inclusive).");
        }

        return switch (score / 20) {
            case 5, 4 -> "Grin Face"; // 80-100
            case 3 -> "Smile Face"; // 60-79
            case 2 -> "Neutral Face"; // 40-59
            case 1 -> "Frown Face"; // 20-39
            default -> "Angry Face"; // 0-19
        };
    }
}