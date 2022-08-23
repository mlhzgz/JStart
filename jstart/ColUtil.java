package jstart;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * Utility class for collections
 */
public final class ColUtil {

    private ColUtil(){}

    public static <T> List<T> list(T... array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    public static <T> List<T> list(List<T> list) {
        List<T> temp = list();
        temp.addAll(list);
        return temp;
    }

    public static List<String> stringList(String... array)
    {
        return list(array);
    }

    public static <K, V> Map<K, V> map()
    {
        return new HashMap<>();
    }

    public static <K, V> Entry<K, V> entry(K k, V v)
    {
        return new AbstractMap.SimpleEntry<K, V>(k, v);
    }

    public static <K, V> Map<K, V> map(Entry<K, V>... entries)
    {
        var map = new HashMap<K, V>();
        
        for (var entry : entries)
        {
            map.put(entry.getKey(), entry.getValue());
        }

        return map;
    }
}
