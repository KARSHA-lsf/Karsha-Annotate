
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
 * Date             Author          Changes 
 * Mar 8, 2013     Kasun Perera    Created   
 * 
 */ 

package org.karsha.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.karsha.entities.MatchPhraseFibo;
import org.karsha.entities.MatchSectionFibo;

/**
 * TODO- describe the  purpose  of  the  class
 * 
 */
public class MatchPhraseFiboDB {
    
     public static void saveMatchSectionFiboDB(ArrayList<MatchPhraseFibo> phrasefibolist) {
        int updateQuery = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            for (int i = 0; i < phrasefibolist.size(); i++) {
                int phraseID = phrasefibolist.get(i).getPhraseId();
                int userId = phrasefibolist.get(i).getUserId();
                int fiboId = phrasefibolist.get(i).getFiboTermId();
               



                String query = "INSERT INTO matchphrasefibo(PhraseID, FIBOTermID , UserID) VALUES (?, ?, ? )";


                ps = connection.prepareStatement(query);
                ps.setInt(1, phraseID);
                ps.setInt(2, fiboId);
                ps.setInt(3, userId);
               
                updateQuery = ps.executeUpdate();
                if (updateQuery != 0) {
                }



            }

        } catch (SQLException e) {
            e.printStackTrace();

        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }

}
