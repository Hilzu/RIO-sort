package rio.sorter;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class WorkQueue {

    private final List<Job> jobs;
    public final Object workAvailable;
    public final Object allDone;
    private final Map<String, Integer> variables;
    private final String waiting;
    private int threads;
    private boolean done;

    public WorkQueue(int threads) {
        this.threads = threads;
        waiting = "WAITING";
        variables = new HashMap<String, Integer>();
        variables.put(waiting, 0);
        jobs = new LinkedList<Job>();
        workAvailable = new Object();
        allDone = new Object();
        done = false;
    }

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

    public void addWaitingThread() {
        int waitAmount;
        synchronized (variables) {
            waitAmount = variables.get(waiting);
            waitAmount++;
            variables.put(waiting, waitAmount);
        }
        if (waitAmount >= threads) {
            done = true;
            synchronized (workAvailable) {
                workAvailable.notifyAll();
            }
            synchronized(allDone) {
                allDone.notifyAll();
            }
        }
    }

    public void removeWaitingThread() {
        synchronized (variables) {
            int waitAmount = variables.get(waiting);
            waitAmount--;
            variables.put(waiting, waitAmount);
        }
    }

    public boolean isDone() {
        return done;
    }
}
