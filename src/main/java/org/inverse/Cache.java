package org.inverse;

/**
 * Created by Dell on 11-01-2017.
 */
public interface Cache<V> {
    void put(String key, V value);

    V get(String key);
}
