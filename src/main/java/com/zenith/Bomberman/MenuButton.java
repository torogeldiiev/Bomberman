package com.zenith.Bomberman;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.AttributedString;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;

import javax.imageio.ImageIO;

import static com.zenith.Bomberman.Constants.GameConstants.*;
import java.awt.font.TextAttribute;

public class MenuButton {
    private BufferedImage[] imgs;
    private int xPos, yPos, rowIndex, index;
    private State state;
    private boolean selected;
    private String buttonString;
    public Font Skranji;
    public GraphicsEnvironment ge;

    public void loadFont() throws IOException {
        try {
            Skranji = Font.createFont(Font.TRUETYPE_FONT, new File("data/Assets/Font/Skranji-Regular.ttf"))
                    .deriveFont(20f);
            GraphicsEnvironment ge = (GraphicsEnvironment.getLocalGraphicsEnvironment());
            ge.registerFont(Font.createFont(Font.TRUETYPE_FONT, new File("data/Assets/Font/Skranji-Regular.ttf")));
        } catch (FontFormatException e) {
            e.fillInStackTrace();
        }

    }

    public MenuButton(int x, int y, int rowIndex, State ge, String buttonString) throws IOException {
        xPos = x;
        yPos = y * rowIndex;
        this.rowIndex = rowIndex;
        state = ge;
        this.buttonString = buttonString;
        getImg();
        loadFont();
    }

    public void getImg() throws IOException {
        imgs = new BufferedImage[2];
        BufferedImage temp = readImage("data/Assets/Buttons/Button_Red_3Slides.png");
        imgs[0] = temp;
        temp = readImage("data/Assets/Buttons/Button_Red_3Slides_Pressed.png");
        imgs[1] = temp;

    }

    public void draw(Graphics2D g) {
        if (selected) {
            g.drawImage(imgs[1], xPos, yPos, TILESIZE * 3, TILESIZE, null);
        } else {
            g.drawImage(imgs[0], xPos, yPos, TILESIZE * 3, TILESIZE, null);
        }
        AttributedString atString = new AttributedString(buttonString);
        atString.addAttribute(TextAttribute.FONT, Skranji);
        if (selected) {
            g.drawString(atString.getIterator(), xPos + TILESIZE / 2, yPos + TILESIZE / 2 + 5);
        } else {
            g.drawString(atString.getIterator(), xPos + TILESIZE / 2, yPos + TILESIZE / 2);
        }

    }

    public void selected() {
        selected = true;
    }

    public void notSelected() {
        selected = false;
    }

    protected BufferedImage readImage(String path) throws IOException {
        return ImageIO.read(Files.newInputStream(Paths.get(path)));
    }
}
