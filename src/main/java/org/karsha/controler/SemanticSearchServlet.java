package org.karsha.controler;

import org.karsha.data.FiboDB;
import org.karsha.entities.FiboTerm;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: randula
 * Date: 11/29/13
 * Time: 8:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class SemanticSearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;

        if(userPath.equals("/semanticSearch")) {
            ArrayList<FiboTerm> fiboList = FiboDB.getAllFiboTerms();
            session.setAttribute("fiboList", fiboList);
            url = "/WEB-INF/view/semanticSearch.jsp";
        }

        RequestDispatcher dispatcher =getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;

        String selectedFiboTerms = (String)request.getParameter("fibo_term");
        session.setAttribute("selectedFiboTerms", selectedFiboTerms);
        if(userPath.equals("/getsimilardocs")) {

            ArrayList<FiboTerm> fiboList =FiboDB.getAllFiboTerms();
            session.setAttribute("fiboList", fiboList);
            url = "/WEB-INF/view/semanticSearch.jsp";

        }
        request.setAttribute("selectedFiboTerms", selectedFiboTerms);
        RequestDispatcher dispatcher =getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
