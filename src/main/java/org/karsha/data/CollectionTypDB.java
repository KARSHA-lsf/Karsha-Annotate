/*
/**
 *   Copyright (C) 2013, Lanka Software Foundation and and University of Maryland.
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
package org.karsha.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.karsha.entities.CollectionType;



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
