package com.steve.aqrender.graphic.mesh;

import com.steve.aqrender.registry.Registries;
import com.steve.aqrender.registry.Registry;
import com.steve.aqrender.util.Identifier;
import java.util.function.Function;
import org.jetbrains.annotations.NotNull;

public class Meshes {
  public static Mesh register(Identifier identifier, @NotNull Function<Identifier, Mesh> function) {
    Mesh mesh = function.apply(identifier);
    return Registry.register(Registries.MESH, identifier, mesh);
  }
}
