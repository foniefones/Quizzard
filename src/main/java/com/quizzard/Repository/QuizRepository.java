package com.quizzard.Repository;

import com.quizzard.domain.QuizCollection;
import com.quizzard.domain.QuizQuestion;
import com.quizzard.domain.User;

public interface QuizRepository {
    User login(String name, String password);
    boolean userExists(String name, String email);
    void createNewUser(String name, String password, String email);
    QuizCollection getQuestions();
    int getQuestionSize();
    QuizQuestion getQuestion(int id);
}
