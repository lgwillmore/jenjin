package com.binarymonks.jj.render.specs;

import com.binarymonks.jj.render.GraphType;

public class GraphSpec<OWNER> {

    OWNER self;
    public GraphType type = GraphType.DEFAULT;

    public GraphSpec(OWNER self) {
        this.self = self;
    }

    public OWNER setToDefault() {
        type = GraphType.DEFAULT;
        return self;
    }

    public OWNER setToLightSource() {
        type = GraphType.LIGHT_SOURCE;
        return self;
    }
}
