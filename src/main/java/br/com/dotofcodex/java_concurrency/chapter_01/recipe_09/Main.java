package br.com.dotofcodex.java_concurrency.chapter_01.recipe_09;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        rodarUnsafeTask();
        rodarSafeTask();
    }

    private static void rodarUnsafeTask() {
        UnsafeTask task = new UnsafeTask();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(task);
            thread.start();

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private static void rodarSafeTask() {
        SafeTask task = new SafeTask();
        for (int i = 0; i < 10; i++) {
            Thread thread = new Thread(task);
            thread.start();

            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
