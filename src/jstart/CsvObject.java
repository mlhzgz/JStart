package jstart;

import java.util.HashMap;
import java.util.Map;

/**
 * CSV record object class
 */
public class CsvObject {

    private Map<String, String> data;
    private String[] fields;
    private String separator;

    /**
     * Creates a record object with fields and data
     * @param fields field names for data
     * @param data data of the record
     * @param separator separator of data (for toString())
     */
    public CsvObject(String[] fields, String[] data, String separator) {
        if (fields == null) {
            fields = new String[data.length];

            for (int f = 0; f < data.length; f++) {
                fields[f] = "field" + f;
            }
        }
        
        this.fields = fields;
        this.data = new HashMap<>();

        for (int x = 0; x < fields.length; x++) {
            this.data.put(fields[x], data[x]);
        }
    }

    /**
     * Get the keys (fields) of the record
     * @return string array with key names
     */
    public String[] getKeys() {
        String[] keys = new String[data.size()];
        return data.keySet().toArray(keys);
    }

    /**
     * get the values of the record
     * @return string array with the values
     */
    public Object[] getValues() {
        Object[] values = new Object[data.size()];
        return data.values().toArray(values);
    }
   
    /**
     * Get the string value for a key
     * @param key key name to get
     * @return the value of the key name
     */
    public String getString(String key) {
        return data.containsKey(key) ? data.get(key).toString() : null;
    }

    /**
     * Get the string value for a key
     * @param key key name to get
     * @return the value of the key name
     */
    public Integer getInteger(String key) {
        return data.containsKey(key) ? Integer.parseInt(data.get(key).toString()) : null;
    }

    /**
     * Get the string value for a key
     * @param key key name to get
     * @return the value of the key name
     */
    public Long getLong(String key) {
        return data.containsKey(key) ? Long.parseLong(data.get(key).toString()) : null;
    }

     /**
     * Get the string value for a key
     * @param key key name to get
     * @return the value of the key name
     */
    public Float getFloat(String key) {
        return data.containsKey(key) ? Float.parseFloat(data.get(key).toString()) : null;
    }

    /**
     * Get the string value for a key
     * @param key key name to get
     * @return the value of the key name
     */
    public Double getDouble(String key) {
        return data.containsKey(key) ? Double.parseDouble(data.get(key).toString()) : null;
    }

    /**
     * Get the string value for a key
     * @param key key name to get
     * @return the value of the key name
     */
    public Boolean getBoolean(String key) {
        return data.containsKey(key) ? Boolean.parseBoolean(data.get(key).toString()) : null;
    }

    /**
     * Get the string value for a key
     * @param key key name to get
     * @return the value of the key name
     */
    public Short getShort(String key) {
        return data.containsKey(key) ? Short.parseShort(data.get(key).toString()) : null;
    }

    /**
     * Get the string value for a key
     * @param key key name to get
     * @return the value of the key name
     */
    public Byte getByte(String key) {
        return data.containsKey(key) ? Byte.parseByte(data.get(key).toString()) : null;
    }

    /**
     * Sets a value for a key name in the object
     * @param key name of the value
     * @param value data to set
     */
    public void setString(String key, String value)
    {
        data.put(key, value);
    }

    /**
     * Sets a value for a key name in the object
     * @param key name of the value
     * @param value data to set
     */
    public void setInteger(String key, Integer value)
    {
        data.put(key, String.valueOf(value));
    }

    /**
     * Sets a value for a key name in the object
     * @param key name of the value
     * @param value data to set
     */
    public void setLong(String key, Long value)
    {
        data.put(key, String.valueOf(value));
    }

    /**
     * Sets a value for a key name in the object
     * @param key name of the value
     * @param value data to set
     */
    public void setDouble(String key, Double value)
    {
        data.put(key, String.valueOf(value));
    }

    /**
     * Sets a value for a key name in the object
     * @param key name of the value
     * @param value data to set
     */
    public void setFloat(String key, Float value)
    {
        data.put(key, String.valueOf(value));
    }

    /**
     * Sets a value for a key name in the object
     * @param key name of the value
     * @param value data to set
     */
    public void setShort(String key, Short value)
    {
        data.put(key, String.valueOf(value));
    }

    /**
     * Sets a value for a key name in the object
     * @param key name of the value
     * @param value data to set
     */
    public void setByte(String key, Byte value)
    {
        data.put(key, String.valueOf(value));
    }

    /**
     * Sets a value for a key name in the object
     * @param key name of the value
     * @param value data to set
     */
    public void setBoolean(String key, Boolean value)
    {
        data.put(key, String.valueOf(value));
    }

    /**
     * Returns the string representation of the record to store in the file
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int x = 0; x < fields.length; x++)
        {
            sb.append(data.get(fields[x])).append(separator);
        }

        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    /**
     * Get the size of the object
     * @return number of values in the record
     */
    public int size()
    {
        return data.size();
    }
}
