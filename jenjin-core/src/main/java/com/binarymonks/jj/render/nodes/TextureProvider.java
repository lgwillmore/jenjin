package com.binarymonks.jj.render.nodes;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface TextureProvider {

    TextureRegion getFrame(float rotationD);

    void dispose();

    public static class SimpleTextureProvider implements TextureProvider {

        Texture texture;
        TextureRegion renderFrame;

        public SimpleTextureProvider(Texture texture) {
            this.texture = texture;
            this.renderFrame = new TextureRegion(texture);
        }

        @Override
        public TextureRegion getFrame(float rotationD) {
            return renderFrame;
        }

        @Override
        public void dispose() {
            texture.dispose();
        }
    }
}
