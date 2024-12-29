package jstart.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Supplier;

public abstract class DBConnection {
    /**
     * Bunch of unique connections to databases
     */
    private static Map<String, DBConnection> connections;

    /**
     * Connection object to database
     */
    private Connection conn;

    /**
     * Select command instance by default for these connection
     */
    private Supplier<Select> selectInstance;

    static {
        connections = new HashMap<>();
    }

    private DBConnection() {
        selectInstance = Select::new;

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    try {
                        if (conn != null)
                            conn.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                })
        );
    }

    protected DBConnection(String urlConn) throws SQLException {
        this();
        conn = DriverManager.getConnection(urlConn);
    }

    protected DBConnection(String urlConn, Properties props) throws SQLException {
        this();
        conn = DriverManager.getConnection(urlConn, props);
    }

    protected DBConnection(String urlConn, String username, String password) throws SQLException {
        this();
        conn = DriverManager.getConnection(urlConn, username, password);
    }

    Connection getConnection() {
        return conn;
    }

    public static DBConnection get(String name) {
        return connections.getOrDefault(name, null);
    }

    public static DBConnection create(String name, String urlConn) throws SQLException {
        if (connections.containsKey(name))
            return connections.get(name);

        var conn = new DBConnection(urlConn) {};

        connections.put(name, conn);

        return conn;
    }

    public static DBConnection create(String name, String urlConn, Properties props) throws SQLException {
        if (connections.containsKey(name))
            return connections.get(name);

        var conn = new DBConnection(urlConn, props) {};

        connections.put(name, conn);

        return conn;
    }

    public static DBConnection create(String name, String urlConn, String username, String password) throws SQLException {
        if (connections.containsKey(name))
            return connections.get(name);

        var conn = new DBConnection(urlConn, username, password) {};

        connections.put(name, conn);

        return conn;
    }

    public Query query(Command command) {
        return Query.with(this, command);
    }

    public DBConnection setSelect(Supplier<Select> selectInstance) {
        this.selectInstance = selectInstance;
        return this;
    }

    public Supplier<Select> getSelect(){
        return selectInstance;
    }

    // public <T extends Entity<T>> Repository<T> repository(Class<Repository<T>> tClass) {
    //     var repo = Repository.get(tClass);

    //     if (repo == null)
    //         repo = new Repository<>(this, tClass.);

    //     return repo;
    // }
}
