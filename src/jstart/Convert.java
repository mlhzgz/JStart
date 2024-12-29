package jstart;

public final class Convert {

    private Object number;

    private Convert(Object number) {
        this.number = number;
    }

    public static Convert from(Object object) {
        return new Convert(object);
    }

    private static String getIntegerValue(String number) {
        return number.matches("[0-9]+[\\.|,].+") ?
            number.split("[\\.|,]")[0] :
            number;
    }

    public Byte toByte() {
        try {
            return Byte.valueOf(getIntegerValue(number.toString()));
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    public Short toShort() {
        try {
            return Short.valueOf(getIntegerValue(number.toString()));
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    public Integer toInteger() {
        try {
            return Integer.valueOf(getIntegerValue(number.toString()));
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    public Long toLong() {
        try {
            return Long.valueOf(getIntegerValue(number.toString()));
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    public Float toFloat() {
        try {
            return Float.valueOf(number.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    public Double toDouble() {
        try {
            return Double.valueOf(number.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    public Boolean toBoolean() {
        try {
            return Boolean.valueOf(number.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    @Override
    public String toString() {
        return number != null ? number.toString() : null;
    }
}

