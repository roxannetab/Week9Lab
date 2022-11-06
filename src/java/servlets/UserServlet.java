/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import models.Role;

import models.User;
import services.RoleService;
import services.UserService;


public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        RoleService roleService = new RoleService();
        String addOrEdit;
        List<User> users = null;
        List<Role> roles = null;

        users = getUsers();
        ArrayList<String> roleList = new ArrayList<>();
        try {
            roles = roleService.getAll();
            roleList = getRoleList();
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        session.setAttribute("roles", roles);
        session.setAttribute("userList", users);

        session.setAttribute("roleList", roleList);

        String editUser = req.getParameter("editUser");
        if (editUser != null && !editUser.equals("")) {
            addOrEdit = "Edit User";

            int editIndex = Integer.parseInt(editUser);
            req.setAttribute("editIndex", editIndex);
            User selectedUser = users.get(editIndex);
            req.setAttribute("selectedUser", selectedUser);
            session.setAttribute("editIndex", editIndex);
            req.setAttribute("firstName", users.get(editIndex).getFirstName());
            req.setAttribute("lastName", users.get(editIndex).getLastName());
        } else {
            editUser = null;
            addOrEdit = "Add User";
        }
        session.setAttribute("addOrEdit", addOrEdit);
        req.setAttribute("editUser", editUser);

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(req, res);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse res)
            throws ServletException, IOException {

        HttpSession session = req.getSession();
        UserService userService = new UserService();
        List<User> users = null;

        users = getUsers();
        session.setAttribute("userList", users);

        String deleteIndex = req.getParameter("delete");
        req.setAttribute("deleteIndex", deleteIndex);

        String action = req.getParameter("action");
        String email = req.getParameter("emailInput");
        String firstName = req.getParameter("firstNameInput");
        String lastName = req.getParameter("lastNameInput");
        String password = req.getParameter("passwordInput");
        String roleInput = req.getParameter("roleInput");
        int editIndex;

        String addOrEdit = (String) session.getAttribute("addOrEdit");
        String message = "";

        if (action.equals("Update")) {

            editIndex = (int) session.getAttribute("editIndex");
            email = users.get(editIndex).getEmail();

        }

        if (deleteIndex == null && (email.equals("") || firstName.equals("")
                || lastName.equals("") || password.equals(""))) {

            req.setAttribute("email", email);
            req.setAttribute("firstName", firstName);
            req.setAttribute("lastName", lastName);
            message = "Please fill out all fields";

        } else if (action.equals("Add user")) {

            List<User> userList = (List<User>) session.getAttribute("userList");
            Boolean exist = false;
            for (int i = 0; i < userList.size(); i++) {
                if (email.equals(userList.get(i).getEmail())) {
                    exist = true;
                    message = "Email already exists please use another email.";
                    req.setAttribute("email", email);
                    req.setAttribute("firstName", firstName);
                    req.setAttribute("lastName", lastName);
                }
            }
            if (!exist) {
                try {
                    userService.insert(email, firstName, lastName, password, Integer.parseInt(roleInput));
                } catch (Exception ex) {
                    Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
                }

            }

        } else if (action.equals("Update")) {

            try {
                userService.update(email, firstName, lastName, password, Integer.parseInt(roleInput));
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        if (action.equals("deleteUser")) {

            try {
                User deleteUser = users.get(Integer.parseInt(deleteIndex));
                userService.delete(deleteUser);
            } catch (Exception ex) {
                Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

        req.setAttribute("message", message);

        users = getUsers();
        ArrayList<String> roleList = new ArrayList<>();
        try {
            roleList = getRoleList();
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        session.setAttribute("userList", users);
        session.setAttribute("roleList", roleList);

        String message2 = "";

        if (users.isEmpty()) {
            message2 = "No users found. Please add a user";
            session.setAttribute("addOrEdit", "Add User");
        } else {
            message2 = "";
        }
        session.setAttribute("message2", message2);

        getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(req, res);
    }

    public List<User> getUsers() {
        List<User> users = null;
        UserService userService = new UserService();
        try {
            users = userService.getAll();
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        return users;
    }

    public ArrayList getRoleList() throws Exception {
        List<User> users = getUsers();
        ArrayList<String> roleList = new ArrayList<>();
        RoleService roleService = new RoleService();
        for (int i = 0; i < users.size(); i++) {

            roleList.add(roleService.get(users.get(i).getRole()).getName());
        }

        return roleList;
    }
}