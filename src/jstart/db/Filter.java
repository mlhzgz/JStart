package jstart.db;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Filters for Select/Update/Delete queries
 */
public class Filter {
    public static class Function {
        private String function;

        private Function(String function) {
            this.function = function;
        }

        public static Function as(String function) {
            return new Function(function);
        }

        @Override
        public String toString() {
            return function;
        }
    }

    private final StringBuilder filter;

    private Filter() {
        filter = new StringBuilder();
    }

    /**
     * Create a new filter
     * 
     * @return filter object
     */
    public static Filter by() {
        return new Filter();
    }

    /**
     * Create a new filter with parameters
     * 
     * @param field    of the filter
     * @param operator of the filter
     * @param value    or values for the filter (string, number, list, etc...)
     * @return filter object
     */
    public static Filter by(String field, String operator, Object value) {
        var filter = new Filter();

        filter.field(field);
        filter.filter.append(operator);
        filter.value(value);

        return filter;
    }

    public static Filter by(String expression) {
        var filter = new Filter();

        filter.filter.append(expression);

        return filter;
    }

    /**
     * Field for filter condition
     * 
     * @param fieldName name of field in condition
     * @return
     */
    public Filter field(String fieldName) {
        filter.append("%s ".formatted(fieldName));
        return this;
    }

    /**
     * Value for condition (string, number, list, subselect, etc...)
     * 
     * @param value
     * @return
     */
    protected Filter value(Object value) {
        if (value instanceof String && !value.equals("?")) {
            filter.append(" '%s'".formatted(value));
            // } else if (value instanceof List) {
            // filter.append(" (%s)".formatted(
            // ((List) value).stream()
            // .map(v -> (v instanceof String) ? "'%s'".formatted(v) : v.toString())
            // .collect(Collectors.joining(","))));
        } else if (value instanceof Filter) {
            filter.append(" (%s)".formatted(value));
        } else if (value instanceof Select) {
            filter.append(" (%s)".formatted(value));
        } else {
            filter.append(" %s".formatted(value.toString()));
        }
        return this;
    }

    public <T> Filter value(List<T> values) {
        filter.append(" (%s)".formatted(
                ((List<T>) values).stream()
                        .map(v -> {
                            return (v instanceof String) ? "'%s'".formatted(v) : v.toString();
                        })
                        .collect(Collectors.joining(","))));
        return this;
    }

    /**
     * Equals operator
     * 
     * @param value for equals condition
     * @return
     */
    public Filter eq(Object value) {
        filter.append("=");
        value(value);
        return this;
    }

    /**
     * Inequality operator
     * 
     * @param value for inequality condition
     * @return
     */
    public Filter ne(Object value) {
        filter.append("<>");
        value(value);
        return this;
    }

    /**
     * Greater than operator
     * 
     * @param value is greater than other
     * @return
     */
    public Filter gt(Object value) {
        filter.append(">");
        value(value);
        return this;
    }

    /**
     * Less than
     * 
     * @param value is lesser than other
     * @return
     */
    public Filter lt(Object value) {
        filter.append("<");
        value(value);
        return this;
    }

    /**
     * Greater or equals than
     * 
     * @param value is greater or equals than other
     * @return
     */
    public Filter get(Object value) {
        filter.append(">=");
        value(value);
        return this;
    }

    /**
     * Lesser or equals than
     * 
     * @param value is lesser or equals then other
     * @return
     */
    public Filter let(Object value) {
        filter.append("<=");
        value(value);
        return this;
    }

    /**
     * IN condition with a list of values
     * 
     * @param values list of values
     * @return
     */
    public <T> Filter in(List<T> values) {
        filter.append("IN");
        value(values);
        return this;
    }

    /**
     * IN condition with a list of values
     * 
     * @param values list of values
     * @return
     */
    public <T> Filter in(T[] values) {
        return in(Arrays.asList(values));
    }

    /**
     * AND operator between conditions
     * 
     * @return
     */
    public Filter and() {
        this.filter.append(" AND ");
        return this;
    }

    /**
     * OR operator between conditions
     * 
     * @return
     */
    public Filter or() {
        this.filter.append(" OR ");
        return this;
    }

    /**
     * AND operator between conditions (with other filter)
     * 
     * @return
     */
    public Filter and(Filter filter) {
        this.filter.append(" AND ");
        this.filter.append(filter);
        return this;
    }

    /**
     * OR operator between conditions (with other filter)
     * 
     * @return
     */
    public Filter or(Filter filter) {
        this.filter.append(" OR ");
        this.filter.append(filter);
        return this;
    }

    /**
     * Negate condition
     * 
     * @return
     */
    public Filter not() {
        filter.append("NOT ");
        return this;
    }

    /**
     * The field is null
     * 
     * @return
     */
    public Filter isNull() {
        filter.append("IS NULL");
        return this;
    }

    /**
     * The field is not null
     * 
     * @return
     */
    public Filter isNotNull() {
        filter.append("IS NOT NULL");
        return this;
    }

    /**
     * The field is like something
     * 
     * @param value string to parse
     * @return
     */
    public Filter like(Object value) {
        filter.append("LIKE");
        value(value);
        return this;
    }

    /**
     * Adds a initial parethesis
     * 
     * @return
     */
    public Filter starts() {
        filter.append("(");
        return this;
    }

    /**
     * Adds a final parethesis
     * 
     * @return
     */
    public Filter ends() {
        filter.append(")");
        return this;
    }

    /**
     * Converts filter to a String
     */
    @Override
    public String toString() {
        return "%s".formatted(filter);
    }
}
