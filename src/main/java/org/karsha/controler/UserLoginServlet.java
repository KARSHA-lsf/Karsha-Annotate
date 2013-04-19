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

import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.karsha.data.CollectionTypDB;
import org.karsha.data.UserDB;
import org.karsha.entities.CollectionType;
import org.karsha.entities.User;

/**
 *
 * @author v-saba
 */
public class UserLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession();
        String url = "";
        String userPath = request.getServletPath();

        if (userPath.equals("/userlogout")) {

            session.invalidate();
            url = "/WEB-INF/view/karshalogin.jsp";
        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);


    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {

        HttpSession session = request.getSession();
        String url = "";
        String userPath = request.getServletPath();


        if (userPath.equals("/userlogin")) {

            String userName = request.getParameter("username");
            String password = request.getParameter("password");

            User karshaUser = UserDB.getUserByUserName(userName);

            if (null == karshaUser) {
                // transfer to login error page if the username or password is not correect with the proper message.
                url = "/WEB-INF/view/karshalogin.jsp";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
            } else {
                if (karshaUser.getPassword().equals(password)) {
                    // login sucessfull redirect to karsha main page or index page.
                    session.setAttribute("username", userName);
                    session.setAttribute("userId", karshaUser.getUserId());

                  //   url = "/WEB-INF/view/createcollection.jsp";
                  //  ArrayList<CollectionType> collectionTypeList = CollectionTypDB.getAllCollectionType();
           // session.setAttribute("collectionTypeList", collectionTypeList);
                    response.sendRedirect("createcollection");
            
          //  RequestDispatcher dispatcher =getServletContext().getRequestDispatcher(url);
       // dispatcher.forward(request, response);
                }
                else {
                    url = "/WEB-INF/view/karshalogin.jsp";

                    RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
                    dispatcher.forward(request, response);
                }
            }
        }



    }
}
