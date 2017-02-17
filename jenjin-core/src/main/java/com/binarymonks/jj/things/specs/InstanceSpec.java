package com.binarymonks.jj.things.specs;

import com.binarymonks.jj.things.InstanceParams;

/**
 * Created by lwillmore on 15/02/17.
 */
public class InstanceSpec {
    public String thingSpecPath;
    public InstanceParams instanceParams;

    public InstanceSpec(String thingSpecPath, InstanceParams instanceParams) {
        this.thingSpecPath = thingSpecPath;
        this.instanceParams = instanceParams;
    }
}
