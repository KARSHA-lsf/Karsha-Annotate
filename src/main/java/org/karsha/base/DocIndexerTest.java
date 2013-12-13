/*
 *   KarshaAnnotate- Annotation tool for financial documents
 *  
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
 *   along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.karsha.base;

import java.io.IOException;
import java.io.StringReader;
import java.util.*;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.DefaultSimilarity;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.karsha.tokenize.DefaultTokenizer;
import org.apache.lucene.store.RAMFile;
/**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes Feb 13, 2013 Kasun Perera Created
 *
 */
/**
 * TODO- describe the purpose of the class
 *
 */
public class DocIndexerTest {

    
    private String[] docNames;
    private RAMDirectory ramMemDir;
    private RAMFile ramFile;
    private String[] filesInText;
    private int[] noOfWordsOfDOc;
    private ArrayList[] ArrLstSentencesOfDoc;
    private int[] noOfSentencesOfDoc;
    private String[][] removedTermsOfDOc;
    private int[][] freqAfterRemovalOfDoc;
    private int curDocNo;

    /**
     * Constructor used when indexing directory is a RAM memory directory, We
     * need RAM directory because Wso2-Stratoes Server that we used to host
     * dosen't allow access local files
     *
     * @param files- List of Documents converted in to bytes
     * @param docNames -Corresponding Document names
     */
    public DocIndexerTest(String docContent[], String docNames[]) {
        
        this.ramFile = new RAMFile();
        this.docNames = docNames;

        //this.bufPathToIndex= new RandomAccessBuffer() ;
        this.ramMemDir = new RAMDirectory();
        //pathToIndex = new RAMDirectory().toString();;//this.bufPathToIndex.toString() ;
        // this.files = files;
        this.filesInText = docContent;
        //this.queryDocIndex = queryDocIndex ;
        int len = filesInText.length;
        this.noOfWordsOfDOc = new int[len];
        this.ArrLstSentencesOfDoc = new ArrayList[len];
        this.noOfSentencesOfDoc = new int[len];
        this.removedTermsOfDOc = new String[len][];
        this.freqAfterRemovalOfDoc = new int[len][];
        this.curDocNo = 0;
        //this.termsOfFIBO = fiboTerms ;
    }

    /**
     * Method to index the documents only using the content of the document
     * "docid" field is used for indexing, since Lucene Dosen't retrieve the
     * documents in the indexed order RAM directory is used for indexing
     *
     * @param docNo- document number of the document to be indexed
     * @throws IOException
     */
    public void index(int docNo) throws IOException {
        //String content = convertPDFToText(docNo);
        //String content = ReadTextFile(fileNames[docNo]);
//String b = new DefaultTokenizer().processText(content);
        // this.noOfWordsOfDOc[curDocNo] = wordCount(content);
        //StringReader strRdElt = new StringReader(content);

        // StringReader strRdElt = new StringReader(new DefaultTokenizer().processText(filesInText[docNo]));

        StringReader strRdElt = new StringReader(filesInText[docNo]);
        StringReader docId = new StringReader(Integer.toString(docNo));

        Document doc = new Document();

        doc.add(new Field("doccontent", strRdElt, Field.TermVector.YES));
        doc.add(new Field("docid", docId, Field.TermVector.YES));



        //  doc.add(new Field(docNames ;
        //this.ArrLstSentencesOfDoc[curDocNo] = sentenceCount(content);
        //this.noOfSentencesOfDoc[curDocNo] = this.ArrLstSentencesOfDoc[curDocNo].size() ;
        IndexWriter iW;
        try {
            //NIOFSDirectory dir = new NIOFSDirectory(new File(pathToIndex)) ;
            //dir = new RAMDirectory() ;
            //iW = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35,
            //new StandardAnalyzer(Version.LUCENE_35)));
            iW = new IndexWriter(ramMemDir, new IndexWriterConfig(Version.LUCENE_36,
                    new StandardAnalyzer(Version.LUCENE_36)));
            iW.addDocument(doc);
            iW.close();
            //dir.close() ;
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to index the documents only using the content of the document
     * "docid" field is used for indexing, since Lucene Dosen't retrieve the
     * documents in the indexed order RAM directory is used for indexing
     *
     *
     * @throws IOException
     */
    public void index() throws IOException {

        int noOfDocs = docNames.length;
        //String content = convertPDFToText(docNo);
        //String content = ReadTextFile(fileNames[docNo]);
//String b = new DefaultTokenizer().processText(content);
        // this.noOfWordsOfDOc[curDocNo] = wordCount(content);
        //StringReader strRdElt = new StringReader(content);

        // StringReader strRdElt = new StringReader(new DefaultTokenizer().processText(filesInText[docNo]));





        //  doc.add(new Field(docNames ;
        //this.ArrLstSentencesOfDoc[curDocNo] = sentenceCount(content);
        //this.noOfSentencesOfDoc[curDocNo] = this.ArrLstSentencesOfDoc[curDocNo].size() ;
        IndexWriter iW;
        try {
            //NIOFSDirectory dir = new NIOFSDirectory(new File(pathToIndex)) ;
            //dir = new RAMDirectory() ;
            //iW = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35,
            //new StandardAnalyzer(Version.LUCENE_35)));
            iW = new IndexWriter(ramMemDir, new IndexWriterConfig(Version.LUCENE_36,
                    new StandardAnalyzer(Version.LUCENE_36)));

            for (int i = 0; i < noOfDocs; i++) {
                StringReader strRdElt = new StringReader(filesInText[i]);
                StringReader docId = new StringReader(Integer.toString(i));

                Document doc = new Document();

                doc.add(new Field("doccontent", strRdElt, Field.TermVector.YES));
                doc.add(new Field("docid", docId, Field.TermVector.YES));
                iW.addDocument(doc);
            }


            iW.close();
            //dir.close() ;
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //---------------------------Okapi Testing-------------------------------------------------------------------
    public HashMap<Integer, HashMap> getTfForDocs(int numberOfDocs, int weight) throws CorruptIndexException, ParseException {

        int noOfDocs = numberOfDocs;

        HashMap<Integer, HashMap> tfMap = new HashMap<Integer, HashMap>();
        //HashMap<Integer, float[]> scoreMap = new HashMap<Integer, float[]>();


        try {

            //IndexReader re = IndexReader.open(FSDirectory.open(new File(pathToIndex)), true) ;
            IndexReader re = IndexReader.open(ramMemDir);

            int i = 0;
            for (int k = 0; k < numberOfDocs; k++) {
                int aInt = 0;
                //TermFreqVector termsFreqVec[];
                TermFreqVector termsFreq;
                TermFreqVector termsFreqDocId = null;
                TermFreqVector termsFreqFiboTerm;
                TermFreqVector termsFreqTaxoTerm;
                HashMap<String, Integer> wordMap = new HashMap<String, Integer>();
                String termsVec[][];
                int freqVec[];
                int noOfTermsVec[];
                String terms[];
                int freq[];
                int noOfTerms;
                float score[] = null;

                //termsFreq3=re.getTermFreqVectors(currentDocID);
                /*
                 * getting the fields in the indexed order, Doccontent, docid,
                 * fiboterms
                 */


                //termsFreqVec = re.getTermFreqVectors(k);
                DefaultSimilarity simi = new DefaultSimilarity();
                for (int m = 0; m < 2; m++) {
                    switch (m) {
                        case 0:       //doc content
                            termsFreq = re.getTermFreqVector(k, "doccontent");
                            //  freq = termsFreqVec[0].getTermFrequencies();
                            // terms = termsFreqVec[0].getTerms();
                            freq = termsFreq.getTermFrequencies();
                            terms = termsFreq.getTerms();
                            noOfTerms = terms.length;
                            score = new float[noOfTerms];

                            for (i = 0; i < noOfTerms; i++) {

                                wordMap.put(terms[i], freq[i]);

                            }

                            break;
                        case 1:        // doc Id
                            termsFreqDocId = re.getTermFreqVector(k, "docid");
                            // terms = termsFreqVec[1].getTerms();
                            aInt = Integer.parseInt(termsFreqDocId.getTerms()[0]);
                            break;


                        default:
                        //System.out.println("Invalid Entry!");
                    }
                }
                tfMap.put(aInt, wordMap);
            }


        } catch (IOException e) {
            // score = null;
            e.printStackTrace();
        }


        return tfMap;
    }

    public HashMap<Integer, TreeMap> topKFiboTerms(int docIndex, String[] selectedDocuments, double okapiCutOff) throws IOException, CorruptIndexException, ParseException, ClassNotFoundException, Exception {
        int noOfDocs = docNames.length;
        float tfIdfScore[][] = new float[noOfDocs][];




        /*
         * @param scoreMap - what contains here????
         */
        HashMap<Integer, HashMap> scoreMap = new HashMap<Integer, HashMap>();

        /*
         * doing all indexing at once
         */

        index();

        /*
         * for (int i = 0; i < noOfDocs; i++) { index(i);
         *
         *
         * }
         *
         *
         */

//        if (!scoreMap.isEmpty()){
//            scoreMap.clear();
//        }


        int weight = 1;



        /*
         * calculating Okapi similarity
         *
         */



        //score map specific for Okapi Sim

        ///////////////////////////////////////////////////////////////////////////////
        scoreMap = getTfForDocs(noOfDocs, weight);

        /////////////////////////////////////////////////////////////////////////////////


        ArrayList<Double> simi = new ArrayList<Double>();


        OkapiSimilarity okapiSim = new OkapiSimilarity(ramMemDir);

        //----------------------------------------------------------------------------
        /*
         * Store <Section ID,TopKterms>
         */
        //HashMap<Integer, TreeMap> topKTerms = new HashMap<Integer, TreeMap>();
        HashMap<Integer, TreeMap> topKTerms = new HashMap<Integer, TreeMap>();




        //for (int p = 0; p < noOfDocSections; p++) {
        for (int p = 0; p < 1; p++) {
            /////////////////////////////////////////////////////////
            int noOfFiles;

            Double db[] = new Double[noOfDocs];



            // System.out.println("\n" + docList[p] + "\n");


            double sim[];
            // ArrayList<Double> simi;
            HashMap<String, Double> termsScore = new HashMap<String, Double>();

            ///////////////////////////////////////////////////////////////////////





            try {
                //sim = doc.consineSimilarityTo(0);
                //sim=docInd.consineSimilarityTo(0);
                // simi = docInd.consineSimilarityTo2(p);
                db = okapiSim.computeSimilarity(scoreMap, docIndex);
                simi.addAll(Arrays.asList(db));

                int aa = 0;
                //Printing the similarity values
                //for (int i = noOfDocSections; i < simi.size(); i++) {
                for (int i = 0 ; i < simi.size(); i++) {
                    aa++;
                    double temp = simi.get(i);
//
//                    if (Double.isNaN(temp)) {
//                        System.out.println(0.0);
//                    } else {
//                        System.out.println(temp);
//                    }

                    // if (!Double.isNaN(temp) && temp > 10.274) {
                    if (!Double.isNaN(temp) && temp > okapiCutOff) {
                        termsScore.put(docNames[i], temp);
                    }

                    // System.out.print(simi.get(i) + "\n");
                }





                ValueComparator bvc = new ValueComparator(termsScore);
                //TreeMap<String, Double> sorted_map = new TreeMap<String, Double>(bvc);

                SortedMap<String, Double> sorted_map = Collections.synchronizedSortedMap(new TreeMap<String, Double>(bvc));


                // System.out.println("unsorted map: "+termsScore);

                sorted_map.putAll(termsScore);
                //if the number of elements are greater than 15 remove eccess elements.
                //TreeMap<String, Double> tempSortedMap = new TreeMap<String, Double>() ;
                int count = 0;
                Iterator it = sorted_map.entrySet().iterator();
                while (it.hasNext()) {
                    it.next();
                    count++;
                    if (count > 14) {
                        // it.
                        //Entry item =      (Entry) it.next();
                        it.remove();
                    }
                }



                topKTerms.put(Integer.parseInt(selectedDocuments[docIndex]), new TreeMap<String, Double>(sorted_map));
//tempSortedMap.clear();

            } catch (IOException e) {
                sim = null;
                e.printStackTrace();
            }

            simi.clear();
        }



        return topKTerms;


    }
}
