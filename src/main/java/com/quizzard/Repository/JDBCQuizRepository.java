package com.quizzard.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.quizzard.domain.User;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;

@Component
public class JDBCQuizRepository implements QuizRepository {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Override
    public User login(String name, String password) {
        if(name.equals("hej"))
            return new User("hej", "mail");
        else
            return null;
    }
}
