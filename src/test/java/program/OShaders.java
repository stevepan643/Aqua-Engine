package program;

import com.steve.aqrender.shader.FragmentShader;
import com.steve.aqrender.shader.Shader;
import com.steve.aqrender.shader.Shaders;
import com.steve.aqrender.shader.VertexShader;
import com.steve.aqrender.util.Identifier;

public class OShaders {
    public static final Shader V = Shaders.register(Identifier.of("shader", "test_vertex"), VertexShader::new);
    public static final Shader F = Shaders.register(Identifier.of("shader", "test_fragment"), FragmentShader::new);
}
