package com.binarymonks.jj;

public class JJConfig {
    public boolean b2dDebug = false;
    public GameViewConfig gameViewConfig = new GameViewConfig();

    public static class GameViewConfig{
        public float worldBoxWidth=100;
        public float cameraPosX;
        public float cameraPosY;
    }
}
