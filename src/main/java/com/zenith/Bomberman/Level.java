package com.zenith.Bomberman;

import com.zenith.Bomberman.items.BombNumberIncreaseItem;
import com.zenith.Bomberman.items.DetonatorItem;
import com.zenith.Bomberman.items.FireRangeIncreaseItem;
import com.zenith.Bomberman.items.PlaceableBoxItem;
import com.zenith.Bomberman.items.RollerSkateItem;
import static com.zenith.Bomberman.HelperMethods.printer;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;

import javax.imageio.ImageIO;
import java.io.BufferedReader;
import java.util.LinkedList;
import java.util.Random;
import java.awt.AlphaComposite;

/**
 * Represents a level in the game.
 */
public class Level {
    private GameEngine ge;
    private Tile[] tileTypes;
    private Tile[][] mapTilesDiff;
    private Tile[] terrainTypes;
    private Tile[] objectivesTypes;
    private Tile[][] terrainTilesDiff;
    private Tile[][] objectivesTilesDiff;
    private Tile enemyTypes;
    private Tile fireRange;
    private Tile[][] enemiesTilesDiff;
    private ArrayList<Enemy> enemies = new ArrayList<>();
    private int numberOfCollecatable = 0;
    private BufferedImage tilesetImage;
    private BufferedImage objectImage;
    private ArrayList<ArrayList<Fire>> explosions = new ArrayList<>();
    private ArrayList<Fire> explosion = new ArrayList<>();
    private Random random = new Random();

    private ArrayList<FireRangeIncreaseItem> fireRangeItems = new ArrayList<>();
    private ArrayList<BombNumberIncreaseItem> bombNumberIncreaseItems = new ArrayList<>();
    private ArrayList<RollerSkateItem> rollerSkateItems = new ArrayList<>();
    private ArrayList<DetonatorItem> detonatorItems = new ArrayList<>();
    private ArrayList<PlaceableBoxItem> boxItems = new ArrayList<>();

    private boolean fireItemSpawned = false;
    private boolean bombItemSpawned = false;
    private boolean rollerSkateItemSpawned = false;
    private boolean detonatorItemSpawned = false;
    private boolean boxItemSpawned = false;

    private boolean fireItemCollected = false;
    private boolean bombItemCollected = false;
    private boolean skateItemCollected = false;
    private boolean DetonatorItemCollected = false;
    private boolean boxItemCollected = false;

    public Level(GameEngine ge, int levelNum) {
        this.ge = ge;
        tileTypes = new Tile[40];
        terrainTypes = new Tile[3];
        mapTilesDiff = new Tile[ge.screenRow][ge.screenCol];
        terrainTilesDiff = new Tile[ge.screenRow][ge.screenCol];
        getTileImage();
        loadLevel(levelNum);
    }

    public boolean isFireItemCollected() {
        return fireItemCollected;
    }

    public boolean isBoxItemCollected() {
        return boxItemCollected;
    }

    public void setBoxItemCollected(boolean boxItemCollected) {
        this.boxItemCollected = boxItemCollected;
    }

    public void setFireItemCollected(boolean fireItemCollected) {
        this.fireItemCollected = fireItemCollected;
    }

    public boolean isBombItemCollected() {
        return bombItemCollected;
    }

    public void setBombItemCollected(boolean bombItemCollected) {
        this.bombItemCollected = bombItemCollected;
    }

    public ArrayList<FireRangeIncreaseItem> getFireRangeItems() {
        return fireRangeItems;
    }

    public ArrayList<DetonatorItem> getDetonatorItems() {
        return detonatorItems;
    }

    public ArrayList<RollerSkateItem> getRollerSkateItems() {
        return rollerSkateItems;
    }

    public ArrayList<BombNumberIncreaseItem> getBombNumberIncreaseItems() {
        return bombNumberIncreaseItems;
    }

    public ArrayList<PlaceableBoxItem> getBoxItems() {
        return boxItems;
    }

    public void getTileImage() {
        try {
            tilesetImage = readImage("data/Assets/Tilesets/Tilemap_Flat.png");
            for (int i = 0; i < 4; i++) {
                for (int j = 0; j < 10; j++) {
                    tileTypes[i * 10 + j] = new Tile();
                    tileTypes[i * 10 + j].img = tilesetImage.getSubimage(j * 64, i * 64, 64, 64);

                }
            }
            // WHAT JUDEH IS TRYING ALSO CHANGED THE CSV
            terrainTypes[0] = new Tile();
            terrainTypes[0].img = readImage("data/Assets/Tilesets/WALL.jpg");
            terrainTypes[1] = new Tile();
            terrainTypes[1].img = readImage("data/Assets/Tilesets/tile_9.png");
            terrainTypes[2] = new Tile();
            terrainTypes[2].img = readImage("data/Assets/Tilesets/WALL2.png");
            //////
            enemyTypes = new Tile();
            enemyTypes.img = readImage("data/Assets/Objects/Egg item.png");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private BufferedImage readImage(String path) throws IOException {
        return ImageIO.read(Files.newInputStream(Paths.get(path)));
    }

    public void loadLevel(int levelNum) {
        try {
            BufferedReader br = Files
                    .newBufferedReader(Paths.get("data/levels/level" + levelNum + "_Tile Layer 1.csv"));

            for (int row = 0; row < ge.screenRow; row++) {
                String line = br.readLine();
                String numbers[] = line.split(",");
                for (int col = 0; col < ge.screenCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTilesDiff[row][col] = new Tile(tileTypes[num]);
                    mapTilesDiff[row][col].x = col * ge.tileSize;
                    mapTilesDiff[row][col].y = row * ge.tileSize;
                    mapTilesDiff[row][col].tileNum = num;
                }
            }
            br.close();
            br = Files.newBufferedReader(Paths.get("data/levels/level1_terrain.csv"));
            for (int row = 0; row < ge.screenRow; row++) {
                String line = br.readLine().trim();
                String numbers[] = line.split(",");
                for (int col = 0; col < ge.screenCol; col++) {
                    int num = Integer.parseInt(numbers[col].trim());
                    if (num == -1) {
                        num = 1;
                    }
                    if (num == 0) {
                        // when num == 0 it means the tile is a wall
                        terrainTilesDiff[row][col] = new Tile(terrainTypes[num]);
                        terrainTilesDiff[row][col].collsion = true;
                        terrainTilesDiff[row][col].height = ge.tileSize;
                        terrainTilesDiff[row][col].width = ge.tileSize;
                        terrainTilesDiff[row][col].x = (col * ge.tileSize);
                        terrainTilesDiff[row][col].y = (row * ge.tileSize);
                        terrainTilesDiff[row][col].tileNum = 0;
                        terrainTilesDiff[row][col].destructible = false;

                    } else {
                        if (num != 2) {
                            terrainTilesDiff[row][col] = new Tile(terrainTypes[num]);
                            terrainTilesDiff[row][col].tileNum = -1;
                        }
                    }
                    if (num < 1) {
                        terrainTilesDiff[row][col].collsion = true;
                        terrainTilesDiff[row][col].height = ge.tileSize;
                        terrainTilesDiff[row][col].width = ge.tileSize;
                        terrainTilesDiff[row][col].x = col * ge.tileSize;
                        terrainTilesDiff[row][col].y = row * ge.tileSize;
                        terrainTilesDiff[row][col].tileNum = num;
                    }
                    if (num == 2) {
                        terrainTilesDiff[row][col] = new Tile(terrainTypes[num]);
                        terrainTilesDiff[row][col].collsion = true;
                        terrainTilesDiff[row][col].height = ge.tileSize;
                        terrainTilesDiff[row][col].width = ge.tileSize;
                        terrainTilesDiff[row][col].x = (col * ge.tileSize);
                        terrainTilesDiff[row][col].y = (row * ge.tileSize); // here was - 30 also idk why
                        terrainTilesDiff[row][col].tileNum = 2;
                        terrainTilesDiff[row][col].destructible = true;
                        terrainTilesDiff[row][col].canDropItem = true;
                    }
                }

            }
            br.close();
            br = Files.newBufferedReader(Paths.get("data/levels/level1_enemy.csv"));
            for (int row = 0; row < ge.screenRow; row++) {
                String line = br.readLine();
                String numbers[] = line.split(",");
                for (int col = 0; col < ge.screenCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    if (num != -1) {
                        Enemy temp = new Enemy(ge, col, row);
                        enemies.add(temp);
                    }
                }
            }

            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void collsionCheck(Player player) {
        for (int i = 0; i < ge.screenRow; i++) {
            for (int j = 0; j < ge.screenCol; j++) {
                if (objectivesTilesDiff[i][j].collectable) {
                    if (objectivesTilesDiff[i][j].collides(player)) {
                        objectivesTilesDiff[i][j].img = objectivesTypes[45].img;
                        objectivesTilesDiff[i][j].collectable = false;
                        objectivesTilesDiff[i][j].tileNum = 45;
                        numberOfCollecatable--;
                    }
                }
            }
        }
    }

    // public int getNumberOfFruitsLeft() { previous logic probably unused
    // return numberOfCollecatable;
    // }

    public Tile[][] getBorders() {
        Rectangle allBorders[] = new Rectangle[ge.screenRow * ge.screenCol];
        Tile[][] lvlData = new Tile[ge.screenRow][ge.screenCol];
        int numOfBorders = 0;
        for (int i = 0; i < ge.screenRow; i++) {
            for (int j = 0; j < ge.screenCol; j++) {
                lvlData[i][j] = terrainTilesDiff[i][j];
                numOfBorders++;
            }
        }
        return lvlData;
    }

    public Rectangle[] getEnemyBorders() {
        Rectangle[] temp = new Rectangle[enemies.size()];
        int count = 0;
        for (Enemy enemy : enemies) {
            temp[count] = enemy.getRect();
            count++;
        }
        return temp;
    }

    public boolean checkBoxCollision(int x, int y){
        int xVal = (Math.round((x) / 64));
        int yVal = (Math.round((y) / 64));
        System.out.println(xVal + " " + yVal);
        boolean collision = false;
        Rectangle box = new Rectangle(yVal * 64, xVal * 64, 64, 64);
        for (int row = 0; row < ge.screenRow; row++) {
            for (int col = 0; col < ge.screenCol; col++) {
                Rectangle box2 = new Rectangle(terrainTilesDiff[row][col].x, terrainTilesDiff[row][col].y, 64, 64);
                if (box.intersects(box2)) {
                    collision = true;
                }
            }
        }
        return collision;
    }

    public void createBox(int x, int y) {
        int xVal = (Math.round((x) / 64));
        int yVal = (Math.round((y) / 64));

        terrainTilesDiff[xVal][yVal] = new Tile(terrainTypes[2]);
        terrainTilesDiff[xVal][yVal].collsion = true;
        terrainTilesDiff[xVal][yVal].height = ge.tileSize;
        terrainTilesDiff[xVal][yVal].width = ge.tileSize;
        terrainTilesDiff[xVal][yVal].x = (xVal * ge.tileSize);
        terrainTilesDiff[xVal][yVal].y = (yVal * ge.tileSize); // here was - 30 also idk why
        terrainTilesDiff[xVal][yVal].tileNum = 2;
        terrainTilesDiff[xVal][yVal].destructible = true;
        terrainTilesDiff[xVal][yVal].canDropItem = false;
    }

    public void explode(Player expPlayer, int x, int y) {
        boolean noCollide;
        ArrayList<Tile> tilesToBeDestoyed = new ArrayList<>();
        explosion = new ArrayList<>();
        Fire tmp = new Fire(x, y);
        explosion.add(tmp);

        ArrayList<Enemy> enemiesToRemove = new ArrayList<>();
        for (int dir = 0; dir < 4; dir++) {
            noCollide = true;
            for (int i = 0; i < expPlayer.getFireLength(); i++) { // this is taking the player's fire length not
                                                                  // the players rn
                if (noCollide) {
                    switch (dir) {
                        case 0:
                            tmp = new Fire(x + (64 * (i + 1)), y);
                            break;
                        case 1:
                            tmp = new Fire(x, y + (64 * (i + 1)));
                            break;
                        case 2:
                            tmp = new Fire(x + (-64 * (i + 1)), y);
                            break;
                        case 3:
                            tmp = new Fire(x, y + (-64 * (i + 1)));
                            break;
                    }

                    for (Enemy enemy : enemies) {
                        if (tmp.collides(enemy)) {
                            enemiesToRemove.add(enemy);
                        }
                    }

                    for (Tile[] row : terrainTilesDiff) {
                        for (Tile tile : row) {
                            if (tile.tileNum == 0 || tile.tileNum == 2) {
                                if (tmp.collides(tile)) {
                                    if (tile.tileNum == 0) {
                                        noCollide = false;
                                    } else {
                                        tilesToBeDestoyed.add(tile);
                                    }
                                }
                            }
                        }
                    }
                    if (noCollide) {
                        explosion.add(tmp);
                    }
                }
            }
        }
        for (Enemy enemyToRemove : enemiesToRemove) {
            enemies.remove(enemyToRemove);
        }
        explosions.add(explosion);
        placeItems(tilesToBeDestoyed);
    }

    public boolean playerDiesFromExplosion(Player player) {
        for (ArrayList<Fire> explosion : explosions) {
            for (Fire fire : explosion) {
                if (fire.collides(player)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void placeItems(ArrayList<Tile> tilesToBeDestroyed) {
        Random random = new Random();
        for (int row = 0; row < ge.screenRow; row++) {
            for (int col = 0; col < ge.screenCol; col++) {
                if (tilesToBeDestroyed.contains(terrainTilesDiff[row][col]) && terrainTilesDiff[row][col].canDropItem) {
                    int randomValue = random.nextInt(5);
                    // int randomValue = 0; //this is to force a specific item to spawn
                    if (randomValue == 4 && !fireItemSpawned) {
                        placeFireIncreaseItem(tilesToBeDestroyed, col, row);
                    } else if (randomValue == 3 && !bombItemSpawned) {
                        placeBombIncreaseItem(tilesToBeDestroyed, col, row);
                    } else if (randomValue == 2 && !rollerSkateItemSpawned) {
                        placeRollerSkateItem(tilesToBeDestroyed, col, row);
                    } else if (randomValue == 1 && !detonatorItemSpawned) {
                        placeDetonatorItem(tilesToBeDestroyed, col, row);
                    } else if (randomValue == 0 && !boxItemSpawned) {
                        placeBoxItem(tilesToBeDestroyed, col, row);
                    } else if (bombItemSpawned || fireItemSpawned || rollerSkateItemSpawned || detonatorItemSpawned
                            || boxItemSpawned) {
                        placeTerrainTile(tilesToBeDestroyed, row, col);
                    }
                }
                if (tilesToBeDestroyed.contains(terrainTilesDiff[row][col])) {
                    placeTerrainTile(tilesToBeDestroyed, row, col);
                }
            }
        }
    }

    public void placeBombIncreaseItem(ArrayList<Tile> tilesToBeDestoyed, int x, int y) {
        BombNumberIncreaseItem bombNumberIncreaseItem = new BombNumberIncreaseItem(x * 64, y * 64);
        bombNumberIncreaseItems.add(bombNumberIncreaseItem);
        // bombItemSpawned = true;
    }

    public void placeRollerSkateItem(ArrayList<Tile> tilesToBeDestroyed, int x, int y) {
        RollerSkateItem rollerSkateItem = new RollerSkateItem(x * 64, y * 64);
        rollerSkateItems.add(rollerSkateItem);
        // rollerSkateItemSpawned = true;
    }

    public void placeFireIncreaseItem(ArrayList<Tile> tilesToBeDestoyed, int x, int y) {
        FireRangeIncreaseItem fireRangeIncreaseItem = new FireRangeIncreaseItem(x * 64, y * 64);
        fireRangeItems.add(fireRangeIncreaseItem);
        // fireItemSpawned = true;
    }

    public void placeDetonatorItem(ArrayList<Tile> tilesToBeDestoyed, int x, int y) {
        DetonatorItem detonatorItem = new DetonatorItem(x * 64, y * 64);
        detonatorItems.add(detonatorItem);
        // detonatorItemSpawned = true;
    }

    public void placeBoxItem(ArrayList<Tile> tilesToBeDestoyed, int x, int y) {
        PlaceableBoxItem box = new PlaceableBoxItem(x * 64, y * 64);
        boxItems.add(box);
        // boxItemSpawned = true;
    }

    public void placeTerrainTile(ArrayList<Tile> tilesToBeDestoyed, int row, int col) {
        terrainTilesDiff[row][col] = new Tile(terrainTypes[1]);
        terrainTilesDiff[row][col].tileNum = -1;
    }

    public void draw(Graphics2D g2) {
        for (Tile[] row : mapTilesDiff) {
            for (Tile tile : row) {
                // the map
                g2.drawImage(tile.img, tile.x, tile.y, ge.tileSize, ge.tileSize, null);

            }
        }
        for (Tile[] row : terrainTilesDiff) {
            for (Tile tile : row) {
                g2.drawImage(tile.img, tile.x, tile.y, ge.tileSize, ge.tileSize, null);
            }
        }
        // drawing the enemies
        for (Enemy enemy : enemies) {
            enemy.draw(g2);
        }
        for (ArrayList<Fire> explosion : explosions) {
            for (Fire fire : explosion) {
                fire.draw(g2);
            }
        }
        for (FireRangeIncreaseItem item : fireRangeItems) {
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
            g2.setComposite(composite);

            g2.drawImage(item.img, item.x, item.y, ge.tileSize, ge.tileSize, null);
        }

        for (BombNumberIncreaseItem item : bombNumberIncreaseItems) {
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
            g2.setComposite(composite);
            g2.drawImage(item.img, item.x, item.y, ge.tileSize, ge.tileSize, null);
        }

        for (RollerSkateItem item : rollerSkateItems) {
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
            g2.setComposite(composite);
            g2.drawImage(item.img, item.x, item.y, ge.tileSize, ge.tileSize, null);
        }

        for (DetonatorItem item : detonatorItems) {
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
            g2.setComposite(composite);
            g2.drawImage(item.img, item.x, item.y, ge.tileSize, ge.tileSize, null);
        }

        for (PlaceableBoxItem item : boxItems) {
            AlphaComposite composite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f);
            g2.setComposite(composite);
            g2.drawImage(item.img, item.x, item.y, ge.tileSize, ge.tileSize, null);
        }
    }

    public void update() {

        for (Enemy enemy : enemies) {
            enemy.update(getBorders());
            if (enemy.collisionDetect(getEnemyBorders())) {
                enemy.collided();
            }
            enemy.update(getBorders());
        }

        for (ArrayList<Fire> explosion : explosions) {
            boolean flag = false;
            for (Fire fire : explosion) {
                if (fire.update()) {
                    flag = true;
                }
            }
            if (flag) {
                explosion.clear();
            }
        }
    }

    public ArrayList<Enemy> getEnemies() {
        return enemies;
    }

    public ArrayList<ArrayList<Fire>> getExplosions() {
        return explosions;
    }
}
