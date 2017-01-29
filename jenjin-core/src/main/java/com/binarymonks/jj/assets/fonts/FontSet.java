package com.binarymonks.jj.assets.fonts;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.utils.ObjectMap;

public class FontSet {
	
	ObjectMap<String,BitmapFont> subFonts = new ObjectMap<>();
	
	public void addSubFont(String name, BitmapFont font){
		subFonts.put(name, font);
	}
	
	public BitmapFont get(String name){
		return  subFonts.get(name);
	}

}
