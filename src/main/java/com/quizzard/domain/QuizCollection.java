package com.quizzard.domain;

import java.util.ArrayList;
import java.util.List;

public class QuizCollection {
    private List<QuizQuestion> questions = new ArrayList<>();
    private int currentQuestion = 0;
    private int currentAlternative = 0;

    public void addQuestion(QuizQuestion question){
        questions.add(question);
    }
    public QuizQuestion getQuestionOnNumber(int number){
        return questions.get(number-1);
    }
    public int getSize(){
        return questions.size();
    }
    public int getCurrentQuestion() {
        return currentQuestion;
    }
    public QuizQuestion getNextQuestion(){
        return questions.get(currentQuestion -1);
    }

    public void incrementQuestion() {
        currentQuestion++;
    }

    public int getRight() {
        int right =0;
        for (QuizQuestion question: questions) {
            if (question.getResult() == 1) {
                right++;
            }
        }
        return right;
    }

    public int getWrong() {
        int wrong =0;
        for (QuizQuestion question: questions) {
            if (question.getResult() == 2) {
                wrong++;
            }
        }
        return wrong;
    }

    public List<QuizQuestion> getQuestions(boolean correct){
        List<QuizQuestion> correctList = new ArrayList<>();
        for(QuizQuestion question : questions){
            if(question.getResult()==1 && correct || question.getResult()==2 && !correct){
                correctList.add(question);
            }
        }
        return correctList;
    }
}
