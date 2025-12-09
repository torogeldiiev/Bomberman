package com.zenith.Bomberman;

import java.awt.Rectangle;

public class HelperMethods {

    public static boolean CanMoveHere(int x, int y, int width, int height, Tile[][] lvlData) {
        if (!IsSolid(x, y, width, height, lvlData))
            return true;
        return false;
    }

    private static boolean edgeCases(int x, int y) {
        if (x < 0 || x >= GameEngine.screenWidth) {
            return true;
        }
        if (y < 0 || y >= GameEngine.screenHeight) {
            return true;
        }
        return false;
    }

    private static boolean IsSolid(int x, int y, int width, int height, Tile[][] lvlData) {
        if (edgeCases(x, y) ||
                edgeCases(x + width, y + height) ||
                edgeCases(x + width, y) ||
                edgeCases(x, y + height))
            return true;
        for (Tile[] tiles : lvlData) {
            for (Tile tile : tiles) {
                Rectangle player = new Rectangle(x, y, width, height);
                if (player.intersects(tile.getBorder())) {
                    return true;
                }
            }
        }

        return false;
    }

    public static void printer(String input) {
        System.out.println(input);
    }
}
