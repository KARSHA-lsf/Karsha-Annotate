package org.karsha.controler;

import org.karsha.data.DocumentDB;
import org.karsha.entities.Document;

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
public class DocSearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;

        if(userPath.equals("/docsearch")) {
            ArrayList<Document> documentList = DocumentDB.getAllDocuments();
            session.setAttribute("documentList", documentList);
            url = "/WEB-INF/view/docsearch.jsp";
        }

        RequestDispatcher dispatcher =getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;

        String selectedDocumentName = (String)request.getParameter("document_name");
        session.setAttribute("selectedDocumentName", selectedDocumentName);
        if(userPath.equals("/getsimilardocuments")) {

            ArrayList<Document> documentList = DocumentDB.getSimilarDocuments();
            session.setAttribute("documentList", documentList);
            url = "/WEB-INF/view/docsearch.jsp";

        }
        request.setAttribute("selectedDocumentName", selectedDocumentName);
        RequestDispatcher dispatcher =getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
}
