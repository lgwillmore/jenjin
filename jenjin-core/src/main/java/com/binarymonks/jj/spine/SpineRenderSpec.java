package com.binarymonks.jj.spine;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.render.nodes.RenderNode;
import com.binarymonks.jj.specs.physics.PhysicsNodeSpec;
import com.binarymonks.jj.specs.render.RenderSpec;
import com.binarymonks.jj.specs.spine.SpineSpec;
import com.binarymonks.jj.things.InstanceParams;
import com.esotericsoftware.spine.Animation;
import com.esotericsoftware.spine.Skeleton;
import com.esotericsoftware.spine.SkeletonData;
import com.esotericsoftware.spine.SkeletonJson;
import com.esotericsoftware.spine.attachments.AtlasAttachmentLoader;

public class SpineRenderSpec extends RenderSpec {

    SpineSpec spineSpec;

    public SpineRenderSpec(SpineSpec spineSpec) {
        this.spineSpec = spineSpec;
    }

    @Override
    public RenderNode makeNode(PhysicsNodeSpec physicsNodeSpec, InstanceParams instanceParams) {

        TextureAtlas atlas = JJ.assets.get(spineSpec.atlasPath,TextureAtlas.class);

        // This loader creates Box2dAttachments instead of RegionAttachments for an easy way to keep
        // track of the Box2D body for each attachment.
        AtlasAttachmentLoader atlasLoader = new AtlasAttachmentLoader(atlas);
        SkeletonJson json = new SkeletonJson(atlasLoader);
        json.setScale(spineSpec.scale);
        SkeletonData skeletonData = json.readSkeletonData(Gdx.files.internal(spineSpec.dataPath));
        Vector2 positionOffset = N.ew(Vector2.class);
        positionOffset.set(spineSpec.originX,spineSpec.originY).scl(spineSpec.scale);
        Skeleton skeleton = new Skeleton(skeletonData);

        SpineRenderNode spineNode = new SpineRenderNode(this, skeleton, skeletonData, positionOffset);
        spineNode.triggerAnimation(spineSpec.startingAnimation);
        return spineNode;
    }
}
