package com.binarymonks.jj.render.nodes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.utils.Array;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.pools.N;
import com.binarymonks.jj.pools.Re;
import com.binarymonks.jj.specs.render.RenderSpec;


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
            Global.renderWorld.shapeRenderer.circle(positionCache3.x, positionCache3.y, radius * Global.renderWorld.worldToScreenScale);
        }
    }

    public static class ChainLine extends ShapeRenderNode<RenderSpec> {

        Array<Vector2> vertices;
        Array<Vector3> vertex3Cache = new Array<>();

        public ChainLine(RenderSpec renderSpec, Array<Vector2> vertices) {
            super(renderSpec, false);
            this.vertices=vertices;
        }

        @Override
        public void dispose() {

        }

        @Override
        protected void drawShape(OrthographicCamera camera) {
            clearCaches();
            Transform transform = parent.physicsroot.getTransform();
            for (Vector2 vertex : vertices) {
                Vector2 v2c = N.ew(Vector2.class).set(vertex);
                transform.mul(v2c);
                Vector3 v3c = N.ew(Vector3.class).set(v2c.x, v2c.y, 0);
                camera.project(v3c);
                vertex3Cache.add(v3c);
                Re.cycle(v2c);
            }
            for (int i = 1; i < vertex3Cache.size; i++) {
                Global.renderWorld.shapeRenderer.line(vertex3Cache.get(i - 1), vertex3Cache.get(i));
            }
        }

        private void clearCaches() {
            for (Vector3 cacheVec : vertex3Cache) {
                Re.cycle(cacheVec);
            }
            vertex3Cache.clear();
        }
    }
}
