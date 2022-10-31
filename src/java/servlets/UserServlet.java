/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.*;
import services.*;

public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        request.setAttribute("action", action);
        request.setAttribute("email", email);
        UserService us = new UserService();
       
        try{
            switch(action){
                case "delete":
                    try{
                     us.delete(email);   
                    }
                    catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("message", "Error deleting user");
                    }
                case "edit":
                     try {
                User user = us.get(email);
                request.setAttribute("firstname", user.getFirstName());
                request.setAttribute("lastname", user.getLastName());
                request.setAttribute("role", user.getRole().getRoleName());
                } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }  
            }    
        }catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", "Error");
                
        }
        
        List<User> users = null;
      
        try {
            users = us.getAll();
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Error when retrieving users");
        }

        request.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");
        String email = request.getParameter("email");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String password = request.getParameter("password");
        String roleName = request.getParameter("role");
        Role role = new Role();
        UserService us = new UserService();
        
        if (email == null || email.equals("") || firstname == null || firstname.equals("") || lastname == null || lastname.equals("") || password == null || password.equals("")) {
            request.setAttribute("error", "All fields are required");
        } else {
            if (roleName.equals("admin")) {
                role = new Role(1, "system admin");
            } else if (roleName.equals("user")) {
                role = new Role(2, "regular user");
            }
        }
        
        try{
            switch(action){
                case "add":
                    try{
                    User user = new User(email, firstname, lastname, password, role);
                    us.insert(user);   
                    }
                    catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                    request.setAttribute("message", "Error adding user");
                    }
                case "edit":
                     try {
                User user = new User(email, firstname, lastname, password, role);
                    us.update(user);
                } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                request.setAttribute("message", "Error editing user");
                }  
            }    
        }catch (Exception ex) {
        Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        request.setAttribute("message", "Error");    
        }
        
        
        List<User> users = null;
        try {
            users = us.getAll();
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            request.setAttribute("message", "Error when retrieving users");
        }

        request.setAttribute("users", users);
        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }

}