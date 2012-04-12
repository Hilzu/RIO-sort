package rio.sorter;

import java.util.LinkedList;
import java.util.List;

public class WorkQueue {

    private final List<Job> jobs = new LinkedList<Job>();
    public final Object workAvailable = new Object();

    public void addJob(Job newJob) {
        synchronized (jobs) {
            jobs.add(newJob);
        }

        synchronized (workAvailable) {
            workAvailable.notify();
        }
    }

    public Job getNextJob() {
        synchronized (jobs) {
            return jobs.isEmpty() ? null : jobs.remove(0);
        }
    }
}
