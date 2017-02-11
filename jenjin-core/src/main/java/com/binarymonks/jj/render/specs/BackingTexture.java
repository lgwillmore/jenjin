package com.binarymonks.jj.render.specs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.assets.AssetReference;
import com.binarymonks.jj.render.nodes.TextureProvider;
import com.binarymonks.jj.utils.Empty;

public abstract class BackingTexture {
    public String path;

    public BackingTexture(String path) {
        this.path = path;
    }

    public abstract TextureProvider getProvider();

    public Array<AssetReference> getAssets() {
        return Empty.Array();
    }

    public static class SimpleTexture extends BackingTexture {
        public SimpleTexture(String path) {
            super(path);
        }

        @Override
        public TextureProvider getProvider() {
            return new TextureProvider.SimpleTextureProvider(JJ.assets.get(path, Texture.class));
        }

        @Override
        public Array<AssetReference> getAssets() {
            Array<AssetReference> assets = new Array<>();
            assets.add(new AssetReference(Texture.class, path));
            return assets;
        }
    }
}
