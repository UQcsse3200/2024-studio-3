package com.csse3200.game.components.ScoreSystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.csse3200.game.extensions.GameExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@ExtendWith(GameExtension.class)
public class ScoreSystemTest {

    @Test
    void testCompareListsFullMatch() {
        List<String> playerIngredients = Arrays.asList("A", "B", "C");
        List<String> orderIngredients = Arrays.asList("A", "B", "C");
        assertEquals(100, ScoreSystem.compareLists(playerIngredients, orderIngredients));
    }

    @Test
    void testCompareListsPartialMatch() {
        List<String> playerIngredients = Arrays.asList("A", "B");
        List<String> orderIngredients = Arrays.asList("A", "B", "C");
        assertEquals(67, ScoreSystem.compareLists(playerIngredients, orderIngredients));
    }

    @Test
    void testCompareListsNoMatch() {
        List<String> playerIngredients = Arrays.asList("D", "E");
        List<String> orderIngredients = Arrays.asList("A", "B", "C");
        assertEquals(0, ScoreSystem.compareLists(playerIngredients, orderIngredients));
    }

    @Test
    void testCompareListsEmptyLists() {
        List<String> emptyList = Arrays.asList();
        assertEquals(0, ScoreSystem.compareLists(emptyList, emptyList));
    }

    @Test
    void testGetScoreDescriptionGrinFace() {
        assertEquals("Grin Face", ScoreSystem.getScoreDescription(100));
        assertEquals("Grin Face", ScoreSystem.getScoreDescription(80));
    }

    @Test
    void testGetScoreDescriptionSmileFace() {
        assertEquals("Smile Face", ScoreSystem.getScoreDescription(79));
        assertEquals("Smile Face", ScoreSystem.getScoreDescription(60));
    }

    @Test
    void testGetScoreDescriptionNeutralFace() {
        assertEquals("Neutral Face", ScoreSystem.getScoreDescription(59));
        assertEquals("Neutral Face", ScoreSystem.getScoreDescription(40));
    }

    @Test
    void testGetScoreDescriptionFrownFace() {
        assertEquals("Frown Face", ScoreSystem.getScoreDescription(39));
        assertEquals("Frown Face", ScoreSystem.getScoreDescription(20));
    }

    @Test
    void testGetScoreDescriptionAngryFace() {
        assertEquals("Angry Face", ScoreSystem.getScoreDescription(19));
        assertEquals("Angry Face", ScoreSystem.getScoreDescription(0));
    }

    @Test
    void testGetScoreDescriptionIllegalScore() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            ScoreSystem.getScoreDescription(-1);
        });

        assertEquals("Invalid score parameter. Must be an integer between 0 and 100 (inclusive).", e.getMessage());

        e = assertThrows(IllegalArgumentException.class, () -> {
            ScoreSystem.getScoreDescription(101);
        });

        assertEquals("Invalid score parameter. Must be an integer between 0 and 100 (inclusive).", e.getMessage());
    }
}
