package com.binarymonks.jj.render.nodes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Transform;
import com.binarymonks.jj.JJ;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.render.specs.RenderSpec;
import com.binarymonks.jj.render.specs.TextureRenderSpec;

public class TextureRenderNode extends RenderNode<RenderSpec> {

    TextureProvider provider;
    float offsetX;
    float offsetY;
    float rotationD;
    float width;
    float height;

    public TextureRenderNode(RenderSpec spec, TextureProvider provider, float offsetX, float offsetY, float rotationD, float width, float height) {
        super(spec);
        this.provider = provider;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
        this.rotationD = rotationD;
        this.width = width;
        this.height = height;
    }

    @Override
    public void render(OrthographicCamera camera) {
        float relativeRotationD = parent.physicsroot.rotationR() * MathUtils.radiansToDegrees + rotationD;
        TextureRegion frame = provider.getFrame(relativeRotationD);
        if (frame != null) {
            Global.renderWorld.switchToBatch();
            Transform transform = parent.physicsroot.getTransform();
            Vector2 position = N.ew(Vector2.class).set(offsetX, offsetY);
            transform.mul(position);
            Global.renderWorld.polyBatch.draw(frame, position.x - (width / 2), position.y - (height / 2), width / 2, height / 2, width,
                    height, 1, 1, relativeRotationD);
        }
    }

    @Override
    public void dispose() {
        provider.dispose();
    }
}
