/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.userDB;

/**
 *
 * @author yyy
 */
@WebServlet(name = "sendEmail", urlPatterns = {"/sendEmail"})
public class sendEmail extends HttpServlet {

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
        
        String email = request.getParameter("currentEmail");
        
        int id = Integer.parseInt(request.getParameter("currentID"));
        System.out.println(id);
        String[] exams = request.getParameterValues("examType");
        
        /*for(int i = 0 ; i < exams.length ; i++)
            System.out.println(exams[i]);*/
        
        userDB user = new userDB();
        
        if(user.addAvailableExams(id, exams)){
            System.out.println("available exams added successfully!");
        }
        else{
            System.out.println("available exams not added successfully!");
        }
        
        sendEmail("", "");
        
        response.sendRedirect("hrProfilePage.jsp");
    }
    public void sendEmail(String hrEmail, String candidateEmail){
        String to = "rehamezzatmohamed@gmail.com";
        String from = "ia.onlineexamssystem@gmail.com";
        String username = "ia.onlineexamssystem@gmail.com";
        String host = "smtp.gmail.com";
        Properties properties = new Properties();
        
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");
        
        String password = "OES_123456";
        
        String subject = "IA project";
        String msgText = "your account has been approved,congratulations now you can login!";
        
        Session session = Session.getInstance(properties,
                 new javax.mail.Authenticator() {
                   protected PasswordAuthentication getPasswordAuthentication() {
                       return new PasswordAuthentication(username,password);
                   }
                 });
       
        try {
            MimeMessage message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
            message.setSubject(subject);
            message.setText(msgText);

            Transport.send(message);
            
            System.out.println("Sent message successfully....");
      } catch (MessagingException mex) {
            System.out.println(mex);
      }
        
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
            Logger.getLogger(sendEmail.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(sendEmail.class.getName()).log(Level.SEVERE, null, ex);
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

}
