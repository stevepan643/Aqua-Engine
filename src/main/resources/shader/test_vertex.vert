#version 330 core
layout (location = 0) in vec3 aPos;

uniform mat4 proj;

out vec3 oCol;

void main()
{
    gl_Position = proj * vec4(aPos, 1.0);
    oCol = vec3(0.8, 0.8, 1.0);
}