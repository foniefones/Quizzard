package com.quizzard.Repository;

import com.quizzard.QuizCollection;
import com.quizzard.QuizQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.quizzard.domain.User;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

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

    @Override
    public QuizCollection getQuestions() {
        try (Connection conn = dataSource.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT id, question, alt_1, alt_2, alt_3, correctAnswer FROM questions")) {
            QuizCollection qz = new QuizCollection();
            while (rs.next()) {
                qz.addQuestion(rsQuizQuestion(rs));
            }
            return qz;
        } catch (Exception e) {

        }
        return null;
    }

    private QuizQuestion rsQuizQuestion(ResultSet rs) throws SQLException {
        return new QuizQuestion(
                rs.getInt("id"),
                rs.getString("question"),
                rs.getString("alt_1"),
                rs.getString("alt_2"),
                rs.getString("alt_3"),
                rs.getString("correctAnswer")
        );
    }
}
