package com.binarymonks.jj.render.specs;

import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.binarymonks.jj.assets.AssetReference;
import com.binarymonks.jj.physics.specs.PhysicsNodeSpec;
import com.binarymonks.jj.render.nodes.RenderNode;
import com.binarymonks.jj.render.nodes.TextureProvider;
import com.binarymonks.jj.render.nodes.TextureRenderNode;

public class TextureRenderSpec extends SpatialRenderSpec<TextureRenderSpec> implements Json.Serializable {

    public BackingTexture backingTexture;
    public float width;
    public float height;

    public TextureRenderSpec() {
    }

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

    @Override
    public String toString() {
        return "TextureRenderSpec{" +
                "backingTexture=" + backingTexture +
                ", width=" + width +
                ", height=" + height +
                "} " + super.toString();
    }

    @Override
    public void write(Json json) {
        super.write(json);
        json.writeValue("backingTexture", backingTexture, null, backingTexture.getClass());
        json.writeValue("width", width);
        json.writeValue("height", height);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        super.read(json, jsonData);
        width = jsonData.getFloat("width");
        height = jsonData.getFloat("height");
        backingTexture = json.readValue(null, jsonData.get("backingTexture"));
    }
}
