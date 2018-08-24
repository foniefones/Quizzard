package com.quizzard.Repository;

import com.quizzard.domain.QuizCollection;
import com.quizzard.domain.QuizQuestion;
import com.quizzard.domain.Statistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import com.quizzard.domain.User;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class JDBCQuizRepository implements QuizRepository {

    @Qualifier("dataSource")
    @Autowired
    private DataSource dataSource;

    @Override
    public User login(String name, String password) {
 
        try (Connection conn = dataSource.getConnection();
             PreparedStatement ps = conn.prepareStatement("SELECT id, username, Mail FROM Users WHERE username=? AND Password=?")) {
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
    public void addStats(int id, int correct, int wrong) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("INSERT INTO [Academy_Projekt3].[dbo].[Statistics] ([userid], [correct], [wrong]) VALUES (?, ?, ?)")) {
            ps.setInt(1, id);
            ps.setInt(2, correct);
            ps.setInt(3, wrong);
            ps.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error adding stats");
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
    public String getAnswer(int id) {
        try(Connection conn = dataSource.getConnection();
            PreparedStatement ps = conn.prepareStatement("SELECT correctanswer FROM Questions WHERE id=?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            return rs.getString(1);
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



    @Override
    public Statistics getStats(int id) {
        try(Connection conn = dataSource.getConnection();
        PreparedStatement ps = conn.prepareStatement("SELECT correct, wrong FROM [Academy_Projekt3].[dbo].[Statistics] WHERE userid=?")) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            Statistics stats = new Statistics(0,0);
            while(rs.next()) {
                stats.increaseCorrect(rs.getInt("correct"));
                stats.increaseWrong(rs.getInt("wrong"));
            }
            return stats;

        } catch (Exception e) {

        }
        return null;
    }

    private User rsUser(ResultSet rs) throws SQLException {
        return new User(rs.getInt("id"),rs.getString("username"), rs.getString("Mail"));
    }

    private Statistics rsStats(ResultSet rs) throws SQLException {
        return new Statistics(rs.getInt("correct"), rs.getInt("wrong"));
    }

    private QuizQuestion rsQuizQuestion(ResultSet rs) throws SQLException {

        List<String> list = new ArrayList<>();
        list.add(rs.getString("alt_1"));
        list.add(rs.getString("alt_2"));
        list.add(rs.getString("alt_3"));
        list.add(rs.getString("correctAnswer"));

        Collections.shuffle(list);
        Collections.shuffle(list);
        Collections.shuffle(list);
        Collections.shuffle(list);
        Collections.shuffle(list);

        return new QuizQuestion(
                rs.getInt("id"),
                rs.getString("question"),
                list.get(0),
                list.get(1),
                list.get(2),
                list.get(3)

        );
    }
}
