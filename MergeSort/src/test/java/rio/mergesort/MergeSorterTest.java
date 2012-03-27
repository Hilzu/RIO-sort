package rio.mergesort;

import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class MergeSorterTest {
    
    private MergeSorter sorter;
    
    @Test
    public void sortEmptyArray() {
        
        long[] array = {};
        long[] expected = {};
        
        sorter = new MergeSorter(array);
        sorter.sort();
        
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void sortArrayWithOneElement() {
        
        long[] array = {1L};
        long[] expected = {1L};
        
        sorter = new MergeSorter(array);
        sorter.sort();
        
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void sortEvenSizedArray() {
        
        long[] array = {3L, 2L, 1L, 4L};
        long[] expected = {1L, 2L, 3L, 4L};
        
        sorter = new MergeSorter(array);
        sorter.sort();
        
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void sortUnevenSizedArray() {
        
        long[] array = {0L, -2L, 4L};
        long[] expected = {-2L, 0L, 4L};
        
        sorter = new MergeSorter(array);
        sorter.sort();
        
        assertArrayEquals(expected, array);
    }
    
    @Test
    public void sortOrderedArray() {
        
        long[] array = {1L, 2L, 3L, 6L, 12L};
        long[] expected = {1L, 2L, 3L, 6L, 12L};
        
        sorter = new MergeSorter(array);
        sorter.sort();
        
        assertArrayEquals(expected, array);
    }
}
