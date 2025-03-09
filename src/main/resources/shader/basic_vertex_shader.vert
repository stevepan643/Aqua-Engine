#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aNom;
layout (location = 2) in vec3 aCol;
layout (location = 3) in vec2 aTex;

uniform bool hasVertex;
uniform bool hasNormal;
uniform bool hasText;
uniform bool hasColor;

out vec3 oNom;
out vec3 oCol;

void main()
{
    if (hasVertex)
    gl_Position = vec4(aPos, 1.0);
    else
    gl_Position = vec4(0, 0, 0, 1.0);

    if (hasNormal)
    oNom = aNom;
    else
    oNom = vec3(0, 0, 1);

    if (hasColor)
    oCol = aCol;
    else
    oCol = vec3(0.8, 0.8, 1.0);

    //TODO

}