/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.data;

import java.sql.*;
import java.util.ArrayList;
import org.karsha.entities.FiboTerm;

/**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes April 14, 2012 Kasun Perera Created 
 * June 20, 2012 Kasun Perera Made all the methods Static with modification to their references
 */
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
