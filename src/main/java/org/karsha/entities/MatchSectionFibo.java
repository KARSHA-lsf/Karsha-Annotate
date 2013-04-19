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
 * Date             Author          Changes 
 * Oct 19, 2012     Kasun Perera    Created   
 * 
 */ 
package org.karsha.entities;
import java.io.Serializable;


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
