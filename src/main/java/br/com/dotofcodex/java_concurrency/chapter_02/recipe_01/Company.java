package br.com.dotofcodex.java_concurrency.chapter_02.recipe_01;

public class Company implements Runnable {
    private Account account;

    public Company(Account account) {
        this.account = account;
    }

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            this.account.addAmount(1_000);
        }
    }
}
