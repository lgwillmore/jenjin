package com.binarymonks.jj.backend;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.specs.ThingSpec;
import com.binarymonks.jj.things.SceneFactory;
import com.binarymonks.jj.spine.SpineFactory;
import com.binarymonks.jj.things.Thing;
import com.binarymonks.jj.things.ThingFactory;

public class Factories {
    public ThingFactory<ThingSpec> things = new ThingFactory<>();
    public SceneFactory scenes = new SceneFactory();
    public SpineFactory spine = new SpineFactory();


    ObjectMap<String, Array<Thing>> pooledThings = new ObjectMap<>();
    private int idCounter = 0;

    public int nextID() {
        return idCounter++;
    }

    public Thing checkPools(String thingSpecPath) {
        if (pooledThings.containsKey(thingSpecPath) && pooledThings.get(thingSpecPath).size > 0) {
            return pooledThings.get(thingSpecPath).pop();
        }
        return null;
    }

    public void recycle(Thing thing) {
        if (!pooledThings.containsKey(thing.path)) {
            pooledThings.put(thing.path, new Array<>());
        }
        pooledThings.get(thing.path).add(thing);
    }


}
