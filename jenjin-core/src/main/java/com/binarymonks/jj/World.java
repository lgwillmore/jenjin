package com.binarymonks.jj;

import com.binarymonks.jj.async.Callback;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.specs.SceneSpec;

public class World {

    public void load(SceneSpec sceneSpec, Callback callback) {

    }

    public void load(SceneSpec sceneSpec) {

    }

    /**
     * Retrieve a specific {@link Thing}.
     *
     * @param uniqueName The unique name set in {@link com.binarymonks.jj.things.InstanceParams}
     * @return
     */
    public Thing getThingByName(String uniqueName) {
        return null;
    }
}
