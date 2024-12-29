package jstart.db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class Query {
    public static class DBNull {
        private int type;

        public static final Query.DBNull IntNull = new Query.DBNull(Types.INTEGER);
        public static final Query.DBNull FloatNull = new Query.DBNull(Types.FLOAT);
        public static final Query.DBNull DoubleNull = new Query.DBNull(Types.DOUBLE);
        public static final Query.DBNull StringNull = new Query.DBNull(Types.VARCHAR);
        public static final Query.DBNull CharNull = new Query.DBNull(Types.CHAR);
        public static final Query.DBNull TimestampNull = new Query.DBNull(Types.TIMESTAMP);
        public static final Query.DBNull BooleanNull = new Query.DBNull(Types.BOOLEAN);

        public DBNull(int type) {
            this.type = type;
        }

        public int getType() {
            return type;
        }
    }

    private final DBConnection connection;
    private final Command command;

    private Query(DBConnection connection, Command command) {
        this.connection = connection;
        this.command = command;
    }

    public static Query with(DBConnection connection, Command command) {
        return new Query(connection, command);
    }

    /**
     * Runs an insert/update/delete query
     * 
     * @param generateKeys true if returns the generate keys (insert queries)
     * @return generate key number (auto incremental ids)
     * @throws SQLException
     */
    public long run(boolean generateKeys) throws SQLException {
        String sql = command.toString();

        if (!command.hasParameters()) {
            try (var stm = connection.getConnection().createStatement()) {
                if (generateKeys) {
                    stm.executeUpdate(sql, java.sql.Statement.RETURN_GENERATED_KEYS);

                    try (var result = stm.getGeneratedKeys()) {
                        result.next();
                        return result.getLong(1);
                    }
                } else {
                    stm.execute(sql);
                }
            }
        } else {
            try (var ps = getPreparedStatement(sql, generateKeys)) {
                final var params = command.getParameters();

                for (int x = 0; x < params.size(); x++) {
                    ps.setObject(x + 1, params.get(x));
                }

                if (generateKeys) {
                    ps.executeUpdate();

                    try (var result = ps.getGeneratedKeys()) {
                        result.next();
                        return result.getLong(1);
                    }
                } else {
                    ps.execute();
                }
            }

        }

        return 0;
    }

    protected Statement getStatement() throws SQLException {
        return connection.getConnection().createStatement();
    }

    public ResultSet run() throws SQLException {
        final String sql = command.toString();

        // if (!command.hasParameters()) {
        return getStatement().executeQuery(sql);
        // } else {
        // try (var ps = getPreparedStatement(sql, null)) {
        // final var params = command.getParameters();

        // for (int x = 0; x < params.size(); x++) {
        // ps.setObject(x + 1, params.get(x));
        // }

        // return ps.executeQuery();
        // }
        // }
    }

    // public void run(Consumer<ResultSet> fun) throws SQLException {
    // var result = run();

    // while (result.next()) {
    // fun.accept(result);
    // }

    // result.close();
    // }

    public void run(Consumer<ResultSet> fun) throws SQLException {
        if (!command.hasParameters()) {
            final var result = run();
            while (result.next()) {
                fun.accept(result);
            }
            result.close();
        } else {
            try (var ps = getPreparedStatement(command.toString(), null)) {
                // final var params = command.getParameters();

                // for (int x = 0; x < params.size(); x++) {
                //     ps.setObject(x + 1, params.get(x));
                // }

                final var result = ps.executeQuery();

                while (result.next()) {
                    fun.accept(result);
                }
            }
        }
    }

    public <T> List<T> runToRecords(Supplier<T> supplier) throws SQLException {
        List<T> records = new ArrayList<>();

        run(rs -> {
            var record = (Entity) supplier.get();
            var map = new MapValues();

            try {
                var metadata = rs.getMetaData();
                var count = metadata.getColumnCount();

                for (int x = 1; x <= count; x++) {
                    var name = metadata.getColumnName(x);
                    map.put(name, rs.getObject(x));
                }

                record.readFrom(map);
                records.add((T) record);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }

        });

        return records;
    }

    public <T> void runToRecord(Supplier<T> supplier, Consumer<T> fun) throws SQLException {
        run(rs -> {
            var record = (Entity) supplier.get();
            var map = new MapValues();

            try {
                var metadata = rs.getMetaData();
                var count = metadata.getColumnCount();

                for (int x = 1; x <= count; x++) {
                    var name = metadata.getColumnName(x);
                    map.put(name, rs.getObject(x));
                }

                record.readFrom(map);

                fun.accept((T) record);

            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public Object runValue() throws SQLException {
        if (!command.hasParameters()) {
            final var result = run();
            if (result.next()) {
                return result.getObject(1);
            }
        } else {
            try (var ps = getPreparedStatement(command.toString(), null)) {
                // final var params = command.getParameters();

                // for (int x = 0; x < params.size(); x++) {
                //     ps.setObject(x + 1, params.get(x));
                // }

                final var result = ps.executeQuery();

                if (result.next())
                    return result.getObject(1);
            }
        }

        return null;
    }

    // public Object runValue() throws SQLException {
    //     var result = run();

    //     if (result.next()) {
    //         return result.getObject(1);
    //     }

    //     result.close();

    //     return null;
    // }

    protected PreparedStatement getPreparedStatement(String query, Boolean generateKeys) throws SQLException {
        PreparedStatement ps;

        if (generateKeys == null) {
            ps = connection.getConnection().prepareStatement(query);
        } else if (generateKeys) {
            ps = connection.getConnection().prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        } else {
            ps = connection.getConnection().prepareStatement(query, Statement.NO_GENERATED_KEYS);
        }

        var parameters = command.getParameters();

        for (int x = 0; x < parameters.size(); x++) {
            var param = parameters.get(x);

            if (param instanceof Integer)
                ps.setInt(x + 1, (Integer) param);
            else if (param instanceof String)
                ps.setString(x + 1, (String) param);
            else if (param instanceof Long)
                ps.setLong(x + 1, (Long) param);
            else if (param instanceof Float)
                ps.setFloat(x + 1, (Float) param);
            else if (param instanceof Double)
                ps.setDouble(x + 1, (Double) param);
            else if (param instanceof Timestamp)
                ps.setTimestamp(x + 1, (Timestamp) param);
            else if (param instanceof Boolean)
                ps.setBoolean(x + 1, (Boolean) param);
            else if (param instanceof DBNull)
                ps.setNull(x + 1, ((DBNull) param).type);
        }

        return ps;
    }
}
