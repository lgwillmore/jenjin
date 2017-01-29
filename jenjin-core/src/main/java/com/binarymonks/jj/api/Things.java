package com.binarymonks.jj.api;

import com.binarymonks.jj.async.Callback;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.specs.SceneSpec;

/**
 * API for Things
 */
public interface Things {

    /**
     * Retrieve a specific {@link Thing}.
     *
     * @param uniqueName The unique name set in {@link com.binarymonks.jj.things.InstanceParams}
     * @return
     */
    Thing getThingByName(String uniqueName);

    void load(SceneSpec sceneSpec, Callback callback);


    void create(String thingSpecPath, InstanceParams instanceParams);
}
