package rio.sorter;

public class ConcurrentQuickSort {

    private final long[] array;
    private final int treshold;

    public ConcurrentQuickSort(long[] array, int treshold) {
        this.array = array;
        this.treshold = treshold;
    }

    public ConcurrentQuickSort(long[] array) {
        this(array, 50);  // 50 seems best when tested with testSpeedWithDifferentTresholds()
    }

    public void sort() {
        Thread t = new Thread(new QuickSortAlgo(0, array.length - 1));
        t.start();
        insertionSort();
    }

    private void swapElements(int index1, int index2) {
        long temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
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

    private class QuickSortAlgo implements Runnable {

        private int leftmostIndex;
        private int rightmostIndex;

        public QuickSortAlgo(int leftmostIndex, int rightmostIndex) {
            this.leftmostIndex = leftmostIndex;
            this.rightmostIndex = rightmostIndex;
        }

        @Override
        public void run() {
            if (rightmostIndex - leftmostIndex < treshold) {
                return;
            }
            if (leftmostIndex < rightmostIndex) {
                int pivotIndex = leftmostIndex + (rightmostIndex - leftmostIndex) / 2;
                pivotIndex = partition(leftmostIndex, rightmostIndex, pivotIndex);
                Thread left = new Thread(new QuickSortAlgo(leftmostIndex, pivotIndex));
                left.start();
                Thread right = new Thread(new QuickSortAlgo(pivotIndex + 1, rightmostIndex));
                right.start();
            }
        }

        private int partition(int leftmostIndex, int rightmostIndex, int pivotIndex) {
            long pivotValue = array[pivotIndex];
            swapElements(pivotIndex, rightmostIndex);
            int newPivotIndex = leftmostIndex;
            for (int i = leftmostIndex; i < rightmostIndex; i++) {
                if (array[i] < pivotValue) {
                    swapElements(i, newPivotIndex);
                    newPivotIndex++;
                }
            }
            swapElements(newPivotIndex, rightmostIndex);

            return newPivotIndex;
        }
    }
}