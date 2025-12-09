package com.zenith.Bomberman;

import com.zenith.Bomberman.KeyboardInputs;
import org.junit.jupiter.api.Test;
import javax.swing.*;
import java.awt.event.KeyEvent;
import com.zenith.Bomberman.GameEngine;
import com.zenith.Bomberman.GameEngine;
import com.zenith.Bomberman.KeyboardInputs;

import static org.junit.jupiter.api.Assertions.*;

class GameEngineTest {

    @Test
    void testInitialization() {
        GameEngine gameEngine = new GameEngine();
        assertNotNull(gameEngine);
        assertNotNull(gameEngine.getPlaying().getLevel());
        assertFalse(gameEngine.getPlaying().isPause());
        assertNull(gameEngine.getGameThread());
    }

    @Test
    void testGameThread() throws InterruptedException {
        GameEngine gameEngine = new GameEngine();
        gameEngine.startGameThread();
        Thread gameThread = gameEngine.getGameThread();
        assertNotNull(gameThread);
        assertTrue(gameThread.isAlive());
    }

}
