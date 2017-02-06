package com.binarymonks.jj.assets;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetDescriptor;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.api.Assets;
import com.binarymonks.jj.assets.fonts.FontSet;
import com.binarymonks.jj.assets.loaders.GifTextureLoader;
import com.binarymonks.jj.assets.loaders.TTFontLoader;
import com.binarymonks.jj.assets.types.GifTexture;
import com.binarymonks.jj.async.Function;
import com.binarymonks.jj.async.Task;

public class AssetManager implements Assets {


    com.badlogic.gdx.assets.AssetManager manager;
    int initialCapacity = 50;
    ObjectMap<String, Disposable> assetCache = new ObjectMap<>(initialCapacity);


    public AssetManager() {
        InternalFileHandleResolver resolver = new InternalFileHandleResolver();
        manager = new com.badlogic.gdx.assets.AssetManager(resolver);
        manager.setLoader(GifTexture.class, new GifTextureLoader(resolver));
        manager.setLoader(FontSet.class, new TTFontLoader(resolver));
    }


    @Override
    public void clear() {
        manager.clear();
    }


    public void dispose() {
        manager.dispose();
    }


    public void finishLoading() {
        manager.finishLoading();
    }


    @Override
    public <T> T get(String fileName, Class<T> type) {
        return manager.get(fileName, type);
    }


    public float getProgress() {
        return manager.getProgress();
    }


    public int getReferenceCount(String fileName) {
        return manager.getReferenceCount(fileName);
    }


    public boolean isLoaded(String fileName) {
        return manager.isLoaded(fileName);
    }


    @Override
    public <T> void load(String fileName, Class<T> type) {
        manager.load(fileName, type);
    }


    @Override
    public void unload(String arg0) {
        //TODO: Reference counting in GDX manager not working as expected...
        //manager.unload(arg0);
    }

    @Override
    public boolean update() {
        return manager.update();
    }

    @Override
    public Texture getTextureNow(String filePath) {
        if (!isLoaded(filePath)) {
            manager.load(filePath, Texture.class);
            manager.finishLoading();
        }
        return manager.get(filePath, Texture.class);
    }

    @Override
    public BitmapFont getFontNow(String filePath) {
        if (!isLoaded(filePath)) {
            manager.load(filePath, BitmapFont.class);
            manager.finishLoading();
        }
        return manager.get(filePath, BitmapFont.class);
    }

    public Music getMusicNow(String filePath) {
        if (!isLoaded(filePath)) {
            manager.load(filePath, Music.class);
            manager.finishLoading();
        }
        return manager.get(filePath, Music.class);
    }

    @Override
    public Skin getSkinNow(String filePath) {
        if (assetCache.containsKey(filePath)) {
            return (Skin) assetCache.get(filePath);
        }
        Skin s = new Skin(Gdx.files.internal(filePath));
        assetCache.put(filePath, s);
        return s;
    }

    @Override
    public FontSet getFontSetNow(String path) {
        if (!isLoaded(path)) {
            manager.load(path, FontSet.class);
            manager.finishLoading();
        }
        return manager.get(path, FontSet.class);
    }

    @Override
    public void loadThen(Array<AssetReference> assets, Function callback) {
        JJ.tasks.addPreLoopTask(new AsyncAssetLoader(assets, callback));
    }

    @Override
    public void loadNow(Array<AssetReference> assets) {
        for(AssetReference assetRef : assets){
            manager.load(assetRef.assetPath,assetRef.clazz);
        }
        manager.finishLoading();
    }

    @Override
    public void load(AssetDescriptor<?> desc) {
        manager.load(desc);
    }

    public static class AsyncAssetLoader implements Task {

        Array<AssetReference> assets;
        Function callback;
        String loggingTag = "AsyncAssetLoader";

        public AsyncAssetLoader(Array<AssetReference> assets, Function callback) {
            this.assets = assets;
            this.callback = callback;
        }

        @Override
        public void getReady() {
            Gdx.app.log(loggingTag, "Loading assets:");
            for (AssetReference assetRef : assets) {
                Gdx.app.log(loggingTag, " - " + assetRef.assetPath);
                JJ.assets.load(assetRef.assetPath, assetRef.clazz);
            }
        }

        @Override
        public void doWork() {

        }

        @Override
        public void tearDown() {
            callback.call();
        }

        @Override
        public boolean isDone() {
            for (AssetReference asset : assets) {
                if (!JJ.assets.isLoaded(asset.assetPath)) {
                    return false;
                }
            }
            return true;
        }
    }
}
