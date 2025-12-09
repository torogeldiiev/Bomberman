package com.zenith.Bomberman;

import java.io.File;
import java.io.IOException;

import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.awt.Graphics2D;

public class Fire extends Tile {
    private int fireTimer = 0;
    private int fireOut = 50;
    public BufferedImage[] idleAni = new BufferedImage[7];
    protected int aniTick = 0, aniIndex = 0, aniSpeed = 7;

    public Fire(int x, int y) {
        this.x = x;
        this.y = y;
        this.width = 64;
        this.height = 64;
        this.collsion = true;
        getFireImages();

    }

    private void getFireImages() {
        try {
            img = ImageIO
                    .read(new File("data/Assets/Objects/fire.png"));
            for (int i = 0; i < 7; i++) {
                idleAni[i] = img.getSubimage(i * 128, 0, width * 2, height * 2);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public BufferedImage getCurAnimaionBufferedImage() {
        return idleAni[aniIndex];
    }

    public void draw(Graphics2D g2) {
        g2.drawImage(this.getCurAnimaionBufferedImage(), x - width / 2, y - height + 20, width * 2,
                height * 2,
                null);
    }

    public int getFireTimer() {
        return this.fireTimer;
    }

    public void incFireTimer() {
        this.fireTimer += 1;
    }

    public void resetFireTimer() {
        this.fireTimer = 0;
    }

    public int getFireOut() {
        return this.fireOut;
    }

    public void animationUpdate() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= 7) {
                aniIndex = 0;
            }
        }
    }

    public boolean update() {
        incFireTimer();
        animationUpdate();
        return (fireTimer >= fireOut);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return x;
    }
}
