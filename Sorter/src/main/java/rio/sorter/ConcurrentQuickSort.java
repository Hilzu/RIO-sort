package rio.sorter;

import jsr166y.ForkJoinPool;

public class ConcurrentQuickSort {

    private final long[] array;
    private final ForkJoinPool pool;
    public final int threshold;

    public ConcurrentQuickSort(long[] array, int threshold) {
        this.threshold = threshold;
        this.array = array;
        pool = new ForkJoinPool();
    }

    public ConcurrentQuickSort(long[] array) {
        this(array, 30);  // 50 seems best when tested with testSpeedWithDifferentThresholds()
    }

    public void sort() {
        pool.invoke(new QuickSortTask(array, 0, array.length - 1, threshold));
    }
}