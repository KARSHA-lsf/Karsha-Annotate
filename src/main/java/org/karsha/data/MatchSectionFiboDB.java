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
import org.karsha.entities.MatchSectionFibo;

/**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes Oct 19, 2012 Kasun Perera Created
 *
 */
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
