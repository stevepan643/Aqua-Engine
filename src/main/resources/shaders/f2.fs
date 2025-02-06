#version 330 core
out vec4 FragColor;

in vec3 normal;
in vec2 texCoord;
in vec3 fragPos;

uniform vec3 viewPos;

struct Material {
    sampler2D   diffuse;
    sampler2D   specular;
    sampler2D   emission;
    float       shininess;
};

struct Light {
    vec3        position;

    vec3        ambient;
    vec3        diffuse;
    vec3        specular;
};

uniform Material    material;
uniform Light       light;
uniform float       move_time;

void main()
{
    vec3 norm = normalize(normal);
    vec3 lightDir = normalize(light.position - fragPos);
    float diff = max(dot(norm, lightDir), 0.0);

    vec3 viewDir = normalize(viewPos - fragPos);
    vec3 reflectDir = reflect(-lightDir, norm);
    float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);

    vec3 ambient = light.ambient * texture(material.diffuse, texCoord).rgb;
    vec3 diffuse = light.diffuse * diff * texture(material.diffuse, texCoord).rgb;
    vec3 specular = light.specular * spec * texture(material.specular, texCoord).rgb;
    vec3 emission = texture(material.emission,vec2(texCoord.x,texCoord.y+move_time)).rgb;

    FragColor = vec4(ambient + diffuse + specular + emission, 1.0);    
    // FragColor = texture(material.emission, texCoord);

}