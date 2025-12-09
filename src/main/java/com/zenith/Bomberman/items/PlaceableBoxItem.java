package com.zenith.Bomberman.items;

import java.awt.Graphics2D;

import com.zenith.Bomberman.GameEngine;
import com.zenith.Bomberman.Item;

public class PlaceableBoxItem extends Item {
    public PlaceableBoxItem(int x, int y) {
        super(x, y, "data/Assets/Objects/Box.jpg");
    }

    public void drawFireRange(GameEngine ge, Graphics2D g2) {
        g2.drawImage(img, x, y, width, height, null);
    }
}
