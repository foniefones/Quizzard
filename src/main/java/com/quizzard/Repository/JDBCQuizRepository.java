package com.quizzard.Repository;

import com.quizzard.domain.User;
import org.springframework.stereotype.Component;

@Component
public class JDBCQuizRepository implements QuizRepository {

    @Override
    public User login(String name, String password) {
        if(name.equals("hej"))
            return new User("hej", "mail");
        else
            return null;
    }
}
