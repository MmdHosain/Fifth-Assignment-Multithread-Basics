package sbu.cs;

import java.util.ArrayList;
import java.util.List;

public class TaskScheduler
{
    public static class Task implements Runnable
    {
        /*
            ------------------------- You don't need to modify this part of the code -------------------------
         */
        String taskName;
        int processingTime;

        public Task(String taskName, int processingTime) {
            this.taskName = taskName;
            this.processingTime = processingTime;
        }
        /*
            ------------------------- You don't need to modify this part of the code -------------------------
         */

        @Override
        public void run() {
            /*
            TODO
                Simulate utilizing CPU by sleeping the thread for the specified processingTime
             */
        }
    }

    public static ArrayList<String> doTasks(ArrayList<Task> tasks)
    {
        ArrayList<Task> sortedTasks = new ArrayList<>();
        ArrayList<String> finishedTasks = new ArrayList<>();

        sortedTasks.add(tasks.getFirst());
        tasks.removeFirst();
        while (!tasks.isEmpty()) {
            boolean x = true;
            for (int i = 0; i < sortedTasks.size(); i++) {
                if (tasks.getFirst().processingTime > sortedTasks.get(i).processingTime && x) {
                    sortedTasks.add(i, tasks.getFirst());
                    x = false;
                }
            }
            if (x){
                sortedTasks.addLast(tasks.getFirst());
            }
            tasks.removeFirst();
        }
        for (int i = 0; i < sortedTasks.size(); i++) {
            finishedTasks.add(sortedTasks.get(i).taskName);
        }
        return finishedTasks;
    }

    public static void main(String[] args) {
        // Test your code here
    }
}
