package com.binarymonks.jj.things;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.PoolManager;
import com.binarymonks.jj.specs.ThingSpec;

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

    public InstanceParams setPosition(Vector2 position){
        this.x = position.x;
        this.y = position.y;
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
     * Scale where possible. Not all things will scale as you expect.
     * Also - IMPORTANT - scaling and pooling do not mix. If you are scaling, and creating and destroying the {@link Thing}
     * you will have to set {@link ThingSpec#setPool(boolean)} to false. So scaling is good
     * for things like scenery and terrain.
     *
     * @param scaleX
     * @param scaleY
     * @return
     */
    public InstanceParams setScale(float scaleX, float scaleY) {
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        return this;
    }


    public InstanceParams setProperty(String key, Object value) {
        this.properties.put(key, value);
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
                    .setScale(1, 1)
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
