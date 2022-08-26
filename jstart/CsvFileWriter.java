package jstart;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class CsvFileWriter implements Closeable {

    private File file;
    private String separator;
    private String[] fields;
    private FileWriter fw;
    private BufferedWriter bw;

    public CsvFileWriter(File file) throws IOException {
        this(file, ";", null, false);
    }

    public CsvFileWriter(File file, String[] fields) throws IOException {
        this(file, ";", fields, false);
    }

    public CsvFileWriter(File file, String separator, String[] fields, boolean append)
            throws IOException {
        this.file = file;
        this.separator = separator;
        this.fields = fields;
        this.fw = new FileWriter(this.file, append);
        this.bw = new BufferedWriter(fw);

        if (this.fields != null && (!append || file.length() == 0)) {
            bw.write(String.join(separator, this.fields));
            bw.newLine();
        }
    }

    public CsvFileWriter(String filename) throws IOException {
        this(new File(filename));
    }

    public CsvFileWriter(String filename, String[] fields) throws IOException {
        this(new File(filename), fields);
    }

    public CsvFileWriter(String file, String separator, boolean withHeader, String[] fields, boolean append)
            throws IOException {
        this(new File(file), separator, fields, append);
    }

    public boolean writeRecord(CsvObject data) {
        boolean success = true;

        if (data.size() != fields.length)
            success = false;
        else {
            try {
                bw.write(data.toString());
                bw.newLine();
            } catch (IOException e) {
                success = false;
            }
        }

        return success;
    }

    public boolean writeArray(String[] data)
    {
        boolean success = true;

        if (data.length != fields.length)
            success = false;
        else
        {
            try {
                bw.write(String.join(separator, data));
                bw.newLine();
            } catch (IOException e) {
                success = false;
            }
        }

        return success;
    }

    /**
     * Closes the file
     */
    @Override
    public void close() throws IOException {
        bw.close();
        fw.close();
    }

}
