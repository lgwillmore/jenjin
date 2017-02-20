package com.binarymonks.jj.api;

import com.binarymonks.jj.async.Function;
import com.binarymonks.jj.specs.ThingSpec;

public interface Specs {

    Specs set(String path, ThingSpec thingSpec);

    void loadSpecAssetsThen(Function callback);

    void loadSpecAssetsNow();
}
