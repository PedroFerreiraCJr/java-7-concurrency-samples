package br.com.dotofcodex.java_concurrency.chapter_01.recipe_04;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        FileSearch searcher = new FileSearch("C:\\", "autoexec.bat");
        Thread thread = new Thread(searcher);
        thread.start();

        try {
            TimeUnit.SECONDS.sleep(10);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }

        thread.interrupt();
    }
}
