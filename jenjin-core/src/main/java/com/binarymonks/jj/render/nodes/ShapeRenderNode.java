package com.binarymonks.jj.render.nodes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Transform;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.render.specs.RenderSpec;
import com.binarymonks.jj.render.specs.ShapeRenderSpec;
import com.binarymonks.jj.render.specs.SpatialRenderSpec;


public abstract class ShapeRenderNode<SPEC extends RenderSpec> extends RenderNode<SPEC> {

    protected boolean fill = true;

    public ShapeRenderNode(SPEC renderSpec, boolean fill) {
        super(renderSpec);
        this.fill = fill;
    }

    @Override
    public void render(OrthographicCamera camera) {
        Global.renderWorld.switchToShapes(fill);
        ShapeRenderer renderer = Global.renderWorld.shapeRenderer;
        renderer.setColor(color.get());
        drawShape(camera);
    }

    protected abstract void drawShape(OrthographicCamera camera);

    public static class Circle extends ShapeRenderNode<RenderSpec> {

        public Vector2 offset = N.ew(Vector2.class);
        public float radius;
        Vector2 positionCache = N.ew(Vector2.class);
        Vector3 positionCache3 = N.ew(Vector3.class);

        public Circle(RenderSpec renderSpec, boolean fill) {
            super(renderSpec, fill);
        }

        @Override
        public void dispose() {

        }

        @Override
        protected void drawShape(OrthographicCamera camera) {
            Transform transform = parent.physicsroot.getTransform();
            positionCache.set(offset);
            transform.mul(positionCache);
            positionCache3.set(positionCache.x, positionCache.y, 0);
            camera.project(positionCache3);
            Global.renderWorld.shapeRenderer.circle(positionCache3.x, positionCache3.y, radius*Global.renderWorld.worldToScreenScale);
        }
    }

}
