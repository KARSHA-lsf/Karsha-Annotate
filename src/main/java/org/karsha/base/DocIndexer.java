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

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.process.DocumentPreprocessor;
import edu.stanford.nlp.tagger.maxent.MaxentTagger;
import java.io.*;
import java.util.*;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.Token;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.TermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.*;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.NIOFSDirectory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.karsha.entities.DocSection;
import org.karsha.tokenize.DefaultTokenizer;
/*
import org.pdfbox.cos.COSDocument;
import org.pdfbox.io.RandomAccessBuffer;
import org.pdfbox.pdfparser.PDFParser;
import org.pdfbox.pdmodel.PDDocument;
import org.pdfbox.util.PDFTextStripper;
* 
* /
/*
 * import org.apache.pdfbox.cos.COSDocument; import
 * org.apache.pdfbox.io.RandomAccessBuffer; import
 * org.apache.pdfbox.pdfparser.PDFParser; import
 * org.apache.pdfbox.pdmodel.PDDocument; import
 * org.apache.pdfbox.util.PDFTextStripper; /
 *
 * /**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes April 14, 2012 Kasun Perera Created
 *
 */

/**
 *
 * Class contains methods for indexing documents with Lucene, and calculating
 * TFIDF weights
 */
public class DocIndexer {

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

    /**
     * Constructor used when indexing directory is the Local directory
     *
     * @param fileNames-The physical Location of the documents
     * @param docNames -Corresponding Document name List
     */
    /*
     * public DocIndexer(String fileNames[], String docNames[]) {
     *
     * this.docNames = docNames; this.pathToIndex =
     * "C:\\Users\\lsf\\Desktop\\index"; this.fileNames = fileNames;
     *
     * //this.ramMemDir = new RAMDirectory(); //pathToIndex = new
     * RAMDirectory().toString();;//this.bufPathToIndex.toString() ;
     * //this.queryDocIndex = queryDocIndex ; int len = fileNames.length;
     * this.noOfWordsOfDOc = new int[len]; this.ArrLstSentencesOfDoc = new
     * ArrayList[len]; this.noOfSentencesOfDoc = new int[len];
     * this.removedTermsOfDOc = new String[len][]; this.freqAfterRemovalOfDoc =
     * new int[len][]; this.curDocNo = 0; //this.termsOfFIBO = fiboTerms ;
     * //this.termsOfFIBO = fiboTerms ; }
     */
    /**
     * Constructor used when indexing directory is the Local directory and FIBO
     * and Taxomommy terms taken into indexing
     *
     * @param fileNames-The physical Location of the documents
     * @param docNames -Corresponding Document name List
     * @param fiboTermList-Corresponding FIBO term List
     * @param taxoTermList-Corresponding Taxonomy terms List
     */
    public DocIndexer(String fileNames[], String docNames[], String fiboTermList[], String taxoTermList[]) {

        this.docNames = docNames;
        //this.pathToIndex = "C:\\Users\\lsf\\Desktop\\index" ;
        this.fileNames = fileNames;
        this.fiboTermList = fiboTermList;
        this.taxoTermList = taxoTermList;

        this.ramMemDir = new RAMDirectory();
        //pathToIndex = new RAMDirectory().toString();;//this.bufPathToIndex.toString() ;
        //this.queryDocIndex = queryDocIndex ;
        int len = fileNames.length;
        this.noOfWordsOfDOc = new int[len];
        this.ArrLstSentencesOfDoc = new ArrayList[len];
        this.noOfSentencesOfDoc = new int[len];
        this.removedTermsOfDOc = new String[len][];
        this.freqAfterRemovalOfDoc = new int[len][];
        this.curDocNo = 0;
        //this.termsOfFIBO = fiboTerms ;
        //this.termsOfFIBO = fiboTerms ;
    }

    /**
     * Constructor used when indexing directory is a RAM memory directory, We
     * need RAM directory because Stratoes Server dosen't allow access local
     * files
     *
     * @param files- List of Documents converted in to bytes
     * @param docNames -Corresponding Document names
     */
    public DocIndexer(byte files[][], String docNames[]) {
        this.docNames = docNames;

        //this.bufPathToIndex= new RandomAccessBuffer() ;
        this.ramMemDir = new RAMDirectory();
        //pathToIndex = new RAMDirectory().toString();;//this.bufPathToIndex.toString() ;
        this.files = files;
        //this.queryDocIndex = queryDocIndex ;
        int len = files.length;
        this.noOfWordsOfDOc = new int[len];
        this.ArrLstSentencesOfDoc = new ArrayList[len];
        this.noOfSentencesOfDoc = new int[len];
        this.removedTermsOfDOc = new String[len][];
        this.freqAfterRemovalOfDoc = new int[len][];
        this.curDocNo = 0;
        //this.termsOfFIBO = fiboTerms ;
    }

    /**
     * Constructor used when indexing directory is a RAM memory directory, We
     * need RAM directory because Wso2-Stratoes Server that we used to host
     * dosen't allow access local files
     *
     * @param files- List of Documents converted in to bytes
     * @param docNames -Corresponding Document names
     */
    public DocIndexer(String docContent[], String docNames[]) {
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
     * Constructor used when indexing directory is a RAM memory directory, We
     * need RAM directory because Stratoes Server dosen't allow access local
     * files. And this uses FIBO and taxomony terms for the indexing
     *
     * @param files- List of Documents converted in to bytes
     * @param docNames -Corresponding Document names
     * @param fiboTermList- Corresponding FIBO term List
     * @param taxoTermList -Corresponding Taxomony term List
     */
    public DocIndexer(byte files[][], String docNames[], String fiboTermList[], String taxoTermList[]) {
        this.docNames = docNames;
        this.fiboTermList = fiboTermList;
        this.taxoTermList = taxoTermList;
        //this.bufPathToIndex= new RandomAccessBuffer() ;
        this.ramMemDir = new RAMDirectory();
        // pathToIndex = new RAMDirectory().toString();;//this.bufPathToIndex.toString() ;
        this.files = files;
        //this.queryDocIndex = queryDocIndex ;
        int len = files.length;
        this.noOfWordsOfDOc = new int[len];
        this.ArrLstSentencesOfDoc = new ArrayList[len];
        this.noOfSentencesOfDoc = new int[len];
        this.removedTermsOfDOc = new String[len][];
        this.freqAfterRemovalOfDoc = new int[len][];
        this.curDocNo = 0;
        //this.termsOfFIBO = fiboTerms ;
    }

    /**
     * Method Used to covert PDF documents in to Text, Because Lucene Can't
     * Process PDF documents directly. This method uses Apache PDFBox Library,
     * and it uses RAM buffer for parsing. Documents have to be initialize using
     * constructor before using this method
     *
     * @param docNo- give the corresponding document ID, (ID selected from the
     * order of the documents Initialized)
     * @return- converted text of the corresponding PDF file as a String
     * @throws IOException
     */
    
    /*
    public String convertPDFToText(int docNo) throws IOException {

        PDFParser parser;
        String parsedText;
        PDFTextStripper pdfStripper;
        PDDocument pdDoc = null;
        COSDocument cosDoc = null;

        try {

            // parser.parse();
            // cosDoc = parser.getDocument();
            pdfStripper = new PDFTextStripper();
            RandomAccessBuffer tempMemBuffer = new RandomAccessBuffer(); //curDocNo++ ;
            pdDoc = PDDocument.load(new ByteArrayInputStream(files[docNo]), tempMemBuffer);
            //pdDoc = new PDDocument(cosDoc);
            parsedText = pdfStripper.getText(pdDoc);


            //System.out.println("An exception occured in parsing the PDF Document.");
        } catch (Exception e) {
            // System.out.println("An exception occured in parsing the PDF Document.");
            e.printStackTrace();

            return null;
        } finally {
            if (cosDoc != null) {
                cosDoc.close();
            }
            if (pdDoc != null) {
                pdDoc.close();
            }
        }




        if (parsedText == null) {

            parsedText = "PDF to Text Conversion failed";

        }


        return parsedText;

    }
*/
    /**
     * This method used to read the text file when running the program locally
     *
     * @param fileName- full file path of the document
     * @return text of the corresponding document as a String
     */
    public String ReadTextFile(String fileName) {

        String fileasString = null;
        try {

            String strLine;
            // Open the file that is the first 
            // command line parameter
            FileInputStream fstream = new FileInputStream(fileName);
            // Get the object of DataInputStream
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            //String strLine;
            //Read File Line By Line

            int h = 0;
            while ((strLine = br.readLine()) != null) {
                //  while ((strLine = br.readLine()) != null && h<100) {
                // Print the content on the console
                // System.out.println(strLine);
                fileasString = fileasString + strLine;
                h++;
            }
            //Close the input stream
            in.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }

        return fileasString;
    }

    /**
     * Count the number of words in a given String
     *
     * @param line- Input String
     * @return - number of words in the input String
     */
    private int wordCount(String line) {
        int numWords = 0;
        int index = 0;
        boolean prevWhiteSpace = true;
        while (index < line.length()) {
            char c = line.charAt(index++);
            boolean currWhiteSpace = Character.isWhitespace(c);
            if (prevWhiteSpace && !currWhiteSpace) {
                numWords++;
            }
            prevWhiteSpace = currWhiteSpace;
        }
        return numWords;
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
        StringReader strRdElt = new StringReader(new DefaultTokenizer().processText(filesInText[docNo]));
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
            iW = new IndexWriter(ramMemDir, new IndexWriterConfig(Version.LUCENE_35,
                    new StandardAnalyzer(Version.LUCENE_35)));
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
     * documents in the indexed order RAM directory is used for indexing It
     * index the taxonomy and FIBO terms as separate fields
     *
     * @param docNo- document number of the document to be indexed
     * @throws IOException
     * @throws ClassNotFoundException
     */
    
    /*
    public void indexWithTaxoFibo(int docNo) throws IOException, ClassNotFoundException {
        String content = convertPDFToText(docNo);
        //String content = ReadTextFile(fileNames[docNo]);


        this.noOfWordsOfDOc[docNo] = wordCount(content);
        StringReader strRdElt = new StringReader(new DefaultTokenizer().processText(content));
        StringReader docId = new StringReader(Integer.toString(docNo));

        // String fibo_taxo = "putable";




        Document doc = new Document();

        doc.add(new Field("doccontent", strRdElt, Field.TermVector.YES));
        doc.add(new Field("docid", docId, Field.TermVector.YES));
        doc.add(new Field("fiboterms", fiboTermList[docNo], Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));
        doc.add(new Field("taxoterms", taxoTermList[docNo], Field.Store.YES, Field.Index.ANALYZED, Field.TermVector.YES));

        //  doc.add(new Field(docNames ;
        //this.ArrLstSentencesOfDoc[curDocNo] = sentenceCount(content);
        //this.noOfSentencesOfDoc[curDocNo] = this.ArrLstSentencesOfDoc[curDocNo].size() ;
        IndexWriter iW;
        try {
            //NIOFSDirectory dir = new NIOFSDirectory(new File(pathToIndex)) ;
            //dir = new RAMDirectory() ;
//iW = new IndexWriter(dir, new IndexWriterConfig(Version.LUCENE_35,
            // new StandardAnalyzer(Version.LUCENE_35)));
            iW = new IndexWriter(ramMemDir, new IndexWriterConfig(Version.LUCENE_35,
                    new StandardAnalyzer(Version.LUCENE_35)));
            iW.addDocument(doc);
            iW.close();
            //dir.close() ;
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    /**
     * Method to index the documents only using the content of the document
     * "docid" field is used for indexing, since Lucene Dosen't retrieve the
     * documents in the indexed order RAM directory is used for indexing
     * Indexing with Lemmatization of terms
     *
     * @param docNo
     * @param tagger
     * @throws IOException
     * @throws ClassNotFoundException
     */
    
    /*
    public void indexWithSPPOSTagger(int docNo, MaxentTagger tagger) throws IOException, ClassNotFoundException {
        String content = convertPDFToText(docNo);
        //String content = ReadTextFile(fileNames[docNo]);
        String lemmatizedText = analyze(new DefaultTokenizer().processText(content), tagger);

        //this.noOfWordsOfDOc[docNo] = wordCount(content);
        //StringReader strRdElt = new StringReader(content);
        this.noOfWordsOfDOc[docNo] = wordCount(lemmatizedText);
        StringReader strRdElt = new StringReader(lemmatizedText);

        StringReader docId = new StringReader(Integer.toString(docNo));

        Document doc = new Document();

        doc.add(new Field("doccontent", strRdElt, Field.TermVector.YES));
        doc.add(new Field("docid", docId, Field.TermVector.YES));




        // Initialize the tagger
        // MaxentTagger tagger = new MaxentTagger("tagger/bidirectional-distsim-wsj-0-18.tagger");

        // The sample string
        //String sample = "This is a sample text";

        // The tagged string
        // String tagged = tagger.tagString(sample);

        // EnglishLemmaAnalyzer englemaanalyzer= new EnglishLemmaAnalyzer(tagger);


        IndexWriter iW;
        try {

            iW = new IndexWriter(ramMemDir, new IndexWriterConfig(Version.LUCENE_35,
                    new StandardAnalyzer(Version.LUCENE_35)));

            //iW = new IndexWriter(ramMemDir, new IndexWriterConfig(Version.LUCENE_35,englemaanalyzer));
            iW.addDocument(doc);
            iW.close();
            //dir.close() ;
        } catch (CorruptIndexException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
*/
    /**
     * This method handles the Lemmatization of given text using
     * EnglishLemmaAnalyzer
     *
     * @param text
     * @param tagger- should supply a Stanford parser "MaxentTagger" object
     * @return- Lemmatized text
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public String analyze(String text, MaxentTagger tagger) throws IOException, ClassNotFoundException {
        // System.out.println("Analzying "" + text + """);
        //MaxentTagger tagger = new MaxentTagger("tagger/bidirectional-distsim-wsj-0-18.tagger");

        Analyzer analyzer = new EnglishLemmaAnalyzer(tagger);
        //System.out.println("\t" + analyzer.getClass().getName() + ":");
        //System.out.print("\t\t");
        TokenStream stream = analyzer.tokenStream("contents", new StringReader(text));
        TermAttribute termAttribute = stream.getAttribute(TermAttribute.class);
        String term = null;
        while (stream.incrementToken()) {
            // stream.
            if (stream.incrementToken()) {
                term = term + " " + termAttribute.term();
//                Token token = stream.next();
//                if (token == null) break;

                // System.out.print("[" + term + "] \n");
            }
        }
        stream.clearAttributes();
        //System.out.println("\n");
        return term;
    }

    /**
     * This method calculates the TF-IDF score for each terms in the indexed
     * documents
     *
     * @param numberOfDocs
     * @return - Hashmap of TF-IDF score per each term in document wise
     * @throws CorruptIndexException
     * @throws ParseException
     */
    public HashMap<Integer, HashMap> tfIdfScore(int numberOfDocs) throws CorruptIndexException, ParseException {

        int noOfDocs = docNames.length;

        HashMap<Integer, HashMap> scoreMap = new HashMap<Integer, HashMap>();
        //HashMap<Integer, float[]> scoreMap = new HashMap<Integer, float[]>();


        try {

            //IndexReader re = IndexReader.open(FSDirectory.open(new File(pathToIndex)), true) ;
            IndexReader re = IndexReader.open(ramMemDir);

            int i = 0;
            for (int k = 0; k < numberOfDocs; k++) {
                int freq[];
                TermFreqVector termsFreq;
                TermFreqVector termsFreqDocId;
                //TermFreqVector termsFreq3[];
                HashMap<String, Float> wordMap = new HashMap<String, Float>();
                String terms[];
                float score[] = null;

                //termsFreq3=re.getTermFreqVectors(currentDocID);
                termsFreq = re.getTermFreqVector(k, "doccontent");
                termsFreqDocId = re.getTermFreqVector(k, "docid");

                int aInt = Integer.parseInt(termsFreqDocId.getTerms()[0]);
                freq = termsFreq.getTermFrequencies();

                terms = termsFreq.getTerms();

                int noOfTerms = terms.length;
                score = new float[noOfTerms];
                DefaultSimilarity simi = new DefaultSimilarity();
                for (i = 0; i < noOfTerms; i++) {
                    int noofDocsContainTerm = re.docFreq(new Term("doccontent", terms[i]));
                    // System.out.println(terms[i]+"\t"+freq[i]);
                    //int noofDocsContainTerm = docsContainTerm(terms[i], "docnames");
                    float tf = simi.tf(freq[i]);
                    float idf = simi.idf(noofDocsContainTerm, noOfDocs);
                    wordMap.put(terms[i], (tf * idf));

                }
                scoreMap.put(aInt, wordMap);
            }


        } catch (IOException e) {
            // score = null;
            e.printStackTrace();
        }



        //Map<Integer,Float[]> scoreMap=new Map<Integer, Float[]>(); 


        return scoreMap;
    }

    /**
     * This method calculates the TF-IDF score for each terms including markedup
     * Taxonoomy and FIBO terms, in the indexed documents
     *
     * @param numberOfDocs
     * @param weight- higher weight can be given for FIBO and Taxomony terms
     * with preference
     * @return - Hashmap of TF-IDF score per each term in document wise
     * @throws CorruptIndexException
     * @throws ParseException
     */
    public HashMap<Integer, HashMap> tfIdfScoreWithMarkUpTerms(int numberOfDocs, int weight) throws CorruptIndexException, ParseException {

        int noOfDocs = docNames.length;

        HashMap<Integer, HashMap> scoreMap = new HashMap<Integer, HashMap>();
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
                HashMap<String, Float> wordMap = new HashMap<String, Float>();
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
                for (int m = 0; m < 4; m++) {
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
                                int noofDocsContainTerm = re.docFreq(new Term("doccontent", terms[i]));

                                float tf = simi.tf(freq[i]);
                                float idf = simi.idf(noofDocsContainTerm, noOfDocs);
                                wordMap.put(terms[i], (tf * idf));

                            }

                            break;
                        case 1:        // doc Id
                            termsFreqDocId = re.getTermFreqVector(k, "docid");
                            // terms = termsFreqVec[1].getTerms();
                            aInt = Integer.parseInt(termsFreqDocId.getTerms()[0]);
                            break;
                        case 2:        //Fiboterms
                            termsFreqFiboTerm = re.getTermFreqVector(k, "fiboterms");
                            if (termsFreqFiboTerm != null) {
                                freq = termsFreqFiboTerm.getTermFrequencies();
                                terms = termsFreqFiboTerm.getTerms();

                                noOfTerms = terms.length;

                                score = new float[noOfTerms];
                                //DefaultSimilarity simi = new DefaultSimilarity();
                                for (i = 0; i < noOfTerms; i++) {
                                    int noofDocsContainTerm = re.docFreq(new Term("fiboterms", terms[i]));

                                    float tf = simi.tf(freq[i]);
                                    float idf = simi.idf(noofDocsContainTerm, noOfDocs);
                                    wordMap.put(terms[i], (tf * idf * weight));

                                }
                            }
                            break;
                        case 3:        //taxoterms
                            termsFreqTaxoTerm = re.getTermFreqVector(k, "taxoterms");
                            if (termsFreqTaxoTerm != null) {
                                freq = termsFreqTaxoTerm.getTermFrequencies();
                                terms = termsFreqTaxoTerm.getTerms();

                                noOfTerms = terms.length;

                                score = new float[noOfTerms];
                                //DefaultSimilarity simi = new DefaultSimilarity();
                                for (i = 0; i < noOfTerms; i++) {
                                    int noofDocsContainTerm = re.docFreq(new Term("taxoterms", terms[i]));

                                    float tf = simi.tf(freq[i]);
                                    float idf = simi.idf(noofDocsContainTerm, noOfDocs);
                                    wordMap.put(terms[i], (tf * idf * weight));
                                }
                            }
                            break;

                        default:
                        //System.out.println("Invalid Entry!");
                    }
                }


                scoreMap.put(aInt, wordMap);
            }


        } catch (IOException e) {
            // score = null;
            e.printStackTrace();
        }



        //Map<Integer,Float[]> scoreMap=new Map<Integer, Float[]>(); 


        return scoreMap;
    }

    /**
     * This method handles indexding documents in several ways(normal index,
     * index with FIBO and Taxo etc..) and the Similarity calculation using
     * different methods(Cosine, Jaccard, Okapi)
     *
     * @param queryIndex
     * @return -array of Simialrity Scores(Double)
     * @throws IOException
     * @throws CorruptIndexException
     * @throws ParseException
     * @throws ClassNotFoundException
     */
    public double[] consineSimilarityTo(int queryIndex) throws IOException, CorruptIndexException, ParseException, ClassNotFoundException {
        int noOfDocs = docNames.length;
        float tfIdfScore[][] = new float[noOfDocs][];
        //HashMap<Integer, float[]> scoreMap = new HashMap<Integer, float[]>();
        HashMap<Integer, HashMap> scoreMap = new HashMap<Integer, HashMap>();
//scoreMap.ge
        /*
         * doing all indexing at once
         */

        /*
         * creating the MaxentTagger object- path to tagger file should be
         * supplied (File can be downloded from Stanford parser website)
         */
        //MaxentTagger tagger = new MaxentTagger("tagger/left3words-wsj-0-18.tagger");
        //MaxentTagger tagger = new MaxentTagger("tagger/bidirectional-distsim-wsj-0-18.tagger");

        for (int i = 0; i < noOfDocs; i++) {
             index(i);
           // indexWithTaxoFibo(i);
            //indexWithSPPOSTagger(i,tagger);
        }

//        if (!scoreMap.isEmpty()){
//            scoreMap.clear();
//        }
        //scoreMap = tfIdfScore(noOfDocs);

        int weight = 1;
        scoreMap = tfIdfScoreWithMarkUpTerms(noOfDocs, weight);
//        for(int o=0;o<scoreMap.size();o++){
//            float test[]=scoreMap.get(o);
//            for (int n=0;n<test.length;n++){
//                System.out.println(test[n]+"\n");
//                
//            }
//        }

        double sim[] = new double[noOfDocs];
        //ArrayList<Double> simi = new ArrayList<Double>();
        /*
         * calculating cosine similarity
         *
         */


        for (int i = 0; i < noOfDocs; i++) {
            sim[i] = Similarity.getCosineSimilarity(scoreMap.get(queryIndex), scoreMap.get(i));
            //simi.add(Similarity.getCosineSimilarity(scoreMap.get(queryIndex), scoreMap.get(i)));
            //simi.add(Similarity.getJaccardSimilarity(scoreMap.get(queryIndex), scoreMap.get(i)));
        }


        /*
         * //calculating the factor of * int j; for (int i = 0; i <noOfDocs;
         * i++) { for( j=i;j<noOfDocs;j++){
         *
         * //simi.add(Similarity.getCosineSimilarity(scoreMap.get(i),
         * scoreMap.get(j)));
         * simi.add(Similarity.getJaccardSimilarity(scoreMap.get(i),
         * scoreMap.get(j)));
         *
         *
         * }
         * }
         *
         */

        return sim;
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

     public HashMap<Integer, TreeMap> topKFiboTermsTest(int noOfDocSections, String[] selectedDocuments, double okapiCutOff){
          int noOfDocs = docNames.length;
         
         for (int i = 0; i < noOfDocs; i++) {
            try {
                index(i);
                // indexWithSPPOSTagger(i,tagger);
                // indexWithSPPOSTagger(i,tagger);
            } catch (IOException ex) {
                ex.printStackTrace();
                Logger.getLogger(DocIndexer.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
      HashMap<Integer, TreeMap> topKTerms = new HashMap<Integer, TreeMap>();
      
      return topKTerms;
         }
     
     
     
     
     
    public HashMap<Integer, TreeMap> topKFiboTerms(int noOfDocSections, String[] selectedDocuments, double okapiCutOff) throws IOException, CorruptIndexException, ParseException, ClassNotFoundException, Exception {
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

        for (int i = 0; i < noOfDocs; i++) {
            index(i);
            //indexWithTaxoFibo(i);
            // indexWithSPPOSTagger(i,tagger);
        }

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
        
        ///////////////////////////////////////////////////////////////////////////////
        scoreMap = getTfForDocs(noOfDocs, weight);
        
        /////////////////////////////////////////////////////////////////////////////////
        
        
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


        OkapiSimilarity okapiSim = new OkapiSimilarity(ramMemDir);

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
            
            ///////////////////////////////////////////////////////////////////////
            
            
            
            

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
                


                topKTerms.put(Integer.parseInt(selectedDocuments[p]), new TreeMap<String, Double>(sorted_map));
//tempSortedMap.clear();

            } catch (IOException e) {
                sim = null;
                e.printStackTrace();
            }

            simi.clear();
        }

        //------------------------------------------------------------------------------







        return topKTerms;


    }
}
