package com.csse3200.game.components.moral;


import com.csse3200.game.entities.Entity;
import com.csse3200.game.extensions.GameExtension;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith(GameExtension.class)
public class MoralDecisionTest {
    private Entity testEntity;

    @BeforeEach
    void init() {
        testEntity = new Entity();
        MoralDecision moralDecision = new MoralDecision();
        testEntity.addComponent(moralDecision);
        testEntity.create();
    }

    @Test
    void addQuestion_shouldAddQuestionSuccessfully() {
        testEntity.getComponent(MoralDecision.class).addQuestion("Is this a good decision?");
        Assertions.assertEquals(1, testEntity.getComponent(MoralDecision.class).getListOfDecisions().size());
    }

    @Test
    void addQuestion_withParameters_shouldAddQuestionSuccessfully() {
        testEntity.getComponent(MoralDecision.class).addQuestion("Is this a good decision?", true, 10);
        Assertions.assertEquals(1, testEntity.getComponent(MoralDecision.class).getListOfDecisions().size());
    }

    @Test
    void addDecision_shouldAddDecisionSuccessfully() {
        var testDecision = new Decision("Is this a good decision?", true, 10);
        testDecision.setDecision(true);
        testEntity.getComponent(MoralDecision.class).addDecision(testDecision);
        Assertions.assertEquals(1, testEntity.getComponent(MoralDecision.class).getListOfDecisions().size());
    }

    @Test
    void getDecision_withNonExistentQuestion_shouldReturnFalse() {
        testEntity.getComponent(MoralDecision.class).addQuestion("Is this a good decision?");
        Assertions.assertFalse(testEntity.getComponent(MoralDecision.class).getDecision("Non-existent question"));
    }

    @Test
    void clearDecisions_shouldRemoveAllDecisions() {
        testEntity.getComponent(MoralDecision.class).addQuestion("Is this a good decision?");
        testEntity.getComponent(MoralDecision.class).clearDecisions();
        Assertions.assertEquals(0, testEntity.getComponent(MoralDecision.class).getListOfDecisions().size());
    }

    @Test
    void removeDecision_byIndex_shouldRemoveDecision() {
        testEntity.getComponent(MoralDecision.class).addQuestion("Is this a good decision?");
        testEntity.getComponent(MoralDecision.class).removeDecision(0);
        Assertions.assertEquals(0, testEntity.getComponent(MoralDecision.class).getListOfDecisions().size());
    }

    @Test
    void removeDecision_byObject_shouldRemoveDecision() {
        var testDecision = new Decision("Is this a good decision?", true, 10);
        testEntity.getComponent(MoralDecision.class).addDecision(testDecision);
        testEntity.getComponent(MoralDecision.class).removeDecision(testDecision);
        Assertions.assertEquals(0, testEntity.getComponent(MoralDecision.class).getListOfDecisions().size());
    }

    @Test
    void removeDecision_byQuestion_shouldRemoveDecision() {
        testEntity.getComponent(MoralDecision.class).addQuestion("Is this a good decision?");
        testEntity.getComponent(MoralDecision.class).removeDecision("Is this a good decision?");
        Assertions.assertEquals(0, testEntity.getComponent(MoralDecision.class).getListOfDecisions().size());
    }

    @Test
    void setDecision_shouldNotAllowMultipleDecisions() {
        var testDecision = new Decision("Is this a good decision?", true, 10);
        Assertions.assertTrue(testDecision.setDecision(true));
        Assertions.assertFalse(testDecision.setDecision(false));
    }

    @Test
    void setPoints_shouldNotAllowNegativePoints() {
        var testDecision = new Decision("Is this a good decision?", true, 10);
        Assertions.assertFalse(testDecision.setPoints(-5));
    }

    @Test
    void getDecision_shouldReturnFalseIfNotMade() {
        var testDecision = new Decision("Is this a good decision?", true, 10);
        Assertions.assertFalse(testDecision.getDecision());
    }

    @Test
    void getDecisionPoints_shouldReturnNegativeForBadDecision() {
        var testDecision = new Decision("Is this a bad decision?", false, 10);
        Assertions.assertEquals(-10, testDecision.getDecisionPoints());
    }

    @Test
    void getDecisionPoints_shouldReturnPositiveForGoodDecision() {
        var testDecision = new Decision("Is this a good decision?", true, 10);
        Assertions.assertEquals(10, testDecision.getDecisionPoints());
    }

    @Test
    void getStatement_shouldReturnCorrectStatement() {
        var testDecision = new Decision("Is this a good decision?", true, 10);
        Assertions.assertEquals("Is this a good decision?", testDecision.getStatement());
    }

    @Test
    void isGood_shouldReturnTrueForGoodDecision() {
        var testDecision = new Decision("Is this a good decision?", true, 10);
        Assertions.assertTrue(testDecision.isGood());
    }

    @Test
    void isGood_shouldReturnFalseForBadDecision() {
        var testDecision = new Decision("Is this a bad decision?", false, 10);
        Assertions.assertFalse(testDecision.isGood());
    }

}
