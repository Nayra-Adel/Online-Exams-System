<%-- 
    Document   : candidateProfilePage
    Created on : Dec 19, 2018, 11:52:34 PM
    Author     : yyy
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="model.examDB"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="model.userDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    String username = request.getSession().getAttribute("username").toString();
    
    examDB exam = new examDB();
    ResultSet examsRS = exam.getAvailableExamsByUsername(username);
    
    int counter = 0; 
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Candidate Profile</title>
    </head>
    <body>
        <form name="logout-form" action="logout" method="POST">
            <input type="submit" value="logout" name="logout"/>
        </form>
        <h1>Welcome ${username}</h1>
        
        <h2>.................................Available Exams.................................</h2>
        <% while(examsRS.next()){ %>
        
        <form action="createExam" method="POST">
           <%= examsRS.getString("type") %> 
           
           <input type="text"  name="userID" hidden="true" value="<%= examsRS.getInt("user_id") %>"/>
           <input type="text"  name="examID" hidden="true" value="<%=  examsRS.getInt("exam_id") %>"/>
           <input type="text"  name="preExamID" hidden="true" value="<%=  examsRS.getInt("pre_exam") %>"/>
           <input type="text"  name="examType" hidden="true" value="<%= examsRS.getString("type")  %>"/>
                
           <input type="submit" name="solveExam" value="solve"/>
        </form>
        
        <% } exam.closeConnection();%>
    </body>
</html>
