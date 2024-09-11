package com.csse3200.game.components.moral;

import com.csse3200.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * The MoralDecision class manages a list of moral decisions and tracks the current morality score.
 */
public class MoralDecision extends Component {

    private static final Logger logger = LoggerFactory.getLogger(MoralDecision.class);

    private final List<Decision> ListOfDecisions = new ArrayList<>();
    private Integer currentMorality = 0;

    /**
     * Adds a new Question, assuming it is good and worth 10 points.
     *
     * @param question the question or statement of the decision
     * @return true if the decision was added successfully
     */
    public boolean addQuestion(String question) {
        ListOfDecisions.add(new Decision(question, true, 10));
        return true;
    }

    /**
     * Adds a new Question with the specified question, goodness, and decision points.
     *
     * @param question the question or statement of the decision
     * @param isGood whether the decision is good
     * @param decisionPoints points associated with the decision
     * @return true if the decision was added successfully
     */
    public boolean addQuestion(String question, boolean isGood, int decisionPoints) {
        ListOfDecisions.add(new Decision(question, isGood, decisionPoints));
        return true;
    }

    /**
     * Adds an existing Decision object to the list of decisions.
     *
     * @param decision the Decision object to add
     */
    public void addDecision(Decision decision) {
        ListOfDecisions.add(decision);
    }

    /**
     * Returns the result of the decision at the specified index.
     *
     * @param index the index of the decision
     * @return the result of the decision, or false if the decision is not found
     */
    public boolean getDecision(int index) {
        return ListOfDecisions.get(index).getDecision();
    }

    /**
     * Returns the result of the decision with the specified question.
     *
     * @param question the question or statement of the decision
     * @return the result of the decision, or false if the decision is not found
     */
    public boolean getDecision(String question) {
        for (Decision decision : ListOfDecisions) {
            if (decision.getStatement().equals(question)) {
                return decision.getDecision();
            }
        }
        return false;
    }

    /**
     * Returns the current morality score.
     *
     * @return the current morality score
     */
    public Integer getCurrentMorality() {
        return currentMorality;
    }

    /**
     * Sets the current morality score.
     *
     * @param currentMorality the new morality score
     */
    public void setCurrentMorality(Integer currentMorality) {
        this.currentMorality = currentMorality;
    }

    /**
     * Sets the decision result at the specified index and updates the morality score.
     *
     * @param index the index of the decision
     * @param decision the result of the decision
     * @return true if the decision was set successfully
     */
    public boolean setDecision(int index, boolean decision) {
        logger.debug("Setting decision for index: {} to {}", index, decision);
        ListOfDecisions.get(index).setDecision(decision);
        currentMorality += ListOfDecisions.get(index).getDecisionPoints();
        return true;
    }

    /**
     * Returns the list of decisions.
     *
     * @return the list of decisions
     */
    public List<Decision> getListOfDecisions() {
        return ListOfDecisions;
    }

    /**
     * Returns the statement of the decision at the specified index.
     *
     * @param index the index of the decision
     * @return the statement of the decision
     */
    public String getDecisionStatement(int index) {
        return ListOfDecisions.get(index).getStatement();
    }

    /**
     * Clears all decisions from the list.
     */
    public void clearDecisions() {
        ListOfDecisions.clear();
        currentMorality = 0;
    }

    /**
     * Removes the decision at the specified index.
     *
     * @param index the index of the decision to remove
     */
    public void removeDecision(int index) {
        ListOfDecisions.remove(index);
    }

    /**
     * Removes the specified Decision object from the list.
     *
     * @param decision the Decision object to remove
     */
    public void removeDecision(Decision decision) {
        ListOfDecisions.remove(decision);
    }

    /**
     * Removes the decision with the specified question from the list.
     *
     * @param question the question or statement of the decision to remove
     */
    public void removeDecision(String question) {
        for (Decision decision : ListOfDecisions) {
            if (decision.getStatement().equals(question)) {
                ListOfDecisions.remove(decision);
                return;
            }
        }
    }
}