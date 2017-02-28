package com.binarymonks.jj.api;

import com.binarymonks.jj.async.Function;
import com.binarymonks.jj.specs.SceneSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.binarymonks.jj.things.SceneParams;
import com.binarymonks.jj.things.Thing;

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

    void loadNow(SceneSpec sceneSpec);

    /**
     * Create a new instance of a {@link com.binarymonks.jj.specs.SceneNodeSpec}.
     *
     * This is suitable for when you need to instantiate a spec during the physics loop and do something with the result
     *
     * @param thingSpecPath  the path of the specification (should have been already loaded {@link com.binarymonks.jj.api.Specs}
     * @param instanceParams the instance params
     * @param callback       a callback for when the instance is actually created.
     *                       If you are making lots of these, remember to pool/reuse or reference an object method.
     */
    void create(String thingSpecPath, InstanceParams instanceParams, Consumer<Thing> callback);


    /**
     * Create a new instance of a {@link com.binarymonks.jj.specs.SceneNodeSpec}.
     *
     * Use this when you do not need the result. The instance will be created as soon as possible :D
     *
     * @param thingSpecPath
     * @param instanceParams
     */
    void create(String thingSpecPath, InstanceParams instanceParams);


    /**
     * Create a new instance of a {@link com.binarymonks.jj.specs.SceneNodeSpec}.
     *
     * When you can't be asked using a callback to get your result, and you are outside of the physics step.
     *
     * The return might be null if you are in the physics step for example.
     * @param thingSpecPath
     * @param instanceParams
     * @return
     */
    Thing createNow(String thingSpecPath, InstanceParams instanceParams);
}
