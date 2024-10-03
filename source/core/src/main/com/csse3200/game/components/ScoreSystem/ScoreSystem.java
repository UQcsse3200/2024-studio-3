package com.csse3200.game.components.ScoreSystem;

import java.util.List;
import java.util.Arrays;
import com.csse3200.game.components.Component;

public class ScoreSystem extends Component {
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

        if (score > 100 || score < 0) {
            throw new IllegalArgumentException(
                    "Invalid score parameter. Must be an integer between 0 and 100 (inclusive).");
        }
        ;

        return switch (score / 20) {
            case 5, 4 -> "Grin Face"; // 80-100
            case 3 -> "Smile Face"; // 60-79
            case 2 -> "Neutral Face"; // 40-59
            case 1 -> "Frown Face"; // 20-39
            default -> "Angry Face"; // 0-19
        };
    }

    /**
     * Testing the score system by comparing lists and printing result to the
     * console.
     
    public static void main(String[] args) {
        List<List<String>> playerLists = Arrays.asList(
                Arrays.asList("A", "B", "C"),
                Arrays.asList("A", "B"),
                Arrays.asList("A", "B", "C"),
                Arrays.asList("A", "B"),
                Arrays.asList("A", "B", "C"));

        List<List<String>> orderLists = Arrays.asList(
                Arrays.asList("A", "B", "C"),
                Arrays.asList("A", "B", "C"),
                Arrays.asList("A", "B"),
                Arrays.asList("C", "D"),
                Arrays.asList("D", "E"));

        for (int i = 0; i < playerLists.size(); i++) {
            int score = compareLists(playerLists.get(i), orderLists.get(i));
            String description = getScoreDescription(score);
            System.out.printf("Case %d: Score = %d%%, Description = %s%n", i + 1, score, description);
        }
    }
        */
}