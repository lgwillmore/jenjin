package com.binarymonks.jj.things;

import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.async.Function;
import com.binarymonks.jj.async.OneTimeTask;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.specs.B2DCompositeSpec;
import com.binarymonks.jj.specs.InstanceSpec;

public class SceneLoader {


    public void load(B2DCompositeSpec sceneSpec, Function callback) {
        if (!Global.physics.isUpdating()) {
            JJ.tasks.addPostPhysicsTask(new SceneLoaderTask(callback, sceneSpec));
        } else {
            new SceneLoaderTask(callback, sceneSpec).doOnce();
        }
    }

    public class SceneLoaderTask extends OneTimeTask {

        Function callback;
        B2DCompositeSpec scene;

        public SceneLoaderTask(Function callback, B2DCompositeSpec scene) {
            this.callback = callback;
            this.scene = scene;
        }

        @Override
        protected void doOnce() {
            ObjectMap<Integer, Thing> thingsByID = new ObjectMap<>();
            for (ObjectMap.Entry<Integer, InstanceSpec> thingInstances : scene.thingPieces) {
                thingsByID.put(thingInstances.key, JJ.things.createNow(thingInstances.value.thingSpecPath, thingInstances.value.instanceParams));
            }
            for (B2DCompositeSpec.JointSpec jointSpec : scene.joints) {
                createJoint(thingsByID.get(jointSpec.thingAID), thingsByID.get(jointSpec.thingBID), jointSpec.jointDef);
            }
            callback.call();
        }

        private void createJoint(Thing thingA, Thing thingB, JointDef jointDef) {
            if (jointDef instanceof RevoluteJointDef) {
                RevoluteJointDef revJointDef = (RevoluteJointDef) jointDef;
                revJointDef.bodyA = thingA.physicsroot.getB2DBody();
                revJointDef.bodyB = thingB.physicsroot.getB2DBody();
                Global.physics.world.createJoint(revJointDef);
            }
        }
    }

}
