package jstart.db;

/**
 * Creates a new delete query sentence
 */
public class Delete extends Command<Delete> {
    protected Delete() {

    }

    public static Delete query() {
        return new Delete();
    }

    @Override
    public String toString() {
        clear();
        append("DELETE FROM %s", table());

        if (getFilter() != null)
            append(" WHERE %s", getFilter());

        return super.toString();
    }
}
