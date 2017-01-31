package com.binarymonks.jj.workshop;

import com.binarymonks.jj.render.RenderNode;
import com.binarymonks.jj.render.ShapeRenderNode;
import com.binarymonks.jj.render.specs.RenderSpec;

public class RenderFactory {
    public RenderNode build(RenderSpec renderSpec) {
        if (renderSpec instanceof RenderSpec.Shape) {
            return buildShape((RenderSpec.Shape) renderSpec);
        }
        return noSpec(renderSpec);
    }


    private RenderNode buildShape(RenderSpec.Shape renderSpec) {
        if (renderSpec instanceof RenderSpec.Shape.Rect) {
            RenderSpec.Shape.Rect rectSpec = (RenderSpec.Shape.Rect) renderSpec;
            return new ShapeRenderNode.RectangleNode(
                    rectSpec.spatial.clone(),
                    rectSpec.order.clone(),
                    rectSpec.draw.clone(),
                    rectSpec.dimension.clone()
            );
        }
        return noSpec(renderSpec);
    }

    private RenderNode noSpec(RenderSpec renderSpec) {
        throw new RuntimeException("No Node for " + renderSpec.getClass().getCanonicalName());
    }
}
