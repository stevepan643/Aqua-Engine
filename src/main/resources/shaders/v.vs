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
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec3 aNormal;
layout (location = 2) in vec2 aTexCoord;

out vec3 normal;
out vec2 texCoord;
out vec3 fragPos;

uniform mat4 proj;
uniform mat4 view;
uniform mat4 model;

void main()
{
   gl_Position = proj * view * model * vec4(aPos, 1.0);
   fragPos = vec3(model * vec4(aPos, 1.0));
   normal = vec3(model * vec4(aNormal, 1.0));
   // normal = mat3(transpose(inverse(model))) * aNormal;
   texCoord = aTexCoord;
}