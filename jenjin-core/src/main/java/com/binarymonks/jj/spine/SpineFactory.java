package com.binarymonks.jj.spine;

import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.joints.RevoluteJointDef;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ObjectMap;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.physics.CollisionGroups;
import com.binarymonks.jj.specs.ThingSpec;
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
        SpineBoneComponent part = buildBone(spineComponent, rootBone, thingspec, skeleton);
        for (Bone childBone : rootBone.getChildren()) {
            buildRecurseHelper(spineComponent, part, childBone, thingspec, skeleton);
        }
    }

    private void buildRecurseHelper(SpineComponent spineComponent, SpineBoneComponent parentPart, Bone childBone, SpineSpec thingspec, Skeleton skeleton) {
        SpineBoneComponent part = buildBone(spineComponent, childBone, thingspec, skeleton);
        RevoluteJointDef revoluteJointDef = new RevoluteJointDef();
        revoluteJointDef.bodyA = parentPart.getParent().physicsroot.getB2DBody();
        revoluteJointDef.bodyB = part.getParent().physicsroot.getB2DBody();
        revoluteJointDef.localAnchorA.set(part.bone.getX(), part.bone.getY());
        revoluteJointDef.localAnchorB.set(0, 0);
        revoluteJointDef.collideConnected = false;
        revoluteJointDef.enableLimit = false;
        Global.physics.world.createJoint(revoluteJointDef);
        for (Bone grandChildBone : childBone.getChildren()) {
            buildRecurseHelper(spineComponent, part, grandChildBone, thingspec, skeleton);
        }
    }

    private SpineBoneComponent buildBone(SpineComponent spineComponent, Bone bone, SpineSpec thingspec, Skeleton skeleton) {
        String boneName = bone.getData().getName();

        String thingSlotPath = thingspec.getPath() + "/" + boneName;
        ThingSpec partSpec;

        if (box2dThings.containsKey(thingSlotPath)) {
            partSpec = box2dThings.get(thingSlotPath);
        } else {
            float boneLength = bone.getData().getLength();
            partSpec = new ThingSpec();
            partSpec.setPhysics(new PhysicsRootSpec.B2D().setBodyType(BodyDef.BodyType.StaticBody));
            partSpec.setPath(thingSlotPath);
            FixtureNodeSpec fixtureNodeSpec;
            if (thingspec.spineSkeletonSpec.boneOverrides.containsKey(boneName)) {
                fixtureNodeSpec = thingspec.spineSkeletonSpec.boneOverrides.get(boneName);
            } else if (boneLength > 0) {
                fixtureNodeSpec = new FixtureNodeSpec()
                        .setShape(new B2DShapeSpec.PolygonRectangle(boneLength, thingspec.spineSkeletonSpec.boneWidth))
                        .setOffset(boneLength / 2, 0);
                fixtureNodeSpec.collisionData = thingspec.spineSkeletonSpec.collisionData;
            } else {
                fixtureNodeSpec = new FixtureNodeSpec()
                        .setShape(new B2DShapeSpec.Circle(thingspec.spineSkeletonSpec.boneWidth));
                fixtureNodeSpec.collisionData = thingspec.spineSkeletonSpec.collisionData;
            }
            fixtureNodeSpec.addInitialBeginCollision(new TriggerRagDollCollision());
            partSpec.newNode().setPhysics(
                    fixtureNodeSpec
            );
            partSpec.addComponent(new SpineBoneComponent());

            box2dThings.put(thingSlotPath, partSpec);
        }

        SpineBoneComponent part = Global.factories.things.create(partSpec, InstanceParams.New()).getComponent(SpineBoneComponent.class);
        spineComponent.addBone(boneName, part);
        part.setBone((RagDollBone) bone);
        return part;
    }


}
