package jstart.db;

import java.util.*;

public class Update extends Command<Update> {

    protected Update() {

    }

    public static Update query() {
        return new Update();
    }

    @Override
    public String toString() {
        List<String> vs = new ArrayList<>();

        for (String key : getValues().keySet()) {
            var value = getValues().get(key);

            if (value instanceof String && !value.equals("?"))
                value = "'%s'".formatted(value);

            vs.add("%s = %s".formatted(encloseString(key), value));
        }

        clear();
        append("UPDATE %s SET %s",
                encloseString(table()),
                String.join(",", vs));

        if (getFilter() != null) {
            append(" WHERE %s", getFilter());
        }

        return super.toString();
    }

    public Update value(String name, Object value) {
        super.value(name, value);
        return this;
    }

    public Update value(String name) {
        super.value(name, "?");
        return this;
    }
}
