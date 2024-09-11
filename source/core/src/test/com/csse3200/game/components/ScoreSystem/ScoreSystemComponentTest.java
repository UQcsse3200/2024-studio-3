package com.csse3200.game.components.ScoreSystem;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.csse3200.game.extensions.GameExtension;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

@ExtendWith(GameExtension.class)
public class ScoreSystemComponentTest {

    @Test
    void testCompareListsFullMatch() {
        List<String> playerIngredients = Arrays.asList("A", "B", "C");
        List<String> orderIngredients = Arrays.asList("A", "B", "C");
        assertEquals(100, ScoreSystemComponent.compareLists(playerIngredients, orderIngredients));
    }

    @Test
    void testCompareListsPartialMatch() {
        List<String> playerIngredients = Arrays.asList("A", "B");
        List<String> orderIngredients = Arrays.asList("A", "B", "C");
        assertEquals(67, ScoreSystemComponent.compareLists(playerIngredients, orderIngredients));
    }

    @Test
    void testCompareListsNoMatch() {
        List<String> playerIngredients = Arrays.asList("D", "E");
        List<String> orderIngredients = Arrays.asList("A", "B", "C");
        assertEquals(0, ScoreSystemComponent.compareLists(playerIngredients, orderIngredients));
    }

    @Test
    void testCompareListsEmptyLists() {
        List<String> emptyList = Arrays.asList();
        assertEquals(0, ScoreSystemComponent.compareLists(emptyList, emptyList));
    }

    @Test
    void testGetScoreDescriptionGrinFace() {
        assertEquals("Grin Face", ScoreSystemComponent.getScoreDescription(100));
        assertEquals("Grin Face", ScoreSystemComponent.getScoreDescription(80));
    }

    @Test
    void testGetScoreDescriptionSmileFace() {
        assertEquals("Smile Face", ScoreSystemComponent.getScoreDescription(79));
        assertEquals("Smile Face", ScoreSystemComponent.getScoreDescription(60));
    }

    @Test
    void testGetScoreDescriptionNeutralFace() {
        assertEquals("Neutral Face", ScoreSystemComponent.getScoreDescription(59));
        assertEquals("Neutral Face", ScoreSystemComponent.getScoreDescription(40));
    }

    @Test
    void testGetScoreDescriptionFrownFace() {
        assertEquals("Frown Face", ScoreSystemComponent.getScoreDescription(39));
        assertEquals("Frown Face", ScoreSystemComponent.getScoreDescription(20));
    }

    @Test
    void testGetScoreDescriptionAngryFace() {
        assertEquals("Angry Face", ScoreSystemComponent.getScoreDescription(19));
        assertEquals("Angry Face", ScoreSystemComponent.getScoreDescription(0));
    }

    @Test
    void testGetScoreDescriptionIllegalScore() {
        IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
            ScoreSystemComponent.getScoreDescription(-1);
        });
    
        assertEquals("Invalid score parameter. Must be an integer between 0 and 100 (inclusive).", e.getMessage());

        e = assertThrows(IllegalArgumentException.class, () -> {
            ScoreSystemComponent.getScoreDescription(101);
        });
    
        assertEquals("Invalid score parameter. Must be an integer between 0 and 100 (inclusive).", e.getMessage());
    }
}
