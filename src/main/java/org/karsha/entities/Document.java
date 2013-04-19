
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
 * Date Author Changes April 14, 2012 v-bala Created
 *
 */
package org.karsha.entities;

import java.io.Serializable;


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
