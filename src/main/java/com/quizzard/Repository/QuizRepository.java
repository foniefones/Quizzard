package com.quizzard.Repository;

import com.quizzard.domain.QuizCollection;
import com.quizzard.domain.User;

public interface QuizRepository {
    User login(String name, String password);
    QuizCollection getQuestions();
}
