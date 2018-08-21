package com.quizzard.domain;

import java.util.ArrayList;
import java.util.List;

public class QuizCollection {
    private List<QuizQuestion> questions = new ArrayList<>();
    private int currentQuestion = 0;

    public void addQuestion(QuizQuestion question){
        questions.add(question);
    }
    public QuizQuestion getQuestionOnNumber(int number){
        return questions.get(number-1);
    }
    public int getSize(){
        return questions.size();
    }
    public QuizQuestion getNextQuestion(){
        return questions.get(currentQuestion++);
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
