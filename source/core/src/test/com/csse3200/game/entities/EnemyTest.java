package com.csse3200.game.entities;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.badlogic.gdx.math.Vector2;
import com.csse3200.game.components.Component;
import com.csse3200.game.entities.configs.BaseEnemyConfig;
import com.csse3200.game.extensions.GameExtension;
import com.csse3200.game.services.ServiceLocator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


@ExtendWith(GameExtension.class)
public class EnemyTest {

    private String configString = "Enemy: %s Abilities: %s Drops: %s Views: %s State:" +
            " %s Speed: %s Full Health: %s Current Health: %s Base Attack: %s";
    private String baseConfigString = "Enemy: %s Abilities: %s Drops: %s Views: %s State: %s" +
            " Speed: 1 Full Health: 1 Current Health: 1 Base Attack: 0";

    // Check that both constructors create an enemy with the correct values
    @Test
    void shouldCreateEnemy() {
        List<Integer> drops = List.of(1, 2);
        List<String> views = List.of("a", "b", "c");
        BaseEnemyConfig enemyB = new BaseEnemyConfig(drops, views, "c");
        int id = enemyB.getId();

        BaseEnemyConfig enemy = new BaseEnemyConfig(drops, views, "c");
        assertEquals(String.format(baseConfigString, id + 1, "c", drops, views, "a"),
                enemy.toString());

        BaseEnemyConfig enemy2 = new BaseEnemyConfig(
                4, 5, drops, views, "c", 6);

        assertEquals(
                String.format(configString, id + 2, "c", drops, views, "a", "4", "5", "5", "6"),
                enemy2.toString());
    }

    // Check the generated ids are unique and are incremented correctly
    @Test
    void idGeneration() {
        List<Integer> drops = List.of(1, 2);
        List<String> views = List.of("a", "b", "c");
        BaseEnemyConfig enemy1 = new BaseEnemyConfig(drops, views, "c");

        BaseEnemyConfig enemy2 = new BaseEnemyConfig(drops, views, "c");
        assertNotEquals(enemy1.getId(), enemy2.getId());
        assertEquals(enemy1.getId() + 1, enemy2.getId());
    }

    // Check exceptions are thrown when invalid values are passed
    @Test
    void notEnoughViewException() {
        List<Integer> drops = List.of(1, 2);
        List<String> views = List.of("a", "b");
        assertThrows(IllegalArgumentException.class, () -> {
            BaseEnemyConfig enemy1 = new BaseEnemyConfig(drops, views, "c");
        });
        assertThrows(IllegalArgumentException.class, () -> {
            BaseEnemyConfig enemy2 = new BaseEnemyConfig(1, 1, drops, views, "c", 1);
        });

    }

    /* Check takeDamage will reduce the health of an enemy */
    @Test
    void testTakeDamage() {
        List<Integer> drops = List.of(1, 2);
        List<String> views = List.of("a", "b", "c");
        BaseEnemyConfig enemy = new BaseEnemyConfig(drops, views, "c");
        int beforeDamage = enemy.getHealth();
        enemy.takeDamage(1);

        assertEquals(enemy.getHealth() + 1, beforeDamage);
    }

    /* Check that changing the damage will change to the correct state */
    @Test
    void testDamageStateChange() {
        List<Integer> drops = List.of(1, 2);
        List<String> views = List.of("a", "b", "c");
        BaseEnemyConfig enemy = new BaseEnemyConfig(1, 10, drops, views, "c", 1);
        assertEquals("a", enemy.getState());

        enemy.takeDamage(4);
        assertEquals("b", enemy.getState());

        enemy.takeDamage(4);
        assertEquals("c", enemy.getState());
    }

    /* Check that drop will return a value within the provided drop options */
    @Test
    void testDrop() {
        List<Integer> drops = List.of(1, 2);
        List<String> views = List.of("a", "b", "c");
        BaseEnemyConfig enemy = new BaseEnemyConfig(drops, views, "c");
        assertTrue(drops.contains(enemy.drop()));
    }

    /* Check that the drop is Random. This test is based on probability and may return false.
     * If false, run again before making changes*/
    @Test
    void dropRandom() {
        List<Integer> drops = List.of(1, 2);
        List<String> views = List.of("a", "b", "c");
        BaseEnemyConfig enemy = new BaseEnemyConfig(drops, views, "c");
        List<Integer> dropped = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            dropped.add(enemy.drop());
        }
        System.out.println(dropped);
        assertFalse(Arrays.stream(drops.toArray()).allMatch(t -> t == dropped.get(0)));
    }

    //TODO: The next tests cannot be implemented until die() and attack() are created
    @Test
    void testDie() {}

    @Test
    void takeDamageToDie() {}

    @Test
    void testAttack() {}

    @Test
    void checkAttackRandom() {}

}
