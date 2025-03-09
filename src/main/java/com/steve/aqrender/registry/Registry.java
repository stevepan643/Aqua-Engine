package com.steve.aqrender.registry;

import com.steve.aqrender.util.Identifier;
import org.jetbrains.annotations.NotNull;

/**
 * 注册表。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @param <T> 注册表对象类型。
 * @since March 8, 2025
 */
public interface Registry<T> {

  /**
   * 向注册表中添加对象。
   *
   * @param key 对象键
   * @param value 对象值
   * @since March 8, 2025
   */
  void add(Identifier key, T value);

  /**
   * 通过键找值。
   *
   * @param name 对象键
   * @return 对象值
   * @since March 8, 2025
   */
  T get(Identifier name);

  /**
   * 通过值找键。
   *
   * @param entry 对象值
   * @return 对象键
   * @since March 8, 2025
   */
  Identifier getID(T entry);

  /**
   * 添加注册对象到{@code registry}。
   *
   * @param registry 添加到此注册表
   * @param id 添加对象的id
   * @param entry 添加对象
   * @since March 8, 2025
   */
  public static <V, T extends V> T register(@NotNull Registry<V> registry, Identifier id, T entry) {
    registry.add(id, entry);
    return entry;
  }
}
