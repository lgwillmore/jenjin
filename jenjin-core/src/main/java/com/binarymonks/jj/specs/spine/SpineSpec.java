package com.binarymonks.jj.specs.spine;

import com.binarymonks.jj.specs.ThingSpec;

public class SpineSpec extends ThingSpec {

    public enum DataType {JSON, SKEL}

    public String atlasPath;
    public String dataPath;
    public DataType dataType;
    public float scale;

    public SpineSpec(String atlasPath, String dataPath, DataType dataType, float scale) {
        this.atlasPath = atlasPath;
        this.dataPath = dataPath;
        this.dataType = dataType;
        this.scale = scale;
    }

    public SpineSpec setAtlasPath(String atlasPath) {
        this.atlasPath = atlasPath;
        return this;
    }

    public SpineSpec setData(String dataPath, DataType type) {
        this.dataPath = dataPath;
        this.dataType = type;
        return this;
    }

    public SpineSpec setScale(float scale) {
        this.scale = scale;
        return this;
    }
}
