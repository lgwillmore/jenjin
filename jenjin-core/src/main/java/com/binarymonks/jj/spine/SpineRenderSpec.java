package com.binarymonks.jj.spine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.render.nodes.RenderNode;
import com.binarymonks.jj.specs.physics.PhysicsNodeSpec;
import com.binarymonks.jj.specs.render.RenderSpec;
import com.binarymonks.jj.specs.spine.SpineSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.esotericsoftware.spine.*;
import com.esotericsoftware.spine.attachments.AtlasAttachmentLoader;
import com.esotericsoftware.spine.attachments.RegionAttachment;

public class SpineRenderSpec extends RenderSpec {

    SpineSpec spineSpec;

    public SpineRenderSpec(SpineSpec spineSpec) {
        this.spineSpec = spineSpec;
    }

    @Override
    public RenderNode makeNode(PhysicsNodeSpec physicsNodeSpec, InstanceParams instanceParams) {

        TextureAtlas atlas = JJ.assets.get(spineSpec.atlasPath, TextureAtlas.class);


        AtlasAttachmentLoader atlasLoader = new AtlasAttachmentLoader(atlas);
        SkeletonJson json = new SkeletonJson(atlasLoader);
        json.setScale(spineSpec.scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(spineSpec.dataPath));
        Vector2 positionOffset = N.ew(Vector2.class);
        positionOffset.set(spineSpec.originX, spineSpec.originY).scl(spineSpec.scale);
        Skeleton skeleton = new Skeleton(skeletonData, SpineRenderSpec::getRagDollBone);

        SpineRenderNode spineNode = new SpineRenderNode(this, skeleton, skeletonData, positionOffset);
        spineNode.triggerAnimation(spineSpec.startingAnimation);
        return spineNode;
    }

    static RagDollBone getRagDollBone(BoneData boneData, Skeleton skeleton, Bone parent) {
        return new RagDollBone(boneData, skeleton, parent);
    }

}
