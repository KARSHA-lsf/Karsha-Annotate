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
 * Date Author Changes Oct 19, 2012 Kasun Perera Created
 *
 */
package org.karsha.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.karsha.entities.MatchSectionFibo;


/**
 * TODO- describe the purpose of the class
 *
 */
public class MatchSectionFiboDB {

    
    /**
     * insert annotated data into the database
     * @param sectionfibolist -data received a array list 
     */
    public static void saveMatchSectionFiboDB(ArrayList<MatchSectionFibo> sectionfibolist) {
        int updateQuery = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;


        try {
            for (int i = 0; i < sectionfibolist.size(); i++) {
                int sectionID = sectionfibolist.get(i).getSectionId();
                int userId = sectionfibolist.get(i).getUserId();
                int fiboId = sectionfibolist.get(i).getFiboTermId();
                double simScore = sectionfibolist.get(i).getSimScore();



                String query = "INSERT INTO matchsectionfibo(SectionID, FIBOTermID , UserID,SimScore) VALUES (?, ?, ?,? )";


                ps = connection.prepareStatement(query);
                ps.setInt(1, sectionID);
                ps.setInt(2, fiboId);
                ps.setInt(3, userId);
                ps.setDouble(4, simScore);
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
