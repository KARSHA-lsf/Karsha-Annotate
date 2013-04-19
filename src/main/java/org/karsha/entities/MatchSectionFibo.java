/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.karsha.entities;
import java.io.Serializable;
/** 
 * Copyright (C) 2012, Lanka Software Foundation. 
 * 
 * Date             Author          Changes 
 * Oct 19, 2012     Kasun Perera    Created   
 * 
 */ 

/**
 * TODO- describe the  purpose  of  the  class
 * 
 */
public class MatchSectionFibo implements Serializable{
    
     private int sectionId;
    private int fiboTermId;
    private int userId;
    private double simScore;

    public double getSimScore() {
        return simScore;
    }

    public void setSimScore(double simScore) {
        this.simScore = simScore;
    }
   

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }


    public int getFiboTermId() {
        return fiboTermId;
    }

    public void setFiboTermId(int fiboTermId) {
        this.fiboTermId = fiboTermId;
    }

    
    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    

}
