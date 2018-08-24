package com.quizzard.domain;

public class Statistics {
    private int correct;
    private int wrong;

    public Statistics(int correct, int wrong) {
        this.correct = correct;
        this.wrong = wrong;
    }

    public int getCorrect() {
        return correct;
    }

    public int getWrong() {
        return wrong;
    }

    public void increaseCorrect(int count) {
        this.correct += count;
    }

    public void increaseWrong(int count) {
        this.wrong += count;
    }
}
