uniform mat4 uMVPMatrix;
attribute vec4 aPosition;
attribute vec2 aTextureCoords;
attribute vec3 aNormal;
varying vec2 vTextureCoords;
varying vec3 vFragPos;
varying vec3 vNormal;

void main(){
    vTextureCoords = aTextureCoords;
    gl_Position = uMVPMatrix * aPosition;
    vFragPos=(uMVPMatrix*aPosition).xyz;
    vNormal=normalize(mat3(uMVPMatrix)*aNormal);
}