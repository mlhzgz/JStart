package jstart;

import java.io.BufferedReader;
import java.io.Closeable;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * Opens a CSV file for reading (usable with try-with-resources)
 */
public class CsvFileReader implements Closeable {

    private File file;
    private String separator;
    private boolean withHeader;
    private String[] fields;
    private FileReader fr;
    private BufferedReader br;

    /**
     * Open a CSV file
     * 
     * @param file file object
     * @throws IOException
     */
    public CsvFileReader(File file) throws IOException {
        this(file, ";", true);
    }

    /**
     * Open a CSV file with configuration. If the file exists, try read the first
     * line for fields
     * 
     * @param file       the CSV file
     * @param separator  string separator of fields and records
     * @param withHeader indicates if the file contains a line with fields
     * @throws IOException
     */
    public CsvFileReader(File file, String separator, boolean withHeader) throws IOException {
        this.file = file;
        this.separator = separator;
        this.withHeader = withHeader;
        this.fields = null;

        if (file.exists()) {
            fr = new FileReader(this.file);
            br = new BufferedReader(fr);

            // Open the file to read the header with the fields of records
            if (this.withHeader) {
                String lineFields = br.readLine();

                if (lineFields != null) {
                    fields = lineFields.split(separator);
                }
            }
        }
    }

    /**
     * Open a CSV file
     * 
     * @param filename file object
     * @throws IOException
     */
    public CsvFileReader(String filename) throws IOException {
        this(new File(filename));
    }

    /**
     * Open a CSV file with configuration. If the file exists, try read the first
     * line for fields
     * 
     * @param filename   the CSV file
     * @param separator  string separator of fields and records
     * @param withHeader indicates if the file contains a line with fields
     * @throws IOException
     */
    public CsvFileReader(String filename, String separator, boolean withHeader) throws IOException {
        this(new File(filename), separator, withHeader);
    }

    /**
     * Reads a record in the CSV file and returns an CsvObject
     * 
     * @return a CsvObject with data from file
     */
    public CsvObject getNextRecord() {
        try {
            String line = br.readLine();

            if (fields != null && fields.length > 0)
                return new CsvObject(fields, line.split(separator, fields.length));
            else
                return new CsvObject(fields, line.split(separator));
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * Closes the file
     */
    @Override
    public void close() throws IOException {
        br.close();
        fr.close();
    }

}
