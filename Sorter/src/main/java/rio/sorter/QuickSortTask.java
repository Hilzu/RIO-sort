package rio.sorter;

import java.util.concurrent.RecursiveAction;

public class QuickSortTask extends RecursiveAction {

    private final long[] array;
    private final int leftmostIndex;
    private final int rightmostIndex;
    private final int treshold;

    public QuickSortTask(long[] array, int leftmostIndex, int rightmostIndex, int treshold) {
        this.array = array;
        this.leftmostIndex = leftmostIndex;
        this.rightmostIndex = rightmostIndex;
        this.treshold = treshold;
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

    private void swapElements(int index1, int index2) {
        long temp = array[index1];
        array[index1] = array[index2];
        array[index2] = temp;
    }
    
    private void insertionSort(int start, int end) {
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
        if (leftmostIndex + (rightmostIndex-leftmostIndex)/2 < treshold) {
            insertionSort(leftmostIndex, rightmostIndex);
        }
        if (leftmostIndex < rightmostIndex) {
            int pivotIndex = leftmostIndex + (rightmostIndex - leftmostIndex) / 2;
            pivotIndex = partition(leftmostIndex, rightmostIndex, pivotIndex);
            invokeAll(new QuickSortTask(array, leftmostIndex, pivotIndex, treshold),
                    new QuickSortTask(array, pivotIndex + 1, rightmostIndex, treshold));
            
        }
    }
}
