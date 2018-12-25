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
public class examDB {
    Connection connection;
    Statement statement;
    ResultSet rs;
    
    public examDB() throws SQLException{
        DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
        connection = DriverManager.getConnection("jdbc:mysql://localhost:330/online_exams_db", "root", "root");
        statement = connection.createStatement();
        rs = null;
    }
    
    public ResultSet getAllExams(){
        try {
            String query = "SELECT * FROM exam";
            rs = statement.executeQuery(query);
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
        return rs;
    }
    
    public ResultSet getAvailableExamsByUsername(String username){
         try {
            String query = "SELECT user.user_id, exam.exam_id, exam.type, exam.pre_exam FROM candidate_available_exams "
                    + "INNER JOIN user ON candidate_available_exams.candidate_id = user.user_id "
                    + "INNER JOIN exam ON exam.exam_id = candidate_available_exams.exam_id "
                    + "WHERE user.username = '" + username + "'";
            
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
