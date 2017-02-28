package com.binarymonks.jj.specs.spine;

import com.binarymonks.jj.specs.SceneNodeSpec;

public class SpineSpec extends SceneNodeSpec{

    public enum DataType {JSON, SKEL}

    public String atlasPath;
    public String dataPath;
    public DataType dataType;

    public SpineSpec setAtlasPath(String atlasPath) {
        this.atlasPath = atlasPath;
        return this;
    }

    public SpineSpec setData(String dataPath, DataType type) {
        this.dataPath = dataPath;
        this.dataType=type;
        return this;
    }

}
