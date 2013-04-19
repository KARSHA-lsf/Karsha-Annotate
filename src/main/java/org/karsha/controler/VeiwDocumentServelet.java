/**
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


import org.karsha.data.DocumentDB;
import org.karsha.entities.Document;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;





/*
 * This Servelet class handels the requests related to classifing document with taxonomy tree
 */
public class VeiwDocumentServelet extends HttpServlet {
    
    /**
     * Get method prevents using goign directly to claasify.jsp page without selecting a document 
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
         
         if (userPath.equals("/viewPdf")) {
           
    
           String selectedValue=request.getParameter("docName");
          
          Document document=DocumentDB.getDocumentDataByDocName(selectedValue);
          
          byte[] documentBlob=document.getDocumentContent();
         
          // request.setAttribute("documentBlob", documentBlob);
            
session.setAttribute("documentBlob", documentBlob);
           

            url = "/WEB-INF/view/viewPdf.jsp";
            
        }
         
        url = "/WEB-INF/view"+userPath +".jsp";
        
        RequestDispatcher dispatcher =getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }
    
    /**
     * Post method handles the classify.jsp request
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException 
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;
        
      
        
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
        
    }
}
