package com.csse3200.game.entities.factories;

import com.csse3200.game.ai.tasks.AITaskComponent;
import com.csse3200.game.components.tasks.waves.WaveClass;
import com.csse3200.game.components.tasks.waves.WaveTask;
import com.csse3200.game.entities.Entity;

import java.util.HashMap;


public class WaveFactory {
    /**
     * Create a Wave entity.
     * @return entity
     */
    public static Entity createWave() {
        HashMap<String, Integer> mobs = new HashMap<>();
        mobs.put("Xeno", 5);
        AITaskComponent aiComponent =
                new AITaskComponent()
                        .addTask(new WaveTask());
        return new WaveClass(mobs, 1).addComponent(aiComponent);
    }

    private WaveFactory() {
        throw new IllegalStateException("Instantiating static util class");
    }
}
