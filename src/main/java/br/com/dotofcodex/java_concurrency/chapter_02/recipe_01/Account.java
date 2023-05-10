package br.com.dotofcodex.java_concurrency.chapter_02.recipe_01;

public class Account {
    private double balance;

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getBalance() {
        return this.balance;
    }

    public synchronized void addAmount(double amount) {
        double temp = this.balance;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        temp += amount;
        this.balance = temp;
    }

    public synchronized void subtractAmount(double amount) {
        double temp = this.balance;
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        temp -= amount;
        this.balance = temp;
    }
}
