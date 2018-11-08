attribute vec3 vPosition;
attribute vec2 vCoord;
uniform mat4 vMatrix;
uniform vec3 vKa;
uniform vec3 vKd;
uniform vec3 vKs;

varying vec2 textureCoordinate;

attribute vec3 vNormal;         //法向量
varying vec4 vDiffuse;          //用于传递给片元着色器的散射光最终强度
varying vec4 vAmbient;          //用于传递给片元着色器的环境光最终强度
varying vec4 vSpecular;          //用于传递给片元着色器的镜面光最终强度

void main(){
    gl_Position = vMatrix*vec4(vPosition,1);
    textureCoordinate = vCoord;

    vec3 lightLocation=vec3(0.0,-200.0,-500.0);      //光照位置
    vec3 camera=vec3(0,200.0,0);
    float shininess=10.0;             //粗糙度，越小越光滑

     vec3 newNormal=normalize((vMatrix*vec4(vNormal+vPosition,1)).xyz-(vMatrix*vec4(vPosition,1)).xyz);
     vec3 vp=normalize(lightLocation-(vMatrix*vec4(vPosition,1)).xyz);
     vDiffuse=vec4(vKd,1.0)*max(0.0,dot(newNormal,vp));                //计算散射光的最终强度

     vec3 eye= normalize(camera-(vMatrix*vec4(vPosition,1)).xyz);
     vec3 halfVector=normalize(vp+eye);    //求视线与光线的半向量
     float nDotViewHalfVector=dot(newNormal,halfVector);   //法线与半向量的点积
     float powerFactor=max(0.0,pow(nDotViewHalfVector,shininess));     //镜面反射光强度因子
     vSpecular=vec4(vKs,1.0)*powerFactor;               //计算镜面光的最终强度

     vAmbient=vec4(vKa,1.0);
}