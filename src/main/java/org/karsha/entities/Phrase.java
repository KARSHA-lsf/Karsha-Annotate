/**
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
 *
 * Date Author Changes 
 * April 14, 2012 Kasun Perera Created
 * 
 *
 */
package org.karsha.entities;
import java.io.Serializable;


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
