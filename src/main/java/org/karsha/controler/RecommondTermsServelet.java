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
//import org.apache.taglibs.standard.tag.rt.core.UrlTag;

/**
 *
 * @author lsf
 */
public class RecommondTermsServelet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;
        if (userPath.equals("/reviewsection")) {
            //int selectedDocIds = Integer.parseInt(request.getParameter("selectedDocs"));

            try {
                // String url = null;
                DocSection tempDocSection;
                Document tempDocument;
                //int selectedDocID;
                int noOfDocSecs = 0;


                //  HashMap<Integer, String> docsInText = (HashMap<Integer, String>) session.getAttribute("docSecContentMap");


                //hashmap<ParentDoc name, Array list doc sections>
                HashMap<String, ArrayList<DocSection>> SelectedDocSectionMap = (HashMap<String, ArrayList<DocSection>>) session.getAttribute("selectedDocsMap");
                for (Map.Entry entry : SelectedDocSectionMap.entrySet()) {
                    noOfDocSecs = noOfDocSecs + ((ArrayList<DocSection>) entry.getValue()).size();
                }
                // Map.Entry entry:SelectedDocSectionMap.entrySet(); 


                /*
                 * String[] selectedDocs =
                 * (request.getParameter("selecteddocids").toString()).split("***");
                 * //String[]
                 * selectedChekBoxList=request.getParameterValues("selected_check_boxes");
                 * for (int i = 0; i < selectedDocs.length; i++) { try {
                 * selectedDocID = Integer.parseInt(selectedDocs[i]);
                 * tempDocSection =
                 * DocSectionDB.getDocumentDataByDocId(selectedDocID); //get the
                 * parent doc of the selected doc section tempDocument =
                 * DocumentDB.getDocumentByDocId(tempDocSection.getParentDocId());
                 *
                 * //create a Map with the selected docSections of
                 * viewsection.jsp if
                 * (SelectedDocSectionMap.containsKey(tempDocument.getDocumentName()))
                 * { //if hashmap already contains name of the parent document.
                 *
                 * SelectedDocSectionMap.get(tempDocument.getDocumentName()).add(tempDocSection);
                 * } else { ArrayList<DocSection> docSections = new
                 * ArrayList<DocSection>(); docSections.add(tempDocSection);
                 * SelectedDocSectionMap.put(tempDocument.getDocumentName(),
                 * docSections); } noOfDocSecs++; } catch (NumberFormatException
                 * e) { continue; } }
                 *
                 */
                //String[] docList = {"1", "2", "3", "12", "9", "10", "7", "8"};
                // int noOfDocSecs = docList.length;

                /*
                 * getting all FIBO definitions
                 */
                ArrayList<FiboTerm> fiboTermList = new ArrayList<FiboTerm>();
                fiboTermList = FiboDB.getAllFiboTerms();
                int noOfFiboTerms = fiboTermList.size();

                /*
                 * docContent- to hold document text and fibo
                 * definitions(sepetrate documents)
                 */
                String[] docContent = new String[noOfDocSecs + noOfFiboTerms];
                String[] docNames = new String[noOfDocSecs + noOfFiboTerms];
                String[] docIds = new String[noOfDocSecs];

                int count = 0;

                //preparing data for okapi similarty calculation
                for (Map.Entry entry : SelectedDocSectionMap.entrySet()) {
                    ArrayList<DocSection> docSections = (ArrayList<DocSection>) entry.getValue();

                    for (int i = 0; i < docSections.size(); i++) {
                        //docContent[count] = ParseDocument.pdfDocToText(DocSectionDB.getDocumentDataByDocId(docSections.get(i).getSectionId()).getSectiontContent());


                        docContent[count] = new String(DocSectionDB.getDocumentDataByDocId(docSections.get(i).getSectionId()).getSectiontContent());
                        docNames[count] = docSections.get(i).getSectionName();
                        docIds[count] = Integer.toString(docSections.get(i).getSectionId());
                        count++;

                    }

                }

                //        ArrayList<DocSection> selectedDocuments = new ArrayList<DocSection>();
                //        for (int i = 0; i < docList.length; i++) {
                //            selectedDocuments.add(DocSectionDB.getDocumentDataByDocId(Integer.parseInt(docList[i])));
                //            docContent[i] = ParseDocument.pdfDocToText(selectedDocuments.get(i).getSectiontContent());
                //            docNames[i] = selectedDocuments.get(i).getSectionName();
                //        }


                // int noOfDocSections = selectedDocuments.size();


                for (int i = 0; i < noOfFiboTerms; i++) {

                    docContent[noOfDocSecs + i] = fiboTermList.get(i).getFiboTerm() + fiboTermList.get(i).getFiboDefinition();

                    docNames[noOfDocSecs + i] = Integer.toString(fiboTermList.get(i).getFiboId());
                }



                DocIndexer docInd = new DocIndexer(docContent, docNames);
                int j = 0;


                /*
                 * returns <docSectionId, TreeMap<fiboId,okapiScore>>
                 */
//Okapi Cut off ==10.274
                HashMap<Integer, TreeMap> topKFiboTerms = docInd.topKFiboTerms(noOfDocSecs, docIds, 10.274);



                /*
                 * hashmap<docsectionId, list of Fiboterms>
                 */
                HashMap<Integer, ArrayList<FiboTerm>> topKFiboTermsDef = new HashMap<Integer, ArrayList<FiboTerm>>();
                FiboTerm tempFiboTerm;

                for (Map.Entry entryParent : topKFiboTerms.entrySet()) {
//                    HashMap<String, String> termDefMap = new HashMap<String, String>();
                    ArrayList<FiboTerm> termList = new ArrayList<FiboTerm>();
                    TreeMap<String, Double> sortedMap = (TreeMap<String, Double>) entryParent.getValue();
                    for (Map.Entry entryChild : sortedMap.entrySet()) {
                        tempFiboTerm = FiboDB.getFiboTermById(Integer.parseInt((String) entryChild.getKey()));
                        tempFiboTerm.setSimScore((Double) entryChild.getValue());
                        termList.add(tempFiboTerm);
                        // tempFiboTerm.
                        // termDefMap.put(tempFiboTerm.getFiboTerm().replaceAll("[\";\',.%$]()", " "), tempFiboTerm.getFiboDefinition().replaceAll("[\";\',.%$()]", " "));

                    }
                    topKFiboTermsDef.put((Integer) entryParent.getKey(), termList);
                }


                session.setAttribute("SelectedDocSectionMap", SelectedDocSectionMap);
                session.setAttribute("fiboTermList", fiboTermList);
                session.setAttribute("topKFiboTermsMap", topKFiboTermsDef);

                //releasing memeory to increase effciency

                session.removeAttribute("selectedDocsMap");


                // url = "/WEB-INF/view/picsentence.jsp";
                url = "/WEB-INF/view/recommondterm.jsp";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
            } catch (ParseException ex) {
                Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } 
        
        
        /*
         * recommod terms for selected sentences
         */ else if (userPath.equals("/recomondtermsforselections")) {
            //int selectedDocIds = Integer.parseInt(request.getParameter("selectedDocs"));

            try {
                // String url = null;
                DocSection tempDocSection;
                Document tempDocument;
                //int selectedDocID;
                int noOfDocSecs = 0;
                int noOfDocSecsSelectedSent = 0;


                /*
                 * getting all FIBO definitions
                 */
                ArrayList<FiboTerm> fiboTermList = new ArrayList<FiboTerm>();
                fiboTermList = FiboDB.getAllFiboTerms();
                int noOfFiboTerms = fiboTermList.size();

                /*
                 * get selected sentences for each doc section for creating a
                 * virtal doc from selected sentences
                 */


                //HashMap<Integer, ArrayList<String>> selectedDocSectionSentenceMap = (HashMap<Integer, ArrayList<String>>) session.getAttribute("selectedDocSectionSentenceMap");
                // HashMap<Integer, ArrayList<Phrase>> selectedDocSectionSentenceMap = (HashMap<Integer, ArrayList<Phrase>>) session.getAttribute("selectedDocSectionSentenceMap");
                HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>> selectedDocSectionSentenceMap = (HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>>) session.getAttribute("selectedDocSectionSentenceMap");


                //count no fo document sections in the selectedDocSectionSentenceMap
                if (!selectedDocSectionSentenceMap.isEmpty() && selectedDocSectionSentenceMap != null) {
                    for (Map.Entry entry : selectedDocSectionSentenceMap.entrySet()) {
                        noOfDocSecsSelectedSent = noOfDocSecsSelectedSent + 1;
                    }
                }

                /*
                 *
                 * docContentSelectedSent- to hold the documents of selected
                 * sentence + fibo definitions
                 */
                String[] docContentSelectedSent = new String[noOfDocSecsSelectedSent + noOfFiboTerms];
                String[] docNamesSelectedSent = new String[noOfDocSecsSelectedSent + noOfFiboTerms];
                String[] docIdsSelectedSent = new String[noOfDocSecsSelectedSent];



                int countSent = 0;
                if (!selectedDocSectionSentenceMap.isEmpty() && selectedDocSectionSentenceMap != null) {
                    //preparing data for okapi similarty calculation--> doc section data
                    for (Map.Entry entry : selectedDocSectionSentenceMap.entrySet()) {
                        // ArrayList<Phrase> docSectionsSent = (ArrayList<Phrase>) entry.getValue();
                        LinkedHashMap<Phrase, ArrayList<FiboTerm>> docSectionsSent = (LinkedHashMap<Phrase, ArrayList<FiboTerm>>) entry.getValue();

                        docNamesSelectedSent[countSent] = "doc" + Integer.toString(countSent);
                        docIdsSelectedSent[countSent] = Integer.toString((Integer) entry.getKey());

                        for (Map.Entry childEntry : docSectionsSent.entrySet()) {
                            docContentSelectedSent[countSent] = docContentSelectedSent[countSent] + " " + ((Phrase) childEntry.getKey()).getPhraseContent();

                        }

//                    for (int i = 0; i < docSectionsSent.size(); i++) {
//
//
//                        docContentSelectedSent[countSent] = docContentSelectedSent[countSent] + " " + docSectionsSent.get(i).getPhraseContent();
//
//
//                    }
                        countSent++;
                    }
                }


                //  HashMap<Integer, String> docsInText = (HashMap<Integer, String>) session.getAttribute("docSecContentMap");


                //hashmap<ParentDoc name, Array list doc sections>
                HashMap<String, ArrayList<DocSection>> SelectedDocSectionMap = (HashMap<String, ArrayList<DocSection>>) session.getAttribute("SelectedDocSectionMap");
                for (Map.Entry entry : SelectedDocSectionMap.entrySet()) {
                    noOfDocSecs = noOfDocSecs + ((ArrayList<DocSection>) entry.getValue()).size();
                }
                // Map.Entry entry:SelectedDocSectionMap.entrySet(); 






                /*
                 * docContent- to hold document text and fibo
                 * definitions(sepetrate documents)
                 */
                String[] docContent = new String[noOfDocSecs + noOfFiboTerms];
                String[] docNames = new String[noOfDocSecs + noOfFiboTerms];
                String[] docIds = new String[noOfDocSecs];




                int count = 0;

                //preparing data for okapi similarty calculation--> doc section data
                for (Map.Entry entry : SelectedDocSectionMap.entrySet()) {
                    ArrayList<DocSection> docSections = (ArrayList<DocSection>) entry.getValue();

                    for (int i = 0; i < docSections.size(); i++) {

                        docContent[count] = new String(DocSectionDB.getDocumentDataByDocId(docSections.get(i).getSectionId()).getSectiontContent());

                        //   docContent[count] = ParseDocument.pdfDocToText(DocSectionDB.getDocumentDataByDocId(docSections.get(i).getSectionId()).getSectiontContent());
                        docNames[count] = docSections.get(i).getSectionName();
                        docIds[count] = Integer.toString(docSections.get(i).getSectionId());
                        count++;

                    }

                }





                //        ArrayList<DocSection> selectedDocuments = new ArrayList<DocSection>();
                //        for (int i = 0; i < docList.length; i++) {
                //            selectedDocuments.add(DocSectionDB.getDocumentDataByDocId(Integer.parseInt(docList[i])));
                //            docContent[i] = ParseDocument.pdfDocToText(selectedDocuments.get(i).getSectiontContent());
                //            docNames[i] = selectedDocuments.get(i).getSectionName();
                //        }


                // int noOfDocSections = selectedDocuments.size();


                for (int i = 0; i < noOfFiboTerms; i++) {

                    docContent[noOfDocSecs + i] = fiboTermList.get(i).getFiboTerm() + fiboTermList.get(i).getFiboDefinition();

                    docNames[noOfDocSecs + i] = Integer.toString(fiboTermList.get(i).getFiboId());


                    docContentSelectedSent[noOfDocSecsSelectedSent + i] = fiboTermList.get(i).getFiboTerm() + fiboTermList.get(i).getFiboDefinition();
                    docNamesSelectedSent[noOfDocSecsSelectedSent + i] = Integer.toString(fiboTermList.get(i).getFiboId());


                }



               // DocIndexer docInd = new DocIndexer(docContent, docNames);
               DocIndexerTest docInd = new DocIndexerTest(docContent, docNames);
                
               // DocIndexer_test docInd = new DocIndexer_test(docContent, docNames);
                int j = 0;


            

              


                /*
                 * returns <docSectionId, TreeMap<fiboId,okapiScore>>
                 */
//Okapi cut off ==10.274
                HashMap<Integer, TreeMap> topKFiboTerms = docInd.topKFiboTerms(noOfDocSecs, docIds, 10.274);

  //HashMap<Integer, TreeMap> topKFiboTerms = docInd.topKFiboTerms(noOfDocSecs, docIds);
              
                /*
                 * hashmap<docsectionId, list of Fiboterms>
                 */
                HashMap<Integer, ArrayList<FiboTerm>> topKFiboTermsDef = new HashMap<Integer, ArrayList<FiboTerm>>();
               
                /////////////////////////////////////////////////////
                
               
                FiboTerm tempFiboTerm;
                if (topKFiboTerms != null && !topKFiboTerms.isEmpty()) {
                    for (Map.Entry entryParent : topKFiboTerms.entrySet()) {
//                    HashMap<String, String> termDefMap = new HashMap<String, String>();
                        ArrayList<FiboTerm> termList = new ArrayList<FiboTerm>();
                        TreeMap<String, Double> sortedMap = (TreeMap<String, Double>) entryParent.getValue();
                        for (Map.Entry entryChild : sortedMap.entrySet()) {
                            tempFiboTerm = FiboDB.getFiboTermById(Integer.parseInt((String) entryChild.getKey()));
                            tempFiboTerm.setSimScore((Double) entryChild.getValue());
                            termList.add(tempFiboTerm);
                            // tempFiboTerm.
                            // termDefMap.put(tempFiboTerm.getFiboTerm().replaceAll("[\";\',.%$]()", " "), tempFiboTerm.getFiboDefinition().replaceAll("[\";\',.%$()]", " "));

                        }
                        topKFiboTermsDef.put((Integer) entryParent.getKey(), termList);
                    }
                }


//////////////////////////////////////////



                /*
                 * doc indexer for selected sentences
                 */

                DocIndexerTest docIndSelectedSent = new DocIndexerTest(docContentSelectedSent, docNamesSelectedSent);

 //DocIndexer_test docIndSelectedSent = new DocIndexer_test(docContentSelectedSent, docNamesSelectedSent);


                /*
                 * returns <docSectionId, TreeMap<fiboId,okapiScore>> for
                 * selected sentences
                 */
//Okapi Cut Off value==5.274
                
                
                HashMap<Integer, TreeMap> topKFiboTermsSelectedSent = docIndSelectedSent.topKFiboTerms(noOfDocSecsSelectedSent, docIdsSelectedSent, 5.274);
//HashMap<Integer, TreeMap> topKFiboTermsSelectedSent = docIndSelectedSent.topKFiboTerms(noOfDocSecsSelectedSent, docIdsSelectedSent);


                /*
                 * hashmap<docsectionId, list of Fiboterms>
                 */
                HashMap<Integer, ArrayList<FiboTerm>> topKFiboTermsDefSelectedSent = new HashMap<Integer, ArrayList<FiboTerm>>();
                
                /////////////////////////////////////////////////////////////////////////////
                
                
                FiboTerm tempFiboTermSelectedSent;
                if (topKFiboTermsSelectedSent != null && !topKFiboTermsSelectedSent.isEmpty()) {
                    for (Map.Entry entryParent : topKFiboTermsSelectedSent.entrySet()) {
//                    HashMap<String, String> termDefMap = new HashMap<String, String>();
                        ArrayList<FiboTerm> termListSelectedSent = new ArrayList<FiboTerm>();
                        TreeMap<String, Double> sortedMapSelectedSent = (TreeMap<String, Double>) entryParent.getValue();
                        for (Map.Entry entryChild : sortedMapSelectedSent.entrySet()) {
                            tempFiboTermSelectedSent = FiboDB.getFiboTermById(Integer.parseInt((String) entryChild.getKey()));
                            tempFiboTermSelectedSent.setSimScore((Double) entryChild.getValue());
                            termListSelectedSent.add(tempFiboTermSelectedSent);
                            // tempFiboTerm.
                            // termDefMap.put(tempFiboTerm.getFiboTerm().replaceAll("[\";\',.%$]()", " "), tempFiboTerm.getFiboDefinition().replaceAll("[\";\',.%$()]", " "));

                        }
                        topKFiboTermsDefSelectedSent.put((Integer) entryParent.getKey(), termListSelectedSent);
                    }
                }
                
                ////////////////////////////////////////////////////

                session.setAttribute("SelectedDocSectionMap", SelectedDocSectionMap);
                session.setAttribute("fiboTermList", fiboTermList);
                session.setAttribute("topKFiboTermsMap", topKFiboTermsDef);
                session.setAttribute("topKFiboTermsMapSelectedSent", topKFiboTermsDefSelectedSent);
                //releasing memeory to increase effciency

                // session.removeAttribute("selectedDocsMap");


                // url = "/WEB-INF/view/picsentence.jsp";
                url = "/WEB-INF/view/termsselectedsent.jsp";
               // url = "/WEB-INF/view/test.jsp";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
            } 
            
            
            //catch (ParseException ex) {
            //    ex.printStackTrace();
               // Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
         //   } catch (ClassNotFoundException ex) {
         //       ex.printStackTrace();
               // Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
           // }
        catch (Exception ex) {
                ex.printStackTrace();
               // Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } /*
         * get doc_section seperated in to sentences and send to the JSP page
         */ else if (userPath.equals("/selectsentences")) {
            //int selectedDocIds = Integer.parseInt(request.getParameter("selectedDocs"));

            try {
                // String url = null;
                DocSection tempDocSection;
                Document tempDocument;
                //int selectedDocID;
                int noOfDocSecs = 0;


                //  HashMap<Integer, String> docsInText = (HashMap<Integer, String>) session.getAttribute("docSecContentMap");


                //hashmap<ParentDoc name, Array list doc sections>
                HashMap<String, ArrayList<DocSection>> SelectedDocSectionMap = (HashMap<String, ArrayList<DocSection>>) session.getAttribute("selectedDocsMap");
                //hashmap<Doc Section ID, Array list of sentences>
                HashMap<Integer, ArrayList<String>> DocSectionSentenceMap = new HashMap<Integer, ArrayList<String>>();
                //HashMap<Integer, ArrayList<Phrase>> selectedDocSectionSentenceMap = new HashMap<Integer, ArrayList<Phrase>>();

                //hashMap<Doc_secID, LinkedHashMap<Phrase,ArrayList<FIBOterm>>>
                HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>> selectedDocSectionSentenceMap = new HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>>();

                for (Map.Entry entry : SelectedDocSectionMap.entrySet()) {
                    ArrayList<DocSection> docSections = (ArrayList<DocSection>) entry.getValue();

                    for (int i = 0; i < docSections.size(); i++) {

                        ArrayList<String> sentenceList = new ArrayList<String>();
                        ParseDocument doc = new ParseDocument();

                        sentenceList = doc.txtDocToSentence(new String(DocSectionDB.getDocumentDataByDocId(docSections.get(i).getSectionId()).getSectiontContent()));

                        DocSectionSentenceMap.put(docSections.get(i).getSectionId(), sentenceList);
                    }

                }


                /*
                 * getting all FIBO definitions
                 */
//                ArrayList<FiboTerm> fiboTermList = new ArrayList<FiboTerm>();
//                fiboTermList = FiboDB.getAllFiboTerms();
//                int noOfFiboTerms = fiboTermList.size();


                session.setAttribute("SelectedDocSectionMap", SelectedDocSectionMap);
                session.setAttribute("DocSectionSentenceMap", DocSectionSentenceMap);
                session.setAttribute("selectedDocSectionSentenceMap", selectedDocSectionSentenceMap);

                //   session.setAttribute("fiboTermList", fiboTermList);
                //releasing memeory to increase effciency

                session.removeAttribute("selectedDocsMap");


                url = "/WEB-INF/view/picsentence.jsp";
                // url = "/WEB-INF/view/recommondterm.jsp";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
            } catch (Exception ex) {
                Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
//        url = "/WEB-INF/view" + userPath + ".jsp";
//
//        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
//        dispatcher.forward(request, response);
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


        if (userPath.equals("/selectedsections")) {
            try {
                String url = null;
                DocSection tempDocSection;
                Document tempDocument;

                int selectedDocID;
                int noOfDocSecs = 0;

                //  HashMap<Integer, String> docsInText = (HashMap<Integer, String>) session.getAttribute("docSecContentMap");


                //hashmap<ParentDoc name, Array list doc sections>
                HashMap<String, ArrayList<DocSection>> SelectedDocSectionMap = new HashMap<String, ArrayList<DocSection>>();

                //Get the selected checkboxes form the viewsection.jsp 
                String[] selectedDocs = (request.getParameter("selected_check_boxes").toString()).split("\t");
                //String[] selectedChekBoxList=request.getParameterValues("selected_check_boxes");
                for (int i = 0; i < selectedDocs.length; i++) {
                    try {
                        selectedDocID = Integer.parseInt(selectedDocs[i]);
                        tempDocSection = DocSectionDB.getDocumentDataByDocId(selectedDocID);
                        //get the parent doc of the selected doc section
                        tempDocument = DocumentDB.getDocumentByDocId(tempDocSection.getParentDocId());

                        //create a Map with the selected docSections of viewsection.jsp 
                        if (SelectedDocSectionMap.containsKey(tempDocument.getDocumentName())) { //if hashmap already contains name of the parent document.

                            SelectedDocSectionMap.get(tempDocument.getDocumentName()).add(tempDocSection);
                        } else {
                            ArrayList<DocSection> docSections = new ArrayList<DocSection>();
                            docSections.add(tempDocSection);
                            SelectedDocSectionMap.put(tempDocument.getDocumentName(), docSections);
                        }
                        noOfDocSecs++;
                    } catch (NumberFormatException e) {
                        continue;
                    }
                }


                //String[] docList = {"1", "2", "3", "12", "9", "10", "7", "8"};
                // int noOfDocSecs = docList.length;

                /*
                 * getting all FIBO definitions @param fiboTermList- contains al
                 * the fibo term objects(id, name , definition)
                 */
                ArrayList<FiboTerm> fiboTermList = new ArrayList<FiboTerm>();
                fiboTermList = FiboDB.getAllFiboTerms();
                int noOfFiboTerms = fiboTermList.size();

                /*
                 * Okapi calculation should receive all the documents for
                 * similarity calculation in a one String array Here it merges
                 * document data and FIBO data in to corresponding arrays @param
                 * docContent- to hold document text and fibo
                 * definitions(sepetrate documents)
                 *
                 */
                String[] docContent = new String[noOfDocSecs + noOfFiboTerms];
                String[] docNames = new String[noOfDocSecs + noOfFiboTerms];
                String[] docIds = new String[noOfDocSecs];

                int count = 0;

                /*
                 *
                 * preparing data for okapi similarty calculation putting bond
                 * document data to the docContent,docNames,docIds
                 */
                for (Map.Entry entry : SelectedDocSectionMap.entrySet()) {
                    ArrayList<DocSection> docSections = (ArrayList<DocSection>) entry.getValue();

                    for (int i = 0; i < docSections.size(); i++) {

                        docContent[count] = new String(DocSectionDB.getDocumentDataByDocId(docSections.get(i).getSectionId()).getSectiontContent());

                        // docContent[count] = ParseDocument.pdfDocToText(DocSectionDB.getDocumentDataByDocId(docSections.get(i).getSectionId()).getSectiontContent());
                        docNames[count] = docSections.get(i).getSectionName();
                        docIds[count] = Integer.toString(docSections.get(i).getSectionId());
                        count++;

                    }

                }

                //        ArrayList<DocSection> selectedDocuments = new ArrayList<DocSection>();
                //        for (int i = 0; i < docList.length; i++) {
                //            selectedDocuments.add(DocSectionDB.getDocumentDataByDocId(Integer.parseInt(docList[i])));
                //            docContent[i] = ParseDocument.pdfDocToText(selectedDocuments.get(i).getSectiontContent());
                //            docNames[i] = selectedDocuments.get(i).getSectionName();
                //        }


                // int noOfDocSections = selectedDocuments.size();

                /*
                 * putting FIBO data in to docSim calculation input arrays,
                 * after allocating documents
                 */
                for (int i = 0; i < noOfFiboTerms; i++) {

                    docContent[noOfDocSecs + i] = fiboTermList.get(i).getFiboTerm() + fiboTermList.get(i).getFiboDefinition();

                    docNames[noOfDocSecs + i] = Integer.toString(fiboTermList.get(i).getFiboId());
                }



                DocIndexerTest docInd = new DocIndexerTest(docContent, docNames);
                int j = 0;


                /*
                 * returns <docSectionId, TreeMap<fiboId,okapiScore>>
                 */
//Okapi Cut Off value==10.274
                HashMap<Integer, TreeMap> topKFiboTerms = docInd.topKFiboTerms(noOfDocSecs, docIds, 10.274);



                /*
                 * hashmap<docsectionId, list of Fiboterms>
                 */
                HashMap<Integer, ArrayList<FiboTerm>> topKFiboTermsDef = new HashMap<Integer, ArrayList<FiboTerm>>();
                FiboTerm tempFiboTerm;
if (topKFiboTerms != null && !topKFiboTerms.isEmpty()) {
                for (Map.Entry entryParent : topKFiboTerms.entrySet()) {
//                    HashMap<String, String> termDefMap = new HashMap<String, String>();
                    ArrayList<FiboTerm> termList = new ArrayList<FiboTerm>();
                    TreeMap<String, Double> sortedMap = (TreeMap<String, Double>) entryParent.getValue();
                    for (Map.Entry entryChild : sortedMap.entrySet()) {
                        tempFiboTerm = FiboDB.getFiboTermById(Integer.parseInt((String) entryChild.getKey()));
                        tempFiboTerm.setSimScore((Double) entryChild.getValue());
                        termList.add(tempFiboTerm);
                        // tempFiboTerm.
                        // termDefMap.put(tempFiboTerm.getFiboTerm().replaceAll("[\";\',.%$]()", " "), tempFiboTerm.getFiboDefinition().replaceAll("[\";\',.%$()]", " "));

                    }
                    topKFiboTermsDef.put((Integer) entryParent.getKey(), termList);
                }
}

                session.setAttribute("SelectedDocSectionMap", SelectedDocSectionMap);
                session.setAttribute("fiboTermList", fiboTermList);
                session.setAttribute("topKFiboTermsMap", topKFiboTermsDef);

                //releasing memeory to increase effciency

                session.removeAttribute("selectedDocsMap");


                url = "/WEB-INF/view/recommondterm.jsp";
                RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
                dispatcher.forward(request, response);
            } catch (ParseException ex) {
                ex.printStackTrace();
               // Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
               ex.printStackTrace();
                       
                //Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                
                ex.printStackTrace();
                //Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            }

        } //add recommonded FIBO terms, requested by user in recommondterm.jsp
        else if (userPath.equals("/addrows")) {
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
                FiboTerm newTerm = new FiboTerm();
                newTerm.setFiboTerm(fiboTerm);
                newTerm.setFiboDefinition(fiboTermDef);
                newTerm.setSimScore(Double.parseDouble(okapiScore));
                newTerm.setFiboId(FiboDB.getFiboTermID(fiboTerm).getFiboId());
                fibotermsForDoc.add(newTerm);

                session.setAttribute("topKFiboTermsMap", topKFiboTermsMap);
            } catch (Exception ex) {
                Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            }




            writer.write("docContentIntext");
            writer.close();
        } //add recommonded FIBO terms, requested by user in recommondterm.jsp
        else if (userPath.equals("/addrowssup")) {
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

                HashMap<Integer, ArrayList<FiboTerm>> topKFiboTermsMap = (HashMap<Integer, ArrayList<FiboTerm>>) session.getAttribute("topKFiboTermsMapSelectedSent");
//ArrayList<FiboTerm> termList

                if (topKFiboTermsMap.containsKey(Integer.parseInt(docSectionID))) {
                    ArrayList<FiboTerm> fibotermsForDoc = (ArrayList<FiboTerm>) topKFiboTermsMap.get(Integer.parseInt(docSectionID));
                    FiboTerm newTerm = new FiboTerm();
                    newTerm.setFiboTerm(fiboTerm);
                    newTerm.setFiboDefinition(fiboTermDef);
                    newTerm.setSimScore(Double.parseDouble(okapiScore));
                    newTerm.setFiboId(FiboDB.getFiboTermID(fiboTerm).getFiboId());
                    fibotermsForDoc.add(newTerm);
                } else {
                    ArrayList<FiboTerm> fibotermsForDoc = new ArrayList<FiboTerm>();
                    FiboTerm newTerm = new FiboTerm();
                    newTerm.setFiboTerm(fiboTerm);
                    newTerm.setFiboDefinition(fiboTermDef);
                    newTerm.setSimScore(Double.parseDouble(okapiScore));
                    newTerm.setFiboId(FiboDB.getFiboTermID(fiboTerm).getFiboId());
                    fibotermsForDoc.add(newTerm);

                    topKFiboTermsMap.put(Integer.parseInt(docSectionID), fibotermsForDoc);
                }
                session.setAttribute("topKFiboTermsMapSelectedSent", topKFiboTermsMap);
            } catch (Exception ex) {
                Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            }




            writer.write("docContentIntext");
            writer.close();
        } /*
         * When a sentece is selected from the all avaiable sentences.
         */ else if (userPath.equals("/addsentence")) {

            String SentenceToAdd = "";
            String docSectionIDToAdd = "";
            try {
                if (request.getParameter("sentence") != null) {
                    SentenceToAdd = request.getParameter("sentence").toString();
                }

                if (request.getParameter("docSecId") != null) {
                    docSectionIDToAdd = request.getParameter("docSecId").toString();
                }

                /*
                 * removing sentence from the the DocSectionSentenceMap
                 */
                HashMap<Integer, ArrayList<String>> DocSectionSentenceMap = (HashMap<Integer, ArrayList<String>>) session.getAttribute("DocSectionSentenceMap");
                ArrayList<String> sentecesForDoc = (ArrayList<String>) DocSectionSentenceMap.get(Integer.parseInt(docSectionIDToAdd));
                sentecesForDoc.remove(SentenceToAdd);

                /*
                 * adding sentece for the selected docSection Map
                 */
                HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>> selectedDocSectionSentenceMap = (HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>>) session.getAttribute("selectedDocSectionSentenceMap");



//

                if (selectedDocSectionSentenceMap.containsKey(Integer.parseInt(docSectionIDToAdd))) {
                    LinkedHashMap<Phrase, ArrayList<FiboTerm>> selectedSentForDoc = (LinkedHashMap<Phrase, ArrayList<FiboTerm>>) selectedDocSectionSentenceMap.get(Integer.parseInt(docSectionIDToAdd));
                    Phrase newSentence = new Phrase();
                    ArrayList<FiboTerm> fiboTerms = new ArrayList<FiboTerm>();
                    newSentence.setPhraseContent(SentenceToAdd);

                    selectedSentForDoc.put(newSentence, fiboTerms);
                } else {
                    LinkedHashMap<Phrase, ArrayList<FiboTerm>> newMap = new LinkedHashMap<Phrase, ArrayList<FiboTerm>>();
                    ArrayList<FiboTerm> fiboTerms = new ArrayList<FiboTerm>();
                    Phrase newSentence = new Phrase();
                    newSentence.setPhraseContent(SentenceToAdd);

                    newMap.put(newSentence, fiboTerms);

                    selectedDocSectionSentenceMap.put(Integer.parseInt(docSectionIDToAdd), newMap);
                }




                session.setAttribute("DocSectionSentenceMap", DocSectionSentenceMap);
                session.setAttribute("selectedDocSectionSentenceMap", selectedDocSectionSentenceMap);
            } catch (Exception ex) {
                ex.printStackTrace();
               // Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            }
            writer.write("docContentIntext2");
            writer.close();
        } // $.post("updatesentence", { docSecId: currentDocId, sentence: ""+rowdata["sentence"], importance:""+rowdata["importance"] }, function(responce){
        /*
         * When a sentece is updated with importance.
         */ else if (userPath.equals("/updatesentence")) {

            String SentenceToModify = "";
            String docSectionIDToModify = "";
            String importance = "";
            try {
                if (request.getParameter("sentence") != null) {
                    SentenceToModify = request.getParameter("sentence").toString();
                }

                if (request.getParameter("docSecId") != null) {
                    docSectionIDToModify = request.getParameter("docSecId").toString();
                }
                if (request.getParameter("docSecId") != null) {
                    importance = request.getParameter("importance").toString();
                }


                /*
                 * adding sentece for the selected docSection Map
                 */


                HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>> selectedDocSectionSentenceMap = (HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>>) session.getAttribute("selectedDocSectionSentenceMap");



                if (selectedDocSectionSentenceMap.containsKey(Integer.parseInt(docSectionIDToModify))) { //if hashmap already contains name of the parent document.

                    LinkedHashMap<Phrase, ArrayList<FiboTerm>> sentenceList = (LinkedHashMap<Phrase, ArrayList<FiboTerm>>) selectedDocSectionSentenceMap.get(Integer.parseInt(docSectionIDToModify));
                    for (Map.Entry entry : sentenceList.entrySet()) {
                        if (((Phrase) entry.getKey()).getPhraseContent().equals(SentenceToModify)) {
                            ((Phrase) entry.getKey()).setImportance(importance);
                            // fibotermsForDoc.remove(i);
                            break;
                        }
                    }



                }


                session.setAttribute("selectedDocSectionSentenceMap", selectedDocSectionSentenceMap);
            } catch (Exception ex) {
                ex.printStackTrace();
               // Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            }
            writer.write("docContentIntext2");
            writer.close();
        } /*
         * When a sentece is removed from the selected sentences.
         */ else if (userPath.equals("/removesentence")) {

            String SentenceToDel = "";
            String docSectionIDToDel = "";
            try {
                if (request.getParameter("sentence") != null) {
                    SentenceToDel = request.getParameter("sentence").toString();
                }

                if (request.getParameter("docSecId") != null) {
                    docSectionIDToDel = request.getParameter("docSecId").toString();
                }

                /*
                 * removing sentence from the the selectedDocSectionSentenceMap
                 */
                HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>> selectedDocSectionSentenceMap = (HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>>) session.getAttribute("selectedDocSectionSentenceMap");
                LinkedHashMap<Phrase, ArrayList<FiboTerm>> selectedSentecesForDoc = (LinkedHashMap<Phrase, ArrayList<FiboTerm>>) selectedDocSectionSentenceMap.get(Integer.parseInt(docSectionIDToDel));



                for (Map.Entry entry : selectedSentecesForDoc.entrySet()) {
                    if (((Phrase) entry.getKey()).getPhraseContent().equals(SentenceToDel)) {

                        selectedSentecesForDoc.remove((Phrase) entry.getKey());
                        break;
                    }
                }
//                for (int i = 0; i < selectedSentecesForDoc.size(); i++) {
//                    if (selectedSentecesForDoc.get(i).getPhraseContent().equals(SentenceToDel)) {
//
//                        selectedSentecesForDoc.remove(i);
//                        break;
//                    }
//                }

                /*
                 * adding sentece for the selected DocSectionSentenceMap
                 */
                HashMap<Integer, ArrayList<String>> DocSectionSentenceMap = (HashMap<Integer, ArrayList<String>>) session.getAttribute("DocSectionSentenceMap");




                if (DocSectionSentenceMap.containsKey(Integer.parseInt(docSectionIDToDel))) { //if hashmap already contains name of the parent document.

                    DocSectionSentenceMap.get(Integer.parseInt(docSectionIDToDel)).add(SentenceToDel);
                } else {
                    ArrayList<String> selcSenences = new ArrayList<String>();
                    selcSenences.add(SentenceToDel);
                    DocSectionSentenceMap.put(Integer.parseInt(docSectionIDToDel), selcSenences);
                }


                // HashMap<Integer, HashMap> topKFiboTermsMap = (HashMap<Integer, HashMap>) session.getAttribute("topKFiboTermsMap");

                // HashMap<String, String> fibotermsForDoc = (HashMap<String, String>) topKFiboTermsMap.get(Integer.parseInt(docSectionIDToDel));
                //  fibotermsForDoc.remove(fiboTermToDel);
                session.setAttribute("DocSectionSentenceMap", DocSectionSentenceMap);
                session.setAttribute("selectedDocSectionSentenceMap", selectedDocSectionSentenceMap);
            } catch (Exception ex) {
                ex.printStackTrace();
              //  Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            }
            writer.write("docContentIntext2");
            writer.close();
        } //delete the recommonded FIBO terms, requested by user in recommondterm.jsp
        else if (userPath.equals("/deleterows")) {

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
        } /*
         * delete rows of the
         */ else if (userPath.equals("/deleterowssup")) {

            String fiboTermToDel = "";
            String docSectionIDToDel = "";
            try {
                if (request.getParameter("fiboTerm") != null) {
                    fiboTermToDel = request.getParameter("fiboTerm").toString();
                }

                if (request.getParameter("docSecId") != null) {
                    docSectionIDToDel = request.getParameter("docSecId").toString();
                }

                HashMap<Integer, ArrayList<FiboTerm>> topKFiboTermsMap = (HashMap<Integer, ArrayList<FiboTerm>>) session.getAttribute("topKFiboTermsMapSelectedSent");
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
                session.setAttribute("topKFiboTermsMapSelectedSent", topKFiboTermsMap);
            } catch (Exception ex) {
                Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            }
            writer.write("docContentIntext2");
            writer.close();
        } /*
         * to get selected sentences from the doc section
         */ else if (userPath.equals("/selectedsentences")) {
            String fiboTermNDefinition = "";
            String docSectionID = "";
            String docContentIntext = "";
            try {


                if (request.getParameter("docSecId") != null) {
                    docSectionID = request.getParameter("docSecId").toString();

//
//                    
                    LinkedHashMap<Phrase, ArrayList<FiboTerm>> sentenceList = new LinkedHashMap<Phrase, ArrayList<FiboTerm>>();

                    //HashMap<Integer, ArrayList<Phrase>> map = (HashMap<Integer, ArrayList<Phrase>>) session.getAttribute("selectedDocSectionSentenceMap");
                    HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>> map = (HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>>) session.getAttribute("selectedDocSectionSentenceMap");

//                    ParseDocument doc = new ParseDocument();
//                    sentenceList = doc.pdfDocToSentence(document.getSectiontContent());
                    sentenceList = (LinkedHashMap<Phrase, ArrayList<FiboTerm>>) map.get(Integer.parseInt(docSectionID));

                    for (Map.Entry entry : sentenceList.entrySet()) {
                        if (docContentIntext.isEmpty()) {
                            docContentIntext = ((Phrase) entry.getKey()).getPhraseContent() + "$$**$$" + ((Phrase) entry.getKey()).getImportance();
                        } else {
                            docContentIntext = ((Phrase) entry.getKey()).getPhraseContent() + "$$**$$" + ((Phrase) entry.getKey()).getImportance() + "$$##$$" + docContentIntext;
                        }
                    }

//                    for (int i = 0; i < sentenceList.size(); i++) {
//                        if (docContentIntext.isEmpty()) {
//                            docContentIntext = sentenceList.get(i).getPhraseContent() + "$$**$$" + sentenceList.get(i).getImportance();
//                        } else {
//                            docContentIntext =  sentenceList.get(i).getPhraseContent() + "$$**$$" + sentenceList.get(i).getImportance()+ "$$##$$" +docContentIntext ;
//                        }
//
//                    }

                    writer.write(docContentIntext);
                    writer.close();
                }
            } catch (Exception ex) {
                ex.getStackTrace();
            }
            writer.write(docContentIntext);
            writer.close();
        } /*
         * to get all sentences from the doc section
         */ else if (userPath.equals("/allsentences")) {
            String fiboTermNDefinition = "";
            String docSectionID = "";
            String docContentIntext = "";
            try {


                if (request.getParameter("docSecId") != null) {
                    docSectionID = request.getParameter("docSecId").toString();

//
//                    DocSection document = DocSectionDB.getDocumentDataByDocId(Integer.valueOf(docSectionID));
//                    //  String docContentIntext = ParseDocument.pdfDocToText(document.getSectiontContent()).replaceAll("[\";\'%$()]", " ");

                    ArrayList<String> sentenceList = new ArrayList<String>();
                    HashMap<Integer, ArrayList<String>> map = (HashMap<Integer, ArrayList<String>>) session.getAttribute("DocSectionSentenceMap");

//                    ParseDocument doc = new ParseDocument();
//                    sentenceList = doc.pdfDocToSentence(document.getSectiontContent());
                    sentenceList = (ArrayList<String>) map.get(Integer.parseInt(docSectionID));
                    if (sentenceList != null && !sentenceList.isEmpty()) {
                        for (int i = 0; i < sentenceList.size(); i++) {
                            if (docContentIntext.isEmpty()) {
                                docContentIntext = sentenceList.get(i);
                            } else {
                                docContentIntext = sentenceList.get(i) + "$$##$$" + docContentIntext;
                            }
                        }
                    }

                    writer.write(docContentIntext);
                    writer.close();
                }
            } catch (Exception ex) {
                ex.getStackTrace();
            }
            writer.write(docContentIntext);
            writer.close();
        } //to get recommended terms for a perticuler docsection
        else if (userPath.equals("/getterms")) {
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
        } //to get recommended terms for a selected sentences?
        else if (userPath.equals("/gettermssent")) {
            String fiboTermNDefinition = "";
            String docSectionID = "";
            try {


                if (request.getParameter("docSecId") != null) {
                    docSectionID = request.getParameter("docSecId").toString();
                }


                // Map<Integer, ArrayList<FiboTerm>>  topKFiboTermsMap = (HashMap<Integer, ArrayList<FiboTerm>> ) session.getAttribute("topKFiboTermsMap");
//ArrayList<FiboTerm> termList
                //ArrayList<FiboTerm> fibotermsForDoc = (ArrayList<FiboTerm>) topKFiboTermsMap.get(Integer.parseInt(docSectionIDToDel));

                Map<Integer, ArrayList<FiboTerm>> topKFiboTermsMap = (Map<Integer, ArrayList<FiboTerm>>) session.getAttribute("topKFiboTermsMapSelectedSent");

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
