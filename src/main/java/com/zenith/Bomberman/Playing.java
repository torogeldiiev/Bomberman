package com.zenith.Bomberman;

import java.awt.Graphics2D;

import static com.zenith.Bomberman.Constants.Directions.DOWN;
import static com.zenith.Bomberman.Constants.Directions.LEFT;
import static com.zenith.Bomberman.Constants.Directions.RIGHT;
import static com.zenith.Bomberman.Constants.Directions.UP;
import static com.zenith.Bomberman.HelperMethods.printer;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

public class Playing extends State implements StateMethods {
    Player[] players;
    int[] playersDir;
    Level level;
    int playerSize = 2;
    protected boolean pause;

    public Playing(GameEngine ge, int playerSize) {
        super(ge);
        this.playerSize = playerSize;
        initPlayingClass();
    }

    public Playing(GameEngine ge) {
        this(ge, 2);
    }

    public void initPlayingClass() {
        players = new Player[playerSize];
        playersDir = new int[playerSize];
        for (int i = 0; i < playerSize; i++) {
            players[i] = new Player(ge, i);
            playersDir[i] = 0;

        }
        pause = false;
        level = new Level(ge, 1);
    }

    @Override
    public void update() {
        // TODO Auto-generated method stub
        if (!pause) {
            updateHelper();
            level.update();
        }
    }

    public void updateHelper() {
        for (Player pl : players) {
            pl.update(playersDir[pl.getPl()], level.getBorders());
            if (pl.collisionDetect(level.getEnemyBorders())) {
                System.out.println("Collided With Enemy");
                pl.playerReset();
                level = new Level(ge, 1);

            }
            if (level.playerDiesFromExplosion(pl)) {
                pl.health--;
                level = new Level(ge, 1);

                pl.setStartValue();
            }
            pl.collectItems(level.getFireRangeItems(), level.getBombNumberIncreaseItems(),
                    level.getRollerSkateItems(), level.getDetonatorItems(), level.getBoxItems());
        }
    }

    public boolean checkBoxCollision(int x, int y) {
        return level.checkBoxCollision(x, y);
    }

    public void createBox(int x, int y) {
        level.createBox(x, y);
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        level.draw(g2);
        for (Player pl : players) {
            pl.draw(g2);
        }
        g2.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            playersDir[0] = RIGHT;
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            playersDir[0] = LEFT;
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            playersDir[0] = DOWN;
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            playersDir[0] = UP;
        } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            pause = !pause;
        }
        if (e.getKeyCode() == KeyEvent.VK_D) {
            playersDir[1] = RIGHT;
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            playersDir[1] = LEFT;
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            playersDir[1] = DOWN;
        } else if (e.getKeyCode() == KeyEvent.VK_W) {
            playersDir[1] = UP;
        }
        if (playerSize > 2) {
            if (e.getKeyCode() == KeyEvent.VK_L) {
                playersDir[2] = RIGHT;
            } else if (e.getKeyCode() == KeyEvent.VK_J) {
                playersDir[2] = LEFT;
            } else if (e.getKeyCode() == KeyEvent.VK_K) {
                playersDir[2] = DOWN;
            } else if (e.getKeyCode() == KeyEvent.VK_I) {
                playersDir[2] = UP;
            }
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (playersDir[0] == 1 && e.getKeyCode() == KeyEvent.VK_RIGHT) {
            playersDir[0] = 0;
        } else if (playersDir[0] == 2 && e.getKeyCode() == KeyEvent.VK_LEFT) {
            playersDir[0] = 0;
        } else if (playersDir[0] == 3 && e.getKeyCode() == KeyEvent.VK_DOWN) {
            playersDir[0] = 0;
        } else if (playersDir[0] == 4 && e.getKeyCode() == KeyEvent.VK_UP) {
            playersDir[0] = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_SEMICOLON) {
            PlayerAddBomb(0);
        } else if (e.getKeyCode() == KeyEvent.VK_COMMA) {
            PlayerPlaceBox(0);
        }
        if (playersDir[1] == 1 && e.getKeyCode() == KeyEvent.VK_D) {
            playersDir[1] = 0;
        } else if (playersDir[1] == 2 && e.getKeyCode() == KeyEvent.VK_A) {
            playersDir[1] = 0;
        } else if (playersDir[1] == 3 && e.getKeyCode() == KeyEvent.VK_S) {
            playersDir[1] = 0;
        } else if (playersDir[1] == 4 && e.getKeyCode() == KeyEvent.VK_W) {
            playersDir[1] = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            PlayerAddBomb(1);
        } else if (e.getKeyCode() == KeyEvent.VK_C) {
            PlayerPlaceBox(1);
        }
        if (playerSize > 2) {
            if (playersDir[2] == 1 && e.getKeyCode() == KeyEvent.VK_L) {
                playersDir[2] = 0;
            } else if (playersDir[2] == 2 && e.getKeyCode() == KeyEvent.VK_J) {
                playersDir[2] = 0;
            } else if (playersDir[2] == 3 && e.getKeyCode() == KeyEvent.VK_K) {
                playersDir[2] = 0;
            } else if (playersDir[2] == 4 && e.getKeyCode() == KeyEvent.VK_I) {
                playersDir[2] = 0;
            } else if (e.getKeyCode() == KeyEvent.VK_H) {
                PlayerAddBomb(2);
            } else if (e.getKeyCode() == KeyEvent.VK_U) {
                PlayerPlaceBox(2);
            }
        }

    }

    public void PlayerPlaceBox(int index) {
        players[index].placeBox();
    }

    public void PlayerAddBomb(int index) {
        players[index].addBomb();
    }

    public void sendExplosion(Player explodingPlayer, int x, int y) {
        level.explode(explodingPlayer, x, y);
    }

    public void checkBombItemCollected() {
        if (level.isBombItemCollected()) {
            level.setBombItemCollected(true);
        }
    }

    public Level getLevel() {
        return level;
    }

    public boolean isPause() {
        return pause;
    }
}
