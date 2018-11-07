precision mediump float;
varying vec2 vTextureCoords;
uniform sampler2D uTexture;
varying vec3 vFragPos;
varying vec3 vNormal;
uniform vec3 uLightPos;
uniform float uNs;
uniform vec3 uKa;
uniform vec3 uKs;
uniform vec3 uKd;

void main(){
    //环境光计算
    vec4 ambientColor = vec4(uKa,1.0);

    //漫反射计算
    vec3 direction=normalize(uLightPos-vFragPos);
    float diffuseDiff = max(dot(vNormal,direction), 0.0);
    vec3 diffuse=diffuseDiff * uKd;
    vec4 diffuseColor = vec4(diffuse,1.0);

    //镜面反射计算
    vec3 lightDirection=normalize(uLightPos-vFragPos);
    vec3 viewDirection=normalize(vec3(3,3,-3)-vFragPos);
    vec3 hafVector=normalize(lightDirection+viewDirection);
    float specularDiff=pow(max(dot(vNormal,hafVector),0.0),uNs);
    vec3 specular=specularDiff*uKs;
    vec4 specularColor = vec4(specular,1.0);

    //最终光线计算
    vec4 color = ambientColor + diffuseColor + specularColor;
    gl_FragColor = texture2D(uTexture, vTextureCoords) * color;
}