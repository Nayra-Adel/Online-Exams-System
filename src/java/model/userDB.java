/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;
import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.util.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author yyy
 */
public class userDB {
    Connection connection;
    Statement statement;
    ResultSet rs;

    public userDB() throws SQLException {
        DriverManager.registerDriver(new com.mysql.jdbc.Driver ());
        connection = DriverManager.getConnection("jdbc:mysql://localhost:330/online_exams_db", "root", "root");
        statement = connection.createStatement();
        rs = null;
    }
    
    public boolean addUser(String username, String email, String password, String cv, String telephone, byte[] cvFileByteArray){
            try {
                String query = "INSERT INTO user (username, password, email, telephone, cv, is_hr, approved, cv_file) " +
                               "VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                preparedStatement.setString(1, username);
                preparedStatement.setString(2, password);
                preparedStatement.setString(3, email);
                preparedStatement.setString(4, telephone);
                preparedStatement.setString(5, cv);
                preparedStatement.setBoolean(6, false);
                preparedStatement.setBoolean(7, false);
                preparedStatement.setBinaryStream(8, new ByteArrayInputStream(cvFileByteArray), cvFileByteArray.length);
                preparedStatement.executeUpdate();
                
                return true;
            } catch (SQLException ex) {
                System.out.println(ex);
                
                return false;
            }
    }

    
    public ResultSet getUserByUsername(String username){
        
        try {
            String query = "SELECT * FROM user WHERE username = '" +username +"'";
            System.err.println(query);

            rs = statement.executeQuery(query);
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
         return rs;
    }
    
    public ResultSet getUserByEmail(String email){
        try {
            String query = "SELECT * FROM user WHERE email = '" +email +"'";

            rs = statement.executeQuery(query);
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
         return rs;
    }
    
    public ResultSet getUserByAvailableExamType(String examType){
        try {
            String query = "SELECT * "
                    + "FROM user "
                    + "INNER JOIN candidate_available_exams ON candidate_available_exams.candidate_id = user.user_id "
                    + "INNER JOIN exam ON exam.exam_id = candidate_available_exams.exam_id "          
                    + "WHERE exam.type = '" +examType +"'";
          
            rs = statement.executeQuery(query);
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
         return rs;
    }
    
    public ResultSet getUserByPassedExamType(String examType){
        try {
            String query = "SELECT * "
                    + "FROM user "
                    + "INNER JOIN candidate_passed_exams ON candidate_passed_exams.candidate_id = user.user_id "
                    + "INNER JOIN exam ON exam.exam_id = candidate_passed_exams.exam_id "          
                    + "WHERE exam.type = '" +examType +"'";
          
            rs = statement.executeQuery(query);
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
         return rs;
    }
    
    
    public ResultSet getUserByExamDate(String examDate){

        try {
            String query = "SELECT * "
                    + "FROM user "
                    + "INNER JOIN candidate_passed_exams ON candidate_passed_exams.candidate_id = user.user_id "         
                    + "WHERE candidate_passed_exams.exam_date = '" +examDate +"'";
          
            rs = statement.executeQuery(query);
            
        } catch (SQLException ex) {
            System.out.println(ex);
        }
         return rs;
        
               //sdf.format(date) 
    }
    
    
    public ResultSet getAllNonApprovedCandidates(){
            try {
                String query = "SELECT * FROM user WHERE is_hr = 0 AND approved = 0";
                rs = statement.executeQuery(query);
                
            } catch (SQLException ex) {
               System.out.println(ex);
            }
            return rs;
    }
    
    public boolean acceptCandidate(int userID){
        try {
            String query = "UPDATE user SET approved = 1 where user_id = " + userID;
            statement.executeUpdate(query);

            return true;
        } catch (SQLException ex) {
            System.out.println(ex);

            return false;
        }
    }
    
    public boolean deleteCandidate(int userID){
       try {
            String query = "DELETE FROM user where user_id = "+userID;
            statement.executeUpdate(query);
                
            return true;
        } catch (SQLException ex) {
            System.out.println(ex);
                
            return false;
        } 
    }
    
    public boolean addAvailableExams(int userID, String[] exams){
       try {
           for(int i = 0 ; i < exams.length ; i++){
               
             String query = "INSERT INTO candidate_available_exams (candidate_id, exam_id)" +
                               "VALUES ("+userID+" , "+Integer.parseInt(exams[i])+")";
                
            statement.executeUpdate(query);  
           }            
            return true;
            
        } catch (SQLException ex) {
            System.out.println(ex);
                
            return false;
        }  
    }
    
    public void closeConnection() throws SQLException{
        connection.close();
        statement.close();
        rs.close();
    }
        
}
