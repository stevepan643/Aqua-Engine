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

void main()
{
    float ambientStrength = 1.0;
    vec3 ambient = ambientStrength * vec3(1.0);

    vec3 result = ambient * vec3(1.0);
    // FragColor = vec4(result, 1.0);
    FragColor = vec4(vec3(gl_FragCoord.z), 1.0);

}