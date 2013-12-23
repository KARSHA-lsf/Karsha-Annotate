
/**
 *   KarshaAnnotate- Annotation tool for financial documents
 *  
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
 *
 * Date Author Changes 
 * April 14, 2012 Kasun Perera Created  
 *
 */
package org.karsha.controler;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
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
