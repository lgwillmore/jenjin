package com.binarymonks.jj.demo;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.demo.d00.D00_Basic;
import com.binarymonks.jj.demo.d01.D01_pong;
import com.binarymonks.jj.demo.d02.D02_b2d_shapes;
import com.binarymonks.jj.demo.d03.D03_pooling_load_test;
import com.binarymonks.jj.demo.d04.D04_lights;

public class DesktopLauncher {
    public static void main(String[] arg) {
        LwjglApplicationConfiguration lwjglConfig = new LwjglApplicationConfiguration();
        lwjglConfig.height = 1000;
        lwjglConfig.width = 1000;
        JJConfig jjconfig = new JJConfig();
        jjconfig.b2dDebug=false;
        new LwjglApplication(new D02_b2d_shapes(jjconfig), lwjglConfig);
//        new LwjglApplication(new D03_pooling_load_test(jjconfig), lwjglConfig);
    }
}
