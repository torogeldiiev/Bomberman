package com.zenith.Bomberman.items;

import com.zenith.Bomberman.GameEngine;
import com.zenith.Bomberman.Item;

import java.awt.*;

public class DetonatorItem extends Item {
    
    public DetonatorItem(int x, int y){
        super(x, y, "data/Assets/Objects/detonator.jpg");
    }

    public void drawFireRange(GameEngine ge, Graphics2D g2) {
        g2.drawImage(img, x, y, width, height, null);
    }
}
