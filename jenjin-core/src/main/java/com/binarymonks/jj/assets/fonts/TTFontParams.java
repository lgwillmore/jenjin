package com.binarymonks.jj.assets.fonts;

public class TTFontParams {
	
	String name;
	int size;
	
	public TTFontParams(String name) {
		this.name=name;
	}

	public String getName() {
		return name;
	}

	public TTFontParams setName(String name) {
		this.name = name;
		return this;
	}

	public int getSize() {
		return size;
	}

	public TTFontParams setSize(int size) {
		this.size = size;
		return this;
	}
	
	

}
