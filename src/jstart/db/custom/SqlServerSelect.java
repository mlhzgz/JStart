package jstart.db.custom;

import jstart.db.Select;

public class SqlServerSelect extends Select {
    @Override
    public String toString() {
        String select = super.toString();

        if (getCount() > 0)
            select = select.replace("SELECT", "SELECT TOP %d".formatted(getCount()));

        return select;
    }
}
