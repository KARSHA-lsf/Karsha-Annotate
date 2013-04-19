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
 * This class defines calss variables related to FiboTerm table
 */
public class FiboTerm implements Serializable{
    
     private int fiboId;
    private String fiboTerm;
    private String fiboDefinition;
    private int collectionID;
    private double simScore;
   

    public int getFiboId() {
        return fiboId;
    }

    public void setFiboId(int fiboId) {
        this.fiboId = fiboId;
    }
 public double getSimScore() {
        return simScore;
    }

    public void setSimScore(double simScore) {
        this.simScore = simScore;
    }


    public String getFiboTerm() {
        return fiboTerm;
    }

    public void setFiboTerm(String fiboTerm) {
        this.fiboTerm = fiboTerm;
    }
    
     public String getFiboDefinition() {
        return fiboDefinition;
    }

    public void setFiboDefinition(String fiboDefinition) {
        this.fiboDefinition = fiboDefinition;
    }
    
       public int getCollectionID() {
        return collectionID;
    }

    public void setCollectionID(int collectionID) {
        this.collectionID = collectionID;
    }
    
    
}
