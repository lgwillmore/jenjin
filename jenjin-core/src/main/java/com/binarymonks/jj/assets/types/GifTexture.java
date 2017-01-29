package com.binarymonks.jj.assets.types;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;

public class GifTexture implements Disposable {
	public Texture texture;
	public Array<TextureRegion> textureRegions;

	public GifTexture(Texture texture, Array<TextureRegion> texReg) {
		super();
		this.texture = texture;
		this.textureRegions=texReg;
	}

	@Override
	public void dispose() {
		texture.dispose();
	}
	
	
}
