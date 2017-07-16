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
            "//uniform float dx;\n" +
            "//uniform float dy;\n" +
            "\n" +
            "uniform sampler2D u_texture0;\n" +
            "\n" +
            "void main()\n" +
            "{\n" +
            " float dy=0.01;\n" +
            " float dx=0.01;\n" +
            " vec2 coord = vec2(dx*floor(v_texCoords.x/dx),\n" +
            "                   dy*floor(v_texCoords.y/dy));\n" +
            "\n" +
            " //float value = v_texCoords.y;\n" +
            " //gl_FragColor = vec4(value,value,value,1);\n" +
            "  gl_FragColor = vec4(texture2D(u_texture0, coord).xyz,1);\n" +
            "}";

    static ShaderProgram shader() {
        ShaderProgram shader = new ShaderProgram(vertex, fragment);

        if (!shader.isCompiled()) {
            Gdx.app.error("ShaderLoader", shader.getLog());
            System.exit(-1);
        }
        return shader;
    }

    public PixelsFilter(float PixelWidth, float PixelHeight) {
        super(shader());
        rebind();
    }

    @Override
    public void rebind() {
        setParams(Vignetting.Param.Texture0, u_texture0);
        endParams();
    }

    @Override
    protected void onBeforeRender() {
        inputTexture.bind(u_texture0);
    }


}
