package br.com.dotofcodex.java_concurrency.chapter_02.recipe_01;

public class Bank implements Runnable {
    private final Account account;

    public Bank(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            this.account.subtractAmount(1_000);
        }
    }
}
