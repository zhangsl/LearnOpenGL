precision mediump float;
varying vec2 vTextureCoords;
uniform sampler2D uTexture;
varying vec3 vFragPos;
varying vec3 vNormal;
uniform vec3 uLightPos;
uniform vec3 uLightColor;
uniform vec4 uObjectColor;
uniform float uAmbientStrength;
uniform float uDiffuseStrength;
uniform float uSpecularStrength;

void main(){
    //环境光计算
    vec3 ambient = uAmbientStrength * uLightColor;
    vec4 ambientColor = vec4(ambient,1.0);

    //漫反射计算
    vec3 direction=normalize(uLightPos-vFragPos);
    float diffuseDiff = max(dot(vNormal,direction), 0.0);
    vec3 diffuse=uDiffuseStrength * diffuseDiff * uLightColor;
    vec4 diffuseColor = vec4(diffuse,1.0);

    //镜面反射计算
    vec3 lightDirection=normalize(uLightPos-vFragPos);
    vec3 viewDirection=normalize(vec3(3,3,-3)-vFragPos);
    vec3 hafVector=normalize(lightDirection+viewDirection);
    float specularDiff=pow(max(dot(vNormal,hafVector),0.0),32.0);
    vec3 specular=uSpecularStrength*specularDiff*uLightColor;
    vec4 specularColor = vec4(specular,1.0);

    //最终光线计算
    vec4 color = (ambientColor + diffuseColor + specularColor) * uObjectColor;
    gl_FragColor = texture2D(uTexture, vTextureCoords) * color;
}