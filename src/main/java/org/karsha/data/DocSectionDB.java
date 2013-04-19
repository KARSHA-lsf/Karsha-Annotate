/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.karsha.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.karsha.entities.DocSection;
import org.karsha.entities.Document;

/** 
 * Copyright (C) 2012, Lanka Software Foundation. 
 * 
 * Date             Author          Changes 
 * Sep 5, 2012     Kasun Perera    Created   
 * 
 */ 

/**
 * Database Connections for DocSection Table
 * 
 */
public class DocSectionDB {
    
  
    public static ArrayList<DocSection> getAllDocumentsByCollectionId(int documentId) {
         ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Blob blob;
        String query = "select * from docsection where DocId = ?";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, documentId);
            rs = ps.executeQuery();
            ArrayList<DocSection> docSectionList = new ArrayList<DocSection>();
            while (rs.next())
            {
                DocSection d = new DocSection();
                d.setSectionId(Integer.parseInt(rs.getString("SectionId")));
                d.setSectionName(rs.getString("SectionName").replaceAll("[\";\',.%$]()", " ").trim());
               // blob = rs.getBlob("SectionFile");
           
               // d.setSectionContent(blob.getBytes((long) 1, (int) blob.length()));
                docSectionList.add(d);
            }
            return docSectionList;
            
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

    
    public static DocSection getDocumentDataByDocId(int secId) {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Blob blob;
        String query = "Select SectionName, DocId, SectionFile from docsection where SectionID = ?";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, secId);
            rs = ps.executeQuery();
           
            rs.next();
            DocSection d = new DocSection();
            d.setSectionName(rs.getString("SectionName").replaceAll("[\";\',.%$]()", " ").trim());
            d.setSectionId(secId);
            d.setParentDocId(rs.getInt("DocId"));
            blob = rs.getBlob("SectionFile");
            d.setSectionContent(blob.getBytes((long) 1, (int) blob.length()));
            
            return d;
            
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

        public static int insert(DocSection newDocument) {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "INSERT INTO docsection (SectionName,  SectionFile, Date, DocID, UserID,SectionCategory  ) VALUES (?, ?, CURDATE(), ?, ?,?)";
        
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, newDocument.getSectionName().replaceAll("[\";\',.%$]()", " ").trim());
            
            // convert string to input stream
            byte[] byteFileContent = newDocument.getSectiontContent();
            InputStream is = new ByteArrayInputStream(byteFileContent); 
            
            ps.setBlob(2,is);
            ps.setInt(3, newDocument.getParentDocId());
            ps.setInt(4, newDocument.getUserId());
            ps.setInt(5, newDocument.getSectionCatog());
            
            ps.executeUpdate();
            
            return 1;
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return 0;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        

    }

}
