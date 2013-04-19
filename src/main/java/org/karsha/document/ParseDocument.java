/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.karsha.document;

import edu.stanford.nlp.ling.HasWord;
import edu.stanford.nlp.ling.Sentence;
import edu.stanford.nlp.process.DocumentPreprocessor;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author lsf
 */
public class ParseDocument {

     public ArrayList<String> txtDocToSentence(String paragraph) {
    // This option shows loading and sentence-segment and tokenizing
    // a file using DocumentPreprocessor
      ArrayList<String> sentenceList = new ArrayList<String>();

      
Reader reader = new StringReader(paragraph);

    for (List<HasWord> sentence : new DocumentPreprocessor(reader)) {
       //Add sentence to array List
   // sentenceList.add(sentence.toString());
    sentenceList.add(Sentence.listToString(sentence));
   //String aa= Sentence.listToString(sentence);
        
 
    }
   
    return sentenceList;
  }

     


}
