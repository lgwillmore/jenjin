package com.binarymonks.jj.assets.loaders;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.AssetLoaderParameters;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.AsynchronousAssetLoader;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.assets.fonts.FontSet;
import com.binarymonks.jj.assets.fonts.TTFontParams;
import com.binarymonks.jj.assets.loaders.TTFontLoader.TTFontLoaderParameters;

public class TTFontLoader extends AsynchronousAssetLoader<FontSet, TTFontLoaderParameters> {


	public TTFontLoader(FileHandleResolver resolver) {
		super(resolver);
	}

	@Override
	public void loadAsync(AssetManager manager, String fileName, FileHandle file, TTFontLoaderParameters parameter) {

	}

	@Override
	public FontSet loadSync(AssetManager manager, String fileName, FileHandle file, TTFontLoaderParameters parameter) {
		FontSet fontset = new FontSet();
		FreeTypeFontGenerator generator = new FreeTypeFontGenerator(file);
		for (TTFontParams param : parameter.subFonts) {
			FreeTypeFontParameter freeTypeFontParameter = new FreeTypeFontParameter();
			freeTypeFontParameter.size = param.getSize();
			BitmapFont font = generator.generateFont(freeTypeFontParameter);
			fontset.addSubFont(param.getName(), font);
		}
		return fontset;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public Array<AssetDescriptor> getDependencies(String fileName, FileHandle file, TTFontLoaderParameters parameter) {
		return new Array<AssetDescriptor>();
	}

	public static class TTFontLoaderParameters extends AssetLoaderParameters<FontSet> {

		Array<TTFontParams> subFonts = new Array<>();
		
		public void add(TTFontParams fontParam){
			subFonts.add(fontParam);
		}

	}

}
