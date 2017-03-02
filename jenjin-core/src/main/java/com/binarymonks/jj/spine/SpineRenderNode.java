package com.binarymonks.jj.spine;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.render.nodes.RenderNode;
import com.esotericsoftware.spine.Skeleton;

public class SpineRenderNode extends RenderNode<SpineRenderSpec> {

    Skeleton skeleton;

    public SpineRenderNode(SpineRenderSpec spec, Skeleton skeleton) {
        super(spec);
        this.skeleton = skeleton;
    }

    @Override
    public void render(OrthographicCamera camera) {
        Vector2 position = parent.physicsroot.position();
        skeleton.setPosition(position.x,position.y);
        skeleton.updateWorldTransform();

        Global.renderWorld.skeletonRenderer.draw(Global.renderWorld.polyBatch,skeleton);

    }

    @Override
    public void dispose() {
    }
}
