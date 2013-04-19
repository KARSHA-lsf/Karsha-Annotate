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
 * Date Author Changes April 14, 2012 Kasun Perera Created 
 * June 20, 2012 Kasun Perera Made all the methods Static with modification to their references
 */
package org.karsha.data;

import java.sql.*;
import java.util.ArrayList;
import org.karsha.entities.FiboTerm;


public class FiboDB {
    
    public static ArrayList<FiboTerm> getAllFiboTerms() {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        Statement ps = null;
        ResultSet rs = null;
        
        String query = "select FIBOTermID,FIBOTerm,TermDefinition from fiboterm";
        
        try {
            ps = connection.createStatement();
            
            rs = ps.executeQuery(query);
            ArrayList<FiboTerm> fiboList = new ArrayList<FiboTerm>();
            while (rs.next()) {
                FiboTerm d = new FiboTerm();
                d.setFiboId(Integer.parseInt(rs.getString("FIBOTermID")));
                d.setFiboTerm(rs.getString("FIBOTerm").replaceAll("[\";\',.%$]()", " ").trim());
                d.setFiboDefinition(rs.getString("TermDefinition").replaceAll("[\";\',.%$]()", " ").trim());
                fiboList.add(d);
            }
            return fiboList;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static FiboTerm getFiboTermById( int fiboID) {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
         PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "select FIBOTermID,FIBOTerm,TermDefinition from fiboterm where FIBOTermID=?";
        
        try {
           ps = connection.prepareStatement(query);
            ps.setInt(1, fiboID);
            rs = ps.executeQuery();
           FiboTerm d = new FiboTerm();
            while (rs.next()) {
                
                d.setFiboId(Integer.parseInt(rs.getString("FIBOTermID")));
                d.setFiboTerm(rs.getString("FIBOTerm").replaceAll("[\";\',.%$]()", " ").trim());
                d.setFiboDefinition(rs.getString("TermDefinition").replaceAll("[\";\',.%$]()", " ").trim());
                
            }
            return d;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static FiboTerm getFiboTermID(String fiboTerm) {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
         PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "select FIBOTermID,FIBOTerm from fiboterm where FIBOTerm=?";
        
        try {
           ps = connection.prepareStatement(query);
            ps.setString(1, fiboTerm);
            rs = ps.executeQuery();
           FiboTerm d = new FiboTerm();
            while (rs.next()) {
                
                d.setFiboId(Integer.parseInt(rs.getString("FIBOTermID")));
                d.setFiboTerm(rs.getString("FIBOTerm").replaceAll("[\";\',.%$]()", " ").trim());
               // d.setFiboDefinition(rs.getString("TermDefinition").replaceAll("[\";\',.%$]()", " ").trim());
                
            }
            return d;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
    }
    
    public static ArrayList<FiboTerm> getAllFiboTermsByCollectionID(int collectionId) {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "select FIBOTermID,FIBOTerm from fiboterm where CollectionId = ?";
        
        try {
            ps = connection.prepareStatement(query);
            ps.setInt(1, collectionId);
            rs = ps.executeQuery();
            ArrayList<FiboTerm> fiboList = new ArrayList<FiboTerm>();
            
            while (rs.next()) {
                FiboTerm d = new FiboTerm();
                d.setFiboId(Integer.parseInt(rs.getString("FIBOTermID")));
                d.setFiboTerm(rs.getString("FIBOTerm").replaceAll("[\";\',.%$]()", " ").trim());
                
                fiboList.add(d);
            }
            return fiboList;
            
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        } finally {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
        
        
    }
}
