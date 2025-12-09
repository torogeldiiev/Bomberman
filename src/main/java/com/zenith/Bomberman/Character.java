package com.zenith.Bomberman;

import java.awt.image.BufferedImage;

public class Character extends Sprite {
    public int direction;

    public BufferedImage[] rightAni = new BufferedImage[6];
    public BufferedImage[] idleAni = new BufferedImage[6];
    protected int aniTick = 0, aniIndex = 0, aniSpeed = 6;
    public int spriteFrame = 0;
    public int spriteNum = 1;
    protected int flipX = 0, flipW = 1;
    protected int speed;

    public int getDirection() {
        return direction;
    }
}
