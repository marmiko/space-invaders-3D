#version 330

//Zmienne jednorodne
uniform mat4 P;
uniform mat4 V;
uniform mat4 M;

//Atrybuty
layout (location=0) in vec4 vertex; //wspolrzedne wierzcholka w przestrzeni modelu
layout (location=1) in vec2 texCoord0; // wspolrzedne teksturowania
layout (location=2) in vec4 normal; //wektor normalny w wierzcholku

//Zmienne interpolowane
out vec4 norm;
out vec4 viewDir;
out vec2 iTexCoord0;
out vec4 mvPos;
out mat4 viewMatrix;


void main(void) {


    //lightDir = normalize(V * LightPos0 - V * M * vertex); //wektor do światła w przestrzeni oka
    norm = normalize(V * M * normal); //wektor normalny w przestrzeni oka
    viewDir = normalize(vec4(0, 0, 0, 1) - V * M * vertex);

    viewMatrix = V; //macierz widoku
    mvPos = V * M * vertex; //pozycja wierzchołka w przestrzeni oka
    gl_Position = P*V*M*vertex;
    iTexCoord0 = texCoord0;

}
