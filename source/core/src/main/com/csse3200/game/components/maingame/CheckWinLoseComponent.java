package com.csse3200.game.components.maingame;

import com.csse3200.game.components.Component;

public class CheckWinLoseComponent extends Component {

    private float totalMoneyMade;
    private float winAmount;
    private float loseThreshold;
    private float mealQuality;
    private int storeRating;

    public CheckWinLoseComponent(float totalMoneyMade, float winAmount, float loseThreshold, float mealQuality) {
        this.totalMoneyMade = totalMoneyMade;
        this.winAmount = winAmount;
        this.loseThreshold = loseThreshold;
        this.mealQuality = mealQuality;
        this.storeRating = calculateStoreRating(); // can have this displayed on the screen 'at all times'?
    }

    public void updateGameState(float totalMoneyMade, float mealQuality) {
        this.totalMoneyMade = totalMoneyMade;
        this.mealQuality = mealQuality;
        this.storeRating = calculateStoreRating();
    }

    public int calculateStoreRating() {
        if (mealQuality >= 0 && mealQuality < 20) {
            return 1;
        } else if (mealQuality >= 20 && mealQuality < 40) {
            return 2;
        } else if (mealQuality >= 40 && mealQuality < 60) {
            return 3;
        } else if (mealQuality >= 60 && mealQuality < 80) {
            return 4;
        } else {
            return 5;
        }
    }

    public String checkGameState() {
        if (checkLose()) {
            triggerLoseState();
            return "LOSE";
        } else if (checkWin()) {
            triggerWinState();
            return "WIN";
        } else {
            return "GAME_IN_PROGRESS";
        }
    }

    private boolean checkLose() {
        if (storeRating <= 2) {
            return true;
        }
        if (totalMoneyMade < loseThreshold) {
            return true;
        }
        return false;
    }

    private boolean checkWin() {
        if (totalMoneyMade >= winAmount) {
            return true;
        }
        return false;
    }

    private void triggerLoseState() {
        // Need to add logic for losing (trigger cutscenes, end game)
    }

    private void triggerWinState() {
        // Need to add logic for winning (trigger cutscenes, end game)
    }
}
