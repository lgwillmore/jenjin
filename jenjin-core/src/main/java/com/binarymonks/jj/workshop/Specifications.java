package com.binarymonks.jj.workshop;

import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.api.Specs;
import com.binarymonks.jj.things.specs.ThingSpec;

public class Specifications implements Specs {
    ObjectMap<String, ThingSpec> specifications = new ObjectMap<>();

    @Override
    public Specs set(String path, ThingSpec thingSpec) {
        if (specifications.containsKey(path)) {
            throw new RuntimeException(String.format("There is already a specification for path %s", path));
        }
        specifications.put(path,thingSpec);
        return this;
    }
}
