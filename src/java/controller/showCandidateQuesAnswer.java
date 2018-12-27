/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.candidateAnswerDB;
import model.candidateExam;
import model.candidatePassedExam;

/**
 *
 * @author dell
 */
@WebServlet(name = "showCandidateQuesAnswer", urlPatterns = {"/showCandidateQuesAnswer"})
public class showCandidateQuesAnswer {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, SQLException {
        String userName = request.getParameter("username");

        candidatePassedExam candExam = new candidatePassedExam();

        ResultSet rs = candExam.getCandidateInfoByUsername(userName); // retrieve candID,ExamID ,questID,AnsID
        //now, we need to get all answers that user is answered for one questions(questions may be had one or more answers)
        int checkQuestionRpeat = 0;
        String tempQuestSentence = "";
        ArrayList<String> answersCandidate = new ArrayList<>();
        ArrayList<candidateExam> candExamList = new ArrayList<candidateExam>();

        while (rs.next()) {
            String questSent = rs.getString(3);
            tempQuestSentence = questSent;
            answersCandidate.add(rs.getString(4));
            while (rs.next()) {
                String questSent1 = rs.getString(3);
                checkQuestionRpeat = questSent1.compareTo(tempQuestSentence);                
                if (checkQuestionRpeat == 0) {                    
                    answersCandidate.add(rs.getString(4));                    
                    tempQuestSentence = questSent1;                   
                    continue;
                } else {                   
                    rs.previous();  // go back step           
                    break;
                }
            }
            candExamList.add(new candidateExam(tempQuestSentence, answersCandidate));
        }

        request.setAttribute("candQuestionsAnswer", candExamList);

        response.sendRedirect("candidateInfo.jsp");
    }

}
