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
public class User implements Serializable{
    
    private String userName;
    private String emailAddress;
    private String password;
    private int userId;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
    

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
    
    
    
}
