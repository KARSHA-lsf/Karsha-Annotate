/*
 *   Copyright (C) 2013, Lanka Software Foundation and University of Maryland.
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
