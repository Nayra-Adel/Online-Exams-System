/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Question;
import model.answerDB;
import model.questionDB;

/**
 *
 * @author yyy
 */
@WebServlet(name = "createExam", urlPatterns = {"/createExam"})
public class createExam extends HttpServlet {

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
        questionDB questionDB = new questionDB();
        ResultSet rs = questionDB.getQuestionsByExamType(examType);
        
        ArrayList<Question> allQuestions = new ArrayList<Question>();
        ArrayList<Question> examQuestions = new ArrayList<Question>();
        int q_id = 0;
        String sentence = "";
        String topic = "";
        
        while(rs.next()){
            q_id = rs.getInt("question_id");
            sentence = rs.getString("sentence");
            topic = rs.getString("topic");
            allQuestions.add(new Question(q_id, sentence, topic));
        }
        questionDB.closeConnection();
        
        Random rand = new Random();
        int randomQuestion = 0;
        int examSize = 5;
        ArrayList<String> answers = new ArrayList<>();
        Question q = new Question();
        
        for(int i=0; i<examSize; i++)
        {
            randomQuestion = rand.nextInt(allQuestions.size());
            q = allQuestions.get(randomQuestion);
            SetAnswers(q);
            System.out.println(randomQuestion + " i->" + i + " " + allQuestions.get(randomQuestion).sentence +" ---------correct answer is -----"+ allQuestions.get(randomQuestion).correctAnswer);
            allQuestions.remove(q);
            examQuestions.add(q);
        }
        
        for(Question qq : examQuestions)
        {
            Collections.shuffle(qq.answers);
            System.out.println(qq.sentence+ " **** "+qq.correctAnswer);
        }
//        try (PrintWriter out = response.getWriter()) {
//            /* TODO output your page here. You may use following sample code. */
////            for(Question qq : examQuestions)
////            {
//                out.println("<!DOCTYPE html>");
//                out.println("<html>");
//                out.println("<head>");
//                out.println("<title>Servlet createExam</title>");            
//                out.println("</head>");
//                out.println("<body>");
//                out.println("<h1>Servlet createExam at " + request.getContextPath() + "</h1>");
//                out.println("</body>");
//                out.println("</html>");
////            }
//        }
//
    request.setAttribute("listOfQuestions", examQuestions);
    request.getRequestDispatcher("exam.jsp").forward(request,response);
    
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(createExam.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(createExam.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void SetAnswers(Question question) throws SQLException {
        answerDB answerDB = new answerDB();
        ResultSet rs = answerDB.getRightAnswers(question.question_id);
        while(rs.next()){
            question.correctAnswer = rs.getString("sentence");
            question.answers.add(rs.getString("sentence"));
        }
        
        rs = answerDB.getWrongAnswers(question.question_id);
        while(rs.next()){
            question.answers.add(rs.getString("sentence"));
        }
    }

}
