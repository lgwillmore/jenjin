package com.binarymonks.jj.things;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.api.Specs;
import com.binarymonks.jj.assets.AssetReference;
import com.binarymonks.jj.async.Function;
import com.binarymonks.jj.audio.SoundParams;
import com.binarymonks.jj.things.specs.ThingSpec;

public class Specifications implements Specs {
    ObjectMap<String, ThingSpec> specifications = new ObjectMap<>();

    @Override
    public Specs set(String path, ThingSpec thingSpec) {
        if (specifications.containsKey(path)) {
            throw new RuntimeException(String.format("There is already a specification for path %s", path));
        }
        specifications.put(path, thingSpec);
        return this;
    }

    @Override
    public void loadSpecAssetsThen(Function callback) {
        Array<AssetReference> assets = getAllAssets();
        JJ.assets.loadThen(assets,callback);
    }

    @Override
    public void loadSpecAssetsNow() {
        Array<AssetReference> assets = getAllAssets();
        JJ.assets.loadNow(assets);
    }

    private Array<AssetReference> getAllAssets() {
        Array<AssetReference> assets = new Array<>();
        for (ObjectMap.Entry<String, ThingSpec> specification : specifications) {
            for (SoundParams sound : specification.value.sounds) {
                for (String path : sound.soundPaths) {
                    assets.add(new AssetReference(Sound.class, path));
                }
            }
        }
        return assets;
    }
}
