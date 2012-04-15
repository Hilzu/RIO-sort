package rio.sorter;

import jsr166y.RecursiveAction;

public class QuickSortTask extends RecursiveAction {

    private final long[] array;
    private int leftmostIndex;
    private int rightmostIndex;
    private final int threshold;

    public QuickSortTask(long[] array, int leftmostIndex, int rightmostIndex, int threshold) {
        this.array = array;
        this.leftmostIndex = leftmostIndex;
        this.rightmostIndex = rightmostIndex;
        this.threshold = threshold;
    }

    private static int partition(long[] array, int leftmostIndex, int rightmostIndex, int pivotIndex) {
        long pivotValue = array[pivotIndex];
        swapElements(array, pivotIndex, rightmostIndex);
        int newPivotIndex = leftmostIndex;
        for (int i = leftmostIndex; i < rightmostIndex; i++) {
            if (array[i] < pivotValue) {
                swapElements(array, i, newPivotIndex);
                newPivotIndex++;
            }
        }
        swapElements(array, newPivotIndex, rightmostIndex);

        return newPivotIndex;
    }

    private static void swapElements(long[] array, int index1, int index2) {
        long temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
    
    private static void insertionSort(long[] array, int start, int end) {
        for (int i = start; i <= end; i++) {
            long value = array[i];
            int j = i - 1;
            while (j >= 0 && array[j] > value) {
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = value;
        }
    }

    @Override
    protected void compute() {
        if (leftmostIndex + (rightmostIndex-leftmostIndex)/2 < threshold) {
            insertionSort(array, leftmostIndex, rightmostIndex);
        }
        if (leftmostIndex < rightmostIndex) {
            int pivotIndex = leftmostIndex + (rightmostIndex - leftmostIndex) / 2;
            pivotIndex = partition(array, leftmostIndex, rightmostIndex, pivotIndex);
            invokeAll(new QuickSortTask(array, leftmostIndex, pivotIndex, threshold),
                    new QuickSortTask(array, pivotIndex + 1, rightmostIndex, threshold));
            
        }

    }
}
