<%-- 
    Document   : hrProfilePage
    Created on : Dec 19, 2018, 11:52:59 PM
    Author     : yyy
--%>

<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.examDB"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="model.userDB"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<% 
    String username = request.getSession().getAttribute("username").toString();
        
    userDB user = new userDB();
    ResultSet nonApprovedCandidatesRS = user.getAllNonApprovedCandidates();
    
    examDB exam = new examDB();
    ResultSet examsRS = exam.getAllExams();
    Map<String, Integer> examsTypes = new HashMap<String, Integer>();
    
    while(examsRS.next()){
        examsTypes.put(examsRS.getString("type"), examsRS.getInt("exam_id"));
    }
    
    int counter = 0; 
%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>HR Profile</title>
        <script src="jquery-3.3.1.min.js"></script>
        <script>
            function acceptCandidate(button){
                console.log(button.id);
                var xmlhttp = new XMLHttpRequest();
                
                xmlhttp.open("GET", "acceptCandidate?userID=" + button.id, true);
                xmlhttp.send();
                
                xmlhttp.onreadystatechange = function(){
                    if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
                        console.log("acccceeeeppted from servlet");
                        $('.' + button.id.toString()).hide();
                        $('[name='+ button.id.toString() +']').show();
                    }
                }
            }
            
            function rejectCandidate(button){
               console.log(button.id);
                var xmlhttp = new XMLHttpRequest();
                
                xmlhttp.open("GET", "rejectCandidate?userID=" + button.id, true);
                xmlhttp.send();
                
                xmlhttp.onreadystatechange = function(){
                    if(xmlhttp.readyState == 4 && xmlhttp.status == 200){
                        console.log("reeejeecteed from servlet");
                        $('.' + button.id.toString()).hide();
                    }
                } 
            }
            
            function search(){
                var searchText = document.getElementById("searchText").value;
                        
                var searchCriteria = document.getElementById("searchCriteria");
                var selectedCriteria = searchCriteria.options[searchCriteria.selectedIndex].text;
                
                var resultsTable = document.getElementById("searchResultsTable");
             
                $.ajax({
                    url:"search?searchText=" + searchText + "&selectedCriteria="+ selectedCriteria,
                    type:'GET',
                    dataType: 'json',
                    success: function(data) {
                        resultsTable.innerHTML = "";
                        $.each(data, function(key, value) {
                            
                            var my_button = document.createElement("input");
                            my_button.type = "submit";
                            my_button.value = "view profile";
                            my_button.name = data[key].username;
                            
                            var my_form = document.createElement("form");
                            my_form.action = "candidateInfo.jsp";
                            
                            var my_hidden_input = document.createElement("input");
                            my_hidden_input.hidden = "true";
                            my_hidden_input.type = "text";
                            my_hidden_input.value = data[key].id;
                            my_hidden_input.name = "candidate_id"
                            
                            my_form.appendChild(my_button);
                            my_form.appendChild(my_hidden_input);
                            
                            var row = resultsTable.insertRow(key);
                            
                            var cell_1 = row.insertCell(0);
                            cell_1.innerHTML = data[key].username;
                            
                            row.appendChild(my_form);
                        });
                    }
                  });    
            }
            
        </script>
    </head>
    <body>
        <form name="logout-form" action="logout" method="POST">
            <input type="submit" value="logout" name="logout"/>
        </form>
        <h1>Welcome ${username}</h1>
        <h2>.................................Registration Requests.................................</h2>
        <% while(nonApprovedCandidatesRS.next()){ %>
        <form name="cadidateInfo" action="sendEmail">
                <h3 id="username"> username: <%= nonApprovedCandidatesRS.getString("username") %> </h3>
                <h3> email: <%= nonApprovedCandidatesRS.getString("email") %> </h3>
                <h3> cv: <%= nonApprovedCandidatesRS.getString("cv") %> </h3>
                
                <input type="button" value="accept" class="<%= nonApprovedCandidatesRS.getString("user_id")%>" id="<%= nonApprovedCandidatesRS.getString("user_id")%>" onclick="acceptCandidate(this)"/>
                <input type="button" value="reject" class="<%= nonApprovedCandidatesRS.getString("user_id")%>" id="<%= nonApprovedCandidatesRS.getString("user_id")%>" onclick="rejectCandidate(this)">
                
                <input type="text"  name="currentID" hidden="true" value="<%= nonApprovedCandidatesRS.getString("user_id") %>"/>
                <input type="text"  name="currentEmail" hidden="true" value="<%= nonApprovedCandidatesRS.getString("email")  %>"/>
                
                
                <fieldset  name="<%= nonApprovedCandidatesRS.getString("user_id")%>" hidden="true">
                    <legend>Choose exams</legend>
                    
                    <% for(Map.Entry<String, Integer> entry : examsTypes.entrySet()){%> 
                    
                    <input type="checkbox" name="examType" value="<%= entry.getValue() %>"><%= entry.getKey() %><br>
                    
                    <% } %>
                    
                    <input type="submit" id="<%= nonApprovedCandidatesRS.getString("user_id")%>" value="ok"/>
                </fieldset>
            
                <h4>.................................................................................</h4>
            </form>
        <% 
            counter += 1; } 
            exam.closeConnection();
            user.closeConnection();
        %>
        
        <h2>.................................Search Approved Candidates.................................</h2>
        <form>
            <input type="text" id="searchText" placeholder="enter search text... "/><br>
            Select search criteria:
            <select id="searchCriteria">
                <option value="candidate email">candidate email</option>
                <option value="candidate name">candidate name</option>
                <option value="candidate exam type">candidate exam type</option>
                <option value="candidate exam date">candidate exam date</option>
            </select>
            <button type="button" onclick="search()">search</button><br>
            <table id="searchResultsTable">
            </table>
        </form>
    </body>
</html>
