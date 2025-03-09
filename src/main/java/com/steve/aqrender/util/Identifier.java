package com.steve.aqrender.util;

import lombok.Getter;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

/**
 * 存储id和命名空间，便于资源查找。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @since March 8, 2025
 */
public class Identifier implements Comparable<Identifier> {
  private static final String SEPARATOR = ":";

  @Getter private final String namespace;
  @Getter private final String id;

  // 创建Identifier。
  private Identifier(@NotNull final String namespace, @NotNull final String id) {

    this.namespace = namespace;
    this.id = id;
  }

  /**
   * 创建Identifier。
   *
   * @param namespace 命名空间
   * @param id id
   * @return namespace:id
   * @since March 8, 2025
   */
  @NotNull
  @Contract(value = "_, _ -> new", pure = true)
  public static Identifier of(@NotNull final String namespace, @NotNull final String id) {
    if (!isNamespaceValid(namespace) || !isIdValid(id)) {
      throw new IllegalArgumentException("Invalid identifier '" + id + "'");
    }
    return new Identifier(namespace, id);
  }

  private static boolean isNamespaceValid(@NotNull String namespace) {
    for (char c : namespace.toCharArray()) {
      if (isCharValid(c)) {
        return false;
      }
    }
    return true;
  }

  private static boolean isIdValid(@NotNull String id) {
    for (char c : id.toCharArray()) {
      if (isCharValid(c)) {
        return false;
      }
    }
    return true;
  }

  // {_, ., [a, z], [0, 9]}
  private static boolean isCharValid(char c) {
    return c != '_' && (c < 'a' || c > 'z') && (c < '0' || c > '9') && c != '.';
  }

  @Override
  public int compareTo(@NotNull Identifier o) {
    int i = this.namespace.compareTo(o.namespace);
    if (i != 0) {
      i = this.id.compareTo(o.id);
    }
    return i;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    } else {
      return o instanceof Identifier identifier
          && this.namespace.equals(identifier.namespace)
          && this.id.equals(identifier.id);
    }
  }

  public int hashCode() {
    return 31 * this.namespace.hashCode() + this.id.hashCode();
  }

  @Override
  public String toString() {
    return namespace + SEPARATOR + id;
  }
}
