package com.binarymonks.jj.render;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.PolygonRegion;
import com.badlogic.gdx.graphics.g2d.PolygonSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.render.specs.RenderSpec;
import com.binarymonks.jj.render.specs.ShapeRenderSpec;

/**
 * Created by lwillmore on 30/01/17.
 */
public class PolygonRenderNode extends RenderNode<RenderSpec> {

    PolygonSprite poly;


    PolygonRenderNode(RenderSpec renderSpec, PolygonSprite poly) {
        super(renderSpec);
        this.poly = poly;
    }

    @Override
    public void render(OrthographicCamera camera) {
        Vector2 parentPos = parent.physicsroot.position();
        poly.setColor(color.get());
        poly.setOrigin(0, 0);
        poly.setRotation(parent.physicsroot.rotationR() * MathUtils.radDeg);
        poly.setPosition(parentPos.x, parentPos.y);
        poly.draw(Global.renderWorld.polyBatch);
    }

    @Override
    public void dispose() {
    }


    public static boolean haveBuilt(RenderSpec sourceSpec) {
        return Global.renderWorld.polySpriteCache.containsKey(sourceSpec.id);
    }

    public static PolygonRenderNode rebuild(RenderSpec sourceSpec) {
        PolygonSprite polygonSprite;
        polygonSprite = Global.renderWorld.polySpriteCache.get(sourceSpec.id);
        return new PolygonRenderNode(sourceSpec, polygonSprite);
    }

    public static PolygonRenderNode buildNew(RenderSpec sourceSpec, Array<Vector2> vertices, Vector2 offset, float rotationD) {
        PolygonSprite polygonSprite;
        Matrix3 trMatrix = N.ew(Matrix3.class);
        trMatrix.translate(offset.x, offset.y);
        trMatrix.rotate(rotationD);
        for (Vector2 vertex : vertices) {
            vertex.mul(trMatrix);
        }
        polygonSprite = Global.renderWorld.polygonSprite(sourceSpec.id, vertices);
        Re.cycleItems(vertices);
        Re.cycle(trMatrix);
        return new PolygonRenderNode(sourceSpec, polygonSprite);
    }

}
