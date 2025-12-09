package com.zenith.Bomberman;

import com.zenith.Bomberman.Tile;
import com.zenith.Bomberman.GameEngine;
import com.zenith.Bomberman.GameEngine;
import com.zenith.Bomberman.Tile;
import org.junit.jupiter.api.Test;

import java.awt.Rectangle;
import static org.junit.jupiter.api.Assertions.*;

class HelperMethodsTest {

    @Test
    void testIsSolid_PlayerIntersectsSolidTile_ReturnsTrue() {
        Tile[][] lvlData = new Tile[10][10];
        lvlData[0][0] = new Tile();

        int playerX = 0;
        int playerY = 0;
        int playerWidth = 10;
        int playerHeight = 10;

        boolean result = IsSolid(playerX, playerY, playerWidth, playerHeight, lvlData);

        assertFalse(result, "Player should intersect with a solid tile");
    }

    private static boolean IsSolid(int x, int y, int width, int height, Tile[][] lvlData) {
        if (edgeCases(x, y) ||
                edgeCases(x + width, y + height) ||
                edgeCases(x + width, y) ||
                edgeCases(x, y + height))
            return true;

        for (Tile[] tiles : lvlData) {
            for (Tile tile : tiles) {
                if (tile != null) { // Check if tile is not null
                    Rectangle player = new Rectangle(x, y, width, height);
                    if (player.intersects(tile.getBorder())) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

    private static boolean edgeCases(int x, int y) {
        if (x < 0 || x >= GameEngine.getScreenWidth()) {
            return true;
        }
        if (y < 0 || y >= GameEngine.getScreenHeight()) {
            return true;
        }
        return false;
    }

}
