package com.steve.aqrender.registry;

import com.steve.aqrender.util.Identifier;
import java.util.HashMap;
import java.util.Map;

/**
 * 对注册表{@link Registries}的简单实现。
 *
 * @author Steve Pan, jane5598141@gmail.com
 * @param <T> 注册注册表对象类型。
 * @since March 8, 2025
 */
public class SimpleRegistry<T> implements Registry<T> {

  private final Map<Identifier, T> keyToValue = new HashMap<>();
  private final Map<T, Identifier> valueToKey = new HashMap<>();

  @Override
  public void add(Identifier key, T value) {
    keyToValue.put(key, value);
    valueToKey.put(value, key);
  }

  @Override
  public T get(Identifier name) {
    return keyToValue.get(name);
  }

  @Override
  public Identifier getID(T entry) {
    return valueToKey.get(entry);
  }
}
