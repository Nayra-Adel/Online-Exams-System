/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.candidateAnswerDB;
import model.candidatePassedExam;

/**
 *
 * @author dell
 */
public class setExam {

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

        String examType = request.getParameter("examType");
        
        int userID = Integer.parseInt(request.getSession().getAttribute("user_id").toString());
        
        candidateAnswerDB candid_answer = new candidateAnswerDB();
        
        ResultSet rs = candid_answer.getExamID(examType);
    
        int examID = rs.getInt(1);
        
        candid_answer.closeConnection();
        
        Date examDate = (Date) request.getAttribute("examDate");
        
        candidatePassedExam candidate_pass = new candidatePassedExam();
        
        int score = candidate_pass.scoreExam();
        
        candidate_pass.addCandidatePassInfo(userID, examID, examDate, score);
        request.setAttribute("Score", score);

        response.sendRedirect("submitExam.html");
    }

}
