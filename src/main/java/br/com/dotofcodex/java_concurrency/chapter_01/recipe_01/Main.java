package br.com.dotofcodex.java_concurrency.chapter_01.recipe_01;

public class Main {

	public static void main(String[] args) {
		for (int i = 1; i <= 10; i++) {
			Calculator calculator = new Calculator(i);
			Thread thread = new Thread(calculator);
			thread.start();
		}
	}
}
