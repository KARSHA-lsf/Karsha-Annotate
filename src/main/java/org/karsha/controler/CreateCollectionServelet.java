/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.karsha.data.CollectionTypDB;
import org.karsha.data.DocumentDB;
import org.karsha.entities.CollectionType;
import org.karsha.entities.Document;
import org.karsha.entities.Phrase;
//import org.apache.taglibs.standard.tag.rt.core.*;

/**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes 
 * April 14, 2012 Kasun Perera Created  
 *
 */

/*
 * This Servelet class handels the requests related to creating document collection, 
 * in the process of calculating document similarity
 * 
 */
public class CreateCollectionServelet extends HttpServlet{
    
    /**
     * Get Method Handles createcollection.jsp request
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

         
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;
         
        if(userPath.equals("/createcollection")) { 
           ArrayList<CollectionType> collectionTypeList = CollectionTypDB.getAllCollectionType();
            session.setAttribute("collectionTypeList", collectionTypeList);
                 url = "/WEB-INF/view/createcollection.jsp";  
         }
         
        else if(userPath.equals("/createdoccollection")) { 
           ArrayList<CollectionType> collectionTypeList = CollectionTypDB.getAllCollectionType();
            session.setAttribute("collectionTypeList", collectionTypeList);
                 url = "/WEB-INF/view/createdoccollection.jsp";  
         }
         else if (userPath.equals("/login")) {
              url = "/WEB-INF/view/userlogin.jsp";  
           
            
        }  
        
        RequestDispatcher dispatcher =getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
       
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;
        
        int selectedCollectionType = Integer.parseInt(request.getParameter("collection_type"));
        session.setAttribute("collectionType", selectedCollectionType);  // setteing collection type for session
        if(userPath.equals("/getsimdocuments")) {
            
            ArrayList<Document> documentList = DocumentDB.getAllDocumentsByCollectionId(selectedCollectionType);
            session.setAttribute("documentList", documentList);
            url = "/WEB-INF/view/createcollection.jsp";
            
        }
        request.setAttribute("selectedCollectionTypeId", selectedCollectionType);
        RequestDispatcher dispatcher =getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
        
    }
    
}
