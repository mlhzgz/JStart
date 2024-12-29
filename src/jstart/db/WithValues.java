package jstart.db;

import java.util.Map;

public interface WithValues<T> {
    class Operation {
        public final String operation;

        private Operation(String operation) {
            this.operation = operation;
        }

        public static Operation with(String operation) {
            return new Operation(operation);
        }

        @Override
        public String toString() {
            return operation;
        }
    }

    void initValues();

    Map<String, Object> getValues();

    default T value(String name, Object value) {
        getValues().put(name, value);
        return (T)this;
    }

    default T value(String name, Operation value) {
        return value(name, (Object) value);
    }
}
