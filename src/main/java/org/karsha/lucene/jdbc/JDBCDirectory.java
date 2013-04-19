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
 * Jan 1, 2013     Kasun Perera    Created   
 * 
 */ 

package org.karsha.lucene.jdbc;



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
