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
    public float scaleX = 1;
    public float scaleY = 1;
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

    public InstanceParams setScaleX(float scaleX) {
        this.scaleX = scaleX;
        return this;
    }

    public InstanceParams setScaleY(float scaleY) {
        this.scaleY = scaleY;
        return this;
    }

    public InstanceParams setProperty(String key, Object value){
        this.properties.put(key,value);
        return this;
    }

    /**
     * Use this to checkPools a new one. This is so recycling and pooling
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
                    .setScaleX(1)
                    .setScaleY(1)
                    .setUniqueName(null);
        }

        @Override
        public InstanceParams create_new() {
            return new InstanceParams();
        }

        @Override
        public void dispose(InstanceParams instanceParams) {

        }
    }

}
