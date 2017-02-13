package com.binarymonks.jj.render.specs;

import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.physics.specs.PhysicsNodeSpec;
import com.binarymonks.jj.render.nodes.RenderNode;


public class AnimatedRenderSpec extends SpatialRenderSpec<AnimatedRenderSpec> {

    public BackingTexture backingTexture;
    public int rows;
    public int columns;
    Array<AnimationSequence> sequences = new Array<>();

    public AnimatedRenderSpec addAnimation(AnimationSequence sequence) {
        sequences.add(sequence);
        return this;
    }

    public AnimatedRenderSpec setBackingTexture(BackingTexture backingTexture) {
        this.backingTexture = backingTexture;
        return this;
    }


    public AnimatedRenderSpec setRowsNColumns(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        return this;
    }

    @Override
    public RenderNode<?> makeNode(PhysicsNodeSpec physicsNodeSpec) {
        return null;
    }
}
