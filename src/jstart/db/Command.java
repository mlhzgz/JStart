package jstart.db;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Command class (SQL instruction support)
 */
public abstract class Command<T> {
    /**
     * Helping class for special operations (like SQL functions, etc...)
     */
    public static class Operation {
        public final String operation;

        private Operation(String operation) {
            this.operation = operation;
        }

        /**
         * Creates a new operation object
         * 
         * @param operation SQL operation to add to command (like SQL functions)
         * @return operations object
         */
        public static Operation with(String operation) {
            return new Operation(operation);
        }

        @Override
        public String toString() {
            return operation;
        }
    }

    private String table;
    private List<Object> parameters;
    private final StringBuilder sb;
    private Map<String, Object> values;
    private Filter filter;
    private String[] enclosingCharacters;

    protected Command() {
        sb = new StringBuilder();
    }

    /**
     * Remove all parameters (for prepared statements)
     */
    public void clearParameters() {
        parameters.clear();
    }

    /**
     * Append a fragment of a command
     * 
     * @param string fragment of command (can be a string format)
     * @param values to include in a format string parameter
     */
    protected void append(String string, Object... values) {
        if (values.length > 0) {
            sb.append(string.formatted(values));
        } else {
            sb.append(string);
        }
    }

    /**
     * Clear all internal command string
     */
    protected void clear() {
        sb.setLength(0);
    }

    /**
     * Gets the command parameters
     * 
     * @return list of values (parameters)
     */
    protected List<Object> getParameters() {
        return parameters;
    }

    /**
     * Adds a new parameter for a prepared statements
     * 
     * @param param value for new parameter
     * @return command object
     */
    @SuppressWarnings("unchecked")
    public T param(Object param) {
        if (parameters == null)
            parameters = new ArrayList<>();

        parameters.add(param);

        return (T) this;
    }

    /**
     * Indicates if the command has parameters
     * 
     * @return command object
     */
    public boolean hasParameters() {
        return parameters != null && !parameters.isEmpty();
    }

    /**
     * Sets the table name of command
     * 
     * @param table command table name
     * @return command object
     */
    @SuppressWarnings("unchecked")
    public T table(String table) {
        this.table = table;
        return (T) this;
    }

    /**
     * Gets the table name
     * 
     * @return command table name
     */
    public String table() {
        return table;
    }

    /**
     * Set a key/value pair (for update or insert commands)
     * 
     * @param key   key of value
     * @param value data for value
     * @return command object
     */
    @SuppressWarnings("unchecked")
    public T value(String key, Object value) {
        if (values == null)
            values = new LinkedHashMap<>();

        values.put(key, value);

        return (T) this;
    }

    /**
     * Set a key/value pair (for update or insert commands)
     * 
     * @param key   key of value
     * @param value operation object for value
     * @return command object
     */
    public T value(String key, Operation value) {
        return value(key, (Object) value);
    }

    /**
     * Gets the command values
     * 
     * @return map of values
     */
    protected Map<String, Object> getValues() {
        return values;
    }

    /**
     * Adds a filter to command
     * 
     * @param filter object to SQL select
     * @return command object
     */
    @SuppressWarnings("unchecked")
    public T where(Filter filter) {
        this.filter = filter;
        return (T) this;
    }

    /**
     * Gets the current filter
     * 
     * @return the current filter
     */
    protected Filter getFilter() {
        return filter;
    }

    /**
     * Gets the builded command string
     */
    @Override
    public String toString() {
        return sb.toString();
    }

    /**
     * Specifies data engine-specific characters to enclose special expressions
     * 
     * @param beginChar
     * @param endChar
     */
    public void setEnclosingChars(String beginChar, String endChar) {
        enclosingCharacters = new String[2];
        enclosingCharacters[0] = beginChar;
        enclosingCharacters[1] = endChar;
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

    /**
     * Enclose a string between the defined enclosing characters
     * 
     * @param str to enclose
     */
    public String encloseString(String str) {
        return enclosingCharacters[0] + str + enclosingCharacters[1];
    }
}
