package com.csse3200.game.services;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.TimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** Controls the game time */
public class GameTime {
  private static Logger logger = LoggerFactory.getLogger(GameTime.class);
  private final long startTime;
  private float timeScale = 1f;
  private boolean isPaused = false;
  private long pausedAt;
  private long totalPauseTime;

  public GameTime() {
    startTime = TimeUtils.millis();
    logger.debug("Setting game start time to {}", startTime);
  }

  /**
   * Set the speed of time passing. This affects getDeltaTime()
   *
   * @param timeScale Time scale, where normal speed is 1.0, no time passing is 0.0
   */
  public void setTimeScale(float timeScale) {
    logger.debug("Setting time scale to {}", timeScale);
    this.timeScale = timeScale;
  }

  /**
   * Pause method for timer
   */
  public void pause() {
    if(!isPaused) {
      logger.info("Time Paused");
      isPaused = true;
      pausedAt = TimeUtils.millis();
    }
  }

  /**
   * Resume method for timer
   */
  public void resume() {
    long resumeAt;
    if(isPaused) {
      logger.info("Time Resumed");
      resumeAt = TimeUtils.millis();
      totalPauseTime += (resumeAt - pausedAt);
      isPaused = false;
    }
  }

  /** @return time passed since the last frame in seconds, scaled by time scale. */
  public float getDeltaTime() {
    if(isPaused){
      return 0;
    }
    return Gdx.graphics.getDeltaTime() * timeScale;
  }

  public boolean isPaused(){
    return isPaused;
  }

  /** @return time passed since the last frame in seconds, not affected by time scale. */
  public float getRawDeltaTime() {
    return Gdx.graphics.getDeltaTime();
  }

  /** @return time passed since the game started in milliseconds */
  public long getTime() {
    if(isPaused){
      return pausedAt - startTime - totalPauseTime;
    }
    return TimeUtils.timeSinceMillis(startTime) - totalPauseTime;
  }

  public long getTimeSince(long lastTime) {
    return getTime() - lastTime;
  }

  public float getTimeScale() {
    return timeScale;
  }
}
