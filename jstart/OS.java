package jstart;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/*
 * Multi Operating System Support for JStart
 */
public final class OS {

    private OS(){}

    public enum Type {
        WINDOWS, UNIX, LINUX, MAC, AIX, SOLARIS, OTHER
    }

    private static Type osType = null;

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

    public static boolean isWindows() {
        return getOSType() == Type.WINDOWS;
    }

    public static boolean isMac() {
        return getOSType() == Type.MAC;
    }

    public static boolean isLinux() {
        return getOSType() == Type.LINUX;
    }

    public static String getVersion() {
        return System.getProperty("os.version");
    }

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

    public static String exec(String command) {
        if (isLinux() || isMac())
            return exec("/bin/sh", "-c", command);
        else if (isWindows())
            return exec("cmd.exe", "/c", command);
        else
            return exec(new String[] { command });
    }

}
