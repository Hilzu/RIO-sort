package rio.sorter;

import java.util.logging.Level;
import java.util.logging.Logger;

public class ConcurrentQuickSort {

    private final long[] array;
    private final int threshold;
    private final WorkQueue workQueue;
    private final int thread_amount;

    public ConcurrentQuickSort(long[] array, int threshold) {
        thread_amount = 4;
        this.array = array;
        this.threshold = threshold;
        workQueue = new WorkQueue(thread_amount);
    }

    public ConcurrentQuickSort(long[] array) {
        this(array, 50);  // 50 seems best when tested with testSpeedWithDifferentThresholds()
    }

    public void sort() {
        workQueue.addJob(new Job(0, array.length - 1));
        QuickSorterThread[] threads = new QuickSorterThread[thread_amount];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new QuickSorterThread(array, 0, workQueue);
            threads[i].run();
        }
        try {
            synchronized (workQueue.allDone) {
                workQueue.allDone.wait();
            }
        } catch (InterruptedException ex) {
            System.out.println(ex);
            return;
        }
    }

    private void insertionSort() {
        for (int i = 0; i < array.length; i++) {
            long value = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > value) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = value;
        }
    }
}