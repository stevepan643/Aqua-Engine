package com.steve.util;

import com.steve.graphic.GameObject;
import com.steve.graphic.Material;
import java.util.HashMap;
import java.util.List;
import org.joml.Vector3f;

public class ObjUtilNew {
  public static GameObject loadModel(String filepath) {
    GameObject o =
        new GameObject(filepath.substring(filepath.lastIndexOf("/"), filepath.lastIndexOf(".")));
    String fileParent = filepath.substring(0, filepath.lastIndexOf("/") + 1);
    HashMap<String, Material> materials = new HashMap<>();

    List<String> lines = FileUtil.readLine(filepath);

    String current = "";
    String currentMaterial = "";

    boolean isFirst = false;
    for (String line : lines) {
      String[] tokens = line.split("\\s+");

      switch (tokens[0]) {
        case "mtllib":
          loadMaterial(fileParent + tokens[1], materials);
          break;
        case "usemtl":
          System.out.println(tokens[1]);
          currentMaterial = tokens[1];
          break;
        case "o":
          if (!isFirst) {
            isFirst = !isFirst;
            current = tokens[1];
            break;
          }
          o.new Model(current, null, materials.get(currentMaterial));
          current = tokens[1];
          break;
        case "v":
        default:
          break;
      }
      o.new Model(current, null, materials.get(currentMaterial));
    }

    return o;
  }

  private static void loadMaterial(String filepath, HashMap<String, Material> map) {
    List<String> material = FileUtil.readLine(filepath);
    String current = "";
    for (String line : material) {
      String[] tokens = line.split("\\s+");
      switch (tokens[0]) {
        case "newmtl":
          current = tokens[1];
          map.put(current, new Material(current));
          break;
        case "Ns":
          map.get(current).setNs(Float.parseFloat(tokens[1]));
          break;
        case "Ka":
          map.get(current)
              .setKa(
                  new Vector3f(
                      Float.parseFloat(tokens[1]),
                      Float.parseFloat(tokens[2]),
                      Float.parseFloat(tokens[3])));
          break;
        case "Kd":
          map.get(current)
              .setKd(
                  new Vector3f(
                      Float.parseFloat(tokens[1]),
                      Float.parseFloat(tokens[2]),
                      Float.parseFloat(tokens[3])));
          break;
        case "Ks":
          map.get(current)
              .setKs(
                  new Vector3f(
                      Float.parseFloat(tokens[1]),
                      Float.parseFloat(tokens[2]),
                      Float.parseFloat(tokens[3])));
          break;
        case "Ke":
          map.get(current)
              .setKe(
                  new Vector3f(
                      Float.parseFloat(tokens[1]),
                      Float.parseFloat(tokens[2]),
                      Float.parseFloat(tokens[3])));
          break;
        case "Ni":
          map.get(current).setNi(Float.parseFloat(tokens[1]));
          break;
        case "d":
          map.get(current).setD(Float.parseFloat(tokens[1]));
          break;
        case "illum":
          map.get(current).setIllum(Integer.parseInt(tokens[1]));
          break;
        default:
          break;
      }
    }
  }
}
