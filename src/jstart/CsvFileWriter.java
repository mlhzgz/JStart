package jstart;

import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Opens a CSV file for writing (usable with try-with-resources)
 */
public class CsvFileWriter implements Closeable {

    private File file;
    private String separator;
    private String[] fields;
    private FileWriter fw;
    private BufferedWriter bw;

    /**
     * Open a CSV file
     * @param file file object
     * @throws IOException
     */
    public CsvFileWriter(File file) throws IOException {
        this(file, ";", null, false);
    }

    public CsvFileWriter(File file, String[] fields) throws IOException {
        this(file, ";", fields, false);
    }

    /**
     * Open a CSV file with configuration. If fields parameter contains data, 
     * it's writted to file
     * @param filename  the CSV file
     * @param separator string separator of fields and records
     * @param fields    fields names for header
     * @param append    file is open in append mode
     * @throws IOException
     */
    public CsvFileWriter(File filename, String separator, String[] fields, boolean append)
            throws IOException {
        this.file = filename;
        this.separator = separator;
        this.fields = fields;
        this.fw = new FileWriter(this.file, append);
        this.bw = new BufferedWriter(fw);

        if (this.fields != null && (!append || filename.length() == 0)) {
            bw.write(String.join(separator, this.fields));
            bw.newLine();
        }
    }

    /**
     * Open a CSV file
     * @param filename the CSV file
     * @throws IOException
     */
    public CsvFileWriter(String filename) throws IOException {
        this(new File(filename));
    }

    /**
     * Open a CSV file
     * @param filename the CSV file
     * @param fields header fields for file
     * @throws IOException
     */
    public CsvFileWriter(String filename, String[] fields) throws IOException {
        this(new File(filename), fields);
    }

    /**
     * Open a CSV file
     * @param filename  the CSV file
     * @param separator string separator of fields and records
     * @param fields    header fields for file
     * @param append    file is open in append mode
     * @throws IOException
     */
    public CsvFileWriter(String filename, String separator, String[] fields, boolean append)
            throws IOException {
        this(new File(filename), separator, fields, append);
    }

    /**
     * Writes a record object
     * @param data record data to save
     * @return true if writing is successful, otherwise false
     */
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

    /**
     * Writes and staring array in the file
     * @param data string array to save
     * @return true if writing is successful, otherwise false
     */
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
