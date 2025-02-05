#version 330 core
out vec4 FragColor;

in vec3 normal;
in vec2 texCoord;
in vec3 fragPos;

uniform sampler2D texture1;
uniform vec3 viewPos;

void main()
{
    vec3 lightColor = vec3(1.0);
    float specularStrength = 0.5;

    float ambientStrength = 0.1;
    vec3 ambient = ambientStrength * lightColor;
    
    vec3 norm = normalize(normal);
    vec3 lightDir = normalize(vec3(1.0, 1.0, -0.5) - fragPos);

    vec3 viewDir = normalize(viewPos - fragPos);
    vec3 reflectDir = reflect(-lightDir, norm);

    float spec = pow(max(dot(viewDir, reflectDir), 0.0), 64);
    vec3 specular = specularStrength * spec * lightColor;

    float diff = max(dot(norm, lightDir), 0.0);
    vec3 diffuse = diff * lightColor;

    vec3 result = (ambient + diffuse + specular) * vec3(1.0, 0.5, 0.31);
    FragColor = vec4(result, 1.0);
}