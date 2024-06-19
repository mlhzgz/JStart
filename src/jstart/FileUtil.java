package jstart;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.function.Consumer;

public final class FileUtil {

    private FileUtil(){}

    public static String readAllText(File file) {
        StringBuilder sb = new StringBuilder();

        try (var fr = new FileReader(file);
                var br = new BufferedReader(fr)) {
            String line = null;

            while ((line = br.readLine()) != null)
                sb.append(line + System.lineSeparator());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return sb.toString();
    }

    public static String readAllText(String file) {
        return readAllText(new java.io.File(file));
    }

    public static String[] readAllLines(File file)
    {
        var lines = ColUtil.stringList();

        try (var fr = new FileReader(file);
                var br = new BufferedReader(fr)) {
            String line = null;

            while ((line = br.readLine()) != null)
                lines.add(line);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return lines.toArray(new String[lines.size()]);
    }

    public static void readAllLines(File file, Consumer<String> proc)
    {
        try (var fr = new FileReader(file);
                var br = new BufferedReader(fr)) {
            String line = null;

            while ((line = br.readLine()) != null)
                proc.accept(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String[] readAllLines(String file)
    {
        return readAllLines(new File(file));
    }

    public static void readAllLines(String file, Consumer<String> proc)
    {
        readAllLines(new File(file), proc);
    }

    private static void writeAllText(File file, String text, boolean append) {
        try (var fw = new FileWriter(file, append);
                var bw = new BufferedWriter(fw)) {
            if (append)
                bw.append(text);
            else
                bw.write(text);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void writeAllText(File file, String text) {
        writeAllText(file, text, false);
    }

    public static void writeAllText(String file, String text) {
        writeAllText(new java.io.File(file), text, false);
    }

    public static void appendTextFile(File file, String text) {
        writeAllText(file, text, true);
    }

    public static void appendTextFile(String file, String text) {
        writeAllText(new java.io.File(file), text, true);
    }
}
