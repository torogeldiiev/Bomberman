/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenith.Bomberman;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;

/**
 *
 * @author bli
 */
public class Tile extends Sprite {
    public boolean collsion = false;
    public boolean canDropItem = false;
    public int tileNum;
    public boolean collectable = false;
    public boolean destructible = false;

    public Tile() {

    }

    public Tile(Tile clone) {
        collsion = clone.collsion;
        x = clone.x;
        y = clone.y;
        width = clone.width;
        height = clone.height;
        tileNum = clone.tileNum;
        collectable = clone.collectable;
        this.collsion = true;
        img = clone.img;
        destructible = clone.destructible;
        canDropItem = clone.canDropItem;
    }

    protected BufferedImage readImage(String path) throws IOException {
        return ImageIO.read(Files.newInputStream(Paths.get(path)));
    }

    public boolean collides(Player other) {
        if (collsion) {
            Rectangle rect = new Rectangle(x, y, width, height);
            Rectangle otherRect = other.solidArea;
            return rect.intersects(otherRect);
        }
        return false;
    }

    public boolean collides(Enemy other) {
        if (collsion) {
            Rectangle rect = new Rectangle(x, y, width, height);
            Rectangle otherRect = other.solidArea;
            return rect.intersects(otherRect);
        }
        return false;
    }

    public boolean collides(Tile other) {
        if (collsion) {
            Rectangle rect = new Rectangle(x, y, width, height);
            Rectangle otherRect = new Rectangle(other.x, other.y, other.width, other.height);
            return rect.intersects(otherRect);
        }
        return false;
    }

    public Rectangle getBorder() {
        return new Rectangle(x + 20, y, width - 20, height - 20);
    }

}
