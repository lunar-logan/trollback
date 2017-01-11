package org.inverse.util;

import java.util.*;

/**
 * Created by Dell on 11-01-2017.
 */
public final class CollectionUtils {
    private CollectionUtils() {
    }

    public static <K, V> HashMap<K, V> toHashMap(Collection<K> keys, Collection<V> values) {
        Map<K, V> map = new HashMap<K, V>();
        toMap(map, keys, values);
        return (HashMap<K, V>) map;
    }

    public static <K, V> TreeMap<K, V> toSortedMap(Collection<K> keys, Collection<V> values) {
        Map<K, V> map = new TreeMap<K, V>();
        toMap(map, keys, values);
        return (TreeMap<K, V>) map;
    }

    public static <K, V> void toMap(Map<K, V> map, Collection<K> keys, Collection<V> values) {
        Objects.requireNonNull(keys);
        Objects.requireNonNull(values);

        Iterator<K> keyItr = keys.iterator();
        Iterator<V> valItr = values.iterator();
        while (keyItr.hasNext() && valItr.hasNext()) {
            map.put(keyItr.next(), valItr.next());
        }
    }

}
