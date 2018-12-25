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

/**
 *
 * @author yyy
 */
public class questionDB {
    Connection connection;
    Statement statement;
    ResultSet rs;
    
    public questionDB() throws SQLException{
        DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
        connection = DriverManager.getConnection("jdbc:mysql://localhost:330/online_exams_db", "root", "root");
        statement = connection.createStatement();
        rs = null;
    }
    
    public ResultSet getQuestionsByExamType(String exam_type){
        try {
            String query = "SELECT * FROM question WHERE exam_type = " + exam_type;
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
