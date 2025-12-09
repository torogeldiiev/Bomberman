/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenith.Bomberman;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;

/**
 *
 * @author bli
 */
public class Sprite {
    /**
     * The coordinates of the top left corner of the sprite
     */
    protected int borderX;
    protected int borderY;
    protected int BorderWidth;
    protected int BorderHeight;

    protected int x;
    protected int y;
    protected int width;
    protected int height;

    public BufferedImage img;

    public Rectangle solidArea;

    public int getX(){
        return x;
    }
    public int getY(){
        return y;
    }

    public int getHeight(){
        return height;
    }
    public int getWidth(){
        return width;
    }
    public void setX(int x){
        this.x = x;
    }
    public void setY(int y){
        this.y = y;
    }
}
