package com.binarymonks.jj.render;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.render.specs.ShapeRenderSpec;

/**
 * Created by lwillmore on 30/01/17.
 */
public abstract class ShapeRenderNode<SPEC extends ShapeRenderSpec> extends RenderNode<SPEC> {

    public ShapeRenderNode(SPEC renderSpec) {
        super(renderSpec);
    }

    @Override
    public void render(OrthographicCamera camera) {
        ShapeRenderer renderer = Global.renderWorld.shapeRenderer;
        renderer.begin(spec.fill ? ShapeRenderer.ShapeType.Filled : ShapeRenderer.ShapeType.Line);
        renderer.setColor(color.get());
        drawShape(camera);
        renderer.end();
    }

    protected abstract void drawShape(OrthographicCamera camera);

    public static class RectangleNode extends ShapeRenderNode<ShapeRenderSpec.Rectangle> {


        public RectangleNode(ShapeRenderSpec.Rectangle spec) {
            super(spec);
        }

        @Override
        protected void drawShape(OrthographicCamera camera) {
            Vector2 parent_pos = parent.physicsroot.position();
            //TODO: Make drawing of any polygon possible - set of vertices. takes care of scaling and rotation.
            //float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY,float degrees
            Global.renderWorld.shapeRenderer.rect(parent_pos.x, parent_pos.y, spec.width / 2, spec.height / 2, spec.width, spec.height, 1, 1, 0);
        }
    }

}
