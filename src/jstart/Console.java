package jstart;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * Utility class for Unix-type console
 * This class can run as "static" class or like super class
 */
public abstract class Console {

    /**
     * Sets the foreground/background color for the terminal (using Colors class)
     * 
     * @param foreground The foreground color
     * @param background The background color
     */
    public static void setColor(String foreground, String background) {
        print(foreground + background);
    }

    /**
     * Sets the foreground color for the terminal (using Colors class)
     * 
     * @param foreground The foreground color
     */
    public static void setColor(String foreground) {
        print(foreground);
    }

    /**
     * Resets the colors on terminal (to default colors)
     */
    public static void reset() {
        print(Colors.RESET);
    }

    /**
     * Sets the text cursor in a position on terminal
     * 
     * @param x position X for cursor
     * @param y position Y for cursor
     */
    public static void setCursor(int x, int y) {
        print(String.format("%c[%d;%df", 0x1B, x, y));
    }

    /**
     * Prints an error to screen using RED foreground color
     * 
     * @param error text to print
     * @param high  indicates the weight of the text
     */
    public static void printError(String error, boolean high) {
        setColor(high ? Colors.Bold.RED : Colors.High.RED);
        print(error);
        reset();
    }

    /**
     * Prints a warning to screen using YELLOW foreground color
     * 
     * @param warning text to print
     * @param high    indicates the weight of the text
     */
    public static void printWarning(String warning, boolean high) {
        setColor(high ? Colors.Bold.YELLOW : Colors.YELLOW);
        print(warning);
        reset();
    }

    /**
     * Prints a text info to screen using GREEN foreground color
     * 
     * @param info text to print
     * @param high indicates de weight of the text
     */
    public static void printInfo(String info, boolean high) {
        setColor(high ? Colors.Bold.GREEN : Colors.GREEN);
        print(info);
        reset();
    }

    /**
     * Prints something to screen
     * 
     * @param what something to print
     */
    public static void print(Object what) {
        System.out.print(what);
    }

    /**
     * Prints something to screen with a new line
     * 
     * @param what something to print
     */
    public static void println(Object what) {
        System.out.println(what);
    }

    /**
     * Prints a new line to screen
     */
    public static void println() {
        System.out.println();
    }

    /**
     * Reads a string value from keyboard
     * 
     * @return the text read from the keyboard
     */
    public static String readString() {
        String string = null;

        try (var isr = new InputStreamReader(System.in);
                var br = new BufferedReader(isr)) {
            string = br.readLine();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (string != null) {
            string = string.trim();

            if (string.isBlank() || string.isEmpty())
                string = null;
        }

        return string;
    }

    /**
     * Reads a string value from keyboard
     * 
     * @param defaultValue The value returned if the user does not type anything
     * @return the text read from the keyboard or defaulValue
     */
    public static String readString(String defaultValue) {
        String value = readString();

        if (value == null)
            return defaultValue;

        return value;
    }

    /**
     * Reads an Integer number from keyboard
     * 
     * @return an Integer number
     */
    public static Integer readInt() {
        var value = readString();

        if (value != null)
            return Integer.parseInt(readString());
        else
            return null;
    }

    /**
     * Reads an int number from keyboard with a default value 
     * @param defaultValue value if the user doesn't enter a value
     * @return an int number
     */
    public static int readInt(int defaultValue) {
        var value = readInt();

        if (value == null)
            value = defaultValue;

        return value;
    }

    /**
     * Reads a Float number from keyboard
     * @return a Float number
     */
    public static Float readFloat() {
        return Float.parseFloat(readString());
    }

    /**
     * Reads a float number from keyboard with a default value
     * @param defaultValue value if the user doesn't enter a value
     * @return a float number
     */
    public static float readFloat(float defaultValue) {
        var value = readFloat();

        if (value == null)
            value = defaultValue;

        return value;
    }

    /**
     * Reads a Double number from keyboard
     * @return a Double number
     */
    public static Double readDouble() {
        return Double.parseDouble(readString());
    }

    /**
     * Reads a double number with a default value
     * @param defaultValue value if the user doesn't enter a value
     * @return a double number
     */
    public static double readDouble(double defaultValue) {
        var value = readDouble();

        if (value == null)
            value = defaultValue;

        return value;
    }

    /**
     * Reads a Long number from keyboard
     * @return a Long number
     */
    public static Long readLong() {
        return Long.parseLong(readString());
    }

    /**
     * REads a long number with a default value
     * @param defaultValue value if the user doesn't enter a value
     * @return a long number
     */
    public static long readLong(long defaultValue) {
        var value = readLong();

        if (value == null)
            value = defaultValue;

        return value;
    }

    /**
     * Clears the terminal
     */
    public static void clearTerminal() {
        if (OS.isWindows())
            OS.exec("cls");
        else
            System.out.print("\033[H\033[2J");
    }
}
