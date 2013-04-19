/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
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
