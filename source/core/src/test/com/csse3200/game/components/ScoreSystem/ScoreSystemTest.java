package com.csse3200.game.components.ScoreSystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.csse3200.game.extensions.GameExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@ExtendWith(GameExtension.class)
class ScoreSystemTest {

    @Test
    void testCompareListsFullMatch() {
        List<String> playerIngredients = Arrays.asList("A", "B", "C");
        List<String> orderIngredients = Arrays.asList("A", "B", "C");
        assertEquals(100, ScoreSystem.getAccuracyScore(playerIngredients, orderIngredients));
    }

    @Test
    void testCompareListsPartialMatch() {
        List<String> playerIngredients = Arrays.asList("A", "B");
        List<String> orderIngredients = Arrays.asList("A", "B", "C");
        assertEquals(67, ScoreSystem.getAccuracyScore(playerIngredients, orderIngredients));
    }

    @Test
    void testCompareListsNoMatch() {
        List<String> playerIngredients = Arrays.asList("D", "E");
        List<String> orderIngredients = Arrays.asList("A", "B", "C");
        assertEquals(0, ScoreSystem.getAccuracyScore(playerIngredients, orderIngredients));
    }

    @Test
    void testCompareListsEmptyLists() {
        List<String> emptyList = Arrays.asList();
        assertEquals(0, ScoreSystem.getAccuracyScore(emptyList, emptyList));
    }

    @Test
    void testGetScoreDescriptionGrinFace() {
        assertEquals("Grin Face", ScoreSystem.getFinalScore(100, 100, 100));
        assertEquals("Grin Face", ScoreSystem.getFinalScore(80, 80, 80));
    }

    @Test
    void testGetScoreDescriptionSmileFace() {
        assertEquals("Smile Face", ScoreSystem.getFinalScore(79, 69, 89));
        assertEquals("Smile Face", ScoreSystem.getFinalScore(60, 60, 60));
    }

    @Test
    void testGetScoreDescriptionNeutralFace() {
        assertEquals("Neutral Face", ScoreSystem.getFinalScore(59, 59, 59));
        assertEquals("Neutral Face", ScoreSystem.getFinalScore(40, 20, 60));
    }

    @Test
    void testGetScoreDescriptionFrownFace() {
        assertEquals("Frown Face", ScoreSystem.getFinalScore(39, 39, 39));
        assertEquals("Frown Face", ScoreSystem.getFinalScore(20, 30, 10));
    }

    @Test
    void testGetScoreDescriptionAngryFace() {
        assertEquals("Angry Face", ScoreSystem.getFinalScore(19, 21, 17));
        assertEquals("Angry Face", ScoreSystem.getFinalScore(0, 0, 0));
    }

    @Test
    void testGetScoreDescriptionIllegalScore() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            ScoreSystem.getFinalScore(-1, -1, -1);
        });

        assertEquals("Invalid score parameter. Must be an integer between 0 and 100 (inclusive).", e.getMessage());

        e = assertThrows(IllegalArgumentException.class, () -> {
            ScoreSystem.getFinalScore(101, 102, 103);
        });

        assertEquals("Invalid score parameter. Must be an integer between 0 and 100 (inclusive).", e.getMessage());
    }
}
