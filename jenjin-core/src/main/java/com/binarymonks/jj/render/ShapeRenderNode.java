package com.binarymonks.jj.render;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
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
    public void render() {
        ShapeRenderer renderer = Global.renderWorld.shapeRenderer;
        renderer.begin(spec.fill ? ShapeRenderer.ShapeType.Filled : ShapeRenderer.ShapeType.Line);
        renderer.setColor(spec.color);
        drawShape();
        renderer.end();
    }

    protected abstract void drawShape();

    public static class RectangleNode extends ShapeRenderNode<ShapeRenderSpec.Rectangle> {


        public RectangleNode(ShapeRenderSpec.Rectangle spec) {
            super(spec);
        }

        @Override
        protected void drawShape() {
            //float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY,float degrees
            Global.renderWorld.shapeRenderer.rect(0, 0, spec.width / 2, spec.height / 2, spec.width, spec.height, 1, 1, 0);
        }
    }

}
