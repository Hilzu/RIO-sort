package rio.sorter;

import java.util.Arrays;

public class QuickSortTask implements Runnable {

    private final long[] array;
    private final int leftmostIndex;
    private final int rightmostIndex;
    private final int depth;

    public QuickSortTask(long[] array, int leftmostIndex, int rightmostIndex, int depth) {
        this.array = array;
        this.leftmostIndex = leftmostIndex;
        this.rightmostIndex = rightmostIndex;
        this.depth = depth;
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

    @Override
    public void run() {
        System.out.println(depth);
        if (depth > Math.sqrt(Runtime.getRuntime().availableProcessors())) {
            Arrays.sort(array, leftmostIndex, rightmostIndex + 1);
            //insertionSort(leftmostIndex, rightmostIndex);
            return;
        }
        if (leftmostIndex < rightmostIndex) {
            
            int center = leftmostIndex + (rightmostIndex - leftmostIndex) / 2;
            
            int pivotIndex;
            if (array[leftmostIndex] > array[center]) {
                if (array[center] > array[rightmostIndex]) {
                    pivotIndex = center;
                } else if (array[leftmostIndex] > array[rightmostIndex]) {
                    pivotIndex = rightmostIndex;
                } else {
                    pivotIndex = leftmostIndex;
                }
            } else {
                if (array[leftmostIndex] > array[rightmostIndex]) {
                    pivotIndex = leftmostIndex;
                } else if (array[center] > array[rightmostIndex]) {
                    pivotIndex = rightmostIndex;
                } else {
                    pivotIndex = center;
                }
            }

            pivotIndex = partition(leftmostIndex, rightmostIndex, pivotIndex);
            
            Thread left = new Thread(new QuickSortTask(array, leftmostIndex, pivotIndex, depth + 1));
            Thread right = new Thread(new QuickSortTask(array, pivotIndex + 1, rightmostIndex, depth + 1));
            
            left.start();
            right.start();
            
            try {
                left.join();
                right.join();
            } catch (InterruptedException ex) {
                System.out.println(ex);
            }
        }
    }
}
