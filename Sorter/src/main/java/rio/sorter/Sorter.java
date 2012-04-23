package rio.sorter;

import java.io.File;
import java.io.FileNotFoundException;

public class Sorter {
    
    private File input;
    private long[] longs;
    
    private Sorter(String[] args) {
        
        evaluateArgs(args);
        evaluateFile(args[0]);
    }
    
    private void evaluateArgs(String[] args) {
        
        if (args.length != 1) {
            System.out.println("Required parameters: [input file] (respectively)");
            System.exit(0);
        }
    }
    
    private void evaluateFile(String path) {
        
        input = new File(path);
        
        if (!input.exists()) {
            System.out.println("File \"" + input + "\" not found.");
            System.exit(0);
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
    
    public static void run(String[] args) {
        
        Sorter sorter = new Sorter(args);
        sorter.readFile();
        sorter.sort();
    } 
}