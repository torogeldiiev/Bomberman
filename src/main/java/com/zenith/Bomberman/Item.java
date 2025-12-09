package com.zenith.Bomberman;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Item extends Sprite{

    public Item(int x, int y, String path) {
        this.x = x;
        this.y = y;
        try {
            this.img = ImageIO.read(new File(path));
            this.width = img.getWidth();
            this.height = img.getHeight();
            this.solidArea = new Rectangle(x, y, width, height);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void draw(GameEngine ge, Graphics2D g2) {
        g2.drawImage(img, x, y, width, height, null);
    }
}
