package com.zenith.Bomberman;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import static com.zenith.Bomberman.Constants.GameConstants.TILESIZE;

import java.awt.Color;
import java.awt.event.KeyEvent;

public class Menu extends State implements StateMethods {
    private MenuButton[] buttons = new MenuButton[3];
    private Image backgroundImg;
    private BufferedImage buttonBackgroundImg;
    private int currentSelected = 0;

    public Menu(GameEngine ge) throws IOException {
        super(ge);
        loadImgs();
    }

    public void update() {
        for (int i = 0; i < 3; i++) {
            buttons[i].notSelected();
        }
        buttons[currentSelected].selected();
    }

    public void loadImgs() throws IOException {
        buttons[0] = new MenuButton(ge.screenWidth / 2 - (int) (TILESIZE * 1.5), 100, 1, this, "3 Players");
        buttons[1] = new MenuButton(ge.screenWidth / 2 - (int) (TILESIZE * 1.5), 100, 2, this, "2 Players");
        buttons[2] = new MenuButton(ge.screenWidth / 2 - (int) (TILESIZE * 1.5), 100, 3, this, "Quit");
        backgroundImg = new ImageIcon("data\\Assets\\Images\\something.gif").getImage();
        buttonBackgroundImg = readImage("data\\Assets\\Banners\\Banner_Vertical.png");
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.drawImage(backgroundImg, -150, -200, ge);
        g2.drawImage(buttonBackgroundImg, ge.screenWidth / 2 - (int) (TILESIZE * 4.5), -50, TILESIZE * 9,
                TILESIZE * 9,
                ge);
        for (MenuButton mb : buttons)
            mb.draw(g2);
        g.setColor(Color.white);
        g2.dispose();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // TODO Auto-generated method stub
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            currentSelected--;
            if (currentSelected < 0) {
                currentSelected = 2;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            currentSelected++;
            if (currentSelected > 2) {
                currentSelected = 0;
            }
        } else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
            pressed();
        }
    }

    public void pressed() {
        GameState.state = GameState.PLAYING;
        if (currentSelected == 0) {
            ge.updatePlaying(3);
        }
        if (currentSelected == 1) {
            ge.updatePlaying(2);
        }
        if (currentSelected == 2) {
            System.exit(0);
        }
    }

    protected BufferedImage readImage(String path) throws IOException {
        return ImageIO.read(Files.newInputStream(Paths.get(path)));
    }
}
