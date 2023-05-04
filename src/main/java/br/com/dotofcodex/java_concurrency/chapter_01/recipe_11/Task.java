package br.com.dotofcodex.java_concurrency.chapter_01.recipe_11;

import java.util.Random;

public class Task implements Runnable {
    @Override
    public void run() {
        int result = 0;
        Random random = new Random(Thread.currentThread().getId());
        while (true) {
            result = 1_000 / ((int) random.nextDouble() * 1_000);
            System.out.printf("%s : %f%n", Thread.currentThread().getId(), result);
            if (Thread.currentThread().isInterrupted()) {
                System.out.printf("%d : Interrupted%n", Thread.currentThread().getId());
                return;
            }
        }
    }
}
