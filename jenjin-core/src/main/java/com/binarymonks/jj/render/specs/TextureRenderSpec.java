package com.binarymonks.jj.render.specs;

import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.assets.AssetReference;
import com.binarymonks.jj.physics.specs.PhysicsNodeSpec;
import com.binarymonks.jj.render.nodes.RenderNode;
import com.binarymonks.jj.render.nodes.TextureProvider;
import com.binarymonks.jj.render.nodes.TextureRenderNode;

public class TextureRenderSpec extends SpatialRenderSpec<TextureRenderSpec> {

    public BackingTexture backingTexture;
    public float width;
    public float height;

    public TextureRenderSpec(BackingTexture backingTexture, float width, float height) {
        this.backingTexture = backingTexture;
        this.width = width;
        this.height = height;
    }

    public TextureRenderSpec setBackingTexture(BackingTexture backingTexture) {
        this.backingTexture = backingTexture;
        return this;
    }

    public TextureRenderSpec setDimensions(float width, float height) {
        this.width = width;
        this.height = height;
        return this;
    }

    @Override
    public Array<AssetReference> getAssets() {
        return backingTexture.getAssets();
    }

    @Override
    public RenderNode<?> makeNode(PhysicsNodeSpec physicsNodeSpec) {
        TextureProvider provider = this.backingTexture.getProvider();
        return new TextureRenderNode(
                this,
                provider,
                spatial.getOffsetX(physicsNodeSpec),
                spatial.getOffsetY(physicsNodeSpec),
                spatial.getRotationD(physicsNodeSpec),
                width,
                height
        );
    }
}
