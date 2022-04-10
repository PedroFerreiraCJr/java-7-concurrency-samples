package br.com.dotofcodex.java_concurrency.chapter_01.recipe_01;

/**
 * Programa principal que calcula a tabuada dos números de 1 até 10 em múltiplas threads
 */
public class Main {
    public static void main(String[] args) {
        for (int i = 1; i <= 10; i++) {
            Calculator calculator = new Calculator(i);
            Thread thread = new Thread(calculator);
            thread.start();
        }
    }
}
