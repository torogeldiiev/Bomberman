package com.zenith.Bomberman;

import com.zenith.Bomberman.GameEngine;
import com.zenith.Bomberman.GameEngine;
import com.zenith.Bomberman.Player;
import com.zenith.Bomberman.Player;
import com.zenith.Bomberman.Tile;
import com.zenith.Bomberman.Tile;
import org.junit.jupiter.api.Test;
import java.awt.Rectangle;
import static org.junit.jupiter.api.Assertions.*;

public class TileTest {

    @Test
    public void testDefaultConstructor() {
        Tile tile = new Tile();
        assertFalse(tile.collsion);
        assertEquals(0, tile.tileNum);
        assertFalse(tile.collectable);
    }

    @Test
    public void testCollides() {
        Tile tile = new Tile();
        GameEngine gameEngine = new GameEngine();
        tile.collsion = true;

        Player player = new Player(gameEngine, 1);
        player.solidArea = new Rectangle(tile.getX(), tile.getY(), tile.getWidth(), tile.getHeight()); // Player's solid
                                                                                                       // area
                                                                                                       // intersects
                                                                                                       // with tile
        assertFalse(tile.collides(player));

    }

    @Test
    public void testGetBorder() {
        Tile tile = new Tile();
        int x = 10;
        int y = 20;
        int width = 30;
        int height = 40;

        Rectangle expectedBorder = new Rectangle(x, y, width, height);
        assertNotEquals(expectedBorder, tile.getBorder());
    }
}
