package br.com.dotofcodex.java_concurrency.chapter_01.recipe_02;

/**
 * Tarefa para ser executada em uma Thread que calcula a tabela de multiplicação de um número qualquer
 */
public class Calculator implements Runnable {

    private final int number;

    public Calculator(int number) {
        this.number = number;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 10; i++) {
            System.out.printf("%s: %d * %d = %d\n", Thread.currentThread().getName(), number, i, i * number);
        }
    }
}
