package com.quizzard;

public class QuizQuestion {
    private int id;
    private String text;
    private String optionOne;
    private String optionTwo;
    private String optionThree;
    private String optionFour;

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
}
