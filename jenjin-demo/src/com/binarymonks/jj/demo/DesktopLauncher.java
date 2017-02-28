package com.binarymonks.jj.demo;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.demo.d08.D08_arrow_game;
import com.binarymonks.jj.demo.d09.D09_spine;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration lwjglConfig = new LwjglApplicationConfiguration();
        lwjglConfig.height = 1000;
        lwjglConfig.width = 1000;
        JJConfig jjconfig = new JJConfig();
        jjconfig.b2dDebug = true;
        jjconfig.gameViewConfig.worldBoxWidth=100;
        jjconfig.gameViewConfig.cameraPosX=50;
        jjconfig.gameViewConfig.cameraPosY=50;
        new LwjglApplication(new D09_spine(jjconfig), lwjglConfig);
//        new LwjglApplication(new D03_pooling_load_test(jjconfig), lwjglConfig);
    }
}
