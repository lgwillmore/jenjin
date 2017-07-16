#ifdef GL_ES
#define LOWP lowp
    precision mediump float;
#else
    #define LOWP
#endif

varying vec2 v_texCoords;

//uniform float dx;
//uniform float dy;

uniform sampler2D u_texture0;

void main()
{
 float dy=5;
 float dx=5;
 vec2 coord = vec2(dx*floor(v_texCoords.x/dx),
                   dy*floor(v_texCoords.y/dy));

 gl_FragColor = vec4(1,1,1,1);
// gl_FragColor = texture2D(u_texture0, coord);
}