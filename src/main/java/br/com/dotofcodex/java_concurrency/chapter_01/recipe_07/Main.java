package br.com.dotofcodex.java_concurrency.chapter_01.recipe_07;

import java.util.ArrayDeque;
import java.util.Deque;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Deque<Event> deque = new ArrayDeque<Event>();

        WriterTask writer = new WriterTask(deque);
        for (int i=0; i<3; i++) {
            Thread thread = new Thread(writer);
            thread.start();
        }

        CleanerTask cleaner = new CleanerTask(deque);
        cleaner.start();
    }
}
