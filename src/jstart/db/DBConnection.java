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

    /**
     * Enclosing characters for tables, fields, etc.
     */
    private String[] enclosingCharacters;

    static {
        connections = new HashMap<>();
    }

    private DBConnection() {
        selectInstance = Select::new;
        enclosingCharacters = new String[] { "", "" };

        Runtime.getRuntime().addShutdownHook(
                new Thread(() -> {
                    try {
                        if (conn != null)
                            conn.close();
                    } catch (SQLException e) {
                        throw new RuntimeException(e);
                    }
                }));
    }

    /**
     * Specify a DB url connection string to drive manager
     * 
     * @param urlConn
     * @throws SQLException
     */
    protected DBConnection(String urlConn) throws SQLException {
        this();
        conn = DriverManager.getConnection(urlConn);
    }

    /**
     * Specify a DB url connection with properties to drive manager
     * 
     * @param urlConn
     * @param props
     * @throws SQLException
     */
    protected DBConnection(String urlConn, Properties props) throws SQLException {
        this();
        conn = DriverManager.getConnection(urlConn, props);
    }

    /**
     * Specify a DB url connection with an username and password
     * 
     * @param urlConn
     * @param username
     * @param password
     * @throws SQLException
     */
    protected DBConnection(String urlConn, String username, String password) throws SQLException {
        this();
        conn = DriverManager.getConnection(urlConn, username, password);
    }

    Connection getConnection() {
        return conn;
    }

    /**
     * Returns a previously created connection
     * 
     * @param name of db connection
     * @return a DBConnection object
     */
    public static DBConnection get(String name) {
        return connections.getOrDefault(name, null);
    }

    /**
     * Creates a new connection with a name and a url connection string
     * 
     * @param name
     * @param urlConn
     * @return
     * @throws SQLException
     */
    public static DBConnection create(String name, String urlConn) throws SQLException {
        if (connections.containsKey(name))
            return connections.get(name);

        var conn = new DBConnection(urlConn) {
        };

        connections.put(name, conn);

        return conn;
    }

    /**
     * Creates a new connection with a name and a url connection string with a
     * properties object
     * 
     * @param name
     * @param urlConn
     * @param props
     * @return
     * @throws SQLException
     */
    public static DBConnection create(String name, String urlConn, Properties props) throws SQLException {
        if (connections.containsKey(name))
            return connections.get(name);

        var conn = new DBConnection(urlConn, props) {
        };

        connections.put(name, conn);

        return conn;
    }

    /**
     * Creates a new connection with a name and a url connection string and a
     * username and password values
     * 
     * @param name
     * @param urlConn
     * @param username
     * @param password
     * @return
     * @throws SQLException
     */
    public static DBConnection create(String name, String urlConn, String username, String password)
            throws SQLException {
        if (connections.containsKey(name))
            return connections.get(name);

        var conn = new DBConnection(urlConn, username, password) {
        };

        connections.put(name, conn);

        return conn;
    }

    /**
     * Creates a new query object
     * 
     * @param command
     * @return
     */
    @SuppressWarnings("rawtypes")
    public Query query(Command command) {
        command.setEnclosingChars(getBeginEnclosingChar(), getEndEnclosingChar());
        return Query.with(this, command);
    }

    /**
     * Sets a new select query object type for the connection (like classes in
     * custom package)
     * 
     * @param selectInstance
     * @return
     */
    public DBConnection setSelect(Supplier<Select> selectInstance) {
        this.selectInstance = selectInstance;
        return this;
    }

    /**
     * Specifies data engine-specific characters to enclose special expressions
     * 
     * @param beginChar
     * @param endChar
     * @return
     */
    public DBConnection setEnclosingChars(String beginChar, String endChar) {
        enclosingCharacters[0] = beginChar;
        enclosingCharacters[1] = endChar;

        return this;
    }

    /**
     * Returns the current select query object of the connection
     * 
     * @return
     */
    public Supplier<Select> getSelect() {
        return selectInstance;
    }

    /**
     * @return the begin enclosing char
     */
    public String getBeginEnclosingChar() {
        return enclosingCharacters[0];
    }

    /**
     * @return the ending enclosing char
     */
    public String getEndEnclosingChar() {
        return enclosingCharacters[1];
    }
}
