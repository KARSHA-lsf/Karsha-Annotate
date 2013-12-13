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
