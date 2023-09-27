package com.csse3200.game.areas;

import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.math.GridPoint2;
import com.csse3200.game.ai.tasks.AITaskComponent;
import com.csse3200.game.areas.ForestGameArea;
import com.csse3200.game.entities.factories.NPCFactory;
import com.csse3200.game.services.GameTime;
import com.csse3200.game.ai.tasks.DefaultTask;
import com.csse3200.game.ai.tasks.PriorityTask;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Random;

public class WaveManager extends AITaskComponent {
  private Logger logger = LoggerFactory.getLogger(WaveManager.class);
  private ForestGameArea forestGameArea;
  private List<WaveDefinition> waves;
  private GameTime timeSource;
  private int currentWaveIndex;
  private boolean waveInProgress;
  private float timeSinceLastSpawn;
  private float initialWaitTime;

//  private float timeElapsed;
//  private long startTime;
//  private long endTime;

  public WaveManager(List<WaveDefinition> waves, ForestGameArea forestGameArea) {
    this.waves = waves;
    this.forestGameArea = forestGameArea;
    this.currentWaveIndex = 0;
    this.timeSinceLastSpawn = 0;
    this.waveInProgress = false;
    this.initialWaitTime = 20.0f;
    this.timeElapsed = 0;

    // randomise waves for this level
  }

  public void startWave() {
    logger.info("Wave {} starting", currentWaveIndex);
    waveInProgress = true;
    WaveDefinition currentWave = waves.get(currentWaveIndex);
    // Add sound trigger here

    // for mobs in wave
    // spawn mob with delay
    int[] pickedLanes = new Random().ints(1, 7)
            .distinct().limit(5).toArray();
    for (int i = 0; i < currentWave.getSize(); i++) {
      GridPoint2 randomPos = new GridPoint2(19, pickedLanes[i]);
      forestGameArea.spawnMob(currentWave.getEntityToSpawn(0), randomPos);
    }
  }
/*
  @Override
  public void update(float delta) {
    timeElapsed = delta;

    if (timeElapsed >= initialWaitTime) {
      if (currentWaveIndex < waves.size()) {
        if (waveInProgress) {
          timeSinceLastSpawn += delta;
          WaveDefinition currentWave = waves.get(currentWaveIndex);

          if (timeSinceLastSpawn >= currentWave.getSpawnDelay()) {
            startWave();
            timeSinceLastSpawn = 0;
            currentWaveIndex += 1;
            logger.info("Starting new wave {}", currentWaveIndex);

            if (currentWaveIndex < waves.size()) {
              waveInProgress = false;
              logger.info("All waves completed - trigger level completed");
            }
          }

        } else {
          timeSinceLastSpawn = 0;
          startWave();
          waveInProgress = true;
          logger.info("No wave currently in progress, starting new wave");
        }
      }
    }
  }
*/
  @Override
  public int getPriority() {
    return 1;
  }
}