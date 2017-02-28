package com.binarymonks.jj.things;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.api.Specs;
import com.binarymonks.jj.assets.AssetReference;
import com.binarymonks.jj.async.Function;
import com.binarymonks.jj.audio.SoundParams;
import com.binarymonks.jj.specs.SceneNodeSpec;
import com.binarymonks.jj.specs.ThingNodeSpec;
import com.binarymonks.jj.specs.ThingSpec;
import com.binarymonks.jj.specs.spine.SpineSpec;

public class Specifications implements Specs {
    ObjectMap<String, SceneNodeSpec> specifications = new ObjectMap<>();
    boolean dirty = false;

    @Override
    public Specs set(String path, SceneNodeSpec sceneNodeSpec) {
        if (specifications.containsKey(path)) {
            throw new RuntimeException(String.format("There is already a specification for path %s", path));
        }
        specifications.put(path, sceneNodeSpec);
        sceneNodeSpec.setPath(path);
        dirty = true;
        return this;
    }

    @Override
    public boolean isDirty() {
        return dirty;
    }

    @Override
    public void loadSpecAssetsThen(Function callback) {
        if (dirty) {
            Array<AssetReference> assets = getAllAssets();
            JJ.assets.loadThen(assets, callback);
        } else {
            callback.call();
        }
    }

    @Override
    public void loadSpecAssetsNow() {
        if (dirty) {
            Array<AssetReference> assets = getAllAssets();
            JJ.assets.loadNow(assets);
        }
    }

    private Array<AssetReference> getAllAssets() {
        Array<AssetReference> assets = new Array<>();
        for (ObjectMap.Entry<String, SceneNodeSpec> specification : specifications) {
            if (specification.value instanceof ThingSpec) {
                ThingSpec spec = (ThingSpec) specification.value;
                addSoundAssets(assets, spec);
                addRenderAssets(assets, spec);
            }
            if (specification.value instanceof SpineSpec) {
                SpineSpec spec = (SpineSpec) specification.value;
                assets.add(new AssetReference(TextureAtlas.class, spec.atlasPath));

            }
        }
        return assets;
    }

    private void addRenderAssets(Array<AssetReference> assets, ThingSpec spec) {
        for (ThingNodeSpec node : spec.nodes) {
            assets.addAll(node.renderSpec.getAssets());
        }
    }

    private void addSoundAssets(Array<AssetReference> assets, ThingSpec spec) {
        for (SoundParams sound : spec.sounds) {
            for (String path : sound.soundPaths) {
                assets.add(new AssetReference(Sound.class, path));
            }
        }
    }
}
