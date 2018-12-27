/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;

/**
 *
 * @author dell
 */
public class candidatePassedExam {

    Connection connection;
    Statement statement;
    ResultSet rs;

    public candidatePassedExam() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_exams_db", "root", "");
        statement = connection.createStatement();
        rs = null;
    }

    public boolean addCandidatePassInfo(int userID, int examID, Date ExamDate, int scoreExam) {
        try {

            String query = "INSERT INTO candidate_passed_exams (candidate_id, exam_id, exam_date, score)"
                    + "VALUES (" + userID + " , " + examID + " , " + ExamDate + " , " + scoreExam + ")";

            statement.executeUpdate(query);

            return true;

        } catch (SQLException ex) {
            System.out.println(ex);

            return false;
        }
    }

    public ResultSet getCandidateInfoByUsername(String username) {
        try {
            String query = "SELECT user.user_id,candidate_available_exams.exam_id, question.sentence, answer.sentence FROM candidate_answers"
                    + "INNER JOIN question ON candidate_answers.question_id = question.question_id "
                    + "INNER JOIN answer ON answer.answer_id = candidate_answers.answer_id"
                    + "INNER JOIN user ON user.user_id = candidate_answers.candidate_id"
                    + "INNER JOIN candidate_available_exams ON candidate_available_exams.exam_id = candidate_answers.exam_id "
                    + "WHERE user.username = '" + username + "'";
        
            rs = statement.executeQuery(query);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return rs;
    }

    public void closeConnection() throws SQLException {
        connection.close();
        statement.close();
        rs.close();
    }

    public int scoreExam() {
        int score = 0;
        try {
            String query = "SELECT right FROM question_answer "
                    + "INNER JOIN candidate_answers ON question_answer.answer_id = candidate_answers.answer_id "
                    + "WHERE question_answer.question_id = candidate_answers.question_id ";

            rs = statement.executeQuery(query);
            while (rs.next()) {
                boolean grade = rs.getBoolean("right");
                if (grade == true) {
                    score += 1;
                }
            }

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return score;

    }

}
