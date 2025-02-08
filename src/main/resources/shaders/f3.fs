#version 330 core
out vec4 FragColor;

in vec3 normal;
in vec2 texCoord;
in vec3 fragPos;

void main()
{
    FragColor = vec4(1.0, 1.0, 1.0, 1.0);
}