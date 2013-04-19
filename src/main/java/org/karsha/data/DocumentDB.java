/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.karsha.data;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import org.karsha.entities.Document;
import org.karsha.entities.User;
import java.sql.Blob;


/**
 * Copyright (C) 2012, Lanka Software Foundation.
 *
 * Date Author Changes April 14, 2012 v-bala Created
 *
 */
public class DocumentDB {
    
    //Used for Annotate tool
    public static ArrayList<Document> getAllDocumentsByCollectionId(int collectionId) {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "select * from document where CollectionId = ?";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, collectionId);
            rs = ps.executeQuery();
            ArrayList<Document> documentList = new ArrayList<Document>();
            while (rs.next())
            {
                Document d = new Document();
                d.setDocId(Integer.parseInt(rs.getString("DocId")));
                d.setDocumentName(rs.getString("Name").replaceAll("[\";\',.%$]()", " ").trim());
               // d.setMarkUpStatus(rs.getBoolean("MarkUpStatus"));
                
                documentList.add(d);
            }
            return documentList;
            
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

    public static ArrayList<Document> getAllMarkedDocumentsByCollectionId(int collectionId) {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "select DocId, Name from document where CollectionId = ? AND MarkUpStatus=?";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, collectionId);
            ps.setBoolean(2, true);
            rs = ps.executeQuery();
            ArrayList<Document> documentList = new ArrayList<Document>();
            while (rs.next())
            {
                Document d = new Document();
                d.setDocId(Integer.parseInt(rs.getString("DocId")));
                d.setDocumentName(rs.getString("Name").replaceAll("[\";\',.%$]()", " ").trim());
                
                documentList.add(d);
            }
            return documentList;
            
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
    
    
    public static int insert(Document newDocument) {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "INSERT INTO document (Name, DocFile, Date, UserID, CollectionID, MarkUpStatus ) VALUES (?, ?, CURDATE(), ?, ?,?)";
        
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, newDocument.getDocumentName().replaceAll("[\";\',.%$]()", " ").trim());
            
            // convert string to input stream
            byte[] byteFileContent = newDocument.getDocumentContent();
            InputStream is = new ByteArrayInputStream(byteFileContent); 
            
            ps.setBlob(2,is);
            ps.setInt(3, newDocument.getUserId());
            ps.setInt(4, newDocument.getCollectionId());
            ps.setBoolean(5, false);
            
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


    public static int insertDocMetaData(Document newDocument) {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "INSERT INTO document (Name, Date, UserID, CollectionID ) VALUES (?, CURDATE(), ?, ?)";
        
        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, newDocument.getDocumentName().replaceAll("[\";\',.%$]()", " ").trim());
            
            // convert string to input stream
           // byte[] byteFileContent = newDocument.getDocumentContent();
           // InputStream is = new ByteArrayInputStream(byteFileContent); 
            
           // ps.setBlob(2,is);
            ps.setInt(2, newDocument.getUserId());
            ps.setInt(3, newDocument.getCollectionId());
           // ps.setBoolean(5, false);
            
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
    
    
    
    public static boolean isFileNameDuplicate(String documentName) {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "Select DocId from document where Name = ?";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, documentName.replaceAll("[\";\',.%$]()", " ").trim());
            rs = ps.executeQuery();
            return rs.next();
            
        }
        catch(SQLException e)
        {
            e.printStackTrace();
            return false;
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
    }
    
    
    public static void updateMarkUpStatusTrue(int docID ){
        
        int updateQuery = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        //Document d= new Document();
        
        try
        {
                 
            
        String query = "UPDATE document SET MarkUpStatus = ? WHERE DocID = ?";

        
            ps = connection.prepareStatement(query);
            ps.setBoolean(1, true);
             ps.setInt(2, docID);
                          
              updateQuery = ps.executeUpdate();
                            if (updateQuery != 0) { 
              }
           
         
    
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
      
          
        
    }
        
           public static void updateMarkUpStatusFalse(int docID ){
        
        int updateQuery = 0;
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        //Document d= new Document();
        
        try
        {
                 
            
        String query = "UPDATE document SET MarkUpStatus = ? WHERE DocID = ?";

        
            ps = connection.prepareStatement(query);
            ps.setBoolean(1, false);
             ps.setInt(2, docID);
                          
              updateQuery = ps.executeUpdate();
                            if (updateQuery != 0) { 
              }
           
         
    
        
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        
        }
        finally
        {
            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        
      
          
        
    } 
    
    public static Document getDocumentByDocId(int docId) {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        
        String query = "select DocId , Name from document where DocId = ?";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setInt(1, docId);
            rs = ps.executeQuery();
           
            rs.next();
            Document d = new Document();
            d.setDocId(Integer.parseInt(rs.getString("DocId")));
            d.setDocumentName(rs.getString("Name").replaceAll("[\";\',.%$]()", " ").trim());
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
    
    public static Document getDocumentDataByDocName(String name) {
        
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        Blob blob;
      //  String query = "Select DocId, DocFile from document where Name = ?";
String query = "Select DocId from document where Name = ?";

        try
        {
            ps = connection.prepareStatement(query);
            ps.setString(1, name.replaceAll("[\";\',.%$]()", " ").trim());
            rs = ps.executeQuery();
           
            rs.next();
            Document d = new Document();
            d.setDocId(Integer.parseInt(rs.getString("DocId")));
//             blob = rs.getBlob("DocFile");
//             d.setDocumentContent(blob.getBytes((long) 1, (int) blob.length()));
 
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
    
}
