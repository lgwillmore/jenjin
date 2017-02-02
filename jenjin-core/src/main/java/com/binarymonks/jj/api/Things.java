package com.binarymonks.jj.api;

import com.binarymonks.jj.async.Function;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.specs.SceneSpec;

import java.util.function.Consumer;

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

    void load(SceneSpec sceneSpec, Function callback);

    void load(SceneSpec sceneSpec);

    /**
     * Create a new object from a Specification.
     *
     * @param thingSpecPath  the path of the specification (should have been already loaded {@link com.binarymonks.jj.api.Specs}
     * @param instanceParams the instance params
     * @param callback       a callback for when the object is actually created.
     *                       If you are making lots of these, remember to pool/reuse or reference an object method.
     */
    void create(String thingSpecPath, InstanceParams instanceParams, Consumer<Thing> callback);

    void create(String thingSpecPath, InstanceParams instanceParams);

}
