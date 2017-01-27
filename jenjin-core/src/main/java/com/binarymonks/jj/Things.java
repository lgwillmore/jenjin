package com.binarymonks.jj;

import com.binarymonks.jj.things.specs.ThingSpec;

/**
 * API for Things
 */
public class Things {

    public Specs specs = new Specs();

    public World world = new World();

    /**
     * API for loading and setting the set of {@link com.binarymonks.jj.things.specs.ThingSpec}s
     */
    public static class Specs {

        public Specs set(String path, ThingSpec thingSpec) {
            return this;
        }

    }

}
