package com.binarymonks.jj.spine;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.binarymonks.jj.components.Component;

public class SpineBoneComponent extends Component {

    SpineComponent spineParent;
    RagDollBone bone;
    boolean ragDoll = false;

    @Override
    public Component clone() {
        return new SpineBoneComponent();
    }

    @Override
    public void doWork() {
        updatePosition();
    }

    @Override
    public void tearDown() {

    }

    @Override
    public void getReady() {

    }

    public void updatePosition() {
        if(!ragDoll) {
            float x = bone.getWorldX();
            float y = bone.getWorldY();
            float rotation = bone.getWorldRotationX();
            parent.physicsroot.getB2DBody().setTransform(x, y, rotation * MathUtils.degRad);
        }
    }

    public void triggerRagDoll() {
        if (!ragDoll) {
            ragDoll = true;
            bone.triggerRagDoll();
            parent.physicsroot.getB2DBody().setType(BodyDef.BodyType.DynamicBody);
            spineParent.triggerRagDoll();
        }
    }

    public void setBone(RagDollBone bone){
        bone.spinePart=this;
        this.bone=bone;
    }
}
