package com.binarymonks.jj.specs.render;


import com.badlogic.gdx.graphics.Color;

public abstract class RenderBuilder<SELF extends RenderBuilder, SPEC extends RenderSpec> {

    protected SPEC spec;
    protected SELF self = (SELF) this;

    public RenderBuilder(SPEC spec) {
        this.spec = spec;
    }

    public SELF setLayer(int layer) {
        spec.layer = layer;
        return self;
    }

    public SELF setPriority(int priority) {
        spec.priority = priority;
        return self;
    }

    public SELF setGraphToDefault() {
        spec.renderGraph.setToDefault();
        return self;
    }

    public SELF setGraphToLightSource() {
        spec.renderGraph.setToLightSource();
        return self;
    }

    public SELF setColor(Color color) {
        spec.color.set(color);
        return self;
    }

    public SELF delegateColorTo(String property) {
        spec.color.delegateToProperty(property);
        return self;
    }

    public SPEC build() {
        return spec;
    }

    public static B2DBuilder b2d() {
        return new B2DBuilder();
    }

    public static TextureBuilder texture(BackingTexture backingTexture, float width, float height) {
        return new TextureBuilder(backingTexture, width, height);
    }

    public static RectangleBuilder shapeRectangle(float width, float height) {
        return new RectangleBuilder(width, height);
    }

    public static AnimatedBuilder animated(BackingTexture backingTexture, int rows, int columns, float width, float height){
        return new AnimatedBuilder(backingTexture,width,height);
    }

    static abstract class SpatialBuilder<SELF extends SpatialBuilder, SPEC extends SpatialRenderSpec> extends RenderBuilder<SELF, SPEC> {

        public SpatialBuilder(SPEC spec) {
            super(spec);
        }

        public SELF setSpatial(Spatial spatial) {
            spec.spatial = spatial;
            return self;
        }
    }

    public static class TextureBuilder extends SpatialBuilder<TextureBuilder, TextureRenderSpec> {

        public TextureBuilder(BackingTexture backingTexture, float width, float height) {
            super(new TextureRenderSpec());
            spec.backingTexture = backingTexture;
            spec.width = width;
            spec.height = height;
        }
    }

    public abstract static class ShapeBuilder<SELF extends ShapeBuilder, SPEC extends ShapeRenderSpec> extends SpatialBuilder<SELF, SPEC> {

        public ShapeBuilder(SPEC spec) {
            super(spec);
        }

        public SELF setFill(boolean fill) {
            spec.fill = fill;
            return self;
        }
    }

    public static class RectangleBuilder extends ShapeBuilder<RectangleBuilder, ShapeRenderSpec.Rectangle> {

        public RectangleBuilder(float width, float height) {
            super(new ShapeRenderSpec.Rectangle());
            spec.width = width;
            spec.height = height;
        }
    }

    public static class B2DBuilder extends RenderBuilder<B2DBuilder, B2DRenderSpec> {

        public B2DBuilder() {
            super(new B2DRenderSpec());
        }
    }

    public static class AnimatedBuilder extends SpatialBuilder<AnimatedBuilder, AnimatedRenderSpec> {

        public AnimatedBuilder(BackingTexture backingTexture, float width, float height) {
            super(new AnimatedRenderSpec());
            spec.backingTexture=backingTexture;
            spec.width=width;
            spec.height=height;
        }

        public AnimatedBuilder addAnimation(AnimationSequence animation) {
            spec.sequences.add(animation);
            return self;
        }
    }

}
