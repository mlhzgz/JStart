package jstart.db;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * Los repositorios permiten el acceso directo a la base de datos por medio de
 * rutinas prediseñadas para determinadas
 * condiciones. Se puede heredar para generar clases especializadas para un
 * repositorio concreto de una entidad
 * 
 * @param <T> Clase de la entidad relacionada con el repositorio
 */
public class Repository<T extends Entity<T>> {
    private final Class<T> genType;
    private final DBConnection dbconnection;
    private static Map<Class, Repository> repositories;

    static {
        repositories = new HashMap<>();
    }

    protected Repository(DBConnection dbConnection) {
        this.dbconnection = dbConnection;

        Type t = getClass().getGenericSuperclass();
        ParameterizedType pt = (ParameterizedType) t;
        genType = (Class<T>) pt.getActualTypeArguments()[0];

        if (!repositories.containsKey(genType))
            repositories.put(genType, this);
        else
            throw new RuntimeException("A repository object of this type cannot be recreated.");
    }

    /**
     * Constructor para indicar una conexión a la base de datos y una clase de
     * entidad que se usará para generar
     * instancias de datos relacionados
     * 
     * @param dbConnection
     * @param entityClass
     */
    public Repository(DBConnection dbConnection, Class<T> entityClass) {
        this.dbconnection = dbConnection;
        this.genType = entityClass;

        if (!repositories.containsKey(genType))
            repositories.put(genType, this);
    }

    public static <T extends Repository> T get(Class<T> tClass) {
        if (!repositories.containsKey(tClass))
            try {
                repositories.put(tClass, tClass.getConstructor().newInstance());
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }

        return (T) repositories.get(tClass);
    }

    /**
     * Returns a new instance of the related data type. If it is a derived class,
     * it will return the generic type, and if it is a regular repository object,
     * it will return the related data type specified in the constructor.
     * 
     * @return
     * @throws NoSuchMethodException
     * @throws InvocationTargetException
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    protected Entity<T> getTypeInstance() throws NoSuchMethodException, InvocationTargetException,
            InstantiationException, IllegalAccessException {
        return genType.getConstructor().newInstance();
    }

    protected DBConnection getDbConnection() {
        return dbconnection;
    }

    protected String tableName()
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        return getTypeInstance().table();
    }

    public Query selectAll(Select.Order... orders) {
        try {
            Entity<T> objT = getTypeInstance();
            Select select = dbconnection.getSelect().get()
                    .table(objT.table());
            Query query = dbconnection.query(select);

            for (var order : orders) {
                select.order(order.isAscendent(), order.getField());
            }

            return query;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Query selectById(Object value) {
        try {
            Entity<T> objT = getTypeInstance();

            return dbconnection.query(
                    dbconnection.getSelect().get()
                            .table(objT.table())
                            .where(Filter.by(objT.primaryKey(), "=", "?"))
                            .param(value));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Query selectByFilter(Filter filter, Select.Order... orders) {
        try {
            Entity<T> objT = getTypeInstance();
            Select select = dbconnection.getSelect().get()
                    .table(objT.table())
                    .where(filter);
            Query query = dbconnection.query(select);

            for (var order : orders) {
                select.order(order.isAscendent(), order.getField());
            }

            return query;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object selectValue(String field, Filter filter) {
        try {
            Entity<T> objT = getTypeInstance();
            var select = dbconnection.getSelect().get()
                    .table(objT.table())
                    .field(field);

            if (filter != null)
                select.where(filter);

            return dbconnection.query(select).runValue();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public Object selectValue(String field) {
        return selectValue(field, null);
    }

    public long countAll() {
        return (long) selectValue("count(*)");
    }

    public long count(Filter filter) {
        return (long) selectValue("count(*)", filter);
    }

    /**
     * Inserta un registro en el repositorio del tipo indicado en la construcción
     * del repositorio
     * 
     * @param record
     * @throws SQLException
     */
    public void insert(T record) throws SQLException {
        var insert = Insert.query()
                .table(record.table());
        var data = new MapValues();

        record.writeTo(data);

        for (var key : data.keySet()) {
            insert.value(key).param(data.get(key));
        }

        dbconnection.query(insert).run(false);
    }

    /**
     * Inserta un registro en el repositorio del tipo indicado en la construcción
     * del repositorio, de forma
     * autoincremental si el sistema lo permite
     * 
     * @param record
     * @throws SQLException
     */
    public long insertAuto(T record) throws SQLException {
        var insert = Insert.query()
                .table(record.table());
        var data = new MapValues();
        var idfield = record.hasPrimaryKey() ? record.primaryKey() : "";

        record.writeTo(data);

        for (var key : data.keySet()) {
            if (!key.equals(idfield)) // Se excluye el campo
                insert.value(key).param(data.get(key));
        }

        return dbconnection.query(insert).run(true);
    }

    /**
     * Actualiza un registro en el repositorio del tipo indicado en la construcción
     * del repositorio
     * 
     * @param record
     * @return
     * @throws SQLException
     */
    public void update(T record) throws SQLException {
        var update = Update.query()
                .table(record.table());
        var data = new MapValues();
        var idfield = record.hasPrimaryKey() ? record.primaryKey() : "";
        var filter = Filter.by();

        record.writeTo(data);

        for (var key : data.keySet()) {
            if (!key.equals(idfield)) // Se excluye el campo
                update.value(key).param(data.get(key));
        }

        if (!idfield.isEmpty()) {
            filter = Filter.by().field(idfield).eq(data.get(idfield));
            update.where(filter);
        }

        dbconnection.query(update).run(false);
    }

    public boolean delete(T record) {
        try {
            Entity<T> objT = getTypeInstance();
            var idfield = record.hasPrimaryKey() ? record.primaryKey() : "";
            var data = new MapValues();

            record.writeTo(data);

            var query = Delete.query()
                    .table(objT.table())
                    .where(
                            Filter.by()
                                    .field(idfield)
                                    .eq(data.get(idfield)));

            dbconnection.query(query).run(false);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    public boolean delete(Filter filter) {
        try {
            Entity<T> objT = getTypeInstance();
            var query = Delete.query()
                    .table(objT.table())
                    .where(filter);

            dbconnection.query(query).run(false);
            return true;

        } catch (Exception e) {
            return false;
        }
    }
}
