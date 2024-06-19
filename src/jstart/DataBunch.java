package jstart;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

/**
 * This class allows to save data into an object, for example, to
 * manage objects into lambdas. The bunch can contains an only value
 * (default value), or many values with designated keys
 */
public class DataBunch<T> {
    private Map<String, T> data;

    private DataBunch() {
        data = new HashMap<>();
    }

    private DataBunch(T value) {
        this();
        data.put("", value);
    }

    /**
     * Creates a new bunch with a default value
     * 
     * @param value default data into de bunch
     * @return bunch object
     */
    public static <T> DataBunch<T> with(T value) {
        final var bunch = new DataBunch<T>();
        bunch.set("", value);
        return bunch;
    }

    /**
     * Creates a empty bunch
     * 
     * @return bunch object
     */
    public static <T> DataBunch<T> empty() {
        return new DataBunch<T>();
    }

    /**
     * Gets an empty integer bunch
     * 
     * @return an initialized bunch
     */
    public static DataBunch<Integer> getIntBunch() {
        return new DataBunch<>(0);
    }

    /**
     * Gets an empty long bunch
     * 
     * @return an initialized bunch
     */
    public static DataBunch<Long> getLongBunch() {
        return new DataBunch<>(0L);
    }

    /**
     * Gets an empty float bunch
     * 
     * @return an initialized bunch
     */
    public static DataBunch<Float> getFloatBunch() {
        return new DataBunch<>(0f);
    }

    /**
     * Gets an empty double bunch
     * 
     * @return an initialized bunch
     */
    public static DataBunch<Double> getDoubleBunch() {
        return new DataBunch<>(0d);
    }

    /**
     * Gets an empty byte bunch
     * 
     * @return an initialized bunch
     */
    public static DataBunch<Byte> getByteBunch() {
        return new DataBunch<>((byte) 0);
    }

    /**
     * Gets an empty short bunch
     * 
     * @return an initialized bunch
     */
    public static DataBunch<Short> getShortBunch() {
        return new DataBunch<>((short) 0);
    }

    /**
     * Gets an empty char bunch
     * 
     * @return an initialized bunch
     */
    public static DataBunch<Character> getCharBunch() {
        return new DataBunch<>('\0');
    }

    /**
     * Gets an empty string bunch
     * 
     * @return an initialized bunch
     */
    public static DataBunch<String> getStringBunch() {
        return new DataBunch<>("");
    }

    /**
     * Gets a false boolean bunch
     * 
     * @return an initialized bunch
     */
    public static DataBunch<Boolean> getBoolBunch() {
        return new DataBunch<Boolean>(false);
    }

    /**
     * Gets the default value in bunch
     * 
     * @return a value
     */
    public T get() {
        return data.get("");
    }

    /**
     * Sets the default value in bunch
     * 
     * @param value as default data in bunch
     */
    public void set(T value) {
        data.put("", value);
    }

    /**
     * Gets a value in a key
     * 
     * @param key to get de value
     * @return a value
     */
    public T get(String key) {
        return data.get(key);
    }

    /**
     * Sets a value in a key
     * 
     * @param key   to set a value
     * @param value to set in bunch
     */
    public void set(String key, T value) {
        data.put(key, value);
    }

    /**
     * Changes "on the fly" a value in the bunch
     * 
     * @param key  key to modify
     * @param func lambda that receives a value to modify
     */
    public void modify(String key, Function<T, T> func) {
        set(key, func.apply(get(key)));
    }

    /**
     * Changes "on the fly" the default value in bunch
     * 
     * @param func lambda that receives the default value to modify
     */
    public void modify(Function<T, T> func) {
        set("", func.apply(get()));
    }

    /**
     * Returns a string representation of the defaulf value or all values in a map
     * representation
     */
    @Override
    public String toString() {
        if (data.size() == 1 && data.containsKey(""))
            return data.get("").toString();
        else
            return data.toString();
    }
}
