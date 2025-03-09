import com.steve.aqrender.Application;
import com.steve.aqrender.config.WindowConfiguration;
import com.steve.aqrender.graphic.mesh.Mesh;
import com.steve.aqrender.registry.Registries;
import com.steve.aqrender.registry.Registry;
import com.steve.aqrender.shader.Shaders;
import com.steve.aqrender.shader.program.ShaderPrograms;
import com.steve.aqrender.util.Identifier;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main extends Application {
  @Override
  protected void config(WindowConfiguration configuration) {
    configuration.setTitle("Test");
    configuration.setBgColor(new BackgroundColor(63, 63, 63));
  }

  @Override
  protected void beforeLoop() {
    log.debug("BASIC_VERTEX_SHADER is {}", Shaders.BASIC_VERTEX_SHADER.getResource());
    log.debug("BASIC_FRAGMENT_SHADER is {}", Shaders.BASIC_FRAGMENT_SHADER.getResource());
    log.debug("BASIC_SHADER: {}", Registries.PROGRAM.getID(ShaderPrograms.BASIC_SHADER));
    Mesh mesh =
        new Mesh(
            new float[] {0.5f, -0.5f, -1, -0.5f, -0.5f, -1, 0, 0.5f, -1},
            new int[] {0, 1, 2},
            Mesh.VERTEX);
    Registry.register(Registries.MESH, Identifier.of("mesh", "test"), mesh);
  }

  @Override
  protected void process() {
    Registries.PROGRAM.get(Identifier.of("program", "basic_shader")).use();
    Registries.MESH.get(Identifier.of("mesh", "test")).render();
  }

  public static void main(String[] args) {
    launch(new Main());
  }
}
