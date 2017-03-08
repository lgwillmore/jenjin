package com.binarymonks.jj.specs.spine;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.binarymonks.jj.specs.ThingSpec;
import com.binarymonks.jj.specs.physics.PhysicsRootSpec;
import com.binarymonks.jj.spine.SpineSkeletonSpec;

public class SpineSpec extends ThingSpec {

    public SpineSkeletonSpec spineSkeletonSpec;

    public enum DataType {JSON, SKEL}

    public String atlasPath;
    public String dataPath;
    public DataType dataType;
    public float scale = 1;
    public float originX;
    public float originY;
    public String startingAnimation;

    public SpineSpec() {
        setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.StaticBody));
    }

    public SpineSpec(String atlasPath, String dataPath, DataType dataType) {
        this();
        this.atlasPath = atlasPath;
        this.dataPath = dataPath;
        this.dataType = dataType;
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

    /**
     * Set the scale of Spine space to world/box2d space.
     *
     * @param scale: 1/[spine_pixels_per_meter]
     * @return
     */
    public SpineSpec setScale(float scale) {
        this.scale = scale;
        return this;
    }

    /**
     * Set the origin for the spine coordinates.
     * The location of your center bone.
     * <p>
     * This will be the location in Spine space that is centered on the {@link com.binarymonks.jj.things.Thing} position.
     *
     * @param x
     * @param y
     * @return
     */
    public SpineSpec setOrigin(float x, float y) {
        this.originX = x;
        this.originY = y;
        return this;
    }

    public SpineSpec setStartingAnimation(String startingAnimation) {
        this.startingAnimation = startingAnimation;
        return this;
    }

    public SpineSpec setSkeletonSpec(SpineSkeletonSpec skeletonSpec) {
        this.spineSkeletonSpec = skeletonSpec;
        return this;
    }
}
