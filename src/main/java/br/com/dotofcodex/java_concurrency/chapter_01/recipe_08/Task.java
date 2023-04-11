package br.com.dotofcodex.java_concurrency.chapter_01.recipe_08;

public class Task implements Runnable {
    @Override
    public void run() {
        int number = Integer.parseInt("valor não numérico");
    }
}
