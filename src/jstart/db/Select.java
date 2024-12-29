package jstart.db;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Select extends Command<Select> {
    public static class Order {
        private boolean ascendent;
        private String field;


        public Order(boolean ascendent, String field) {
            this.ascendent = ascendent;
            this.field = field;
        }

        public Order(String field) {
            this(true, field);
        }

        public static Order by(String field, boolean ascendent) {
            return new Order(ascendent, field);
        }

        public static Order by(String field) {
            return by(field, true);
        }

        @Override
        public String toString() {
            return "%s %s".formatted(field, ascendent ? "ASC" : "DESC");
        }

        public boolean isAscendent() {
            return ascendent;
        }

        public String getField() {
            return field;
        }
    }

    public static class Join {
        public enum Type { INNER, LEFT, RIGHT, NATURAL, CROSS }
        public static final String INNER_TYPE = "INNER";
        public static final String LEFT_TYPE = "LEFT";
        public static final String RIGHT_TYPE = "RIGHT";
        public static final String NATURAL_TYPE = "NATURAL";
        public static final String CROSS_TYPE = "CROSS";

        String table;
        Type joinType;
        String joinExpression;

        public Join(String table, String expression) {
            this.table = table;
            this.joinType = Type.INNER;
            this.joinExpression = expression;
        }

        public Join(String table, Type joinType, String expression) {
            this(table, expression);
            this.joinType = joinType;
        }

        @Override
        public String toString() {
            if (joinType == Type.NATURAL || joinType == Type.CROSS)
                return "%s JOIN %s".formatted(joinType, table);
            else
                return "%s JOIN %s ON %s".formatted(joinType, table, joinExpression);
        }
    }

    private final List<String> fields;
    private int offset;
    private int count;
    private final List<Order> orders;
    private final List<Join> joins;
    private String groupBy;
    private Filter having;

    protected Select() {
        orders = new ArrayList<>();
        joins = new ArrayList<>();
        fields = new ArrayList<>();
    }

    public static Select query() {
        return new Select();
    }

    public Select field(String field) {
        fields.add(field);
        return this;
    }

    public Select offset(int offset) {
        this.offset = offset;
        return this;
    }

    public Select count(int count) {
        this.count = count;
        return this;
    }

    public Select order(String field) {
        this.orders.add(new Order(field));
        return this;
    }

    public Select order(boolean ascendent, String field) {
        this.orders.add(new Order(ascendent, field));
        return this;
    }

    public Select join(String table, Join.Type type, String expression) {
        this.joins.add(new Join(table, type, expression));
        return this;
    }

    public Select join(String table, Join.Type type) {
        this.joins.add(new Join(table, type, ""));
        return this;
    }

    public Select join(String table, String expression) {
        this.joins.add(new Join(table, expression));
        return this;
    }

    public Select groupBy(String groupBy) {
        this.groupBy = groupBy;
        return this;
    }

    public Select having(Filter having) {
        this.having = having;
        return this;
    }

    protected List<Join> getJoins() {
        return joins;
    }

    protected List<Order> getOrders() {
        return orders;
    }

    protected String getGroupBy() {
        return groupBy;
    }

    protected Filter getHaving() {
        return having;
    }

    protected int getCount() {
        return count;
    }

    protected int getOffset() {
        return offset;
    }

    @Override
    public String toString() {
        clear();
        append("SELECT %s FROM %s", (!fields.isEmpty() ? String.join(",", fields) : "*"), table());

        if (!joins.isEmpty()) {
            joins.forEach(j->append(" %s", j));
        }

        if (getFilter() != null) {
            append(" WHERE %s", getFilter());
        }

        if (groupBy != null) {
            append(" GROUP BY %s", groupBy);
        }

        if (having != null) {
            append(" HAVING %s", having);
        }

        if (!orders.isEmpty()) {
            append(" ORDER BY %s", orders.stream().map(Order::toString).collect(Collectors.joining(",")));
        }

        return super.toString();
    }
}
