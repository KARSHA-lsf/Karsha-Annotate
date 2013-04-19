/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.karsha.lucene.jdbc;

/** 
 * Copyright (C) 2012, Lanka Software Foundation. 
 * 
 * Date             Author          Changes 
 * Jan 1, 2013     Kasun Perera    Created   
 * 
 */ 

/**
 * TODO- describe the  purpose  of  the  class
 * 
 */
public class JDBCDirectory {
    
     /**
     * The jdbc directory.
     */
    //private Directory	jdbcDirectory	= null;
    private static MyJDBCDirectory jdbcDirectory = null;

    /**
     * Gets the jdbc directory.
     *
     * @return the jdbc directory
     */
    public MyJDBCDirectory getJdbcDirectory() {
        if (jdbcDirectory == null) {
            throw new IllegalStateException("Index not yet build, rerun indexing");
        }
        return jdbcDirectory;
    }

    /**
     * Sets the jdbc directory.
     *
     * @param jdbcDirectory the new jdbc directory
     */
    public void setJdbcDirectory(MyJDBCDirectory jdbcDirectory) {
        
        /*
         * set the details here
         */
       
        
        this.jdbcDirectory = jdbcDirectory;
    }


}
