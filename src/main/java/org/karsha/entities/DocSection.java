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
 * Date Author Changes Sep 5, 2012 Kasun Perera Created
 *
 */
package org.karsha.entities;

import java.io.Serializable;


/**
 * This class defines class variables related to Document table
 *
 */
public class DocSection implements Serializable {

    private int sectionId;
    private String sectionName;
    private String dateUpdated;
    private int userId;
    private int parentDocId;
     private int sectionCatog;
    private byte[] sectionContent;

    public String getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    
            
             public int getSectionCatog() {
        return sectionCatog;
    }

    public void setSectionCatog(int sectionCatog) {
        this.sectionCatog = sectionCatog;
    }
            
    public int getParentDocId() {
        return parentDocId;
    }

    public void setParentDocId(int parentDocId) {
        this.parentDocId = parentDocId;
    }
    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] getSectiontContent() {
        return sectionContent;
    }

    public void setSectionContent(byte[] sectionContent) {
        this.sectionContent = sectionContent;
    }
}
