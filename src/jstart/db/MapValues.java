package jstart.db;

import java.math.BigInteger;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.Map;

import jstart.Convert;

public class MapValues extends HashMap<String, Object> {
    public MapValues() {
        super();
    }

    public MapValues(Map<String, Object> map) {
        super(map);
    }

    public static MapValues with(ResultSet rs) {
        final var map = new MapValues();

        try {
            var metadata = rs.getMetaData();
            var count = metadata.getColumnCount();

            for (int x = 1; x <= count; x++) {
                // var name = metadata.getColumnName(x);
                // Hay que devolver los alias (también devuelve los nobmres de campos)
                var name = metadata.getColumnLabel(x);
                map.put(name, rs.getObject(x));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return map;
    }

    @Override
    public Object getOrDefault(Object key, Object defaultValue) {
        if (!containsKey(key) || get(key) == null)
            return defaultValue;

        return super.getOrDefault(key, defaultValue);
    }

    public Integer getInteger(String key) {
        return Convert.from(getOrDefault(key, null)).toInteger();
    }

    public Byte getByte(String key) {
        return Convert.from(getOrDefault(key, null)).toByte();
    }

    public String getString(String key) {
        return (String) getOrDefault(key, null);
    }

    public Float getFloat(String key) {
        return Convert.from(getOrDefault(key, null)).toFloat();
    }

    public Long getLong(String key) {
        return Convert.from(getOrDefault(key, null)).toLong();
    }

    public Double getDouble(String key) {
        return Convert.from(getOrDefault(key, null)).toDouble();
    }

    public Timestamp getTimestamp(String key) {
        return (Timestamp) getOrDefault(key, null);
    }

    public LocalDateTime getLocalDateTime(String key) {
        if (get(key) instanceof Timestamp) {
            final var ts = getTimestamp(key);
            return ts != null ? ts.toLocalDateTime() : null;
        } else {
            return (LocalDateTime) getOrDefault(key, null);
        }
    }

    public Boolean getBoolean(String key) {
        return Convert.from(getOrDefault(key, null)).toBoolean();
    }

    public Object getObject(String key) {
        return getOrDefault(key, null);
    }

    public Short getShort(String key) {
        return Convert.from(getOrDefault(key, null)).toShort();
    }

    public Date getDate(String key) {
        return (Date) getOrDefault(key, null);
    }

    public LocalDate getLocalDate(String key) {
        final var ld = getDate(key);
        return ld != null ? ld.toLocalDate() : null;
    }

    public Time getTime(String key) {
        return (Time) getOrDefault(key, null);
    }

    public LocalTime getLocalTime(String key) {
        final var lt = getTime(key);
        return lt != null ? lt.toLocalTime() : null;
    }

    public BigInteger getBigInteger(String key) {
        return (BigInteger) getOrDefault(key, null);
    }
}
