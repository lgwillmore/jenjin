package com.binarymonks.jj.things;

import com.binarymonks.jj.api.Things;
import com.binarymonks.jj.async.Callback;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.things.specs.SceneSpec;

public class ThingManager implements Things {

    SceneLoader sceneLoader = new SceneLoader();

    @Override
    public Thing getThingByName(String uniqueName) {
        return null;
    }

    @Override
    public void load(SceneSpec sceneSpec, Callback callback) {
        sceneLoader.load(sceneSpec, callback);
    }

    @Override
    public void create(String thingSpecPath, InstanceParams instanceParams) {
        Global.factories.things.create(thingSpecPath,instanceParams);
    }
}
