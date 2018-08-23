package com.quizzard.domain;

public class QuizQuestion {
    private int id;
    private String text;
    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String optionFour;
    private int result = 0;


    public QuizQuestion(int id, String text, String optionOne, String optionTwo, String optionThree, String optionFour) {
        this.id = id;
        this.text = text;
        this.optionOne = optionOne;
        this.optionTwo = optionTwo;
        this.optionThree = optionThree;
        this.optionFour = optionFour;
    }

    public int getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public String getOptionOne() {
        return optionOne;
    }

    public String getOptionTwo() {
        return optionTwo;
    }

    public String getOptionThree() {
        return optionThree;
    }

    public String getOptionFour() {
        return optionFour;
    }

    public int getResult() {

        return result;
    }

    public void setResult(boolean correct) {
        if (correct) {
            result = 1;
        } else {
            result = 2;
        }
    }

    @Override
    public String toString() {
        return this.id + "\n" +
                this.text + "\n" +
                this.optionOne + "\n" +
                this.optionTwo + "\n" +
                this.optionThree + "\n" +
                this.optionFour;
    }

    public String getOptionOnNumber(int number) {
        switch (number) {
            case 1: return optionOne;
            case 2: return optionTwo;
            case 3: return optionThree;
            case 4: return optionFour;
        }
        return "";
    }
}
