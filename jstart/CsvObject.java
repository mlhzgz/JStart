package jstart;

import java.util.HashMap;
import java.util.Map;

public class CsvObject {

    private Map<String, String> data;
    private String[] fields;
    private String separator;

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

    public String[] getKeys() {
        String[] keys = new String[data.size()];
        return data.keySet().toArray(keys);
    }

    public Object[] getValues() {
        Object[] values = new Object[data.size()];
        return data.values().toArray(values);
    }
   
    public String getString(String key) {
        return data.containsKey(key) ? data.get(key).toString() : null;
    }

    public Integer getInteger(String key) {
        return data.containsKey(key) ? Integer.parseInt(data.get(key).toString()) : null;
    }

    public Long getLong(String key) {
        return data.containsKey(key) ? Long.parseLong(data.get(key).toString()) : null;
    }

    public Float getFloat(String key) {
        return data.containsKey(key) ? Float.parseFloat(data.get(key).toString()) : null;
    }

    public Double getDouble(String key) {
        return data.containsKey(key) ? Double.parseDouble(data.get(key).toString()) : null;
    }

    public Boolean getBoolean(String key) {
        return data.containsKey(key) ? Boolean.parseBoolean(data.get(key).toString()) : null;
    }

    public Short getShort(String key) {
        return data.containsKey(key) ? Short.parseShort(data.get(key).toString()) : null;
    }

    public Byte getByte(String key) {
        return data.containsKey(key) ? Byte.parseByte(data.get(key).toString()) : null;
    }

    public void setString(String key, String value)
    {
        data.put(key, value);
    }

    public void setInteger(String key, Integer value)
    {
        data.put(key, String.valueOf(value));
    }

    public void setLong(String key, Long value)
    {
        data.put(key, String.valueOf(value));
    }

    public void setDouble(String key, Double value)
    {
        data.put(key, String.valueOf(value));
    }

    public void setFloat(String key, Float value)
    {
        data.put(key, String.valueOf(value));
    }

    public void setShort(String key, Short value)
    {
        data.put(key, String.valueOf(value));
    }

    public void setByte(String key, Byte value)
    {
        data.put(key, String.valueOf(value));
    }

    public void setBoolean(String key, Boolean value)
    {
        data.put(key, String.valueOf(value));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int x = 0; x < fields.length; x++)
        {
            sb.append(data.get(fields[x])).append(separator);
        }

        return sb.deleteCharAt(sb.length() - 1).toString();
    }

    public int size()
    {
        return data.size();
    }
}
