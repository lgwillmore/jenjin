package com.binarymonks.jj.demo;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.binarymonks.jj.JJConfig;
import com.binarymonks.jj.demo.d00.D00_Basic;
import com.binarymonks.jj.demo.d01.D01_full_simple_game;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration lwjglConfig = new LwjglApplicationConfiguration();
		JJConfig jjconfig = new JJConfig();
		jjconfig.b2dDebug=true;
		new LwjglApplication(new D00_Basic(jjconfig), lwjglConfig);
	}
}
