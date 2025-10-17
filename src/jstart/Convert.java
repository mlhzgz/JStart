package jstart;

/**
 * Allow convert an object to another data types
 */
public final class Convert {

    private Object number;

    private Convert(Object number) {
        this.number = number;
    }

    /**
     * get if data to convert is null
     * 
     * @return true if data is null
     */
    public boolean isNull() {
        return number == null;
    }

    /**
     * Sets the data to convert to another type
     * 
     * @param object
     * @return
     */
    public static Convert from(Object object) {
        return new Convert(object);
    }

    /**
     * Gets the integer part of a number string
     * 
     * @param number number string
     * @return integer part of number string
     */
    private static String getIntegerValue(String number) {
        return number.matches("[0-9]+[\\.|,].+") ? number.split("[\\.|,]")[0] : number;
    }

    /**
     * Converts the data to Byte
     * 
     * @return Byte value
     */
    public Byte toByte() {
        try {
            return Byte.valueOf(getIntegerValue(number.toString()));
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Converts the data to Short
     * 
     * @return Short value
     */
    public Short toShort() {
        try {
            return Short.valueOf(getIntegerValue(number.toString()));
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Converts the data to Integer
     * 
     * @return Integer value
     */
    public Integer toInteger() {
        try {
            return Integer.valueOf(getIntegerValue(number.toString()));
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Converts the data to Long
     * 
     * @return Long value
     */
    public Long toLong() {
        try {
            return Long.valueOf(getIntegerValue(number.toString()));
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Converts the data to Float
     * 
     * @return Float value
     */
    public Float toFloat() {
        try {
            return Float.valueOf(number.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Converts the data to Double
     * 
     * @return Double value
     */
    public Double toDouble() {
        try {
            return Double.valueOf(number.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Converts the data to Boolean
     * 
     * @return Boolean value
     */
    public Boolean toBoolean() {
        try {
            return Boolean.valueOf(number.toString());
        } catch (NumberFormatException | NullPointerException e) {
            return null;
        }
    }

    /**
     * Gets the string representation of the data, or null if data is null
     */
    @Override
    public String toString() {
        return number != null ? number.toString() : null;
    }
}
