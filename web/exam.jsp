<%-- 
    Document   : temp
    Created on : Dec 24, 2018, 4:02:19 PM
    Author     : Aya Essam
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="model.Question"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Exam Page</title>
        <script src="jquery-3.3.1.min.js"></script>
        <script>
            function skipQuestion(button){
                console.log(button.id);
                $('.' + button.id.toString()).hide();
            }
        </script>
    </head>
    <body>
        <h1>Welcome ${username}</h1>
         <% ArrayList<Question> questions = (ArrayList<Question>) request.getAttribute("listOfQuestions");
         int i=1;  
         for(Question q : questions){ 
            %>
                    <form name="questionform" action="">
                        <h3 id="username"> question <%=i%> : <br><h4><%= q.sentence %></h4></h3>
                        <input type="checkbox" name="answer" value="answer1"> <%= q.answers.get(0) %> <br>
                        <input type="checkbox" name="answer" value="answer2"> <%= q.answers.get(1) %> <br>
                        <input type="checkbox" name="answer" value="answer3"> <%= q.answers.get(2) %> <br>
                        <input type="checkbox" name="answer" value="answer4"> <%= q.answers.get(3) %> <br>
                        <input type="submit" value="skip" id="<%= q.question_id %>" onclick="skipQuestion(this)"><br>
                        mark question? <input type="checkbox" name="mark" value="mark" ><br>
                <h4>.................................................................................</h4>
            </form> 
        <%
            i++;
            }
         %>
         
         <h2><input type="submit" value="submit exam" style="display: block; margin: 0 auto;"></h2>

    </body>
</html>
