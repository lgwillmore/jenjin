package com.binarymonks.jj.render.specs;

import com.binarymonks.jj.JsonTest;
import com.binarymonks.jj.specs.render.BackingTexture;
import com.binarymonks.jj.specs.render.RenderBuilder;
import com.binarymonks.jj.specs.render.Spatial;
import com.binarymonks.jj.specs.render.TextureRenderSpec;
import org.junit.Test;


public class TextureRenderSpecTest {

    @Test
    public void testJson() {
        TextureRenderSpec textureRenderSpec = RenderBuilder.texture(
                new BackingTexture.Simple("arbitrarypath"), 10, 10)
                .setLayer(2)
                .setPriority(1)
                .setSpatial(new Spatial.Fixed(3, 4, 5))
                .build();

        JsonTest.jsonRoundTrip(textureRenderSpec);
    }

}