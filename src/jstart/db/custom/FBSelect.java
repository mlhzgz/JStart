package jstart.db.custom;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import jstart.db.Command;
import jstart.db.Select;

public class FBSelect extends Select {
    @Override
    public String toString() {
        String select = super.toString();

        if (getCount() > 0)
            select = select.replace("SELECT", "SELECT FIRST %d SKIP %d".formatted(getCount(), getOffset()));

        return select;
    }

    public static Command.Operation castTimestamp(Timestamp timestamp) {
        return Command.Operation.with("CAST('%s' AS TIMESTAMP)"
                .formatted(timestamp.toString().split("\\.")[0])); // delete nanoseconds, FB bug
    }

    public static Command.Operation castTimestamp(LocalDateTime localDateTime) {
        return castTimestamp(Timestamp.valueOf(localDateTime));
    }
}
