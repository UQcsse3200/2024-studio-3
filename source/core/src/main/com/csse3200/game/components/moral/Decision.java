package com.csse3200.game.components.moral;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * The Decision class represents a moral decision with a statement,
 * whether it is good or bad, and associated decision points.
 */
public class Decision {

    private static final Logger logger = LoggerFactory.getLogger(Decision.class);

    private final String statement; // The statement of the decision
    private final boolean isGood; // Indicates if the decision is good
    private MoralEnum.Value decisionMade; // The result of the decision
    private int decisionPoints; // Points associated with the decision

    /**
     * Constructs a new Decision with the specified statement, goodness, and decision points.
     *
     * @param statement the statement of the decision
     * @param isGood whether the decision is good
     * @param decisionPoints points associated with the decision
     */
    public Decision(String statement, boolean isGood, int decisionPoints) {
        this.statement = statement;
        this.isGood = isGood;
        this.decisionPoints = decisionPoints;
        this.decisionMade = MoralEnum.Value.Pending;
    }

    /**
     * Returns the statement of the decision.
     *
     * @return the statement of the decision
     */
    public String getStatement() {
        return statement;
    }

    /**
     * Returns the decision made.
     *
     * @return the decision made. If the decision has not been made, returns false.
     */
    public boolean getDecision() {
        if (this.decisionMade == MoralEnum.Value.Pending) {
            logger.error("Decision has not been made yet");
            return false;
        } else {
            return this.decisionMade == MoralEnum.Value.Yes;
        }
    }

    /**
     * Returns the points associated with the decision.
     *
     * @return the decision points
     */
    public int getDecisionPoints() {
        if (isGood) {
            return this.decisionPoints;
        } else {
            return -this.decisionPoints;
        }
    }

    /**
     * Returns whether the decision is good.
     *
     * @return true if the decision is good, false otherwise
     */
    public boolean isGood() {
        return this.isGood;
    }

    /**
     * Sets the decision to the specified value.
     * The decision can only be set once.
     *
     * @param decision the decision to set
     * @return true if the decision was set successfully
     */
    public boolean setDecision(boolean decision) {
        if (this.decisionMade != MoralEnum.Value.Pending) {
            logger.error("Decision has already been made");
            return false;
        } else {
            this.decisionMade = decision ? MoralEnum.Value.Yes : MoralEnum.Value.No;
            return true;
        }
    }

    /**
     * Sets the points associated with the decision.
     *
     * @param points the points to set
     * @return true if the points were set successfully
     */
    public boolean setPoints(int points) {
        if (points < 0) {
            logger.error("Points must be non-negative");
            return false;
        } else {
            this.decisionPoints = points;
            return true;
        }
    }
}