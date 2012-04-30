package rio.sorter;

import java.io.File;
import java.io.FileNotFoundException;


public class Sorter {

    private File input;
    private File output;
    private int outputAmount;
    private Printer printer;
    private long[] longs;


    private Sorter(String[] args) {

        evaluateArgs(args);
        evaluateInput(args[0]);
        evaluateOutput(args[1], args[2]);
    }

    private void evaluateArgs(String[] args) {

        if (args.length != 3) {
            System.out.println("Required parameters: [input file] [output file] [output amount] (respectively)");
            System.exit(0);
        }
    }

    private void evaluateInput(String path) {

        input = new File(path);

        if (!input.exists()) {
            System.out.println("File \"" + input + "\" not found.");
            System.exit(0);
        }
    }
    
    private void evaluateOutput(String path, String amountParameter) {
        
        try {
            outputAmount = Integer.parseInt(amountParameter); 
        } catch (NumberFormatException exception) {
            System.out.println("Third parameter must be a number.");
            System.exit(0);
        }
        
        output = new File(path);
        
        try {
            printer = new Printer(output);
        } catch (FileNotFoundException exception) {
            System.out.println("Captain overboard!");
            exception.printStackTrace();
        }
    }

    public void readFile() {

        LittleEndianReader reader = null;

        try {
            reader = new LittleEndianReader(input);
        } catch (FileNotFoundException exception) {
            System.out.println("Captain overboard!");
            exception.printStackTrace();
        }

        System.out.println("Reading file \"" + input + "\"...");

        long startTime = System.currentTimeMillis();
        longs = reader.read();

        System.out.println("Read file in " + (System.currentTimeMillis() - startTime) + "ms.");
    }

    public void sort() {

        ConcurrentQuickSort sorter = new ConcurrentQuickSort(longs);

        System.out.println("Sorting longs...");

        long startTime = System.currentTimeMillis();
        sorter.sort();

        System.out.println("Sorted longs in " + (System.currentTimeMillis() - startTime + "ms."));
    }
    
    public void print() {
        
        System.out.println("Writing output...");
        
        long startTime = System.currentTimeMillis();
        printer.print(longs, outputAmount);
        
        System.out.println("Wrote output in " + (System.currentTimeMillis() - startTime) + "ms to \"" + output + "\"...");
    }
    
    public long[] getLongs() {
        return longs;
    }

    public static void run(String[] args) throws FileNotFoundException {

        Sorter sorter = new Sorter(args);
        sorter.readFile();
        sorter.sort();
        sorter.print();
    }
}