package jstart;

import java.io.FileInputStream;

/**
 * Class to read custom properties in a Java-style configuration file
 */
public class Properties {
    private static boolean loaded;

    private Properties(){}

    /**
     * Loads in memory the config.properties file in the current directory
     */
    public static void loadProperties() {
        loadProperties("config.properties");
    }

    /**
     * Loads in memory the indicated file
     * @param file path of the properties file
     */
    public static void loadProperties(String file)
    {
        try (FileInputStream fis = new FileInputStream(file))
        {
            java.util.Properties p = new java.util.Properties(System.getProperties());
            p.load(fis);
            System.setProperties(p);
            loaded = true;
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static boolean isLoaded() {
        return loaded;
    }

    /**
     * Gets the value of an item property
     * @param item name of the property
     * @return the value of the property
     */
    public static String get(String item)
    {
        return System.getProperty(item);
    }
}
