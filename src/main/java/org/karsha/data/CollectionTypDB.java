/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.karsha.entities.CollectionType;

/**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes April 14, 2012 v-bala Created
 *
 */

/*
 * Class contains methods for communicating with the Collection table
 */
public class CollectionTypDB {
    
    /**
     * Getting the all collection types from the Collection Table
     * @return- List of all the collection types 
     */
    public static ArrayList<CollectionType> getAllCollectionType() {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "select * from collection";

        try
        {
            ps = connection.prepareStatement(query);
            rs = ps.executeQuery();
            ArrayList<CollectionType> collectionTypeList = new ArrayList<CollectionType>();
            while (rs.next())
            {
                CollectionType ct = new CollectionType();
                ct.setCollectionId(Integer.parseInt(rs.getString("CollectionId")));
                ct.setCollectionType(rs.getString("CollectionName"));
                
                collectionTypeList.add(ct);
            }
            return collectionTypeList;
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return null;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
}
