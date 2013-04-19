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
import org.karsha.entities.MatchPhraseFibo;
import org.karsha.entities.MatchSectionFibo;

/** 
 * Copyright (C) 2012, Lanka Software Foundation. 
 * 
 * Date             Author          Changes 
 * Mar 8, 2013     Kasun Perera    Created   
 * 
 */ 

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
