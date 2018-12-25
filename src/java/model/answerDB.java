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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yyy
 */
public class answerDB {
    Connection connection;
    Statement statement;
    ResultSet rs;
    
    public answerDB() throws SQLException{
        DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
        connection = DriverManager.getConnection("jdbc:mysql://localhost:330/online_exams_db", "root", "root");
        statement = connection.createStatement();
        rs = null;
    }
    
    public ResultSet getRightAnswers(int question_id){
        try {
            String query = "SELECT * FROM question_answer"
                    + "INNER JOIN answer ON question_answer.answer_id = answer.answer_id "
                    + "WHERE question_id = " + question_id + " AND right = " + 1;
            
            rs = statement.executeQuery(query);
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return rs;
    }
    
    public ResultSet getWrongAnswers(int question_id){
        try {
            String query = "SELECT * FROM question_answer"
                    + "INNER JOIN answer ON question_answer.answer_id = answer.answer_id "
                    + "WHERE question_id = " + question_id + " AND right = " + 0;
            
            rs = statement.executeQuery(query);
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return rs;
    }
    
    public void closeConnection() throws SQLException{
        connection.close();
        statement.close();
        rs.close();
    }
}
