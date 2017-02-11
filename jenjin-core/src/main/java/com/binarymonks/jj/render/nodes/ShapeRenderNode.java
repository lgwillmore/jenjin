package com.binarymonks.jj.render.nodes;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.binarymonks.jj.backend.Global;
import com.binarymonks.jj.render.specs.ShapeRenderSpec;


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

}
