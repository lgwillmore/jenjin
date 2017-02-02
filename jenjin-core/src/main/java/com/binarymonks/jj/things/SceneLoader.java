package com.binarymonks.jj.things;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.async.Function;
import com.binarymonks.jj.things.specs.SceneSpec;

public class SceneLoader {


    public void load(SceneSpec sceneSpec) {
        for (ObjectMap.Entry<String, Array<InstanceParams>> thingInstances : sceneSpec.instances) {
            for (InstanceParams ip : thingInstances.value) {
                JJ.things.create(thingInstances.key,ip);
            }
        }
    }

}
