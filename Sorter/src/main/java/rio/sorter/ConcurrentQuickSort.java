package rio.sorter;

public class ConcurrentQuickSort {

    private final long[] array;

    public ConcurrentQuickSort(long[] array) {
        this.array = array;
    }

    public void sort() {
        
        final int maxDepth = (int) (Math.sqrt(Runtime.getRuntime().availableProcessors()));
        Thread sort = new Thread(new QuickSortTask(array, 0, array.length - 1, 1, maxDepth));
        sort.start();
        
        try {
            sort.join();
        } catch (InterruptedException ex) {
            System.out.println(ex);
        }
    }
}