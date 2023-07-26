package br.com.dotofcodex.java_concurrency.chapter_02.recipe_03;

import java.util.Date;
import java.util.LinkedList;

public class EventStorage {
    private int maxSize;
    private LinkedList<Date> storage;

    public EventStorage() {
        super();
        this.maxSize = 10;
        this.storage = new LinkedList<>();
    }

    public synchronized void set() {
        while (this.storage.size() == maxSize) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        this.storage.offer(new Date());
        System.out.printf("Set: %d", storage.size());
        notifyAll();
    }

    public synchronized void get() {
        while (storage.size() == 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.printf("Get: %d: %s", storage.size(), ((LinkedList<?>) storage).poll());
        notifyAll();
    }
}
