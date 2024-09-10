package com.csse3200.game.components.npc;

public class CountdownManager {
    private int initialCountDown;
    private int currentCountDown;
    private boolean countdownComplete;

    public CountdownManager(int initialCountDown) {
        this.initialCountDown = initialCountDown;
        this.currentCountDown = initialCountDown;
        this.countdownComplete = false;
    }

    public void start() {
        // Reset countdown and start
        this.currentCountDown = initialCountDown;
        this.countdownComplete = false;
    }

    public void update() {
        if (!countdownComplete) {
            if (currentCountDown > 0) {
                currentCountDown--;
                // Assuming update is called once per second
                if (currentCountDown <= 0) {
                    countdownComplete = true;
                    currentCountDown = 0;
                }
            }
        }
    }

    public void setCountDown(int countDown) {
        this.initialCountDown = countDown;
        this.currentCountDown = countDown;
        this.countdownComplete = false;
    }

    public boolean isCountdownComplete() {
        return countdownComplete;
    }

    public void reset() {
        this.currentCountDown = initialCountDown;
        this.countdownComplete = false;
    }
}
