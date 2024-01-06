package br.com.dotofcodex.java_concurrency.chapter_02.recipe_06;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrintQueue {

    private final Lock queueLock = new ReentrantLock(true);

    public void printJob(Object document) {
        queueLock.lock();
        try {
            Long duration = (long) (Math.random() * 10_000);
            System.out.println(Thread.currentThread().getName() +
                ":PrintQueue: Printing a Job during " + (duration / 10_00) +
                " seconds");
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }

        queueLock.lock();

        try {
            Long duration = (long) (Math.random() * 10_000);
            System.out.println(Thread.currentThread().getName() +
                ":PrintQueue: Printing a Job during " + (duration / 10_00) +
                " seconds");
            Thread.sleep(duration);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            queueLock.unlock();
        }
    }

}
