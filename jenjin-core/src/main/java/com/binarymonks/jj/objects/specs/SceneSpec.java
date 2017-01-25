package com.binarymonks.jj.objects.specs;

import com.badlogic.gdx.utils.Array;

public class SceneSpec {
    Array<RootSpec> objectRoots = new Array<>();

    public SceneSpec add(RootSpec rootSpec) {
        objectRoots.add(rootSpec);
        return this;
    }
}
