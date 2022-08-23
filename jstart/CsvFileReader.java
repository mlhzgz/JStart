package jstart;

import java.io.File;
import java.util.function.Consumer;

public class CsvFileReader {

    private File file;
    private String separator;
    private boolean withHeader;
    private String[] fields;

    public CsvFileReader(File file, String separator, boolean withHeader) {
        this.file = file;
        this.separator = separator;
        this.withHeader = withHeader;
        this.fields = null;
    }

    public void readRecords(Consumer<CsvObject> proc) {
        Integer[] count = new Integer[] { 0 };

        FileUtil.readAllLines(file, l -> {
            String[] values = l.split(separator);

            // In the first line (reading header)
            if (withHeader && count[0] == 0)
            {
                fields = values;
            }
            else
            {
                var obj = new CsvObject(fields, values);
                proc.accept(obj);
            }

            count[0]++;
        });
    }

}
