package com.steve.manager;

import com.steve.graphic.GameObject;
import java.util.HashMap;

public class ObjectManager {
  private static HashMap<String, GameObject> objects;

  public static void init() {
    objects = new HashMap<>();
  }

  public static void addObject(GameObject gameObject) {
    objects.put(gameObject.getId(), gameObject);
  }

  public static void addObject() {}

  public static void render() {
    objects.forEach((id, gameObject) -> gameObject.render());
  }
}
