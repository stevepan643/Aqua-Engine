package com.steve.manager;

import static org.lwjgl.opengl.GL20.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20.GL_VERTEX_SHADER;

import com.steve.graphic.GameObject.Model;
import com.steve.graphic.Shader;
import com.steve.graphic.ShaderProgram;
import com.steve.graphic.Uniform;
import com.steve.util.LogUtil;
import java.util.HashMap;
import org.joml.Matrix4f;
import org.slf4j.Logger;

public class ShaderManager {
  private static HashMap<String, ShaderProgram> shaders;

  private static final Logger LOGGER = LogUtil.getLogger();

  private static final Uniform<Matrix4f> proj =
      new Uniform<Matrix4f>("proj", new Matrix4f().identity());
  private static final Uniform<Matrix4f> view =
      new Uniform<Matrix4f>("view", new Matrix4f().identity());
  private static final Uniform<Matrix4f> model =
      new Uniform<Matrix4f>("model", new Matrix4f().identity());

  public static void init() {
    shaders = new HashMap<>();

    Shader v = new Shader("default/vertex.vert", GL_VERTEX_SHADER);
    Shader f = new Shader("default/fragment.frag", GL_FRAGMENT_SHADER);

    ShaderProgram shader = new ShaderProgram();
    shader.addShader("default_vertex_shader", v);
    shader.addShader("default_fragment_shader", f);
    shader.link();

    shaders.put("default", shader);
  }

  public static void render(Model model) {
    if (!shaders.containsKey(model.getShader())) {
      LOGGER.error("unknown key, Model's shader <{}>", model.getShader());
    }

    ShaderManager.model.setValue(model.getTransform());
    ShaderManager.model.update();

    shaders.get(model.getShader()).use();
    shaders.get(model.getShader()).addUniforms(proj, view, ShaderManager.model);
  }

  public static Uniform<Matrix4f> getProj() {
    return proj;
  }

  public static Uniform<Matrix4f> getView() {
    return view;
  }
}
