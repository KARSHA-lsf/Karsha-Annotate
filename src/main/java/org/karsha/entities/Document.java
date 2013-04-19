/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.entities;

import java.io.Serializable;

/**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes April 14, 2012 v-bala Created
 *
 */

/*
 * This class defines class variables related to Document table
 */
public class Document implements Serializable{
    private int docId;
    private String documentName;
    private String dateUpdated;
    private int userId;
    private int collectionId;
    private byte[] documentContent;
    private boolean markUpStatus;

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getDateUpdated() {
        return dateUpdated;
    }

    
    public void setDateUpdated(String dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public int getDocId() {
        return docId;
    }

    public void setDocId(int docId) {
        this.docId = docId;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] getDocumentContent() {
        return documentContent;
    }

    public void setDocumentContent(byte[] documentContent) {
        this.documentContent = documentContent;
    }

    public boolean isMarkUpStatus() {
        return markUpStatus;
    }

    public void setMarkUpStatus(boolean markUpStatus) {
        this.markUpStatus = markUpStatus;
    }
    
}
