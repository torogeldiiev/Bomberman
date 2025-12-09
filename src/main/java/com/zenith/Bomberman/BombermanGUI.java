/**
 * The YogiGUI class is responsible for creating the main JFrame for the Yogi Bear game.
 * It initializes the game window, sets up the GameEngine, and starts the game thread.
 */
package com.zenith.Bomberman;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import java.awt.GraphicsEnvironment;
import java.awt.FontFormatException;
import java.awt.Font;

/**
 * The BombermanGUI class represents the main graphical user interface for the
 * Yogi
 * Bear game.
 */
public class BombermanGUI {


    public BombermanGUI() {
        // Create a new JFrame
        JFrame frame = new JFrame("BomberMan");

        // Set default close operation and make the frame non-resizable
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);

        // Create a new instance of the GameEngine
        GameEngine gameArea = new GameEngine();

        // Add the GameEngine to the JFrame
        frame.add(gameArea);

        // Pack the components, set the frame location to the center of the screen, and
        // make it visible
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        // Start the game thread
        gameArea.startGameThread();
    }
}
