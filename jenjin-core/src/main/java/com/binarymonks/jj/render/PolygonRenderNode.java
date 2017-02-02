package com.binarymonks.jj.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.render.specs.RenderSpec;
import com.binarymonks.jj.render.specs.ShapeRenderSpec;

/**
 * Created by lwillmore on 30/01/17.
 */
public class PolygonRenderNode extends RenderNode<RenderSpec> {

    PolygonSprite poly;


    public PolygonRenderNode(RenderSpec renderSpec, PolygonSprite poly) {
        super(renderSpec);
        this.poly = poly;
    }

    @Override
    public void render(OrthographicCamera camera) {
        Vector2 parentPos = parent.physicsroot.position();
        poly.setColor(spec.color);
        poly.setOrigin(0, 0);
        poly.setRotation(parent.physicsroot.rotationR() * MathUtils.radDeg);
        poly.setPosition(parentPos.x, parentPos.y);
        poly.draw(Global.renderWorld.polyBatch);
    }

}
