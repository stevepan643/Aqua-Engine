package program;

import com.steve.aqrender.shader.program.ShaderProgram;
import com.steve.aqrender.shader.program.ShaderPrograms;
import com.steve.aqrender.util.Identifier;

public class OPrograms {
    public static final ShaderProgram P = ShaderPrograms.register(Identifier.of("program", "test_program"), ShaderProgramTest::new);
}
