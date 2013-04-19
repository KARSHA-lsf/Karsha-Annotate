/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.karsha.data.DocSectionDB;
import org.karsha.data.DocumentDB;
import org.karsha.document.ParseDocument;
import org.karsha.entities.DocSection;
import org.karsha.entities.Document;

/**
 *
 * @author lsf
 */
public class SelectSectionServelet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();
        String url = null;

        if (userPath.equals("/selectSections")) {
//            ArrayList<CollectionType> collectionTypeList = CollectionTypDB.getAllCollectionType();
//            session.setAttribute("collectionTypeList", collectionTypeList);
            url = "/WEB-INF/view" + userPath + ".jsp";
            RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
            dispatcher.forward(request, response);
        } //getting the document content from the database
        else if (userPath.equals("/getDocSection")) {
            // String docID=request.getParameter("docID");



            try {
                //String orderId = request.getAttribute("orderId").toString();
                String docID = request.getParameter("docID").toString();
                DocSection document = DocSectionDB.getDocumentDataByDocId(Integer.valueOf(docID));
                   String docContentIntext = (new String(document.getSectiontContent())).replaceAll("[\";\'%$()]", " ");

                
           //     String docContentIntext = ParseDocument.pdfDocToText(document.getSectiontContent()).replaceAll("[\";\'%$()]", " ");

                writer.write(docContentIntext);
                writer.close();
            } catch (Exception ex) {
                ex.getStackTrace();
            }

        }
        //TODO decide what to do when comes from the do method



    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, CorruptIndexException {
        // DBConnection db=new DBConnection();
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;
        HashMap<String, ArrayList<DocSection>> docSectionMap = new HashMap<String, ArrayList<DocSection>>(); //Data Structure to hold all document Sections
        // ArrayList<Similarity> similaritytList = new ArrayList<Similarity>();
        //HashMap<Integer, String>    if (userPath.equals("/selectSections")) { docSecContentMap = new HashMap<Integer, String>();
        if (userPath.equals("/selectSections")) {
            response.setContentType("text/html");
            String queryDoc = null;

            //geting selected checkbox values
            //String docs2[] = request.getParameterValues("docCkBox");
            String docs[] = null;
            ArrayList<String> documents = new ArrayList<String>();
            //get selected check boxes
            if(request.getParameterValues("docCkBox")!=null){
                String[] docCkBox=request.getParameterValues("docCkBox");
              documents.addAll(Arrays.asList(docCkBox)); 
              session.setAttribute("docCkBox", docCkBox);
            }
            else{
                if(session.getAttribute("docCkBox")!=null){
                    documents.addAll(Arrays.asList((String [])session.getAttribute("docCkBox")));
                }
            }
            
            
            //Integer[] selectedCollectionType = Integer.parseInt(request.getParameterValues("collection_type"));
            if (!documents.isEmpty()) {



                docs = documents.toArray(new String[documents.size()]);
                int n = docs.length;

                //  byte docFiles[][] = new byte[n][];
                Document tempDoc = new Document();
                ArrayList<Document> selectedDoc = new ArrayList<Document>();
                ArrayList<DocSection> docSec = new ArrayList<DocSection>();
                DocSection docSection = new DocSection();
                // String result; = temp.replaceAll("[\";()\'$ %,]","");
                for (int i = 0; i < n; i++) {

                    docSec = DocSectionDB.getAllDocumentsByCollectionId(Integer.parseInt(docs[i]));
                    tempDoc = DocumentDB.getDocumentByDocId(Integer.parseInt(docs[i]));
                    selectedDoc.add(tempDoc);
                    docSectionMap.put(tempDoc.getDocumentName(), docSec);

                    //Iterating over document sections for each document
//                    Iterator itr = docSec.iterator();
//                    while (itr.hasNext()) {
//                        docSection = (DocSection) itr.next();
//                        
//                        
//                       docSecContentMap.put(docSection.getSectionId(), ParseDocument.pdfDocToText(docSection.getSectiontContent()).replaceAll("[\";\'%$()]"," "));
//                    //Free the memory used for the holding the document contents
//                    docSection.setSectionContent(null);
//                    }


                }

                session.setAttribute("selectedDocsMap", docSectionMap);
                //session.setAttribute("selectedDocsList", selectedDoc);
                //session.setAttribute("selectedDocSecList", docSec);
                // session.setAttribute("docSecContentMap", docSecContentMap);
                //session.setAttribute("docContent","testing");




            }


            if (docSectionMap.size() > 1) {
                url = "/WEB-INF/view/viewsection.jsp";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
                 
             
            } else {
                //response.sendRedirect("reviewsection");
                response.sendRedirect("docsecmarkup");
               // response.sendRedirect("selectsentences");
                
            }


        }



    }
}
