package com.binarymonks.jj.specs;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.things.InstanceParams;

/**
 * A graph of {@Link com.binarymonks.jj.things.Thing} instances to make.
 */
public class SceneSpec {
    public ObjectMap<String, Array<InstanceParams>> instances = new ObjectMap<>();

    public SceneSpec add(String thingSpecName, InstanceParams... instanceParams) {
        if (!instances.containsKey(thingSpecName)) {
            instances.put(thingSpecName, new Array<InstanceParams>());
        }
        instances.get(thingSpecName).addAll(instanceParams);
        return this;
    }

}
