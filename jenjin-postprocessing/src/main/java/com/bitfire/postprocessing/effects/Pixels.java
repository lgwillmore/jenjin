package com.bitfire.postprocessing.effects;

import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.bitfire.postprocessing.PostProcessorEffect;
import com.bitfire.postprocessing.filters.PixelsFilter;

public class Pixels extends PostProcessorEffect {

    private PixelsFilter pixelsFilter;

    public Pixels(float pixelWidth, float pixelHeight){
        pixelsFilter = new PixelsFilter(pixelWidth,pixelHeight);
    }


    @Override
    public void rebind() {
        pixelsFilter.rebind();
    }

    @Override
    public void render(FrameBuffer src, FrameBuffer dest) {
        pixelsFilter.setInput(src).setOutput(dest).render();
    }

    @Override
    public void dispose() {
        pixelsFilter.dispose();
    }
}
