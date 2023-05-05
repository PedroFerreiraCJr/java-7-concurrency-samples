package br.com.dotofcodex.java_concurrency.chapter_01.recipe_12;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ThreadFactory;

public class MyThreadFactory implements ThreadFactory {
    private int counter;
    private String name;
    private List<String> stats;

    public MyThreadFactory(String name) {
        super();
        this.counter = 0;
        this.name = name;
        this.stats = new ArrayList<String>();
    }

    @Override
    public Thread newThread(Runnable r) {
        Thread t = new Thread(r, this.name + "-Thread_" + this.counter);
        this.counter++;
        stats.add(String.format("Created thread %d with name %s on %s%n",
                t.getId(), t.getName(), new Date()));
        return t;
    }

    public String getStatistics() {
        StringBuffer buffer = new StringBuffer();
        Iterator<String> it = this.stats.iterator();

        while (it.hasNext()) {
            buffer.append(it.next());
            buffer.append("\n");
        }

        return buffer.toString();
    }
}
