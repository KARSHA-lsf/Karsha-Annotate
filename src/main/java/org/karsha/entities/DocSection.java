/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.entities;

import java.io.Serializable;

/**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes Sep 5, 2012 Kasun Perera Created
 *
 */
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
