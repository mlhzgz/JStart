package jstart.db;

import java.util.stream.Collectors;

public class Insert extends Command<Insert> {

    protected Insert() {
    }

    public static Insert query() {
        return new Insert();
    }

    public Insert value(String name, Object value) {
        super.value(name, value);
        return this;
    }

    public Insert value(String name) {
        super.value(name, "?");
        return this;
    }

    @Override
    public String toString() {
        final var values = getValues();

        clear();
        append("INSERT INTO %s (%s) VALUES (%s)",
                table(),
                values.keySet().stream().map("`%s`"::formatted).collect(Collectors.joining(",")),
                values.values().stream().map(v -> {
                    if (v instanceof String && !v.equals("?")) // Si es texto pero no un parámetro
                        return "'%s'".formatted(v);
                    else if (v == null)
                        return "NULL";
                    else
                        return v.toString();
                }).collect(Collectors.joining(",")));

        return super.toString();
    }
}
