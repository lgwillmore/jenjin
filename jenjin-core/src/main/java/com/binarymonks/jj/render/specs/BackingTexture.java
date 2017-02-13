package com.binarymonks.jj.render.specs;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.assets.AssetReference;
import com.binarymonks.jj.render.nodes.TextureProvider;
import com.binarymonks.jj.utils.Empty;

public abstract class BackingTexture<CONCRETE extends BackingTexture> implements Json.Serializable {
    public String path;
    CONCRETE self = (CONCRETE) this;

    public BackingTexture() {
    }

    public BackingTexture(String path) {
        this.path = path;
    }

    public CONCRETE setPath(String path) {
        this.path = path;
        return self;
    }

    public abstract TextureProvider getProvider();

    public Array<AssetReference> getAssets() {
        return Empty.Array();
    }

    @Override
    public String toString() {
        return "BackingTexture{" +
                "path='" + path + '\'' +
                '}';
    }

    public static class Simple extends BackingTexture {
        public Simple() {
        }

        public Simple(String path) {
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


        @Override
        public void write(Json json) {
            json.writeValue("path", path);
        }

        @Override
        public void read(Json json, JsonValue jsonData) {
            this.path = jsonData.getString("path");
        }
    }
}
