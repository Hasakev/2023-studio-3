package com.csse3200.game.entities.factories;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.ai.tasks.AITaskComponent;
import com.csse3200.game.components.CombatStatsComponent;
import com.csse3200.game.components.npc.GhostAnimationController;
import com.csse3200.game.components.npc.XenoAnimationController;
import com.csse3200.game.components.TouchAttackComponent;
import com.csse3200.game.components.tasks.ShootTask;
import com.csse3200.game.components.tasks.WanderTask;
import com.csse3200.game.entities.Entity;
import com.csse3200.game.entities.Melee;
import com.csse3200.game.entities.PredefinedWeapons;
import com.csse3200.game.entities.Weapon;
import com.csse3200.game.entities.configs.BaseEnemyConfig;
import com.csse3200.game.entities.configs.BaseEntityConfig;
import com.csse3200.game.entities.configs.BossKingConfigs;
import com.csse3200.game.entities.configs.NPCConfigs;
import com.csse3200.game.files.FileLoader;
import com.csse3200.game.physics.PhysicsLayer;
import com.csse3200.game.physics.PhysicsUtils;
import com.csse3200.game.physics.components.ColliderComponent;
import com.csse3200.game.physics.components.HitboxComponent;
import com.csse3200.game.physics.components.PhysicsComponent;
import com.csse3200.game.physics.components.PhysicsMovementComponent;
import com.csse3200.game.rendering.AnimationRenderComponent;
import com.csse3200.game.rendering.TextureRenderComponent;
import com.csse3200.game.services.ServiceLocator;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * Factory to create non-playable character (NPC) entities with predefined components.
 *
 * <p>Each NPC entity type should have a creation method that returns a corresponding entity.
 * Predefined entity properties can be loaded from configs stored as json files which are defined in
 * "NPCConfigs".
 *
 * <p>If needed, this factory can be separated into more specific factories for entities with
 * similar characteristics.
 */
public class NPCFactory {
  private static final NPCConfigs configs =
      FileLoader.readClass(NPCConfigs.class, "configs/NPCs.json");

  /**
   * Creates a ghost entity.
   *
   * @param target entity to chase
   * @return entity
   */
  public static Entity createGhost(Entity target) {
    Entity ghost = createBaseNPC(target);
    BaseEntityConfig config = configs.ghost;
    /**
    AnimationRenderComponent animator =
        new AnimationRenderComponent(
            ServiceLocator.getResourceService().getAsset("images/ghost.atlas", TextureAtlas.class));
    animator.addAnimation("angry_float", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("float", 0.1f, Animation.PlayMode.LOOP);
  **/
    ghost
        .addComponent(new CombatStatsComponent(config.health, config.baseAttack))
     //   .addComponent(animator)
             .addComponent(new TextureRenderComponent("images/satyr.png"));
     //   .addComponent(new GhostAnimationController());

    ghost.getComponent(TextureRenderComponent.class).scaleEntity();

    return ghost;
  }
  /**
   * Creates a xeno grunt entity.
   *
   * @param target entity to chase
   * @return entity
   */
  public static Entity createXenoGrunt(Entity target) {
    Entity xenoGrunt = createBaseNPC(target);
    BaseEnemyConfig config = configs.xenoGrunt;
    ArrayList<Melee> melee = new ArrayList<>(Arrays.asList(PredefinedWeapons.sword, PredefinedWeapons.kick));
    ArrayList<Weapon> projectiles = new ArrayList<>(Arrays.asList(PredefinedWeapons.fireBall, PredefinedWeapons.hurricane));
    ArrayList<Integer> drops = new ArrayList<>(Arrays.asList(1, 2));

    AnimationRenderComponent animator =
            new AnimationRenderComponent(
                    ServiceLocator.getResourceService().getAsset("images/xenoGruntRunning.atlas", TextureAtlas.class));
    animator.addAnimation("xeno_run", 0.1f, Animation.PlayMode.LOOP);
    animator.addAnimation("xeno_shoot", 0.1f, Animation.PlayMode.NORMAL);
    animator.addAnimation("xeno_melee", 0.1f, Animation.PlayMode.NORMAL);
    animator.addAnimation("xeno_die", 0.1f, Animation.PlayMode.NORMAL);

    xenoGrunt
            .addComponent(new CombatStatsComponent(config.fullHeath, config.baseAttack, drops, melee, projectiles))
            .addComponent(animator)
            .addComponent(new XenoAnimationController());

    xenoGrunt.getComponent(AnimationRenderComponent.class).scaleEntity();

    return xenoGrunt;
  }

  /**
   * Creates a generic NPC to be used as a base entity by more specific NPC creation methods.
   *
   * @return entity
   */
  public static Entity createBaseNPC(Entity target) {
    AITaskComponent aiComponent =
        new AITaskComponent()
            .addTask(new WanderTask(new Vector2(2f, 2f), 2f))
            .addTask(new ShootTask(target, 10, 3f, 4f));
            //.addTask(new ChaseTask(target, 10, 3f, 4f));
    Entity npc =
        new Entity()
            .addComponent(new PhysicsComponent())
            .addComponent(new PhysicsMovementComponent())
            .addComponent(new ColliderComponent())
            .addComponent(new HitboxComponent().setLayer(PhysicsLayer.NPC))
            .addComponent(new TouchAttackComponent(PhysicsLayer.PLAYER, 1.5f))
            .addComponent(aiComponent);

    PhysicsUtils.setScaledCollider(npc, 0.9f, 0.4f);
    return npc;
  }

  private NPCFactory() {
    throw new IllegalStateException("Instantiating static util class");
  }
}


