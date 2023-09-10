package com.csse3200.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.csse3200.game.ai.tasks.AITaskComponent;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.TouchAttackComponent;
import com.csse3200.game.components.player.HumanAnimationController;
import com.csse3200.game.components.tasks.human.HumanWanderTask;
import com.csse3200.game.components.tasks.scanner.ScannerTask;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.configs.BaseEntityConfig;
import com.csse3200.game.entities.configs.EngineerConfigs;
import com.csse3200.game.files.FileLoader;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.HitboxComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.physics.components.PhysicsMovementComponent;
import com.csse3200.game.rendering.AnimationRenderComponent;

/**
 * Factory to create scanner entities that determine whether to spawn engineer entities.
 *
 * These do not interact with any of the entities in the game area except to detect other entities
 *
 */
public class GapScannerFactory {

  /**
   * Creates a scanner entity

   * @return entity
   */
  public static Entity createScanner() {
    Entity scanner = new Entity();

    AITaskComponent aiComponent = new AITaskComponent();

    scanner
            .addComponent(new PhysicsComponent())
            .addComponent(aiComponent);

    scanner.getComponent(AITaskComponent.class).addTask(new ScannerTask());
    return scanner;
  }

  private GapScannerFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}
