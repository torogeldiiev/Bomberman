package com.zenith.Bomberman;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.zenith.Bomberman.Player;
import com.zenith.Bomberman.GameEngine;
import com.zenith.Bomberman.GameEngine;
import com.zenith.Bomberman.Player;
import com.zenith.Bomberman.Tile;
import com.zenith.Bomberman.Level;
import com.zenith.Bomberman.Enemy;
import com.zenith.Bomberman.Bomb;
import com.zenith.Bomberman.Fire;
import java.util.ArrayList;

public class JudehTest {

    private Player player;
    private Level level;

    @BeforeEach
    public void setUp() {
        GameEngine ge = new GameEngine();
        player = new Player(ge, 1);
        level = new Level(ge, 1);
    }

    @Test
    public void testBombConstructor() {
        Bomb bomb = new Bomb(5, 10, 3);
        assertEquals(5, bomb.getBombX());
        assertEquals(10, bomb.getBombY());
        assertEquals(3, bomb.getFireLength());
    }

    @Test
    public void testIncBombTimer() {
        Bomb bomb = new Bomb(0, 0, 2);
        bomb.incBombTimer();
        assertEquals(1, bomb.getBombTimer());
    }

    @Test
    public void testResetBombTimer() {
        Bomb bomb = new Bomb(0, 0, 2);
        bomb.incBombTimer();
        bomb.resetBombTimer();
        assertEquals(0, bomb.getBombTimer());
    }

    @Test
    public void testAddBomb() {
        assertEquals(0, player.getBombs().size());
        player.addBomb();
        assertEquals(1, player.getBombs().size());
    }

    @Test
    public void testBombCollisions() {
        assertEquals(0, player.getBombs().size());
        player.addBomb();
        player.addBomb();
        assertEquals(1, player.getBombs().size());
    }

    @Test
    public void testExplosions() {
        level.explode(player, 0, 0);
        ArrayList<ArrayList<Fire>> explosions = level.getExplosions();
        for (ArrayList<Fire> tmp : explosions) {
            for (Fire fire : tmp) {
                assertNotNull(fire);
            }
        }
    }

    @Test
    public void testFireLocations() {
        GameEngine ge = new GameEngine();
        Player bob = new Player(ge, 1);
        bob.setX(100);
        bob.setY(100);
        ArrayList<Fire> explosion = new ArrayList<>();
        Fire tmp = new Fire(-1, -1);
        explosion.add(tmp);
        for (int dir = 0; dir < 4; dir++) {
            for (int i = 0; i < player.getFireLength(); i++) {
                switch (dir) {
                    case 0:
                        tmp = new Fire(bob.getX() + (64 * (i + 1)), bob.getY());
                        break;
                    case 1:
                        tmp = new Fire(bob.getX(), bob.getY() + (64 * (i + 1)));
                        break;
                    case 2:
                        tmp = new Fire(bob.getX() + (-64 * (i + 1)), bob.getY());
                        break;
                    case 3:
                        tmp = new Fire(bob.getX(), bob.getY() + (-64 * (i + 1)));
                        break;
                }
            }
        }
        for (int i = 0; i < explosion.size(); i++) {
            for (int j = i + 1; j < explosion.size(); j++) {
                assertNotEquals(explosion.get(i), explosion.get(j));
            }
        }
    }

}
