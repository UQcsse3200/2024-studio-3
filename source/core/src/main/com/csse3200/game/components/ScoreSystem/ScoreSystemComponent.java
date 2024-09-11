package com.csse3200.game.components.ScoreSystem;

import java.util.List;
import com.csse3200.game.components.Component;

public class ScoreSystemComponent extends Component {
    private static final String scoreException = "Invalid score parameter. Must be an integer between 0 and 100 (inclusive).";
    public static int compareLists(List<String> playerIngredients, List<String> orderIngredients) {
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

    public static String getScoreDescription(int score) {
        if (score < 0 || score > 100) {
            throw new IllegalArgumentException(scoreException);
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