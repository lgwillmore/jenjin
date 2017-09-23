/*******************************************************************************
 * Copyright 2012 bmanuel
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.bitfire.postprocessing.filters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.bitfire.utils.ShaderLoader;

public final class Vignetting extends Filter<Vignetting> {

	private float x, y;
	private float intensity, saturation, saturationMul;

	private Texture texLut;
	private boolean dolut, dosat;
	private float lutintensity;
	private int[] lutindex;
	private float lutStep, lutStepOffset, lutIndexOffset;
	private float centerX, centerY;

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
			"\t#define PRECISION mediump\n" +
			"\tprecision PRECISION float;\n" +
			"#else\n" +
			"\t#define PRECISION\n" +
			"#endif\n" +
			"\n" +
			"uniform PRECISION sampler2D u_texture0;\n" +
			"uniform float VignetteIntensity;\n" +
			"uniform float VignetteX;\n" +
			"uniform float VignetteY;\n" +
			"uniform float CenterX;\n" +
			"uniform float CenterY;\n" +
			"\n" +
			"varying vec2 v_texCoords;\n" +
			"\n" +
			"\n" +
			"#ifdef CONTROL_SATURATION\n" +
			"\tuniform float Saturation;\n" +
			"\tuniform float SaturationMul;\n" +
			"\tconst vec3 GRAYSCALE = vec3(0.3, 0.59, 0.11);\n" +
			"\n" +
			"\t// 0 = totally desaturated\n" +
			"\t// 1 = saturation unchanged\n" +
			"\t// higher = increase saturation\n" +
			"\t//const float BaseSat = 1;\n" +
			"\t//const float BloomSat = 1;\n" +
			"\n" +
			"\tvec3 adjustSaturation(vec3 color, float saturation) {\n" +
			"\t\tvec3 grey = vec3(dot(color, GRAYSCALE));\n" +
			"\t\t//vec3 grey = vec3((color.r+color.g+color.b)*0.333);\t// simple\n" +
			"\t\treturn mix(grey, color, saturation);\t// correct\n" +
			"\t}\n" +
			"#endif\n" +
			"\n" +
			"\n" +
			"\n" +
			"#ifdef ENABLE_GRADIENT_MAPPING\n" +
			"\tuniform PRECISION sampler2D u_texture1;\n" +
			"\tuniform float LutIntensity;\n" +
			"\n" +
			"\tuniform int LutIndex;\n" +
			"\tuniform int LutIndex2;\n" +
			"\tuniform float LutIndexOffset;\n" +
			"\n" +
			"\tuniform float LutStep;\n" +
			"\tuniform float LutStepOffset;\n" +
			"\n" +
			"\tvec3 do_lookup( vec3 color ) {\n" +
			"\t\tvec3 curveColorA;\n" +
			"\t\tvec3 curveColorB;\n" +
			"\n" +
			"\t\tfloat idxA = float(LutIndex) * LutStep + LutStepOffset;\n" +
			"\t\tfloat idxB = float(LutIndex2) * LutStep + LutStepOffset;\n" +
			"\n" +
			"\t\tcurveColorA.r = texture2D( u_texture1, vec2(color.r, idxA ) ).r;\n" +
			"\t\tcurveColorA.g = texture2D( u_texture1, vec2(color.g, idxA ) ).g;\n" +
			"\t\tcurveColorA.b = texture2D( u_texture1, vec2(color.b, idxA ) ).b;\n" +
			"\n" +
			"\t\tcurveColorB.r = texture2D( u_texture1, vec2(color.r, idxB ) ).r;\n" +
			"\t\tcurveColorB.g = texture2D( u_texture1, vec2(color.g, idxB ) ).g;\n" +
			"\t\tcurveColorB.b = texture2D( u_texture1, vec2(color.b, idxB ) ).b;\n" +
			"\n" +
			"\t\treturn mix(color,mix(curveColorA,curveColorB,LutIndexOffset),LutIntensity);\n" +
			"\t}\n" +
			"#endif\n" +
			"\n" +
			"void main() {\n" +
			"\tvec3 rgb = texture2D(u_texture0, v_texCoords).xyz;\n" +
			"\tfloat d = distance(v_texCoords, vec2(CenterX, CenterY));\n" +
			"\tfloat factor = smoothstep(VignetteX, VignetteY, d);\n" +
			"\trgb = rgb*factor + rgb*(1.0-factor) * (1.0-VignetteIntensity);\n" +
			"\n" +
			"#ifdef CONTROL_SATURATION\n" +
			"\trgb = adjustSaturation(rgb,Saturation) * SaturationMul;\n" +
			"#endif\n" +
			"\n" +
			"\n" +
			"#ifdef ENABLE_GRADIENT_MAPPING\n" +
			"\t// theoretically, this conditional though still a branch instruction\n" +
			"\t// should be able to shave some cycles instead of blindly performing\n" +
			"\t// 3 lookups+mix with a 0 intensity... still, i don't like this\n" +
			"\tif( LutIndex > -1 ) {\n" +
			"\t\trgb = do_lookup(rgb);\n" +
			"\t}\n" +
			"#endif\n" +
			"\n" +
			"\tgl_FragColor = vec4(rgb,1);\n" +
			"}";

	public enum Param implements Parameter {
		// @formatter:off
		Texture0("u_texture0", 0), TexLUT("u_texture1", 0), VignetteIntensity("VignetteIntensity", 0), VignetteX("VignetteX", 0), VignetteY(
			"VignetteY", 0), Saturation("Saturation", 0), SaturationMul("SaturationMul", 0), LutIntensity("LutIntensity", 0), LutIndex(
			"LutIndex", 0), LutIndex2("LutIndex2", 0), LutIndexOffset("LutIndexOffset", 0), LutStep("LutStep", 0), LutStepOffset(
			"LutStepOffset", 0), CenterX("CenterX", 0), CenterY("CenterY", 0);
		// @formatter:on

		private final String mnemonic;
		private int elementSize;

		private Param (String m, int elementSize) {
			this.mnemonic = m;
			this.elementSize = elementSize;
		}

		@Override
		public String mnemonic () {
			return this.mnemonic;
		}

		@Override
		public int arrayElementSize () {
			return this.elementSize;
		}
	}

	static ShaderProgram shader(boolean controlSaturation){
		String defines = controlSaturation ? "#define CONTROL_SATURATION\n#define ENABLE_GRADIENT_MAPPING" : "#define ENABLE_GRADIENT_MAPPING";
		ShaderProgram shader = new ShaderProgram( defines + "\n" + vertex, defines + "\n" + fragment );

		if( !shader.isCompiled() ) {
			Gdx.app.error( "ShaderLoader", shader.getLog() );
			System.exit( -1 );
		}
		return shader;
	}

	public Vignetting (boolean controlSaturation) {
		super(shader(controlSaturation));
		dolut = false;
		dosat = controlSaturation;

		texLut = null;
		lutindex = new int[2];
		lutindex[0] = -1;
		lutindex[1] = -1;

		lutintensity = 1f;
		lutIndexOffset = 0;
		rebind();
		setCoords(0.8f, 0.25f);
		setCenter(0.5f, 0.5f);
		setIntensity(1f);
	}

	public void setIntensity (float intensity) {
		this.intensity = intensity;
		setParam(Param.VignetteIntensity, intensity);
	}

	public void setSaturation (float saturation) {
		this.saturation = saturation;
		if (dosat) {
			setParam(Param.Saturation, saturation);
		}
	}

	public void setSaturationMul (float saturationMul) {
		this.saturationMul = saturationMul;
		if (dosat) {
			setParam(Param.SaturationMul, saturationMul);
		}
	}

	public void setCoords (float x, float y) {
		this.x = x;
		this.y = y;
		setParams(Param.VignetteX, x);
		setParams(Param.VignetteY, y);
		endParams();
	}

	public void setX (float x) {
		this.x = x;
		setParam(Param.VignetteX, x);
	}

	public void setY (float y) {
		this.y = y;
		setParam(Param.VignetteY, y);
	}

	/** Sets the texture with which gradient mapping will be performed. */
	public void setLut (Texture texture) {
		texLut = texture;
		dolut = (texLut != null);

		if (dolut) {
			lutStep = 1f / (float)texture.getHeight();
			lutStepOffset = lutStep / 2f; // center texel
			setParams(Param.TexLUT, u_texture1);
			setParams(Param.LutStep, lutStep);
			setParams(Param.LutStepOffset, lutStepOffset).endParams();
		}
	}

	public void setLutIntensity (float value) {
		lutintensity = value;
		setParam(Param.LutIntensity, lutintensity);
	}

	public void setLutIndexVal (int index, int value) {
		lutindex[index] = value;

		switch (index) {
		case 0:
			setParam(Param.LutIndex, lutindex[0]);
			break;
		case 1:
			setParam(Param.LutIndex2, lutindex[1]);
			break;
		}

	}

	public void setLutIndexOffset (float value) {
		lutIndexOffset = value;
		setParam(Param.LutIndexOffset, lutIndexOffset);
	}

	/** Specify the center, in normalized screen coordinates. */
	public void setCenter (float x, float y) {
		this.centerX = x;
		this.centerY = y;
		setParams(Param.CenterX, centerX);
		setParams(Param.CenterY, centerY).endParams();
	}

	public float getCenterX () {
		return centerX;
	}

	public float getCenterY () {
		return centerY;
	}

	public int getLutIndexVal (int index) {
		return (int)lutindex[index];
	}

	public float getLutIntensity () {
		return lutintensity;
	}

	public Texture getLut () {
		return texLut;
	}

	public float getX () {
		return x;
	}

	public float getY () {
		return y;
	}

	public float getIntensity () {
		return intensity;
	}

	public float getSaturation () {
		return saturation;
	}

	public float getSaturationMul () {
		return saturationMul;
	}

	public boolean isGradientMappingEnabled () {
		return dolut;
	}

	@Override
	public void rebind () {
		setParams(Param.Texture0, u_texture0);

		setParams(Param.LutIndex, lutindex[0]);
		setParams(Param.LutIndex2, lutindex[1]);
		setParams(Param.LutIndexOffset, lutIndexOffset);

		setParams(Param.TexLUT, u_texture1);
		setParams(Param.LutIntensity, lutintensity);
		setParams(Param.LutStep, lutStep);
		setParams(Param.LutStepOffset, lutStepOffset);

		if (dosat) {
			setParams(Param.Saturation, saturation);
			setParams(Param.SaturationMul, saturationMul);
		}

		setParams(Param.VignetteIntensity, intensity);
		setParams(Param.VignetteX, x);
		setParams(Param.VignetteY, y);
		setParams(Param.CenterX, centerX);
		setParams(Param.CenterY, centerY);
		endParams();
	}

	@Override
	protected void onBeforeRender () {
		inputTexture.bind(u_texture0);
		if (dolut) {
			texLut.bind(u_texture1);
		}
	}
}
