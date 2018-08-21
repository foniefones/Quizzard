package com.quizzard.Repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;

@Component
public class JDBCQuizRepository implements QuizRepository {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    

}
