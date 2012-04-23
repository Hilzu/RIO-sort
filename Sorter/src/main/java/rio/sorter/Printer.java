package rio.sorter;

import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Printer {

    private PrintWriter printer;

    public Printer() throws FileNotFoundException {
        printer = new PrintWriter("result.txt");
    }

    public void print(long[] longs, int amount) {
        for (int i = 0; i < amount; i++) {
            printer.println(longs[i]);
        }
        printer.close();
    }
}
