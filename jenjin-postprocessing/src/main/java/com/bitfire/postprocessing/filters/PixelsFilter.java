package com.bitfire.postprocessing.filters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;

public class PixelsFilter extends Filter<PixelsFilter> {

    static String vertex = "#ifdef GL_ES\n" +
            "\t#define PRECISION mediump\n" +
            "\tprecision PRECISION float;\n" +
            "#else\n" +
            "\t#define PRECISION\n" +
            "#endif\n" +
            "\n" +
            "attribute vec4 a_position;\n" +
            "attribute vec2 a_texCoord0;\n" +
            "varying vec2 v_texCoords;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            "\tv_texCoords = a_texCoord0;\n" +
            "\tgl_Position = a_position;\n" +
            "}";

    static String fragment = "#ifdef GL_ES\n" +
            "#define LOWP lowp\n" +
            "    precision mediump float;\n" +
            "#else\n" +
            "    #define LOWP\n" +
            "#endif\n" +
            "\n" +
            "varying vec2 v_texCoords;\n" +
            "\n" +
            "uniform float pixel_width;\n" +
            "uniform float pixel_height;" +
            "\n" +
            "uniform sampler2D u_texture0;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            " float dy=pixel_width;\n" +
            " float dx=pixel_width;\n" +
            " vec2 coord = vec2(dx*floor(v_texCoords.x/dx),\n" +
            "                   dy*floor(v_texCoords.y/dy));\n" +
            "\n" +
            " //float value = v_texCoords.y;\n" +
            " //gl_FragColor = vec4(value,value,value,1);\n" +
            "  gl_FragColor = vec4(texture2D(u_texture0, coord).xyz,1);\n" +
            "}";

    float pixelWidth;
    float pixelHeight;

    public enum Param implements Parameter {
        // @formatter:off
		Texture0("u_texture0", 0), PixelWidth("pixel_width",0), PixelHeight("pixel_height",0);
		// @formatter:on

        private final String mnemonic;
        private int elementSize;

        private Param(String m, int elementSize) {
            this.mnemonic = m;
            this.elementSize = elementSize;
        }

        @Override
        public String mnemonic() {
            return this.mnemonic;
        }

        @Override
        public int arrayElementSize() {
            return this.elementSize;
        }
    }

    static ShaderProgram shader() {
        ShaderProgram shader = new ShaderProgram(vertex, fragment);

        if (!shader.isCompiled()) {
            Gdx.app.error("ShaderLoader", shader.getLog());
            System.exit(-1);
        }
        return shader;
    }

    public PixelsFilter(float pixelWidth, float pixelHeight) {
        super(shader());
        this.pixelWidth = pixelWidth;
        this.pixelHeight = pixelHeight;
        rebind();
    }

    @Override
    public void rebind() {
        setParams(Param.Texture0, u_texture0);
        setParams(Param.PixelWidth, pixelWidth);
        setParams(Param.PixelHeight, pixelHeight);
        endParams();
    }

    @Override
    protected void onBeforeRender() {
        inputTexture.bind(u_texture0);
    }


}
