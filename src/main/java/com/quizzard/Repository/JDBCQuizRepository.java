package com.quizzard.Repository;

import com.quizzard.domain.QuizCollection;
import com.quizzard.domain.QuizQuestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.quizzard.domain.User;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.List;

@Component
public class JDBCQuizRepository implements QuizRepository {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Override
    public User login(String name, String password) {
 
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT username, Mail FROM Users WHERE username=? AND Password=?")) {
            ps.setString(1, name);
            ps.setString(2, password);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next())
                    return rsUser(rs);
            }

        } catch (Exception e) {

        }
        return null;



    }

    @Override
    public boolean userExists(String name, String email) {
        try(Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT username, mail FROM Users WHERE username=? OR mail=?")) {
            ps.setString(1, name);
            ps.setString(2, email);
            try(ResultSet rs = ps.executeQuery()) {
                if(rs.next())
                    return true;
            }
        } catch (Exception e) {

        }
        return false;
    }

    @Override
    public void createNewUser(String name, String password, String email) {
        try(Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("INSERT INTO Users (username, password, mail) VALUES (?, ?, ?)")) {
            ps.setString(1,name);
            ps.setString(2,password);
            ps.setString(3,email);
            ps.executeUpdate();
        } catch (Exception e) {

        }
    }

    @Override
    public QuizQuestion getQuestion(int id) {
        try(Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT * FROM Questions WHERE id=?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rsQuizQuestion(rs);
        } catch (Exception e) {

        }
        return null;
    }

    @Override
    public int getQuestionSize() {
        try(Connection conn = dataSource.getConnection();
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery("SELECT COUNT(*) AS rows FROM Questions")) {
            rs.next();
            return rs.getInt("rows");
        } catch (Exception e) {

        }
        return -1;
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

    private User rsUser(ResultSet rs) throws SQLException {
        return new User(rs.getString("username"), rs.getString("Mail"));
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
