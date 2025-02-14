package com.steve.manager;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import com.steve.graphic.GameObject.Model;
import com.steve.graphic.Shader;
import com.steve.graphic.ShaderProgram;
import com.steve.util.LogUtil;
import java.util.HashMap;
import org.slf4j.Logger;

public class ShaderManager {
  private static HashMap<String, ShaderProgram> shaders;

  private static final Logger LOGGER = LogUtil.getLogger();

  public static void init() {
    shaders = new HashMap<>();

    Shader v = new Shader("src/main/resources/default/vertex.vs", GL_VERTEX_SHADER);
    Shader f = new Shader("src/main/resources/default/fragment.fs", GL_FRAGMENT_SHADER);

    ShaderProgram shader = new ShaderProgram();
    shader.addShader("default_vertex_shader", v);
    shader.addShader("default_fragment_shader", f);
    shader.link();

    shaders.put("default", shader);

    System.out.println("1");
  }

  public static void render(Model model) {
    if (!shaders.containsKey(model.getShader())) {
      LOGGER.error("unknown key, Model's shader <{}>", model.getShader());
    }

    shaders.get(model.getShader()).use();
  }
}
