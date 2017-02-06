package com.binarymonks.jj.demo;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.demo.d01.D01_pong;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration lwjglConfig = new LwjglApplicationConfiguration();
        lwjglConfig.height = 1000;
        lwjglConfig.width = 1000;
        JJConfig jjconfig = new JJConfig();
        new LwjglApplication(new D01_pong(jjconfig), lwjglConfig);
    }
}
