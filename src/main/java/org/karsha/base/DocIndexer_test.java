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
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.LockObtainFailedException;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.jdbc.dialect.MySQLDialect;
import org.apache.lucene.util.Version;
import org.karsha.lucene.jdbc.JDBCDatabaseUtil;
import org.karsha.lucene.jdbc.JDBCDirectory;
import org.karsha.lucene.jdbc.JDBCIndexer;
import org.karsha.lucene.jdbc.MyJDBCDirectory;
import org.karsha.tokenize.DefaultTokenizer;
import org.apache.lucene.store.jdbc.JdbcDirectory;

/** 
 * Copyright (C) 2012, Lanka Software Foundation. 
 * 
 * Date             Author          Changes 
 * Jan 3, 2013     Kasun Perera    Created   
 * 
 */ 

/**
 * TODO- describe the  purpose  of  the  class
 * 
 */
public class DocIndexer_test {
    
    
     private String docNames[];
    private String pathToIndex;
    private String fiboTermList[]; //marked up fibo terms
    private String taxoTermList[]; // marked up taxonomy terms
    private RAMDirectory ramMemDir;
    private String fileNames[];
    private byte files[][];
    private String filesInText[];
    int noOfWordsOfDOc[];
    int noOfSentencesOfDoc[];
    ArrayList<String> ArrLstSentencesOfDoc[];
    String removedTermsOfDOc[][];
    int freqAfterRemovalOfDoc[][];
    //int queryDocIndex ;
    private int curDocNo;
    private final int maxTerms = 1000000;
    private Directory jdbcDir ;
    JDBCDirectory jdbcIn = new JDBCDirectory();
    JDBCIndexer jdbcIndex = new JDBCIndexer();

    
     /**
     * Constructor used when indexing directory is a RAM memory directory, We
     * need RAM directory because Stratoes Server dosen't allow access local
     * files
     *
     * @param files- List of Documents converted in to bytes
     * @param docNames -Corresponding Document names
     */
     public DocIndexer_test(String docContent[], String docNames[]) {
        this.docNames = docNames;

        //this.bufPathToIndex= new RandomAccessBuffer() ;
       // this.ramMemDir = new RAMDirectory();
       
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
     * *******index directory is database********** "docid" field is used for
     * indexing, since Lucene Dosen't retrieve the documents in the indexed
     * order RAM directory is used for indexing
     *
     * @param docNo- document number of the document to be indexed
     * @throws IOException
     */
    public void indexToDB() throws IOException {

        /*
         * index to the database
         */
        IndexWriter writer = null;
        try {
            //indexWriter = new IndexWriter(getJdbcDirectory(), indexWriterConfig);
            //addIndex(indexWriter);
           MyJDBCDirectory d=(MyJDBCDirectory) jdbcIn.getJdbcDirectory();
            writer = new IndexWriter(d, new IndexWriterConfig(Version.LUCENE_35,
                    new StandardAnalyzer(Version.LUCENE_35)));
            addIndex(writer);
            // writer.addDocument(doc);
            // writer.close();
          //  d.close();
         
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (LockObtainFailedException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } 
        
        finally {
          //  jdbcIn.getJdbcDirectory().close();
            
            
            if (writer != null) {
                try {
                    writer.close();
                    
                } catch (CorruptIndexException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    writer = null;
                }
            }
            
            
        }
        
        
    }

    /**
     * Add records in Index table
     *
     * @param indexWriter the index writer
     */
    private void addIndex(IndexWriter indexWriter) throws CorruptIndexException, IOException {
        try {
            for (int i = 0; i < docNames.length; i++) {
                StringReader strRdElt = new StringReader(new DefaultTokenizer().processText(filesInText[i]));
                StringReader docId = new StringReader(Integer.toString(i));

                Document doc = new Document();

                doc.add(new Field("doccontent", strRdElt, Field.TermVector.YES));
                doc.add(new Field("docid", docId, Field.TermVector.YES));


                indexWriter.addDocument(doc);

            }
          //  indexWriter.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    
    /*
     * Calculate Term frequecies for the documents
     */
        //---------------------------Okapi Testing-------------------------------------------------------------------
    public HashMap<Integer, HashMap> getTfForDocs(int numberOfDocs, int weight) throws CorruptIndexException, ParseException {

        int noOfDocs = numberOfDocs;

        HashMap<Integer, HashMap> tfMap = new HashMap<Integer, HashMap>();
        //HashMap<Integer, float[]> scoreMap = new HashMap<Integer, float[]>();


        try {
           // MyJDBCDirectory myJdbcDir = jdbcIn.getJdbcDirectory();
            

            IndexReader re = IndexReader.open(jdbcDir);
            //IndexReader re = IndexReader.open(FSDirectory.open(new File(pathToIndex)), true) ;
           //  IndexReader re = IndexReader.open(ramMemDir);

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

                        /*
                         * case 2: //Fiboterms termsFreqFiboTerm =
                         * re.getTermFreqVector(k, "fiboterms"); if
                         * (termsFreqFiboTerm != null) { freq =
                         * termsFreqFiboTerm.getTermFrequencies(); terms =
                         * termsFreqFiboTerm.getTerms();
                         *
                         * noOfTerms = terms.length;
                         *
                         * score = new float[noOfTerms]; //DefaultSimilarity
                         * simi = new DefaultSimilarity(); for (i = 0; i <
                         * noOfTerms; i++) {
                         *
                         * wordMap.put(terms[i], freq[i]);
                         *
                         * }
                         * }
                         * break; case 3: //taxoterms termsFreqTaxoTerm =
                         * re.getTermFreqVector(k, "taxoterms"); if
                         * (termsFreqTaxoTerm != null) { freq =
                         * termsFreqTaxoTerm.getTermFrequencies(); terms =
                         * termsFreqTaxoTerm.getTerms();
                         *
                         * noOfTerms = terms.length;
                         *
                         * score = new float[noOfTerms]; //DefaultSimilarity
                         * simi = new DefaultSimilarity(); for (i = 0; i <
                         * noOfTerms; i++) {
                         *
                         * wordMap.put(terms[i], freq[i]); } } break;
                         */
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
    
    
    
    
    
    ////////////////////////////////////////////
     public HashMap<Integer, TreeMap> topKFiboTerms2(int noOfDocSections, String[] selectedDocuments) throws IOException, CorruptIndexException, ParseException, ClassNotFoundException, Exception {
        int noOfDocs = docNames.length;
        float tfIdfScore[][] = new float[noOfDocs][];
        //HashMap<Integer, float[]> scoreMap = new HashMap<Integer, float[]>();

        /*
         * @param scoreMap - what contains here????
         */
        HashMap<Integer, HashMap> scoreMap = new HashMap<Integer, HashMap>();
//scoreMap.ge
        /*
         * doing all indexing at once
         */
        //  MaxentTagger tagger = new MaxentTagger("tagger/left3words-wsj-0-18.tagger");
        //MaxentTagger tagger = new MaxentTagger("tagger/bidirectional-distsim-wsj-0-18.tagger");

        /**
         * Creates the index table in the database
         */
        jdbcIndex.createIndex();
        
        
         
        indexToDB();
        
          HashMap<Integer, TreeMap> topKTerms = new HashMap<Integer, TreeMap>();
          return topKTerms;
     }
    
    ////////////////////////////////
    
    /*
     * calculate top K terms for the document
     */
    
     public HashMap<Integer, TreeMap> topKFiboTerms(int noOfDocSections, String[] selectedDocuments) throws IOException, CorruptIndexException, ParseException, ClassNotFoundException, Exception {
        int noOfDocs = docNames.length;
        float tfIdfScore[][] = new float[noOfDocs][];
        //HashMap<Integer, float[]> scoreMap = new HashMap<Integer, float[]>();

        /*
         * @param scoreMap - what contains here????
         */
        HashMap<Integer, HashMap> scoreMap = new HashMap<Integer, HashMap>();
//scoreMap.ge
        /*
         * doing all indexing at once
         */
        //  MaxentTagger tagger = new MaxentTagger("tagger/left3words-wsj-0-18.tagger");
        //MaxentTagger tagger = new MaxentTagger("tagger/bidirectional-distsim-wsj-0-18.tagger");

        /**
         * Creates the index table in the database
         */
        jdbcIndex.createIndex();
        
        
         
        indexToDB();
        
        jdbcDir=jdbcIn.getJdbcDirectory();
       // JdbcDirectory dd= (JdbcDirectory)jdbcIn.getJdbcDirectory();
       // Directory ddd= (Directory)dd;
       //  this.ramMemDir=new RAMDirectory(jdbcDir);
        
       // jdbcIn.setJdbcDirectory(new MyJDBCDirectory(JDBCDatabaseUtil.getDataSource(), new MySQLDialect(), "lucene_index_table_okapi"));
//
//        for (int i = 0; i < noOfDocs; i++) {
//            index(i);
//            //indexWithTaxoFibo(i);
//            // indexWithSPPOSTagger(i,tagger);
//        }

//        if (!scoreMap.isEmpty()){
//            scoreMap.clear();
//        }


        int weight = 1;


        //  scoreMap = tfIdfScoreWithMarkUpTerms(noOfDocs,weight);
//        for(int o=0;o<scoreMap.size();o++){
//            float test[]=scoreMap.get(o);
//            for (int n=0;n<test.length;n++){
//                System.out.println(test[n]+"\n");
//                
//            }
//        }




        /*
         * calculating Okapi similarity
         *
         */



        //score map specific for Okapi Sim
        scoreMap = getTfForDocs(noOfDocs, weight);
        /*
         * printing average document length
         */

        /*
         * int docsectionWordCount=0; int fidosectionWordCount=0;
         * HashMap<String, Integer> wordMap; for(int k=0;k<noOfDocSections;k++){
         * wordMap=scoreMap.get(k); for(Map.Entry entry: wordMap.entrySet()){
         * docsectionWordCount=docsectionWordCount+(Integer)entry.getValue(); }
         *
         * }
         *
         * System.out.println("Average words per
         * docSection="+docsectionWordCount/noOfDocSections ); for(int
         * k=noOfDocSections;k<noOfDocs;k++){ wordMap=scoreMap.get(k);
         * for(Map.Entry entry: wordMap.entrySet()){
         * fidosectionWordCount=fidosectionWordCount+(Integer)entry.getValue();
         * }
         *
         * }
         *
         * System.out.println("Average words per fibo
         * docs="+fidosectionWordCount/(noOfDocs-noOfDocSections) );
         */
        ArrayList<Double> simi = new ArrayList<Double>();

//MyJDBCDirectory myJdbcDir = jdbcIn.getJdbcDirectory();

       OkapiSimilarity okapiSim = new OkapiSimilarity(jdbcDir);
       //   OkapiSimilarity okapiSim = new OkapiSimilarity(ramMemDir);

        //----------------------------------------------------------------------------
        /*
         * Store <Section ID,TopKterms>
         */
        //HashMap<Integer, TreeMap> topKTerms = new HashMap<Integer, TreeMap>();
        HashMap<Integer, TreeMap> topKTerms = new HashMap<Integer, TreeMap>();
        for (int p = 0; p < noOfDocSections; p++) {
            /////////////////////////////////////////////////////////
            int noOfFiles;

            Double db[] = new Double[noOfDocs];



            // System.out.println("\n" + docList[p] + "\n");


            double sim[];
            // ArrayList<Double> simi;
            HashMap<String, Double> termsScore = new HashMap<String, Double>();

            try {
                //sim = doc.consineSimilarityTo(0);
                //sim=docInd.consineSimilarityTo(0);
                // simi = docInd.consineSimilarityTo2(p);
                db = okapiSim.computeSimilarity(scoreMap, p);
                simi.addAll(Arrays.asList(db));

                int aa = 0;
                //Printing the similarity values
                for (int i = noOfDocSections; i < simi.size(); i++) {
                    aa++;
                    double temp = simi.get(i);
//
//                    if (Double.isNaN(temp)) {
//                        System.out.println(0.0);
//                    } else {
//                        System.out.println(temp);
//                    }
                    if (!Double.isNaN(temp) && temp > 10.274) {
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
                /*
                 * for (Map.Entry entry : sorted_map.entrySet()) {
                 * //tempSortedMap.put((String)entry.getKey(),
                 * (Double)entry.getValue()); count++; if (count > 15) {
                 * sorted_map.remove(entry.getKey()); }
                 *
                 * }
                 */


                topKTerms.put(Integer.parseInt(selectedDocuments[p]), new TreeMap<String, Double>(sorted_map));
//tempSortedMap.clear();

            } catch (IOException e) {
                sim = null;
                e.printStackTrace();
            }

            simi.clear();
        }

        //------------------------------------------------------------------------------




//myJdbcDir.close();


        return topKTerms;


    }

}
