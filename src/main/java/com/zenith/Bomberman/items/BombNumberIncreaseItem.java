package com.zenith.Bomberman.items;

import com.zenith.Bomberman.GameEngine;
import com.zenith.Bomberman.Item;

import java.awt.*;

public class BombNumberIncreaseItem extends Item {

    public BombNumberIncreaseItem(int x, int y) {
        super(x, y, "data/Assets/Objects/bomb-increase.png");
    }

    public void drawBombNumber(GameEngine ge, Graphics2D g2) {
        g2.drawImage(img, x, y, width, height, null);
    }
}
