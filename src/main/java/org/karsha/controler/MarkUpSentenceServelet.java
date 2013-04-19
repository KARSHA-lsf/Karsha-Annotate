/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.controler;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.karsha.entities.FiboTerm;
import org.karsha.entities.Phrase;

/**
 *
 * @author lsf
 */
public class MarkUpSentenceServelet extends HttpServlet {

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
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String userPath = request.getServletPath();


        // if category page is requested
        if (userPath.equals("/markupsentence")) {


            userPath = "/sentencemarkup";

        } else if (userPath.equals("/simOutput")) {
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
        HttpSession session = request.getSession();
        PrintWriter writer = response.getWriter();

        // if addToCart action is called
        if (userPath.equals("/markupsentence")) {


            userPath = "sentencemarkup";

            // use RequestDispatcher to forward request internally
            String url = "/WEB-INF/view" + userPath + ".jsp";


            try {
                request.getRequestDispatcher(url).forward(request, response);
            } catch (Exception ex) {
                ex.printStackTrace();
            }

        } /*
         * delete rows of the $.post("delfibotermsent", { docSecId:
         * currentDocId, curSentence:""+currentSelectedsent ,fiboTerm:
         * ""+fiboterm }, function(responce){
         *
         */ else if (userPath.equals("/delfibotermsent")) {

            String fiboTermToDel = "";
            String docSectionIDToDel = "";
            String sentence = "";
            try {
                if (request.getParameter("fiboTerm") != null && request.getParameter("docSecId") != null && request.getParameter("curSentence") != null) {
                    fiboTermToDel = request.getParameter("fiboTerm").toString();
                    sentence = request.getParameter("curSentence").toString();
                    docSectionIDToDel = request.getParameter("docSecId").toString();

                    LinkedHashMap<Phrase, ArrayList<FiboTerm>> sentenceList = new LinkedHashMap<Phrase, ArrayList<FiboTerm>>();

                    //HashMap<Integer, ArrayList<Phrase>> map = (HashMap<Integer, ArrayList<Phrase>>) session.getAttribute("selectedDocSectionSentenceMap");
                    HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>> map = (HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>>) session.getAttribute("selectedDocSectionSentenceMap");

//                    ParseDocument doc = new ParseDocument();
//                    sentenceList = doc.pdfDocToSentence(document.getSectiontContent());
                    sentenceList = (LinkedHashMap<Phrase, ArrayList<FiboTerm>>) map.get(Integer.parseInt(docSectionIDToDel));


                    for (Map.Entry entry : sentenceList.entrySet()) {
                        if (((Phrase) entry.getKey()).getPhraseContent().equals(sentence)) {
                            ArrayList<FiboTerm> fiboTerms = (ArrayList<FiboTerm>) entry.getValue();
                            if (!fiboTerms.isEmpty()) {
                                for (int i = 0; i < fiboTerms.size(); i++) {
                                    if (fiboTerms.get(i).getFiboTerm().equals(fiboTermToDel)) {
                                        fiboTerms.remove(i);
                                        break;
                                    }
                                }
                            }

                        }


                    }
                    session.setAttribute("selectedDocSectionSentenceMap", map);
                }

            } catch (Exception ex) {
                Logger.getLogger(RecommondTermsServelet.class.getName()).log(Level.SEVERE, null, ex);
            }
            writer.write("docContentIntext2");
            writer.close();
        } /*
         * to get selected sentences from the doc section
         * $.post("gettermsforsent", { docSecId: number, sentence:""+sentence},
         * function(responce){
         */ else if (userPath.equals("/gettermsforsent")) {
            String fiboTermNDefinition = "";
            String docSectionID = "";
            String sentence = "";
            String docContentIntext = "";
            try {


                if (request.getParameter("docSecId") != null && request.getParameter("sentence") != null) {
                    docSectionID = request.getParameter("docSecId").toString();
                    sentence = request.getParameter("sentence").toString();
//
//                    
                    LinkedHashMap<Phrase, ArrayList<FiboTerm>> sentenceList = new LinkedHashMap<Phrase, ArrayList<FiboTerm>>();

                    //HashMap<Integer, ArrayList<Phrase>> map = (HashMap<Integer, ArrayList<Phrase>>) session.getAttribute("selectedDocSectionSentenceMap");
                    HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>> map = (HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>>) session.getAttribute("selectedDocSectionSentenceMap");

//                    ParseDocument doc = new ParseDocument();
//                    sentenceList = doc.pdfDocToSentence(document.getSectiontContent());
                    sentenceList = (LinkedHashMap<Phrase, ArrayList<FiboTerm>>) map.get(Integer.parseInt(docSectionID));


                    for (Map.Entry entry : sentenceList.entrySet()) {
                        if (((Phrase) entry.getKey()).getPhraseContent().equals(sentence)) {
                            ArrayList<FiboTerm> fiboTerms = (ArrayList<FiboTerm>) entry.getValue();
                            if (!fiboTerms.isEmpty()) {
                                for (int i = 0; i < fiboTerms.size(); i++) {


                                    if (docContentIntext.isEmpty()) {
                                        docContentIntext = fiboTerms.get(i).getFiboTerm() + "$$**$$" + fiboTerms.get(i).getFiboDefinition();
                                    } else {
                                        docContentIntext = fiboTerms.get(i).getFiboTerm() + "$$**$$" + fiboTerms.get(i).getFiboDefinition() + "$$##$$" + docContentIntext;
                                    }
                                }
                            }
                            break;
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


                }
            } catch (Exception ex) {
                ex.getStackTrace();
            }
            writer.write(docContentIntext);
            writer.close();
        } /*
         * to save the marked up sentences //$.post("addfibotermsent", {
         * docSecId: currentDocId,curSentence:""+currentSelectedsent, fiboTerm:
         * ""+datarow['fiboterm'] ,fiboTermDef: ""+datarow['definition']},
         * function(responce){
         *
         */
         else if (userPath.equals("/addfibotermsent")) {
            String fiboTermToAdd = "";
            String fiboTermNDefinition = "";
            String docSectionID = "";
            String sentence = "";
            String docContentIntext = "";
            String fiboID="";
            try {


                if (request.getParameter("docSecId") != null && request.getParameter("curSentence") != null && request.getParameter("fiboTerm") != null && request.getParameter("fiboTermDef") != null) {
                    docSectionID = request.getParameter("docSecId").toString();
                    sentence = request.getParameter("curSentence").toString();
                    fiboTermToAdd = request.getParameter("fiboTerm").toString();
                    fiboTermNDefinition = request.getParameter("fiboTermDef").toString();
                    fiboID= request.getParameter("fiboID").toString();
                    LinkedHashMap<Phrase, ArrayList<FiboTerm>> sentenceList = new LinkedHashMap<Phrase, ArrayList<FiboTerm>>();

                    //HashMap<Integer, ArrayList<Phrase>> map = (HashMap<Integer, ArrayList<Phrase>>) session.getAttribute("selectedDocSectionSentenceMap");
                    HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>> map = (HashMap<Integer, LinkedHashMap<Phrase, ArrayList<FiboTerm>>>) session.getAttribute("selectedDocSectionSentenceMap");

//                    ParseDocument doc = new ParseDocument();
//                    sentenceList = doc.pdfDocToSentence(document.getSectiontContent());
                    sentenceList = (LinkedHashMap<Phrase, ArrayList<FiboTerm>>) map.get(Integer.parseInt(docSectionID));


                    for (Map.Entry entry : sentenceList.entrySet()) {
                        if (((Phrase) entry.getKey()).getPhraseContent().equals(sentence)) {
                            ArrayList<FiboTerm> fiboTerms = (ArrayList<FiboTerm>) entry.getValue();
                            FiboTerm newTerm = new FiboTerm();
                            newTerm.setFiboTerm(fiboTermToAdd);
                            newTerm.setFiboDefinition(fiboTermNDefinition);
                            newTerm.setFiboId(Integer.parseInt(fiboID));
                            fiboTerms.add(newTerm);
                            break;
                        }


                    }

                    session.setAttribute("selectedDocSectionSentenceMap", map);

                }
            } catch (Exception ex) {
                ex.getStackTrace();
            }
            writer.write(docContentIntext);
            writer.close();
        }

    }
}
