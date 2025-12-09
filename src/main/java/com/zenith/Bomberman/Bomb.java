package com.zenith.Bomberman;

import java.awt.Graphics2D;
import java.io.File;

import javax.imageio.ImageIO;
import java.io.IOException;

public class Bomb extends Sprite {
    private int bombx;
    private int bomby;
    private int bombTimer = 0;
    private int bombExplodesIn = 100;
    private int fireLength = 2; // ISSUE WILL REEST, SAVE POWERUPS IN PLAYER INSTEAD!!

    public Bomb(int x, int y, int fireLength) {
        this.bombx = x;
        this.bomby = y;
        this.fireLength = fireLength;
    }

    public int getBombX() {
        return bombx;
    }

    public void drawBomb(GameEngine ge, Graphics2D g2) {
        try {
            g2.drawImage(ImageIO
                    .read(new File("data/Assets/Objects/Egg item.png")),
                    Math.round((this.getBombX() - (this.getBombX() % ge.tileSize))),
                    Math.round((this.getBombY() - (this.getBombY() % ge.tileSize))),
                    64,
                    64,
                    null);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Bomb incBombTimer() {
        this.bombTimer += 1;
        return this;
    }

    public void resetBombTimer() {
        this.bombTimer = 0;
    }

    public int getBombY() {
        return bomby;
    }

    public int getBombTimer() {
        return bombTimer;
    }

    public int getBombExplodesIn() {
        return bombExplodesIn;
    }

    public int getFireLength() {
        return fireLength;
    }
}