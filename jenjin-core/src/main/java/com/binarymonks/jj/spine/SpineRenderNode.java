package com.binarymonks.jj.spine;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.render.nodes.RenderNode;
import com.esotericsoftware.spine.Event;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;

public class SpineRenderNode extends RenderNode<SpineRenderSpec> {

    Skeleton skeleton;
    SkeletonData skeletonData;
    Vector2 positionOffset;
    String currentAnimation;
    float animationTimeElapsed;
    Array<Event> events = new Array(); //What is this?

    public SpineRenderNode(SpineRenderSpec spec, Skeleton skeleton, SkeletonData skeletonData, Vector2 positionOffset) {
        super(spec);
        this.skeleton = skeleton;
        this.skeletonData = skeletonData;
        this.positionOffset = positionOffset;
    }

    public void triggerAnimation(String animationName) {
        currentAnimation = animationName;
        animationTimeElapsed = 0;
    }

    @Override
    public void render(OrthographicCamera camera) {
        animationTimeElapsed += JJ.time.getDeltaFloat();
        Vector2 position = parent.physicsroot.position().sub(positionOffset);

        if (currentAnimation != null) {
            skeletonData.findAnimation(currentAnimation).apply(skeleton, animationTimeElapsed, animationTimeElapsed, true, events, 1, false, false);
        }
        skeleton.setPosition(position.x, position.y);
        skeleton.updateWorldTransform();


        Global.renderWorld.switchToBatch();
        Global.renderWorld.skeletonRenderer.draw(Global.renderWorld.polyBatch, skeleton);

    }

    @Override
    public void dispose() {
    }
}
