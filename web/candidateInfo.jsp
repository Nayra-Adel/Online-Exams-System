<%-- 
    Document   : candidateInfo
    Created on : Dec 24, 2018, 8:38:30 PM
    Author     : yyy
--%>

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
        <h1>Hello World <%=candidate_id%></h1>
    </body>
</html>
