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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.karsha.entities.DocSection;
import org.karsha.entities.FiboTerm;

/**
 *
 * @author lsf
 */
public class ShowGridServelet extends HttpServlet {

    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;
        /*
         * hashmap<docuemnt_name, list_of_doc_sections>
         */
        HashMap<String, ArrayList<DocSection>> SelectedDocSectionMap = (HashMap<String, ArrayList<DocSection>>) session.getAttribute("SelectedDocSectionMap");

        /*
         * hashmap<docsection_id, list_of_recommended_terms>
         */
        HashMap<Integer, ArrayList<FiboTerm>> topKFiboTermsDef = (HashMap<Integer, ArrayList<FiboTerm>>) session.getAttribute("topKFiboTermsMap");


        /*
         * haskmap<section name , hashmap<documentname,list_of_recommended_terms
         * >>
         */
        HashMap<String, HashMap<String, ArrayList<FiboTerm>>> organizedDocSection = new HashMap<String, HashMap<String, ArrayList<FiboTerm>>>();

        /*
         * to store the all the fibo terms recommonded for the document
         * haskmap<Document name , hashmap<fiboterm,count >>
         */
        HashMap<String, HashMap<String, Integer>> termsForDocMap = new HashMap<String, HashMap<String, Integer>>();
//int maxRowCount=0;
        //To keep track of document order
        /*
         * to store the color of the fibo term
         */
        HashMap<String, Integer> termColor = new HashMap<String, Integer>();


        ArrayList<String> documentOrder = new ArrayList<String>();
        String tempSectionName;
        String oriSectionName;
        for (Map.Entry entry : SelectedDocSectionMap.entrySet()) {
            documentOrder.add(entry.getKey().toString());
            ArrayList<DocSection> docSections = (ArrayList<DocSection>) entry.getValue();

            for (int i = 0; i < docSections.size(); i++) {
                oriSectionName = docSections.get(i).getSectionName().replaceAll("(\\r|\\n)", "").trim();
               
//                if( oriSectionName.length()> 50 ){
//                    oriSectionName=oriSectionName.substring(0,15).trim();
//                }
                
                tempSectionName = matchSectionName(oriSectionName);
                ArrayList<FiboTerm> temp_fiboTerms_DocSec = topKFiboTermsDef.get(docSections.get(i).getSectionId());
                /*
                 * Putting elements to termsForDocMap happence here
                 */
                if (termsForDocMap.containsKey(entry.getKey())) { //if hashmap already contains name of the parent document.
                    HashMap<String, Integer> fiboTermsForDoc = termsForDocMap.get(entry.getKey());
                    for (int j = 0; j < temp_fiboTerms_DocSec.size(); j++) {
                        if (fiboTermsForDoc.containsKey(temp_fiboTerms_DocSec.get(j).getFiboTerm())) {
                            fiboTermsForDoc.put(temp_fiboTerms_DocSec.get(j).getFiboTerm(), fiboTermsForDoc.get(temp_fiboTerms_DocSec.get(j).getFiboTerm()) + 1);
                        } else {
                            fiboTermsForDoc.put(temp_fiboTerms_DocSec.get(j).getFiboTerm(), 1);
                        }
                    }


                } else {
                    HashMap<String, Integer> fiboTermsForDoc = new HashMap<String, Integer>();

                    for (int j = 0; j < temp_fiboTerms_DocSec.size(); j++) {
                        if (fiboTermsForDoc.containsKey(temp_fiboTerms_DocSec.get(j).getFiboTerm())) {
                            fiboTermsForDoc.put(temp_fiboTerms_DocSec.get(j).getFiboTerm(), fiboTermsForDoc.get(temp_fiboTerms_DocSec.get(j).getFiboTerm()) + 1);
                        } else {
                            fiboTermsForDoc.put(temp_fiboTerms_DocSec.get(j).getFiboTerm(), 1);
                        }
                    }
                    //fiboTerms_DocSec.put(entry.getKey().toString(), topKFiboTermsDef.get(docSections.get(i).getSectionId()));
                    termsForDocMap.put(entry.getKey().toString(), fiboTermsForDoc);
                    //  SelectedDocSectionMap.put(tempDocument.getDocumentName(), docSections);
                }



                /*
                 * Putting elements to organizedDocSection happence here
                 *
                 */

                if (organizedDocSection.containsKey(tempSectionName.toLowerCase())) { //if hashmap already contains name of the parent document.

                    if (organizedDocSection.get(tempSectionName.toLowerCase()).containsKey(entry.getKey().toString())) {
                        HashMap<String, ArrayList<FiboTerm>> fiboTerms_DocSec = new HashMap<String, ArrayList<FiboTerm>>();
                        fiboTerms_DocSec.put(entry.getKey().toString().replaceAll("(\\r|\\n)", "").trim(), topKFiboTermsDef.get(docSections.get(i).getSectionId()));
                        organizedDocSection.put(oriSectionName.toLowerCase(), fiboTerms_DocSec);

                    } else {
                        organizedDocSection.get(tempSectionName.toLowerCase()).put(entry.getKey().toString(), temp_fiboTerms_DocSec);

                    }

                } else {
                    HashMap<String, ArrayList<FiboTerm>> fiboTerms_DocSec = new HashMap<String, ArrayList<FiboTerm>>();
                    fiboTerms_DocSec.put(entry.getKey().toString().replaceAll("(\\r|\\n)", "").trim(), topKFiboTermsDef.get(docSections.get(i).getSectionId()));
                    organizedDocSection.put(tempSectionName.toLowerCase(), fiboTerms_DocSec);
                    //  SelectedDocSectionMap.put(tempDocument.getDocumentName(), docSections);
                }
            }






        }


        /*
         * putting element to termColor map happence here
         */

        for (Map.Entry entryTerms : termsForDocMap.entrySet()) {
            for (Map.Entry entryTermsChild : ((HashMap<String, Integer>) entryTerms.getValue()).entrySet()) {
                if (termColor.containsKey(entryTermsChild.getKey().toString())) {
                    termColor.put(entryTermsChild.getKey().toString(), termColor.get(entryTermsChild.getKey().toString()) + 1);
                } else {
                    termColor.put(entryTermsChild.getKey().toString(), 1);
                }
            }

        }


        /*
         * selecting to compare two documents or more and direc to the
         * respective locations
         */
        
        if (SelectedDocSectionMap.size() == 2) {
            userPath = "comparetwodocs";

        } 
        
        
        else {
            // userPath="comparealldocs";
            userPath = "termsgrid";
        }
         
        
        session.setAttribute("documentOrder", documentOrder);
        session.setAttribute("organizedDocSection", organizedDocSection);
        session.setAttribute("termsForDocMap", termsForDocMap);
        session.setAttribute("termColor", termColor);



        //session.setAttribute(url, url);
        // url = "/WEB-INF/view/termsgrid.jsp";
        url = "/WEB-INF/view/" + userPath + ".jsp";
        // url = "/WEB-INF/view/test.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    /**
     * Post method handles the classify.jsp request
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;

        /*
         * //hashmap<docuemnt_name, list_of_doc_sections> HashMap<String,
         * ArrayList<DocSection>> SelectedDocSectionMap = (HashMap<String,
         * ArrayList<DocSection>>)
         * session.getAttribute("SelectedDocSectionMap");
         *
         * //hashmap<docsection_id, map_of_recommended_terms> HashMap<Integer,
         * HashMap> topKFiboTermsDef=(HashMap<Integer, HashMap>)
         * session.getAttribute("topKFiboTermsMap");
         *
         * //haskmap<section name ,
         * hashmap<documentname,Hashmap_of_recommended_terms >> HashMap<String,
         * HashMap<String,HashMap>> organizedDocSection= new HashMap<String,
         * HashMap<String,HashMap>>(); String tempSectionName; for(Map.Entry
         * entry: SelectedDocSectionMap.entrySet()){ ArrayList<DocSection>
         * docSections=(ArrayList<DocSection>) entry.getValue();
         *
         * for(int i=0;i<docSections.size();i++){
         * tempSectionName=matchSectionName(docSections.get(i).getSectionName());
         * if(organizedDocSection.containsKey(tempSectionName.toLowerCase())){
         * //if hashmap already contains name of the parent document. int
         * temp=docSections.get(i).getSectionId(); HashMap<String,HashMap>
         * temp_fiboTerms_DocSec=topKFiboTermsDef.get(temp);
         *
         * organizedDocSection.get(tempSectionName.toLowerCase()).put(entry.getKey().toString(),
         * temp_fiboTerms_DocSec); }
         *
         * else{ HashMap<String,HashMap> fiboTerms_DocSec= new
         * HashMap<String,HashMap>();
         * fiboTerms_DocSec.put(entry.getKey().toString(),
         * topKFiboTermsDef.get(docSections.get(i).getSectionId()));
         * organizedDocSection.put(tempSectionName.toLowerCase(),
         * fiboTerms_DocSec); //
         * SelectedDocSectionMap.put(tempDocument.getDocumentName(),
         * docSections); } }
         *
         *
         * }
         */
        /*
         * ArrayList<ArrayList<ArrayList<String>>> topTerms= new
         * ArrayList<ArrayList<ArrayList<String>>>();
         * ArrayList<ArrayList<String>> grades = null; ArrayList<String> row =
         * null; for(Map.Entry grandParentEntry: organizedDocSection.entrySet()
         * ){
         *
         * grades = new ArrayList<ArrayList<String>>();
         *
         * HashMap<String,HashMap> fiboTerms_DocSec=(HashMap<String,HashMap>)
         * grandParentEntry.getValue(); for(Map.Entry parentEntry:
         * fiboTerms_DocSec.entrySet()){
         *
         * row = new ArrayList<String>();
         *
         * HashMap<String,String> termDefinition=(HashMap<String,String>)
         * parentEntry.getValue(); for (Map.Entry childEntry:
         * termDefinition.entrySet()){
         *
         * row.add(childEntry.getKey().toString()); } grades.add(row); }
         * topTerms.add(grades); }
         */
        //--------------------
         /*
         * for(int k=0;k<topTerms.size();k++){ for (int i = 0; i <
         * topTerms.get(k).size(); i++) { for (int j = 0; j <
         * topTerms.get(k).get(i).size(); j++) {
         * System.out.print(topTerms.get(k).get(i).get(j)); System.out.print(",
         * "); }
         *
         * System.out.println(""); }
         * System.out.println("-----------------------------"); }
         */

        /*
         * //session.setAttribute("organizedDocSection", topTerms);
         * session.setAttribute("organizedDocSection",organizedDocSection);
         *
         *
         * //releasing memeory to increase effciency
         *
         * session.removeAttribute("SelectedDocSectionMap");
         * session.removeAttribute("topKFiboTermsMap");
         */

        //session.setAttribute(url, url);
        url = "/WEB-INF/view/termsgrid.jsp";
        RequestDispatcher dispatcher = getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);


    }

    //TODO- this is very bad way of comapring section names redo this method again
    private String matchSectionName(String sectionName) {

        String tempSectionName = sectionName;

        //Sections of municipal bonds
        String[] test = {"sale", "security", "insurance", "bonds", "legal", "tax", "debt", "risk", "introduction", "funds", "disclosure", "description","appendix"};
        String[] test_fulName = {"sale of bonds", "security for the bonds", "bond insurance", "the bonds", "legal matters", "tax considerations", "debt service", "risk factors", "introduction", "sources and uses of funds", "continuing disclosure", "description","appendix"};

        //Sections of common bonds
        //String[] test = {"risk", "distribution", "tax", "description", "erisa"};
        // String[] test_fulName = {"risk factors", "plan of distribution", "tax considerations", "description of notes", "erisa considerations"};
        for (int i = 0; i < test.length; i++) {

            if (sectionName.toLowerCase().contains(test[i])) {
                //System.out.println( test2_ful[j]+"\n");
                tempSectionName = test_fulName[i];
                break;
            }

        }

        return tempSectionName;
    }
}
