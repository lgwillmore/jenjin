package com.binarymonks.jj.api;

import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.assets.AssetReference;
import com.binarymonks.jj.assets.fonts.FontSet;
import com.binarymonks.jj.async.Function;


public interface Assets {

    public <T> T get(String filePath, Class<T> type);

    public boolean update();

    public void clear();

    public <T> void load(String filePath, Class<T> type);

    public void load(AssetDescriptor<?> desc);

    public void unload(String filePath);

    public int getReferenceCount(String fileName);

    public boolean isLoaded(String filePath);

    public Texture getTextureNow(String texturePath);

    public BitmapFont getFontNow(String string);

    public Skin getSkinNow(String path);

    public FontSet getFontSetNow(String path);


    void loadThen(Array<AssetReference> assets, Function callback);
}