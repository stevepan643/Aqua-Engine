/*
 * Copyright 2024 Steve Pan
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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

float near = 0.1; 
float far  = 100.0; 

float LinearizeDepth(float depth) 
{
    float z = depth * 2.0 - 1.0; // 转换为 NDC
    return (2.0 * near * far) / (far + near - z * (far - near));    
}

void main()
{
    vec3 norm = normalize(normal);
    vec3 lightDir = normalize(light.position - fragPos);
    float diff = max(dot(norm, lightDir), 0.0);

    // vec3 viewDir = normalize(viewPos - fragPos);
    // vec3 reflectDir = reflect(-lightDir, norm);
    // float spec = pow(max(dot(viewDir, reflectDir), 0.0), material.shininess);

    vec3 objectColor = vec3(1.0, 1.0, 1.0);
    // vec3 objectColor = texture(material.diffuse, texCoord).rgb
    // vec3 specularText = texture(material.specular, texCoord).rgb

    vec3 ambient = light.ambient * objectColor;
    vec3 diffuse = light.diffuse * diff * objectColor;
    // vec3 specular = light.specular * spec * texture(material.specular, texCoord).rgb;
    // vec3 emission = texture(material.emission, vec2(texCoord.x, texCoord.y + move_time)).rgb;

    FragColor = vec4(ambient + diffuse, 1.0);    
    // FragColor = texture(material.emission, texCoord);
    // FragColor = vec4(vec3(gl_FragCoord.z), 1.0);

    // float depth = LinearizeDepth(gl_FragCoord.z) / far; // 为了演示除以 far
    // FragColor = vec4((vec3(0.5) - vec3(depth)), 1.0);

}