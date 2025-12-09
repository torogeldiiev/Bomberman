/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.zenith.Bomberman;

import com.zenith.Bomberman.items.*;

import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.Iterator;

import static com.zenith.Bomberman.Constants.PlayerConstants.*;
import static com.zenith.Bomberman.Constants.Directions.*;
import static com.zenith.Bomberman.HelperMethods.CanMoveHere;
import static com.zenith.Bomberman.HelperMethods.printer;

/**
 *
 * @author bli
 */
public class Player extends Character {
    private GameEngine ge;
    private ArrayList<Bomb> bombs;
    private int rectx;
    private int recty;
    private int rectWidth;
    private int rectHeight;
    private int width = 192;
    private int height = 192;
    private int state;
    public int health;
    private int score;
    private int fireLength;
    private int bombPlaced = 0;
    private int canPlace = 1;
    private int boxesCanPlace = 0;
    private int lastKnownDir = 4;
    int cnt = 1;
    private int pl = 0;

    private boolean detonator = false;

    public Player(GameEngine ge, int version) {
        this.ge = ge;
        pl = version;
        setStartValue();
        pl = version;
        getPlayerImage();
        bombs = new ArrayList<>();
        health = 3;
        rectWidth = 64;
        rectHeight = 64;
        score = 0;
        fireLength = 2;
        solidArea = new Rectangle(rectx, recty, rectHeight, rectWidth);
        detonator = false;
        bombPlaced = 0;
        canPlace = 1;
        boxesCanPlace = 0;
        lastKnownDir = 4;
    }

    public Player playerReset() {
        setStartValue();
        solidArea = new Rectangle(rectx, recty, rectHeight, rectWidth);
        bombPlaced = 0;
        canPlace = 1;
        fireLength = 2;
        health--;
        bombs = new ArrayList<>();

        detonator = false;
        bombPlaced = 0;
        canPlace = 1;
        fireLength = 2;
        boxesCanPlace = 0;
        return this;
    }

    public int getPl() {
        return pl;
    }

    public void getPlayerImage() {
        try {
            if (pl == 0) {
                img = ImageIO
                        .read(new File("data/Assets/Characters/Warrior_Yellow.png"));
            } else if (pl == 1) {
                img = ImageIO
                        .read(new File("data/Assets/Characters/Warrior_Purple.png"));
            } else {
                img = ImageIO
                        .read(new File("data/Assets/Characters/Warrior_Blue.png"));
            }
            for (int i = 0; i < 6; i++) {
                idleAni[i] = img.getSubimage(i * 192, 0, width, height);
                rightAni[i] = img.getSubimage(i * 192, 192, width, height);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setStartValue() {
        if (pl == 0) {
            x = -64;
            y = -64;
            // to center the border/rect
            rectx = x + (64);
            recty = y + (64);
        } else if (pl == 1) {
            x = -64;
            y = 400;
            // to center the border/rect
            rectx = x + (64);
            recty = y + (64);
        } else {
            x = 400;
            y = -64;
            // to center the border/rect
            rectx = x + (64);
            recty = y + (64);
        }

        speed = 4;
        direction = RIGHT;
        state = IDLE;
    }

    public boolean collisionDetect(Rectangle[] borders) {
        for (Rectangle border : borders) {
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

    public void placeBox() {
        boolean collision = false;
        if (boxesCanPlace > 0) {
            switch (lastKnownDir) {
                case 1:
                    // direction = RIGHT
                    collision = ge.getPlaying().checkBoxCollision((x + 96) + 128, y + 96);
                    if (!collision) {
                        ge.getPlaying().createBox((x + 96) + 128, y + 96);
                        boxesCanPlace--;
                    }
                    break;
                case 2:
                    // direction = LEFT;
                    collision = ge.getPlaying().checkBoxCollision((x + 96) - 128, y + 96);
                    if (!collision) {
                        ge.getPlaying().createBox((x + 96) - 128, y + 96);
                        boxesCanPlace--;
                    }
                    break;
                case 3:
                    // direction = UP;
                    collision = ge.getPlaying().checkBoxCollision(x + 96, (y + 96) + 128);
                    if (!collision) {
                        ge.getPlaying().createBox(x + 96, (y + 96) + 128);
                        boxesCanPlace--;
                    }
                    break;
                case 4:
                    // direction = DOWN;
                    collision = ge.getPlaying().checkBoxCollision(x + 96, (y + 96) - 128);
                    if (!collision) {
                        ge.getPlaying().createBox(x + 96, (y + 96) - 128);
                        boxesCanPlace--;
                    }
                    break;
            }
        }
    }

    public void addBomb() {
        boolean doesItCollide = false;
        boolean waitone = false;
        Rectangle rect = new Rectangle(x + 96, y + 96, 64, 64);
        for (Bomb bomb : bombs) {
            Rectangle otherRect = new Rectangle(bomb.getBombX(), bomb.getBombY(), 64, 64);
            doesItCollide = rect.intersects(otherRect);
        }
        if (bombPlaced == canPlace && detonator) {
            for (int j = 0; j < bombs.size(); j++) {
                ge.getPlaying().sendExplosion(this,
                        Math.round((bombs.get(j).getBombX() - (bombs.get(j).getBombX() % ge.tileSize))),
                        Math.round((bombs.get(j).getBombY() - (bombs.get(j).getBombY() % ge.tileSize))));
                bombs.remove(j);
                bombPlaced--;
                j--;
            }
            waitone = true;
        }
        if (!doesItCollide && (bombPlaced < canPlace) && !waitone) {
            bombs.add(new Bomb(getX() + 96, getY() + 96, getFireLength()));
            bombPlaced++;
        }
    }

    public void update(int dir, Tile[][] lvlData) {
        // TODO should be able to get the gameengine levl borders and collision detect
        setDirection(dir);
        // bomb stuff here
        if (bombPlaced != 0) {
            for (int i = 0; i < bombs.size(); i++) {
                cnt++;
                Bomb bomb = bombs.get(i);
                if (!detonator) {
                    bomb.incBombTimer();
                    if (bomb.getBombTimer() >= bomb.getBombExplodesIn()) {
                        ge.getPlaying().sendExplosion(this,
                                Math.round((bomb.getBombX() - (bomb.getBombX() % ge.tileSize))),
                                Math.round((bomb.getBombY() - (bomb.getBombY() % ge.tileSize))));
                        bombs.remove(i);
                        bombPlaced--;
                        i--;
                    }
                }
            }
        }
        int xSpeed = 0, ySpeed = 0;
        switch (dir) {
            case 1:
                direction = RIGHT;
                lastKnownDir = 1;
                xSpeed = speed;
                break;
            case 2:
                direction = LEFT;
                lastKnownDir = 2;
                xSpeed = -1 * speed;
                break;
            case 3:
                direction = UP;
                lastKnownDir = 3;
                ySpeed = speed;
                break;
            case 4:
                direction = DOWN;
                lastKnownDir = 4;
                ySpeed = -1 * speed;
                break;
            case 0:
                state = IDLE;

                break;
        }
        if (CanMoveHere(rectx + xSpeed, recty + ySpeed, rectWidth, rectHeight, lvlData)) {
            y += ySpeed;
            recty += ySpeed;
            x += xSpeed;
            rectx += xSpeed;
            state = RUNNING;
        }
        animationUpdate();
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

        solidArea = new Rectangle(rectx, recty, rectWidth, rectHeight);
        g2.setColor(Color.white);
        // g2.fillRect(rectx, recty, rectWidth, rectHeight);
        g2.drawImage(image,
                x + flipX,
                y,
                ge.tileSize * 3 * flipW,
                ge.tileSize * 3,
                null);
        if (bombPlaced > 0) {
            for (Bomb bomb : bombs) {
                bomb.drawBomb(ge, g2);
            }
        }
    }

    public void collectItems(ArrayList<FireRangeIncreaseItem> fireItems,
            ArrayList<BombNumberIncreaseItem> bombItems,
            ArrayList<RollerSkateItem> items,
            ArrayList<DetonatorItem> itemsDetonator,
            ArrayList<PlaceableBoxItem> boxItems) {
        Rectangle playerRect = new Rectangle(rectx, recty, rectWidth, rectHeight);

        for (FireRangeIncreaseItem fireItem : fireItems) {
            Rectangle fireRect = new Rectangle(fireItem.x, fireItem.y, ge.tileSize, ge.tileSize);
            if (playerRect.intersects(fireRect)) {
                fireItems.remove(fireItem);
                this.setFireLength(3);
                break;
            }
        }

        for (BombNumberIncreaseItem bombItem : bombItems) {
            Rectangle bombRect = new Rectangle(bombItem.x, bombItem.y, ge.tileSize, ge.tileSize);
            if (playerRect.intersects(bombRect)) {
                bombItems.remove(bombItem);
                canPlace++;
                break;
            }
        }

        for (RollerSkateItem item : items) {
            Rectangle itemRect = new Rectangle(item.x, item.y, ge.tileSize, ge.tileSize);
            if (playerRect.intersects(itemRect)) {
                items.remove(item);
                speed += 2;
                break;
            }
        }

        for (DetonatorItem item : itemsDetonator) {
            Rectangle itemRect = new Rectangle(item.x, item.y, ge.tileSize, ge.tileSize);
            if (playerRect.intersects(itemRect)) {
                itemsDetonator.remove(item);
                detonator = true;
                break;
            }
        }

        for (PlaceableBoxItem item : boxItems) {
            Rectangle itemRect = new Rectangle(item.x, item.y, ge.tileSize, ge.tileSize);
            if (playerRect.intersects(itemRect)) {
                boxItems.remove(item);
                boxesCanPlace += 3;
                break;
            }
        }
    }

    public int getState() {
        return state;
    }

    public int getRectWidth() {
        return rectWidth;
    }

    public int getRectHeight() {
        return rectHeight;
    }

    public void setFireLength(int fireLength) {
        this.fireLength = fireLength;
    }

    public int getFireLength() {
        return fireLength;
    }

    public ArrayList<Bomb> getBombs() {
        return bombs;
    }
}
