package jstart.db;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import jstart.annotations.PrimaryKey;
import jstart.annotations.RecordTable;

/**
 * Base model for database entities (models)
 */
public abstract class Entity<T> implements Serializable {
    private transient final Class<T> genType;

    @SuppressWarnings("unchecked")
    protected Entity() {
        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        genType = (Class<T>) pt.getActualTypeArguments()[0];

        if (!getClass().isAnnotationPresent(RecordTable.class))
            throw new RuntimeException("Data record table must be indicated whith @RecordTable annotation");
    }

    /**
     * @return record table name from entity
     */
    public String table() {
        return getClass().getAnnotation(RecordTable.class).table();
    }

    /**
     * @return primary key fields from entity
     */
    public String primaryKey() {
        return getClass().getAnnotation(PrimaryKey.class).fields();
    }

    /**
     * Gets if the entity has primary key fields
     * 
     * @return
     */
    public boolean hasPrimaryKey() {
        return getClass().isAnnotationPresent(PrimaryKey.class);
    }

    /**
     * Reads data from a map to deserialize external data in entity
     * 
     * @param data
     */
    public abstract void readFrom(MapValues data);

    /**
     * Writes data to a map to serialize internal data out of entity
     * 
     * @param data
     */
    public abstract void writeTo(MapValues data);

    /**
     * Creates a fresh copy of a entity
     * 
     * @param <Y>
     * @return
     */
    @SuppressWarnings("unchecked")
    public Entity<T> copy() {
        try {
            var o = (Entity<T>) genType.getConstructor().newInstance();
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
