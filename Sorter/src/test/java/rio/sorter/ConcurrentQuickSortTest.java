package rio.sorter;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

public class ConcurrentQuickSortTest {

    private String path = "/home/fs/kerola/rio_testdata/uint64-keys.bin";
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

            long startTime = System.currentTimeMillis();
            sorter.sort();
            long stopTime = System.currentTimeMillis();

            System.out.println("Elapsed time with 1M values: " + (stopTime - startTime) + "ms");


            assertArrayEquals(expected, array);
        }

    @Test
        public void sort5MValuesInRandomOrder() {

            int size = 5000000;

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

            long startTime = System.currentTimeMillis();
            sorter.sort();
            long stopTime = System.currentTimeMillis();

            System.out.println("Elapsed time with 5M values: " + (stopTime - startTime) + "ms");

            assertArrayEquals(expected, array);
        }

    @Test
        public void sortTestData() throws FileNotFoundException {

            File file = new File(path);
            LittleEndianReader reader = new LittleEndianReader(file);

            long[] array = reader.read();
            long[] expected = Arrays.copyOf(array, array.length);
            Arrays.sort(expected);

            sorter = new ConcurrentQuickSort(array);

            long startTime = System.currentTimeMillis();
            sorter.sort();
            long stopTime = System.currentTimeMillis();

            System.out.println("Elapsed time with test data: " + (stopTime - startTime) + "ms");

            assertArrayEquals(expected, array);
        }
}
