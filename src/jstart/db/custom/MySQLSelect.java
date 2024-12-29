package jstart.db.custom;

import jstart.db.Select;

public class MySQLSelect extends Select {

    @Override
    public String toString() {
        String select = super.toString();

        if (getCount() > 0)
            select += " LIMIT %d,%d".formatted(getOffset(), getCount());

        return select;
    }
}
