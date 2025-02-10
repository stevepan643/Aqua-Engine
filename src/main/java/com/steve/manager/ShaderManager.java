package com.steve.manager;

import com.steve.graphic.GameObject.Model;
import com.steve.graphic.ShaderProgram;
import java.util.HashMap;

public class ShaderManager {
  private static HashMap<String, ShaderProgram> shaders;

  public ShaderManager() {}

  public static void render(Model model) {
    shaders.get(model.getShader()).use();
  }
}
