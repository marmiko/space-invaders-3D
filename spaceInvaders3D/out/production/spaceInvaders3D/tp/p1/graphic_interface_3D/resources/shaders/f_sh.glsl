#version 330
const int MAX_POINT_LIGHTS = 4;

struct PointLight
{
    vec3 color;
    vec3 position;
    float strength;
};

struct SpotLight
{
    PointLight pl;
    float cutOff;
    vec3 coneDir;
};

uniform sampler2D textureMap0;
uniform sampler2D textureMap1;
uniform PointLight[MAX_POINT_LIGHTS] pointLights;
uniform SpotLight spotLight;
uniform float ambientStrength;
uniform int hasSpecularMap;
uniform int ufoState;

//Zmienna wyjsciowa fragment shadera. Zapisuje sie do niej ostateczny (prawie) kolor piksela
out vec4 pixelColor;

//Zmiene wejściowe (interpolowane)
in vec2 iTexCoord0; //koordynaty teksturowania
in vec4 norm; //wektor normalny (przestrzeń oka)
in vec4 viewDir;
in vec4 mvPos;
in mat4 viewMatrix;


vec4 calculatePointLight(PointLight pl, vec4 objCol, vec4 reflectCol){
    vec4 nNorm = normalize(norm);
    vec4 nViewDir = normalize(viewDir);
    vec4 lightDir = viewMatrix * vec4(pl.position,1) - mvPos;
    vec4 nLightDir = normalize(lightDir);
    vec4 nReflectDir = reflect(-nLightDir, nNorm);

    float nl = clamp(dot(nNorm, nLightDir),0,1);
    float rv = pow(clamp(dot(nViewDir, nReflectDir), 0, 1), 32);

    vec3 lightColor = pl.color * pl.strength;


    vec3 diffuse = lightColor * nl;
    vec3 specular = lightColor * rv;


    return (vec4(objCol.rgb*diffuse, 0)+vec4(reflectCol.rgb*specular, 0));


}

vec4 calculateSpotLight(SpotLight spl, vec4 objCol, vec4 reflectCol)
{
    vec4 lightDir = viewMatrix * vec4(spl.pl.position,1) - mvPos;
    vec4 nLightDir = normalize(lightDir);
    float alfa = dot(-nLightDir, vec4(normalize(-spl.coneDir),0));

    if(alfa > spl.cutOff){

        float coeff = (1.0 - (1.0 - alfa)/(1.0 - spl.cutOff));

        return calculatePointLight(spl.pl, objCol, reflectCol) * coeff;
    }
    return vec4(0,0,0,0);
}




void main(void){

    //vec4 kd = texture(textureMap0, iTexCoord0);
    int i;

    vec4 objectColor = texture(textureMap0, iTexCoord0);
    vec4 col = vec4(0,0,0,0);
    vec3 ambient = ambientStrength * vec3(1,1,1);
    vec4 reflectColor;

    if(hasSpecularMap == 1){
       reflectColor = texture(textureMap1, iTexCoord0);
    }else{
       reflectColor = objectColor;
    }


    col += vec4(ambient * objectColor.rgb,0);

    for(i=0;i<MAX_POINT_LIGHTS;i++){
        col += calculatePointLight(pointLights[i], objectColor, reflectColor);
    }


    if(ufoState==1){
        col += calculateSpotLight(spotLight, objectColor, reflectColor);
    }

    pixelColor = vec4(col.rgb, objectColor.a);


    }


