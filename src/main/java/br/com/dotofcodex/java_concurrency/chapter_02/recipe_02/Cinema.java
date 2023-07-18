package br.com.dotofcodex.java_concurrency.chapter_02.recipe_02;

public class Cinema {
    private final Object controlCinema1;
    private final Object controlCinema2;

    private long vacanciesCinema1;
    private long vacanciesCinema2;

    public Cinema() {
        super();
        this.controlCinema1 = new Object();
        this.controlCinema2 = new Object();
        this.vacanciesCinema1 = 20;
        this.vacanciesCinema2 = 20;
    }

    public boolean sellTickets1(int number) {
        synchronized (controlCinema1) {
            if (number < vacanciesCinema1) {
                vacanciesCinema1 -= number;
                return true;
            }
            return false;
        }
    }

    public boolean sellTickets2(int number) {
        synchronized (controlCinema2) {
            if (number < vacanciesCinema2) {
                vacanciesCinema2 -= number;
                return true;
            }
            return false;
        }
    }

    public boolean returnTickets1(int number) {
        synchronized (controlCinema1) {
            vacanciesCinema1 += number;
            return true;
        }
    }

    public boolean returnTickets2(int number) {
        synchronized (controlCinema2) {
            vacanciesCinema2 += number;
            return true;
        }
    }

    public long getVacanciesCinema1() {
        return this.vacanciesCinema1;
    }

    public long getVacanciesCinema2() {
        return this.vacanciesCinema2;
    }
}
