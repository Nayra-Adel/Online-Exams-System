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
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.userDB;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author yyy
 */
@WebServlet(name = "search", urlPatterns = {"/search"})
public class search extends HttpServlet {

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
            throws ServletException, IOException, SQLException, JSONException, ParseException {
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            
            String searchText = request.getParameter("searchText");
            String searchCriteria = request.getParameter("selectedCriteria");
            
            userDB user = new userDB();
            ResultSet rs = null;
            ResultSet temp_rs = null;
            
            System.out.println(searchText);
            System.out.println(searchCriteria);
            
            switch(searchCriteria){
                case "candidate email":
                    rs = user.getUserByEmail(searchText);
                    break;
                case "candidate name":
                    rs = user.getUserByUsername(searchText);
                    break;
                case "candidate exam type":
                    rs = user.getUserByAvailableExamType(searchText);
                    temp_rs = user.getUserByPassedExamType(searchText);
                    break;
                case "candidate exam date":
                    rs = user.getUserByExamDate(searchText);
                    break;
                default:
                    break;
            }
            
            JSONObject jo = new JSONObject();
            JSONArray ja = new JSONArray();
            
            if(rs != null){
                while(rs.next()){
                jo = new JSONObject();
                
                jo.put("username" ,rs.getString("username"));
                jo.put("id", rs.getInt("user_id"));
                System.out.println(jo.toString());
                ja.put(jo);
                }
            }
            
            if(temp_rs != null){
                while(temp_rs.next()){
                jo = new JSONObject();
                
                jo.put("username" ,temp_rs.getString("username"));
                jo.put("id", temp_rs.getInt("user_id") + " passed this exam type");
                System.out.println(jo.toString());
                ja.put(jo);
                }
            }
            
            response.setContentType("application/json");
            out.write(ja.toString());
                    
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
            Logger.getLogger(search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(search.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (JSONException ex) {
            Logger.getLogger(search.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(search.class.getName()).log(Level.SEVERE, null, ex);
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
