package rio.sorter;

import java.util.Arrays;
import static org.junit.Assert.*;
import org.junit.Test;

public class ConcurrentQuickSortTest {
    
    private ConcurrentQuickSort sorter;
    
    @Test
    public void sortEmptyArray() {
        
        long[] array = {};
        long[] expected = {};
        
        sorter = new ConcurrentQuickSort(array);
        sorter.sort();
        
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void sortArrayWithOneElement() {
        
        long[] array = {1L};
        long[] expected = {1L};
        
        sorter = new ConcurrentQuickSort(array);
        sorter.sort();
        
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void sortEvenSizedArray() {
        
        long[] array = {3L, 2L, 1L, 4L};
        long[] expected = {1L, 2L, 3L, 4L};
        
        sorter = new ConcurrentQuickSort(array);
        sorter.sort();
        
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void sortUnevenSizedArray() {
        
        long[] array = {0L, -2L, 4L};
        long[] expected = {-2L, 0L, 4L};
        
        sorter = new ConcurrentQuickSort(array);
        sorter.sort();
        
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void sortOrderedArray() {
        
        long[] array = {1L, 2L, 3L, 6L, 12L};
        long[] expected = {1L, 2L, 3L, 6L, 12L};
        
        sorter = new ConcurrentQuickSort(array);
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
        
        sorter = new ConcurrentQuickSort(array);
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
        
        sorter = new ConcurrentQuickSort(array);
        long startTime = System.nanoTime();
        sorter.sort();
        long elapsedTimeInMS = (System.nanoTime() - startTime) / 1000000;
        System.out.println("Quick: Elapsed time in MS: " + elapsedTimeInMS);
        sorter = null;
    }
    
    @Test
    public void testSpeedWithDifferentThresholds() {
        long[] randomArray = genRandomLongArray();


        for (int threshold = 0; threshold < 1000; threshold += 100) {
            testThreshold(randomArray, threshold);
        }

    }

    private static long[] genRandomLongArray() {
        int size = 50000000;
        long[] array = new long[size];
        for (int i = 0; i < size; i++) {
            array[i] = (long) (Math.random() * Long.MAX_VALUE);
        }
        return array;
    }

    private static void testThreshold(long[] randomArray, int threshold) {
        long[] times = new long[5];
        for (int i = 0; i < 5; i++) {
            long[] sortArray = Arrays.copyOf(randomArray, randomArray.length);
            ConcurrentQuickSort sorter = new ConcurrentQuickSort(sortArray, threshold);
            long startTime = System.nanoTime();
            sorter.sort();
            long elapsedTimeInMS = (System.nanoTime() - startTime) / 1000000;
            times[i] = elapsedTimeInMS;
        }
        Arrays.sort(times);
        System.out.println("Threshold: " + threshold + " Median time: " + times[2]);
    }
}
