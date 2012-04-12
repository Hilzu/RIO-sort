package rio.sorter;

public class ThreadPool implements Runnable {

    private final QuickSorterThread[] threads;

    public ThreadPool(int threadAmount, long[] array, int treshold, WorkQueue workQueue) {
        threads = new QuickSorterThread[threadAmount];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new QuickSorterThread(array, treshold, workQueue);
        }
    }

    @Override
    public void run() {
        for (int i = 0; i < threads.length; i++) {
            threads[i].run();
        }
    }
    
}
