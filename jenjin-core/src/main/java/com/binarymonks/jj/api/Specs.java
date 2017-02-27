package com.binarymonks.jj.api;

import com.binarymonks.jj.async.Function;
import com.binarymonks.jj.specs.SceneNodeSpec;
import com.binarymonks.jj.specs.ThingSpec;
import com.binarymonks.jj.specs.spine.SpineSpec;

public interface Specs {

    Specs set(String path, SceneNodeSpec thingSpec);

    void loadSpecAssetsThen(Function callback);

    void loadSpecAssetsNow();
}
