<%-- 
    Document   : users
    Created on : 28-Oct-2022, 3:06:04 AM
    Author     : RT
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Users</title>
    </head>
    
    <body>
        
        <h1>Manage Users</h1>

        <c:if test="${userList.size() > 0}">
            <table border="1" >
                <thead >
                    <tr style=" background-color: #777777" >
                        <th >Email</th>
                        <th>First Name</th>
                        <th>Last Name</th>
                        <th>Role</th>
                        <th></th>
                        <th></th>
                    </tr>
                </thead>
                
                <tbody>
                    <c:forEach items="${userList}" var="user" varStatus="loop">
                        <tr style=" background-color: #96D4D4">
                            <th>${user.getEmail()}</th>
                            <th>${user.getFirstName()}</th>
                            <th>${user.getLastName()}</th>
                            <!--<th>${user.getRole()}</th>-->
                            <th>${roleList.get(loop.index)}</th>
                            <th><a href="User?editUser=${loop.index}" >Edit</a></th>
                            <th>
                                <form action="User?delete=${loop.index}" method="post" >
                                    <button name="action" value="deleteUser" type="submit">Delete</button>
                                </form>
                            </th>
                        </tr>
                    </c:forEach>
                </tbody>
            </table>
        </c:if>

        <c:if test="${userList.size() == 0}">
            <div><b>${message2}</b></div>
        </c:if>

        <h2>${addOrEdit}</h2>

        <form  action="User" method="post">

            <c:choose>

                <c:when test="${addOrEdit == 'Edit User'}">
                    <div>Email: ${selectedUser.getEmail()}</div>
                </c:when>

                <c:otherwise>
                    Email: <input type="text" name="emailInput" value="${email}">
                    <br>
                </c:otherwise>

            </c:choose>

            First name: <input type="text" name="firstNameInput" value="${firstName}">
            <br>
            Last name: <input type="text" name="lastNameInput" value="${lastName}">
            <br>
            Password: <input type="password" name="passwordInput" >
            <br>
            Role: 

            <select name="roleInput" type="text">
                <c:forEach items="${roles}" var="role" varStatus="loop">
                    <option <c:if test="${addOrEdit == 'Edit User' && selectedUser.getRole() == (loop.index + 1)}">selected</c:if> value="${loop.index + 1}">${role.getName()}</option>
                </c:forEach>
            </select>

            <br>

            <c:if test="${addOrEdit == 'Add User'}">
                <input name="action" type="submit" value="Add user">
            </c:if>

            <c:if test="${addOrEdit == 'Edit User'}">
                <input name="action" type="submit" value="Update">
            </c:if>

        </form>

        <c:if test="${editUser != null || addOrEdit == 'Edit User'}">
            <form id="test" action="User?editUser=null" method="get"><button form="test" type="submit" value="cancel">Cancel</button></form>
        </c:if>

        <c:if test="${message != ''}">
            <div style="color:red"><b>${message}</b></div>
                </c:if>    

    </body>
</html>