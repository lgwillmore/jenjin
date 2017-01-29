package com.binarymonks.jj.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.assets.loaders.GifTextureLoader.GifLoaderParameters;
import com.binarymonks.jj.assets.types.GifTexture;

import java.io.IOException;
import java.io.InputStream;

public class GifTextureLoader extends AsynchronousAssetLoader<GifTexture,GifLoaderParameters> {
	
	GifReader gifReader = new GifReader();
	GifTexture gifTexture;

	public GifTextureLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, GifLoaderParameters parameter) {
		
	}

	@Override
	public GifTexture loadSync(AssetManager manager, String fileName, FileHandle file, GifLoaderParameters parameter) {
		try(InputStream in = file.read()){
			gifTexture = gifReader.readGifTexture(in);
		} catch (IOException e) {
			throw new RuntimeException("Could not load gif",e);
		}
		GifTexture gTex = gifTexture;
		gifTexture=null;
		return gTex;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, GifLoaderParameters parameter) {
		return new Array<AssetDescriptor>();
	}
	
	public class GifLoaderParameters extends AssetLoaderParameters<GifTexture> {

	}

}
