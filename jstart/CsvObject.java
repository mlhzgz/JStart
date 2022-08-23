package jstart;

import java.util.Map;

public class CsvObject {

    private Map<String, String> data;

    public CsvObject(String[] fields, String[] data) {
        if (fields == null) {
            fields = new String[data.length];

            for (int f = 0; f < data.length; f++) {
                fields[f] = "field" + f;
            }
        }
        
        for (int x = 0; x < fields.length; x++) {
            this.data.put(fields[x], data[x]);
        }
    }

    public String getValue(String key) {
        return data.containsKey(key) ? data.get(key).toString() : null;
    }

    public String[] getKeys() {
        String[] keys = new String[data.size()];
        return data.keySet().toArray(keys);
    }

    public Object[] getValues() {
        Object[] values = new Object[data.size()];
        return data.values().toArray(values);
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
}
