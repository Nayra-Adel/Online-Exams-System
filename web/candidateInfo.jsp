<%-- 
    Document   : candidateInfo
    Created on : Dec 24, 2018, 8:38:30 PM
    Author     : yyy
--%>

<%@page import="model.candidateExam"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    int candidate_id = Integer.parseInt(request.getParameter("candidate_id"));
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Welcome ${username}</h1>
        <% ArrayList<candidateExam> candExamList = (ArrayList<candidateExam>) request.getAttribute("listOfQuestions");
            int i = 1;
            for (candidateExam q : candExamList) {
        %>
        <form name="questionform" action="">
            <h3 id="username"> question <%=i%> : <br><h4><%= q.question%></h4></h3>
                <% ArrayList<String> candAnswer = q.candidateAnswer;
                    int j = 0;
                    while (j < candAnswer.size()) {

                %>
            <p> <%= candAnswer.get(j)%> </p><br>

            <%
                    j++;
                }
            %>
            <h4>.................................................................................</h4>
        </form> 
        <%
                i++;
            }
        %>

    </body>
</html>
