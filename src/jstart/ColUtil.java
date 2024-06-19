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

    /**
     * Creates a list of a generic type with initial possible values 
     * @param <T> generic typs
     * @param array list of initial values
     * @return a list with the setted values
     */
    @SafeVarargs
    public static <T> List<T> list(T... array) {
        return new ArrayList<>(Arrays.asList(array));
    }

    /**
     * Creates a list with another list
     * @param <T> generic type
     * @param list list with initial values
     * @return a list with values
     */
    public static <T> List<T> list(List<T> list) {
        List<T> temp = list();
        temp.addAll(list);
        return temp;
    }

    /**
     * Creates a string list
     * @param array initial string values
     * @return a list with string values
     */
    public static List<String> stringList(String... array)
    {
        return list(array);
    }

    /**
     * Crates an empty map
     * @param <K> generic type for keys
     * @param <V> generic type for values
     * @return a new map with no values
     */
    public static <K, V> Map<K, V> map()
    {
        return new HashMap<>();
    }

    /**
     * Creates a map entry with a key and value
     * @param <K> entry key type
     * @param <V> entry value type
     * @param k entry key
     * @param v entry value
     * @return a new entry map
     */
    public static <K, V> Entry<K, V> entry(K k, V v)
    {
        return new AbstractMap.SimpleEntry<K, V>(k, v);
    }

    /**
     * Creates a new map with initial entries
     * @param <K> map key type
     * @param <V> map value type
     * @param entries optional entries for the map
     * @return a new map
     */
    @SafeVarargs
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
