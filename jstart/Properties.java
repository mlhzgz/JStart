package jstart;

import java.io.FileInputStream;

public class Properties {

    private Properties(){}

    public static void loadProperties()
    {
        try (FileInputStream fis = new FileInputStream("config.properties"))
        {
            java.util.Properties p = new java.util.Properties(System.getProperties());
            p.load(fis);
            System.setProperties(p);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static String get(String item)
    {
        return System.getProperty(item);
    }
}
