package com.csse3200.game.services;

public class SystemClockTimer {
    private final long durationMillis;
    private long startTimeMillis;
    private boolean isRunning = false;
    private boolean isTriggered = false;

    public SystemClockTimer(long durationMillis) {
        this.durationMillis = durationMillis;
    }

    public void start() {
        startTimeMillis = System.currentTimeMillis();
        isRunning = true;
        isTriggered = false;
    }

    public void update() {
        if (isRunning && !isTriggered) {
            long currentTimeMillis = System.currentTimeMillis();
            if (currentTimeMillis - startTimeMillis >= durationMillis) {
                triggerAction();
                isTriggered = true;
                isRunning = false;
            }
        }
    }

    private void triggerAction() {
        System.out.println("Timer is over! Triggering action...");
    }

    public boolean isRunning() {
        return isRunning;
    }
}

