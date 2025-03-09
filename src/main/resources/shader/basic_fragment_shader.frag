#version 330 core

in vec3 oNom;
in vec3 oCol;

out vec4 FragColor;

void main()
{

    FragColor = vec4(oCol, 1.0);

}