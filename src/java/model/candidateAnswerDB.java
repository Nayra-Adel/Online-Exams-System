/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author dell
 */
public class candidateAnswerDB {

    Connection connection;
    Statement statement;
    ResultSet rs;

    public candidateAnswerDB() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver());
        connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/online_exams_db", "root", "");
        statement = connection.createStatement();
        rs = null;
    }

    public void addCandidateAnswers(int candidateID, int examID, int QID, int answerID) {
        try {

            String query = "INSERT INTO candidate_answers (candidate_id, exam_id, question_id, answer_id) "
                    + "VALUES (?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, candidateID);
            preparedStatement.setInt(2, examID);
            preparedStatement.setInt(3, QID);
            preparedStatement.setInt(4, answerID);
            preparedStatement.executeUpdate();      
        } catch (SQLException ex) {
            System.out.println(ex);         
        }
    }

    public ResultSet getAnswerID(String Sentence) {
        try {
            String query = "SELECT answer_id FROM answer WHERE sentence = '" + Sentence + "'";

            rs = statement.executeQuery(query);

        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return rs;
    }

    public ResultSet getExamID(String examType) {
        try {
            String query = "SELECT exam_id FROM exam WHERE type = '" + examType + "'";

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

}
