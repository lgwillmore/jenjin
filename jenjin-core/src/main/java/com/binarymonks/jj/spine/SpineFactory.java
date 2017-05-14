package com.binarymonks.jj.spine;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.specs.ThingNodeSpec;
import com.binarymonks.jj.specs.ThingSpec;
import com.binarymonks.jj.specs.physics.PhysicsNodeSpec;
import com.binarymonks.jj.specs.physics.PhysicsRootSpec;
import com.binarymonks.jj.specs.physics.b2d.B2DShapeSpec;
import com.binarymonks.jj.specs.physics.b2d.FixtureNodeSpec;
import com.binarymonks.jj.specs.spine.SpineSpec;
import com.binarymonks.jj.things.*;
import com.esotericsoftware.spine.Bone;
import com.esotericsoftware.spine.Skeleton;

public class SpineFactory extends ThingFactory<SpineSpec> {

    ObjectMap<String, ThingSpec> box2dThings = new ObjectMap<>();


    @Override
    protected Array<ThingNode> buildNodes(Thing thing, SpineSpec thingSpec, InstanceParams instanceParams, Body body) {
        Array<ThingNode> nodes = super.buildNodes(thing, thingSpec, instanceParams, body);
        SpineRenderSpec renderSpec = new SpineRenderSpec(thingSpec);

        ThingNode node = new ThingNode(null);

        SpineRenderNode render = (SpineRenderNode) renderSpec.makeNode(null, instanceParams);
        node.render = render;
        nodes.add(node);

        SpineComponent spineComponent = new SpineComponent();
        thing.addComponent(spineComponent);

        ThingTools.addNodeToThing(thing, node);
        if (thingSpec.spineSkeletonSpec != null) {
            Skeleton skeleton = render.skeleton;
            buildBoneRecurse(spineComponent, skeleton.getRootBone(), thingSpec, skeleton);
        }

        return nodes;
    }


    private void buildBoneRecurse(SpineComponent spineComponent, Bone rootBone, SpineSpec thingspec, Skeleton skeleton) {
        SpineBoneComponent part = buildBone(spineComponent, rootBone, thingspec, skeleton, thingspec.spineSkeletonSpec.everyBone.coreMass);
        for (Bone childBone : rootBone.getChildren()) {
            buildRecurseHelper(spineComponent, part, childBone, thingspec, skeleton, thingspec.spineSkeletonSpec.everyBone.coreMass);
        }
    }

    private void buildRecurseHelper(SpineComponent spineComponent, SpineBoneComponent parentPart, Bone childBone, SpineSpec thingspec, Skeleton skeleton, float currentMass) {
        float mass = currentMass * thingspec.spineSkeletonSpec.everyBone.coreMass;
        SpineBoneComponent part = buildBone(spineComponent, childBone, thingspec, skeleton, mass);
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = parentPart.getParent().physicsroot.getB2DBody();
        revoluteJointDef.bodyB = part.getParent().physicsroot.getB2DBody();
        revoluteJointDef.localAnchorA.set(part.bone.getX(), part.bone.getY());
        revoluteJointDef.localAnchorB.set(0, 0);
        revoluteJointDef.collideConnected = true;
        revoluteJointDef.enableLimit = false;
        Global.physics.world.createJoint(revoluteJointDef);
        for (Bone grandChildBone : childBone.getChildren()) {
            buildRecurseHelper(spineComponent, part, grandChildBone, thingspec, skeleton, mass);
        }
    }

    private SpineBoneComponent buildBone(SpineComponent spineComponent, Bone bone, SpineSpec thingspec, Skeleton skeleton, float mass) {
        String boneName = bone.getData().getName();

        String thingSlotPath = thingspec.getPath() + "/" + boneName;
        ThingSpec partSpec;

        if (box2dThings.containsKey(thingSlotPath)) {
            partSpec = box2dThings.get(thingSlotPath);
        } else {
            partSpec = buildNewBoneSpec(thingSlotPath, boneName, bone, thingspec.spineSkeletonSpec, mass);
        }

        SpineBoneComponent part = Global.factories.things.create(partSpec, InstanceParams.New()).getComponent(SpineBoneComponent.class);
        spineComponent.addBone(boneName, part);
        part.setBone((RagDollBone) bone);
        return part;
    }

    private ThingSpec buildNewBoneSpec(String thingSlotPath, String boneName, Bone bone, SpineSkeletonSpec spineSkeletonSpec, float mass) {
        ThingSpec partSpec;
        float boneLength = bone.getData().getLength();
        partSpec = new ThingSpec();
        partSpec.setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.DynamicBody));
        partSpec.setPath(thingSlotPath);
        if(spineSkeletonSpec.everyBone.append!=null){
            append(boneName,spineSkeletonSpec,mass,partSpec,boneLength,spineSkeletonSpec.everyBone.append);
        }
        if (spineSkeletonSpec.boneAppendices.containsKey(boneName)) {
            BoneAppendSpec appendixSpec = spineSkeletonSpec.boneAppendices.get(boneName);
            append(boneName, spineSkeletonSpec, mass, partSpec, boneLength, appendixSpec);
        }
        FixtureNodeSpec fixtureNodeSpec = getFixtureNodeSpec(boneName, spineSkeletonSpec, mass, boneLength);
        partSpec.newNode().setPhysics(
                fixtureNodeSpec
        );
        partSpec.addComponent(new SpineBoneComponent());
        box2dThings.put(thingSlotPath, partSpec);
        return partSpec;
    }

    private void append(String boneName, SpineSkeletonSpec spineSkeletonSpec, float mass, ThingSpec partSpec, float boneLength, BoneAppendSpec appendixSpec) {
        for (BoneAppendNodeSpec node : appendixSpec.nodes) {
            ThingNodeSpec nodeSpec = new ThingNodeSpec();
            nodeSpec.properties.putAll(node.properties);
            nodeSpec.renderSpec = node.renderSpec;
            nodeSpec.components.addAll(node.components);
            nodeSpec.setName(node.name);
            if (node.physicsNodeSpec instanceof BonePhysicsNodeSpec.B2DBoneFixture) {
                BonePhysicsNodeSpec.B2DBoneFixture copySpec = (BonePhysicsNodeSpec.B2DBoneFixture) node.physicsNodeSpec;
                FixtureNodeSpec fixtureSpec = getFixtureNodeSpec(boneName, spineSkeletonSpec, mass, boneLength);
                fixtureSpec.initialBeginCollisions.addAll(copySpec.initialBeginCollisions);
                fixtureSpec.finalBeginCollisions.addAll(copySpec.finalBeginCollisions);
                fixtureSpec.endCollisions.addAll(copySpec.endCollisions);
                fixtureSpec.isSensor = copySpec.isSensor;
                fixtureSpec.density=0;
                fixtureSpec.shape= copySpec.shape;
                fixtureSpec.offsetX=copySpec.offsetX;
                fixtureSpec.offsetY=copySpec.offsetY;
                nodeSpec.physicsNodeSpec = (PhysicsNodeSpec) node.physicsNodeSpec;
            }
            if (node.physicsNodeSpec instanceof BonePhysicsNodeSpec.BoneShadowNode) {
                BonePhysicsNodeSpec.BoneShadowNode copySpec = (BonePhysicsNodeSpec.BoneShadowNode) node.physicsNodeSpec;
                FixtureNodeSpec fixtureSpec = getFixtureNodeSpec(boneName, spineSkeletonSpec, mass, boneLength);
                fixtureSpec.initialBeginCollisions.addAll(copySpec.initialBeginCollisions);
                fixtureSpec.finalBeginCollisions.addAll(copySpec.finalBeginCollisions);
                fixtureSpec.endCollisions.addAll(copySpec.endCollisions);
                fixtureSpec.isSensor = copySpec.isSensor;
                fixtureSpec.density=0;
                nodeSpec.physicsNodeSpec = fixtureSpec;
            }
            partSpec.nodes.add(nodeSpec);
        }
        partSpec.components.addAll(appendixSpec.components);
        partSpec.lights.addAll(appendixSpec.lights);
    }

    private FixtureNodeSpec getFixtureNodeSpec(String boneName, SpineSkeletonSpec spineSkeletonSpec, float mass, float boneLength) {
        FixtureNodeSpec fixtureNodeSpec;
        if (spineSkeletonSpec.boneOverrides.containsKey(boneName)) {
            fixtureNodeSpec = spineSkeletonSpec.boneOverrides.get(boneName);
        } else {
            if (boneLength > 0) {
                fixtureNodeSpec = new FixtureNodeSpec()
                        .setShape(new B2DShapeSpec.PolygonRectangle(boneLength, spineSkeletonSpec.everyBone.boneWidth))
                        .setOffset(boneLength / 2, 0)
                        .setDensity(mass)
                        .setSensor(spineSkeletonSpec.everyBone.isSensor)
                ;
                fixtureNodeSpec.collisionData = spineSkeletonSpec.everyBone.collisionData;
            } else {
                fixtureNodeSpec = new FixtureNodeSpec()
                        .setShape(new B2DShapeSpec.Circle(spineSkeletonSpec.everyBone.boneWidth))
                        .setDensity(mass)
                        .setSensor(spineSkeletonSpec.everyBone.isSensor)
                ;
                fixtureNodeSpec.collisionData = spineSkeletonSpec.everyBone.collisionData;
            }
            if (spineSkeletonSpec.everyBone.collisionFunction != null) {
                fixtureNodeSpec.addInitialBeginCollision(spineSkeletonSpec.everyBone.collisionFunction.clone());
            }
        }
        return fixtureNodeSpec;
    }


}
