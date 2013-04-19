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
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.karsha.data.ConnectionPool;
import org.karsha.data.DocumentDB;
import org.karsha.data.PhraseDB;
//import org.karsha.data.TaxonomyDB;
//import org.karsha.entities.DocTaxo;
import org.karsha.entities.Document;
import org.karsha.entities.Phrase;
//import org.karsha.entities.Taxonomy;
import org.karsha.document.ParseDocument;

/**
 *
 * @author lsf
 */
public class TopKPhrasesServelet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;


        if (userPath.equals("/edittopkphrases")) {

            int documentId = Integer.parseInt(request.getParameter("docId"));
            session.setAttribute("documentID", documentId);
            Document d = DocumentDB.getDocumentByDocId(documentId);
            session.setAttribute("documentName", d.getDocumentName());

            // get the phrases in the document
            HashMap<Integer, String> allPhrasesMap = extractPhrasesFromDocument(documentId);
            session.setAttribute("allphrases", allPhrasesMap);

            // get all the phrases which were extracted and stored in the databases.
            ArrayList<Phrase> existingPhrasesList = PhraseDB.getAllPhrasesByDocId(documentId);

            HashMap<Integer, Phrase> existingPhrasesMap = new HashMap<Integer, Phrase>();

            // string to be concatanated with selected string vlaues and indexes
            String selected_phrases = "";
            String selected_phrases_index = "";

            for (int i = 0; i <= existingPhrasesList.size() - 1; i++) {

                Phrase p = existingPhrasesList.get(i);


                // function to remove already existing phrase from the allPhrasesMap hashmap
                // although this is a lengthy process it might not work for large documents
                for (Iterator<Map.Entry<Integer, String>> iter = allPhrasesMap.entrySet().iterator();
                        iter.hasNext();) {
                    Map.Entry<Integer, String> entry = iter.next();
                    if (p.getPhraseContent().trim().equals(entry.getValue().trim())) {
                        int key = entry.getKey();
                        existingPhrasesMap.put(key, p);

                        // arranging the hidden values in the selected_phrases_index
                        if (selected_phrases_index.equals("")) {
                            selected_phrases_index = entry.getKey().toString();
                            selected_phrases = entry.getValue().trim();
                        } else {
                            selected_phrases_index += "\t" + entry.getKey().toString();
                            selected_phrases += "\t" + entry.getValue().trim();
                        }
                        // remove from the hash map
                        iter.remove();
                        break; // if only want to remove first match.
                    }
                }

            }

            // resetting hidden values so that when they are picked up by the topkphrases pages, they dont throw errors
            request.setAttribute("selected_phrases_index", selected_phrases_index); // setting the selected_phrases_index hidden value.
            request.setAttribute("selected_phrases", selected_phrases); // setting the selected_phrases hidden value.

            // resetting selectedPhrasesMap after adding phrases which were saved in the database.
            session.setAttribute("selectedPhrasesMap", existingPhrasesMap);

            userPath = "/topkphrases";

        } else if (userPath.equals("/topkphrases")) {
            userPath = "/selectdocument";
        }

        url = "/WEB-INF/view" + userPath + ".jsp";

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;


        if (userPath.equals("/topkphrases")) {
            
            
            /*fixing the bug 
             * Edit markedUp> Back > Select New Doc for markup > Next,
             * you will see the selected phrases from previous doc*/
           HashMap<Integer,Phrase> selectedPhrasesMap = ( HashMap<Integer,Phrase>)session.getAttribute("selectedPhrasesMap");
               
                if(selectedPhrasesMap != null){
                    selectedPhrasesMap.clear();
                }
            
            String stdoc = request.getParameter("selectdocument");

            if (stdoc != null) {      //if user selected a document
                int documentId = Integer.parseInt(stdoc);
                session.setAttribute("documentID", documentId);

                Document d = DocumentDB.getDocumentByDocId(documentId);
                session.setAttribute("documentName", d.getDocumentName());
                session.setAttribute("allphrases", extractPhrasesFromDocument(documentId));
            } else {
                userPath = "/selectdocument";
            }

            url = "/WEB-INF/view" + userPath + ".jsp";

        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }

    byte[] getBLOB(int docId) throws Exception {

        ConnectionPool pool = ConnectionPool.getInstance();
        //  DBConnection db=new DBConnection();
        Connection conn = null;
        ResultSet rs = null;
        PreparedStatement pstmt = null;
        String query = "SELECT DocFile FROM document where DocId = ? ";
        Blob blob = null;

        try {

            conn = pool.getConnection();
            pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, docId); // assign the user requeested pdf id to the above prepared statement.

            rs = pstmt.executeQuery();
            rs.next();
            blob = rs.getBlob(1); // retrieve the blob stored as column PDF.

        } catch (Exception e) {
        } finally {
            rs.close();
            pstmt.close();
            conn.close();
        }
        if (blob != null) {
            return blob.getBytes((long) 1, (int) blob.length());
        } else {
            return null;
        }
    }

    private HashMap<Integer, String> extractPhrasesFromDocument(int documentId) throws IOException {

        byte[] b = null;

        try {

            b = getBLOB(documentId);

        } catch (Exception ex) {
            Logger.getLogger(ControlerServelet.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }

        ArrayList<String> sentenceList = new ArrayList<String>();
        ParseDocument doc = new ParseDocument();
        //send to bolb directly
        sentenceList=doc.txtDocToSentence( new String(b));
        
       // sentenceList = doc.pdfDocToSentence(b);

        HashMap<Integer, String> sentenceMap = new HashMap<Integer, String>();
        for (int i = 0; i <= sentenceList.size() - 1; i++) {
            sentenceMap.put(i, sentenceList.get(i));
        }

        return sentenceMap;


    }
}
