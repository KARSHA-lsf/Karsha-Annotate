/*
 *   Copyright (C) 2013, Lanka Software Foundation and and University of Maryland.
 *
 *   This program is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU Affero General Public License as
 *   published by the Free Software Foundation, either version 3 of the
 *   License, or (at your option) any later version.
 *
 *   This program is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU Affero General Public License for more details.
 *
 *   You should have received a copy of the GNU Affero General Public License
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>
 */
package org.karsha.controler;

import org.karsha.data.UserDB;
import org.karsha.entities.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author v-saba
 */
public class NewUserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String userPath = request.getServletPath();
        if (userPath.equals("/userdelete")) {

            int selectedValue = Integer.parseInt(request.getParameter("userId"));
            String userName = request.getParameter("userName");
            if (!userName.equals("admin")) {
                UserDB.deleteUserByUserId(selectedValue);
            } else {

                session.setAttribute("noAdminDelete", "You can't Delete Admin User");
            }

        }
        
        else if (userPath.equals("/useredit")) {
            int selectedValue = Integer.parseInt(request.getParameter("userId"));
            String userName = request.getParameter("userName");
            User user=UserDB.getUserByUserName(userName);
            session.setAttribute("user", user);
        }

        ArrayList<User> Users = UserDB.getAllUsers();
        session.setAttribute("users", Users);
        request.getRequestDispatcher("/WEB-INF/view/newuser.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
String userPath = request.getServletPath();
HttpSession session = request.getSession();
        String url = null;
        String userName = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        User newUser = new User();
        newUser.setUserName(userName);
        newUser.setEmailAddress(email);
        newUser.setPassword(password);

if(userPath.equals("/newuser")){
        

        if (!UserDB.userNameExist(userName)) {

            UserDB.insert(newUser);

            doGet(request, response);

        } else {

            url = "/WEB-INF/view/newuser.jsp?error=existing_user";
        }
}

else if(userPath.equals("/edituser")){
    
      
      User user=(User)session.getAttribute("user");
      newUser.setUserId(user.getUserId());
      UserDB.updateUser(newUser);
      
      url = "/WEB-INF/view/newuser.jsp";
}
        
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
