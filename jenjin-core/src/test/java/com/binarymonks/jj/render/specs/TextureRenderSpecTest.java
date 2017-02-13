package com.binarymonks.jj.render.specs;

import com.binarymonks.jj.JsonTest;
import org.junit.Test;


public class TextureRenderSpecTest {

    @Test
    public void testJson() {
        TextureRenderSpec textureRenderSpec = new TextureRenderSpec(
                new BackingTexture.Simple("arbitrarypath"), 10, 10)
                .setLayer(2)
                .setPriority(1)
                .setSpatial(new Spatial.Fixed(3, 4, 5));

        JsonTest.jsonRoundTrip(textureRenderSpec);
    }

}