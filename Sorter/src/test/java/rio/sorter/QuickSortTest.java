package rio.sorter;

import static org.junit.Assert.*;
import org.junit.Test;

public class QuickSortTest {
    
    private QuickSort sorter;
    
    @Test
    public void sortEmptyArray() {
        
        long[] array = {};
        long[] expected = {};
        
        sorter = new QuickSort(array);
        sorter.sort();
        
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void sortArrayWithOneElement() {
        
        long[] array = {1L};
        long[] expected = {1L};
        
        sorter = new QuickSort(array);
        sorter.sort();
        
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void sortEvenSizedArray() {
        
        long[] array = {3L, 2L, 1L, 4L};
        long[] expected = {1L, 2L, 3L, 4L};
        
        sorter = new QuickSort(array);
        sorter.sort();
        
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void sortUnevenSizedArray() {
        
        long[] array = {0L, -2L, 4L};
        long[] expected = {-2L, 0L, 4L};
        
        sorter = new QuickSort(array);
        sorter.sort();
        
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void sortOrderedArray() {
        
        long[] array = {1L, 2L, 3L, 6L, 12L};
        long[] expected = {1L, 2L, 3L, 6L, 12L};
        
        sorter = new QuickSort(array);
        sorter.sort();
        
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void sort1MValuesInRandomOrder() {
        
        int size = 1000000;
        
        long[] array = new long[size];
        long[] expected = new long[size];
        
        for (int i = 0; i < size; i++) {
            array[i] = i + 1;
            expected[i] = i + 1;
        }
        
        // Shuffle
        for (int i = 0; i < size; i++) {
            int randomIndex = (int) (Math.random() * size);
            
            long swapWith = array[i];
            array[i] = array[randomIndex];
            array[randomIndex] = swapWith;
        }
        
        sorter = new QuickSort(array);
        sorter.sort();
        
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void canSort58Mvariables() {
        int size = 58000000;
        long[] array = new long[size];
        for (int i = 0; i < size; i++) {
            array[i] = (long) (Math.random() * Long.MAX_VALUE);
        }
        
        sorter = new QuickSort(array);
        long startTime = System.nanoTime();
        sorter.sort();
        long elapsedTimeInMS = (System.nanoTime() - startTime) / 1000000;
        System.out.println("Quick: Elapsed time in MS: " + elapsedTimeInMS);
    }
}
