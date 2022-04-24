package br.com.dotofcodex.java_concurrency.chapter_01.recipe_03;

public class Main {
    public static void main(String[] args) {
        /**
         * Thread que calcula os números primos
         */
        Thread task = new PrimeGenerator();
        task.start();

        try {
            Thread.sleep(5_000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * Invocação do método de interrupção da Thread PrimeGenerator
         * */
        task.interrupt();
    }
}
