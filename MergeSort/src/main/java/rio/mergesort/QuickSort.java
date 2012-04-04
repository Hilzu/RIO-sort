package rio.mergesort;

public class QuickSort {

    private final long[] array;

    public QuickSort(long[] array) {
        this.array = array;
    }

    public void sort() {
        quickSort(0, array.length - 1);
    }

    private void quickSort(int leftmostIndex, int rightmostIndex) {
        if (leftmostIndex < rightmostIndex) {
            int pivotIndex = choosePivotIndex(leftmostIndex, rightmostIndex);
            pivotIndex = partition(leftmostIndex, rightmostIndex, pivotIndex);
            quickSort(leftmostIndex, pivotIndex);
            quickSort(pivotIndex + 1, rightmostIndex);
        }
    }

    // left ≤ pivotIndex ≤ right
    private int choosePivotIndex(int leftmostIndex, int rightmostIndex) {
        return (rightmostIndex - leftmostIndex) / 2 + leftmostIndex;
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
}
