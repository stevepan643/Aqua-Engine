import com.steve.aqrender.Application;
import com.steve.aqrender.config.WindowConfiguration;
import com.steve.aqrender.graphic.mesh.Mesh;
import com.steve.aqrender.registry.Registries;
import com.steve.aqrender.registry.Registry;
import com.steve.aqrender.shader.Shaders;
import com.steve.aqrender.shader.program.ShaderPrograms;
import com.steve.aqrender.util.Identifier;
import lombok.extern.slf4j.Slf4j;
import org.joml.Matrix4f;
import program.OPrograms;

@Slf4j
public class Main extends Application {
  @Override
  protected void config(WindowConfiguration configuration) {
    configuration.setTitle("Test");
    m = new Matrix4f().perspective((float) Math.toRadians(60.0f), (float) configuration.getWidth() / configuration.getHeight(), 0.1f, 1000.f);
  }

  @Override
  protected void beforeLoop() {
    log.debug("BASIC_VERTEX_SHADER is {}", Shaders.BASIC_VERTEX_SHADER.getResource());
    log.debug("BASIC_FRAGMENT_SHADER is {}", Shaders.BASIC_FRAGMENT_SHADER.getResource());
    log.debug("BASIC_SHADER: {}", Registries.PROGRAM.getID(ShaderPrograms.BASIC_SHADER));
    Mesh mesh1 =
        new Mesh(
            new float[] {0.5f, -0.5f, -1, 1, 0, 0, -0.5f, -0.5f, -1, 0, 1, 0, 0, 0.5f, -1, 0, 0, 1},
            new int[] {0, 1, 2},
            Mesh.VERTEX | Mesh.COLOR);
    Mesh mesh2 =
        new Mesh(
            new float[] {0.5f, 0.5f, -1, -0.5f, 0.5f, -1, 0, -0.5f, -1},
            new int[] {0, 1, 2},
            Mesh.VERTEX);
    Registry.register(Registries.MESH, Identifier.of("mesh", "test1"), mesh1);
    Registry.register(Registries.MESH, Identifier.of("mesh", "test2"), mesh2);
  }

  Matrix4f m;

  @Override
  protected void process() {
    ShaderPrograms.BASIC_SHADER.use();
    ShaderPrograms.BASIC_SHADER.setUniform(
        "", Registries.MESH.get(Identifier.of("mesh", "test1")).getType());
    Registries.MESH.get(Identifier.of("mesh", "test1")).render();

    OPrograms.P.use();
    OPrograms.P.setUniform("proj", m);
    Registries.MESH.get(Identifier.of("mesh", "test2")).render();
  }

  @Override
  protected void onResize(int width, int height) {
    m = new Matrix4f().perspective((float) Math.toRadians(60.0f), (float) width / height, 0.1f, 1000.f);
  }

  double timer;

  @Override
  protected void afterProcess() {
    timer += getDeltaTime() / 1000_000_000.0;
    if (timer >= 5) {
      log.debug("fps: {}", getFps());
      timer = 0;
    }
  }

  public static void main(String[] args) {
    launch(new Main());
  }
}
