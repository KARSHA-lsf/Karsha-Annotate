package org.karsha.controler;

import org.karsha.base.DocIndexer;
import org.karsha.base.DocIndexerTest;
import org.karsha.data.DocSectionDB;
import org.karsha.data.DocumentDB;
import org.karsha.data.FiboDB;
import org.karsha.entities.DocSection;
import org.karsha.entities.Document;
import org.karsha.entities.FiboTerm;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: randula
 * Date: 11/29/13
 * Time: 8:14 AM
 * To change this template use File | Settings | File Templates.
 */
public class SemanticSearchServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;

        if(userPath.equals("/semanticSearch")) {
            ArrayList<FiboTerm> fiboList = FiboDB.getAllFiboTerms();
            session.setAttribute("fiboList", fiboList);
            url = "/WEB-INF/view/semanticSearch.jsp";
        }

        RequestDispatcher dispatcher =getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userPath = request.getServletPath();
        HttpSession session = request.getSession();
        String url = null;
        String[] selectedFibos;
        selectedFibos = request.getParameterValues("fiboterms");
        HashMap<Integer,Double> docSim= new HashMap<Integer, Double>();

        HashMap<Integer, TreeMap> topKDocs = new HashMap<Integer, TreeMap>();
        int noOfFiboTerms=selectedFibos.length;

        if(userPath.equals("/getsimilardocs")) {
            System.out.println("noOfFiboTerms"+noOfFiboTerms);
            int noOfDocSecs = 0;
            int noOfDocs = 0;
            ArrayList<Integer> Docids = DocumentDB.getAlldocumentIDs();
            noOfDocs = Docids.size();
            Document tempDoc = new Document();
            ArrayList<DocSection> docSec = new ArrayList<DocSection>();
            HashMap<String, ArrayList<DocSection>> SelectedDocSectionMap = new HashMap<String, ArrayList<DocSection>>();

            for (int i = 0; i < noOfDocs; i++) {
                docSec = DocSectionDB.getAllDocSectionsbyDocId(Docids.get(i));
                noOfDocSecs = noOfDocSecs + docSec.size();
                tempDoc = DocumentDB.getDocumentByDocId(Docids.get(i));
                SelectedDocSectionMap.put(tempDoc.getDocumentName(), docSec);

            }

            String[] docContent = new String[noOfDocs+noOfFiboTerms];
            String[] docNames = new String[noOfDocs+noOfFiboTerms];
            String[] docIds = new String[noOfDocs];
            int count = 0;

            for ( Map.Entry entry : SelectedDocSectionMap.entrySet() ){
                String doc = "";
                docNames[count] = (String)entry.getKey();
                ArrayList<DocSection> docSections = (ArrayList<DocSection>) entry.getValue();

                for ( int i = 0; i < docSections.size(); i++ ){
                    String sec = new String( DocSectionDB.getDocumentDataByDocId( docSections.get( i ).getSectionId() ).getSectiontContent() );
                    doc = doc.concat(sec);
                }
                docContent[count] = doc;
                count++;
            }
            /*
            *CHANGE HERE
             */
            for ( int i = 0; i < Docids.size(); i++ ){
                docIds[i] = Docids.get(i).toString();
            }
            for (int i = 0; i < noOfFiboTerms; i++) {
                docContent[noOfDocs + i] = FiboDB.getFiboTermById(Integer.parseInt(selectedFibos[i])).getFiboTerm() + FiboDB.getFiboTermById(Integer.parseInt(selectedFibos[i])).getFiboDefinition();
                docNames[noOfDocs + i] = selectedFibos[i];
            }

            DocIndexerTest docInd = new DocIndexerTest( docContent, docNames );

            int noOfDocuments= DocumentDB.getAllDocuments().size();
            try {
                double avgSimScore;
                int counter;
                topKDocs = docInd.getSimilarDocs( noOfDocuments, docIds, 0);
                for (Map.Entry entryParent: topKDocs.entrySet()){
                    counter=0;
                    avgSimScore=0;
                    String docID =entryParent.toString();
                    System.out.println("docID"+Integer.parseInt(docID));
                    TreeMap<String, Double> sortedMap = (TreeMap<String, Double>) entryParent.getValue();

                    for (Map.Entry entryChild : sortedMap.entrySet()) {
                        double SimScore=(Double) entryChild.getValue();
                        System.out.println("avgSimScore"+avgSimScore/counter);
                        avgSimScore = avgSimScore + SimScore;
                        counter++;
                    }
                    docSim.put(Integer.parseInt(docID),avgSimScore/counter);
                }
            }  catch (Exception e){

            }

            session.setAttribute("topKDocs", docSim);
            url = "/WEB-INF/view/semanticSearch.jsp";
        }

        request.setAttribute("topKDocs", docSim);
        RequestDispatcher dispatcher =getServletContext().getRequestDispatcher(url);
        dispatcher.forward(request, response);

    }
}
