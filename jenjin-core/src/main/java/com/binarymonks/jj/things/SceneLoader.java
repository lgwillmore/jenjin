package com.binarymonks.jj.things;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.async.Function;
import com.binarymonks.jj.physics.specs.b2d.B2DCompositeSpec;
import com.binarymonks.jj.things.specs.InstanceSpec;
import com.binarymonks.jj.things.specs.SceneSpec;

public class SceneLoader {


    public void load(B2DCompositeSpec sceneSpec) {
        for (ObjectMap.Entry<Integer, InstanceSpec> thingInstances : sceneSpec.thingPieces) {
                JJ.things.create(thingInstances.value.thingSpecPath,thingInstances.value.instanceParams);
        }
    }

}
