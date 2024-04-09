package jstart;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
 * Multi Operating System Support for JStart
 */
public final class OS {

    private OS(){}

    /**
     * Operating System Types Enumeration
     */
    public enum Type {
        WINDOWS, UNIX, LINUX, MAC, AIX, SOLARIS, OTHER
    }

    /**
     * This variable saves in memory the last operating system
     */
    private static Type osType = null;

    /**
     * Gets the operating system type from System.getProperty()
     * @return a system type
     */
    public static Type getOSType() {
        if (osType == null) {
            var type = System.getProperty("os.name").toLowerCase();

            if (type.contains("darwin") || type.contains("mac")) {
                osType = Type.MAC;
            } else if (type.contains("win")) {
                osType = Type.WINDOWS;
            } else if (type.contains("nux")) {
                osType = Type.LINUX;
            } else if (type.contains("aix")) {
                osType = Type.AIX;
            } else if (type.contains("nix")) {
                osType = Type.UNIX;
            } else if (type.contains("sunos")) {
                osType = Type.SOLARIS;
            } else {
                osType = Type.OTHER;
            }
        }

        return osType;
    }

    /**
     * Indicates that the installed OS is Windows
     * @return true if OS is Windows
     */
    public static boolean isWindows() {
        return getOSType() == Type.WINDOWS;
    }

    /**
     * Indicates that the installed OS is Mac
     * @return true if OS is Mac
     */
    public static boolean isMac() {
        return getOSType() == Type.MAC;
    }

    /**
     * Indicates that the installed OS is Linux
     * @return true if OS is Linux
     */
    public static boolean isLinux() {
        return getOSType() == Type.LINUX;
    }

    /**
     * Gets the OS version string
     * @return version string
     */
    public static String getVersion() {
        return System.getProperty("os.version");
    }

    /**
     * Executes a system command
     * @param command order to execute in the operating system
     * @return a String with the result of the command
     */
    public static String exec(String... command) {

        var result = "";

        try {
            var p = Runtime.getRuntime().exec(command);
            var i = p.getInputStream();
            var sb = new StringBuilder();

            try (var br = new BufferedReader(new InputStreamReader(i))) {
                String line;

                while ((line = br.readLine()) != null)
                    sb.append(String.format("%s\n", line));
            }

            result = sb.toString();
        } catch (Exception e) {
            result = null;
        }

        return result;

    }

    /**
     * Executes a system command in a console terminal
     * @param commandorder to execute in the terminal
     * @return a string with the result of the command
     */
    public static String exec(String command) {
        if (isLinux() || isMac())
            return exec("/bin/sh", "-c", command);
        else if (isWindows())
            return exec("cmd.exe", "/c", command);
        else
            return exec(new String[] { command });
    }

    /**
     * Gets the temp directory of system
     * @return path to temporal directory
     */
    public static String getTempDirectory() {
        return System.getProperty("java.io.tmpdir");
    }
}
