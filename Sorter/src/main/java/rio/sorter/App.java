package rio.sorter;

import java.util.Arrays;

public class App {

    public static void main(String[] args) {
        
        int size = 1000;
        
        long[] array = new long[size];
        
        for (int i = 0; i < size; i++) {
            array[i] = i + 1;
        }
        
        // Shuffle
        for (int i = 0; i < size; i++) {
            int randomIndex = (int) (Math.random() * size);
            
            long swapWith = array[i];
            array[i] = array[randomIndex];
            array[randomIndex] = swapWith;
        }
        
        ConcurrentQuickSort sorter = new ConcurrentQuickSort(array);
        sorter.sort();
        
        System.out.println("Array: " + Arrays.toString(array));
    }
}