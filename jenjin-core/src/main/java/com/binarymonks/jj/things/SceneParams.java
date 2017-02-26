package com.binarymonks.jj.things;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.PoolManager;
import com.binarymonks.jj.specs.ThingSpec;

/**
 * This defines the parameters of a nested or transformed {@link com.binarymonks.jj.specs.SceneSpec}
 * This is pooled by default, as you can make a lot of these.
 * The recycling is handled automatically though, so don't worry.
 */
public class SceneParams {

    public float x;
    public float y;
    public float rotationD = 0;

    SceneParams() {

    }

    public SceneParams setPosition(float x, float y) {
        this.x = x;
        this.y = y;
        return this;
    }

    public SceneParams setPosition(Vector2 position){
        this.x = position.x;
        this.y = position.y;
        return this;
    }

    public SceneParams setRotationD(float rotationD) {
        this.rotationD = rotationD;
        return this;
    }

    /**
     * Use this to get a new one. This is so recycling and pooling
     * can be handled automatically.
     */
    public static SceneParams New() {
        return N.ew(SceneParams.class);
    }


    public static class PM implements PoolManager<SceneParams> {

        @Override
        public void reset(SceneParams instanceParams) {
            instanceParams
                    .setPosition(0, 0)
                    .setRotationD(0);
        }

        @Override
        public SceneParams create_new() {
            return new SceneParams();
        }

        @Override
        public void dispose(SceneParams instanceParams) {

        }
    }

}
