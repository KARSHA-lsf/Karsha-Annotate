/*
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
 */
package org.karsha.controler;

//import antlr.Version;
//import com.mysql.jdbc.Connection;
//import com.mysql.jdbc.Statement;
//import karsha.data.DBConnection;

import org.karsha.entities.Phrase;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;


/**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes 
 * April 14, 2012 Kasun Perera Created  
 *
 */

/*
 * This Servelet class handels the only requests, and direct to relevent page
 * TODO-this is a dummy class, should move what ever here to seperate controller servelets
 */
@WebServlet(name = "ControlerServelet",
loadOnStartup = 1 //        urlPatterns = {"/classify", "/markwithfibo", "/topkphrases", "/uploaddoc","/viewdocument","/viewphrase"}
)
public class ControlerServelet extends HttpServlet {
  //  private int byteRead;

    // DBConnection db=new DBConnection();
    
    /**
     * Handles the HTTP
     * <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userPath = request.getServletPath();

        // if category page is requested
        if (userPath.equals("/uploaddoc")) {
           
        } else if (userPath.equals("/noadminprivilege")) {
            userPath = "/adminprivilege_error";
            
        }  
        else if (userPath.equals("/login")) {
            userPath = "/karshalogin";
            
        }  
        else if (userPath.equals("/upload")) {
            


        }
         else if (userPath.equals("/checkbox")) {
            


        }
        
           else if (userPath.equals("/viewPdf")) {
            
           
        }else if (userPath.equals("/pickQueryDoc")) {
        
               
         }else if (userPath.equals("/simOutput")) {
        
               
         }else if (userPath.equals("/openPDF")) {
        String t = request.getAttribute("btnOpenPDF").toString() ;
               
         }else if (userPath.equals("/finalPage")) {
        
               
         }
         
           
        else if (userPath.equals("/queryOutput")) {
            // TODO: Implement cart page request
            //  userPath = "/cart";
            // if checkout page is requested
        } else if (userPath.equals("/docSimilarity")) {
            // TODO: Implement cart page request
            //  userPath = "/cart";
            // if checkout page is requested
        }
           
           else if (userPath.equals("/topkphrases")) {
        
                userPath = "/selectdocument";
         }else if (userPath.equals("/docSimilarity")) {
          
        } else if (userPath.equals("/buildIndex")) {
           
        }
        else if (userPath.equals("/collection")) {
           
        } else if (userPath.equals("/selectdocument")) {
            
  
            HttpSession session = request.getSession();
             HashMap<Integer,Phrase> selectedPhrasesMap = (HashMap<Integer,Phrase>)session.getAttribute("selectedPhrasesMap");
    if(selectedPhrasesMap != null){
        selectedPhrasesMap.clear();
    }
        } else if (userPath.equals("/classify")) {

            userPath = "/selectdocument";
            
           
        }  
          else if (userPath.equals("/markwithfibo")) {
              userPath = "/selectdocument";
          }
        
        else if (userPath.equals("/viewdocument")) {
           
        } else if (userPath.equals("/viewphrase")) {
          
        }
        // use RequestDispatcher to forward request internally
        String url = "/WEB-INF/view" + userPath + ".jsp";

        try {
            request.getRequestDispatcher(url).forward(request, response);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    /**
     * Handles the HTTP
     * <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userPath = request.getServletPath();

        // if addToCart action is called
        if (userPath.equals("/classify")) {
            

         
            
        } 
       else if (userPath.equals("/viewPdf")) {
 
           

       } 
        
         else if (userPath.equals("/topkphrases")) {
              


     
        } 
        
        else if (userPath.equals("/upload")) {

        } else if (userPath.equals("/uploaddoc")) {
            


        } 
        
        else if (userPath.equals("/markwithfibo")) {

        }
        
        
        else if (userPath.equals ( "/savedata")) {
  
    }
        else if (userPath.equals ( "/similaritydata")) {
  
    }
    // use RequestDispatcher to forward request internally
    String url = "/WEB-INF/view" + userPath + ".jsp";

    
        try {
            request.getRequestDispatcher(url).forward(request, response);
    }
    catch (Exception ex

    
        ) {
            ex.printStackTrace();
    }
               
        
}
    

    
}
