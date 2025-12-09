package com.zenith.Bomberman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import static com.zenith.Bomberman.Constants.PlayerConstants.*;
import static com.zenith.Bomberman.Constants.Directions.*;
import com.zenith.Bomberman.Player;
import com.zenith.Bomberman.GameEngine;
import com.zenith.Bomberman.GameEngine;
import com.zenith.Bomberman.Player;
import com.zenith.Bomberman.Tile;
import com.zenith.Bomberman.Level;
import com.zenith.Bomberman.Enemy;

public class PumaTest {

    private Player player;
    private Level level;
    GameEngine ge = new GameEngine();
    Tile[][] lvlData = new Tile[1][];

    @BeforeEach
    public void setUp() {
        ge = new GameEngine();
        player = new Player(ge, 1);
        level = new Level(ge, 1);
        for (Tile[] row : lvlData) {
            row = new Tile[1];
            for (Tile cell : row) {
                cell = new Tile();
            }
        }
    }

    @Test
    public void testEnemyConstructor() {
        Enemy testEnemy = new Enemy(ge, 1, 1);
        assertEquals(testEnemy.getRectHeight(), 64);
        assertEquals(testEnemy.getRectWidth(), 64);
        assertEquals(testEnemy.getRectX(), 64);
        assertEquals(testEnemy.getRectY(), 64);
    }

    @Test
    public void testEnemyMovementTimer() {
        Enemy testEnemy = new Enemy(ge, 0, 0);
        testEnemy.move(1, 1, ge.getPlaying().getLevel().getBorders());
        assertEquals(testEnemy.getRectX(), 1);
        assertEquals(testEnemy.getRectY(), 1);
    }

    @Test
    public void test_increment_aniTick() {
        Enemy enemy = new Enemy(null, 0, 0);
        enemy.animationUpdate();
        assertEquals(1, enemy.getAniTick());
    }

    // When the enemy is moving right, calling collided() should change its
    // direction to left.
    @Test
    public void test_change_direction_right_to_left() {
        Enemy enemy = new Enemy(null, 0, 0);
        enemy.setDirection(RIGHT);
        enemy.collided();
        assertEquals(LEFT, enemy.getDirection());
    }

}
