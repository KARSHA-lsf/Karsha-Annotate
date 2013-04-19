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
 * This class defines class variables related to Collection table
 */
public class CollectionType implements Serializable{
    
    private int collectionId;
    private String collectionType;

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }

    public String getCollectionType() {
        return collectionType;
    }

    public void setCollectionType(String collectionType) {
        this.collectionType = collectionType;
    }
    
    
}
