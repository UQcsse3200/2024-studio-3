package com.csse3200.game.components.moral;

import com.csse3200.game.components.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class MoralDecision extends Component {

    private static final Logger logger = LoggerFactory.getLogger(MoralDecision.class);

    private final List<Decision> ListOfDecisions = new ArrayList<>();
    private Integer currentMorality = 0;

    public boolean addDecision(String question) {
        ListOfDecisions.add(new Decision(question, true, 10));
        return true;
    }

    public void addDecision(Decision decision) {
        ListOfDecisions.add(decision);
    }

    public void addDecision(String question, boolean isGood, int decisionPoints) {
        ListOfDecisions.add(new Decision(question, isGood, decisionPoints));
    }

    public boolean getDecision(int index) {
        return ListOfDecisions.get(index).getDecision();
    }

    public boolean getDecision(String question) {
        for (Decision decision : ListOfDecisions) {
            if (decision.getStatement().equals(question)) {
                return decision.getDecision();
            }
        }
        return false;
    }

    public Integer getCurrentMorality() {
        return currentMorality;
    }

    public void setCurrentMorality(Integer currentMorality) {
        this.currentMorality = currentMorality;
    }

    public boolean setDecision(int index, boolean decision) {
        logger.debug("Setting decision for index: {} to {}", index, decision);
        ListOfDecisions.get(index).setDecision(decision);
        currentMorality += decision ? ListOfDecisions.get(index).getDecisionPoints() : -ListOfDecisions.get(index).getDecisionPoints();
        return true;
    }

    public List<Decision> getListOfDecisions() {
        return ListOfDecisions;
    }

    public void clearDecisions() {
        ListOfDecisions.clear();
    }

    public void removeDecision(int index) {
        ListOfDecisions.remove(index);
    }

    public void removeDecision(Decision decision) {
        ListOfDecisions.remove(decision);
    }

    public void removeDecision(String question) {
        for (Decision decision : ListOfDecisions) {
            if (decision.getStatement().equals(question)) {
                ListOfDecisions.remove(decision);
                return;
            }
        }
    }
}
