package com.binarymonks.jj.render.specs;

import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.JJ;

/**
 * Specifications for how to determine position stuff in world space.
 */
public interface SpatialSpec {
    /****************************************
     *           Implementers Below         *
     ****************************************/

    /**
     * Positioning relative to the {@link com.binarymonks.jj.objects.ObjectRoot}
     */
    class ObjectRelative implements SpatialSpec {
        public Vector2 offset = JJ.pools.nuw(Vector2.class);
        public float rotationD = 0;

        public ObjectRelative offset(float x, float y) {
            offset.set(x, y);
            return this;
        }

        public ObjectRelative rotationD(float rotationD) {
            this.rotationD = rotationD;
            return this;
        }
    }
}
