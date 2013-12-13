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
 * Sep 5, 2012     Kasun Perera    Created   
 * 
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
 * Database Connections for DocSection Table
 * 
 */
public class DocSectionDB {
    
  
    public static ArrayList<DocSection> getFullDocumentByDocumentId(int documentId) {
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
