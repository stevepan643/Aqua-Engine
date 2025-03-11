package program;

import com.steve.aqrender.shader.program.SimpleUniformShaderProgram;
import com.steve.aqrender.shader.program.Uniform;
import com.steve.aqrender.util.Identifier;

public class ShaderProgramTest extends SimpleUniformShaderProgram {
    public ShaderProgramTest(Identifier id) {
        super(OShaders.V, OShaders.F, id);
        addUniform("proj", Uniform.TYPE.FOUR_FLOAT_MATRIX);
    }
}
