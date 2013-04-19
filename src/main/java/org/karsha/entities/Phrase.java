/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.entities;
import java.io.Serializable;
/**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes 
 * April 14, 2012 Kasun Perera Created
 * 
 *
 */

/*
 * This class defines calss variables related to Phrase table
 */
public class Phrase implements Serializable{
    
    private int docId;
    private int phraseId;
    private String phraseContent;
   // private String dateUpdated;
    private int score;
    private String importance;
    private int userId;
   

    public int getPhraseId() {
        return phraseId;
    }

    public void setPhraseId(int phraseId) {
        this.phraseId = phraseId;
    }

//    public String getDateUpdated() {
//        return dateUpdated;
//    }
//
//    
//    public void setDateUpdated(String dateUpdated) {
//        this.dateUpdated = dateUpdated;
//    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    
     public String getImportance() {
        return importance;
    }

    public void setImportance(String importance) {
        this.importance = importance;
    }
    public String getPhraseContent() {
        return phraseContent;
    }

    public void setPhraseContent(String phraseContent) {
        this.phraseContent = phraseContent;
    }
    
     public int getScore() {
        return score;
    }
     
     public String getScoreAsString() {
        
         String value = "Not Selected";
         
         switch (score) {
             case 1: value = "Very Important";break;   
             case 2: value = "Important"; break;
             case 3: value = "Average"; break;
         }
         
         return value;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    
}
