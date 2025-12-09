package com.zenith.Bomberman;

public class Constants {
    public static class Directions {
        public static final int LEFT = 2;
        public static final int UP = 4;
        public static final int RIGHT = 1;
        public static final int DOWN = 3;
    }

    public static class GameConstants {
        public static final int TILESIZE = 64;

    }

    public static class PlayerConstants {
        public static final int IDLE = 0;
        public static final int RUNNING = 1;
        public static final int HIT = 2;

        public static int GetSpriteAmount(int player_action) {
            switch (player_action) {
                case RUNNING:
                    return 1;
                case IDLE:
                    return 0;
                case HIT:
                    return 2;
                default:
                    return 1;
            }
        }

        public int getIdle() {
            return IDLE;
        }
    }

}