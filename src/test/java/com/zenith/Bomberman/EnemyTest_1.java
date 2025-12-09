package com.zenith.Bomberman;

import com.zenith.Bomberman.Constants;
import com.zenith.Bomberman.Constants;
import com.zenith.Bomberman.Enemy;
import com.zenith.Bomberman.GameEngine;
import org.junit.jupiter.api.Test;

import static com.zenith.Bomberman.Constants.Directions.RIGHT;
import static org.junit.jupiter.api.Assertions.*;
import com.zenith.Bomberman.Enemy;
import com.zenith.Bomberman.GameEngine;

public class EnemyTest_1 {
    @Test
    public void testSetDirection() {
        GameEngine ge = new GameEngine();
        Enemy enemy = new Enemy(ge, 0, 0);
        enemy.setDirection(RIGHT);
        assertEquals(enemy.getDirection(), RIGHT);
    }

    @Test
    public void testSetStartValue() {
        GameEngine ge = new GameEngine();
        Enemy enemy = new Enemy(ge, 0, 0);
        enemy.setStartValue(0, 0);

        assertEquals(-64, enemy.getX());
        assertEquals(-64, enemy.getY());
        assertEquals(enemy.getX() + 64, enemy.getRectX());
        assertEquals(enemy.getY() + 64, enemy.getRectY());
        assertEquals(Constants.Directions.RIGHT, enemy.direction);
        assertEquals(Constants.PlayerConstants.IDLE, enemy.getState());
    }

    @Test
    public void testGetEnemyImage() {
        GameEngine ge = new GameEngine();
        Enemy enemy = new Enemy(ge, 0, 0);
        assertDoesNotThrow(() -> enemy.getEnemyImage());
    }
}
