#version 330 core
out vec4 FragColor;

in vec3 normal;
in vec2 texCoord;
in vec3 fragPos;

void main()
{
    float ambientStrength = 1.0;
    vec3 ambient = ambientStrength * vec3(1.0);

    vec3 result = ambient * vec3(1.0);
    // FragColor = vec4(result, 1.0);
    FragColor = vec4(vec3(gl_FragCoord.z), 1.0);

}