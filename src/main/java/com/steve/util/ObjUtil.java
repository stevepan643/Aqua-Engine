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

package com.steve.util;

import com.steve.graphic.GameObject;
import com.steve.graphic.Material;
import com.steve.graphic.Mesh;
import java.util.ArrayList;
import java.util.List;
import org.joml.Vector2f;
import org.joml.Vector3f;

public class ObjUtil {

  private static class IdxGroup {

    public static final int NONE = -1;

    public int idxPos;
    public int idxTextCoord;
    public int idxVecNormal;

    public IdxGroup() {
      idxPos = NONE;
      idxTextCoord = NONE;
      idxVecNormal = NONE;
    }
  }

  private static class Face {
    private IdxGroup[] idxGroups;

    public Face(String v1, String v2, String v3) {
      this.idxGroups = new IdxGroup[3];

      this.idxGroups[0] = parseLine(v1);
      this.idxGroups[1] = parseLine(v2);
      this.idxGroups[2] = parseLine(v3);
    }

    private IdxGroup parseLine(String line) {
      IdxGroup idxGroup = new IdxGroup();

      String[] lineTokens = line.split("/");
      int length = lineTokens.length;
      idxGroup.idxPos = Integer.parseInt(lineTokens[0]) - 1;
      if (length > 1) {
        String textCoord = lineTokens[1];
        idxGroup.idxTextCoord =
            textCoord.length() > 0 ? Integer.parseInt(textCoord) - 1 : IdxGroup.NONE;
        if (length > 2) {
          idxGroup.idxVecNormal = Integer.parseInt(lineTokens[2]) - 1;
        }
      }

      return idxGroup;
    }

    public IdxGroup[] getFaceVertexIndices() {
      return idxGroups;
    }
  }

  public static GameObject loadModel(String filepath) {
    List<String> lines = FileUtil.readLine(filepath);

    List<Vector3f> vertices = new ArrayList<>();
    List<Vector2f> textures = new ArrayList<>();
    List<Vector3f> normals = new ArrayList<>();
    List<Face> faces = new ArrayList<>();

    GameObject o =
        new GameObject(filepath.substring(filepath.lastIndexOf("/"), filepath.lastIndexOf(".")));
    String id = "";
    boolean isFirst = false;
    for (String line : lines) {
      String[] tokens = line.split("\\s+");
      switch (tokens[0]) {
        case "v":
          vertices.add(
              new Vector3f(
                  Float.parseFloat(tokens[1]),
                  Float.parseFloat(tokens[2]),
                  Float.parseFloat(tokens[3])));
          break;
        case "vt":
          textures.add(new Vector2f(Float.parseFloat(tokens[1]), Float.parseFloat(tokens[2])));
          break;
        case "vn":
          normals.add(
              new Vector3f(
                  Float.parseFloat(tokens[1]),
                  Float.parseFloat(tokens[2]),
                  Float.parseFloat(tokens[3])));
          break;
        case "f":
          faces.add(new Face(tokens[1], tokens[2], tokens[3]));
          break;
        case "o":
          if (!isFirst) {
            isFirst = !isFirst;
            id = tokens[1];
            break;
          }
          o.new Model(id, generateMesh(vertices, textures, normals, faces), new Material("test"));
          System.out.println("created 1 object " + id);
          id = tokens[1];
          break;
        default:
          break;
      }
    }

    o.new Model(id, generateMesh(vertices, textures, normals, faces), new Material("test"));
    System.out.println("created 1 object " + id);
    return o;
  }

  private static Mesh generateMesh(
      List<Vector3f> posList,
      List<Vector2f> textCoordList,
      List<Vector3f> normalList,
      List<Face> faces) {
    List<Integer> indices = new ArrayList<>();

    float[] posArr = new float[posList.size() * 3];
    int i = 0;
    for (Vector3f pos : posList) {
      posArr[i * 3] = pos.x;
      posArr[i * 3 + 1] = pos.y;
      posArr[i * 3 + 2] = pos.z;
      i++;
    }

    float[] textCoordArr = new float[posList.size() * 2];
    float[] normalArr = new float[posList.size() * 3];

    for (Face face : faces) {
      IdxGroup[] faceVertexIndices = face.getFaceVertexIndices();
      for (IdxGroup indValue : faceVertexIndices) {
        processFaceVertex(indValue, textCoordList, normalList, indices, textCoordArr, normalArr);
      }
    }

    int[] indicesArr = indices.stream().mapToInt(Integer::intValue).toArray();
    float[] vertexArr = new float[posArr.length + textCoordArr.length + normalArr.length];
    for (int j = 0; j < posList.size(); j++) {
      int posIndex = j * 3;
      int normalIndex = j * 3;
      int textCoordIndex = j * 2;
      int vertexIndex = j * 8;

      vertexArr[vertexIndex] = posArr[posIndex];
      vertexArr[vertexIndex + 1] = posArr[posIndex + 1];
      vertexArr[vertexIndex + 2] = posArr[posIndex + 2];

      vertexArr[vertexIndex + 3] = normalArr[normalIndex];
      vertexArr[vertexIndex + 4] = normalArr[normalIndex + 1];
      vertexArr[vertexIndex + 5] = normalArr[normalIndex + 2];

      vertexArr[vertexIndex + 6] = textCoordArr[textCoordIndex];
      vertexArr[vertexIndex + 7] = textCoordArr[textCoordIndex + 1];
    }

    return Mesh.createMeshWithNormalAndText(vertexArr, indicesArr);
  }

  private static void processFaceVertex(
      IdxGroup indices,
      List<Vector2f> textCoordList,
      List<Vector3f> normalList,
      List<Integer> indicesList,
      float[] textCoordArr,
      float[] normalArr) {

    int posIndex = indices.idxPos;
    indicesList.add(posIndex);

    if (indices.idxTextCoord >= 0) {
      Vector2f textCoord = textCoordList.get(indices.idxTextCoord);
      textCoordArr[posIndex * 2] = textCoord.x;
      textCoordArr[posIndex * 2 + 1] = 1 - textCoord.y;
    }
    if (indices.idxVecNormal >= 0) {
      Vector3f vecNormal = normalList.get(indices.idxVecNormal);
      normalArr[posIndex * 3] = vecNormal.x;
      normalArr[posIndex * 3 + 1] = vecNormal.y;
      normalArr[posIndex * 3 + 2] = vecNormal.z;
    }
  }
}
