package rio.sorter;

public class Job {

    private final int leftmostIndex;
    private final int rightmostIndex;

    public Job(int leftmostIndex, int rightmostIndex) {
        this.leftmostIndex = leftmostIndex;
        this.rightmostIndex = rightmostIndex;
    }

    public int getLeftmostIndex() {
        return leftmostIndex;
    }

    public int getRightmostIndex() {
        return rightmostIndex;
    }
}
