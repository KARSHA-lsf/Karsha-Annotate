
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
    public static ArrayList<Document> getAllDocuments() {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "select * from document";

        try
        {
            ps = connection.prepareStatement(query);
           // ps.setInt(1, collectionId);
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

    public static ArrayList<Document> getSimilarDocuments() {

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;

        String query = "select * from document";

        try
        {
            ps = connection.prepareStatement(query);
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
    //returns top k documents according to the selected fibo terms
    public static ArrayList<Document> getSimilarDocs(String[] selectedFibos){

        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        int NumberofSelectedFibos = selectedFibos.length;
        ArrayList<Document> documentList = new ArrayList<Document>();

        for(int i=0; i<selectedFibos.length; i++){
            String query = "select DocId,SimilarityValue from matchDocFibo where FiboId="+selectedFibos[i]+";";
            try {
                ps = connection.prepareStatement(query);
                rs = ps.executeQuery();

            while (rs.next())
            {
                Document d = new Document();
                d.setDocId(Integer.parseInt(rs.getString("DocId")));
                d.setDocumentName(rs.getString("Name").replaceAll("[\";\',.%$]()", " ").trim());

                documentList.add(d);
            }
            } catch (Exception e) {
                e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
            }

            DBUtil.closeResultSet(rs);
            DBUtil.closePreparedStatement(ps);
            pool.freeConnection(connection);
        }
        return documentList;
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
    public static ArrayList<Integer> getAlldocumentIDs(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Integer> docIDs = new ArrayList<Integer>();
        int doc = 0;


        String query = "Select DocId from document";


        try
        {
            ps = connection.prepareStatement(query);
            //ps.setString(1, name.replaceAll("[\";\',.%$]()", " ").trim());
            rs = ps.executeQuery();

            while(rs.next()){

                doc = rs.getInt("DocId");
                // System.out.println(doc);
                docIDs.add(doc);

            }
            return docIDs;

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

    
    /*public static ArrayList<Integer> getAlldocumentIDs(){
        ConnectionPool pool = ConnectionPool.getInstance();
        Connection connection = pool.getConnection();
        PreparedStatement ps = null;
        ResultSet rs = null;
        ArrayList<Integer> docIDs = new ArrayList<Integer>();
        int doc = 0;
        
        
        String query = "Select DocId from document";
        
        
        try
        {
            ps = connection.prepareStatement(query);
            //ps.setString(1, name.replaceAll("[\";\',.%$]()", " ").trim());
            rs = ps.executeQuery();
           
            while(rs.next()){
                
                doc = rs.getInt("DocId");
               // System.out.println(doc);
                docIDs.add(doc);
               
            }
            return docIDs;
            
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
    
    }*/
    
}
