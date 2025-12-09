package com.zenith.Bomberman;

import com.zenith.Bomberman.Player;
import com.zenith.Bomberman.GameEngine;
import com.zenith.Bomberman.GameEngine;
import com.zenith.Bomberman.Player;
import com.zenith.Bomberman.Tile;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;

import static org.junit.jupiter.api.Assertions.*;

public class PlayerTest {

    @Test
    public void testCollisionDetect_NoCollision() {
        GameEngine gameEngine = new GameEngine();
        Player player = new Player(gameEngine, 0);
        Rectangle[] borders = { new Rectangle(0, 0, 50, 50), new Rectangle(100, 100, 50, 50) };

        boolean collision = player.collisionDetect(borders);

        assertTrue(collision);
    }

    @Test
    public void testCollisionDetect_WithCollision() {
        GameEngine gameEngine = new GameEngine();
        Player player = new Player(gameEngine, 1);
        Rectangle[] borders = { new Rectangle(-64, -64, 50, 50), new Rectangle(100, 100, 50, 50) };

        boolean collision = player.collisionDetect(borders);

        assertFalse(collision);
    }

    @Test
    public void testPlayerInitialization() {
        GameEngine gameEngine = new GameEngine();
        Player player = new Player(gameEngine, 0);

        assertEquals(-64, player.getX());
        assertEquals(-64, player.getY());
        assertEquals(3, player.health);
        assertEquals(64, player.getRectHeight());
        assertEquals(64, player.getRectWidth());
        assertNotNull(player.solidArea);
    }

    @Test
    public void testPlayerReset() {
        GameEngine gameEngine = new GameEngine();
        Player player = new Player(gameEngine, 0);

        Player resetPlayer = player.playerReset();

        assertEquals(-64, resetPlayer.getX());
        assertEquals(-64, resetPlayer.getY());
        assertEquals(0, resetPlayer.getState());
        assertEquals(2, resetPlayer.health);
    }
}
