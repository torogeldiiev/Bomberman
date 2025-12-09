/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenith.Bomberman;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics2D;
import static com.zenith.Bomberman.Constants.PlayerConstants.*;
import static com.zenith.Bomberman.Constants.Directions.*;
import static com.zenith.Bomberman.HelperMethods.CanMoveHere;
import static com.zenith.Bomberman.HelperMethods.printer;
import static com.zenith.Bomberman.Constants.GameConstants.*;

//something
/**
 *
 * @author bli
 */
// I made this one cause y not
public class Enemy extends Character {
    private GameEngine ge;
    private int rectx;
    private int recty;
    private int rectWidth;
    private int rectHeight;
    private int width = 192;
    private int height = 192;
    private int state;
    public int health;
    private int score;
    public int something = 0;

    public Enemy(GameEngine ge, int startX, int startY) {
        this.ge = ge;
        setStartValue(startX, startY);
        getEnemyImage();
        rectWidth = 64;
        rectHeight = 64;
        solidArea = new Rectangle(rectx, recty, rectHeight, rectWidth);
    }

    public void getEnemyImage() {
        try {
            img = ImageIO
                    .read(new File("data//Assets/Characters/TNT_Red.png"));
            for (int i = 0; i < 6; i++) {
                idleAni[i] = img.getSubimage(i * 192, 0, width, height);
                rightAni[i] = img.getSubimage(i * 192, 192, width, height);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStartValue(int startX, int startY) {
        x = startX * TILESIZE - 64;
        y = startY * TILESIZE - 64;
        // to center the border/rect
        rectx = x + (64);
        recty = y + (64);
        speed = 1;
        this.direction = RIGHT;
        state = IDLE;
    }

    public boolean collisionDetect(Rectangle[] borders) {

        for (Rectangle border : borders) {
            if (!(border == solidArea))
                if (solidArea.intersects(border)) {
                    return true;
                }
        }
        return false;
    }

    public void setDirection(int direction) {
        this.direction = direction;
        this.state = RUNNING;
    }

    public boolean randomMove(Tile[][] lvlData) {
        int rnd = new Random().nextInt(4);
        int[] arrMoves = { RIGHT, LEFT, UP, DOWN };
        int xSpeed = 0, ySpeed = 0;
        switch (arrMoves[rnd]) {
            case RIGHT:
                xSpeed = speed;
                break;
            case LEFT:
                xSpeed = -1 * speed;
                break;
            case DOWN:
                ySpeed = speed;
                break;
            case UP:
                ySpeed = -1 * speed;
                break;
            case 0:
                state = IDLE;
                break;
        }
        if (CanMoveHere(rectx + xSpeed, recty + ySpeed, rectWidth, rectHeight, lvlData)) {
            setDirection(arrMoves[rnd]);
            return false;
        }
        return true;
    }

    public void update(Tile[][] lvlData) {
        int xSpeed = 0, ySpeed = 0;
        switch (direction) {
            case RIGHT:
                xSpeed = speed;
                break;
            case LEFT:
                xSpeed = -1 * speed;
                break;
            case DOWN:
                ySpeed = speed;
                break;
            case UP:
                ySpeed = -1 * speed;
                break;
            case 0:
                state = IDLE;

                break;
        }
        if (something > 100) {
            int prevDir = direction;
            if (randomMove(lvlData)) {
                setDirection(prevDir);
            }
            something = 0;
        }
        move(xSpeed, ySpeed, lvlData);
        animationUpdate();
    }

    public Enemy move(int xSpeed, int ySpeed, Tile[][] lvlData) {
        if (CanMoveHere(rectx + xSpeed, recty + ySpeed, rectWidth, rectHeight, lvlData)) {
            y += ySpeed;
            recty += ySpeed;
            x += xSpeed;
            rectx += xSpeed;
            solidArea.setLocation(rectx, recty);
            state = RUNNING;
        } else {
            collided();
        }
        return this;
    }

    public Enemy collided() {
        if (direction == RIGHT) {
            setDirection(LEFT);
        } else if (direction == LEFT) {
            setDirection(RIGHT);
        }
        if (direction == UP) {
            setDirection(DOWN);
        } else if (direction == DOWN) {
            setDirection(UP);
        }
        return this;
    }

    public Rectangle getRect() {
        return solidArea;
    }

    public void animationUpdate() {
        aniTick++;
        if (aniTick >= aniSpeed) {
            aniTick = 0;
            aniIndex++;
            if (aniIndex >= 6) {
                aniIndex = 0;
            }
        }
        something++;
    }

    private BufferedImage setAnimation() {
        if (state == RUNNING) {

            switch (direction) {
                case UP:
                    return rightAni[aniIndex];
                case DOWN:
                    return rightAni[aniIndex];
                case LEFT:

                    flipX = width;
                    flipW = -1;
                    return rightAni[aniIndex];
                case RIGHT:
                    flipX = 0;
                    flipW = 1;
                    return rightAni[aniIndex];
            }
        }
        return idleAni[aniIndex];
    }

    public void draw(Graphics2D g2) {

        BufferedImage image = setAnimation();

        g2.setColor(Color.white);
        // g2.fillRect(rectx, recty, rectWidth, rectHeight);
        g2.drawImage(image,
                x + flipX,
                y,
                ge.tileSize * 3 * flipW,
                ge.tileSize * 3,
                null);
    }

    public int getDirection() {
        return direction;
    }

    public int getRectX() {
        return rectx;
    }

    public int getRectY() {
        return recty;
    }

    public int getRectWidth() {
        return rectWidth;
    }

    public int getRectHeight() {
        return rectHeight;
    }

    public int getState() {
        return state;
    }

    public int getAniTick() {
        return aniTick;
    }
}
