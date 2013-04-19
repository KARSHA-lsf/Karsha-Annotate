/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.controler;

import org.karsha.entities.Phrase;

import org.karsha.entities.Document;
import org.karsha.entities.MatchSectionFibo;
import org.karsha.entities.FiboTerm;
import org.karsha.data.PhraseDB;
import org.karsha.data.MatchSectionFiboDB;
import org.karsha.data.FiboDB;

import org.karsha.data.DocumentDB;
import java.io.IOException;
import java.util.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.karsha.data.*;
import org.karsha.entities.*;

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
 * Date Author Changes April 14, 2012 Kasun Perera Created Jun 18, 2012 Kasun
 * Perera Modified/Removed Unwanted data Structure
 */
public class SaveDataServelet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;

        if (userPath.equals("/savedata")) {


            ArrayList<MatchSectionFibo> matchSectionFibo = new ArrayList<MatchSectionFibo>();

            int userId = Integer.parseInt(session.getAttribute("userId").toString());
            //hashmap<docSectionID,Hashmap<fiboterm,fibodef>>
            HashMap<Integer, ArrayList<FiboTerm>> topKFiboTermsMap = (HashMap<Integer, ArrayList<FiboTerm>>) session.getAttribute("topKFiboTermsMap");
            //hashmap<parentdoc, arraylist of doc section>
            HashMap<String, ArrayList<DocSection>> SelectedDocSectionMap = (HashMap<String, ArrayList<DocSection>>) session.getAttribute("SelectedDocSectionMap");
            // ArrayList<FiboTerm> fiboTermList = (ArrayList<FiboTerm>) session.getAttribute("SelectedDocSectionMap");

            ArrayList<DocSection> tempDocSectionList = new ArrayList<DocSection>();
            ArrayList<FiboTerm> tempFiboTerms = new ArrayList<FiboTerm>();
            ArrayList<MatchSectionFibo> tempSectionFiboData = new ArrayList<MatchSectionFibo>();

            for (Map.Entry entry : SelectedDocSectionMap.entrySet()) {

                tempDocSectionList = (ArrayList<DocSection>) entry.getValue();
                for (int i = 0; i < tempDocSectionList.size(); i++) {
                    tempFiboTerms = topKFiboTermsMap.get(tempDocSectionList.get(i).getSectionId());

                    if (tempFiboTerms != null && !tempFiboTerms.isEmpty()) {
                        for (int j = 0; j < tempFiboTerms.size(); j++) {
                            MatchSectionFibo tempMatchSectionFifo = new MatchSectionFibo();
                            tempMatchSectionFifo.setFiboTermId(tempFiboTerms.get(j).getFiboId());
                            tempMatchSectionFifo.setSectionId(tempDocSectionList.get(i).getSectionId());
                            tempMatchSectionFifo.setSimScore(tempFiboTerms.get(j).getSimScore());
                            tempMatchSectionFifo.setUserId(userId);
                            tempSectionFiboData.add(tempMatchSectionFifo);

                        }
                        tempFiboTerms.clear();
                    }
                }
                tempDocSectionList.clear();
            }


            MatchSectionFiboDB.saveMatchSectionFiboDB(tempSectionFiboData);

            session.setAttribute("savesucces", "Annotation Data Saved Succesfully");
            userPath = "/createcollection";
        } else if (userPath.equals("/savedocmarkupdata")) {


            ArrayList<MatchSectionFibo> matchSectionFibo = new ArrayList<MatchSectionFibo>();

            int userId = Integer.parseInt(session.getAttribute("userId").toString());
            //hashmap<docSectionID,Hashmap<fiboterm,fibodef>>
            HashMap<Integer, ArrayList<FiboTerm>> topKFiboTermsMap = (HashMap<Integer, ArrayList<FiboTerm>>) session.getAttribute("topKFiboTermsMap");
            //hashmap<parentdoc, arraylist of doc section>
            HashMap<String, ArrayList<DocSection>> SelectedDocSectionMap = (HashMap<String, ArrayList<DocSection>>) session.getAttribute("SelectedDocSectionMap");
            // ArrayList<FiboTerm> fiboTermList = (ArrayList<FiboTerm>) session.getAttribute("SelectedDocSectionMap");

            ArrayList<DocSection> tempDocSectionList = new ArrayList<DocSection>();
            ArrayList<FiboTerm> tempFiboTerms = new ArrayList<FiboTerm>();
            ArrayList<MatchSectionFibo> tempSectionFiboData = new ArrayList<MatchSectionFibo>();

            for (Map.Entry entry : SelectedDocSectionMap.entrySet()) {

                tempDocSectionList = (ArrayList<DocSection>) entry.getValue();
                for (int i = 0; i < tempDocSectionList.size(); i++) {
                    tempFiboTerms = topKFiboTermsMap.get(tempDocSectionList.get(i).getSectionId());
                    if (tempFiboTerms != null && !tempFiboTerms.isEmpty()) {

                        for (int j = 0; j < tempFiboTerms.size(); j++) {
                            MatchSectionFibo tempMatchSectionFifo = new MatchSectionFibo();
                            tempMatchSectionFifo.setFiboTermId(tempFiboTerms.get(j).getFiboId());
                            tempMatchSectionFifo.setSectionId(tempDocSectionList.get(i).getSectionId());
                            tempMatchSectionFifo.setSimScore(tempFiboTerms.get(j).getSimScore());
                            tempMatchSectionFifo.setUserId(userId);
                            tempSectionFiboData.add(tempMatchSectionFifo);

                        }


                        tempFiboTerms.clear();
                    }
                }
                tempDocSectionList.clear();
            }


            MatchSectionFiboDB.saveMatchSectionFiboDB(tempSectionFiboData);

            session.setAttribute("savesucces", "Annotation Data Saved Succesfully");
            userPath = "/createdoccollection";
        } else if (userPath.equals("/saveselectedsentences")) {

            //TO_DO save selected sentences
            int userId = Integer.parseInt(session.getAttribute("userId").toString());
            //senteces selected and marked the importance but NO FIBO terms for the doc_sections + Sentences not markedup with FIBo terms


            /*
             * saving the selected sentences
             */
            HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>> selectedDocSectionSentenceMap = (HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>>) session.getAttribute("selectedDocSectionSentenceMap");

            ArrayList<Phrase> listOfPhrase = new ArrayList<Phrase>();
            for (Map.Entry parentEntry : selectedDocSectionSentenceMap.entrySet()) {
                for (Map.Entry childEntry : ((LinkedHashMap<Phrase, ArrayList<FiboTerm>>) parentEntry.getValue()).entrySet()) {
                    ((Phrase) childEntry.getKey()).setDocId((Integer) parentEntry.getKey());
                    ((Phrase) childEntry.getKey()).setUserId(userId);
                    String importance = ((Phrase) childEntry.getKey()).getImportance();
                    if ("V. Importanr".equals(importance)) {
                        ((Phrase) childEntry.getKey()).setScore(3);
                    } else if ("Important".equals(importance)) {
                        ((Phrase) childEntry.getKey()).setScore(2);
                    } else if ("Average".equals(importance)) {
                        ((Phrase) childEntry.getKey()).setScore(1);
                    } else {
                        ((Phrase) childEntry.getKey()).setScore(0);
                    }

                    listOfPhrase.add((Phrase) childEntry.getKey());
                }

            }



            PhraseDB.saveTopKPhrses(listOfPhrase);
            session.setAttribute("savesucces", "Annotation Data Saved Succesfully");
            userPath = "/createcollection";
        } else if (userPath.equals("/savesentencedata")) {

            //TO_DO save selected sentences
            int userId = Integer.parseInt(session.getAttribute("userId").toString());
            // /senteces selected and marked the importance + Recommonded FIBO terms for doc_section + Setences NOT marked up with FIBO terms


            /*
             * saving the selected sentences
             */
            HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>> selectedDocSectionSentenceMap = (HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>>) session.getAttribute("selectedDocSectionSentenceMap");

            ArrayList<Phrase> listOfPhrase = new ArrayList<Phrase>();
            for (Map.Entry parentEntry : selectedDocSectionSentenceMap.entrySet()) {
                for (Map.Entry childEntry : ((LinkedHashMap<Phrase, ArrayList<FiboTerm>>) parentEntry.getValue()).entrySet()) {
                    ((Phrase) childEntry.getKey()).setDocId((Integer) parentEntry.getKey());
                    ((Phrase) childEntry.getKey()).setUserId(userId);
                    String importance = ((Phrase) childEntry.getKey()).getImportance();
                    if ("V. Importanr".equals(importance)) {
                        ((Phrase) childEntry.getKey()).setScore(3);
                    } else if ("Important".equals(importance)) {
                        ((Phrase) childEntry.getKey()).setScore(2);
                    } else if ("Average".equals(importance)) {
                        ((Phrase) childEntry.getKey()).setScore(1);
                    } else {
                        ((Phrase) childEntry.getKey()).setScore(0);
                    }

                    listOfPhrase.add((Phrase) childEntry.getKey());
                }

            }

            PhraseDB.saveTopKPhrses(listOfPhrase);

            /*
             * saving match Docsection-terms recommended based on selected
             * sentences
             */

            Map<Integer, ArrayList<FiboTerm>> topKFiboTermsMap = (Map<Integer, ArrayList<FiboTerm>>) session.getAttribute("topKFiboTermsMapSelectedSent");
            ArrayList<MatchSectionFibo> sectionfibolist = new ArrayList<MatchSectionFibo>();
            for (Map.Entry entry : topKFiboTermsMap.entrySet()) {
                ArrayList<FiboTerm> listOfTerms = ((ArrayList<FiboTerm>) entry.getValue());
                for (int i = 0; i < listOfTerms.size(); i++) {
                    MatchSectionFibo matchSecFibo = new MatchSectionFibo();
                    matchSecFibo.setSectionId((Integer) entry.getKey());
                    matchSecFibo.setFiboTermId(listOfTerms.get(i).getFiboId());
                    matchSecFibo.setSimScore(listOfTerms.get(i).getSimScore());
                    matchSecFibo.setUserId(userId);
                    sectionfibolist.add(matchSecFibo);
                }

            }
            MatchSectionFiboDB.saveMatchSectionFiboDB(sectionfibolist);
            session.setAttribute("savesucces", "Annotation Data Saved Succesfully");
            userPath = "/createcollection";
        } else if (userPath.equals("/savemarkedupsentences")) {

            //TO_DO save selected sentences
            int userId = Integer.parseInt(session.getAttribute("userId").toString());
            // /senteces selected and marked the importance + Recommonded FIBO terms for doc_section + Marked up sentences with FIBO terms


            /*
             * saving the selected sentences
             */

            HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>> selectedDocSectionSentenceMap = (HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>>) session.getAttribute("selectedDocSectionSentenceMap");

            // ArrayList<Phrase> listOfPhrase = new ArrayList<Phrase>();
            ArrayList<MatchPhraseFibo> phrasefibolist = new ArrayList<MatchPhraseFibo>();
            for (Map.Entry parentEntry : selectedDocSectionSentenceMap.entrySet()) {
                for (Map.Entry childEntry : ((LinkedHashMap<Phrase, ArrayList<FiboTerm>>) parentEntry.getValue()).entrySet()) {
                    ((Phrase) childEntry.getKey()).setDocId((Integer) parentEntry.getKey());
                    ((Phrase) childEntry.getKey()).setUserId(userId);
                    String importance = ((Phrase) childEntry.getKey()).getImportance();
                    if ("V. Importanr".equals(importance)) {
                        ((Phrase) childEntry.getKey()).setScore(3);
                    } else if ("Important".equals(importance)) {
                        ((Phrase) childEntry.getKey()).setScore(2);
                    } else if ("Average".equals(importance)) {
                        ((Phrase) childEntry.getKey()).setScore(1);
                    } else {
                        ((Phrase) childEntry.getKey()).setScore(0);
                    }

                    //saving the Phrase

                    PhraseDB.saveTopKPhrses((Phrase) childEntry.getKey());

                    int phraseID = PhraseDB.getPhrasesIDByContent(((Phrase) childEntry.getKey()).getPhraseContent());
                    for (int j = 0; j < ((ArrayList<FiboTerm>) childEntry.getValue()).size(); j++) {
                        MatchPhraseFibo matchPhraseFibo = new MatchPhraseFibo();
                        matchPhraseFibo.setFiboTermId(((ArrayList<FiboTerm>) childEntry.getValue()).get(j).getFiboId());
                        matchPhraseFibo.setPhraseId(phraseID);
                        matchPhraseFibo.setUserId(userId);

                        phrasefibolist.add(matchPhraseFibo);
                    }


                    //  listOfPhrase.add((Phrase) childEntry.getKey());
                }

            }
            MatchPhraseFiboDB.saveMatchSectionFiboDB(phrasefibolist);
            //  PhraseDB.saveTopKPhrses(listOfPhrase);


            /*
             * saving match Docsection-terms recommended based on selected
             * sentences
             */
            Map<Integer, ArrayList<FiboTerm>> topKFiboTermsMap = (Map<Integer, ArrayList<FiboTerm>>) session.getAttribute("topKFiboTermsMapSelectedSent");
            ArrayList<MatchSectionFibo> sectionfibolist = new ArrayList<MatchSectionFibo>();
            for (Map.Entry entry : topKFiboTermsMap.entrySet()) {
                ArrayList<FiboTerm> listOfTerms = ((ArrayList<FiboTerm>) entry.getValue());
                for (int i = 0; i < listOfTerms.size(); i++) {
                    MatchSectionFibo matchSecFibo = new MatchSectionFibo();
                    matchSecFibo.setSectionId((Integer) entry.getKey());
                    matchSecFibo.setFiboTermId(listOfTerms.get(i).getFiboId());
                    matchSecFibo.setSimScore(listOfTerms.get(i).getSimScore());
                    matchSecFibo.setUserId(userId);
                    sectionfibolist.add(matchSecFibo);
                }

            }
            MatchSectionFiboDB.saveMatchSectionFiboDB(sectionfibolist);

            session.setAttribute("savesucces", "Annotation Data Saved Succesfully");
            userPath = "/createcollection";
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

//        int selectedCollectionType = Integer.parseInt(request.getParameter("collection_type"));
//        session.setAttribute("collectionType", selectedCollectionType);  // setteing collection type for session
        if (userPath.equals("/savedata")) {
            ArrayList<MatchSectionFibo> matchSectionFibo = new ArrayList<MatchSectionFibo>();

            int userId = Integer.parseInt(session.getAttribute("userId").toString());
            //hashmap<docSectionID,Hashmap<fiboterm,fibodef>>
            HashMap<Integer, ArrayList<FiboTerm>> topKFiboTermsMap = (HashMap<Integer, ArrayList<FiboTerm>>) session.getAttribute("topKFiboTermsMap");
            //hashmap<parentdoc, arraylist of doc section>
            HashMap<String, ArrayList<DocSection>> SelectedDocSectionMap = (HashMap<String, ArrayList<DocSection>>) session.getAttribute("SelectedDocSectionMap");
            // ArrayList<FiboTerm> fiboTermList = (ArrayList<FiboTerm>) session.getAttribute("SelectedDocSectionMap");

            ArrayList<DocSection> tempDocSectionList = new ArrayList<DocSection>();
            ArrayList<FiboTerm> tempFiboTerms = new ArrayList<FiboTerm>();
            ArrayList<MatchSectionFibo> tempSectionFiboData = new ArrayList<MatchSectionFibo>();

            for (Map.Entry entry : SelectedDocSectionMap.entrySet()) {

                tempDocSectionList = (ArrayList<DocSection>) entry.getValue();
                for (int i = 0; i < tempDocSectionList.size(); i++) {
                    tempFiboTerms = topKFiboTermsMap.get(tempDocSectionList.get(i).getSectionId());
                    for (int j = 0; j < tempFiboTerms.size(); j++) {
                        MatchSectionFibo tempMatchSectionFifo = new MatchSectionFibo();
                        tempMatchSectionFifo.setFiboTermId(tempFiboTerms.get(j).getFiboId());
                        tempMatchSectionFifo.setSectionId(tempDocSectionList.get(i).getSectionId());
                        tempMatchSectionFifo.setSimScore(tempFiboTerms.get(j).getSimScore());
                        tempMatchSectionFifo.setUserId(userId);
                        tempSectionFiboData.add(tempMatchSectionFifo);

                    }
                    tempFiboTerms.clear();
                }
                tempDocSectionList.clear();
            }


            MatchSectionFiboDB.saveMatchSectionFiboDB(tempSectionFiboData);


            session.setAttribute("savesucces", "Annotation Data Saved Succesfully");

            url = "/WEB-INF/view/selectdocument.jsp";

        }

        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }
}
