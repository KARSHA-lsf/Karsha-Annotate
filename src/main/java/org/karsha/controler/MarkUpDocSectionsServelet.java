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
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.DocFlavor;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.karsha.base.DocIndexer;
import org.karsha.base.DocIndexerTest;
import org.karsha.base.DocIndexer_test;
import org.karsha.data.DocSectionDB;
import org.karsha.data.DocumentDB;
import org.karsha.data.FiboDB;
import org.karsha.document.ParseDocument;
import org.karsha.entities.DocSection;
import org.karsha.entities.Document;
import org.karsha.entities.FiboTerm;
import org.karsha.entities.Phrase;

/**
 *
 * @author lsf
 */
public class MarkUpDocSectionsServelet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;
        if (userPath.equals("/docsecmarkup")) {
            //int selectedDocIds = Integer.parseInt(request.getParameter("selectedDocs"));

            try {
                response.setContentType("text/html");
                String queryDoc = null;

                //hashmap<ParentDoc name, Array list doc sections> 
                HashMap<String, ArrayList<DocSection>> SelectedDocSectionMap = new HashMap<String, ArrayList<DocSection>>(); //Data Structure to hold all document Sections


                //geting selected checkbox values
                //String docs2[] = request.getParameterValues("docCkBox");
                String docs[] = null;
                ArrayList<String> documents = new ArrayList<String>();
                //get selected check boxes
                if (request.getParameterValues("docCkBox") != null) {
                    String[] docCkBox = request.getParameterValues("docCkBox");
                    documents.addAll(Arrays.asList(docCkBox));
                    session.setAttribute("docCkBox", docCkBox);
                } else {
                    if (session.getAttribute("docCkBox") != null) {
                        documents.addAll(Arrays.asList((String[]) session.getAttribute("docCkBox")));
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
                  
                    for (int i = 0; i < n; i++) {

                        docSec = DocSectionDB.getFullDocumentByDocumentId(Integer.parseInt(docs[i]));
                        tempDoc = DocumentDB.getDocumentByDocId(Integer.parseInt(docs[i]));
                        selectedDoc.add(tempDoc);
                        SelectedDocSectionMap.put(tempDoc.getDocumentName(), docSec);

                    }

                    session.setAttribute("selectedDocsMap", SelectedDocSectionMap);
                }




                /*
                 * getting all FIBO definitions
                 */
                ArrayList<FiboTerm> fiboTermList = new ArrayList<FiboTerm>();
                fiboTermList = FiboDB.getAllFiboTerms();
               


                /*
                 * placeholder for topk terms
                 */
                HashMap<Integer, ArrayList<FiboTerm>> topKFiboTermsDef = new HashMap<Integer, ArrayList<FiboTerm>>();


                session.setAttribute("SelectedDocSectionMap", SelectedDocSectionMap);
                session.setAttribute("fiboTermList", fiboTermList);
                session.setAttribute("topKFiboTermsMap", topKFiboTermsDef);

                //releasing memeory to increase effciency

                session.removeAttribute("selectedDocsMap");


                // url = "/WEB-INF/view/picsentence.jsp";
                url = "/WEB-INF/view/docsectionmarkup.jsp";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
            } catch (Exception ex) {
                Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }


    }

    /**
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, CorruptIndexException {

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();


        //add recommonded FIBO terms, requested by user in docsectionmarkup.jsp
        if (userPath.equals("/addrows_doc")) {
            String fiboTerm = "";
            String fiboTermDef = "";
            String docSectionID = "";
            String okapiScore = "";

            try {
                if (request.getParameter("fiboTerm") != null) {
                    fiboTerm = request.getParameter("fiboTerm").toString();
                }

                if (request.getParameter("fiboTermDef") != null) {
                    fiboTermDef = request.getParameter("fiboTermDef").toString();
                }
                if (request.getParameter("docSecId") != null) {
                    docSectionID = request.getParameter("docSecId").toString();
                }
                if (request.getParameter("score") != null) {
                    okapiScore = request.getParameter("score").toString();
                }

                HashMap<Integer, ArrayList<FiboTerm>> topKFiboTermsMap = (HashMap<Integer, ArrayList<FiboTerm>>) session.getAttribute("topKFiboTermsMap");
//ArrayList<FiboTerm> termList
                ArrayList<FiboTerm> fibotermsForDoc = (ArrayList<FiboTerm>) topKFiboTermsMap.get(Integer.parseInt(docSectionID));
                if (fibotermsForDoc == null || fibotermsForDoc.isEmpty()) {
                    ArrayList<FiboTerm> tempFibotermsForDoc = new ArrayList<FiboTerm>();
                    FiboTerm newTerm = new FiboTerm();
                    newTerm.setFiboTerm(fiboTerm);
                    newTerm.setFiboDefinition(fiboTermDef);
                    newTerm.setSimScore(Double.parseDouble(okapiScore));
                    newTerm.setFiboId(FiboDB.getFiboTermID(fiboTerm).getFiboId());
                    tempFibotermsForDoc.add(newTerm);
                    topKFiboTermsMap.put(Integer.parseInt(docSectionID), tempFibotermsForDoc);
                } else {
                    FiboTerm newTerm = new FiboTerm();
                    newTerm.setFiboTerm(fiboTerm);
                    newTerm.setFiboDefinition(fiboTermDef);
                    newTerm.setSimScore(Double.parseDouble(okapiScore));
                    newTerm.setFiboId(FiboDB.getFiboTermID(fiboTerm).getFiboId());
                    fibotermsForDoc.add(newTerm);
                }
                session.setAttribute("topKFiboTermsMap", topKFiboTermsMap);
            } catch (Exception ex) {
                Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            }




            writer.write("docContentIntext");
            writer.close();
        } //delete the recommonded FIBO terms, requested by user in docsectionmarkup.jsp
        else if (userPath.equals("/deleterows_doc")) {

            String fiboTermToDel = "";
            String docSectionIDToDel = "";
            try {
                if (request.getParameter("fiboTerm") != null) {
                    fiboTermToDel = request.getParameter("fiboTerm").toString();
                }

                if (request.getParameter("docSecId") != null) {
                    docSectionIDToDel = request.getParameter("docSecId").toString();
                }

                HashMap<Integer, ArrayList<FiboTerm>> topKFiboTermsMap = (HashMap<Integer, ArrayList<FiboTerm>>) session.getAttribute("topKFiboTermsMap");
//ArrayList<FiboTerm> termList
                ArrayList<FiboTerm> fibotermsForDoc = (ArrayList<FiboTerm>) topKFiboTermsMap.get(Integer.parseInt(docSectionIDToDel));
                for (int i = 0; i < fibotermsForDoc.size(); i++) {
                    if (fibotermsForDoc.get(i).getFiboTerm().equals(fiboTermToDel)) {

                        fibotermsForDoc.remove(i);
                        break;
                    }
                }


                // HashMap<Integer, HashMap> topKFiboTermsMap = (HashMap<Integer, HashMap>) session.getAttribute("topKFiboTermsMap");

                // HashMap<String, String> fibotermsForDoc = (HashMap<String, String>) topKFiboTermsMap.get(Integer.parseInt(docSectionIDToDel));
                //  fibotermsForDoc.remove(fiboTermToDel);
                session.setAttribute("topKFiboTermsMap", topKFiboTermsMap);
            } catch (Exception ex) {
                Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            }
            writer.write("docContentIntext2");
            writer.close();
        } //to get recommended terms for a perticuler docsection requested by docsectionmarkup.jsp
        else if (userPath.equals("/getterms_doc")) {
            String fiboTermNDefinition = "";
            String docSectionID = "";
            try {


                if (request.getParameter("docSecId") != null) {
                    docSectionID = request.getParameter("docSecId").toString();
                }


                // Map<Integer, ArrayList<FiboTerm>>  topKFiboTermsMap = (HashMap<Integer, ArrayList<FiboTerm>> ) session.getAttribute("topKFiboTermsMap");
//ArrayList<FiboTerm> termList
                //ArrayList<FiboTerm> fibotermsForDoc = (ArrayList<FiboTerm>) topKFiboTermsMap.get(Integer.parseInt(docSectionIDToDel));

                Map<Integer, ArrayList<FiboTerm>> topKFiboTermsMap = (Map<Integer, ArrayList<FiboTerm>>) session.getAttribute("topKFiboTermsMap");

                ArrayList<FiboTerm> fibotermsForDoc = (ArrayList<FiboTerm>) topKFiboTermsMap.get(Integer.parseInt(docSectionID));

                if (fibotermsForDoc != null && !fibotermsForDoc.isEmpty()) {
                    for (int i = 0; i < fibotermsForDoc.size(); i++) {
                        //##$$##- to seperate ##$$##FIBOterms_1$$**$$FiboDefinitions_1##$$##FIBOterms_2$$**$$FiboDefinitions_2
                        //$$**$$- to seperate FIBOterms$$**$$FiboDefinitions

                        if (fiboTermNDefinition.isEmpty()) {
                            fiboTermNDefinition = fibotermsForDoc.get(i).getFiboTerm() + "$$**$$" + fibotermsForDoc.get(i).getSimScore() + "$$**$$" + fibotermsForDoc.get(i).getFiboDefinition();
                        } else {
                            fiboTermNDefinition = fibotermsForDoc.get(i).getFiboTerm() + "$$**$$" + fibotermsForDoc.get(i).getSimScore() + "$$**$$" + fibotermsForDoc.get(i).getFiboDefinition() + "##$$##" + fiboTermNDefinition;
                        }
                    }
                }


            } catch (Exception ex) {
                Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            }
            writer.write(fiboTermNDefinition);
            writer.close();
        }

    }
}
