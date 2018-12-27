/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Question;
import model.candidateAnswerDB;


/**
 *
 * @author dell
 */
@WebServlet(name = "sendCandidateAnswer", urlPatterns = {"/sendCandidateAnswer"})
public class sendCandidateAnswer {

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

        int qid = Integer.parseInt(request.getParameter("questionID"));

        String[] answers = request.getParameterValues("answer");

        String examType = request.getParameter("examType");

        int userID = Integer.parseInt(request.getSession().getAttribute("user_id").toString());

        candidateAnswerDB candid_answer = new candidateAnswerDB();
        

        ResultSet rs = candid_answer.getExamID(examType);

        int examID = rs.getInt(1);

        if (answers.length == 0) {
            candid_answer.addCandidateAnswers(userID, examID, qid, 0);
        } else {

            for (int i = 0; i < answers.length; i++) {
                ResultSet rs1 = candid_answer.getAnswerID(answers[i]);

                int answerID = rs1.getInt("answer_id");

                candid_answer.addCandidateAnswers(userID, examID, qid, answerID);
            }
        }
        candid_answer.closeConnection();

        response.sendRedirect("examPage.jsp");
    }

}
