package com.binarymonks.jj.things;

import com.badlogic.gdx.physics.box2d.JointDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.physics.box2d.joints.WeldJointDef;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.async.Function;
import com.binarymonks.jj.async.OneTimeTask;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Poolable;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.specs.SceneSpec;
import com.binarymonks.jj.specs.InstanceSpec;

public class SceneLoader {


    public void load(SceneSpec sceneSpec, SceneParams sceneParams, Function callback) {
        if (!Global.physics.isUpdating()) {
            JJ.tasks.addPostPhysicsTask(N.ew(SceneLoaderTask.class).set(sceneSpec, sceneParams, callback));
        } else {
            N.ew(SceneLoaderTask.class).set(sceneSpec, sceneParams, callback).doOnce();
        }
    }

    public static class SceneLoaderTask extends OneTimeTask implements Poolable {

        Function callback;
        SceneSpec scene;
        SceneParams sceneParams;

        public SceneLoaderTask set(SceneSpec scene, SceneParams sceneParams, Function callback) {
            this.callback = callback;
            this.scene = scene;
            this.sceneParams = sceneParams;
            return this;
        }

        @Override
        protected void doOnce() {
            ObjectMap<Integer, Thing> thingsByID = new ObjectMap<>();
            for (ObjectMap.Entry<Integer, InstanceSpec> thingInstances : scene.thingPieces) {
                InstanceParams instanceParams = thingInstances.value.instanceParams;
                InstanceParams ipCopy = instanceParams.clone();
                ipCopy.setPosition(ipCopy.x+sceneParams.x,ipCopy.y+sceneParams.y);
                ipCopy.setRotationD(ipCopy.rotationD+sceneParams.rotationD);
                thingsByID.put(thingInstances.key, JJ.things.createNow(thingInstances.value.thingSpecPath, ipCopy));
            }
            for (SceneSpec.JointSpec jointSpec : scene.joints) {
                createJoint(thingsByID.get(jointSpec.thingAID), thingsByID.get(jointSpec.thingBID), jointSpec.jointDef);
            }
            callback.call();
            Re.cycle(this);
        }

        private void createJoint(Thing thingA, Thing thingB, JointDef jointDef) {
            jointDef.bodyA=thingA.physicsroot.getB2DBody();
            jointDef.bodyB=thingB.physicsroot.getB2DBody();
            Global.physics.world.createJoint(jointDef);
        }

        @Override
        public void reset() {
            callback=null;
            scene=null;
            sceneParams=null;
        }
    }

}
