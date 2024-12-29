package jstart.db;

import jstart.annotations.RecordTable;
import jstart.annotations.PrimaryKey;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public abstract class Entity<T> implements Serializable {
    private transient final Class<T> genType;

    protected Entity() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        genType = (Class<T>) pt.getActualTypeArguments()[0];

        if (!getClass().isAnnotationPresent(RecordTable.class))
            throw new RuntimeException("Data record table must be indicated whith @RecordTable annotation");
    }

    public String table() {
        return getClass().getAnnotation(RecordTable.class).table();
    }

    public String primaryKey() {
        return getClass().getAnnotation(PrimaryKey.class).fields();
    }

    public boolean hasPrimaryKey() {
        return getClass().isAnnotationPresent(PrimaryKey.class);
    }

    public abstract void readFrom(MapValues data);
    public abstract void writeTo(MapValues data);

    public <T extends Entity<T>> T copy() {
        try {
            var o = (T)genType.getConstructor().newInstance();
            var m = new MapValues();

            writeTo(m);
            o.readFrom(m);

            return o;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String toString() {
        var m = new MapValues();
        writeTo(m);
        return m.toString();
    }
}
