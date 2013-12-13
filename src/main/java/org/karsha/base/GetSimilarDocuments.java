/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.index.CorruptIndexException;
import org.apache.lucene.queryParser.ParseException;
import org.karsha.data.DocSectionDB;
import org.karsha.data.DocumentDB;
import org.karsha.entities.DocSection;
import org.karsha.entities.Document;

/**
 *
 * @author najiniharischandra
 */
public class GetSimilarDocuments {

    public static ArrayList<Document> getSimilarDocumentsForADocument(String selectedDocumentName) {
        
        try {
            int noOfDocs = 0;            
            ArrayList<Document> topSimilarDocs = new ArrayList<Document>();
            
            ArrayList<Integer> Docids = DocumentDB.getAlldocumentIDs();
            noOfDocs = Docids.size();

            Document tempDoc = new Document();
            ArrayList<DocSection> docSec = new ArrayList<DocSection>();
            HashMap<String, ArrayList<DocSection>> SelectedDocSectionMap = new HashMap<String, ArrayList<DocSection>>();

            for (int i = 0; i < noOfDocs; i++) {

                docSec = DocSectionDB.getFullDocumentByDocumentId(Docids.get(i));
                tempDoc = DocumentDB.getDocumentByDocId(Docids.get(i));

                SelectedDocSectionMap.put(tempDoc.getDocumentName(), docSec);

            }

            String[] docContent = new String[noOfDocs];
            String[] docNames = new String[noOfDocs];
            String[] docIds = new String[noOfDocs];

            int count = 0;

            for (Map.Entry entry : SelectedDocSectionMap.entrySet()) {
                String doc = "";
                docNames[count] = (String) entry.getKey();
                ArrayList<DocSection> docSections = (ArrayList<DocSection>) entry.getValue();

                for (int i = 0; i < docSections.size(); i++) {

                    String sec = new String(DocSectionDB.getDocumentDataByDocId(docSections.get(i).getSectionId()).getSectiontContent());
                    doc = doc.concat(sec);
                }
                docContent[count] = doc;
                count++;
            }

            for (int i = 0; i < Docids.size(); i++) {
                docIds[i] = Docids.get(i).toString();
            }

            DocIndexerTest docInd = new DocIndexerTest(docContent, docNames);

            //String docname = "ELECTRIC PLANT BOARD OF THE CITY OF MAYFIELD  KENTUCKY Electric System Revenue Bonds";
            Document docObject = new Document();
            docObject = DocumentDB.getDocumentDataByDocName(selectedDocumentName);
            int docID = docObject.getDocId();
            String sDocId = Integer.toString(docID);
            int docIndex = 0;

            for (int i = 0; i < docIds.length; i++) {
                if (sDocId.equals(docIds[i])) {
                    docIndex = i;
                }
            }

            HashMap<Integer, TreeMap> topKSimilarDocs = docInd.topKFiboTerms(docIndex, docIds, 0);

            for (Map.Entry entry : topKSimilarDocs.entrySet()) {

                int selectedDocID = (Integer) entry.getKey();
                //System.out.println(" Selected Doc ID = " + selectedDocID);

                TreeMap<String, Double> fiboSim = (TreeMap<String, Double>) entry.getValue();

                for (Map.Entry entryVal : fiboSim.entrySet()) {
                    Document similardoc = new Document();
                    String DocName = (String) entryVal.getKey();
                    similardoc.setDocumentName(DocName);
                    //int fiboId = Integer.parseInt(sFiboId);
                    //double simScore = (Double) entryVal.getValue();
                    //System.out.println(DocName + "\t\t" + simScore);
                    topSimilarDocs.add(similardoc);
                }
            }
            
            return topSimilarDocs;
        } catch (IOException ex) {
            Logger.getLogger(GetSimilarDocuments.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ParseException ex) {
            Logger.getLogger(GetSimilarDocuments.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(GetSimilarDocuments.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } catch (Exception ex) {
            Logger.getLogger(GetSimilarDocuments.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
