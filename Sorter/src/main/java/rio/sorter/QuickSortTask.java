package rio.sorter;

import java.util.Arrays;

public class QuickSortTask implements Runnable {

    private final long[] array;
    private final int leftmostIndex;
    private final int rightmostIndex;
    private final int currentDepth;
    private final int maxDepth;

    public QuickSortTask(long[] array, int leftmostIndex, int rightmostIndex, int currentDepth, int maxDepth) {
        
        this.array = array;
        this.leftmostIndex = leftmostIndex;
        this.rightmostIndex = rightmostIndex;
        this.currentDepth = currentDepth;
        this.maxDepth = maxDepth;
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
        
        if (currentDepth > maxDepth) {
            Arrays.sort(array, leftmostIndex, rightmostIndex + 1);
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
            
            Thread left = new Thread(new QuickSortTask(array, leftmostIndex, pivotIndex, currentDepth + 1, maxDepth));
            Thread right = new Thread(new QuickSortTask(array, pivotIndex + 1, rightmostIndex, currentDepth + 1, maxDepth));
            
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
