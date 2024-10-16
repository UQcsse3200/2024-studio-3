package com.csse3200.game.components.ScoreSystem;

import java.util.List;
import com.csse3200.game.components.Component;

public class ScoreSystem extends Component {

    /**
     * Function that is called to retrieve the accuracy of the player's meal.
     *
     * @param playerIngredients - the ingredients used in the player's meal.
     * @param orderIngredients - the ingredients needed to make the ordered meal.
     */
    public static int getAccuracyScore(List<String> playerIngredients, List<String> orderIngredients) {
        int score = 0;
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
        if (longerIngredientList > 0) {
            double fraction = (double) matchingIngredients / longerIngredientList;
            score = (int) Math.round(fraction * 100);
        }

        // Round to nearest whole digit
        return score;
    }

    /**
     * Function that is called to retrieve the score based on the time remaining before the ticket expires.
     *
     * @param orderTime - the time that is remaining before the order ticket disappears.
     */
    public static int getTimeScore(String orderTime) {
        float time = Float.parseFloat(orderTime);
        int score;
        if (time >= 15) {
            score = 100;
        } else if (time >= 10 && time < 15) {
            score = 75;
        } else if (time >= 5 && time < 10) {
            score = 50;
        } else if (time > 0 && time < 5) {
            score = 25;
        } else {
            score = 0;
        }

        return score;
    }

    /**
     * Function that is called to retrieve the score based on the how 'chopped' or 'cooked' an ingredient is.
     *
     * @param ingredients - the ingredients that were used to create the player's meal.
     */
    public static int getCompletionScore(List<Float> ingredients) {
        int totalScore = 0; 
        int score;
        for (Float ingredient : ingredients) {
            int completionPercent = Math.round(ingredient);
            totalScore += completionPercent;
            }
        score = totalScore / ingredients.size();
        return score;
    }

    /**
     * Function that is called to retrieve the final score based on all considered factors.
     *
     * @param accuracyScore - the score that measures the accuracy of the player's meal.
     * @param timeScore - the score that is dependent on how much time remains on the ticket.
     * @param completionScore - the score that indicates how 'cooked' or 'chopped' the ingredients are.
     */
    public static String getFinalScore(int accuracyScore, int timeScore, int completionScore) {
        int score;
        score = (accuracyScore + timeScore + completionScore) / 3;

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