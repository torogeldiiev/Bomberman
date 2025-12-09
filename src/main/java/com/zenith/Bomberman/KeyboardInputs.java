package com.zenith.Bomberman;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import static com.zenith.Bomberman.Constants.Directions.*;

public class KeyboardInputs implements KeyListener {

    GameEngine ge;

    public KeyboardInputs(GameEngine gEngine) {
        ge = gEngine;
    }

    @Override
    public void keyTyped(KeyEvent e) {
        ;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (GameState.state) {
            case MENU:
                ge.getMenu().keyPressed(e);
                break;
            case PLAYING:
                ge.getPlaying().keyPressed(e);
                break;
            default:
                break;

        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        switch (GameState.state) {
            case MENU:
                ge.getMenu().keyReleased(e);
                break;
            case PLAYING:
                ge.getPlaying().keyReleased(e);
                break;
            default:
                break;
        }
    }

}

// @Override
// public void keyTyped(KeyEvent e) {
// // TODO Auto-generated method stub
// System.out.println("SOMETHING WAS TYPED");
// }

// @Override
// public void keyPressed(KeyEvent e) {
// // TODO Auto-generated method stub
// if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
// playerOneDir = 1;
// } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
// playerOneDir = 2;
// } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
// playerOneDir = 3;
// } else if (e.getKeyCode() == KeyEvent.VK_UP) {
// playerOneDir = 4;
// } else if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
// pause = !pause;
// }
// }

// @Override
// public void keyReleased(KeyEvent e) {
// if (playerOneDir == 1 && e.getKeyCode() == KeyEvent.VK_RIGHT) {
// playerOneDir = 0;
// } else if (playerOneDir == 2 && e.getKeyCode() == KeyEvent.VK_LEFT) {
// playerOneDir = 0;
// } else if (playerOneDir == 3 && e.getKeyCode() == KeyEvent.VK_DOWN) {
// playerOneDir = 0;
// } else if (playerOneDir == 4 && e.getKeyCode() == KeyEvent.VK_UP) {
// playerOneDir = 0;
// }
// }
