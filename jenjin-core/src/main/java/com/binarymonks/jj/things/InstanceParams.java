package com.binarymonks.jj.things;

import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.PoolManager;

/**
 * This defines the parameters of an instance of a {@link Thing} on creation.
 * This is pooled by default, as you tend to make a lot of these.
 * The recycling is handled automatically though, so don't worry.
 */
public class InstanceParams {

    public float x;
    public float y;
    public float rotationD = 0;
    public String uniqueInstanceName;

    ObjectMap<String, Object> properties = new ObjectMap<>();

    InstanceParams() {

    }

    public InstanceParams setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public InstanceParams setRotationD(float rotationD) {
        this.rotationD = rotationD;
        return this;
    }

    public InstanceParams setUniqueName(String uniqueName) {
        this.uniqueInstanceName = uniqueName;
        return this;
    }

    /**
     * Use this to get a new one. This is so recycling and pooling
     * can be handled automatically.
     */
    public static InstanceParams New() {
        return N.ew(InstanceParams.class);
    }


    public static class PM implements PoolManager<InstanceParams> {

        @Override
        public void reset(InstanceParams instanceParams) {
            instanceParams.properties.clear();
            instanceParams
                    .setPosition(0, 0)
                    .setRotationD(0)
                    .setUniqueName(null);
        }

        @Override
        public InstanceParams create_new() {
            return new InstanceParams();
        }
    }

}