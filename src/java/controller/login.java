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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.userDB;

/**
 *
 * @author yyy
 */
@WebServlet(name = "login", urlPatterns = {"/login"})
public class login extends HttpServlet {

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
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        String passwordInDb = "";
        boolean is_hr = false;
        boolean approved = false;
        int userID = 0;
        
        userDB user = new userDB();
        ResultSet rs = user.getUserByUsername(username);
        
        
        while(rs.next()){
            passwordInDb = rs.getString("password");
            is_hr = rs.getBoolean("is_hr");
            userID = rs.getInt("user_id");
            approved = rs.getBoolean("approved");
        }
        user.closeConnection();
        
        if(approved && password.equals(passwordInDb)){
            
            HttpSession session = request.getSession();
            session.setAttribute("username", username);
            session.setAttribute("user_id",userID);
            session.setAttribute("is_hr", is_hr);
            
            if(is_hr){
               response.sendRedirect("hrProfilePage.jsp"); 
            }
            else{
               response.sendRedirect("candidateProfilePage.jsp");  
            }
        }
        else{
            response.setContentType("text/html;charset=UTF-8");
            try (PrintWriter out = response.getWriter()) {
               
                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Login Page</title>");            
                out.println("</head>");
                out.println("<body>");
                out.println("<h1>username or password is incorrect!</h1>");
                out.println("</body>");
                out.println("</html>");
            }
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
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(login.class.getName()).log(Level.SEVERE, null, ex);
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
