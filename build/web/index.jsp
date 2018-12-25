<%-- 
    Document   : index
    Created on : Dec 19, 2018, 8:36:00 PM
    Author     : yyy
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    System.out.println("xxxxx in session:"+session.getAttribute("username") );
    session.setAttribute("is_hr", true);
    //session.setAttribute("is_hr", false);
    if(session.getAttribute("username") == null){
        response.sendRedirect("homePage.html");
    }
    else{
        if(Boolean.parseBoolean(session.getAttribute("is_hr").toString()))
        {
            //response.sendRedirect("hrProfile");
            response.sendRedirect("hrProfilePage.jsp");
        }
        else{
            response.sendRedirect("candidateProfilePage.jsp");
        }  
    }
%>

