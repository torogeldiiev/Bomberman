/**
 * The GameEngine class represents the core of the game.
 * It extends JPanel and implements Runnable and KeyListener interfaces.
 * This class handles game mechanics, player movement, levels, and high scores.
 */
package com.zenith.Bomberman;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JPanel;
import static com.zenith.Bomberman.Constants.GameConstants.*;
import java.io.IOException;

/**
 * The GameEngine class manages the game logic, rendering, and player input.
 */
public class GameEngine extends JPanel implements Runnable {
    // SCREEN SETTINGS
    // Constants for screen and tile dimensions
    private static int scale = 1;
    public static int tileSize = TILESIZE * scale;
    public static int screenCol = 12;
    public static int screenRow = 12;
    protected static int screenWidth = tileSize * screenCol;
    protected static int screenHeight = tileSize * screenRow;
    private final int FPS = 60;
    protected boolean quit = false;
    Thread gameThread;

    // player
    Playing playing;
    Menu menu;

    public GameEngine() {
        try {
            playing = new Playing(this);
            menu = new Menu(this);
        } catch (IOException e) {
            e.fillInStackTrace();
        }
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
        addKeyListener(new KeyboardInputs(this));
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawInterval = 1000 / FPS;
        double delta = 0;
        long lastTime = System.currentTimeMillis();
        long currentTime;
        while (gameThread != null) {
            currentTime = System.currentTimeMillis();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;
            if (delta >= 1) {
                update();
                repaint();
                delta--;
            }
        }
    }

    public void update() {
        switch (GameState.state) {
            case MENU:
                menu.update();
                break;
            case PLAYING:
                playing.update();
                break;
            default:
                break;
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        switch (GameState.state) {
            case MENU:
                menu.draw(g);
                break;
            case PLAYING:
                playing.draw(g);
                break;
            default:
                break;
        }
    }

    

    public void updatePlaying(int playerCount) {
        playing = new Playing(this, playerCount);
    }

    public Playing getPlaying() {
        return playing;
    }

    public Menu getMenu() {
        return menu;
    }

    public Thread getGameThread() {
        return gameThread;
    }

    public static int getScreenHeight() {
        return screenHeight;
    }

    public static int getScreenWidth() {
        return screenWidth;
    }

}
