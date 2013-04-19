/**
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
 *
 * Date Author Changes Jun 14, 2012 Kasun Perera Created
 *
 */
package org.karsha.base;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.DefaultSimilarity;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.store.RAMFile;

/**
 * This class calculates the similarity between documents Okapi Scheme mentioned
 * here http://www2002.org/CDROM/refereed/643/node6.html Most of the codes taken
 * from Shanchan Wu's implementation of Okapi Similarity
 */
public class OkapiSimilarity {

    private String searchField = "doccontent";
    private IndexReader indexReader = null;
    /**
     * The length of query after being tokenized.
     */
    private int numUsefuleQueryWords;
    private int totalNumIndexedDocs;
    /**
     * Map a word to the number of documents which contain this word.
     */
    private HashMap<String, Integer> docFreqHash;
    /**
     * Map a word to the idf value of this word (only the words in the query is
     * hashed)
     */
    private HashMap<String, Double> idfHash;
    private double sumIdfValueOfQuery;
    /**
     * The hash which maps TermDocs to the string of this term (word)
     */
    private HashMap<TermDocs, String> termDocsToWordHash;
    private HashMap<String, Double> wordToTFWeightHash;
    HashMap<Integer, HashMap> tfWeightOfEachDocMap;
    HashMap<String, Double> wordMap;
    HashMap<String, Integer> wordVector;
    /**
     * The average length of the documents in the index (after being tokenized)
     */
    private double avgDocLength;
    /**
     * The norms of the document lengths in the index
     */
    private byte[] normsDocLengthArr = null;
    private byte[] normsDocLengthArrField1 = null;
    private byte[] normsDocLengthArrField2 = null;
    private byte[] normsDocLengthArrField3 = null;
    /**
     * use in test cases.
     */
    private boolean bPrint = false;

    /**
     * The constructor with the index directory argument
     *
     * @param indexDir the index directory
     */
    public OkapiSimilarity(RAMDirectory ramMemDir) {

        try {

            indexReader = IndexReader.open(ramMemDir);
            //TODo there are several field sin the documents need to consider every field for average lenth 
            normsDocLengthArr = indexReader.norms("doccontent");
            //norms-Returns the byte-encoded normalization factor for the named field of every document.
/*
             * get normlization factor when document has more than one field
             */
//        normsDocLengthArrField1 = indexReader.norms("filed1");
//        normsDocLengthArrField2 = indexReader.norms("filed2");
//        normsDocLengthArrField3 = indexReader.norms("filed3");

            setAverageLength();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    OkapiSimilarity(Directory jdbcDir) {
         try {

            indexReader = IndexReader.open(jdbcDir);
            //TODo there are several field sin the documents need to consider every field for average lenth 
            normsDocLengthArr = indexReader.norms("doccontent");
            //norms-Returns the byte-encoded normalization factor for the named field of every document.
/*
             * get normlization factor when document has more than one field
             */
//        normsDocLengthArrField1 = indexReader.norms("filed1");
//        normsDocLengthArrField2 = indexReader.norms("filed2");
//        normsDocLengthArrField3 = indexReader.norms("filed3");

            setAverageLength();
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * set the average document length. normsDocLengthArr must be loaded into
     * memory before calling this method.
     */
    private void setAverageLength() {

        //TODO evryage lenth should be calculated for every filed
        double sumLength = 0;

        for (int i = 0; i < normsDocLengthArr.length; i++) {
            double encodeLength = DefaultSimilarity.decodeNorm(normsDocLengthArr[i]);
            //decodeNorm -Decodes a normalization factor stored in an index.
            double length = 1 / (encodeLength * encodeLength);

            sumLength += length;

        }

        this.avgDocLength = sumLength / normsDocLengthArr.length;
    }

    private void setMultipleFieldAverageLength() {


        //private double avgDocLength;

        //norms-Returns the byte-encoded normalization factor for the named field of every document.
        double sumLength = 0;
        for (int i = 0; i < normsDocLengthArrField1.length; i++) {
            double encodeLengthFOne = DefaultSimilarity.decodeNorm(normsDocLengthArrField1[i]);
            double encodeLengthFTwo = DefaultSimilarity.decodeNorm(normsDocLengthArrField2[i]);
            double encodeLengthFThree = DefaultSimilarity.decodeNorm(normsDocLengthArrField3[i]);

            //decodeNorm -Decodes a normalization factor stored in an index.
            double lengthFieldOne = 1 / (encodeLengthFOne * encodeLengthFOne);
            double lengthFieldTwo = 1 / (encodeLengthFTwo * encodeLengthFTwo);
            double lengthFieldThree = 1 / (encodeLengthFThree * encodeLengthFThree);
            sumLength += lengthFieldOne + lengthFieldTwo + lengthFieldThree;

        }
        this.avgDocLength = sumLength / (normsDocLengthArrField1.length);
    }

    /**
     * Computer the similarity of the contentToCompare to the content where
     * URL=urlOfTarget in the field of fieldOfTargetURL.
     *
     * @param contentToCompare
     * @param urlOfTarget
     * @param fieldOfTargetURL
     * @return
     * @throws Exception
     */
    public Double[] computeSimilarity(HashMap<Integer, HashMap> termFrequencyMap, int queryIndex) throws Exception {

        wordVector = termFrequencyMap.get(queryIndex);

        //numUsefuleQueryWords = wordsList.length;

        //HashMap<String, WordLocation> wordHash = this.genWordHash(wordsList);
        //Locations of the words in query, word (a word may have several locations)

        //Collection<WordLocation>  wordLocationCollection= wordHash.values();
        //int numDistWord = wordLocationCollection.size();
        int numDistWord = wordVector.size();
        numUsefuleQueryWords = numDistWord;
        //indexReader = IndexReader.open("index");
        totalNumIndexedDocs = indexReader.maxDoc();
        //maxDoc- Returns one greater than the largest possible document number.

        docFreqHash = new HashMap<String, Integer>();
        this.termDocsToWordHash = new HashMap<TermDocs, String>();
        this.idfHash = new HashMap<String, Double>();

        Term[] termArr = new Term[numDistWord];
        ArrayList<TermDocs> curTermDocsList = new ArrayList<TermDocs>();

        this.wordToTFWeightHash = new HashMap<String, Double>();

        //initialize the terms, docFreqHash
        sumIdfValueOfQuery = 0;
        //wordLocationCollection is list of words and their locations 
        //Iterator<WordLocation> wordIterator = wordLocationCollection.iterator();
        int i = 0;
        for (Iterator it = wordVector.keySet().iterator(); it.hasNext();) {
            String w = (String) it.next();
            int tfValue = (Integer) wordVector.get(w);
            Term termArrTest = new Term(searchField, w);
            int docFreq = indexReader.docFreq(termArrTest);
            // System.out.println(w + "\t" + docFreq);
            docFreqHash.put(w, docFreq);

            double idf = (this.totalNumIndexedDocs - docFreq + 0.5) / (docFreq + 0.5);
            // System.out.println(idf);
            idf = Math.log(idf);
            // System.out.println(idf);
            if (idf < 0) {
                idf = 0;
            }

            this.idfHash.put(w, idf);

            i++;
        }

        tfWeightOfEachDocMap = new HashMap<Integer, HashMap>();


        for (int j = 0; j < termFrequencyMap.size(); j++) {
            //System.out.println("--------------------------------------------------------------------------------\n");

            double encodeLength = DefaultSimilarity.decodeNorm(this.normsDocLengthArr[j]);
            double docLen = 1 / (encodeLength * encodeLength);
            wordMap = new HashMap<String, Double>();
            for (Iterator it = wordVector.keySet().iterator(); it.hasNext();) {
                String w = (String) it.next();
                int vec2Val = (Integer) (termFrequencyMap.get(j).containsKey(w) ? termFrequencyMap.get(j).get(w) : 0);
                //System.out.println(w +"-" +vec2Val+"\n");
                double weight = this.calTFWeight(vec2Val, docLen);
                //wordToTFWeightHash.put(w, weight);
                wordMap.put(w, weight);

            }

            tfWeightOfEachDocMap.put(j, wordMap);

        }

        Double[] similarityArr = new Double[termFrequencyMap.size()];
        for (int k = 0; k < termFrequencyMap.size(); k++) {
            similarityArr[k] = getSimilarityValue(queryIndex, k);
        }
/*
           //calculating Similarity for all the combination of the documents 
      
        int j;
        for (int i = 0; i <noOfDocs; i++) {
            for( j=i;j<noOfDocs;j++){
         
          //simi.add(Similarity.getCosineSimilarity(scoreMap.get(i), scoreMap.get(j)));
            simi.add(Similarity.getJaccardSimilarity(scoreMap.get(i), scoreMap.get(j)));
          
         
          }
        }
 */       


        return similarityArr;
    }

    /**
     *
     * @param queryDocID
     * @param comparingDocID
     * @return
     */
    private Double getSimilarityValue(int queryDocID, int comparingDocID) {
        double sumSimValue = 0;
        //System.out.println("----------------------------------------------------------------------------------");

//           for (Iterator it2 = tfWeightOfEachDocMap.get(comparingDocID).keySet().iterator(); it2.hasNext();) {
//            String ww = (String) it2.next();
//            System.out.println(ww);
//             }
        for (Iterator it = wordVector.keySet().iterator(); it.hasNext();) {
            String w = (String) it.next();
            Double tfValueQueryTerm = (Double) tfWeightOfEachDocMap.get(queryDocID).get(w);

            //int tfValueQueryTerm = (Integer) wordVector.get(w);


            Double tfValueDocTerm = (Double) tfWeightOfEachDocMap.get(comparingDocID).get(w);
            double idf = this.idfHash.get(w);


            sumSimValue += tfValueQueryTerm * tfValueDocTerm * idf;
        }


        return sumSimValue;
    }

    /**
     * Calculate the weight of document frequency by the formulas: | (k1 + 1) *
     * tf | ---------------------------- | k1*[(1-b) + b*dl/avdl] + tf
     *
     * where b=0.75 and k1 = 1.2
     *
     * @param tf
     * @param docLen
     * @return
     */
    private double calTFWeight(int tf, double docLen) {
        double up = 2.2 * tf;
        double down = 1.2 * (0.25 + 0.75 * docLen / this.avgDocLength) + tf;

        return up / down;
    }
}
