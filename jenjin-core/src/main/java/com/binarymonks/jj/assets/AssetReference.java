package com.binarymonks.jj.assets;

public class AssetReference {

    public Class<?> clazz;

    public String assetPath;

    public AssetReference(Class<?> clazz, String assetPath) {
        this.clazz = clazz;
        this.assetPath = assetPath;
    }
}
