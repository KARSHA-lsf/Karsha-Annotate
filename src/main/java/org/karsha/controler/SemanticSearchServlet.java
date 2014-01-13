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
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.*;
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
        LinkedHashSet nodeList;
        Map nodeParent;

        if(userPath.equals("/semanticSearch")) {
            MappedTreeStructure mts = new MappedTreeStructure();
            ArrayList<FiboTerm> fiboList = FiboDB.getAllFiboTerms();
            MutableTree tree= mts.setFiboTree();
            List<String> roots = tree.getRoots();
            session.setAttribute("roots", roots);
            List children =  tree.getChildren(roots.get(0));
            session.setAttribute("children", children);
            session.setAttribute("numofchildren",""+children.size());
            for(int i=0;i<1;i++){
                for(int x=0;x<children.size();x++) {
                    session.setAttribute("childrenof"+x,tree.getChildren((Serializable) children.get(x)));
                    session.setAttribute("numofsubchi"+x,""+tree.getChildren((Serializable) children.get(x)).size());
                    for(int y=0;y<tree.getChildren((Serializable) children.get(x)).size();y++){
                        session.setAttribute("subChiof"+x+""+y,tree.getChildren((Serializable) tree.getChildren((Serializable) children.get(x)).get(y)));
                    }
                }
            }
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
        PrintWriter writer = response.getWriter();
        String url = null;
        String hashMapValues="";

        HashMap<Integer,Double> docSim= new HashMap<Integer, Double>();
        LinkedHashMap<String,Integer> docSimMap = new LinkedHashMap<String, Integer>();
        HashMap<Integer, TreeMap> topKDocs = new HashMap<Integer, TreeMap>();

        if(userPath.equals("/getsimilardocs")) {
            String selectedFibosIn;
            selectedFibosIn =request.getParameter("items");
            String FilteredIn = selectedFibosIn.replaceAll("[\"\\[\\]]","");
            String[] selectedFibos = FilteredIn.split(",");
            for(int i=0;i<selectedFibos.length;i++){
            System.out.println(selectedFibos[i]);   }
            int noOfFiboTerms=selectedFibos.length;
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
                docContent[noOfDocs + i] = selectedFibos[i] + FiboDB.getFiboTermById(FiboDB.getFiboTermID(selectedFibos[i]).getFiboId()).getFiboDefinition();
                docNames[noOfDocs + i] = ""+FiboDB.getFiboTermID(selectedFibos[i]).getFiboId();
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
                    String docID =entryParent.getKey().toString();
                    System.out.println("docID"+Integer.parseInt(docID));
                    TreeMap<String, Double> sortedMap = (TreeMap<String, Double>) entryParent.getValue();

                    for (Map.Entry entryChild : sortedMap.entrySet()) {
                        counter++;
                        double SimScore=(Double) entryChild.getValue();
                        avgSimScore = avgSimScore + SimScore;
                    }
                    System.out.println("avgSimScore"+avgSimScore/counter);
                    docSim.put(Integer.parseInt(docID),avgSimScore/counter);

                }
                docSimMap = sortHashMapByValues(docSim);
                for (Map.Entry entry : docSimMap.entrySet()) {
                    hashMapValues =hashMapValues+ entry.getKey()+"$"+ entry.getValue()+"#";
                }

            }  catch (Exception e){
                System.out.println(e);
            }
            session.setAttribute("topKDocs", docSimMap);
           writer.write(hashMapValues);
           writer.close(); 
      
        }

    }
    public LinkedHashMap sortHashMapByValues(HashMap hashMap) {
        List mapKeys = new ArrayList(hashMap.keySet());
        List mapValues = new ArrayList(hashMap.values());
        Collections.sort(mapValues);
        Collections.reverse(mapValues);
        Collections.sort(mapKeys);
        Collections.reverse(mapKeys);

        LinkedHashMap sortedMap = new LinkedHashMap();

        Iterator valueIt = mapValues.iterator();
        while (valueIt.hasNext()) {
            Object val = valueIt.next();
            Iterator keyIt = mapKeys.iterator();

            while (keyIt.hasNext()) {
                Object key = keyIt.next();
                String comp1 = hashMap.get(key).toString();
                String comp2 = val.toString();

                if (comp1.equals(comp2)){
                    hashMap.remove(key);
                    mapKeys.remove(key);
                    sortedMap.put(DocumentDB.getDocumentByDocId(Integer.parseInt(key.toString())).getDocumentName(), Math.round((Double)val*100.0)/100.0);
                    break;
                }
            }
        }
        return sortedMap;
    }
}
