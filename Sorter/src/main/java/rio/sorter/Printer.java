package rio.sorter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Printer {

    private PrintWriter printer;

    public Printer(File file) throws FileNotFoundException {
        printer = new PrintWriter(file);
    }

    public void print(long[] longs, int amount) {
        
        for (int i = 0; i < amount; i++) {
            printer.println(longs[i]);
        }
        
        printer.close();
    }
}
