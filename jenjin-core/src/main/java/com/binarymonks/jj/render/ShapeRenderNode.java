package com.binarymonks.jj.render;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.composable.Dimension;
import com.binarymonks.jj.composable.Spatial;
import com.binarymonks.jj.render.composable.Draw;
import com.binarymonks.jj.render.composable.RenderOrder;

/**
 * Created by lwillmore on 30/01/17.
 */
public abstract class ShapeRenderNode extends RenderNode {
    Draw draw;

    public ShapeRenderNode(Spatial spatial, RenderOrder order, Draw draw) {
        this.spatial = spatial;
        this.order = order;
        this.draw = draw;
    }

    @Override
    public void render() {
        ShapeRenderer renderer = Global.renderWorld.shapeRenderer;
        renderer.begin(draw.fill ? ShapeRenderer.ShapeType.Filled : ShapeRenderer.ShapeType.Line);
        renderer.setColor(draw.color);
        drawShape();
        renderer.end();
    }

    protected abstract void drawShape();

    public static class RectangleNode extends ShapeRenderNode {

        Dimension dimension;

        public RectangleNode(Spatial spatial, RenderOrder order, Draw draw, Dimension dimension) {
            super(spatial, order, draw);
            this.dimension = dimension;
        }

        @Override
        protected void drawShape() {
            //float x, float y, float originX, float originY, float width, float height, float scaleX, float scaleY,float degrees
            Global.renderWorld.shapeRenderer.rect(0, 0, dimension.width / 2, dimension.height / 2, dimension.width, dimension.height, 1, 1, 0);
        }
    }

}
